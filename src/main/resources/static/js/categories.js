var btnAddCategory;
var btnEditCategory;
var btnReset;
var btnSearch, btnEraser;
var tblCategorys;
var fieldCategoryName;
var thisObj;

$(document).ready(function(){
	btnAddCategory = $("#btnAddCategory");
	btnEditCategory = $('.btnEditCategory');
	btnReset = $('#btnReset');
	btnSearch = $('#btnSearch');
	btnEraser = $('#btnEraser');
	tblCategorys = $('#tableCategorys'); 
	fieldCategoryName = $('#category_name');
	
	btnAddCategory.click(function(){
		$('#inputSearch').val('');
		if(checkInputCategory(fieldCategoryName)){
			addCategory();
			resetTable(thisObj);
		}
		
	});
	
	btnReset.click(function(){
		fieldCategoryName.val("");
	});
	
	btnSearch.click(function(){
		paging(1);
	});
	
	btnEraser.click(function(){
		$('#inputSearch').val('');
		paging(1);
	});
	
	$('#selectPageItem').on('change', function(){
		paging(1);
	});
	
	$('#tableCategorys').on('click', '.btn-edit', function(){
		resetTable(thisObj);  
        var name = $(this).parents("tr").attr('data-name');  
		
		$(this).parents("tr").find("td:eq(0)").html('<input name="edit_name" value="'+name+'">');  
        
        $(this).parents("tr").find("td:eq(1)").prepend("<button class='btn btn-info btn-xs btn-sm btn-update'>Update</button>\n<button class='btn btn-warning btn-xs btn-sm btn-cancel'>Cancel</button>")  
        $(this).hide(); 
		$(this).parents("tr").find(".btn-delete").hide();
		thisObj = $(this);
	});
	
	$("#tableCategorys").on("click", ".btn-delete", deleteCategory);  

	$("#tableCategorys").on("click", ".btn-cancel", function(){    
        var name = $(this).parents("tr").attr('data-name');  
      
        $(this).parents("tr").find("td:eq(0)").text(name);  
     
        resetBtn(thisObj); 
    });

	$("#tableCategorys").on("click", ".btn-update", updateCategory); 
	
	paging(1);   
});

function addCategory(){
	url = contextPath + "api/category/save";
	categoryName = fieldCategoryName.val();
	jsonData = {name : categoryName};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(category){		
		alertSuccess("Thêm mới thành công");
		if(elementExists('contentNoneData')){
			paging(pageEnd);
		}else if(!addNewItem()){
			tableNewRowAddCategory(category.id, category.name);
		}
		
		
		errorAction(fieldCategoryName, "", true);
	}).fail(function(error){
		console.log(error);		
		if(error.status === 400){
			result = error.responseJSON;
			
			if(result.status !== undefined){
				mess = result.message;
			}else{
				mess = result.name;
			}
			errorAction(fieldCategoryName, mess, false);
		}else{
			
			alertDanger("Thêm mới thất bại");
		}
		
	});
}

function deleteCategory(){
	thisObj = $(this);
	var categoryId = $(this).parents("tr").attr('data-id');
	var categoryName = $(this).parents("tr").attr('data-name');
	
	if (confirm('Bạn có muốn xóa ' + categoryName + ' ?')) {
		
		url = contextPath + "api/category/delete/"+categoryId;
		$.get(url, function(){
		
		}).fail(function(error){
			alertDanger("Không thể xóa");
			console.log(error);
		}).done(function(resp){
			console.log(resp);
			alertSuccess("Xóa thành công");
			thisObj.parents("tr").remove(); 
			
			reloadTable();
		})
		
	}
}

function updateCategory(){
	
	var categoryId = thisObj.parents("tr").attr('data-id');          
    var categoryName = thisObj.parents("tr").find("input[name='edit_name']").val();  
	url = contextPath + "api/category/save";
	
	jsonData = {id: categoryId, name : categoryName};
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		
		data: JSON.stringify(jsonData),
		
		contentType: 'application/json'
	}).done(function(res){		
		alertSuccess("Cập nhật thành công");
		thisObj.parents("tr").find("td:eq(0)").text(categoryName);        
    	thisObj.parents("tr").attr('data-name', categoryName);
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
			categoryName = thisObj.parents("tr").attr('data-name');
			thisObj.parents("tr").find("td:eq(0)").text(categoryName);
			resetBtn(thisObj);
		}
	});
}

