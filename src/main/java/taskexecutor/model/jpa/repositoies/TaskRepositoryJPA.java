package taskexecutor.model.jpa.repositoies;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import taskexecutor.model.jpa.domain.TaskEntityJPA;

public interface TaskRepositoryJPA extends CrudRepository<TaskEntityJPA, Long>{
	Page<TaskEntityJPA> findAll(Pageable pageable);
	List<TaskEntityJPA> findByName(String name);
}
