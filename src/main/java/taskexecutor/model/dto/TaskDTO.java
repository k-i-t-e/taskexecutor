package taskexecutor.model.dto;

import java.sql.Time;
import java.sql.Date;
import java.text.DateFormat;

public class TaskDTO {
	private Integer id;
	private String status;
	private Time timeStart;
	private Time timeFinish;
	private Date dateStart;
	private Date dateFinish;
	private String name;
	private Integer length;
	//private Time time;
	public TaskDTO() {
	}
	public TaskDTO(Integer id, String  name, String status, Time timeStart, Time timeFinish, Integer length, Date dateStart, Date dateFinish) {
		setId(id);
		setStatus(status);
		setTimeFinish(timeFinish);
		setTimeStart(timeStart);
		setName(name);
		setLength(length);
		setDateStart(dateStart);
		setDateFinish(dateFinish);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeFinish() {
		return timeFinish;
	}
	public void setTimeFinish(Time timeFinish) {
		this.timeFinish = timeFinish;
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
}
