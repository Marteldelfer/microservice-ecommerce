package mf.ecommerce.product_service.service;

import lombok.AllArgsConstructor;
import mf.ecommerce.product_service.dto.TagRequestDto;
import mf.ecommerce.product_service.dto.TagResponseDto;
import mf.ecommerce.product_service.exception.TagNotFoundException;
import mf.ecommerce.product_service.mapper.TagMapper;
import mf.ecommerce.product_service.model.Tag;
import mf.ecommerce.product_service.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagResponseDto createTag(TagRequestDto dto) {
        return TagMapper.toDto(tagRepository.save(TagMapper.toTag(dto)));
    }

    public TagResponseDto updateTag(UUID id, TagRequestDto dto) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new TagNotFoundException("Tag with id " + id + " not found")
        );
        tag.setName(dto.getName());
        tag.setDescription(dto.getDescription());
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
}
