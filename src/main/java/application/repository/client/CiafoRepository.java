package application.repository.client;

import application.entity.client.CiafoItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CiafoRepository {
    // TODO: Dummy!
    public List<CiafoItem> getItemsForCategory(String category) {
        CiafoItem item = new CiafoItem();
        item.setId(123L);
        item.setDescription("asdsda");
        item.setDelete(false);
        return List.of(item);
    }
}