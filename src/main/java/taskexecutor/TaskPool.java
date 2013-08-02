package taskexecutor;

import java.util.HashMap;
import java.util.concurrent.FutureTask;

public class TaskPool {
	HashMap<Integer, FutureTask<Integer>> taskMap = new HashMap<Integer, FutureTask<Integer>>();
	
	public void add(Integer id, FutureTask<Integer> task) {
		taskMap.put(id, task);
	}
	
	public FutureTask<Integer> get(Integer id) {
		return taskMap.get(id);
	}
}
