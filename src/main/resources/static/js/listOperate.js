var dayTime;
var btnDeleteOperate;
var mDate;
$(document).ready(function(){
	dayTime = $('#dayTime');
	btnDeleteOperate = $('.btn-delete-operate');
	
	dayTime.on('change', function() {
		mDate = dayTime.val();		
	});
	
	btnDeleteOperate.on('click', infoDeleteOperatinal);
	
	$('.close-modal').on('click', resetModal);
});

function infoDeleteOperatinal(){
	infoOperate = $(this).parents('.operate-content');
	id = infoOperate.attr('data-id');
	d = dayTime.val();
	link = infoOperate.find('.link').text();
	operateEvent = infoOperate.find('.event').text();
	$('#operateId').val(id);
	$('#operateDate').val(d);
	$('#deleteContent').text("Bạn có muốn xóa " + operateEvent + " tuyến " + link + "?");
	$('#deleteOperateModal').modal('show');
}

function resetModal(){
	$('#operateId').val('');
	$('#operateDate').val('');
	$('#deleteContent').text('');
}

function goToURL() {
	if(dayTime.val() == null || dayTime.val() == ""){
		errorAction(dayTime, "Chưa chọn ngày xem thống kê", false);
	}else{
		mDate = dayTime.val();
		location.href = contextPath + 'operate/list/'+mDate;
	}
      
}