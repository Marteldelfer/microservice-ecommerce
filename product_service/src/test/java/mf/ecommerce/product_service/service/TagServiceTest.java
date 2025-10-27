package mf.ecommerce.product_service.service;

import mf.ecommerce.product_service.dto.TagRequestDto;
import mf.ecommerce.product_service.dto.TagResponseDto;
import mf.ecommerce.product_service.exception.TagNotFoundException;
import mf.ecommerce.product_service.exception.TagWithNameAlreadyExistsException;
import mf.ecommerce.product_service.mapper.TagMapper;
import mf.ecommerce.product_service.model.Product;
import mf.ecommerce.product_service.model.Tag;
import mf.ecommerce.product_service.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create tag successfully")
    void createTagSuccessfully() {
        when(tagRepository.existsByName("Tag")).thenReturn(false);

        TagRequestDto dto = new TagRequestDto("Tag", "Description");
        Tag toSave = TagMapper.toEntity(dto);
        Tag saved = TagMapper.toEntity(dto);
        saved.setId(UUID.randomUUID());

        when(tagRepository.save(toSave)).thenReturn(saved);

        TagResponseDto tag = tagService.createTag(dto);
        assertThat(tag.getId()).isNotNull();
        assertThat(tag.getName()).isEqualTo("Tag");
        assertThat(tag.getDescription()).isEqualTo("Description");
    }

    @Test
    @DisplayName("Should throw TagWithNameAlreadyExistsException")
    void createTagThrowsTagWithNameAlreadyExistsException() {
        when(tagRepository.existsByName("Tag")).thenReturn(true);

        TagRequestDto dto = new TagRequestDto("Tag", "Description");
        Tag toSave = TagMapper.toEntity(dto);
        Tag saved = TagMapper.toEntity(dto);
        saved.setId(UUID.randomUUID());

        when(tagRepository.save(toSave)).thenReturn(saved);

        assertThatThrownBy(() -> tagService.createTag(dto)).isInstanceOf(TagWithNameAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Should update tag successfully")
    void updateTagSuccessfully() {
        UUID id = UUID.fromString("58834e3c-12eb-48b5-925c-c25f3079a466");
        Tag toUpdate = Tag.builder()
                .id(id)
                .name("Tag")
                .description("Description")
                .build();
        when(tagRepository.findById(id)).thenReturn(Optional.of(toUpdate));
        Tag updated = Tag.builder()
                .id(id)
                .name("Tag Updated")
                .description("Description Updated")
                .build();
        when(tagRepository.save(updated)).thenReturn(updated);

        TagResponseDto response = tagService.updateTag(id, new TagRequestDto("Tag Updated", "Description Updated"));
        assertThat(response.getId()).isEqualTo(updated.getId());
        assertThat(response.getName()).isEqualTo("Tag Updated");
        assertThat(response.getDescription()).isEqualTo("Description Updated");
    }

    @Test
    @DisplayName("Should throw TagNotFoundException")
    void updateTagThrowsTagNotFoundException() {
        UUID id = UUID.fromString("58834e3c-12eb-48b5-925c-c25f3079a466");
        TagRequestDto dto = new TagRequestDto("Tag Updated", "Description Updated");
        when(tagRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.updateTag(id, dto)).isInstanceOf(TagNotFoundException.class);
    }

    @Test
    void deleteTag() {
        tagService.deleteTag(UUID.randomUUID());
    }

    @Test
    @DisplayName("Should get tag successfully")
    void getTagSuccessfully() {
        UUID id = UUID.randomUUID();
        Tag tag = Tag.builder()
                .id(id)
                .name("Tag")
                .description("Description")
                .build();
        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        TagResponseDto response = tagService.getTag(id);
        assertThat(response.getId()).isEqualTo(tag.getId());
        assertThat(response.getName()).isEqualTo(tag.getName());
        assertThat(response.getDescription()).isEqualTo(tag.getDescription());
    }

    @Test
    @DisplayName("Should throw TagNotFoundException")
    void getTagThrowsTagNotFoundException() {
        UUID id = UUID.randomUUID();
        when(tagRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.getTag(id)).isInstanceOf(TagNotFoundException.class);
    }


    @Test
    @DisplayName("Should get all tags successfully")
    void getAllTags() {
        List<Tag> tags = Stream.generate(() ->
                Tag.builder()
                .id(UUID.randomUUID())
                .name("Tag")
                .description("Description")
                .build()
        ).limit(10).toList();
        List<TagResponseDto> expectedResponse = tags.stream().map(TagMapper::toDto).toList();
        when(tagRepository.findAll()).thenReturn(tags);
        List<TagResponseDto> response = tagService.getAllTags();

        for (int i = 0; i < 10; i++) {
            assertThat(response.get(i).getId()).isEqualTo(expectedResponse.get(i).getId());
            assertThat(response.get(i).getName()).isEqualTo(expectedResponse.get(i).getName());
            assertThat(response.get(i).getDescription()).isEqualTo(expectedResponse.get(i).getDescription());
        }
    }

    @Test
    @DisplayName("Should link product successfully")
    void linkProductSuccessfully() {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder()
                .id(productId)
                .name("Product")
                .description("Description")
                .build();
        UUID tagId = UUID.randomUUID();
        Tag tag = Tag.builder()
                .id(tagId)
                .name("Tag")
                .description("Description")
                .products(new HashSet<>())
                .build();
        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag savedTag = tagService.linkProduct(tagId, product);
        assertThat(savedTag.getId()).isEqualTo(tagId);
        assertThat(savedTag.getName()).isEqualTo(tag.getName());
        assertThat(savedTag.getDescription()).isEqualTo(tag.getDescription());
        assertThat(savedTag.getProducts().stream().filter(
                p -> p.getId().equals(productId)
        ).toList().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw TagNotFoundException")
    void linkProductThrowsTagNotFoundException() {
        Product product = new Product();
        UUID id = UUID.randomUUID();
        when(tagRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.linkProduct(id, product)).isInstanceOf(TagNotFoundException.class);
    }

    @Test
    void unlinkProductSuccessfully() {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder().id(productId).build();
        UUID tagId = UUID.randomUUID();
        Tag tag = Tag.builder()
                .id(tagId)
                .name("Tag")
                .description("Description")
                .products(new HashSet<>(Set.of(product)))
                .build();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        when(tagRepository.save(tag)).thenReturn(tag);

        Tag savedTag = tagService.unlinkProduct(tagId, product);

        assertThat(savedTag.getId()).isEqualTo(tagId);
        assertThat(savedTag.getName()).isEqualTo(tag.getName());
        assertThat(savedTag.getDescription()).isEqualTo(tag.getDescription());
        assertThat(savedTag.getProducts().stream().filter(
                p -> p.getId().equals(productId
        )).toList().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should throw TagNotFoundException")
    void unlinkProductThrowsTagNotFoundException() {
        UUID tagId = UUID.randomUUID();
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tagService.unlinkProduct(tagId, new Product())).isInstanceOf(TagNotFoundException.class);
    }
}