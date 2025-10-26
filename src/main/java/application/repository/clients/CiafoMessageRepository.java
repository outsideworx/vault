package application.repository.clients;

import application.model.CallbackEntity;
import org.springframework.data.repository.CrudRepository;

public interface CiafoMessageRepository extends CrudRepository<CallbackEntity, Long> {
}