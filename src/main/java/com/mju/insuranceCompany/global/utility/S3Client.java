package com.mju.insuranceCompany.global.utility;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Client {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadFile(String fileDir, MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) return null;
        try {
            File file = convertMultipartFileToFile(multipartFile).orElseThrow();
            return upload(fileDir, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private String upload(String dir, File file) {
        String fileName = "upload/"+ dir +"/"+ UUID.randomUUID();
        return putS3(file, fileName);
    }

    private String putS3(File file, String key) {
        amazonS3.putObject(new PutObjectRequest(bucket, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        removeFile(file); // Delete local file instance

        return getS3(bucket, key);
    }

    private String getS3(String bucket, String key) {
        return amazonS3.getUrl(bucket, key).toString();
    }

    public String updateFile(String fileDir, MultipartFile multipartFile, String originFileUrl) {
        if(multipartFile.isEmpty() || originFileUrl==null || originFileUrl.isBlank()) return null;
        try {
            File file = convertMultipartFileToFile(multipartFile).orElseThrow();

            deleteFile(fileDir, originFileUrl); // Delete S3 Object for update file

            return upload(fileDir, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(String fileDir, String originFileUrl) {
        int index = originFileUrl.lastIndexOf("upload/"+ fileDir +"/");
        String key = originFileUrl.substring(index);
        deleteS3(key);
    }

    private void deleteS3(String key) {
        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new AmazonS3Exception("Object " +key+ " does not exist!");
        }
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    /**
     * Delete local file instance
     * @param file Deleted file
     */
    private void removeFile(File file) {
        file.delete();
    }
}
