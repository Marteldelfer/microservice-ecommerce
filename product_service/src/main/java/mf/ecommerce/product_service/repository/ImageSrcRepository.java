package mf.ecommerce.product_service.repository;

import mf.ecommerce.product_service.model.ImageSrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageSrcRepository extends JpaRepository<ImageSrc, UUID> {
    Optional<ImageSrc> findBySlug(String slug);
}
