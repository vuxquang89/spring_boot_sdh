/**
 * 
 */

var btnOpenModalAddDistrict,btnAddDistrict, btnEditDistrict;
var btnAddSite, btnAddFast;
var fieldDistrictName, fieldSiteName;
var thisObjCard, thisObjCardOfSite;
var cardDistrict;
var siteID, siteName;

$(document).ready(function(){
	btnOpenModalAddDistrict = $('#btnOpenModalAddDistrict'); 
	btnAddDistrict = $('#btnAddDistrict');
	btnEditDistrict = $('#btnEditDistrict');
	btnAddSite = $('#btnAddSite');
	btnAddFast = $('#btnAddFast');
	fieldDistrictName = $('#districtEditName');
	cardDistrict = $('#cardDistrict');
	
	btnOpenModalAddDistrict.click(function(){
		$('#staticBackdrop').modal('show');
	});
	
	btnAddDistrict.click(function(){
		fieldDistrictName = $('#districtName');
		if(checkInput(fieldDistrictName, "tên khu vực")){
			addDistrict();
		}
	});
	
	btnEditDistrict.click(function(){
		fieldDistrictName = $('#districtEditName');
		if(checkInput(fieldDistrictName, "tên khu vực")){
			editDistrict();
		}
	});
	
	$('body').on('click','.btn-add-site', function(){
		//var parent_id = $(this).parent().parent().parent().parent().parent().attr('data-id');
		thisObjCard = $(this).parents('.card-district');
		fieldSiteName = $('#inputSiteName');
	});
	
	$('body').on('click', '.btn-delete-district', deleteDistrict);
	
	$('.btn-close-modal').click(function(){
		setOffLoadDevice();
		$('#siteContent').html('');
		errorAction($('#inputSiteName'), "", true);
		loadDistrict();
		$('#staticAddSite').modal('toggle');
	});
	
	$('.btn-close-modal-add-district').click(function(){
		fieldDistrictName.val('');
		errorAction(fieldDistrictName, "", true);
		$('#staticBackdrop').modal('toggle');
		
	});
	
	$('.btn-close-modal-edit-district').click(function(){
		fieldDistrictName.val('');
		errorAction(fieldDistrictName, "", true);
		$('#staticEditDistrict').modal('toggle');
		
	});
	
	btnAddSite.click(function(){
		addSite();
	});
	
	$('#btnCancelSelectDevice').click(function(){
		$( ".checkAll").prop('checked', false);
		setOffLoadDevice();
	});
	
	/*
	$('input[type=checkbox][name=checkbox-device]').change(function() {
	    if ($(this).is(':checked')) {
	        $('#btnAddDeviceSite').attr("disabled", true);
	    }else{
	    	$('#btnAddDeviceSite').attr("disabled", false);
	    }
	});
	*/
	
	$('#btnAddDeviceSite').click(addDeviceSite);
	
	$('#staticAddSite').on('click', '.btn-delete-site', function(){
		cardSite = $(this).parents('.card-site');
		siteID = cardSite.attr('data-id');
		siteName = cardSite.attr('data-name');
		deleteSite(siteID, siteName);
		setOffLoadDevice();
	});
	
	$('#staticAddSite').on('click', '.btn-edit-load-device', function(){
		setOffLoadDevice()
		cardSite = $(this).parents('.card-site');
		siteID = cardSite.attr('data-id');
		siteName = cardSite.attr('data-name');
		loadDeviceSiteCard(siteID, siteName);
	});
	
	$('#btnEditSite').on('click', function(){
		editSite();
	});
	
	cardDistrict.on('click', '.btn-delete-site', function(){
		cardSite = $(this).parents('.card-body-item');
		siteID = cardSite.attr('data-id');
		siteName = cardSite.attr('data-name');
		
		deleteSite(siteID, siteName);
	});
	
	cardDistrict.on('click', '.btn-add-device-site', function(){
		thisObjCardOfSite = $(this).parents('.card-body-item');
		
		loadModalAddDeviceForSite();
	});
	
	cardDistrict.on('click', '.btn-edit-site', function(){
		thisObjCardOfSite = $(this).parents('.card-body-item');
		siteName = thisObjCardOfSite.attr('data-name');
		fieldSiteName = $('#siteEditName');
		fieldSiteName.val(siteName)
	});
	
	
	$('.btn-close-modal-add-device').click(function(){
		$('#staticAddDeviceSiteLabel').text(''); 
		$('#cardAddDevices').html('');
		$('#staticAddDeviceSite').modal('toggle');
		$( ".checkAll").prop('checked', false);
	});
	
	$('.btn-close-modal-edit-site').click(function(){
		fieldSiteName.val('');
		errorAction(fieldSiteName, "", true);
		$('#staticEditSite').modal('toggle');
	});
	
	
	cardDistrict.on('click', '.btn-edit-district', function(){
		thisObjCard = $(this).parents('.card-district');
		districtID = thisObjCard.attr('data-id');
		districtName = thisObjCard.find('.lbl-district-name').text();
		$('#districtEditName').val(districtName);
		
	});
	
	$(".checkAll").click(function () {
	    $('input:checkbox').not(this).prop('checked', this.checked);
	});
	
	$(".inputKeySearchDevice").keyup(function(){
		$('.device-check').hide();
	    var key = $(this).val().trim();
	    $('.device-check').each(function(){
	       if($(this).text().toUpperCase().indexOf(key.toUpperCase()) != -1){
	           $(this).show();
	           $('.div-box-check-all').show();
	       	   $('.div-box-add-fast').hide();
	       	   $('.div-box-select-category').hide(400);
	       	   $('#btnAddDeviceSite').attr("disabled", false);
	       	   
	       }else{
	           btnAddFast.val("Thêm nhanh");
	           btnAddFast.attr('title', 'Thêm nhanh thiết bị vào danh mục');
	       	   $('.div-box-check-all').hide();
	       	   $('.div-box-add-fast').show();
	       	   $('#btnAddDeviceSite').attr("disabled", true);
	       }
	    });
	});
	
	btnAddFast.click(function(){
		if(btnAddFast.val() == "OK"){
			btnAddFast.val("Thêm nhanh");
			btnAddFast.attr('title', 'Thêm nhanh thiết bị vào danh mục');
			$('.div-box-select-category').hide(400);
			$('.div-box-check-all').show();
	       	$('.div-box-add-fast').hide();
	       	//add new device
	       	addFastNewDevice();
	       	
		}else{			
			btnAddFast.val("OK");
			btnAddFast.attr('title', 'Thêm thiết bị vào danh mục');
			
			//load list category
			loadListCategory();
		}
	});
	
	loadDistrict();
});

