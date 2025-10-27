package mf.ecommerce.product_service.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mf.ecommerce.product_service.dto.TagRequestDto;
import mf.ecommerce.product_service.dto.TagResponseDto;
import mf.ecommerce.product_service.exception.TagNotFoundException;
import mf.ecommerce.product_service.exception.TagWithNameAlreadyExistsException;
import mf.ecommerce.product_service.mapper.TagMapper;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.model.Tag;
import mf.ecommerce.product_service.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagResponseDto createTag(TagRequestDto dto) {
        if (tagRepository.existsByName(dto.getName())) {
            throw new TagWithNameAlreadyExistsException("Tag with name " + dto.getName() + " already exists");
        }
        return TagMapper.toDto(tagRepository.save(TagMapper.toEntity(dto)));
    }

    public TagResponseDto updateTag(UUID id, TagRequestDto dto) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new TagNotFoundException("Tag with id " + id + " not found")
        );
        TagMapper.update(tag, dto);
        return TagMapper.toDto(tagRepository.save(tag));
    }

    public void deleteTag(UUID id) {
        tagRepository.deleteById(id);
    }

    public TagResponseDto getTag(UUID id) {
        return TagMapper.toDto(tagRepository.findById(id).orElseThrow(
                () -> new TagNotFoundException("Tag with id " + id + " not found")
        ));
    }

    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream().map(TagMapper::toDto).toList();
    }

    @Transactional
    public Tag linkProduct(UUID tagId, Product product) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new TagNotFoundException("Tag with id " + tagId + " not found")
        );
        log.info("linking product with id {} to product with id {}", tagId, product.getId());
        tag.getProducts().add(product);
        tag = tagRepository.save(tag);
        return tag;
    }

    public Tag unlinkProduct(UUID tagId, Product product) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(
                () -> new TagNotFoundException("Tag with id " + tagId + " not found")
        );
        tag.getProducts().remove(product);
        return tagRepository.save(tag);
    }
}
