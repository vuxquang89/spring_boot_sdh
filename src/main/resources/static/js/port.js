/**
 * 
 */
 $(document).ready(function(){
	$('#btnGetPort').on('click', function(){
		getPort();
	});
	
	$('#btnGetUrl').on('click', function(){
		getUrl();
	});
});

function getUrl(){
	url = contextPath + "api/url";
	
	$.get(url, function(responseJson){
		
		console.log(responseJson);
		$('.port').html(responseJson);
	}).fail(function(error){
		alert("failed");
	}).done(function(){
		console.log("done");
	});
}

function getPort(){
	url = contextPath + "api/port";
	
	$.get(url, function(responseJson){
		
		console.log(responseJson);
		$('.port').html(responseJson);
	}).fail(function(error){
		alert("failed");
	}).done(function(){
		console.log("done");
	});
}