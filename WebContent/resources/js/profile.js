

var startTimeMoment, endTimeMoment, region;
var imageURLArray = [];
var currentImageIndex = 0;
var imagePageContext = 'http://www2.mmm.ucar.edu/imagearchive1/RadarComposites/';
var minutesToAdd = moment.duration(30, 'minutes');
var playIntervalID;

function createImage(url){
	
	var image = new Image();
	image.id = 'radarimage';
	image.alt = 'Radar Image fetched from UCAR database';
	image.src = url;
	image.setAttribute('class','img-responsive');
	return image;
	
}


function getMinutes(dateTime){
	var minutes = dateTime.minutes();
	return minutes < 10 ? ("0" + minutes) : minutes;
}

function getHours(dateTime){
	var hour = dateTime.hour();
	return hour < 10 ? ("0" + hour) : hour;
}

function getDate(dateTime){
	var date = dateTime.date();
	return date < 10 ? ("0" + date) : date;
}

function getMonth(dateTime){
	var month = dateTime.month() + 1;
	return month < 10 ? ("0" + month) : month;
}

function getYear(dateTime){
	return dateTime.year();
}

function getImageSources(){
	
	var tempTime = startTimeMoment;
	var URL;
	while(!tempTime.isAfter(endTimeMoment))
	{
		URL = getURL(tempTime);
		imageURLArray.push(URL);
		tempTime = tempTime.add(minutesToAdd);
	}
}	
	
function getURL(dateTime){
	
	var URL = region 
			 + '/' 
			 + getYear(dateTime) + getMonth(dateTime) + getDate(dateTime) 
			 + '/' 
			 + region + '_' 
			 + getYear(dateTime) + getMonth(dateTime) + getDate(dateTime) + getHours(dateTime) + getMinutes(dateTime)
			 + '.gif';
 	
	return URL;
}	

function loadGIF(infoID, startTime, endTime, reg){

	startTimeMoment = new moment(startTime);
	endTimeMoment = new moment(endTime);
	region = reg;

	getImageSources();

	$('#image-modal').modal('show');

	$.ajax({

		headers: {          
			Accept : "application/json; charset=utf-8",         
			"Content-Type": "application/json; charset=utf-8"   
		}, 
		url: "profile/specific-observation",
		method:  "GET",
		data: {
			"infoID" : infoID,
		},
		success: function(response){
			console.log(response);
		},
		error: function(){
			alert('Something Went Wrong');
		}
	});



	playGIF();




}

function playGIF() {
	
	if(document.getElementById('radarimage') == null)
	{
		var radarImage = createImage(imagePageContext + imageURLArray[currentImageIndex]);
		document.getElementById('radar-image-display').appendChild(radarImage);
	}
	else
	{
		document.getElementById('radarimage').src = imagePageContext + imageURLArray[currentImageIndex];
	}
	
	if(currentImageIndex < imageURLArray.length - 1)
		currentImageIndex++;
	else
		currentImageIndex = 0;
	
	playIntervalID = setTimeout( playGIF , 2000);
	
	
}

$('#image-modal').on('hidden.bs.modal', function(e){
	imageURLArray = [];
	currentImageIndex = 0;
	clearTimeout(playIntervalID);
});