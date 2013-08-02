
//////////////////////////////////

// Frontend controller(?)

//////////////////////////////////

function TaskManager(configObj) {
	this.pageNum = 0;										// current page number
	this.getTasksURL = configObj.getTasksURL;				// URL of getTasks controller
	this.newTaskURL = configObj.newTaskURL;					// URL of newTask controller
	this.cancelTaskURL = configObj.cancelTaskURL; 
	this.tbodyId = configObj.tbodyId;						// Id of tbody to insert rows
	this.newTaskNameId = configObj.newTaskNameId;			// Id of field of new task name
	this.newTaskLengthId = configObj.newTaskLengthId;		// Id of field of new task length
	this.newTaskButtonId = configObj.newTaskButtonId;		// Id of button to create new task
	this.refreshButtonId = configObj.refreshButtonId;		// Id of button to refresh
	this.paginationDivClass = configObj.paginationDivClass;	// Class name of div that contains pages
	this.pageCount;											// overall number of pages
	
	var self = this;
	$('#'+this.newTaskButtonId).on('click', function() {
		//console.log("ololo");
		self.newTask(self.newTaskURL);
	});
	
	$('#'+this.refreshButtonId).on('click', function() {
		self.getTasks(self.pageNum);
	});
	
	this.setFieldHandlers(this.newTaskNameId);
	this.setFieldHandlers(this.newTaskLengthId);
}

TaskManager.prototype.getTasks = function(pageNum) {		// Sends the request to get tasks on the page
	var self = this;
	console.log(self);
	$.post(self.getTasksURL, {page: pageNum}, function(data) {
		//console.log(self);
	//	console.log(this);
	//	console.log(data);
		var data = JSON.parse(data);
		self.pageNum = data.pageNum;
		self.showTasks(data);
		self.showPages(data);
	});
};

TaskManager.prototype.showTasks = function(data) {			// Prints retrieved task list into the table
	console.log(this);
	var tbody = $('#'+this.tbodyId);
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
				if (tasks[i].status == "CANCELED")
					row = $("<tr class='error'>");
				else
					if (tasks[i].status == "WAITING")
						row = $("<tr class='warning'>");
		
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
			row.append("<td><button data-id='"+tasks[i].id+"' class='btn' type='button'>Cancel</button></td>");
		else
			row.append("<td>");
		//console.log(row);
		tbody.append(row);
	}
	
	var self = this;
	tbody.on('click', 'button', function() {
		//var element = this;
		self.cancelTask(this);
	});
};

TaskManager.prototype.newTask = function(URL) {				// Sends request to create new task
	var self = this;
	var task = {name: $('#'+this.newTaskNameId).val(), length: parseInt($('#'+this.newTaskLengthId).val())};
	if (isNaN(task.length)) {
		alert("Not a number for length!");
		return;
	}
	$.post(URL, task, function(data) {
		console.log(data);
		alert(data);
	});
	self.getTasks(self.pageNum);
};

