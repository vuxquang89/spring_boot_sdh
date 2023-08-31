var btnAddDevice;
var btnEditDevice;
var btnSearch, btnEraser;
var btnReset;
var selCategory;
var tblDevices;
var fieldDeviceName;
var thisObj;

$(document).ready(function(){
	fieldDeviceName = $('#inputDeviceName');
	
	btnAddDevice = $('#btnAddDevice');
	btnReset = $('#btnReset');
	btnSearch = $('#btnSearch');
	btnEraser = $('#btnEraser');
	selCategory = $('#selectCategory');
	
	selCategory.on('change', function() {
		selCategory.removeClass("is-invalid");
        selCategory.next().text("");
		$("#tableDevices tbody").empty();
		//loadDeviceByCategory();
		paging(1);
	});
	
	$('#selectPageItem').on('change', function(){
		paging(1);
	});
	
	btnAddDevice.click(function(){
		$('#inputSearch').val('');
		if(checkInputDevice(fieldDeviceName) && checkSelectCategory(selCategory)){
			addDevice();
			resetTable(thisObj);	
		}
	});
	
	btnReset.click(function(){
		fieldDeviceName.val("");
	});
	
	btnSearch.click(function(){
		paging(1);
	})
	
	btnEraser.click(function(){
		$('#inputSearch').val('');
		paging(1);
	});
	
	$('#tableDevices').on('click', '.btn-edit', function(){
		resetTable(thisObj);  
        var name = $(this).parents("tr").attr('data-name');  
		
		$(this).parents("tr").find("td:eq(0)").html('<input name="edit_name" value="'+name+'">');  
        
        $(this).parents("tr").find("td:eq(2)").prepend("<button class='btn btn-info btn-xs btn-sm btn-update'>Update</button>\n<button class='btn btn-warning btn-xs btn-sm btn-cancel'>Cancel</button>")  
        $(this).hide(); 
		$(this).parents("tr").find(".btn-delete").hide();
		thisObj = $(this);
	});
	
	$("#tableDevices").on("click", ".btn-cancel", function(){    
        var name = $(this).parents("tr").attr('data-name');  
      
        $(this).parents("tr").find("td:eq(0)").text(name);  
     
        $(this).parents("tr").find(".btn-edit").show();  
		$(this).parents("tr").find(".btn-delete").show(); 
        $(this).parents("tr").find(".btn-update").remove();  
        $(this).parents("tr").find(".btn-cancel").remove();  
    });

	$("#tableDevices").on("click", ".btn-delete", deleteDevice); 
	$("#tableDevices").on("click", ".btn-update", updateDevice);
});

function paging(currentPage){
	pageSize = $('#selectPageItem').find(':selected').val();
	categoryId = selCategory.find(":selected").val();
	categoryName = selCategory.find(":selected").text();
	pagingDevice(categoryId, categoryName, pageSize, currentPage);
}

function loadDeviceByCategory(){
	categoryId = selCategory.find(":selected").val();
	categoryName = selCategory.find(":selected").text();
	
	url = contextPath + "api/device/list/"+categoryId;
	
	
	$.get(url, function(responseJson){
		//console.log(responseJson);
		$.each(responseJson, function(index, device){
			
			$('#tableDevices tbody').append("<tr data-id='"+device.id+"' data-name='"+device.name+"'>"+
				  "<th scope='row'>"+device.id+"</th><td>"+device.name+"</td>"+
                  "<td>"+categoryName+"</td>"+	
                  "<td class='text-center'>"+
					"<button class='btn btn-warning btn-sm btn-edit' type='button'>"+
						"<i class='bi bi-pencil-square'></i></button>\n"+
					"<button class='btn btn-danger btn-sm btn-delete' type='button'>"+                     		
						"<i class='bi bi-trash3-fill'></i></button>"+
            "</td></tr>");
			
		});
		
	}).fail(function(error){
		//alert("failed");
	}).done(function(){
		//alert("done");
	})
}

