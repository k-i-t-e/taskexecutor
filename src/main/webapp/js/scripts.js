var newTaskNameChanged = false;
var newTaskLengthChanged = false;

$(function() {
	$('#new_task_name').val('Enter new task name here...');
	$('#new_task_length').val('and length');
	$('#new_task_name').on('click', function() {
		if (!newTaskNameChanged) {
			$(this).val('');
			newTaskNameChanged = true;
		}
	}).on('blur', function() {
		if ($(this).val() == '') {
			$(this).val('Enter new task name here...');
			newTaskNameChanged = false;
		}
	});
	
	$('#new_task_length').on('click', function() {
		if (!newTaskLengthChanged) {
			$(this).val('');
			newTaskLengthChanged = true;
		}
	}).on('blur', function() {
		if ($(this).val() == '') {
			$(this).val('and length');
			newTaskLengthChanged = false;
		}
	});
});