package taskexecutor.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import taskexecutor.model.dto.TaskDTO;

public class TaskManager extends JdbcDaoSupport implements ITaskManager {
	
	public int numRows;
	
	private boolean firstTime = true;
	
	private TaskDTO makeTaskDTO(ResultSet rs) throws SQLException {
		TaskDTO task = new TaskDTO(rs.getInt("task_id"),
				rs.getString("task_name"), 
				rs.getString("status_name"), 
				rs.getTime("task_time_start"), 
				rs.getTime("task_time_finish"), 
				rs.getInt("task_length"), 
				rs.getDate("task_time_start"), 
				rs.getDate("task_time_finish"));
		return task;
	}
	
	@Override
	public List<TaskDTO> getTasks(int count, int pageNum) {
		pageNum *= count;
		String query = "SELECT task_id, task_name, status_name, task_time_start, task_time_finish, task_length from (task join task_status on task_status_id=status_id) order by task_id desc limit ?,?";
		List<TaskDTO> tasks = getJdbcTemplate().query(query,
				new RowMapper<TaskDTO>() {

					@Override
					public TaskDTO mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return makeTaskDTO(rs);
					}

				}, pageNum, count);
		//RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		//numRows = countCallback.getRowCount();
		if (firstTime)
			numRows = getJdbcTemplate().queryForObject("SELECT count(*) FROM task", Integer.class);
		return tasks;
	}

	@Override
	public int createTask(final String name, final Integer length) {	// Should return an id of recently created task
		final String query = "INSERT INTO task (task_name, task_status_id, task_time_start, task_length) values (?, 1, now(), ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
				ps.setString(1, name);
				ps.setInt(2, length);
				return ps;
			}
		}, keyHolder);
		numRows++;
		return keyHolder.getKey().intValue();
	}

	@Override
	public void updateTask(int id, int statusId) {
		String query;
		if (statusId == 2)	// If setting status to FINISHED
			query = "UPDATE task SET task_status_id=?, task_time_finish=now() WHERE task_id=?";
		else
			query = "UPDATE task SET task_status_id=? WHERE task_id=?";
		getJdbcTemplate().update(query, statusId, id);
	}

	
	@Override
	public List<TaskDTO> getTasksByName(String name, int count, int pageNum) {
		pageNum *= count;
		String query = "SELECT task_id, task_name, status_name, task_time_start, task_time_finish, task_length from (task join task_status on task_status_id=status_id) where task_name=?% limit ?,?";
		List<TaskDTO> tasks = getJdbcTemplate().query(query, new RowMapper<TaskDTO>() {

			@Override
			public TaskDTO mapRow(ResultSet rs, int index) throws SQLException {
				return makeTaskDTO(rs);
			}

			
		}, name, pageNum, count);
		return tasks;
	}

	@Override
	public List<TaskDTO> getTasksByMap(Map<String, Object> paramMap, int count, int pageNum) {	// This one needs to be tested... for sure!))
		pageNum *= count;
		String query = "SELECT task_id, task_name, status_name, task_time_start, task_time_finish, task_length from (task join task_status on task_status_id=status_id) where ";
		int i = 0;
		for (String s: paramMap.keySet()) {
			if (i>0)
				query+=" and ";
			query = query + s + "=:" + s;
			i++;
		}
		query+="limit :count, :pageNum";
		paramMap.put("count", count);
		paramMap.put("pageNum", pageNum);
		
		NamedParameterJdbcTemplate namedOne = new NamedParameterJdbcTemplate(getJdbcTemplate());
		List<TaskDTO> tasks = namedOne.query(query, paramMap, new RowMapper<TaskDTO>() {

			@Override
			public TaskDTO mapRow(ResultSet rs, int index) throws SQLException {
				return makeTaskDTO(rs);
			}
			
		});
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		numRows = countCallback.getRowCount();
		return tasks;
	}

	@Override
	public int getNumRows() {
		return numRows;
	}
}