function loadListCategory(){
	selectCategory = $('#selectCategory');
	
	url = contextPath + "api/category/list";
	
	$.get(url, function(responseJson){
		
		selectCategory.html('');
		$.each(responseJson, function(index, category){
			selectCategory.append($('<option/>', { 
		        value: category.id,
		        text : category.name 
		    }));
		});
		$('.div-box-select-category').show(500);
	}).fail(function(error){
		alert("failed");
		$('.div-box-select-category').show(500);
	}).done(function(){
		console.log("done");
	});
}

function addFastNewDevice(){
	selectCategory = $('#selectCategory');
	fieldDeviceName = $('.inputKeySearchDevice');
	url = contextPath + "api/device/save";
	
	deviceName = fieldDeviceName.val().trim();
	categoryId = selectCategory.find(":selected").val();
	categoryName = selectCategory.find(":selected").text();
	
	jsonData = {name : deviceName, category: {id: categoryId, name: categoryName}};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(device){
		alerts('success', 'Thêm mới thành công');
		$('#cardDevices').append('<div class="col-6">'+
                            '<div class="form-check device-check">'+
                            	'<input type="checkbox" class="form-check-input check-box-device" id="check'+device.id+'" name="checkbox-device" data-name="'+device.name+'" value="'+device.id+'">'+
                                '<label class="form-check-label" for="check'+device.id+'">'+device.name+'</label></div></div>');
		$('.div-box-select-category').hide(400);
		$('.div-box-check-all').show();		
		$('.div-box-add-fast').hide();
		$('#btnAddDeviceSite').attr("disabled", false);
	}).fail(function(error){
			
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldDeviceName, mess, false);
		}else{
			
			alertDanger("Thêm mới thất bại");
		}
	});
}

function addDistrict(){
	url = contextPath + "api/district/save";
	
	districtName = fieldDistrictName.val();
	jsonData = {name : districtName};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(district){
		
		$('#staticBackdrop').modal('toggle');
		alerts('success', 'Thêm mới thành công');
		fieldDistrictName.val("");
		
		addElementDistrict(district.id, district.name);
		errorAction(fieldDistrictName, "", true);
	}).fail(function(error){
		
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldDistrictName, mess, false);
		}else{
			$('#staticBackdrop').modal('toggle');
			alerts('danger','Thêm mới thất bại');
		}
		
	});
}

