$('#change-tab').on('click',function(e){
	e.preventDefault();
	$('#about-tabs a[href="#classifications"]').tab('show');
});