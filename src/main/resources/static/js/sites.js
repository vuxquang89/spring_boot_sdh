var selectDistrict, selectSite;
var btnAddDevice, btnAddDeviceSite;
var btnSearch, btnErase;
var tableSiteInfo;
var categorys;
var result;
var thisObjRow;

$(document).ready(function(){
	selectDistrict = $('#selectDistrict');
	selectSite = $('#selectSite');
	tableSiteInfo = $('#tableSiteInfo');
	
	btnAddDevice = $('#btnAddDevice');
	btnAddDeviceSite = $('#btnAddDeviceSite');
	btnEraser = $('#btnEraser');
	btnSearch = $('#btnSearch');
	
	selectDistrict.on('change', loadSites);
	
	//selectSite.on('change', loadSiteInfo);
	selectSite.on('change', function(){
		checkSelectSite(selectSite);
		paging(1);
	});
	
	
	$('#selectPageItem').on('change', function(){
		paging(1);
	});
	
	btnSearch.click(function(){
		paging(1);
	})
	
	btnEraser.click(function(){
		$('#inputSearch').val('');
		paging(1);
	});
	
	btnAddDevice.click(function(){
		if(checkSelectSite(selectSite)){			
			loadDeviceSiteCard();
		}
		
	});
	
	btnAddDeviceSite.click(function(){
		
	    if(checkChoiceDevice() && isNumberic($('#quantityDevice'))){
	    	addNewDeviceSite();
	    }
		
	});
	
	$('.btn-close-modal').click(function(){
		setModelAddNewDevice();
		$('#modalAddDeviceSite').modal('toggle');
	});
	
	$('.btn-close-model-slider-image').click(function(){
		resetModelShowImage();
		$('#showImageSliderModal').modal('toggle');
	});
	
	$('.btn-close-edit-modal').click(function(){
		resetModelSiteInfoEdit();
		reloadRowTable();
		$('#editModalScrollable').modal('toggle');
	});
	
	$('#tableSiteInfo').on('click','.element-model-show-image', loadModelShowImage);
	
	$('#tableSiteInfo').on('click','.btn-delete-site-info', deleteSiteInfo);
	
	$('#tableSiteInfo').on('click','.btn-edit-site-info', loadModalSiteInfoEdit);
	
	$('#editModalScrollable').on('click', '.btn-delete-device-image', deleteDeviceImage);
	
	$('.btn-edit-site-info').on('click', updateSiteInfo);
	
	$(".inputKeySearchDevice").keyup(function(){
		$('.device-check').hide();
	    var key = $(this).val().trim();
	    $('.device-check').each(function(){
	       if($(this).text().toUpperCase().indexOf(key.toUpperCase()) != -1){
	           $(this).show();
	       }
	    });
	});
	
});

function paging(currentPage){
	pageSize = $('#selectPageItem').find(':selected').val();
	
	districtId = selectDistrict.find(':selected').val();
	siteId = selectSite.find(':selected').val();
	pagingAdminSiteInfo(siteId, districtId, pageSize, currentPage);
}

