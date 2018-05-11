/*
 * Developed at Iowa State University
 */
//Stores the current date and time of the image; default value = selected date + '0000' i.e, 00:00 AM
var currentTime ;

//JSON structure for sending to server side once, user finishes classification.
var ajaxDetailsJSON={
		"startTime":"",
		"endTime":"",
		"region":"",
		"centreTime":"",
		
		"rectangle": {
			"rectTop" : "",
			"rectBottom" : "",
			"rectLeft" : "",
			"rectRight" : ""
		}
};

//Time and Classification JSONArray
var timeAndClass={};

//To track if the user has started classifying
var selectionStarted = false;

//Missed Classification - Time Array and its index;
var missedTimes = [];
var currentMissedIndex;


//getting the region from URL parameter
//credit : http://www.jquerybyexample.net/2012/06/get-url-parameters-using-jquery.html
function getRegion(regionName)
{
	var pageURL = window.location.search.substring(1);
	var URLVariables = pageURL.split('&');
	for (var i = 0; i < URLVariables.length; i++)
	{
		var param = URLVariables[i].split('=');
		if (param[0] == regionName)
		{
			//returning the value of the param
			return param[1];
		}

	}
}


//Some Utility functions
function getCurrentMinutes(time)
{
	var currentMinutes = time.minutes();
	currentMinutes = (currentMinutes < 10 ? '0':'') + currentMinutes;
	return currentMinutes;
}

function getCurrentHours(time)
{
	var currentHours = time.hour();
	currentHours = (currentHours < 10 ? '0':'') + currentHours;
	return currentHours;
}

function getCurrentDate(time)
{
	var currentDate = time.date();
	currentDate = (currentDate < 10 ? '0':'') + currentDate;
	return currentDate;
}

function getCurrentYear(time)
{
	var currentYear = time.year();
	return currentYear;
}

function getCurrentMonth(time)
{
	var currentMonth = time.month() + 1; // Moment JS defines month from 0. i.e, January = 0, Feb = 1....
	currentMonth = (currentMonth < 10 ? '0':'') + currentMonth;
	return currentMonth;
}

//End of utility functions

//Previous and Next Functionalities rely on change of time
function timeChange(action)
{
	//Minutes To add/subtract
	var minutes = moment.duration(30, 'minutes');

	if(action == 'subtract')
		currentTime.subtract(minutes);
	else
		currentTime.add(minutes);

	//Changing the value in the date field 
	//document.getElementById('selectedDate').value = moment.utc(currentTime).format('YYYY-MM-DD');

}

//create Image
function createImage(url){

	var radarImage = new Image();
	radarImage.id = 'radarimage';
	radarImage.alt = 'Radar Image fetched from UCAR database';
	radarImage.src = url;
	radarImage.setAttribute("data-step", "1");
	radarImage.setAttribute("data-intro", "A radar image from a randomly chosen date!");
	radarImage.setAttribute('class','img-responsive');
	document.getElementById('imagearea').appendChild(radarImage);

}




//Fetch the image for the date the user wishes. Default time is 12:00 AM UTC for any given day
function fetchImage(time)
{
	//getting the region
	var region = getRegion("region");

	//setting the region for sending to Server side;
	ajaxDetailsJSON.region = region;

	//Setting the date and time details for fetching the image.
	var year = getCurrentYear(time);

	var month = getCurrentMonth(time);

	var date = getCurrentDate(time);

	var hours = getCurrentHours(time);

	var minutes = getCurrentMinutes(time);


	//setting the url
	var url="http://www2.mmm.ucar.edu/imagearchive1/RadarComposites/"
		+region
		+"/"
		+year+month+date
		+"/"
		+region
		+"_"
		+year+month+date+hours+minutes
		+".gif";
	
	if(document.getElementById('radarimage') == null) 
	{
		createImage(url);
	}
	else
	{
		document.getElementById('radarimage').src = url;
	}
	document.getElementById('imagearea').style.display = 'block';
}


