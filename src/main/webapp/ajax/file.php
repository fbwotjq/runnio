<?php
	
	function d($what) {
    	print '<pre>';
    	print_r($what);
    	print '</pre>';
    }
	
	/* 	Dropzone does not provide the server side implementation of handling the files, 
	* 	but the way files are uploaded is identical to simple file upload forms like this:
	
	*	<form action="" method="post" enctype="multipart/form-data">
	*		<input type="file" name="file" />
	*	</form>
	*
	* 	Find more about dropzone: http://www.dropzonejs.com/
	*/

	d($_FILES);

?>