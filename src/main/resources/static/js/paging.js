
function pagingSiteInfo(districtId, siteId, pageSize, pageNo){
	keySearch = $('#inputSearch').val().trim();
	
	url = contextPath + "api/siteinfo/"+siteId+"/page?pageSize="+pageSize+"&pageNo="+pageNo+"&search="+keySearch;
	//url = contextPath + "api/siteinfo/"+siteId+"/page?pageSize="+pageSize+"&pageNo="+pageNo;
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			//xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		
		listSiteInfo = responseJson.content;
		if(!jQuery.isEmptyObject(listSiteInfo[0])){
			$('#tableSiteInfo tbody').html("");
			$('#pagination').html("");
				$.each(listSiteInfo, function(index, siteInfo){
					$('#tableSiteInfo tbody').append('<tr>'+
                                            '<th scope="row">'+siteInfo.id+'</th>'+
                                            '<td>'+checkCategory(siteInfo.device.id)+'</td>'+
                                            '<td class="site-info-device-name">'+siteInfo.device.name+'</td>'+
                                            '<td>'+siteInfo.quantity+'</td>'+
                                            '<td>'+siteInfo.serialNumber+'</td>'+
                                            '<td>'+siteInfo.slot+'</td>'+
                                            '<td id="'+siteInfo.id+'"><div class="row"></div></td>'+
                                            '<td>'+siteInfo.note+'</td>'+
                                            
                                            '</td></tr>');
	                if(!jQuery.isEmptyObject(siteInfo.siteImages)){
	                	siteImages = siteInfo.siteImages;
	                	var i = 0;
	                	$.each(siteImages, function(idx, img){
	                		$('#tableSiteInfo tbody td#'+siteInfo.id+' .row').append('<div class="col-md-5 ms-2 px-0 pb-1 element-model-show-image" '+                                                    
                                                    ' data-slider="'+i+'" data-siteinfo-id="'+siteInfo.id+'" data-img-id="'+img.id+'" data-device-name="'+siteInfo.device.name+'" data-img-name="'+img.fileName+'">'+
                                                        '<a data-targer="#carouselExampleCaptions" data-bs-slide-to="'+i+'">'+
                                                        	'<img class="img-fluid td-img" src="'+ contextPath +img.pathName+'/'+img.fileName100+'" alt="Image">'+                                                           
                                                        '</a></div>');
                            i++
                        });
	                }
                
			});
			
			loadSiteInfoPagination(pageNo, responseJson.totalPages);
		}else{
			$('#tableSiteInfo tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
			$('#pagination').html("");
		}
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log('ERROR', error);
		console.log("error loadDistrict");
	});
}

function loadSiteInfoPagination(currentPage, totalPages){
	pageEnd = totalPages;
	if(currentPage > 1){
		$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:loadPageSiteInfo('+(currentPage - 1)+')"><< Previous</a></li>');
	}else{
		$('#pagination').append('<li class="page-item disabled"><a class="page-link" href="#"><< Previous</a></li>');
	}
	for(i = 1; i <= totalPages; i++){
		if(i !== currentPage){
			$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:loadPageSiteInfo('+i+')">'+i+'</a></li>');
		}else{
			$('#pagination').append('<li class="page-item active"><span class="page-link">'+i+'</span></li>');
		}
				
	}
			
	if(currentPage < totalPages){
		$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:loadPageSiteInfo('+(currentPage + 1)+')">Next >></a></li>');
	}else{
		$('#pagination').append('<li class="page-item disabled"><a class="page-link" href="#">Next >></a></li>');
	}
}

