package org.example.apiflutter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.apiflutter.entity.Images;

import java.sql.SQLException;
import java.util.Base64;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String base64Image;  // Mã hóa hình ảnh thành chuỗi Base64
    private String downloadUrl; // Chuyển hình ảnh thành chuỗi Base64 nếu cần

    // Tạo constructor từ entity Image
    public ImageResponse(Images image) {
        this.id = image.getId();
        this.fileName = image.getFileName();
        this.filePath = image.getFilePath();
        this.fileType = image.getFileType();
        this.downloadUrl = image.getDownloadUrl();

        // Mã hóa hình ảnh thành Base64 nếu hình ảnh không null
        if (image.getImage() != null) {
            try {
                this.base64Image = Base64.getEncoder().encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
