package application.repository;

import application.model.CallbackEntity;
import org.springframework.data.repository.CrudRepository;

public interface CallbackRepository extends CrudRepository<CallbackEntity, Long> {
}