//Getting the rectangle Coordinates
function getRectCoordinates(rectElement)
{

	//Left Edge of the rectangle
	var rectLeft=parseInt(rectElement.style.left);

	//Subtracting the offset value for left edge
	rectLeft=rectLeft - document.getElementById('radarimage').offsetLeft;

	var rectWidth = parseInt(rectElement.style.width);

	var rectHeight = parseInt(rectElement.style.height);

	//Top Edge of the rectangle
	var rectTop = parseInt(rectElement.style.top);

	//Subtracting the offset value for top edge
	rectTop = rectTop - document.getElementById('radarimage').offsetTop;

	var rectRight = rectLeft + rectWidth;
	var rectBottom = rectTop + rectHeight;

	var rectCoordinates = {};

	//Wrapping all the rectangle coordinates in an object
	rectCoordinates['rectTop'] = rectTop;
	rectCoordinates['rectBottom'] = rectBottom;
	rectCoordinates['rectLeft'] = rectLeft;
	rectCoordinates['rectRight'] = rectRight;

	
	ajaxDetailsJSON.rectangle = rectCoordinates;
	
	return rectCoordinates;
}


//Finding a rough centre Image
function centreTime()
{
	var format = 'MM/DD/YYYY HHmm';
	
	var startTime = moment(ajaxDetailsJSON.startTime,format);
	var endTime = moment(ajaxDetailsJSON.endTime,format);
	
	var diffInMinutes = endTime.diff(startTime,'minutes');

	var timeDiffBetweenImages = 30; // Each image differs by 30 minutes.

	var centreIndex = Math.floor((1/2)*(diffInMinutes/timeDiffBetweenImages));
	
	var centreTimeMoment =  startTime.add(centreIndex*timeDiffBetweenImages,'minutes');
	
	ajaxDetailsJSON.centreTime = centreTimeMoment.format(format);
		
	return centreTimeMoment;

}

rectangleBox();
var rectangleCount=0;


