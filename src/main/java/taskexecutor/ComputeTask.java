package taskexecutor;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import taskexecutor.model.dao.ITaskManager;
import taskexecutor.model.mongo.domain.TaskEntity;
import taskexecutor.model.mongo.repositories.TaskRepository;

public class ComputeTask implements Runnable {
	
	private ITaskManager taskManager;
	private PiCalculator calc;
	private int taskId;
	private int taskLength;
	private String taskName;
	private TaskEntity task;
	
	private TaskRepository taskRepo;
	
	public ComputeTask(ITaskManager taskManager, int taskId, int taskLength, TaskEntity task, TaskRepository taskRepo) {
		this.taskManager = taskManager;
		calc = new PiCalculator();
		this.taskId = taskId;
		this.taskLength = taskLength;
		this.task = task;
		taskName = task.getName();
		this.taskRepo = taskRepo;
	}
	
	@Override
	public void run() {
		for (int i=0; i<taskLength; i++) {
			calc.calculatePi();
		}
		taskManager.updateTask(taskId, 2); // finished
		
		task.setStatus("FINISHED");
		task.setFinishDate(new Date());
		taskRepo.save(task);
	}

}
