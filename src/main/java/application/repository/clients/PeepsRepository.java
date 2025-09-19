package application.repository.clients;

import application.entity.clients.peeps.PeepsItem;
import org.springframework.data.repository.ListCrudRepository;

public interface PeepsRepository extends ListCrudRepository<PeepsItem, Long> {
}