function loadSites(){
	$('#tableSiteInfo tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
	selectSite.removeClass("is-invalid");
    selectSite.next().text("");
	districtId = $(this).find(":selected").val();
	
	url = contextPath + "api/district/site/list/"+districtId;
	
	$.get(url, function(responseJson){
		
		selectSite.html('<option value="0" disabled selected>-- Chọn Site --</option>');
		$.each(responseJson, function(index, site){
			selectSite.append($('<option/>', { 
		        value: site.id,
		        text : site.name 
		    }));
		});
	}).fail(function(error){
		alert("failed");
	}).done(function(){
		console.log("done");
	});
}

function loadSiteInfo(){
	districtId = selectDistrict.find(':selected').val();
	siteId = $(this).find(':selected').val();
	
	url = contextPath + "api/siteinfo/list/"+siteId;
	
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
				$.each(responseJson, function(index, siteInfo){
					$('#tableSiteInfo tbody').append('<tr class="site-info-item" data-id="'+siteInfo.id+'" '+
												'data-device-id="'+siteInfo.device.id+'" data-device-name="'+siteInfo.device.name+'">'+
                                            '<th scope="row">'+siteInfo.id+'</th>'+
                                            '<td>'+checkCategory(siteInfo.device.id)+'</td>'+
                                            '<td class="site-info-device-name">'+siteInfo.device.name+'</td>'+
                                            '<td>'+siteInfo.quantity+'</td>'+
                                            '<td>'+siteInfo.serialNumber+'</td>'+
                                            '<td>'+siteInfo.slot+'</td>'+
                                            '<td id="'+siteInfo.id+'"><div class="row"></div></td>'+
                                            '<td>'+siteInfo.note+'</td>'+
                                            '<td class="text-center">'+
                                                '<button class="btn btn-warning btn-sm btn-edit-site-info" type="button" '+
                                                    //'data-bs-toggle="modal" data-bs-target="#editModalScrollable">'+
                                                        '><i class="bi bi-pencil-square"></i></button>'+
                                                '<button class="btn btn-danger btn-sm btn-delete-site-info" type="button"><i '+
                                                        'class="bi bi-trash3-fill"></i></button>'+
                                            '</td></tr>');
	                if(!jQuery.isEmptyObject(siteInfo.siteImages)){
	                	siteImages = siteInfo.siteImages;
	                	var i = 0;
	                	$.each(siteImages, function(idx, img){
	                		$('#tableSiteInfo tbody td#'+siteInfo.id+' .row').append('<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
                                                    ' data-slider="'+i+'" data-siteinfo-id="'+siteInfo.id+'" data-img-id="'+img.id+'" data-device-name="'+siteInfo.device.name+'" data-img-name="'+img.fileName+'">'+
                                                        '<a data-target="#carouselExampleCaptions" data-bs-slide-to="'+i+'">'+
                                                        	//'<img class="img-fluid td-img" src="'+contextPath+'upload/images/'+districtId+'/'+siteId+'/'+img.fileName+'" alt="Image">'+
                                                        	'<img class="img-fluid td-img" src="'+contextPath+img.pathName+'/'+img.fileName100+'" alt="Image">'+                                                           
                                                        '</a></div>');
                            i++
                        });
	                }
                
			});
		}else{
			$('#tableSiteInfo tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
		}
		
		
		$('body').removeClass("loading");
	}).fail(function(){
		console.log("error loadDistrict");
	});
}

function reloadRowTable(){
	districtId = selectDistrict.find(':selected').val();
	siteId = selectSite.find(':selected').val();
	siteInfoId = thisObjRow.parents('.site-info-item').attr('data-id');
	
	url = contextPath + "api/siteinfo/"+siteInfoId;
	
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
			updateRowTable(districtId, siteId, responseJson[0]);
		}else{
			console.log("null");
		}
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log(error);
		console.log("error loadDistrict");
	});
}

function updateRowTable(districtId, siteId, siteInfo){
	thisObjRow.parents('.site-info-item').html('<th scope="row">'+siteInfo.id+'</th>'+
    		'<td>'+checkCategory(siteInfo.device.id)+'</td>'+
            '<td class="site-info-device-name">'+siteInfo.device.name+'</td>'+
            '<td>'+siteInfo.quantity+'</td>'+
            '<td>'+siteInfo.serialNumber+'</td>'+
            '<td>'+siteInfo.slot+'</td>'+
            '<td id="'+siteInfo.id+'"><div class="row"></div></td>'+
            '<td>'+siteInfo.note+'</td>'+
            '<td class="text-center">'+
            '<button class="btn btn-warning btn-sm btn-edit-site-info" type="button">'+                                                    
            	'<i class="bi bi-pencil-square"></i></button>'+
            '<button class="btn btn-danger btn-sm btn-delete-site-info" type="button"><i '+
            	'class="bi bi-trash3-fill"></i></button>'+
            '</td>');
			
	images = siteInfo.siteImages;
	if(!jQuery.isEmptyObject(images)){
		var i = 0;
	    $.each(images, function(idx, img){
	    	$('#tableSiteInfo tbody td#'+siteInfoId+' .row').append('<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
            		' data-slider="'+i+'" data-siteinfo-id="'+siteInfoId+'" data-img-id="'+img.id+'" data-device-name="'+siteInfo.device.name+'" data-img-name="'+img.fileName+'">'+
                    	'<a data-target="#carouselExampleCaptions" data-bs-slide-to="'+i+'">'+
                        	'<img class="img-fluid td-img" src="'+contextPath+img.pathName+'/'+img.fileName100+'" alt="Image">'+                                                           
                        '</a></div>');
            i++
        });
				
	}
}

