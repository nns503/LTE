package lte.backend.image.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.image.dto.response.UploadImageResponse;
import lte.backend.util.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageController implements ImageApi {

    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestPart MultipartFile image
    ) {
        String url = s3Service.uploadFile(image);
        return ResponseEntity.ok(new UploadImageResponse(url));
    }
}
