function TaskManager(backendURL, tbodyID, newTaskNameID, newTaskLengthId, newTaskButtonId, refreshButtonId) {
	this.pageNum = 0;
	this.backendURL = backendURL;
	this.newTaskURL = "http://localhost:8080/tasks/new_task.form";
	this.tbodyID = tbodyID;
	this.newTaskNameChanged = false;
	this.newTaskLengthChanged = false;
	this.newTaskNameId = newTaskNameID;
	this.newTaskLengthId = newTaskLengthId;
	this.newTaskButtonId = newTaskButtonId;
	this.refreshButtonId = refreshButtonId;
	this.paginationDivClass = "pagination";
	
	var self = this;
	$('#'+this.newTaskButtonId).on('click', function() {
		//console.log("ololo");
		self.newTask(self.newTaskURL);
	});
	
	$('#'+this.refreshButtonId).on('click', function() {
		self.getTasks(self, self.pageNum);
	})
}

TaskManager.prototype.getTasks = function(self, pageNum) {
	//var self = this;
	console.log(self);
	$.post(self.backendURL, {page: pageNum}, function(data) {
		console.log(self);
		console.log(this);
		console.log(data);
		var data = JSON.parse(data);
		self.showTasks(data);
		self.showPages(data);
	});
};

TaskManager.prototype.showTasks = function(data) {
	console.log(this);
	var tbody = $('#'+this.tbodyID);
	console.log(tbody);
	var tasks = data.tasks;
	//console.log(data.length);
	//console.log(data[0]);
	tbody.children().remove();
	for (var i=0; i<tasks.length; i++) {
		var row;
		if (tasks[i].status == "RUNNING")
			row = $("<tr class='info'>");
		else
			if (tasks[i].status == "FINISHED")
				row = $("<tr class='success'>");
			else
				if (tasks[i].status == "ERROR")
					row = $("<tr class='error'>");
				else
					if (tasks[i].status == "WAITING")
						row = $("<tr class='warning'>");
					else
						if (tasks[i].status == "CANCELED")
							row = $("<tr>");
		
		if (tasks[i].dateFinish == undefined)	// unfinished tasks has undefined in these fields
			tasks[i].dateFinish = '';
		if (tasks[i].timeFinish == undefined)
			tasks[i].timeFinish = '';
		
		row.append($("<td>").text(tasks[i].id));
		row.append($("<td>").text(tasks[i].name));
		row.append($("<td>").text(tasks[i].length));
		row.append($("<td>").text(tasks[i].dateStart+ ' ' +tasks[i].timeStart));
		row.append($("<td>").text(tasks[i].dateFinish+ ' ' +tasks[i].timeFinish));
		row.append($("<td>").text(tasks[i].status));
		if (tasks[i].status == "RUNNING" || tasks[i].status == "WAITING" || tasks[i].status == "ERROR")
			row.append("<td><button class='btn' type='button'>Cancel</button></td>");
		else
			row.append("<td>");
		//console.log(row);
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
		alert(data);
	});
	self.getTasks(self, self.pageNum);
};

TaskManager.prototype.showPages = function(data) {	// the most freaky handler >_<
	///////
	//this.pageNum = 3; // test only
	// /////

	var ul = $('.' + this.paginationDivClass + ' ul');
	ul.children().remove();
	ul.append("<li><a>Prev</a></li>");
	if (data.pageCount > 4) { // 0-based
		if (this.pageNum <= 3) { // because 0-based
			// show first 3
			for ( var i = 0; i < this.pageNum; i++) {
				ul.append("<li><a>" + (i + 1) + "</a></li>");
			}
		} else {
			ul.append("<li><a>1</a></li>");
			// show 1
			// show ...
			ul.append("<li class='disabled'><a>...</a></li>");
			// show pageNum-1, pageNum
			// show pageNum+1
			ul.append("<li><a>" + (this.pageNum - 1) + "</a></li>");
			ul.append("<li><a>" + (this.pageNum) + "</a></li>");
		}
		ul.append("<li class='active'><a>" + (this.pageNum + 1) + "</a></li>"); // current
																	// page
		
		if (data.pageCount - this.pageNum <= 3) {
			// show pageNum+2,+3, pageCount+1 - no! show the last
			
			for (var i=this.pageNum+2; i<data.pageCount+1; i++)
				ul.append("<li><a>" + (i) + "</a></li>");
			
			ul.append("<li><a>" + (data.pageCount+1) + "</a></li>");
		} else {
			ul.append("<li><a>" + (this.pageNum + 2) + "</a></li>");
			ul.append("<li><a>" + (this.pageNum + 3) + "</a></li>");
			// show ...and last
			ul.append("<li class='disabled'><a>...</a></li>");
			ul.append("<li><a>" + (data.pageCount+1) + "</a></li>");
		}
	} else {
		// just show all the pages
		for ( var i = 0; i < data.pageCount+1; i++) {
			if (i == this.pageNum)
				ul.append("<li class='active'><a>" + (i + 1) + "</a></li>");
			else
				ul.append("<li><a>" + (i + 1) + "</a></li>");
		}
	}
	ul.append("<li><a>Next</a></li>");
};

$(function() {
	var taskManager = new TaskManager("http://localhost:8080/tasks/get_task_list.form", "tbody", "new_task_name", "new_task_length", "new_task", "refresh");	
	setInterval(function() {
		taskManager.getTasks(taskManager, 0);
		
	}, 1000);
})