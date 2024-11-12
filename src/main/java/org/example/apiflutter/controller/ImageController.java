package org.example.apiflutter.controller;

import org.example.apiflutter.dto.response.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
//import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.ImageRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.entity.Images;
import org.example.apiflutter.services.ImageServices;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {
    ImageServices imageServices;

    @PostMapping("/upload")
    ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

//        List<ImageRequest> imageRequests = imageServices.saveImage(files, productId);
        return ResponseEntity.ok(new ApiResponse().builder()
                .result(imageServices.saveImage(files, productId))
//                .result(imageRequests)
                .build());


    }

    @GetMapping("/image/download/{imageId}")
    ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Images images = imageServices.getById(imageId);
//        ByteArrayResource resource = new ByteArrayResource(images.getImage().getBytes(1, (int) images.getImage().length()));
        ByteArrayResource resource = new ByteArrayResource(images.getImage().getBytes(1, (int) images.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(images.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + images.getFileName() + "\"")
                .body((Resource) resource);
    }

    @GetMapping("/{imageId}")
    ApiResponse<Images> getById(@PathVariable Long imageId) {
        return ApiResponse.<Images>builder()
                .result(imageServices.getById(imageId))
                .build();
    }

    @PutMapping("/{imageId}")
    ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file) {

        Images images = imageServices.getById(imageId);

        imageServices.update(file, imageId);
        return ResponseEntity.ok(new ApiResponse().builder()
                .result(images)

                .build());


    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable Long id) {
        imageServices.delete(id);
        return "Image has been Deleted";
    }
    @GetMapping("/by-productId/{productId}")

    ApiResponse<List<ImageResponse>> getByproductId(@PathVariable Long productId){
        return ApiResponse.<List<ImageResponse>>builder()
                .result(imageServices.getProductByImage(productId))
                .build();
    }

}
