/**
 * 
 */
var btnAddItem, btnAddNewItem;
var tableSiteInfo;
var result;
var thisObjRow;

$(document).ready(function(){
	tableSiteInfo = $('#tableSiteInfo');
	
	btnAddItem = $('#btnAddItem');
	btnAddNewItem = $('#btnAddNewItem');
	
	btnAddItem.click(function(){
		loadUserForModalCard();
	});
	
	btnAddNewItem.click(function(){
		
	    if(checkChoiceUser()){
	    	addNewUserItem();
	    }
		
	});
	
	$('.btn-close-modal').click(function(){
		setModelAddNewItem();
		$('#modalAddItem').modal('toggle');
	});
	
	$('.btn-close-edit-modal').click(function(){
		resetModalEdit();
		$('#editModalScrollable').modal('toggle');
	});
	
	$('#tableSiteInfo').on('click','.btn-delete-item', deleteItem);
	
	$('#tableSiteInfo').on('click','.btn-edit-item', loadModalEdit);
	
	$('.btn-edit-modal-item').on('click', updateItem);
	
	$(".inputKeySearchUser").keyup(function(){
		$('.item-check').hide();
	    var key = $(this).val().trim();
	    $('.item-check').each(function(){
	       if($(this).text().toUpperCase().indexOf(key.toUpperCase()) != -1){
	           $(this).show();
	       }
	    });
	});
	
	
	//load table
	loadTable();
});

function loadUserForModalCard(){
	
	url = contextPath + "api/admin/users";
	
	$.get(url, function(responseJson){
		console.log(responseJson);
		$('#modalBodyItemContent').html('');
		$.each(responseJson, function(index, item){
			
			$('#modalBodyItemContent').append('<div class="col-6">'+
                                        '<div class="form-check item-check">'+
                                            '<input class="form-check-input" type="radio" name="radioUser" data-name="'+item.fullName+'" value="'+item.id+'" id="radio'+item.id+'">'+
                                            '<label class="form-check-label" for="radio'+item.id+'">'+item.fullName+'</label></div></div>');
		});		
		
		modalAddItem('show');
	}).fail(function(error){
		alert(error);
	}).done(function(){
		console.log("done");
	});
	
}

function addNewUserItem(){
	url = contextPath + "api/admin/excellentindividual";
	
	userId = $('input[name=radioUser]:checked').val();
	content = $('#content').val().trim();
	
	var formData = new FormData();
	
	formData.append('userId', userId);
	formData.append('content', content);
	if($('#imageUser')[0].files.length === 0){	
		alerts('warning','Chưa chọn hình đại diện');
		errorAction($('#imageUser'), 'Chưa chọn hình đại diện', false);
		return;
	}
	formData.append('imageAvatar', $('#imageUser')[0].files[0]);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: url,
        beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
			$('.btn-close-modal').attr('disabled', true);
			$('#btnAddNewItem').attr("disabled", true);
			$('#btnAddNewItem').children('span').eq(0).addClass('spinner-border spinner-border-sm');
		},
        data: formData,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (result) {
            
            if(result.status === 200){
            	modalAddItem('hide');            	
            	alerts('success',result.message);
            	$('#inputSearch').val('');
            						
				addItemForTable(result.content);
				
            }else if(result.status === 401){
            	errorAction($('#imageUser'), result.message, false);
            }else{
            	modalAddItem('hide'); 
            
            	alerts('danger',result.message);
            }
            
            $('.btn-close-modal').removeAttr('disabled');
            $('#btnAddNewItem').removeAttr("disabled");				
			$('#btnAddNewItem').children('span').eq(0).removeClass('spinner-border spinner-border-sm'); 
			        
        },
        error: function (e) {
            console.log("ERROR : ", e);
			if(e.status === 400){
				alerts('warning','Chưa chọn hình đại diện');
				errorAction($('#imageUser'), 'Chưa chọn hình đại diện', false);
			}else{
				alerts('danger','Thêm mới thất bại');
            	modalAddItem('hide');
			}
            
            $('.btn-close-modal').removeAttr('disabled');
            $('#btnAddNewItem').removeAttr("disabled");				
			$('#btnAddNewItem').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
			
        }
    });

}