function editDistrict(){
	
	url = contextPath + "api/district/save";
	
	districtName = fieldDistrictName.val();
	districtID = thisObjCard.attr('data-id');
	jsonData = {id: districtID, name : districtName};

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(district){
		$('#staticEditDistrict').modal('toggle');
		alerts('success', 'Cập nhật thành công');
		thisObjCard.find('.lbl-district-name').text($('#districtEditName').val());
		$('#districtEditName').val("");
		errorAction(fieldDistrictName, "", true);
	}).fail(function(error){
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldDistrictName, mess, false);
		}else{
			$('#staticEditDistrict').modal('toggle');
			alerts('danger','Cập nhật thất bại');
		}
		
	});
}

function deleteDistrict(){
	thisObjCard = $(this).parents('.card-district');
	
	var districtId = thisObjCard.attr('data-id');
	var districtName = thisObjCard.attr('data-name');
	
	url = contextPath + "api/district/delete/"+districtId;
		
	if (confirm('Bạn có muốn xóa '+ districtName +' ?')) {
		$.get(url, function(){
		
		}).fail(function(error){
			alerts('danger','Không thể xóa');
		}).done(function(){
			alerts('success','Xóa thành công');
			thisObjCard.remove();
			
		})	
	}
}

function addSite(){
	url = contextPath + "api/site/save";
	
	siteName = fieldSiteName.val();
	districtId = thisObjCard.attr('data-id');
	districtName = thisObjCard.attr('data-name');
	
	$('#staticAddSiteLabel').text(districtName + " - Thêm Site")
	
	jsonData = {name : siteName, district:{id : districtId, name : districtName}};
	console.log("addSite", jsonData);
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(result){
		
		console.log(result);
		if(result.status === 200){
			site = result.site;
			alerts('success',result.message);
			siteID = site.id;
			$('#siteContent').append('<div id="'+siteID+'" class="row border-bottom me-0 card-site" data-id="'+siteID+'" data-name="'+siteName+'">'+
	                                        '<div class="col-md-9">'+
	                                            '<label class="lblSiteName pt-1 ps-2">'+siteName+'</label>'+
	                                        '</div>'+
	                                        '<div class="col-md-3">'+
	                                            '<button title="Thêm thiết bị cho Site" class="btn btn-outsite btn-sm text-warning btn-edit-load-device" type="button"><i class="bi bi-pencil-fill"></i></button>\n'+                                            
	                                        	'<button title="Xóa site" class="btn btn-outsite btn-sm text-danger btn-delete-site" type="button"><i class="bi bi-trash3-fill"></i></button></div></div>');
			
			loadDeviceSiteCard(siteID, siteName);
			//$('body').removeClass("loading");
			fieldSiteName.val("");
			errorAction(fieldSiteName, "", true);
		}else{
			errorAction(fieldSiteName, result.message, false);
		}
		
	}).fail(function(error){
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldSiteName, mess, false);
		}else{
			$('#staticBackdrop').modal('toggle');
			alerts('danger','Thêm mới thất bại');
		}
	});
}

function editSite(){
	url = contextPath + "api/site/save";
	
	siteId = thisObjCardOfSite.attr('data-id');
	siteName = fieldSiteName.val();
	thisObjCard = thisObjCardOfSite.parents('.card-district');
	districtName = thisObjCard.attr('data-name');
	districtId = thisObjCard.attr('data-id');
	
	jsonData = {id: siteId, name : siteName, district:{id : districtId, name : districtName}};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(result){
		
		console.log(result);
		if(result.status === 200){
			$('#staticEditSite').modal('toggle');
			site = result.site;
			alerts('success',result.message);
			thisObjCard.find('#'+site.id+' .lblSiteName').text(site.name);
			thisObjCardOfSite.attr('data-name', site.name);
			fieldSiteName.val("");
			errorAction(fieldSiteName, "", true);
		}else{
			errorAction(fieldSiteName, result.message, false);
		}
		
	}).fail(function(error){
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldSiteName, mess, false);
		}else{
			$('#staticBackdrop').modal('toggle');
			alerts('danger','Cập nhật thất bại');
		}
	});
}

function deleteSite(siteID, siteName){
	
	url = contextPath + "api/site/delete/"+siteID;
	if (confirm('Bạn có muốn xóa Site - ' + siteName + '?')) {
		$.get(url, function(){
		
		}).fail(function(error){
			alerts('danger','Không thể xóa');
		}).done(function(){
			alerts('success','Xóa thành công');	
			$('#'+siteID).remove();		
		});	
	}
}

