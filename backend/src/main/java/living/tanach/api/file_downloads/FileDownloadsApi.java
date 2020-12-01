package living.tanach.api.file_downloads;

import living.tanach.api.utils.S3Service;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@AllArgsConstructor
public class FileDownloadsApi {

    private final S3Service s3Service;
    // http://localhost:5000/download-images?keys=['image1.jpg', 'image2.png', ...]
    @GetMapping(value = "/download-images", produces = "application/zip")
    public void downloadImagesEndpoint(@RequestParam List<String> keys, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"images.zip\"");
        var zipOutputStream = new ZipOutputStream(response.getOutputStream());
        var files = keys.stream().map(s3Service::getObject).collect(Collectors.toList());
        for (File file : files) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
    }
}
