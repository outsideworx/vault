package application.repository.client;

import application.entity.client.CiafoItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CiafoRepository extends CrudRepository<CiafoItem, Long> {
    List<CiafoItem> getByCategory(String category);
    void deleteByCategoryAndId(String category, Long id);
}