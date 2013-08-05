package taskexecutor.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import taskexecutor.ComputeTask;
import taskexecutor.TaskPool;
import taskexecutor.model.dao.ITaskManager;
import taskexecutor.model.dao.TaskManager;
import taskexecutor.model.dto.TaskDTO;
import taskexecutor.model.mongo.domain.TaskEntity;
import taskexecutor.model.mongo.repositories.TaskRepository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class TaskExecutionController {
	
	private TaskExecutor taskExecutor;
	private TaskPool taskPool;
	private ITaskManager taskManager;
	@Autowired
	private TaskRepository taskRepo;
	
	
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public void setTaskPool(TaskPool taskPool) {
		this.taskPool = taskPool;
	}
	
	@Required
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/tasks/get_task_list.form")
	public String getTaskList(@RequestParam("page") int page) {
		List<TaskDTO> tasks = taskManager.getTasks(10, page);
		JsonObject json = new JsonObject();
		Gson gson = new Gson();
		json.add("tasks", gson.toJsonTree(tasks));
		json.addProperty("pageCount", taskManager.getNumRows()/10);
		json.addProperty("pageNum", page);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks/get_task_list_async.form")
	public Callable<String> getTaskListAsync(@RequestParam("page") final int page) {
		return new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				// How to monitor if there are changes in task table??
				//List<TaskDTO> tasks = taskManager.getTasks(10, page);
				return getTaskList(page);
			}
		};
		
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks/new_task.form")
	public String newTask(@RequestParam("name") String name, @RequestParam("length") Integer length) {
		
		int id = taskManager.createTask(name, length);	// save to MySQL using JDBC
		
		TaskEntity taskEntity = new TaskEntity();		// save to MongoDB using Data
		taskEntity.setName(name);
		taskEntity.setStatus("RUNNING");
		taskEntity.setFinishDate(null);
		taskEntity.setLength(length);
		taskRepo.save(taskEntity);
		
		//taskExecutor.execute(new ComputeTask(taskManager, id, length));
		FutureTask<Integer> task = new FutureTask<Integer>(new ComputeTask(taskManager, id, length, taskEntity, taskRepo), null);
		taskPool.add(id, task);
		taskExecutor.execute(task);
		//taskPool.get(id).cancel(true);
		//task.cancel(true);
		
		JsonObject json = new JsonObject();
		json.addProperty("newId", id);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks/cancel_task.form")
	public void cancelTask(@RequestParam("id") Integer id) {
		FutureTask<Integer> task = taskPool.get(id);
		if (task.cancel(true))
			taskManager.updateTask(id, 5);
	}
}
