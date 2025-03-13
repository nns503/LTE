package lte.backend.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.image.dto.response.UploadImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/image")
@Tag(name = "Image", description = "이미지 로직 관리")
public interface ImageApi {

    @Operation(summary = "이미지 업로드")
    @PostMapping
    ResponseEntity<UploadImageResponse> uploadImage(
            @Parameter(description = "이미지 파일") @RequestPart MultipartFile image
    );
}
