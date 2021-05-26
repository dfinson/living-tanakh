package living.tanach.api.utils;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Component
public class S3Service {

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Value("${aws.s3.thumbnails-bucket-name}")
  private String thumbnailsBucketName;

  @Value("#{new Integer('${s3.url-ttl:60}')}")
  private Integer urlTtl;

  @Value("#{new Integer('${s3.download-url-ttl:500}')}")
  private Integer downloadUrlTtl;

  private S3Client s3Client;
  private S3Presigner s3Presigner;

  @PostConstruct
  @SneakyThrows
  private void init() {
    s3Presigner = S3Presigner.create();
    s3Client = S3Client.create();
  }

  public String generateUploadUrl(String key, String contentType) {
    val objectRequest = PutObjectRequest
      .builder()
      .bucket(bucketName)
      .key(key)
      .contentType(contentType)
      .build();
    val presignRequest = PutObjectPresignRequest
      .builder()
      .signatureDuration(Duration.ofMinutes(urlTtl))
      .putObjectRequest(objectRequest)
      .build();
    val presignedRequest = s3Presigner.presignPutObject(presignRequest);
    val url = presignedRequest.url();
    return url.toString();
  }

  public String generateDownloadUUrl(String key) {
    try {
      val getObjectRequest = GetObjectRequest
        .builder()
        .bucket(this.bucketName)
        .key(toPreviewKey(key))
        .build();
      val getObjectPresignRequest = GetObjectPresignRequest
        .builder()
        .signatureDuration(Duration.ofMinutes(downloadUrlTtl))
        .getObjectRequest(getObjectRequest)
        .build();
      val presignedGetObjectRequest = s3Presigner.presignGetObject(
        getObjectPresignRequest
      );
      return presignedGetObjectRequest.url().toString();
    } catch (S3Exception e) {
      e.getStackTrace();
      throw new RuntimeException(e);
    }
  }

  public String generatePreviewUrl(String key) {
    try {
      val getObjectRequest = GetObjectRequest
        .builder()
        .bucket(thumbnailsBucketName)
        .key(toPreviewKey(key))
        .build();
      val getObjectPresignRequest = GetObjectPresignRequest
        .builder()
        .signatureDuration(Duration.ofMinutes(urlTtl))
        .getObjectRequest(getObjectRequest)
        .build();
      val presignedGetObjectRequest = s3Presigner.presignGetObject(
        getObjectPresignRequest
      );
      return presignedGetObjectRequest.url().toString();
    } catch (S3Exception e) {
      e.getStackTrace();
      throw new RuntimeException(e);
    }
  }

  public String generateHDPreviewUrl(String key) {
    try {
      val getObjectRequest = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(toPreviewKey(key))
        .build();
      val getObjectPresignRequest = GetObjectPresignRequest
        .builder()
        .signatureDuration(Duration.ofMinutes(urlTtl))
        .getObjectRequest(getObjectRequest)
        .build();
      val presignedGetObjectRequest = s3Presigner.presignGetObject(
        getObjectPresignRequest
      );
      return presignedGetObjectRequest.url().toString();
    } catch (S3Exception e) {
      e.getStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void deleteObjects(Collection<String> keys) {
    deleteObjects(keys, bucketName);
    var previewKeys = keys
      .stream()
      .map(this::toPreviewKey)
      .collect(Collectors.toSet());
    deleteObjects(previewKeys, thumbnailsBucketName);
  }

  private void deleteObjects(Collection<String> keys, String bucketName) {
    try {
      val identifiers = keys
        .stream()
        .map(key -> ObjectIdentifier.builder().key(key).build())
        .collect(Collectors.toList());

      val deleteObjectsRequest = DeleteObjectsRequest
        .builder()
        .bucket(bucketName)
        .delete(Delete.builder().objects(identifiers).build())
        .build();
      s3Client.deleteObjects(deleteObjectsRequest);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String toPreviewKey(String key) {
    return key.replaceAll("[.].+", ".jpeg");
  }

  public void uploadObject(File object, String key) {
    s3Client.putObject(
      PutObjectRequest.builder().bucket(bucketName).key(key).build(),
      RequestBody.fromFile(object)
    );
  }

  public File getObject(String key) {
    try {
      val request = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();
      val response = s3Client.getObjectAsBytes(request);
      val inStream = response.asInputStream();
      Files.deleteIfExists(Paths.get(key));
      File targetFile = new File(key);
      Files.copy(inStream, targetFile.toPath(), REPLACE_EXISTING);
      IOUtils.closeQuietly(inStream);
      return targetFile;
    } catch (Exception e) {
      log.error(
        e.getClass().getName() +
        " while getting object with key: \"" +
        key +
        "\" from S3. Message is: \n" +
        e.getMessage()
      );
      throw new RuntimeException(e);
    }
  }

  public Long getFullSizeMediaObjectSize(String key) {
    try {
      val request = GetObjectRequest
        .builder()
        .bucket(bucketName)
        .key(key)
        .build();
      val response = s3Client.getObjectAsBytes(request);
      return response.response().contentLength();
    } catch (Exception e) {
      return -1L;
    }
  }
}
