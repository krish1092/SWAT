/*function validEmail( email ){
	return true;
}


$('#emailID-signup').blur(function(){
	if(validEmail($(this).val()))
		$.ajax({
			url: 'emailValidation',
			type: 'POST',
			contentType : 'application/json; charset=utf-8',
		    dataType : 'json',
			data : JSON.stringify({'emailAddress' : $(this).val() document.getElementById('emailID-signup').value}),
			success: function(data) {
				alert("success");
			}
		})
});


*/



//Validations

//Regex: http://stackoverflow.com/questions/14850553/javascript-regex-for-password-containing-at-least-8-characters-1-number-1-uppe

$('#password-signup').on("blur",function(){
	var regex = /^(?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,}$/;
	
	if(!new RegExp(regex).test($(this).val()))
	{
		document.getElementById('signup-password-error').innerHTML = 'The password is invalid!';
		
	}
	else
	{
		document.getElementById('signup-password-error').innerHTML = '';
	}
	
});

$('#emailID-signup').on("blur",function(){
	
	var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
	if(!new RegExp(regex).test($(this).val()))
	{
		document.getElementById('signup-email-error').innerHTML = 'The email ID is invalid!';
	}
	else
	{
		document.getElementById('signup-email-error').innerHTML = '';
	}
});


// Login using Ajax
	 
function login() {
	var str = $( '#loginForm' ).serialize();
	$.ajax({
		url: 'login',
		type: 'POST',
		data: str,
		success: function(response) {
			$('#loginModal').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
			$('#navigationbar').html(response);
			
		},
		error: function(data){
			alert('The login could not be processed. Please try again!');
		}
	})
}

function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
	  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	  console.log('Name: ' + profile.getName());
	  console.log('Image URL: ' + profile.getImageUrl());
	  console.log('Email: ' + profile.getEmail());
	}

function forgotPassword(){
	
	var str = $( '#forgotPasswordForm' ).serialize();
	
	$.ajax({
		url: 'forgotpassword',
		type: 'POST',
		data: str,
		success: function(response) {
			$('#info').html(response);
			
		},
		error: function(data){
			alert('The forgot password request could not be processed. Please try again!');
		}
	})
	
	
}


function changePassword(){
	
var str = $( '#changePasswordForm' ).serialize();
	
	$.ajax({
		url: 'changePassword',
		type: 'POST',
		data: str,
		success: function(response) {
			$('#info').html(response);
			
		},
		error: function(data){
			alert('The change password request could not be processed. Please try again!');
		}
	})
	
	
}


