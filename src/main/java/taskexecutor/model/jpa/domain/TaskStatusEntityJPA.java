package taskexecutor.model.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name="task_status")
public class TaskStatusEntityJPA {
	
	public TaskStatusEntityJPA() {
	}
	
	@Id
	@Column(name="status_id")
	private Integer statusId;
	
	@Column(name="status_name")
	private String statusName;
	
	
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
