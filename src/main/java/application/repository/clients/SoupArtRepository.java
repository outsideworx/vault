package application.repository.clients;

import application.entity.clients.SoupArtItem;
import application.entity.clients.mapping.SoupArtFirstImage;
import application.entity.clients.mapping.SoupArtImages;
import application.entity.clients.mapping.SoupArtThumbnails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SoupArtRepository extends CrudRepository<SoupArtItem, Long> {
    @Cacheable(value = "soupArtItems", key = "#category")
    @Query(value = """
            SELECT id, category, description, thumbnail1, thumbnail2, thumbnail3, thumbnail4
                    FROM SOUPART
                    WHERE category = :category
            """, nativeQuery = true)
    List<SoupArtThumbnails> getThumbnailsByCategory(String category);

    @Cacheable(value = "soupArtItems", key = "#id")
    @Query(value = """
            SELECT id, category, description, image1, image2, image3, image4
                    FROM SOUPART
                    WHERE id = :id
            """, nativeQuery = true)
    SoupArtImages getImagesById(Long id);

    @Cacheable(value = "soupArtItems", key = "#category + #offset")
    @Query(value = """
            SELECT id, category, description, image1
                    FROM SOUPART
                    WHERE category = :category
                    ORDER BY id
                    LIMIT 6 OFFSET :offset
            """, nativeQuery = true)
    List<SoupArtFirstImage> getFirstImagesByCategoryAndOffset(String category, int offset);

    @Modifying
    @Query(value = """
            UPDATE SOUPART SET
                    category = :#{#item.category},
                    description = :#{#item.description},
                    image1 = COALESCE(:#{#item.image1}, image1),
                    image2 = COALESCE(:#{#item.image2}, image2),
                    image3 = COALESCE(:#{#item.image3}, image3),
                    image4 = COALESCE(:#{#item.image4}, image4),
                    thumbnail1 = COALESCE(:#{#item.thumbnail1}, thumbnail1),
                    thumbnail2 = COALESCE(:#{#item.thumbnail2}, thumbnail2),
                    thumbnail3 = COALESCE(:#{#item.thumbnail3}, thumbnail3),
                    thumbnail4 = COALESCE(:#{#item.thumbnail4}, thumbnail4)
                    WHERE id = :#{#item.id}
            """, nativeQuery = true)
    void update(SoupArtItem item);
}