function addDeviceSite(){
	url = contextPath + "api/siteinfo/device/save";
	
	var arrayId = []; 
	$.each($("input[type='checkbox']:checked"),function(){
		var deviceId;
	   	deviceId = $(this).val();
		
		if(deviceId != ""){
			arrayId.push(deviceId);
		}
		/*
		deviceName = $(this).attr('data-name');
	   	devices[deviceId] = deviceName;
		*/
	});
	//jsonData = {site:{id: siteID, name: siteName}, devicesId : arrayId};
	//jsonData = {site:{id: siteID, name: siteName}, device : {id: "1", name: "ODF"}};
	
	if(arrayId.length == 0){
		alerts('danger','Chưa chọn thiết bị');
		return false;
	}
	jsonData = {siteId: siteID, devicesId : arrayId};
	//console.log(jsonData);
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(status){
		alerts('success','Thêm thiết bị thành công');
		setOffLoadDevice();
	}).fail(function(){
		alerts("Thêm thiết bị thất bại");
	});
}

function addElementDistrict(districtId, districtName){
	cardDistrict.append('<div class="col-md-4 mb-4 card-district" data-id="'+districtId+'" data-name="'+districtName+'">'+
                    '<div class="card h-100">'+
                        '<div class="card-header">'+
                            '<div class="row">'+
                                '<label class="col-md-7 lbl-district-name">'+districtName+'</label>'+
                                '<div class="col-md-5">'+
                                    '<button class="btn btn-outline-primary btn-sm fw-bold btn-add-site" type="button"'+
                                        'data-toggle="modal" title="Thêm site cho khu vực" data-target="#staticAddSite">+</button>\n'+
                                    '<button class="btn btn-outline-warning btn-sm text-warning btn-edit-district" title="Sửa tên khu vực" '+
	                                        'data-toggle="modal" data-target="#staticEditDistrict" type="button"><i class="bi bi-pencil-fill"></i></button>\n'+
                                '</div></div></div>'+
                        '<div class="card-body">'+
                        	'<div class="pb-2 border-bottom input-group input-group-sm"><input id="inputItem'+districtId+'" class="form-control" type="text" onkeyup="searchItem('+districtId+')"></div>'+
                            '<div id="'+districtId+'" class="overflow-auto card-body-content" style="max-width: 370px; max-height: 200px;"></div></div></div></div>');

	
}

function loadModalAddDeviceForSite(){
	siteID = thisObjCardOfSite.attr('data-id');
	siteName = thisObjCardOfSite.attr('data-name');
	
	districtName = thisObjCardOfSite.parents('.card-district').attr('data-name');
	
	$('#staticAddDeviceSiteLabel').text(districtName+" : Thêm thiết bị cho site - "+siteName); 
	$('#cardAddDevices').html('');
	
	url = contextPath + "api/device/site/list/"+siteID;
	
	$.get(url, function(responseJson){
		$.each(responseJson, function(index, device){
		
		$('#cardAddDevices').append('<div class="col-6">'+
                            '<div class="form-check device-check">'+
                            	'<input type="checkbox" class="form-check-input" id="check'+device.id+'" name="'+device.name+'" value="'+device.id+'">'+
                                '<label class="form-check-label" for="check'+device.id+'">'+device.name+'</label></div></div>');
		});
		$('#boxCardAddDevices').show(500);
		$('#staticAddDeviceSite').modal('show');
	}).fail(function(error){
		alert("failed");
		$('#staticAddDeviceSite').modal('hide');
	}).done(function(){
		console.log("done");
	});
	
}

function loadDeviceSiteCard(siteId, siteName){
	$('#titleDeviceSite').text("Chọn thiết bị cho Site - " + siteName);
	$('#inputSiteName').attr("disabled", true)
	$('#btnAddSite').attr("disabled", true)
	$('#cardDevices').html('');
	
	url = contextPath + "api/device/site/list/"+siteId;
	
	$.get(url, function(responseJson){
		$.each(responseJson, function(index, device){
		
			$('#cardDevices').append('<div class="col-6">'+
                            '<div class="form-check device-check">'+
                            	'<input type="checkbox" class="form-check-input check-box-device" id="check'+device.id+'" name="checkbox-device" data-name="'+device.name+'" value="'+device.id+'">'+
                                '<label class="form-check-label" for="check'+device.id+'">'+device.name+'</label></div></div>');
		});
		$('#boxCardDevices').show(500);
	}).fail(function(error){
		alert("failed");
	}).done(function(){
		console.log("done");
	});
	
}

