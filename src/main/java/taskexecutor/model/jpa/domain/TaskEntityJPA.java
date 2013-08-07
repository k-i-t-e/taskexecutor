package taskexecutor.model.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="task")
public class TaskEntityJPA {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="task_id")
	private Long id;
	
	@ManyToOne
	private
	TaskStatusEntityJPA status;
	
/*	@Column(name="task_status_id")
	private Integer statusId;*/
	
	@Column(name="task_time_start")
	private Date dateStart;
	
	@Column(name="task_time_finish")
	private Date dateFinish;
	
	@Column(name="task_name")
	private String name;
	
	@Column(name="task_length")
	private Integer length;
	
	public TaskEntityJPA() {
	}
	
	public TaskEntityJPA(Long id, String  name, TaskStatusEntityJPA status, Integer length, Date dateStart, Date dateFinish) {
		setId(id);
		setStatus(status);
		//setStatusId(statusId);
		setName(name);
		setLength(length);
		setDateStart(dateStart);
		setDateFinish(dateFinish);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}*/

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public TaskStatusEntityJPA getStatus() {
		return status;
	}

	public void setStatus(TaskStatusEntityJPA status) {
		this.status = status;
	}
}
