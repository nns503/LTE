package lte.backend.util;

import lombok.RequiredArgsConstructor;
import lte.backend.image.exception.BadRequestUploadImageFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class S3Service {

    private final S3Client s3Client;
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${CLOUDFRONT_DOMAIN}")
    private String cloudFrontDomain;


    @Transactional
    public String uploadFile(MultipartFile multipartFile) {
        validateMultipartFile(multipartFile);
        String originalFilename = getOriginalFilename(multipartFile);
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String storeFileName = createStoreFileName(ext);

        return uploadS3File(multipartFile, storeFileName);
    }

    public String uploadS3File(MultipartFile file, String storeFileName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .key(storeFileName)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            throw new BadRequestUploadImageFileException();
        }

        return cloudFrontDomain + "/" + storeFileName;
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BadRequestUploadImageFileException();
        }

    }

    private String getOriginalFilename(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        validateOriginalFilename(originalFilename);

        return originalFilename;
    }

    private void validateOriginalFilename(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BadRequestUploadImageFileException();
        }
    }

    private String createStoreFileName(String ext) {
        String uuid = UUID.randomUUID().toString();
        return uuid + ext;
    }
}
