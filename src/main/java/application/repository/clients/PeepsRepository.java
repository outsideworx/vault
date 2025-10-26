package application.repository.clients;

import application.model.clients.peeps.PeepsEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PeepsRepository extends ListCrudRepository<PeepsEntity, Long> {
}