var selectDistrict, selectSite, selectPageSize;
var btnSearch, btnEraser;
var fieldSearch;
var tableSiteInfo;

$(document).ready(function(){
	selectDistrict = $('#selectDistrict');
	selectSite = $('#selectSite');
	selectPageSize = $('#selectPageSize');
	btnSearch = $('#btnSearch');
	btnEraser = $('#btnEraser');
	fieldSearch = $('#inputSearch');
	tableSiteInfo = $('#tableSiteInfo');
	
	
	selectDistrict.on('change', loadSites);
	
	//selectSite.on('change', loadSiteInfo);
	selectSite.on('change', function(){
		loadPageSiteInfo(1);
	});
	
	selectPageSize.on('change', function(){
		loadPageSiteInfo(1);
	})
	
	btnSearch.on('click', function(){
		loadPageSiteInfo(1);
	});
	
	btnEraser.on('click', function(){
		fieldSearch.val('');
		loadPageSiteInfo(1);
	});
	
	$('.btn-close-model-slider-image').click(function(){
		resetModelShowImage();
	});
	
	$('#tableSiteInfo').on('click','.element-model-show-image', loadModelShowImage);
	
})

function loadSites(){
	$('#tableSiteInfo tbody').html('<tr><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
	$('#pagination').html("");
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

function loadPageSiteInfo(pageNo){
	districtId = selectDistrict.find(':selected').val();
	siteId = selectSite.find(':selected').val();
	pageSize = selectPageSize.find(':selected').val();
	
	pagingSiteInfo(districtId, siteId, pageSize, pageNo);
	backToTop(500);
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
			//xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(responseJson){
		console.log(responseJson);
		if(!jQuery.isEmptyObject(responseJson[0])){
			$('#tableSiteInfo tbody').html("");
			$('#pagination').html("");
				$.each(responseJson, function(index, siteInfo){
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
                                                        '<a href="#carouselExampleCaptions" data-bs-slide-to="'+i+'">'+
                                                        	'<img width="100px" height="100px" class="img-fluid td-img" src="'+ contextPath +'upload/images/'+districtId+'/'+siteId+'/'+img.fileName+'" alt="Image">'+                                                           
                                                        '</a></div>');
                            i++
                        });
	                }
                
			});
			
			
		}else{
			$('#tableSiteInfo tbody').html('<tr><td colspan="9" class="text-center">Không có dữ liệu!</td></tr>');
			$('#pagination').html("");
		}
		
		
		$('body').removeClass("loading");
	}).fail(function(){
		console.log("error loadDistrict");
	});
}

function loadModelShowImage(){
	
	siteInfoId = $(this).attr('data-siteinfo-id');
	districtId = selectDistrict.find(':selected').val();
	siteId = selectSite.find(':selected').val();
	deviceName = $(this).attr('data-device-name');
	iSlider = $(this).attr('data-slider');
	
	$('#showImageSliderModalLabel').text(deviceName);
	
	var rowCountImage = $('#tableSiteInfo tbody td#'+siteInfoId+' .element-model-show-image').length;
	var elImage = $('#tableSiteInfo tbody td#'+siteInfoId+' .element-model-show-image');
	var i = 0;
	
	elImage.each(function(){
		imageId = $(this).attr('data-img-id');
		imageName = $(this).attr('data-img-name');
		$('#carouselExampleCaptions .carousel-indicators').append('<button type="button" id="btn-item-'+imageId+'" data-bs-target="#carouselExampleCaptions" '+
									'data-bs-slide-to="'+i+'" aria-label="Slide '+i+'"></button>');
		$('#carouselExampleCaptions .carousel-inner').append('<div id="div-item-'+imageId+'" class="carousel-item">'+
                                    '<img id="img'+imageId+'" class="d-block w-100" '+
                                        'src="'+ contextPath +'upload/images/'+districtId+'/'+siteId+'/'+imageName+'" alt="First '+i+'">'+
                                    '<div class="carousel-caption d-none d-md-block">'+
                                        '<h5></h5>'+
                                        '<p></p>'+
                                    '</div></div>');
		//wheelzoom(document.getElementById("img"+imageId));
		//zoomImage(imageId, $(this).find('img').attr('src'));
        if(i==iSlider){
        	$('#carouselExampleCaptions .carousel-indicators #btn-item-'+imageId).addClass('active');
            $('#carouselExampleCaptions .carousel-indicators #btn-item-'+imageId).attr('aria-current', 'true');
            $('#carouselExampleCaptions .carousel-inner #div-item-'+imageId).addClass('active');
        }
        i++;
	})
	$('#showImageSliderModal').modal('show');
	
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

function resetModelShowImage(){
	$('#carouselExampleCaptions .carousel-indicators').html('');
	$('#carouselExampleCaptions .carousel-inner').html('');
	$('#showImageSliderModal').modal('toggle');
}