function addDevice(){
	url = contextPath + "api/device/save";
	deviceName = fieldDeviceName.val().trim();
	categoryId = selCategory.find(":selected").val();
	categoryName = selCategory.find(":selected").text();
	
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
		alertSuccess("Thêm mới thành công");
		if(elementExists('contentNoneData')){
			paging(pageEnd);
		}else if(!addNewItem()){
			tableNewRowAddDevice(device.id, device.name, categoryName);
		}
		fieldDeviceName.val('');
		
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

function deleteDevice(){
	thisObj = $(this);
	var deviceId = $(this).parents("tr").attr('data-id');
	var deviceName = $(this).parents("tr").attr('data-name');
	
	url = contextPath + "api/device/delete/"+deviceId;
	
	if (confirm('Bạn có muốn xóa ' + deviceName + ' ?')) {
		
		$.get(url, function(){
		
		}).fail(function(error){
			alertDanger("Không thể xóa");
		}).done(function(mess){
			alertSuccess("Xóa thành công");
			thisObj.parents("tr").remove(); 
			reloadTable();
		})
		
	}
}

function updateDevice(){
	url = contextPath + "api/device/save";
	
	var deviceId = thisObj.parents("tr").attr('data-id');          
    var deviceName = thisObj.parents("tr").find("input[name='edit_name']").val().trim();     

	categoryId = selCategory.find(":selected").val();
	categoryName = selCategory.find(":selected").text();
		
	jsonData = {id: deviceId, name : deviceName, category: {id: categoryId, name: categoryName}};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(deviceId){
		alertSuccess("Cập nhật thành công");
		thisObj.parents("tr").find("td:eq(0)").text(deviceName);         
    	thisObj.parents("tr").attr('data-name', deviceName);
    	
    	resetBtn(thisObj);
	}).fail(function(error){
		if(error.status === 400){
			result = error.responseJSON;
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			alertDanger(mess);
		}else{
			alertDanger("Cập nhật thất bại");
			deviceName = thisObj.parents("tr").attr('data-name');
			thisObj.parents("tr").find("td:eq(0)").text(deviceName);
			resetBtn(thisObj);
		}
		
		
	});
}

//them dong moi vao table
function tableNewRowAddDevice(deviceId, deviceName, categoryName){
	$('#tableDevices tbody').append("<tr data-id='"+deviceId+"' data-name='"+deviceName+"'>"+
				  "<th scope='row'>"+deviceId+"</th><td>"+deviceName+"</td>"+
                  "<td>"+categoryName+"</td>"+	
                  "<td class='text-center'>"+
					"<button class='btn btn-warning btn-sm btn-edit' type='button'>"+
						"<i class='bi bi-pencil-square'></i></button>\n"+
					"<button class='btn btn-danger btn-sm btn-delete' type='button'>"+                     		
						"<i class='bi bi-trash3-fill'></i></button>"+
            "</td></tr>");
	fieldDeviceName.val("");
}

//check selection
function checkSelectCategory(select) {
	var value = select.find(":selected").val();
	//console.log(value);
    if (value == 0) {
    	select.addClass("is-invalid");
        select.next().text("Chưa chọn danh mục");
        return false;
    } else {
    	select.removeClass("is-invalid");
        select.next().text("");
        return true;
    }
}

function checkInputDevice(input){
	var value = input.val().trim();
	
    if (value == "") {
        errorAction(input, "Chưa nhập tên thiết bị", false);
        return false;
    } else {
        errorAction(input, "", true);
        return true;
    }
}

function resetTable(thisObj){
	if(thisObj != null){
		var name = thisObj.parents("tr").attr('data-name');
		thisObj.parents("tr").find("td:eq(0)").text(name);         
	    
		resetBtn(thisObj);
	}
	 
}

function addNewItem(){
	var countRowTable = $('#tableDevices tbody').find('tr').length;
	pageSize = $('#selectPageItem').find(':selected').val();
	if(countRowTable >= pageSize){
		console.log(pageEnd);
		paging(pageEnd);
	}
	return false;
}

function reloadTable(){
	var countRowTable = $('#tableDevices tbody').find('tr').length;
	if(countRowTable == 0){
		paging(1);
	}
}

function resetBtn(thisObj){
	thisObj.parents("tr").find(".btn-edit").show();  
	thisObj.parents("tr").find(".btn-delete").show(); 
    thisObj.parents("tr").find(".btn-cancel").remove();  
    thisObj.parents("tr").find(".btn-update").remove();
}