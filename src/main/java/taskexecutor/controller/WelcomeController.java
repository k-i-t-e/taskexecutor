package taskexecutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import taskexecutor.model.dao.ITaskManager;
import taskexecutor.model.dto.TaskDTO;

@Controller
public class WelcomeController {
	
	private ITaskManager taskManager;

	@Required
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	@RequestMapping(value="/")
	public String handleWelcomPage(Model model) {	// I'm sure there is more efficient way...
		
		List<TaskDTO> tasks = taskManager.getTasks(10, 0);
		StringBuilder resultHtml = new StringBuilder();
		for (TaskDTO task: tasks) {
			if (task.getStatus().equals("RUNNING")) 
				resultHtml.append("<tr class='info'>");
			else
				if (task.getStatus().equals("FINISHED"))
					resultHtml.append("<tr class='success'>");
				else
					if (task.getStatus().equals("CANCELED"))
						resultHtml.append("<tr class='error'>");
					else
						if (task.getStatus().equals("WAITING"))
							resultHtml.append("<tr class='warning'>");
			
			resultHtml.append("<td>").append(task.getId()).append("</td>");
			resultHtml.append("<td>").append(task.getName()).append("</td>");
			resultHtml.append("<td>").append(task.getLength()).append("</td>");
			resultHtml.append("<td>").append(task.getDateStart()).append("</td>");
			resultHtml.append("<td>").append(task.getDateFinish()).append("</td>");
			resultHtml.append("<td>").append(task.getStatus()).append("</td>");
			if (task.getStatus() == "RUNNING" || task.getStatus() == "WAITING" || task.getStatus() == "ERROR")
				resultHtml.append("<td><button data-id='"+task.getId()+"' class='btn' type='button'>Cancel</button></td>");
			else
				resultHtml.append("<td></td>");
			
			resultHtml.append("</tr>");
		}
		
		//model.addAttribute("tasksTable", resultHtml);
		model.addAttribute("tasks", tasks);
		//return "main";
		return "taskexecutor.index";
	}
}
