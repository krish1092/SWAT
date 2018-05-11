$(function() {
    $( "#from" ).datepicker({
      defaultDate: "+1w",
      numberOfMonths: [3,4],
      stepMonths: 12,
      onClose: function( selectedDate ) {
        $( "#to" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    $( "#to" ).datepicker({
      defaultDate: "+1w",
      numberOfMonths: [3,4],
      stepMonths: 12,
      onClose: function( selectedDate ) {
        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
  });

var filters = [];

$('#fetchResult').on('click',function(){
	var filters = {};
	filters['from'] = $('#from').val();
	filters['to'] = $('#to').val();
	filters['state'] = $('#state').val();
	filters['month'] = $('#month').val();
	console.log(filters);
	
	
	
	var fil = $('#filter-form').serialize();
	
	$.ajax({
		url: 'filteredResult',
		type: 'POST',
		data: fil,
		success: function(response) {
			$('#result-table').html(response);
		},
		error: function(data){
			alert('Result could not be fetched at this time. Please try again!');
		}
	})
	
	
	
	
})