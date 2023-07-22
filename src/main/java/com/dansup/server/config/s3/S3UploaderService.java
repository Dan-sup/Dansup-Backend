package com.dansup.server.config.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploaderService {

    private final AmazonS3Client amazonS3Client;


    /**
     * @description 프로필 이미지에 해당하는 이미지 파일을 버킷의 image 폴더에 저장한다.
     * @return image 파일이 저장된 위치의 S3 주소 반환.
     */

    public String imageUpload(MultipartFile multipartFile) throws IOException {
        return upload(multipartFile, "dansupbucket", "image");
    }

    /**
     * @description 포트폴리오 영상 및 수업 영상에 해당하는 영상 파일을 버킷의 video 폴더에 저장한다.
     * @return video 파일이 저장된 위치의 S3 주소 반환.
     */
    public String videoUpload(MultipartFile multipartFile) throws IOException {
        return upload(multipartFile, "dansupbucket", "video");
    }

    public String upload(MultipartFile multipartFile, String bucket, String dirName) throws IOException {
        if(multipartFile.isEmpty())
            return null;
        String fileName = dirName + "/" + UUID.randomUUID() + multipartFile.getName() + "." + extractExt(multipartFile.getOriginalFilename());   // S3에 저장될 파일 이름
        String uploadImageUrl = putS3(multipartFile, bucket, fileName); // s3로 업로드
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(MultipartFile multipartFile, String bucket, String fileName) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        log.info("Metadata 생성 완료");

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        log.info("파일 업로드 완료");

        return amazonS3Client.getUrl(bucket, fileName).toString(); // 접근가능한 URL 가져오기
    }

    /**
     * @description 사용자가 업로드한 파일에서 확장자를 추출한다.
     *
     * @param originalFilename 원본 파일 이름
     * @return 파일 확장자
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest("dansupbucket", fileName));
    }

}
