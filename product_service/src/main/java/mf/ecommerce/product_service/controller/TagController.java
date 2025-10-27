package mf.ecommerce.product_service.controller;

import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.dto.TagRequestDto;
import mf.ecommerce.product_service.dto.TagResponseDto;
import mf.ecommerce.product_service.service.TagService;
import mf.ecommerce.product_service.validators.CreateValidationGroup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable UUID id) {
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(
            @Validated({CreateValidationGroup.class, Default.class}) @RequestBody TagRequestDto requestDto
    ) {
        TagResponseDto responseDto = tagService.createTag(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(
            @Validated({Default.class}) @RequestBody TagRequestDto requestDto,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(tagService.updateTag(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
