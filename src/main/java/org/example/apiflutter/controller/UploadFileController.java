package org.example.apiflutter.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.apiflutter.dto.request.ProductRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.ProductResponse;
import org.example.apiflutter.services.UploadFileServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UploadFileController {

    UploadFileServices uploadFileServices;

    @PostMapping()
    public boolean uploadFile(@RequestParam("file") MultipartFile file) {
       try{
           uploadFileServices.saveFile(file);
           return true;
       }catch (Exception e){
          log.error("Error");
       }
        return false;
    }

}