function rectangleBox()
{

	var mousePosition = {    

			startX: 0,startY: 0,
			endX: 0,endY: 0,

	};

	var rectElement=null;


	//$('#classifyradarimage').on("click",function(){
	$('#imagearea').on('click touchstart','.finished',function(){
		if(rectangleCount==0)
		{
			if(rectElement==null)
			{
				var offset = $('#radarimage').offset();
				mousePosition.startX = event.pageX - offset.left/*-this.offsetLeft*/;
				mousePosition.startY = event.pageY - offset.top/*-this.offsetTop*/;
				
				console.log('----------------------');
				console.log('event.pageX:'+event.pageX);
			    console.log('this.offsetLeft:'+this.offsetLeft);
			    console.log('event.pageY:'+event.pageY);
			    console.log('this.offsetTop:'+this.offsetTop);
			    console.log('mousePosition.startX:'+mousePosition.startX);
			    console.log('mousePosition.startY:'+mousePosition.startY);
			    console.log('----------------------');

				//Div for all rectanglular boxes in this refTime
				if($('#rectangleboxes_'+currentTime).length == 0)
				{
					var rectangularBlock=document.createElement('div');
					rectangularBlock.id='rectangleboxes_'+currentTime;
					document.getElementById('imagearea').appendChild(rectangularBlock);

				}

				//Individual Box Div
				rectElement=document.createElement('div');
				rectElement.className='rectangle';


				rectElement.style.left=(mousePosition.startX+this.offsetLeft)+'px';
				rectElement.style.top=(mousePosition.startY+this.offsetTop)+'px';
				document.getElementById('rectangleboxes_'+currentTime).appendChild(rectElement);

			}
			else
			{
				var rectCoordinates = getRectCoordinates(rectElement);

				rectangleCount++;
				$("#rectangleboxes_"+currentTime).attr("data-rectangleCount",rectangleCount);
				
				rectElement=null;

				ajaxDetailsJSON.timeAndClass=timeAndClass;
				console.log(ajaxDetailsJSON);
				
				$('#submit').prop('disabled', false);

				document.getElementById('hiddenJSON').value=JSON.stringify(ajaxDetailsJSON);

			}
		}
		else
		{
			alert('You can draw only one rectangle');
		}
	}).on('mousemove touchmove',function(event){
		
		var offset = $('#radarimage').offset();
		
		/*mousePosition.endX = event.pageX-this.offsetLeft;
		mousePosition.endY = event.pageY-this.offsetTop;*/
		
		mousePosition.endX = event.pageX-offset.left;
		mousePosition.endY = event.pageY-offset.top;

		if(rectElement!==null)
		{
			rectElement.style.width = Math.abs(mousePosition.endX - mousePosition.startX) ;
			rectElement.style.height = Math.abs(mousePosition.endY - mousePosition.startY) ;

			
			console.log('------------------');
			console.log('event.pageX:' + event.pageX);
			console.log('event.pageY:' + event.pageY);
			console.log('mousePosition.startX:' + mousePosition.startX);
			console.log('mousePosition.startY:' + mousePosition.startY);
			console.log('offset.left:' + offset.left);
			console.log('offset.top:' + offset.top);
			console.log('((event.pageX - (mousePosition.startX+offset.left)):' + ((event.pageX - (mousePosition.startX+offset.left))));
			console.log('((event.pageY - (mousePosition.startY+offset.top)):' +((event.pageY - (mousePosition.startY+offset.top))));
			console.log('------------------');
			
		/*	rectElement.style.left = ((event.pageX - (mousePosition.startX+this.offsetLeft)) < 0) ? event.pageX : (mousePosition.startX+this.offsetLeft) ;
			rectElement.style.top = ((event.pageY - (mousePosition.startY+this.offsetTop)) < 0) ? event.pageY  : (mousePosition.startY+this.offsetTop) ;*/
			
			/* TEMPORARY FIX!!!!!*/
			
			rectElement.style.left = (mousePosition.endX - mousePosition.startX) >= 0 ? rectElement.style.left : event.pageX + 1 ;
			rectElement.style.top = (mousePosition.endY - mousePosition.startY) >= 0 ?  rectElement.style.top : event.pageY - this.offsetTop ;
			
			
			/*rectElement.style.left = ((event.pageX - (mousePosition.startX+offset.left)) < 0) ? event.pageX : (mousePosition.startX+offset.left) ;
			rectElement.style.top = ((event.pageY - (mousePosition.startY+offset.top)) < 0) ? event.pageY  : (mousePosition.startY+offset.top) ;
			*/
		}
	});

}

//Pure JQuery

//When the user clicks Erase Box button, clear the rectangle and reset rectangle count
$('#image-buttons').on("click", '#erase-box' ,function(){
	$( '#rectangleboxes_'+currentTime).remove();
	rectangleCount=0;
	$('#rectangleboxes_'+currentTime).attr("data-rectangleCount",rectangleCount);

});

//Fetching the image and initialising several parameters
$("#btnFetch").on("click", function () {

	//Initialising an ajax Details variable for passing to server side
	ajaxDetailsJSON = {
			"startTime":"",
			"endTime":"",
			"region":"",
			"centreTime":"",
			"rectangle": {
				"rectTop" : "",
				"rectBottom" : "",
				"rectLeft" : "",
				"rectRight" : ""
			}
	};

	//Emptying out the timeAndClass (if marked till now)
	timeAndClass={};

	//For future Implementation; Multiple rectangle boxes if count of classified images > 10
	rectangleCount=0;

	//$('#imagearea').css('display','none');

	//Select another random date
	randomDateSelector();


	fetchImage(currentTime);

	//$('#radarimage').css('display','block');
	$('#rectangleboxes_'+currentTime).remove();
	$('.rectangle').remove();
	$('#classificationarea').css('display','block');
	$('.finished').removeClass('finished');
	$('#btnNext').prop('disabled',false);
	$('#btnPrevious').prop('disabled',false);
	$('#slider-area').css('display','block');
	$('#user-classification > p').remove();
	$('.selected-class').removeClass('selected-class');


	//Illogical way to do stuff
	ui = {
			"value" : (currentTime.hours()*60) + currentTime.minutes()
	};
	$('#time-slider').trigger("slide");


});