function pagingCategory(pageSize, currentPage){
	keySearch = $('#inputSearch').val().trim();
	
	url = contextPath + "api/category/page?pageSize="+pageSize+"&currentPage="+currentPage+"&search="+keySearch;
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
		}
	}).done(function(resJson){
		
		listCategory = resJson.content;
		if(!jQuery.isEmptyObject(listCategory[0])){
			$('#tableCategorys tbody').html("");
			$('#pagination').html("");
				$.each(listCategory, function(index, category){
					$('#tableCategorys tbody').append('<tr data-id="'+category.id+'" data-name="'+category.name+'">'+
                                                    '<th scope="row">'+category.id+'</th>'+
                                                    '<td>'+category.name+'</td>'+
                                                    '<td class="text-center">'+
                                                        '<button id="btnEditCategory" class="btn btn-warning btn-sm btn-edit" type="button">'+
                                                                '<i class="bi bi-pencil-square"></i></button>\n'+
                                                        '<button class="btn btn-danger btn-sm btn-delete" type="button">'+ 
                                                        	'<i class="bi bi-trash3-fill"></i></button>'+
                                                    '</td></tr>');
			});
			
			loadPagination(currentPage, resJson.totalPages);
		}else{
			$('#tableCategorys tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
			$('#pagination').html("");
		}
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log(error);
	});
}

function loadPagination(currentPage, totalPages){
	pageEnd = totalPages;
	if(currentPage > 1){
		$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:paging('+(currentPage - 1)+')"><< Previous</a></li>');
	}else{
		$('#pagination').append('<li class="page-item disabled"><a class="page-link" href="#"><< Previous</a></li>');
	}
	for(i = 1; i <= totalPages; i++){
		if(i !== currentPage){
			$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:paging('+i+')">'+i+'</a></li>');
		}else{
			$('#pagination').append('<li class="page-item active"><span class="page-link">'+i+'</span></li>');
		}
				
	}
			
	if(currentPage < totalPages){
		$('#pagination').append('<li class="page-item"><a class="page-link" href="javascript:paging('+(currentPage + 1)+')">Next >></a></li>');
	}else{
		$('#pagination').append('<li class="page-item disabled"><a class="page-link" href="#">Next >></a></li>');
	}
}

function pagingDevice(categoryId, categoryName, pageSize, pageNo){
	keySearch = $('#inputSearch').val().trim();
	
	url = contextPath + "api/device/category/"+categoryId+"/page?pageSize="+pageSize+"&pageNo="+pageNo+"&search="+keySearch;	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			//xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		
		listDevice = responseJson.content;
		console.log(responseJson);
		
		if(!jQuery.isEmptyObject(listDevice[0])){
			$('#tableDevices tbody').html("");
			$('#pagination').html("");
			$.each(listDevice, function(index, device){
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
			
			loadPagination(pageNo, responseJson.totalPages);
		}else{
			$('#tableDevices tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
			$('#pagination').html("");
		}
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log(error);
		console.log("error loadDevice");
	});
}

function pagingAdminSiteInfo(siteId, districtId, pageSize, pageNo){
	keySearch = $('#inputSearch').val().trim();
	url = contextPath + "api/siteinfo/"+siteId+"/page?pageSize="+pageSize+"&pageNo="+pageNo+"&search="+keySearch;
	
	$.ajax({
		type: "GET",
		url: url,
		beforeSend: function(xhr){
			$('body').addClass("loading");
			//xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log(responseJson);
		listSiteInfo = responseJson.content;
		
		if(!jQuery.isEmptyObject(listSiteInfo[0])){
			$('#tableSiteInfo tbody').html("");
			$('#pagination').html("");
			$.each(listSiteInfo, function(index, siteInfo){
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
                                                        	'<img class="img-fluid td-img" src="'+ contextPath +img.pathName+'/'+img.fileName100+'" alt="Image">'+                                                           
                                                        '</a></div>');
                            i++
                        });
	                }
                
			});
			
			loadPagination(pageNo, responseJson.totalPages);
		}else{
			$('#tableSiteInfo tbody').html('<tr id="contentNoneData"><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
			$('#pagination').html("");
		}
		
		$('body').removeClass("loading");
	}).fail(function(error){
		console.log(error);
		console.log("error loadDevice");
	});
}