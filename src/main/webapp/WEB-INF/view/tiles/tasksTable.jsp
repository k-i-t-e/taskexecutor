<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
				<div class="span6">
					<form class="form-search">
						<input type="text" class="input-medium search-query" /> 
						<button type="submit" class="btn">Search</button>
					</form>
				</div>
				<div class="span6">
					<form class="form-search" style="float:right">
						<input data-changed="0" data-init="Enter new task name..." id="new_task_name" type="text" value="Enter new task name..."/> 
						<input data-changed="0" data-init="and length" id="new_task_length" style="width:70px" type="text" value="and length"/> 
						<button id="new_task" type="button" class="btn">New task</button>
						<button id="refresh" type="button" class="btn">Refresh</button>
						</form>
				</div>
	</div>
	<div class="row">
		<div class="span12">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<th>ID</th>
						<th>Task name</th>
						<th>Length</th>
						<th>Started</th>
						<th>Finished</th>
						<th>Status</th>
						<th></th>
					</tr>
				</thead>
				<tbody id="tbody">
					${tasksTable}
				</tbody>
			</table>
			<div class="pagination pagination-right pagination-small custom-pagination">
				<ul>
					<li><a href="#">Prev</a></li>
					<li><a href="#">1</a></li>
					<li class="disabled"><a href="#">...</a></li>
					<li><a href="#">21</a></li>
					<li><a href="#">22</a></li>
					<li class="active"><a href="#">23</a></li>
					<li><a href="#">24</a></li>
					<li><a href="#">25</a></li>
					<li class="disabled"><a href="#">...</a></li>
					<li><a href="#">42</a></li>
					<li><a href="#">Next</a></li>
				</ul>
			</div>
		</div>
	</div>