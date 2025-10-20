package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImageSrcRequest {

    @Size(max = 255, message = "Alt text to long")
    private final String altText;
    @NotNull
    private final int orderIndex;
    @NotNull
    private final MultipartFile image;
}