function loadModalSiteInfoEdit(){
	
	thisObjRow = $(this);
	siteInfoId = thisObjRow.parents('.site-info-item').attr('data-id');
	districtId = selectDistrict.find(':selected').val();
	districtName = selectDistrict.find(':selected').text();
	siteId = selectSite.find(':selected').val();
	siteName = selectSite.find(':selected').text();
	
	$('#editModalScrollableTitle').text('Cập nhật '+districtName +' - ' + siteName);
	
	url = contextPath + "api/siteinfo/"+siteInfoId;
	
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
			
			$('#editDeviceName').val(responseJson[0].device.name);
			$('#editQuantityDevice').val(responseJson[0].quantity);
			$('#editSerialNumber').val(responseJson[0].serialNumber);
			$('#editSlot').val(responseJson[0].slot);
			$('#editNote').val(responseJson[0].note);
			images = responseJson[0].siteImages;
			if(!jQuery.isEmptyObject(images)){
				$.each(images, function(i, img){
					$('#editModalScrollable .row-container-device-image').append('<div class="col-sm-3"> '+
                            			'<div class="row justify-content-center row-content" data-device-image-id="'+img.id+'">'+
                            				//'<img src="'+contextPath+'upload/images/'+districtId+'/'+siteId+'/'+img.fileName+'" style="height: 100%; width: 100%; object-fit: contain" alt="Image">'+
                            				'<img src="'+contextPath+img.pathName+'/'+img.fileName100+'" style="height: 100%; width: 100%; object-fit: contain" alt="Image">'+
                            				'<button type="button" class="btn btn-danger btn-sm btn-delete-device-image">'+
                            					'<i class="bi bi-trash3-fill"></i>'+
                            				'</button>'+
                            			'</div>'+
                            		'</div>');
				});
			}
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

function deleteSiteInfo(){
	siteInfoItem = $(this).parents('.site-info-item');
	siteInfoId = siteInfoItem.attr('data-id');
	
	deviceName = siteInfoItem.find('.site-info-device-name').text();
	
	url = contextPath + "api/siteinfo/device/delete/"+siteInfoId;
	
	if (confirm('Bạn có muốn xóa ' + deviceName + ' ?')) {
		
		$.get(url, function(){
		
		}).fail(function(error){
			alerts('danger','Không thể xóa');
		}).done(function(response){
			if(response == "OK"){
				alerts('success','Xóa thành công');
				siteInfoItem.remove();
			} else{
				alerts('warning', 'Không thể xóa');
			}
		})
		
	}
}

function deleteDeviceImage(){
	contentDeviceImage = $(this).parents('.row-content');
	deviceImageId = contentDeviceImage.attr('data-device-image-id');
	
	url = contextPath + "api/siteinfo/image/device/delete/"+deviceImageId;
	
	if (confirm('Bạn có muốn xóa ?')) {
		//contentDeviceImage.remove();
		
		$.get(url, function(){
		
		}).fail(function(error){
			alerts('danger','Không thể xóa');
		}).done(function(response){
			if(response == "OK"){
				alerts('success','Xóa thành công');
				contentDeviceImage.remove();
			} else{
				alerts('warning', 'Không thể xóa');
			}
		})
		
	}
}

function loadModelShowImage(){
	
	siteInfoId = $(this).attr('data-siteinfo-id');
	districtId = selectDistrict.find(':selected').val();
	siteId = selectSite.find(':selected').val();
	deviceName = $(this).attr('data-device-name');
	iSlider = $(this).attr('data-slider');
	
	$('#showImageSliderModalLabel').text(deviceName);
	
	/*
	
	url = contextPath + "api/siteinfo/image/list/"+siteInfoId;
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log(responseJson);
		$('#showImageSliderModalLabel').text(deviceName);
		var n = 0;
		
		$.each(responseJson, function(k, image){
			
			$('#carouselExampleCaptions .carousel-indicators').append('<button type="button" id="btn-item-'+image.id+'" data-bs-target="#carouselExampleCaptions" '+
									'data-bs-slide-to="'+n+'" aria-label="Slide '+i+'"></button>');
			$('#carouselExampleCaptions .carousel-inner').append('<div id="div-item-'+image.id+'" class="carousel-item">'+
                                    '<img class="d-block w-100"'+
                                        'src="'+contextPath+'upload/images/'+districtId+'/'+siteId+'/'+image.fileName+'" alt="First '+i+'">'+
                                    '<div class="carousel-caption d-none d-md-block">'+
                                        '<h5></h5>'+
                                        '<p></p>'+
                                    '</div></div>');
            if(n==0){
            	$('#carouselExampleCaptions .carousel-indicators #btn-item-'+image.id).addClass('active');
            	$('#carouselExampleCaptions .carousel-indicators #btn-item-'+image.id).attr('aria-current', 'true');
            	$('#carouselExampleCaptions .carousel-inner #div-item-'+image.id).addClass('active');
            }
        	n++;
		});
		
		$('#showImageSliderModal').modal('show');
	}).fail(function(err){
		console.log(err);
		alerts('warning', 'Đã có lỗi xảy ra');
	});
	*/
	
	
	var rowCountImage = $('#tableSiteInfo tbody td#'+siteInfoId+' .element-model-show-image').length;
	var elImage = $('#tableSiteInfo tbody td#'+siteInfoId+' .element-model-show-image');
	var i = 0;
	
	elImage.each(function(){
		imageId = $(this).attr('data-img-id');
		imageName = $(this).attr('data-img-name');
		$('#carouselExampleCaptions .carousel-indicators').append('<button type="button" id="btn-item-'+imageId+'" data-bs-target="#carouselExampleCaptions" '+
									'data-bs-slide-to="'+i+'" aria-label="Slide '+i+'"></button>');
		$('#carouselExampleCaptions .carousel-inner').append('<div id="div-item-'+imageId+'" class="carousel-item">'+
                                    '<img class="d-block w-100" '+
                                        'src="'+contextPath+'upload/images/'+districtId+'/'+siteId+'/'+imageName+'" alt="First '+i+'">'+
                                    '<div class="carousel-caption d-none d-md-block">'+
                                        '<h5></h5>'+
                                        '<p></p>'+
                                    '</div></div>');
        if(i==iSlider){
        	$('#carouselExampleCaptions .carousel-indicators #btn-item-'+imageId).addClass('active');
            $('#carouselExampleCaptions .carousel-indicators #btn-item-'+imageId).attr('aria-current', 'true');
            $('#carouselExampleCaptions .carousel-inner #div-item-'+imageId).addClass('active');
        }
        i++;
	})
	$('#showImageSliderModal').modal('show');
	
}

function addNewDeviceSite(){
	url = contextPath + "api/siteinfo/save";
	
	districtId = selectDistrict.find(":selected").val();
	siteName = selectSite.find(":selected").text();
	siteId = selectSite.find(":selected").val();
	deviceId = $('input[name=radioDevice]:checked').val();
	deviceName = $('input[name=radioDevice]:checked').attr('data-name');
	quantity = $('#quantityDevice').val().trim();
	serialNumber = $('#serialNumber').val().trim();
	slot = $('#slot').val().trim();
	note = $('#note').val().trim();
	
	/*
	imageDevice = $('input[type=file]').val().replace(/.*(\/|\\)/, '');
	jsonData = {device : {id:deviceId, name:deviceName}, site:{id:siteId, name:siteName}, quantity: quantity, note : note, serialNumber: serialNumber, slot: slot, siteImages : getFileImages()};
	console.log(jsonData);
	var files = $('#imageDevice').prop("files");
	$('input[type="file"]').change(function(e){
                var geekss = e.target.files[0].name;
                alert(geekss + ' is the selected file .');
            });
	*/
	var formData = new FormData();
	formData.append('districtId', districtId);
	formData.append('deviceId', deviceId);
	formData.append('siteId', siteId);
	formData.append('quantity', quantity);
	formData.append('note', note);
	formData.append('serialNumber', serialNumber);
	formData.append('slot', slot);
	
	$.each($('#imageDevice')[0].files, function(i, file) {
	    formData.append('files', file);
	});
	
	console.log(formData);
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: url,
        beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
			$('.btn-close-modal').attr('disabled', true);
			$('#btnAddDeviceSite').attr("disabled", true);
			$('#btnAddDeviceSite').children('span').eq(0).addClass('spinner-border spinner-border-sm');
		},
        data: formData,
        processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (result) {
            console.log(result);
            if(result.status === 200){
            	modalAddDeviceSite('hide');            	
            	alerts('success',result.message);
            	$('#inputSearch').val('');
            	if(elementExists('contentNoneData')){
					paging(pageEnd);
				}else if(!addNewItem()){					
					addItemSiteInfoForTable(siteId, districtId, result.content);
				}
            }else if(result.status === 401){
            	errorAction($('#quantityDevice'), result.message, false);
            }else{
            	modalAddDeviceSite('hide');
            
            	alerts('danger',result.message);
            }
            
            $('.btn-close-modal').removeAttr('disabled');
            $('#btnAddDeviceSite').removeAttr("disabled");				
			$('#btnAddDeviceSite').children('span').eq(0).removeClass('spinner-border spinner-border-sm');          
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alerts('danger','Thêm mới thất bại');
            modalAddDeviceSite('hide');
            $('.btn-close-modal').removeAttr('disabled');
            $('#btnAddDeviceSite').removeAttr("disabled");				
			$('#btnAddDeviceSite').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
        }
    });
}