function addItemForTable(item){
	if( $('#contentNoneData').length ){
		$('#tableSiteInfo tbody').html("");
	} 
	$('#tableSiteInfo tbody').append('<tr class="site-info-item" data-id="'+item.id+'">'+
                                            '<th scope="row">'+item.id+'</th>'+
											'<td>'+item.fullName+'</td>'+
                                            '<td>'+item.content+'</td>'+
                                            '<td id="'+item.id+'"><div class="row">'+
												'<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
							            			' data-slider="0" data-id="'+item.id+'">'+
							                        	'<a data-target="#carouselExampleCaptions" data-bs-slide-to="0">'+							                            	
							                            	'<img class="img-fluid td-img" src="'+contextPath+item.imageAvatarResize+'" alt="Image">'+                                                           
							                            '</a></div>'+					
												'</div></td>'+
                                            
                                            '<td class="text-center">'+
                                                '<button class="btn btn-warning btn-sm btn-edit-item" type="button" '+
                                                    
                                                        '><i class="bi bi-pencil-square"></i></button>'+
                                                '<button class="btn btn-danger btn-sm btn-delete-item" type="button"><i '+
                                                        'class="bi bi-trash3-fill"></i></button>'+
                                            '</td></tr>');
}


function loadTable(){
	
	url = contextPath + "api/admin/excellentindividual";
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log(responseJson);
		if(!jQuery.isEmptyObject(responseJson[0])){
			$('#tableSiteInfo tbody').html("");
				$.each(responseJson, function(index, item){
					$('#tableSiteInfo tbody').append('<tr class="site-info-item" data-id="'+item.id+'">'+
                                            '<th scope="row">'+item.id+'</th>'+
											'<td>'+item.fullName+'</td>'+
                                            '<td>'+item.content+'</td>'+
                                            '<td id="'+item.id+'"><div class="row">'+
												'<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
							            			' data-slider="0" data-id="'+item.id+'">'+
							                        	'<a data-target="#carouselExampleCaptions" data-bs-slide-to="0">'+							                            	
							                            	'<img class="img-fluid td-img" src="'+contextPath+item.imageAvatarResize+'" alt="Image">'+                                                           
							                            '</a></div>'+					
												'</div></td>'+
                                            
                                            '<td class="text-center">'+
                                                '<button class="btn btn-warning btn-sm btn-edit-item" type="button" '+
                                                    
                                                        '><i class="bi bi-pencil-square"></i></button>'+
                                                '<button class="btn btn-danger btn-sm btn-delete-item" type="button"><i '+
                                                        'class="bi bi-trash3-fill"></i></button>'+
                                            '</td></tr>');
			});
		}else{
			$('#tableSiteInfo tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
		}
		
		
		$('body').removeClass("loading");
	}).fail(function(){
		console.log("error loadDistrict");
	});
}

function loadModalEdit(){
	
	thisObjRow = $(this);
	itemId = thisObjRow.parents('.site-info-item').attr('data-id');
	
	$('#editModalScrollableTitle').text('Cập nhật');
	
	url = contextPath + "api/admin/excellentindividual/"+itemId;
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log(responseJson);
		
		
		if(!jQuery.isEmptyObject(responseJson)){
			response = responseJson.content;
			$('#editName').val(response.fullName);
			$('#editContent').val(response.content);
			
			$('#editModalScrollable .row-container-image').append('<div class="col-sm-3"> '+
                            			'<div class="row justify-content-center row-content" data-device-image-id="'+response.id+'">'+                            				
                            				'<img src="'+contextPath+response.imageAvatarResize+'" style="height: 100%; width: 100%; object-fit: contain" alt="Image">'+
                            			'</div>'+
                            		'</div>');
			
		}else{
			console.log("null");
		}
		
		$('body').removeClass("loading");
		$('#editModalScrollable').modal('show');
	}).fail(function(error){
		console.log(error);
		console.log("error loadDistrict");
	});
}

