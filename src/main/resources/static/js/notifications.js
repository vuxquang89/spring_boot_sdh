/**
 * 
 */

$(document).ready(function(){
	if($('#district').length){
		var district = $('#district').val();
		var url = contextPath + "api/operate/notifications/"+district;
		$.ajax({
		        type: "GET",
		        enctype: 'multipart/form-data',
		        url: url,
		        beforeSend: function(xhr){
					/*
					$('#btnReset').attr('disabled', true);
					$('#btnAddOperational').attr("disabled", true);
					$('#btnAddOperational').children('span').eq(0).addClass('spinner-border spinner-border-sm');
					*/
				},
				success: function (result) {
					var countNoti = result.length;
					if(countNoti > 0)
						$('#badgeNotification').text(countNoti);
						
					
					$('.notifications-wrapper').html('');
					if(!jQuery.isEmptyObject(result[0])){
						$.each(result, function(index, item){
							$('.notifications-wrapper').append(
								'<a class="content" href="'+contextPath+'operate/edit/'+item.id+'">'+					      
							       '<div class="notification-item">'+
							        '<h4 class="item-title">'+item.title+' · '+item.start.replace('T', ' ')+'</h4>'+
							        '<p class="item-info">'+item.note+'</p>'+
							      '</div>'+							       
							    '</a>'
							);
						});
					}else{
						$('.notifications-wrapper').html('không có thông báo mới');
					}
						
		                    
		        },
		        error: function (e) {
		            console.log("ERROR : ", e);
					
		        }
			});
	}else{
		console.log("không tồn tại");
	}
	
})