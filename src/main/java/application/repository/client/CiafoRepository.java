package application.repository.client;

import application.entity.client.CiafoImages;
import application.entity.client.CiafoItem;
import application.entity.client.CiafoThumbnails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CiafoRepository extends CrudRepository<CiafoItem, Long> {
    @Cacheable(value = "items", key = "#category")
    @Query(value = """
            SELECT id, category, description, thumbnail1, thumbnail2, thumbnail3, thumbnail4
                    FROM CIAFO
                    WHERE category = :category
            """, nativeQuery = true)
    List<CiafoThumbnails> getThumbnailsByCategory(String category);

    @Cacheable(value = "items", key = "#category + #offset")
    @Query(value = """
            SELECT id, category, description, image1, image2, image3, image4
                    FROM CIAFO
                    WHERE category = :category
                    ORDER BY id
                    LIMIT 6 OFFSET :offset
            """, nativeQuery = true)
    List<CiafoImages> getImagesByCategory(String category, int offset);

    void deleteByCategoryAndId(String category, Long id);

    @Modifying
    @Query(value = """
            UPDATE CIAFO SET
                    id = COALESCE(:#{#item.id}, id),
                    category = COALESCE(:#{#item.category}, category),
                    description = COALESCE(:#{#item.description}, description),
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
    void update(CiafoItem item);
}