$('.select').on("click",function () {
	
	
	selectionStarted = true;

	$('#btnFetch').css('display','none');
	$('.selected-class').removeClass('selected-class');
	$(this).closest('.caption').addClass('selected-class');

	var format = 'MM/DD/YYYY HHmm';

	if(ajaxDetailsJSON.startTime=="" || currentTime.isBefore(moment.utc(ajaxDetailsJSON.startTime,format)))
	{
		ajaxDetailsJSON.startTime=currentTime.format(format);
	}

	if(ajaxDetailsJSON.endTime=="" || currentTime.isAfter(moment.utc(ajaxDetailsJSON.endTime,format)))
	{
		ajaxDetailsJSON.endTime=currentTime.format(format);
	}


	var currentTimeFormatted = currentTime.format('MM/DD/YYYY HHmm');

	timeAndClass[currentTimeFormatted] = $(this).val();

});

function makeBorderForCurrentSelectedClass()
{
	var format = 'MM/DD/YYYY HHmm';
	var currentTimeFormatted = currentTime.format(format);

	//Check if the currentTime's value in timeAndClass is not NULL

	if(!(!timeAndClass[currentTimeFormatted]))
	{
		//Add selected class to make border
		$('button[value="'+timeAndClass[currentTimeFormatted]+'"]').closest('.caption').addClass('selected-class');
	}
}


$("#btnPrevious").on("click", function () {
	$( '#rectangleboxes_'+currentTime ).css('display','none');
	
	timeChange('subtract');
	fetchImage(currentTime);
	rectangleCount=0;
	if($('#rectangleboxes_'+currentTime).length !=0)
	{
		$( '#rectangleboxes_'+currentTime).css('display','block');
	}

	$('.selected-class').removeClass('selected-class');

	makeBorderForCurrentSelectedClass();
	
	var hours = (currentTime.hours() < 10 ? '0':'') + currentTime.hours();
	var minutes = (currentTime.minutes() < 10 ? '0':'') + currentTime.minutes();
	
	ui = {
			"value" : (currentTime.hours()*60) + currentTime.minutes()
		  };
	
	$('#time-slider').trigger("slide");
	
	
});

$("#btnNext").on("click", function () {

	$( '#rectangleboxes_'+currentTime).css('display','none');
	
	timeChange('add');
	fetchImage(currentTime);
	rectangleCount=0;
	if($('#rectangleboxes_'+currentTime).length !=0)
	{
		$( '#rectangleboxes_'+currentTime).css('display','block');
	}

	$('.selected-class').removeClass('selected-class');

	makeBorderForCurrentSelectedClass();
	var hours = (currentTime.hours() < 10 ? '0':'') + currentTime.hours();
	var minutes = (currentTime.minutes() < 10 ? '0':'') + currentTime.minutes();
	
	//Illogical way to do stuff
	ui = {
			"value" : (currentTime.hours()*60) + currentTime.minutes()
		  };
	$('#time-slider').trigger("slide");
	//$('#time-value').html(hours+":"+minutes);
	
	
	
});