function addItemSiteInfoForTable(siteId, districtId, siteInfo){
	$('#tableSiteInfo tbody').append('<tr class="site-info-item" data-id="'+siteInfo.id+'" data-device-id="'+siteInfo.device.id+'" data-device-name="'+siteInfo.device.name+'">'+
                                            '<th scope="row">'+siteInfo.id+'</th>'+
                                            '<td>'+checkCategory(siteInfo.device.id)+'</td>'+
                                            '<td class="site-info-device-name">'+siteInfo.device.name+'</td>'+
                                            '<td>'+siteInfo.quantity+'</td>'+
                                            '<td>'+siteInfo.serialNumber+'</td>'+
                                            '<td>'+siteInfo.slot+'</td>'+
                                            '<td id="'+siteInfo.id+'"><div class="row"></div></td>'+
                                            '<td>'+siteInfo.note+'</td>'+
                                            '<td class="text-center">'+
                                                '<button class="btn btn-warning btn-sm btn-edit-site-info" type="button" '+
                                                    //'data-bs-toggle="modal" data-bs-target="#editModalScrollable">'+
                                                        '><i class="bi bi-pencil-square"></i></button>'+
                                                '<button class="btn btn-danger btn-sm btn-delete-site-info" type="button"><i '+
                                                        'class="bi bi-trash3-fill"></i></button>'+
                                            '</td></tr>');
	if(!jQuery.isEmptyObject(siteInfo.siteImages)){
		siteImages = siteInfo.siteImages;
	    var i = 0;
	    $.each(siteImages, function(idx, img){
	    	$('#tableSiteInfo tbody td#'+siteInfo.id+' .row').append('<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
            			' data-slider="'+i+'" data-siteinfo-id="'+siteInfo.id+'" data-img-id="'+img.id+'" data-device-name="'+siteInfo.device.name+'" data-img-name="'+img.fileName+'">'+
                        	'<a data-target="#carouselExampleCaptions" data-bs-slide-to="'+i+'">'+
                            	//'<img class="img-fluid td-img" src="'+contextPath+'upload/images/'+districtId+'/'+siteId+'/'+img.fileName+'" alt="Image">'+
                            	'<img class="img-fluid td-img" src="'+contextPath+img.pathName+'/'+img.fileName100+'" alt="Image">'+                                                           
                            '</a></div>');
            i++
        });
	}
}

