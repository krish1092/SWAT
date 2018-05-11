
function bugFormSubmit() {
	var str = $( '#bugCapture' ).serialize();
	$.ajax({
		url: 'bugSubmit',
		type: 'POST',
		data: str,
		success: function(data) {
			document.getElementById('bugModalBody').innerHTML = '<p>Your bug has been captured</p>';
		}
	})
}