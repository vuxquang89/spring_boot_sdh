function alertSuccess(message){
	$('#alerts').html('<div class="alert alert-success alert-dismissible fade show" role="alert">'+
		'<strong>Success! </strong> '+message+'.'+
		'<button type="button" class="btn-close btn-sm" data-dismiss="alert" aria-label="Close"></button>'+
	'</div>');
	$(window).scrollTop(0);
	window.setTimeout(function() {
		$(".alert").fadeTo(500, 0).slideUp(500, function(){
        	$(this).remove(); 
            });
        }, 5000);
    $('.alert .close').on("click", function(e){
       	$(this).parent().fadeTo(500, 0).slideUp(500);
    });
}

function alertDanger(message){
	$('#alerts').html('<div class="alert alert-danger alert-dismissible fade show" role="alert">'+
		'<strong>Danger! </strong> '+message+'.'+
		'<button type="button" class="btn-close btn-sm" data-dismiss="alert" aria-label="Close"></button>'+
	'</div>');
	$(window).scrollTop(0);
	window.setTimeout(function() {
		$(".alert").fadeTo(500, 0).slideUp(500, function(){
        	$(this).remove(); 
            });
        }, 5000);
    $('.alert .close').on("click", function(e){
       	$(this).parent().fadeTo(500, 0).slideUp(500);
    });
}

function alertWarning(message){
	$('#alerts').html('<div class="alert alert-warning alert-dismissible fade show" role="alert">'+
		'<strong>Warning! </strong> '+message+'.'+
		'<button type="button" class="btn-close btn-sm" data-dismiss="alert" aria-label="Close"></button>'+
	'</div>');
	$(window).scrollTop(0);
	window.setTimeout(function() {
		$(".alert").fadeTo(500, 0).slideUp(500, function(){
        	$(this).remove(); 
            });
        }, 5000);
    $('.alert .close').on("click", function(e){
       	$(this).parent().fadeTo(500, 0).slideUp(500);
    });
}

function alerts(status, message){
	$('.alert-top').html('<div class="toast align-items-center bg-'+status+' border-0" role="alert" '+
					'data-autohide="false" aria-live="assertive" aria-atomic="true">'+
			'<div class="d-flex">'+
				'<div class="toast-body"><strong>'+status+'! </strong>'+message+'</div>'+
				    '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'+
				'</div></div>');
	$('.toast').toast('show');
	window.setTimeout(function() {
		$(".toast").fadeTo(500, 0).slideUp(500, function(){
        	$(this).remove(); 
            });
        }, 5000);
}