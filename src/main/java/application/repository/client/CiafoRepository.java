package application.repository.client;

import application.entity.client.CiafoItem;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CiafoRepository {
    public List<CiafoItem> getItemsForCategory(String category) {
        CiafoItem item = new CiafoItem();
        item.setDescription("asdsda");
        item.setDelete(false);
        return List.of(item);
    }

    public void saveItemsForcategory(String category, List<CiafoItem> items) {

    }
}
