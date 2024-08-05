package com.example.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    /**
     * 여러 파일을 S3에 업로드하고, 파일 URL 목록을 반환합니다.
     *
     * @param multipartFiles 업로드할 파일 목록
     * @return 업로드된 파일의 URL 목록
     */
    public List<String> uploadFile(List<MultipartFile> multipartFiles) {
        List<String> fileUrlList = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                String fileUrl = amazonS3.getUrl(bucket, fileName).toString(); // S3 URL 가져오기
                fileUrlList.add(fileUrl);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
        });

        return fileUrlList;
    }

    /**
     * 단일 파일을 S3에 업로드하고, 파일 URL을 반환합니다.
     *
     * @param multipartFile 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 파일 입출력 중 오류 발생 시
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3.getUrl(bucket, fileName).toString(); // S3 URL 가져오기
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }

    /**
     * 파일 업로드 시, 파일명을 난수화하기 위해 UUID를 활용하여 난수를 생성합니다.
     *
     * @param fileName 원본 파일명
     * @return 난수화된 파일명
     */
    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    /**
     * 파일의 확장자를 추출합니다. 확장자가 없는 경우 예외를 발생시킵니다.
     *
     * @param fileName 파일명
     * @return 파일 확장자
     */
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    /**
     * S3에서 파일을 삭제합니다.
     *
     * @param fileName 삭제할 파일명
     */
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}