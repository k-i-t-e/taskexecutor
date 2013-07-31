function TaskManager(backendURL, tbodyID, newTaskNameID, newTaskLengthId, newTaskButtonId, refreshButtonId) {
	this.backendURL = backendURL;
	this.newTaskURL = "http://localhost:8080/tasks/new_task.form";
	this.tbodyID = tbodyID;
	this.newTaskNameChanged = false;
	this.newTaskLengthChanged = false;
	this.newTaskNameId = newTaskNameID;
	this.newTaskLengthId = newTaskLengthId;
	this.newTaskButtonId = newTaskButtonId;
	this.refreshButtonId = refreshButtonId;
	
	var self = this;
	$('#'+this.newTaskButtonId).on('click', function() {
		console.log("ololo");
		self.newTask(self.newTaskURL);
	});
}

TaskManager.prototype.getTasks = function(self, pageNum) {
	//var self = this;
	console.log(self);
	$.post(self.backendURL, {page: pageNum}, function(data) {
		console.log(self);
		console.log(this);
		console.log(data);
		self.showTasks(data);
	});
};

TaskManager.prototype.showTasks = function(data) {
	var tbody = $('#'+this.tbodyID);
	console.log(tbody);
	data = JSON.parse(data);
	console.log(data.length);
	console.log(data[0]);
	tbody.children().remove();
	for (var i=0; i<data.length; i++) {
		var row;
		if (data[i].status == "RUNNING")
			row = $("<tr>");
		else
			if (data[i].status == "FINISHED")
				row = $("<tr class='success'>");
			else
				if (data[i].status == "ERROR")
					row = $("<tr class='error'>");
				else
					if (data[i].status == "WAITING")
						row = $("<tr class='warning'>");
		
		if (data[i].dateFinish == undefined)	// unfinished tasks has undefined in these fields
			data[i].dateFinish = '';
		if (data[i].timeFinish == undefined)
			data[i].timeFinish = '';
		
		row.append($("<td>").text(data[i].id));
		row.append($("<td>").text(data[i].name));
		row.append($("<td>").text(data[i].length));
		row.append($("<td>").text(data[i].dateStart+ ' ' +data[i].timeStart));
		row.append($("<td>").text(data[i].dateFinish+ ' ' +data[i].timeFinish));
		row.append($("<td>").text(data[i].status));
		if (data[i].status == "RUNNING" || data[i].status == "WAITING" || data[i].status == "ERROR")
			row.append("<td><button class='btn' type='button'>Button</button></td>");
		else
			row.append("<td>");
		console.log(row);
		tbody.append(row);
	}
};

TaskManager.prototype.newTask = function(URL) {
	var self = this;
	var task = {name: $('#'+this.newTaskNameId).val(), length: parseInt($('#'+this.newTaskLengthId).val())};
	if (isNaN(task.length)) {
		alert("Not a number");
		return;
	}
	$.post(URL, task, function(data) {
		console.log(data);
	});
	
}

$(function() {
	var taskManager = new TaskManager("http://localhost:8080/tasks/get_task_list.form", "tbody", "new_task_name", "new_task_length", "new_task", "refresh");	
	taskManager.getTasks(taskManager, 0);
})