$('#finish').on('click',function(){

	
	if(selectionStarted)
	{
		missedTimes = [];
		findMissed();
		
		if(missedTimes.length > 0)
		{
			$('.selected-class').removeClass('selected-class');
			if(missedTimes.length > 1)	
			{
				$('#finish-warning').html('Looks like you have not classified '+missedTimes.length+' images! Please classify the below image!');
				if(document.getElementById('next-missed') == null)
					$('#finish-warning-area').append('<input type="button" id="next-missed" value="Next Missed Image" class="btn btn-primary" />');
				$('#finish-warning').animate({
					scrollTop: $('#finish-warning')
				},1000);
			}
			else
			{
				$('#finish-warning').html('Oops! You forgot to classify one image! Please classify and click Finish My Classification');
				if(document.getElementById('next-missed') != null)
					$('#next-missed').css('display','none');
				
				$('#finish-warning').animate({
					scrollTop: $(this)
				},1000);
				
			}

			currentMissedIndex = 0;
			currentTime = moment.utc(missedTimes[currentMissedIndex], 'MM/DD/YYYY HHmm');
			fetchImage(currentTime);
		}
		else if(Object.keys(timeAndClass).length < 3)
		{
			$('#finish-warning').html('You should classify atleast three consecutive images');
			$('#finish-warning').animate({
				scrollTop: $(this)
			},1000);
		}
		else
		{
			fetchImage(centreTime());
			$('#radarimage').addClass('finished');
			$(this).css('display','none');
			$('#submit').css('display','block');
			$('#submit').prop('disabled', true);
			$('#btnNext').prop('disabled',true);
			$('#btnPrevious').prop('disabled',true);
			$('#slider-area').css('display','none');
			$('#classificationarea').css('display','none');
			
			var text = 'Draw a box around the area affected by your system at all times --'+
						'be sure to include a little extra space around where the storms traveled.'
						+'To draw the box, bring the cursor to where you want the northwest corner of the box to be,'
						+'and click on the left mouse button and then let go. '+
						' Then move the cursor to the southeast corner and click again.  Your box should appear.'
			
			$('#finish-warning').html( text );
			$('#finish-warning').scroll();
			if(document.getElementById('erase-box') == null)
				$('#image-buttons').append('<input type="button" id="erase-box" value="Erase Box"	class="btn btn-primary" />');
			
			displayUserClassification();
		}
	}
	else
	{
		$('#finish-warning').html('You have not classified any image! Please start your classification!');
	}
});


function findMissed ()
{
	var format = 'MM/DD/YYYY HHmm';
	var startTime = moment.utc(ajaxDetailsJSON.startTime, format);
	var endTime = moment.utc(ajaxDetailsJSON.endTime, format);
	var minutes = moment.duration(30, 'minutes');
	var formattedTime;
	while(! startTime.isAfter(endTime))
	{
		formattedTime = startTime.format('MM/DD/YYYY HHmm');
		if(!timeAndClass[formattedTime])
			missedTimes.push(formattedTime);
		
		startTime.add(minutes);
	}
	
}

$('#finish-warning-area').on('click' ,'#next-missed', function(){
	
	$('.selected-class').removeClass('selected-class');
	 if(timeAndClass[missedTimes[currentMissedIndex]])
	 {
		 missedTimes.splice(currentMissedIndex, 1);
		 currentMissedIndex = 0;
	 }
	 else
	 {
		 if(currentMissedIndex < missedTimes.length - 1)
			 currentMissedIndex++;
		 else
			 currentMissedIndex = 0;
	 }
	 
	 if(missedTimes.length == 1)
	 {
		 $('#finish-warning').html('You have just one more image pending! Please classify and click Finish My Classification button!');
			if(document.getElementById('next-missed') != null)
				$('#next-missed').css('display','none');
	 }
	 
	 if(missedTimes.length == 0)
	 {
		 $('#finish-warning').html('You have now classified consecutive images. Please click on "Finish My classification"');
		 $(this).remove();
	 }	
	 else
	 {
		 
		currentTime =  moment.utc(missedTimes[currentMissedIndex], 'MM/DD/YYYY HHmm');
		fetchImage(currentTime);
		ui = {
				"value" : (currentTime.hours()*60) + currentTime.minutes()
			  };
		$('#time-slider').trigger("slide");

	 }
	
});


$('#time-slider').slider({
	min: 0,
	max: 1410,
	step: 30,
	orientation: "horizontal",
	range: "min"
	
}).on('slide', function(e,ui){
	//ui.value = 0 to 1410
	if(ui!=null)
	{	
		var hours = Math.floor(ui.value/60);
		var minutes = ui.value - (hours * 60);
		currentTime.hours(hours);
		currentTime.minutes(minutes);
		fetchImage(currentTime);
	}
	else
	{
		var hours = currentTime.hours();
		var minutes = currentTime.minutes();
		var value = ((hours*60) + minutes)/30;
		$('.ui-slider-range').css('width',value*2.12766+'%');
		$('.ui-slider-handle').css('left',value*2.12766+'%');
		
	}
	hours = (hours < 10 ? '0':'') + hours;
	minutes = (minutes < 10 ? '0':'') + minutes;
	$('#time-value').html(hours+":"+minutes);
	$('.selected-class').removeClass('selected-class');
	makeBorderForCurrentSelectedClass();
	
});


