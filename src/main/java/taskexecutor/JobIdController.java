package taskexecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import taskexecutor.model.dao.ITaskManager;
import taskexecutor.model.dao.TaskManager;
import taskexecutor.model.dto.TaskDTO;
import taskexecutor.model.mongo.domain.TaskEntity;
import taskexecutor.model.mongo.repositories.TaskRepository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class JobIdController {
	
	private TaskExecutor taskExecutor;
	private ITaskManager taskManager;
	@Autowired
	private TaskRepository taskRepo;
	
	
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Required
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	class MyResponse{
		private String text;
		private List tasks;
		 public MyResponse(String text, List tasks) {
			 this.text= text;
			 this.tasks = tasks;
		}
	}
	
	private class IdleTask implements Runnable {

	    private String message;
	    private ITaskManager taskManager;
	    public IdleTask(ITaskManager taskManager, String message) {
	      this.message = message;
	      this.taskManager = taskManager;
	    }

	    public void run() {
	    	System.out.println(message);
	    	taskManager.updateTask(4, 2);
	    }

	  }
	
	
	@ResponseBody
	@RequestMapping(value="/jobs/jobid.form", method=RequestMethod.POST)
	public String jobIdHandler(@RequestParam("jobId") int jobId) {
		List tasks = taskManager.getTasks(10, 0);
		MyResponse resp = new MyResponse("Got" + jobId, tasks);	
		Gson gson = new Gson();
		gson.toJson(resp);
		return gson.toJson(resp);
	}
	
	@ResponseBody
	@RequestMapping(value="/tasks/get_task_list.form")
	public String getTaskList(@RequestParam("page") int page) {
		List<TaskDTO> tasks = taskManager.getTasks(10, page);
		JsonObject json = new JsonObject();
		Gson gson = new Gson();
		json.add("tasks", gson.toJsonTree(tasks));
		json.addProperty("pageCount", taskManager.getNumRows());
		//taskExecutor.execute(new IdleTask(this.taskManager, "ololo"));
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
		int id = taskManager.createTask(name, length);
		taskExecutor.execute(new ComputeTask(taskManager, id, length));
		JsonObject json = new JsonObject();
		json.addProperty("newId", id);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/jobs/create.form")
	public String taskCreate(@RequestParam("jobId") String taskName) {
		TaskEntity task = new TaskEntity();
		task.setName(taskName);
		task.setStatus("RUNNING");
		task.setFinishDate(new Date());
		task.setLength(1);
		taskRepo.save(task);
		JsonObject json = new JsonObject();
		json.addProperty("text", taskName);	
		return json.toString();
	}

	
}
