/*global $:false, alert:false */

$(document).ready(function() {
	
	"use strict";
	
	// init dropzone
	$(".dropzone-box").dropzone({ url: "ajax/file.php" });
	
	// init file tree
	$(document).ready( function() {
		$('#file_tree_box').fileTree({ 
			root: '/xampp/htdocs/www/theme.timex/',
			script: 'ajax/jquery.file.tree.php'
		}, function(file) {
			alert(file);
		});
	});
	
});