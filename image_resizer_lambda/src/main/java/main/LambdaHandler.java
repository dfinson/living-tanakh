package main;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@SuppressWarnings("unchecked")
public class LambdaHandler {

    public static void main(String[] args) throws IOException {
        var handler = new LambdaHandler();
        var input = new HashMap<String, Object>(){{
            put("Records", Collections.singletonList(new HashMap<String, Object>(){{
                put("s3", new HashMap<String, Object>(){{
                    put("bucket", new HashMap<String, Object>(){{
                        put("name", "maayan-bucket");
                    }});
                    put("object", new HashMap<String, Object>(){{
                        put("key", "test.jpg");
                    }});
                }});
            }}));
        }};
        handler.handleRequest(input, null);
    }

    public String handleRequest(Map<String, Object> input, Context context) throws IOException {
        var bucketName = getSourceBucket(input);
        var key = getObjectKey(input);
        var s3 = S3Client.create();
        System.out.println("Start handling of request for image compression for key \"" + key + "\". Event from bucket \"" + bucketName + "\"");
        var originalImageObject = getObject(key, bucketName, s3, context);
        var compressedImageObject = compressImageObject(originalImageObject, key);
        uploadCompressedImage(compressedImageObject, key, s3);
        return String.format("Successfully applied compression transformation to %s", key);
    }

    private void uploadCompressedImage(File imageObject, String key, S3Client s3Client) {
        var bucketName = System.getenv("DEST_BUCKET");
        System.out.println("Uploading compressed version of " + key + " to " + bucketName);
        var response =
                s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), RequestBody.fromFile(imageObject));
        System.out.println("Upload complete with response: \n" + response.toString());
    }

    private File compressImageObject(File image, String key) throws IOException {
        System.out.println("Starting image compression...");
        var jpegParams = new JPEGImageWriteParam(null);
        jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(0.2f);
        var writer = ImageIO.getImageWritersByFormatName("jpg").next();
        System.out.println("Creating target file in /tmp/new/");
        var dirPath = Paths.get("/tmp/new");
        Files.createDirectory(dirPath);
        var compressedImage = new File("/tmp/new/" + image.getName());
        System.out.println("target file is: " + compressedImage.toString());
        var imageOutputStream = new FileImageOutputStream(compressedImage);
        writer.setOutput(imageOutputStream);
        var imageInputStream = ImageIO.read(new FileInputStream(image));
        writer.write(null, new IIOImage(imageInputStream, null, null), jpegParams);
        writer.dispose();
        System.out.println("Original image size: " + String.format("%.2f MB",((float)image.length())/(1024*1024)));
        System.out.println("Compressed image size: " + String.format("%.2f MB",((float)compressedImage.length())/(1024*1024)));
        return compressedImage;
    }

    public File getObject(String key, String bucketName, S3Client s3Client, Context context){
        try {
            var request = GetObjectRequest.builder().bucket(bucketName).key(key).build();
            var response =  s3Client.getObjectAsBytes(request);
            var inStream = response.asInputStream();
            System.out.println("fetched \"" + key + "\" from " + bucketName);
            System.out.println("deleting local file if exists");
            Files.deleteIfExists(Paths.get(key));
            System.out.println("creating temp local target file");
            File targetFile = new File("/tmp/" + key);
            Files.copy(inStream, targetFile.toPath(), REPLACE_EXISTING);
            IOUtils.closeQuietly(inStream);
            System.out.println("returning fetched object");
            return targetFile;
        }catch (Exception e){
            System.out.println("exception in getObject(...): " + e.getMessage());
            System.out.println("Exception stacktrace:\n\n\n");
            e.printStackTrace();
            System.out.println("Throwing new RuntimeException(e) for exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private String getSourceBucket(Map<String, Object> input){
        var bucket = asMap(getS3(input).get("bucket"));
        var bucketName = bucket.get("name");
        return (String) bucketName;
    }
    private String getObjectKey(Map<String, Object> input){
        var object = asMap(getS3(input).get("object"));
        var key = object.get("key");
        return (String) key;
    }
    private Map<String, Object> getS3(Map<String, Object> input){
        var records = (List<Object>) input.get("Records");
        var record = asMap(records.get(0));
        return asMap(record.get("s3"));
    }
    private Map<String, Object> asMap(Object obj){
        return (Map<String, Object>)obj;
    }
}
