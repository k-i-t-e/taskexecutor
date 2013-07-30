package taskexecutor.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import taskexecutor.model.dto.TaskDTO;

public interface ITaskManager {
	public List<TaskDTO> getTasks(int count, int pageNum);
	public int createTask(String name, Integer length);
	public void updateTask(int id, int statusId);
	public List<TaskDTO> getTasksByName(String name, int count, int pageNum);
	public List<TaskDTO> getTasksByMap(Map<String, Object> paramMap, int count, int pageNum);
}