function paging(currentPage){
	
	pageSize = $('#selectPageItem').find(':selected').val();
	
	pagingCategory(pageSize, currentPage);
}

function loadCategorys(){
	//url = "https://jsonplaceholder.typicode.com/users";
	url = contextPath + "api/category/list";
	
	
	$.get(url, function(responseJson){
		
		$.each(responseJson, function(index, category){
			//console.log(category.name);
			$('#tableCategorys tbody').append("<tr data-id='"+category.id+"' data-name='"+category.name+"'><th scope='row'></th><td>"+category.name+"</td>"+
                  "<td class='text-center'>"+
					"<button class='btn btn-warning btn-sm btn-edit' type='button'>"+
						"<i class='bi bi-pencil-square'></i></button>"+
					"<button class='btn btn-danger btn-sm btn-delete' type='button'>"+                     		
						"<i class='bi bi-trash3-fill'></i></button>"+
            "</td></tr>");
			//$(".data-table tbody").append("<tr data-name='"+name+"' data-email='"+email+"'><td>"+name+"</td><td>"+email+"</td>
			//<td><button class='btn btn-info btn-xs btn-edit'>Edit</button><button class='btn btn-danger btn-xs btn-delete'>Delete</button></td></tr>");
		});
		
	}).fail(function(error){
		//alert("failed");
	}).done(function(){
		//alert("done");
	})
	/*
	$.ajax({
		type: "GET",
        //url: "https://jsonplaceholder.typicode.com/users",
		url: url,
        dataType: "json",
        success: function (result, status, xhr) {
        	console.log('result', result );
			/*	
            result.forEach(element => {
            	$('#content').append("<tr><td>"+element.id+"</td><td>"+element.username+"</td><td>"+element.name+"</td><td>"+element.company.name+"</td></tr>");
            });
			*/
			/*
        },
        error: function (xhr, status, error) {
        	console.log("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
        }
      
	});
	*/
}

//them dong moi vao table
function tableNewRowAddCategory(categoryId, categoryName){
	$('#tableCategorys tbody').append("<tr data-id='"+categoryId+"' data-name='"+categoryName+"'><th scope='row'>"+categoryId+"</th><td>"+categoryName+"</td>"+
                  "<td class='text-center'>"+
					"<button class='btn btn-warning btn-sm btn-edit' type='button'>"+
						"<i class='bi bi-pencil-square'></i></button>\n"+
					"<button class='btn btn-danger btn-sm btn-delete' type='button'>"+                     		
						"<i class='bi bi-trash3-fill'></i></button>"+
					
            "</td></tr>");
	fieldCategoryName.val("");
}

function reloadTable(){
	var countRowTable = $('#tableCategorys tbody').find('tr').length;
	if(countRowTable == 0){
		paging(1);
	}
}

function resetTable(thisObj){
	if(thisObj != null){
		var name = thisObj.parents("tr").attr('data-name');
		thisObj.parents("tr").find("td:eq(0)").text(name);  
	          
	    resetBtn(thisObj);
	}
	 
}

function resetBtn(thisObj){
	thisObj.parents("tr").find(".btn-edit").show();  
	thisObj.parents("tr").find(".btn-delete").show(); 
    thisObj.parents("tr").find(".btn-cancel").remove();  
    thisObj.parents("tr").find(".btn-update").remove();
}

function addNewItem(){
	var countRowTable = $('#tableCategorys tbody').find('tr').length;
	pageSize = $('#selectPageItem').find(':selected').val();
	if(countRowTable >= pageSize){
		paging(pageEnd);
	}
	return false;
}

function checkInputCategory(input) {
	var value = input.val().trim();
	
    if (value == "") {
    	//input.addClass("is-invalid");
        //input.next().text("Chưa nhập tên danh mục");
        errorAction(input, "Chưa nhập tên danh mục", false);
        return false;
    } else {
    	//input.removeClass("is-invalid");
        //input.next().text("");
        errorAction(input, "", true);
        return true;
    }
}