function loadDistrict(){
	var onload = 'all';
	if(thisObjCard == null){
		url = contextPath + "api/district/list/all";
		
	}else{
		url = contextPath + "api/district/list/"+thisObjCard.attr('data-id');
		
		onload = 'item';
	}
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log("load district", responseJson);
		$.each(responseJson, function(index, district){
			if(onload === 'all'){
				cardDistrict.append('<div class="col-md-4 mb-4 card-district" data-id="'+district.id+'" data-name="'+district.name+'">'+
	                    '<div class="card h-100">'+
	                        '<div class="card-header">'+
	                            '<div class="row">'+
	                                '<label class="col-md-7 lbl-district-name">'+district.name+'</label>'+
	                                '<div class="col-md-5">'+
	                                    '<button class="btn btn-outline-primary btn-sm fw-bold btn-add-site" type="button"'+
	                                        'data-toggle="modal" title="Thêm site cho khu vực" data-target="#staticAddSite">+</button>\n'+
	                                    '<button class="btn btn-outline-warning btn-sm text-warning btn-edit-district" title="Sửa tên khu vực" '+
	                                        'data-toggle="modal" data-target="#staticEditDistrict" type="button"><i class="bi bi-pencil-fill"></i></button>\n'+
	                                    
	                                '</div></div></div>'+
	                        '<div class="card-body">'+
	                        	'<div class="pb-2 border-bottom input-group input-group-sm"><input id="inputItem'+district.id+'" class="form-control" type="text" onkeyup="searchItem('+district.id+')"></div>'+
	                            '<div id="site-content-'+district.id+'" class="overflow-auto card-body-content" style="max-width: 370px; max-height: 200px;"></div></div></div></div>');
				
					$.each(district.sites, function(i, site){
						cardDistrict.find('#site-content-'+district.id).append('<div id="'+site.id+'" data-id="'+site.id+'" data-name="'+site.name+'" class="row border-bottom me-0 card-body-item body-item-'+district.id+'">'+
	                                    '<div class="col-md-7">'+
	                                        '<label class="lblSiteName pt-1 ps-2">'+site.name+'</label>'+
	                                    '</div>'+
	                                    '<div class="col-md-5 pe-0">'+
	                                        '<button title="Thêm thiết bị cho Site" class="btn btn-outsite btn-sm text-primary btn-add-device-site" type="button"><i class="bi bi-plus-square"></i></button>'+
	                                        '<button title="Sửa tên Site" class="btn btn-outsite btn-sm text-warning btn-edit-site" data-toggle="modal" data-target="#staticEditSite" type="button"><i class="bi bi-pencil-fill"></i></button>'+
	                                    	'</div></div>');
					});
				}else{
					cardDistrict.find('#site-content-'+district.id).html('');
					$.each(district.sites, function(i, site){
						cardDistrict.find('#site-content-'+district.id).append('<div id="'+site.id+'" data-id="'+site.id+'" data-name="'+site.name+'" class="row border-bottom me-0 card-body-item body-item-'+district.id+'">'+
	                                    '<div class="col-md-7">'+
	                                        '<label class="lblSiteName pt-1 ps-2">'+site.name+'</label>'+
	                                    '</div>'+
	                                    '<div class="col-md-5 pe-0">'+
	                                        '<button title="Thêm thiết bị cho Site" class="btn btn-outsite btn-sm text-primary btn-add-device-site" type="button"><i class="bi bi-plus-square"></i></button>'+
	                                        '<button title="Sửa tên Site" class="btn btn-outsite btn-sm text-warning btn-edit-site" data-toggle="modal" data-target="#staticEditSite" type="button"><i class="bi bi-pencil-fill"></i></button>'+
	                                    	'</div></div>');
					});
				}
		});
		
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log(error);
	});
	
}

function searchItem(districtId){	
	// Search text
	var text = $('#inputItem'+districtId).val();
	
	// Hide all content class element
	$('.body-item-'+districtId).hide();
	/*
	$('.contact-name').each(function(){
	   if($(this).text().toUpperCase().indexOf(txt.toUpperCase()) != -1){
	       $(this).show();
	   }
	});
    */
	// Search and show
	$('.body-item-'+districtId+':contains("'+text+'")').show();
}

function setOnLoadDevice(){
	
}

function checkInput(element, name){
	var value = element.val().trim();
	
    if (value == "") {
        errorAction(element, "Chưa nhập " + name, false);
        return false;
    } else {
        errorAction(element, "", true);
        return true;
    }
}

function setOffLoadDevice(){
	$('#boxCardDevices').hide(500);
	$('#inputSiteName').removeAttr("disabled");
	$('#btnAddSite').removeAttr("disabled");
	$('#btnAddDeviceSite').removeAttr("disabled");
	$('#cardDevices').html("");
	$('.inputKeySearchDevice').val("");
	$('.div-box-select-category').hide(400);
	$('.div-box-check-all').show();
	$('.div-box-add-fast').hide();
}