function updateItem(){
	url = contextPath + "api/admin/excellentindividual";
	
	itemId = thisObjRow.parents('.site-info-item').attr('data-id');
	
	content = $('#editContent').val().trim();
	
	var formData = new FormData();
	formData.append('id', itemId);
	
	formData.append('content', content);
	if($('#editImageAvatar')[0].files.length !== 0){
	 	formData.append('imageAvatar', $('#editImageAvatar')[0].files[0]);
	}
	
	console.log(formData);
	
	$.ajax({
        type: "PUT",
        enctype: 'multipart/form-data',
        url: url,
        beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
			
			$('.btn-close-edit-modal').attr("disabled", true);			
			$('.btn-edit-modal-item').attr("disabled", true);
			$('.btn-edit-modal-item').children('span').eq(0).addClass('spinner-border spinner-border-sm');
		},
        data: formData,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (result) {
            console.log(result);
            if(result.status === 200){            	            	
            	alerts('success',result.message);  
            	updateRowTable(result.content);          	
	            
            }else if(result.status === 401){            	
            	
            	$('.btn-close-edit-modal').removeAttr("disabled");
            	$('.btn-edit-modal-item').removeAttr("disabled");				
				$('.btn-edit-modal-item').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
				return false;
            }else{
            	alerts('danger',result.message);
            }       
            $('#editModalScrollable').modal('hide');
            resetModalEdit();
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alerts('danger','Cập nhật thất bại');
            resetModalEdit();
			$('#editModalScrollable').modal('hide');
			$('.btn-close-edit-modal').removeAttr("disabled");
            $('.btn-edit-modal-item').removeAttr("disabled");				
			$('.btn-edit-modal-item').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
        }
    });
    
}


function updateRowTable(siteInfo){

	thisObjRow.parents('.site-info-item').html('<th scope="row">'+siteInfo.id+'</th>'+
    		'<td>'+siteInfo.fullName+'</td>'+
            '<td>'+siteInfo.content+'</td>'+
            '<td id="'+siteInfo.id+'"><div class="row">'+
				'<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
							            			' data-slider="0" data-id="'+siteInfo.id+'">'+
							                        	'<a data-target="#carouselExampleCaptions" data-bs-slide-to="0">'+							                            	
							                            	'<img class="img-fluid td-img" src="'+contextPath+siteInfo.imageAvatarResize+'" alt="Image">'+                                                           
							                            '</a></div>'+					
			'</div></td>'+            
            '<td class="text-center">'+
            '<button class="btn btn-warning btn-sm btn-edit-item" type="button">'+                                                    
            	'<i class="bi bi-pencil-square"></i></button>'+
            '<button class="btn btn-danger btn-sm btn-delete-item" type="button"><i '+
            	'class="bi bi-trash3-fill"></i></button>'+
            '</td>');
	
}


function deleteItem(){
	siteInfoItem = $(this).parents('.site-info-item');
	itemId = siteInfoItem.attr('data-id');
	
	deviceName = siteInfoItem.find('.site-info-device-name').text();
	
	url = contextPath + "api/admin/excellentindividual/"+itemId;
	
	if (confirm('Bạn có muốn xóa ?')) {
		$.ajax({
			type: "DELETE",
			url: url,
			beforeSend: function(xhr){
				
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function(responseJson){
			console.log(responseJson);
			if(responseJson == "OK"){
				alerts('success','Xóa thành công');
				siteInfoItem.remove();
			} else{
				alerts('warning', 'Không thể xóa');
			}
		}).fail(function(err){
			console.log(err);
			alerts('warning', 'Đã có lỗi xảy ra');
		});
				
	}
}

function setModelAddNewItem(){
	$('#label_header_title').next().text("");
	errorAction($('#imageUser'), "", true);
	$('#content').val('');
}

/* function check */

function checkChoiceUser(){
	var $radios = $('input:radio[name=radioUser]');
	if($radios.is(':checked') === false) {
		
		$('#label_header_title').addClass("is-invalid");
	   	$('#label_header_title').next().text("Chưa chọn cá nhân nào");
	   	return false;
	}else{
		$('#label_header_title').removeClass("is-invalid");
		$('#label_header_title').next().text("");
		return true;
	}
}

function modalAddItem(status){
	$('#modalAddItem').modal(status);
}

function resetModalEdit(){
	$('#editName').val('');
	
	errorAction($('#editImageAvatar'), "", true);
	$('#editContent').val('');
	$('#editModalScrollable .row-container-image').html('');
	
	$('.btn-close-edit-modal').removeAttr("disabled");
	$('.btn-edit-modal-item').removeAttr("disabled");
	$('.btn-edit-modal-item').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
}

function addNewItem(){
	var countRowTable = $('#tableSiteInfo tbody').find('tr').length;
	pageSize = $('#selectPageItem').find(':selected').val();
	if(countRowTable >= pageSize){
		paging(pageEnd);
	}
	return false;
}