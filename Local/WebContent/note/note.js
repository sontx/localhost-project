function checkNote(title, content) {
	return title.length > 0 && content.length > 0;
}

function createNote() {
	var date = new Date();
	var _created = "{0}:{1} - {2}/{3}/{4}".format(date.getHours(), date.getMinutes(), date.getDay(), date.getMonth() + 1, date.getFullYear());
	var box = bootbox.dialog({
		title : "New Note",
		message : 
			  '<div class="row"> '
			+ '<div class="col-md-12"> '
			+ '<form class="form-horizontal"> '
			+ '<div class="form-group"> '
			+ '<label class="col-md-2 control-label" for="title">Title</label> '
			+ '<div class="col-md-10"> '
			+ '<input name="req" type="hidden" value="add"> '
			+ '<input id="__title" name="title" type="text" placeholder="Note title" class="form-control input-md"> '
			+ '</div> '
			+ '</div> '
			+ '<div class="form-group"> '
			+ '<label class="col-md-2 control-label" for="content">Content</label> '
			+ '<div class="col-md-10"> '
			+ '<textarea id="__content" rows="10" name="content" placeholder="Note content" class="form-control input-md" style="resize:vertical;"></textarea> '
			+ '</div> '
			+ '</div> '
			+ '<div class="form-group"> '
			+ '<span class="col-md-12 help-block" style="text-align: right">Now: '
			+ _created + '</span>' + '</div> ' + '</form> '
			+ '</div> ' + '</div> ',
		buttons : {
			cancel : {
				label : "Cancel",
				className : "btn-default"
			},
			save : {
				label : "Add",
				className : "btn-success",
				callback : function() {
					var _title = $('#__title').val().trim();
					var _content = $('#__content').val().trim();
					console.log(checkNote(_title, _content));
					if (checkNote(_title, _content)) {
						$.ajax({
							type : "POST",
							url : "note",
							data : $('form.form-horizontal').serialize(),
							success : function(msg) {
								if (msg == "OK") {
									tooltip.show("Your new note has been saved!");
									location.reload();
								} else {
									tooltip.show('<span style="color: red"><b>Your new note can not be saved!</b></span>');
								}
							},
							error : function() {
								tooltip.show('<span style="color: red"><b>Connect problem!</b></span>');
							}
						});
					} else {
						bootbox.alert('<span style="color: red">Either title or content of this note can not empty!</span>');
						tooltip.show('<span style="color: yellow"><b>Your new note can not be saved!</b></span>');
					}
				}
			}
		}
	});
	box.on('shown.bs.modal', function() {
		$("#title").focus();
	});
}

function preprocess(s) {
	return s.replace(/<br>/g, "&#10;");
}

function showNote(element) {
	var _title = $(element).find('#td_title').html();
	var _content = $(element).find('#td_content').html();
	var _created = $(element).find('#td_created').html();
	var _id = $(element).find('#td_id').html();
	bootbox.dialog({
		message : '<pre>' + _content + '</pre><hr> <span class="help-block" style="text-align: right">' + _created + '</span>',
		title : '<input type="text" class="title-overflow" readonly="readonly" value="' + _title + '">',
		buttons : {
			del : {
				label : "Delete",
				className : "btn-danger",
				callback : function() {
					bootbox.confirm('Are you sure you want to delete "' + _title + '" ?',
						function(result) {
							if (result) {
								$.ajax({
									type : "POST",
									url : "note",
									data : 'req=del&id=' + _id,
									success : function(msg) {
										if (msg == "OK") {
											tooltip.show("You note has been deleted!");
											location.reload();
										} else {
											tooltip.show('<span style="color: red"><b>Your note can not be deleted!</b></span>');
										}
									},
									error : function() {
										tooltip.show('<span style="color: red"><b>Connect problem!</b></span>');
									}
								});
							}
					});
				}
			},
			edit : {
				label : "Edit...",
				className : "btn-success",
				callback : function() {
					editNote(_title, preprocess(_content), _created, _id);
				}
			},
			main : {
				label : "Close",
				className : "btn-primary"
			}
		}
	});
}

function disableSubmit() {
	return false;
}

function editNote(_title, _content, _created, _id) {
	var box = bootbox.dialog({
		title : "Note Editor",
		message : 
			'<div class="row"> '
			+ '<div class="col-md-12"> '
			+ '<form class="form-horizontal" onsubmit="return disableSubmit();"> '
			+ '<div class="form-group"> '
			+ '<label class="col-md-2 control-label" for="title">Title</label> '
			+ '<div class="col-md-10"> '
			+ '<input name="req" type="hidden" value="update"> '
			+ '<input name="id" type="hidden" value="' + _id
			+ '"> '
			+ '<input id="__title" name="title" type="text" placeholder="Note title" value="' + _title
			+ '" class="form-control input-md"> '
			+ '</div> '
			+ '</div> '
			+ '<div class="form-group"> '
			+ '<label class="col-md-2 control-label" for="content">Content</label> '
			+ '<div class="col-md-10"> '
			+ '<textarea id="__content" rows="10" name="content" placeholder="Note content" class="form-control input-md" style="resize:vertical;">' + _content
			+ '</textarea> '
			+ '</div> '
			+ '</div> '
			+ '<div class="form-group"> '
			+ '<span class="col-md-12 help-block" style="text-align: right">Created at ' + _created
			+ '</span>'
			+ '</div> '
			+ '</form> '
			+ '</div> ' 
			+ '</div> ',
		buttons : {
			cancel : {
				label : "Cancel",
				className : "btn-default"
			},
			save : {
				label : "Save",
				className : "btn-success",
				callback : function() {
					_title = $('#__title').val().trim();
					_content = $('#__content').val().trim();
					if (checkNote(_title, _content)) {
						$.ajax({
							type : "POST",
							url : "note",
							data : $('form.form-horizontal').serialize(),
							success : function(msg) {
								if (msg == "OK") {
									tooltip.show("Your note has been updated!");
									location.reload();
								} else {
									tooltip.show('<span style="color: red"><b>Your note can not be updated!</b></span>');
								}
							},
							error : function() {
								tooltip.show('<span style="color: red"><b>Connect problem!</b></span>');
							}
						});
					} else {
						bootbox.alert('<span style="color: red">Either title or content of this note can not empty!</span>');
						tooltip.show('<span style="color: yellow"><b>Your new note can not be updated!</b></span>');
					}
				}
			}
		}
	});
	box.on('shown.bs.modal', function() {
		$("#content").focus();
	});
}