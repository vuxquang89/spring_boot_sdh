/**
 * 
 */
$(document).ready(function(){
	$('#selectAction').change(function() {
		
	    var selected = $(this).val();
	    if(selected == 1){
	      $('#noteAction').show(500);
		  $('#noteAction').next().show();
	    }
	    else{
		  $('#noteAction').hide(500);	
		  $('#noteAction').val("");		
		  $('#noteAction').next().hide();	
	    }
	});
	
	$('#noteAction').on('change keyup paste',function(){
		if($(this).val().trim().length >= 20){
			errorAction($('#noteAction'), "", true);
		}else{
			errorAction($('#noteAction'), "Cần nhập nội dung lớn hơn 20 ký tự", false);
		}
	});
	
	$('#btnReset').on('click', function(){
		$('input:checkbox[name=checkAction]').each(function() 
		{    
			var id = $(this).attr('id');
		   	$('#lbl_'+id).removeClass("text-red");
			  
		});
		$('#noteAction').hide(500);	
		$('#noteAction').val("");		
		errorAction($('#noteAction'), "", true);
	});
	
	$('.list-check').on('change', function() {
		var chklab = $(this).attr('id');
		if ($(this).is(':checked')) {
	      	$("#lbl_" + chklab).removeClass("text-red");
	    } else{
			$("#lbl_" + chklab).addClass("text-red");
		}
	});
	
	$('#btnAddShift').on('click', addShift);
})

function addShift(){
	if(formValidate()){
		alert("send port");
	}
}

function formValidate(){
	var check = true;
	
	if($('#selectAction').val() == 1){
		if($('#noteAction').val().trim().length < 20){
			errorAction($('#noteAction'), "Cần nhập nội dung lớn hơn 20 ký tự", false);
			check = false;
		}else{
			errorAction($('#noteAction'), "", true);
		}
	}
	
	$('input:checkbox[name=checkAction]').each(function() 
	{    
		var id = $(this).attr('id');
	    if($(this).is(':checked')){
			//alert($(this).attr('id'));
			$('#lbl_'+id).removeClass("text-red");
			//var chklab = $(this).attr('id');
			//console.log("val", $(this).val());
		}else{
			//alert("uncheck " + $(this).attr('id'));
			
			$('#lbl_'+id).addClass("text-red");
			check = false;
		}
	      
	});
	
	
	return check;
}