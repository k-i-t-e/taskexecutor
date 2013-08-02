//var newTaskNameChanged = false;
//var newTaskLengthChanged = false;


function setFieldHandlers(field) {
	var $field = $(field);
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

$(function() {
	var $name = $('#new_task_name');
	var $length = $('#new_task_length');
	$name.val($name.data('init'));
	$length.val($length.data('init'));
	/*$name.on('click', function() {
		if (!$(this).data('changed')) {
			$(this).val('');
			$(this).data('changed', 1);
		}
	}).on('blur', function() {
		if ($(this).val() == '') {
			$(this).val($(this).data('init'));
			$(this).data('changed', 0);
		}
	});
	
	$length.on('click', function() {
		if (!newTaskLengthChanged) {
			$(this).val('');
			newTaskLengthChanged = true;
		}
	}).on('blur', function() {
		if ($(this).val() == '') {
			$(this).val('and length');
			newTaskLengthChanged = false;
		}
	});*/
	
	setFieldHandlers('#new_task_name');
	setFieldHandlers('#new_task_length');
});