function updateSiteInfo(){
	url = contextPath + "api/siteinfo/update";
	
	siteInfoId = thisObjRow.parents('.site-info-item').attr('data-id');
	districtId = selectDistrict.find(":selected").val();
	//siteName = selectSite.find(":selected").text();
	siteId = selectSite.find(":selected").val();
	deviceId = thisObjRow.parents('.site-info-item').attr('data-device-id');
	deviceName = thisObjRow.parents('.site-info-item').attr('data-device-name');
	quantity = $('#editQuantityDevice').val().trim();
	serialNumber = $('#editSerialNumber').val().trim();
	slot = $('#editSlot').val().trim();
	note = $('#editNote').val().trim();
	
	var formData = new FormData();
	formData.append('id', siteInfoId);
	formData.append('districtId', districtId);
	formData.append('deviceId', deviceId);
	formData.append('deviceName', deviceName);
	formData.append('siteId', siteId);
	formData.append('quantity', quantity);
	formData.append('note', note);
	formData.append('serialNumber', serialNumber);
	formData.append('slot', slot);
	
	console.log($('#editImageDevice')[0].files);
	//if($('#editImageDevice')[0].files.length>0){
		$.each($('#editImageDevice')[0].files, function(i, file) {
		    formData.append('files', file);
		});
	//}else{
	//	var arr = [];
		//formData.append('files', arr);
	//}
	
	console.log(formData);
	
	
	$.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: url,
        beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
			
			$('.btn-close-edit-modal').attr("disabled", true);			
			$('.btn-edit-site-info').attr("disabled", true);
			$('.btn-edit-site-info').children('span').eq(0).addClass('spinner-border spinner-border-sm');
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
            	updateRowTable(districtId, siteId, result.content);          	
	            //reloadRowTable();
            }else if(result.status === 401){
            	errorAction($('#editQuantityDevice'), result.message, false);
            	
            	$('.btn-close-edit-modal').removeAttr("disabled");
            	$('.btn-edit-site-info').removeAttr("disabled");				
				$('.btn-edit-site-info').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
				return false;
            }else{
            	alerts('danger',result.message);
            }       
            $('#editModalScrollable').modal('hide');
            resetModelSiteInfoEdit();
        },
        error: function (e) {
            console.log("ERROR : ", e);
            alerts('danger','Cập nhật thất bại');
            resetModelSiteInfoEdit();
			$('#editModalScrollable').modal('hide');
        }
    });
    
}

