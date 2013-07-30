package taskexecutor.model.mongo.repositories;

import org.springframework.data.repository.CrudRepository;

import taskexecutor.model.mongo.domain.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, String> {
	TaskEntity findByName(String name);
	TaskEntity findByStatus(String status);
}
