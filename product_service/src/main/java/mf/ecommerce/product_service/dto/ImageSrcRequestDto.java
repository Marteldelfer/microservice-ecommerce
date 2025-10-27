package mf.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImageSrcRequestDto {

    @Size(max = 255, message = "Alt text to long")
    private final String altText;
    @NotNull(message = "OrderIndex is required")
    private final int orderIndex;
    @NotNull(message = "Image is required")
    private final MultipartFile image;
}