function getFileImages(){
	var fi = $('#imageDevice');
	var fileImages = [];
	if(fi.get(0).files.length > 0){
		for (var i = 0; i <= fi.get(0).files.length - 1; i++) {
	
	    	var fname = fi.get(0).files[i].name;      // THE NAME OF THE FILE.
	        var fsize = fi.get(0).files[i].size;      // THE SIZE OF THE FILE.
			fileImages.push(fname);
	        
	    }
    }
     return fileImages;
}

function loadDeviceSiteCard(){
	districtId = selectDistrict.find(":selected").val();
	districtName = selectDistrict.find(":selected").text();
	siteName = selectSite.find(":selected").text();
	siteId = selectSite.find(":selected").val();
	
	$('#modalAddDeviceSiteTitle').text(districtName + " - " + siteName);
	
	
	url = contextPath + "api/device/site/list/"+siteId;
	
	$.get(url, function(responseJson){
		
		$('#modelBodyDevicesContent').html('');
		$.each(responseJson, function(index, device){
			
			$('#modelBodyDevicesContent').append('<div class="col-6">'+
                                        '<div class="form-check device-check">'+
                                            '<input class="form-check-input" type="radio" name="radioDevice" data-name="'+device.name+'" value="'+device.id+'" id="radio'+device.id+'">'+
                                            '<label class="form-check-label" for="radio'+device.id+'">'+device.name+'</label></div></div>');
		});		
		
		modalAddDeviceSite('show');
	}).fail(function(error){
		alert(error);
	}).done(function(){
		console.log("done");
	});
	
}


