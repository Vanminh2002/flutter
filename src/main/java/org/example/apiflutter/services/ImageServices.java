package org.example.apiflutter.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.ImageRequest;
import org.example.apiflutter.dto.response.ImageResponse;
import org.example.apiflutter.entity.Images;
import org.example.apiflutter.entity.Product;
import org.example.apiflutter.exception.ImageException;
import org.example.apiflutter.mapper.ImageMapper;
import org.example.apiflutter.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageServices {

    ImageRepository imageRepository;
     ProductServices productServices;
     ImageMapper imageMapper;

    public Images getById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageException("Image Not Found ID" + id));
    }


    public void delete(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ImageException("Image Not Found With ID" + id);
        });
    }

    public List<ImageRequest> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productServices.getById(productId);
        List<ImageRequest> saveImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Images images = new Images();
                images.setFileName(file.getOriginalFilename());
                images.setFileType(file.getContentType());
//                images.setImage(new SerialBlob(file.getBytes()));
                images.setImage(new SerialBlob(file.getBytes()));
                images.setProducts(product);

                String pathDownload = "/flutter/images/image/download/";
                String downloadUrl = pathDownload + images.getId();
                images.setDownloadUrl(downloadUrl);
                Images saveImage = imageRepository.save(images);
                saveImage.setDownloadUrl(pathDownload + saveImage.getId());
                imageRepository.save(saveImage);


                ImageRequest imageRequest = new ImageRequest();
                imageRequest.setId(saveImage.getId());
                imageRequest.setFileName(saveImage.getFileName());
                imageRequest.setDownloadUrl(saveImage.getDownloadUrl());
                saveImageDto.add(imageRequest);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());

            }
        }
        return saveImageDto;
    }

    public void update(MultipartFile file, Long imageId) {
        Images images = getById(imageId);
        try {
            images.setFileName(file.getOriginalFilename());
            images.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(images);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
//        return null;
    }

    public List<ImageResponse> getProductByImage(Long productId){
        List<Images> images = imageRepository.findByProductsId(productId);

        // Chuyển đổi các entity Images thành ImageResponse
        return images.stream()
                .map(imageMapper::toImageResponse)
               .toList();
    }
}
