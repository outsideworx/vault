package application.entity.client;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CiafoItem {
    private Long id;
    private String description;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private MultipartFile image4;
    private boolean update;
    private boolean delete;
}
