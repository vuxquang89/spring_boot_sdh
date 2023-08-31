var selectAddCableType, selectEditCableType,selectCableLink;
$(document).ready(function(){
	selectAddCableType = $('#selectAddCableType');
	selectEditCableType = $('#selectEditCableType');
	selectCableLink = $('#selectCableLink');
	
	selectAddCableType.on('change', function() {
		Id = selectAddCableType.find(":selected").val();
		getCableLink("add", Id);
	});
	
	selectEditCableType.on('change', function() {
		Id = selectEditCableType.find(":selected").val();
		getCableLink("edit", Id);
	});
	
	$('#startTime').on('change', getTime);
	$('#endTime').on('change', getTime);
	
	$('#btnAddOperational').on('click', function(){
		
		checkInputOperate();
	});
	
	$('.btn-close-modal').on('click', function(){
		$('#messageContent').text('');
	})
	
	$('#btnOk').on('click', function(){
		$('#formOperate').submit();
	})
});

function checkInputOperate(){ 
	var date = new Date($('#startTime').val());
	id = selectAddCableType.find(":selected").val();
	var url = contextPath + "api/operate/checkinput/"+formatDate(date)+"/cabletype/"+id;
	
	if(form_validate()){
		
		$.ajax({
	        type: "GET",
	        enctype: 'multipart/form-data',
	        url: url,
	        beforeSend: function(xhr){
				
				$('#btnReset').attr('disabled', true);
				$('#btnAddOperational').attr("disabled", true);
				$('#btnAddOperational').children('span').eq(0).addClass('spinner-border spinner-border-sm');
			},
			success: function (result) {
	            console.log(result);
				
	            if(result.status == 300){
					$('#formOperate').submit();
				}else if(result.status == 400){
					
					$('#messageContent').text(result.message + " Bạn có muốn tiếp tục thêm?");
					$('#notiModal').modal('show');
				}else{
					alerts('danger',result.message);
				}
	            $('#btnReset').removeAttr('disabled');
				$('#btnAddOperational').removeAttr("disabled");
				$('#btnAddOperational').children('span').eq(0).removeClass('spinner-border spinner-border-sm');        
	        },
	        error: function (e) {
	            console.log("ERROR : ", e);
				
	            $('#btnReset').removeAttr('disabled');
				$('#btnAddOperational').removeAttr("disabled");
				$('#btnAddOperational').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
	        }
		});
		
	}
}

function form_validate(){
	var startTime = $('#startTime');
	var endTime = $('#endTime');
	var processingTime = $('#processingTime');
	var note = $('#note');
	
	var checkForm = true;
	if(startTime.val() == null || startTime.val() == ""){
		errorAction(startTime, "Chưa chọn thời gian bắt đầu", false);		
		checkForm = false;
	}else{
		errorAction(startTime, "", true);
	}
	
	if(endTime.val() == null || endTime.val() == ""){
		errorAction(endTime, "Chưa chọn thời gian kết thúc", false);	
		checkForm = false;
	}else{
		errorAction(endTime, "", true);	
	}
	
	if(processingTime.val() == 'NaN'){
		errorAction(processingTime, "Thời gian kết thúc cần lớn hơn thời gian bắt đầu", false);		
		checkForm = false;
	}else{
		errorAction(processingTime, "", true);	
	}
	
	if(note.val().trim().length < 20){
		errorAction(note, "Cần nhập ghi chú lớn hơn 20 ký tự", false);
		chekcheckForm = false;
	}else{
		errorAction(note, "", true);
	}
	
	return checkForm;
	/*
	$("#formOperate").validate({
		//errorClass: "invalid-feedback",
	    rules: {
	      startTime: "required",
	      endTime: "required",
		  note: {
	        required: true,
	        minlength: 50
	      },
	    },
	    message: {
		  startTime: {
		    required: "Chưa chọn thời gian bắt đầu",
		  },
	     
	      endTime: "Chưa chọn thời gian kết thúc",
	      note: {
			required: "Cần nhập ghi chú",
	        minlength: "Cần nhập ghi chú lớn hơn 50 ký tự",        
	      },
	    },
	    submitHandler: function (form) {
	      	/*
			$.ajax({
		        url: "/JWeb101/add-content",
		        type: "POST",
		        data: $(form).serialize(),
		        success: function (response) {
		          $('.message').html(response);
		          resetForm();
		        },
		    });
			
			//form.submit();
	    },
  	});
	*/
}

function getCableLink(action, Id){
	
	url = contextPath + "api/operate/cablelink/list/"+action+"/"+Id;
	
	
	$.get(url, function(responseJson){
		
		selectCableLink.html('');
		if(!jQuery.isEmptyObject(responseJson[0])){
			$.each(responseJson, function(index, cableLink){
				selectCableLink.append($('<option/>', { 
			        value: cableLink.id,
			        text : cableLink.name 
			    }));
			});
		}else{
			selectCableLink.html('<option value="0" disabled selected>-- Không có dữ liệu --</option>');
		}
	}).fail(function(error){
		console.log("error", error);
		//alert("failed");
	}).done(function(){
		//alert("done");
		console.log("done");
	})
}

function getTime(){
	//value start
	var start = Date.parse($("input#startTime").val()); //get timestamp
	
	//value end
	var end = Date.parse($("input#endTime").val()); //get timestamp
	
	totalHours = NaN;
	minutes = NaN;
	if (start < end) {
		totalHours = Math.floor((end - start) / 1000 / 60 / 60); //milliseconds: /1000 / 60 / 60
		minutes = Math.floor((end - start) / 1000 / 60 % 60);
	}
	
	$("#processingTime").val(totalHours + "h" + minutes);
}

function convertTime(totalMinutes){
	hours = Math.floor(totalMinutes / 60);
	minutes = Math.floor(totalMinutes % 60);
	
	$("#processingTime").val(hours + "h" + minutes);
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}