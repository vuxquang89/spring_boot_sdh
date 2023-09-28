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
	
	$('#selectAction_MB').change(function() {
		
	    var selected = $(this).val();
	    if(selected == 1){
	      $('#noteAction_MB').show(500);
		  $('#noteAction_MB').next().show();
	    }
	    else{
		  $('#noteAction_MB').hide(500);	
		  $('#noteAction_MB').val("");		
		  $('#noteAction_MB').next().hide();	
	    }
	});
	
	$('#noteAction').on('change keyup paste',function(){
		if($(this).val().trim().length >= 20){
			errorAction($('#noteAction'), "", true);
		}else{
			errorAction($('#noteAction'), "Cần nhập nội dung lớn hơn 20 ký tự", false);
		}
	});
	
	$('#noteAction_MB').on('change keyup paste',function(){
		if($(this).val().trim().length >= 20){
			errorAction($('#noteAction_MB'), "", true);
		}else{
			errorAction($('#noteAction_MB'), "Cần nhập nội dung lớn hơn 20 ký tự", false);
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
	
	$('#btnReset_MB').on('click', function(){
		$('input:checkbox[name=MB_checkAction]').each(function() 
		{    
			var id = $(this).attr('id');
		   	$('#MB_lbl_'+id).removeClass("text-red");
			  
		});
		$('#noteAction_MB').hide(500);	
		$('#noteAction_MB').val("");		
		errorAction($('#noteAction_MB'), "", true);
	});
	
	$('.list-check').on('change', function() {
		var chklab = $(this).attr('id');
		if ($(this).is(':checked')) {
	      	$("#lbl_" + chklab).removeClass("text-red");
	    } else{
			$("#lbl_" + chklab).addClass("text-red");
		}
	});
	
	$('.list-check-MB').on('change', function() {
		var chklab = $(this).attr('id');
		if ($(this).is(':checked')) {
	      	$("#lbl_" + chklab).removeClass("text-red");
	    } else{
			$("#lbl_" + chklab).addClass("text-red");
		}
	});
	
	$('#btnAddShift').on('click', addShift);
	$('#btnAddShift_MB').on('click', addShift_MB);
	
	//set default month select
	setDefaultMonth();
	
	$('#btnExportExcel').on('click', exportExcel);
})

function setDefaultMonth(){
	//const monthControl = document.querySelector('input[type="month"]');
	const monthControl = $('#shiftMonth');
	const date= new Date();
	const month=("0" + (date.getMonth() + 1)).slice(-2);
	const year=date.getFullYear()
	monthControl.val(`${year}-${month}`);
}

function exportExcel(){
	var month = $('#shiftMonth').val();
	var filter = $('#filter').val();
	url = contextPath + "shift/export/excel/"+month+"/"+filter;
	console.log(url);
	$.ajax({
	        type: "GET",
	        enctype: 'multipart/form-data',
	        contentType: "application/json; charset=utf-8",
	        url: url,
	        xhrFields: {
                responseType: 'blob'
            },
	        beforeSend: function(xhr){
				$('#overlay').fadeIn(300);
				
			},
			success: function (response, status, xhr) {
				
	            //var blob = new Blob([response], { type: 'data:application/vnd.ms-excel' });
	            var blob = new Blob([response], { type: "application/octetstream" });
	            //Check the Browser type and download the File.
                var isIE = false || !!document.documentMode;
                
                var filename = "";
                var disposition = xhr.getResponseHeader('Content-Disposition');

                if (disposition) {
                    var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                    var matches = filenameRegex.exec(disposition);
                    if (matches !== null && matches[1]) filename = matches[1].replace(/['"]/g, '');
                }
                if (isIE) {
                	window.navigator.msSaveBlob(blob, filename);
                } else {
                	var url = window.URL || window.webkitURL;
                   	link = url.createObjectURL(blob);
                  	var a = $("<a />");
                    a.attr("download", filename);
                    a.attr("href", link);
                    $("body").append(a);
                    a[0].click();
                    $("body").remove(a);
                }
                
	            $('#overlay').fadeOut(300);
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
				$('#overlay').fadeOut(300);
	        }
		});
}

function addShift(){
	if(formValidate()){
		$('#formShift_MT').submit();
	}
}

function addShift_MB(){
	if(formValidate_MB()){
		$('#formShift_MB').submit();
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

function formValidate_MB(){
	var check = true;
	
	if($('#selectAction_MB').val() == 1){
		if($('#noteAction_MB').val().trim().length < 20){
			errorAction($('#noteAction_MB'), "Cần nhập nội dung lớn hơn 20 ký tự", false);
			check = false;
		}else{
			errorAction($('#noteAction_MB'), "", true);
		}
	}
	
	$('input:checkbox[name=MB_checkAction]').each(function() 
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