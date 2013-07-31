package taskexecutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import taskexecutor.model.dao.ITaskManager;
import taskexecutor.model.dto.TaskDTO;

//@Controller
public class WelcomeController {
	
	private ITaskManager taskManager;

	@Required
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	
	public String handleWelcomPage(Model model) {
		
		List<TaskDTO> tasks = taskManager.getTasks(10, 0);
		StringBuilder resultHtml = new StringBuilder();
		for (TaskDTO task: tasks) {
			if (task.getStatus() == "RUNNING") 
				resultHtml.append("<tr>");
			else
				if (task.getStatus() == "DONE")
					resultHtml.append("<tr class='success'>");
				else
					if (task.getStatus() == "ERROR")
						resultHtml.append("<tr class='error'>");
					else
						if (task.getStatus() == "WAITING")
							resultHtml.append("<tr class='warning'>");
			
			resultHtml.append("td").append(task.getId()).append("/td");
		}
		return null;
		
	}
}
