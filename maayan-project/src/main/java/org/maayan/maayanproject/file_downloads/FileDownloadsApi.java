package org.maayan.maayanproject.file_downloads;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.time.DateTime;
import org.maayan.maayanproject.utils.S3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class FileDownloadsApi {

  private final S3Service s3Service;

  // http://localhost:5000/download-images
  @PostMapping(value = "/download-images", produces = "application/zip")
  public void downloadImagesEndpoint(
    @RequestBody List<String> keys,
    HttpServletResponse response
  ) throws IOException {
    log.info(
      "start process for download request for keys: [\"" +
      String.join("\", \"", keys) +
      "\"]"
    );
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader(
      "Content-Disposition",
      "attachment; filename=\"images.zip\""
    );
    var zipOutputStream = new ZipOutputStream(response.getOutputStream());
    var files = getObjectsFromS3(keys);
    for (File file : files) {
      zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
      FileInputStream fileInputStream = new FileInputStream(file);
      IOUtils.copy(fileInputStream, zipOutputStream);
      fileInputStream.close();
      zipOutputStream.closeEntry();
    }
    zipOutputStream.close();
  }

  private static final Executor executor = Executors.newFixedThreadPool(100);

  private List<File> getObjectsFromS3(List<String> keys) {
    keys = keys.stream().map(this::decodeKey).collect(Collectors.toList());
    log.info("start fetch of " + keys.size() + " objects from S3");
    long start = DateTime.now().getMillis();
    List<File> objects = new ArrayList<>();
    var lock = new ReentrantLock();
    List<Runnable> tasks = keys
      .stream()
      .map(
        key ->
          (Runnable) () -> {
            var object = s3Service.getObject(key);
            lock.lock();
            objects.add(object);
            lock.unlock();
          }
      )
      .collect(Collectors.toList());
    CompletableFuture<?>[] futures = tasks
      .stream()
      .map(task -> CompletableFuture.runAsync(task, executor))
      .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
    long end = DateTime.now().getMillis();
    log.info(
      "Fetched " +
      keys.size() +
      " objects from S3 in " +
      String.format("%.2f", (float) (end - start) / 1000) +
      " seconds"
    );
    return objects;
  }

  private String decodeKey(String key) {
    try {
      return URLDecoder.decode(key, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
