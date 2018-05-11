
$('#submit-classification').on('click',function(){
	
	$(this).prop('disabled',true);
	$.ajax({
		
		headers: {          
    		Accept : "application/json; charset=utf-8",         
    		"Content-Type": "application/json; charset=utf-8"   
  		}, 
		url: "analytics-classification",
		method:  "POST",
		data: JSON.stringify({
			classification : $('#classification').val(),
			region: $('#region').val(),
			dateTime: $('#dateTime').val()
		}),
		success: function(response){
			alert(response);
			console.log(response);
			var tr = '<tr>';
			tr = tr + '<td>';
			tr = tr + $('#classification').val();
			tr = tr + '</td>';
			tr = tr + '<td>';
			tr = tr + response.expertClassification.emailAddress;
			tr = tr + '</td>';
			tr = tr + '</tr>';
			$('#experts-table tbody').append(tr)


		},
		error: function(){
			alert('Something Went Wrong');
		}
	});
	$(this).prop('disabled',false);
});