$(function(){
	
	//Initialising an ajax Details variable for passing to server side
	ajaxDetailsJSON={
			"startTime":"",
			"endTime":"",
			"region":"",
			"centreTime":""
	};

	
	randomDateSelector();
	
	//For future Implementation; Multiple rectangle boxes if count of classified images > 10
	rectangleCount = 0;

	fetchImage(currentTime);

	$('#rectangleboxes_'+currentTime).remove();
	$('.rectangle').remove();
	$('#classificationarea').css('display','block');
	$('.finished').removeClass('finished');	
});


function randomDateSelector()
{
	var format = "YYYY-MM-DD HHmm";
	var minDate = moment.utc('2015-06-01',format);
	var maxDate = moment.utc('2015-06-30',format);
	var difference = maxDate.diff(minDate,'days');
	var toAdd = Math.floor(Math.random() * difference) + 1;
	
	//setting the current Time to a random date , 12:00 AM UTC
	currentTime = minDate.add(toAdd,'days');
}


function displayUserClassification(){
	
	var divElement = '<div>';
	
	var tableElement = '<table class="table table-condensed">';
	
	tableElement = tableElement + '<caption>Your Classification</caption>';
	tableElement = tableElement + '<thead>';
	tableElement = tableElement + '<tr>';
	tableElement = tableElement + '<th>Time (in UTC)</th>';
	tableElement = tableElement + '<th>Classification</th>';
	tableElement = tableElement + '</tr>';
	tableElement = tableElement + '</thead>';
	tableElement = tableElement + '<tbody>';
	
	$.each(timeAndClass,function(index, value){
		
		tableElement = tableElement + '<tr>';
		
		tableElement = tableElement + '<td>';
		tableElement = tableElement + index ;
		tableElement = tableElement + '</td>';
		
		tableElement = tableElement + '<td>';
		tableElement = tableElement + displayFullMorphologyNames(value);
		tableElement = tableElement + '</td>';
		
		tableElement = tableElement + '</tr>';
		
	});
	
	tableElement = tableElement + '</tbody>';
	tableElement = tableElement + '</table>';
	
	var divElement = divElement + tableElement +'</div>';
	$("#user-classification").append(divElement);
	$("#user-classification").css('display','block');
	
}


/*$('#selectedDate').datepicker({
	maxDate: new Date(2015,7,31), new Date(date.year(),date.month(),date.date()),
	minDate: new Date(2012,3,1),
	dateFormat: 'yy-mm-dd',
	autoSize: true,
	stepMonths: 12,
	numberOfMonths: [1,5],
	defaultDate: new Date(2015,3,1),
	beforeShowDay: function(date){
		if(date.getMonth() > 2 && date.getMonth() < 8)
			return [true, ""];
		else
			return [false,"","Date Unavailable"]
	}

}).on('change', function(){
	var d = moment.utc($(this).val());
	if(!(d.month() > 2 && d.month() < 8) )
		{
			$(this).val('');
			alert('You can\'t select this date');
		}
});*/


/*var date = moment.utc();
date = date.subtract(3,'months').date(0);*/
///Selected Month
/*$('#selectedDate').datepicker({
	maxDate: new Date(2015,5,30), new Date(date.year(),date.month(),date.date()),
	minDate: new Date(2015,5,1),
	dateFormat: 'yy/mm/dd',
	autoSize: true,
	numberOfMonths: 1,
	beforeShowDay: function(date){
		if(date.getMonth() != 6)
			return [true, ""];
		else
			return [false,"","Date Unavailable"]
	}

}).on('change', function(){
	var d = moment.utc($(this).val());
	if(d.month() != 5)
		{
			$(this).val('');
			alert('You can\'t select this date');
		}
});*/