function setModelAddNewDevice(){
	$('#label_header_title').next().text("");
	$('#quantityDevice').val('1');
	errorAction($('#quantityDevice'), "", true);
	$('#serialNumber').text('');
	$('#slot').text('');
	$('#imageDevice').val('');
	$('#note').val('');
}

/* function check */


function checkSelectSite(select) {
	var value = select.find(":selected").val();
	
    if (value == 0) {
    	select.addClass("is-invalid");
        select.next().text("Chưa chọn Site");
        return false;
    } else {
    	select.removeClass("is-invalid");
        select.next().text("");
        return true;
    }
}

function checkChoiceDevice(){
	var $radios = $('input:radio[name=radioDevice]');
	if($radios.is(':checked') === false) {
		
		$('#label_header_title').addClass("is-invalid");
	   	$('#label_header_title').next().text("Chưa chọn thiết bị");
	   	return false;
	}else{
		$('#label_header_title').removeClass("is-invalid");
		$('#label_header_title').next().text("");
		return true;
	}
}

function checkCategory(deviceId){
	
	check = false;
	for(i =0; i<categorys.length;i++){
		
		devices = categorys[i].devices;
		for(j = 0; j < devices.length; j++){
			if(devices[j].id == deviceId){
				
				check = true;
				break;
			}
		}
		if(check)
			return categorys[i].name;
	}
	
	return "";
}

function isNumberic(input){
	var inputVal = input.val();
	var gfg = $.isNumeric(inputVal);
                  
	if (gfg) {    	
		if(inputVal < 1){			
			errorAction(input, "Cần nhập số lớn hơn 0", false);
        	return false;
		}
    }else {
		errorAction(input, "Cần nhập kiểu số", false);
        return false;
    }
	errorAction(input, "", true);
    return true;
}

function modalAddDeviceSite(status){
	$('#modalAddDeviceSite').modal(status);
	setModelAddNewDevice();
}

function resetModelShowImage(){
	$('#carouselExampleCaptions .carousel-indicators').html('');
	$('#carouselExampleCaptions .carousel-inner').html('');
}

function resetModelSiteInfoEdit(){
	$('#eidtDeviceName').val('');
	$('#editQuantityDevice').val('');
	errorAction($('#editQuantityDevice'), "", true);
	$('#editSerialNumber').val('');
	$('#editImageDevice').val('');
	$('#editSlot').val('');
	$('#editNote').val('');
	$('#editModalScrollable .row-container-device-image').html('');
	
	$('.btn-close-edit-modal').removeAttr("disabled");
	$('.btn-edit-site-info').removeAttr("disabled");
	//$('.btn-edit-site-info').text('Cập nhật');
	$('.btn-edit-site-info').children('span').eq(0).removeClass('spinner-border spinner-border-sm');
}

function addNewItem(){
	var countRowTable = $('#tableSiteInfo tbody').find('tr').length;
	pageSize = $('#selectPageItem').find(':selected').val();
	if(countRowTable >= pageSize){
		paging(pageEnd);
	}
	return false;
}
