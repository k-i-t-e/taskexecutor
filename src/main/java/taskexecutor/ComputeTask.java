package taskexecutor;

import taskexecutor.model.dao.ITaskManager;

public class ComputeTask implements Runnable {
	
	private ITaskManager taskManager;
	private PiCalculator calc;
	private int taskId;
	private int taskLength;
	
	public ComputeTask(ITaskManager taskManager, int taskId, int taskLength) {
		this.taskManager = taskManager;
		calc = new PiCalculator();
		this.taskId = taskId;
		this.taskLength = taskLength;
	}
	
	@Override
	public void run() {
		for (int i=0; i<taskLength; i++) {
			calc.calculatePi();
		}
		this.taskManager.updateTask(taskId, 2); // finished
	}

}
