function JobIdFormHandler(sendButtonId, jobIdFieldId, formId, respDivId, url) {
	this.url = url;
	this.sendButtonId = sendButtonId;
	this.jobIdFieldId = jobIdFieldId;
	this.formId = formId;
	this.respDivId = respDivId;
	var self = this; 
	$('#'+this.sendButtonId).click(function() {
		//console.log(self);	- this is the object
		//console.log(this);	- this is clicked button
		//$('#'+self.jobIdFieldId).val('ololo');
		self.sendRequest(self);
	});
}

JobIdFormHandler.prototype.sendRequest = function(self) {
	console.log($('#'+self.formId).serializeArray())
	$.post(self.url, $('#'+self.formId).serializeArray(), function (data) {
		console.log(data.text);
		//console.log(self);
		self.handleResponse(data, self);
	} ,'json');
	//$('#'+self.jobIdFieldId).val('ololo');
}

JobIdFormHandler.prototype.handleResponse = function(data, self) {
	$('#'+self.respDivId).text('Server sys:'+data.text);
}