TaskManager.prototype.showPages = function(data) {			// Prints page numbers into the div, the most freaky handler >_<
	///////
	//this.pageNum = 3; // test only
	// /////
	this.pageCount = data.pageCount;
	var ul = $('.' + this.paginationDivClass + ' ul');
	ul.children().remove();
	ul.append("<li data-page='prev'><a>Prev</a></li>");
	if (data.pageCount > 4) { // 0-based
		if (this.pageNum <= 3) { // because 0-based
			// show first 3
			for ( var i = 0; i < this.pageNum; i++) {
				ul.append("<li><a>" + (i + 1) + "</a></li>");
			}
		} else {
			ul.append("<li data-page='0'><a>1</a></li>");
			// show 1
			// show ...
			ul.append("<li class='disabled'><a>...</a></li>");
			// show pageNum-1, pageNum
			// show pageNum+1
			ul.append("<li data-page='"+(this.pageNum - 2)+"'><a>" + (this.pageNum - 1) + "</a></li>");
			ul.append("<li data-page='"+(this.pageNum - 1)+"'><a>" + (this.pageNum) + "</a></li>");
		}
		ul.append("<li class='active'><a>" + (this.pageNum + 1) + "</a></li>"); // current
																	// page
		
		if (data.pageCount - this.pageNum <= 3) {
			// show pageNum+2,+3, pageCount+1 - no! show the last
			
			for (var i=this.pageNum+2; i<data.pageCount+1; i++)
				ul.append("<li data-page='"+(i - 1)+"'><a>" + (i) + "</a></li>");
			
			ul.append("<li data-page='"+(data.pageCount)+"'><a>" + (data.pageCount+1) + "</a></li>");
		} else {
			ul.append("<li data-page='"+(this.pageNum + 1)+"'><a>" + (this.pageNum + 2) + "</a></li>");
			ul.append("<li data-page='"+(this.pageNum + 2)+"'><a>" + (this.pageNum + 3) + "</a></li>");
			// show ...and last
			ul.append("<li class='disabled'><a>...</a></li>");
			ul.append("<li data-page='"+(data.pageCount)+"'><a>" + (data.pageCount+1) + "</a></li>");
		}
	} else {
		// just show all the pages
		for ( var i = 0; i < data.pageCount+1; i++) {
			if (i == this.pageNum)
				ul.append("<li class='active'><a>" + (i + 1) + "</a></li>");
			else
				ul.append("<li data-page='"+i+"'><a>" + (i + 1) + "</a></li>");
		}
	}
	ul.append("<li data-page='next'><a>Next</a></li>");
	this.setPageHandler();
};

TaskManager.prototype.setPageHandler = function() {				// Sets the click handler for all the page numbers 
	var self = this;
	$('.'+this.paginationDivClass).on('click', 'li', function() {
		var element = this;
		//console.log(self);
		self.pageHandler(element);
		//console.log(this);
	});
};

TaskManager.prototype.cancelTask = function(element) {
	var self = this;
	$.post(this.cancelTaskURL, {id: $(element).data('id')}, function (data) {
		self.getTasks(self.pageNum);
	});
};

TaskManager.prototype.pageHandler = function(element) {			// Sends the request to get tasks from the page chosen. The new page number 
	$element = $(element);										// is retrieved through ajax in getTasks() method		
	if ($element.hasClass('disabled') || $element.hasClass('active'))
		return;
	else {
		if ($element.data('page') == 'prev') {
			if (this.pageNum>0) {
				//this.pageNum--;
				this.getTasks(this.pageNum-1);
			}
			return;
		}
		else {
			if ($element.data('page') == 'next') {
				if (this.pageNum<this.pageCount) {
					//this.pageNum++;
					this.getTasks(this.pageNum+1);
				}
				return;
			}
		}
		//this.pageNum = parseInt($element.data("page"));
		this.getTasks(parseInt($element.data("page")));
	}
};

TaskManager.prototype.setFieldHandlers = function(fieldId) {		// Sets decorative handlers for fields to clear their default values
	var $field = $('#'+fieldId);
	$field.val($field.data('init'));
	$field.on('click', function() {
		$this = $(this);
		if (!$this.data('changed')) {
			$this.val('');
			$this.data('changed', 1);
		}
	}).on('blur', function() {
		$this = $(this);
		if ($this.val() == '') {
			$this.val($(this).data('init'));
			$this.data('changed', 0);
		}
	});
}

var config = {														// TaskManager is configured through this object
		getTasksURL:"http://localhost:8080/tasks/get_task_list.form",
		tbodyId:"tbody",
		newTaskNameId:"new_task_name", 
		newTaskLengthId:"new_task_length", 
		newTaskButtonId:"new_task", 
		refreshButtonId:"refresh",
		newTaskURL:"http://localhost:8080/tasks/new_task.form",
		paginationDivClass:"pagination",
		cancelTaskURL:"/tasks/cancel_task.form"
};

$(function() {
	var taskManager = new TaskManager(config);	
	taskManager.getTasks(0);
	setInterval(function() {		// Polling every second
		taskManager.getTasks(taskManager.pageNum);
	}, 1000);
})