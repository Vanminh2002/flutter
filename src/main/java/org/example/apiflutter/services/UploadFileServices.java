package org.example.apiflutter.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class UploadFileServices {
    private static final String STORAGE_DIRECTORY = "C:\\Users\\MyPC\\Desktop\\New folder\\api-flutter\\images";
//    private static final Path STORAGE_DIRECTORY = Path.of("D:\\api-flutter\\images");

    //    public UploadFileServices() {
//        try {
//            Files.createDirectories(STORAGE_DIRECTORY);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize UploadFileServices class", e);
//
//        }
//    }
//
//
//    private boolean isImageFile(MultipartFile file) {
//        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//        return Arrays.asList(new String[]{"png", "jpe", "jpeg",})
//                .contains(fileExtension.trim().toLowerCase());
//    }
//
//    public String saveFile(MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                throw new RuntimeException("File is empty");
//            }
//            if (!isImageFile(file)) {
//                throw new RuntimeException("File is not an image");
//            }
//            float size = file.getSize() / 1_000_000;
//            if (size > 5.0f) {
//                throw new RuntimeException("File is too large");
//            }
//            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
//            generatedFileName = generatedFileName + "." + fileExtension;
//            Path destinationFilePath = STORAGE_DIRECTORY
//                    .resolve(Paths.get(generatedFileName))
//                    .normalize().toAbsolutePath();
//            if (!destinationFilePath.getParent().equals(STORAGE_DIRECTORY.toAbsolutePath())) {
//                throw new RuntimeException("Destination file path does not match storage directory");
//            }
//            try (InputStream inputStream = file.getInputStream()) {
//                {
//                    Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
//                }
//                ;
//            }
//            return generatedFileName;
//
//        } catch (IOException e) {
//            throw new RuntimeException("Could not save file", e);
//        }
//    }
//
//    public Stream<Path> loadAll() {
//        return null;
//    }
//
//    public byte[] readFileContent(String fileName) {
//        return null;
//    }
//
//    public void deleteAllFile() {
//        return;
//    }
//    public String saveFile(MultipartFile file) throws IOException {
//
//        if (file.isEmpty()) {
//            throw new NullPointerException("File is null");
//        }
//        var targetFile = new File(STORAGE_DIRECTORY + "/" + File.separator + file.getOriginalFilename());
//        if (!Objects.equals(targetFile.getParent(), STORAGE_DIRECTORY)) {
//            throw new SecurityException("File is not a directory");
//        }
//        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//        return targetFile.toString();
//    }
// Lưu file ảnh vào thư mục local
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new NullPointerException("File is null");
        }

        // Tạo tên file duy nhất
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File targetFile = new File(STORAGE_DIRECTORY + File.separator + fileName);

        // Kiểm tra và tạo thư mục nếu chưa có
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        // Lưu file vào thư mục
        Files.copy(file.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return targetFile.getAbsolutePath(); // Trả về đường dẫn tuyệt đối của file
    }
}
