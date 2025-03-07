package application.repository.client;

import application.entity.client.CiafoForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CiafoRepository {
    public List<CiafoForm> getItemsForCategory(String furniture) {
        return new ArrayList<>();
    }
}
