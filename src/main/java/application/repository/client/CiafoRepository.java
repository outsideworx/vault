package application.repository.client;

import application.entity.client.CiafoItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CiafoRepository extends CrudRepository<CiafoItem, Long> {
    List<CiafoItem> getByCategory(String category);
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
                image4 = COALESCE(:#{#item.image4}, image4)
                WHERE id = :#{#item.id}
        """, nativeQuery = true)
    void update(@Param("item") CiafoItem item);
}