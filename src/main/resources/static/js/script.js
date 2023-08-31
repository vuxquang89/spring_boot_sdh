//Get the button
let mybutton = document.getElementById("btn-back-to-top");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
  scrollFunction();
};

function scrollFunction() {
  if (
    document.body.scrollTop > 20 ||
    document.documentElement.scrollTop > 20
  ) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}
// When the user clicks on the button, scroll to the top of the document
mybutton.addEventListener("click", function() {
  backToTop(0);
});

function backToTop(x) {
  document.body.scrollTop = x;
  document.documentElement.scrollTop = x;
}

function errorAction(el, mess, status){
	if(status){
		el.removeClass("is-invalid");
	}else{
		el.addClass("is-invalid");
	}
    el.next().text(mess);
}

function elementExists(elementId){
	var element =  document.getElementById(elementId);
	if (typeof(element) != 'undefined' && element != null)
	{
	  	return true;
	}
	return false;
}

//zoom image

 var defaults = {
    zoom: 0.05,
    maxZoom: false,
    initialZoom: 1,
    initialX: 0.5,
    initialY: 0.5,
}, canvas;

function zoomImage(id, src) {
  //console.log('zoom image');
  canvas = document.createElement("canvas");
  //checkLoadComplete = false;
  //var modal = document.getElementById("myModal");
  var place_image = document.getElementById("div-item-"+id),
    imgZoom = document.createElement("img");
  imgZoom.setAttribute("id", "img"+id);
  imgZoom.setAttribute("class", "d-block w-100");
  place_image.appendChild(imgZoom);
  //var imgZoom = document.getElementById('imgZoom');
  addEventZoomScroll(imgZoom);
  //var captionText = document.getElementById("caption");
  //modal.style.display = "block";
  imgZoom.src = src;
  //captionText.innerHTML = e.alt;
}

function addEventZoomScroll(img) {
  //var img = document.getElementById('imgView');

  var settings = {};
  var width, bgWidth, bgPosX;
  var height, bgHeight, bgPosY;
  var previousEvent;
  var cachedDataUrl;

  

  function setSrcToBackground(img) {
    img.style.backgroundImage = 'url("' + img.src + '")';
    img.style.backgroundRepeat = "no-repeat";
    canvas.width = img.naturalWidth;
    canvas.height = img.naturalHeight;
    cachedDataUrl = canvas.toDataURL();
    //console.log(cachedDataUrl);
    img.src = cachedDataUrl;
  }

  function reset() {
    bgWidth = width;
    bgHeight = height;
    bgPosX = bgPosY = 0;
    updateBgStyle();
    console.log("reset");
  }

  function updateBgStyle() {
    if (bgPosX > 0) {
      bgPosX = 0;
    } else if (bgPosX < width - bgWidth) {
      bgPosX = width - bgWidth;
    }

    if (bgPosY > 0) {
      bgPosY = 0;
    } else if (bgPosY < height - bgHeight) {
      bgPosY = height - bgHeight;
    }

    img.style.backgroundSize = bgWidth + "px " + bgHeight + "px";
    img.style.backgroundPosition = bgPosX + "px " + bgPosY + "px";
    
    console.log("update");
  }

  function onWheel(e) {
    var deltaY = 0;
    e.preventDefault();

    if (e.deltaY) {
      //FireFox 17+ (IE9+, Chrome 31+)
      deltaY = e.deltaY;
    } else if (e.wheelDelta) {
      deltaY = -e.wheelDelta;
    }
   
      var rect = img.getBoundingClientRect();
      var offsetX = e.pageX - rect.left - window.pageXOffset;
      var offsetY = e.pageY - rect.top - window.pageYOffset;

      var bgCursorX = offsetX - bgPosX;
      var bgCursorY = offsetY - bgPosY;

      var bgRatioX = bgCursorX / bgWidth;
      var bgRatioY = bgCursorY / bgHeight;

      // Update the bg size:
      if (deltaY < 0) {
        bgWidth += bgWidth * settings.zoom;
        bgHeight += bgHeight * settings.zoom;
      } else {
        bgWidth -= bgWidth * settings.zoom;
        bgHeight -= bgHeight * settings.zoom;
      }

      if (settings.maxZoom) {
        bgWidth = Math.min(width * settings.maxZoom, bgWidth);
        bgHeight = Math.min(height * settings.maxZoom, bgHeight);
      }

      // Take the percent offset and apply it to the new size:
      bgPosX = offsetX - bgWidth * bgRatioX;
      bgPosY = offsetY - bgHeight * bgRatioY;

      // Prevent zooming out beyond the starting size
      if (bgWidth <= width || bgHeight <= height) {
        reset();
      } else {
        updateBgStyle();
      }
   	console.log("onwheel");
  }

  function drag(e) {
    e.preventDefault();
    bgPosX += e.pageX - previousEvent.pageX;
    bgPosY += e.pageY - previousEvent.pageY;
    previousEvent = e;
    updateBgStyle();
    console.log("drag");
  }

  function removeDrag() {
    document.removeEventListener("mouseup", removeDrag);
    //document.removeEventListener("mousedown", getPositionXY);
    document.removeEventListener("mousemove", drag);
    console.log("removedrag");
  }

  // Make the background draggable
  function draggable(e) {
    e.preventDefault();
    previousEvent = e;
    document.addEventListener("mousemove", drag);
    //document.addEventListener("mousedown", getPositionXY);
    document.addEventListener("mouseup", removeDrag);
    
    console.log("draggable");
  }

  function load() {
    var initial = Math.max(settings.initialZoom, 1);

    if (img.src === cachedDataUrl) return;

    var computedStyle = window.getComputedStyle(img, null);

    width = parseInt(computedStyle.width, 10);
    height = parseInt(computedStyle.height, 10);
    bgWidth = width * initial;
    bgHeight = height * initial;
    bgPosX = -(bgWidth - width) * settings.initialX;
    bgPosY = -(bgHeight - height) * settings.initialY;

    setSrcToBackground(img);

    img.style.backgroundSize = bgWidth + "px " + bgHeight + "px";
    img.style.backgroundPosition = bgPosX + "px " + bgPosY + "px";
    img.addEventListener("wheelzoom.reset", reset);

    img.addEventListener("wheel", onWheel);
    img.addEventListener("mousedown", draggable);
	console.log("load");
	console.log(img.style.backgroundSize);
  }
  
  var destroy = function (originalProperties) {
      img.removeEventListener("wheelzoom.destroy", destroy);
      img.removeEventListener("wheelzoom.reset", reset);
      img.removeEventListener("load", load);
      img.removeEventListener("mouseup", removeDrag);
      img.removeEventListener("mousemove", drag);
      img.removeEventListener("mousedown", draggable);
      img.removeEventListener("wheel", onwheel);

      img.style.backgroundImage = originalProperties.backgroundImage;
      img.style.backgroundRepeat = originalProperties.backgroundRepeat;
      img.src = originalProperties.src;
    }.bind(null, {
      backgroundImage: img.style.backgroundImage,
      backgroundRepeat: img.style.backgroundRepeat,
      src: img.src,
    });

    img.addEventListener("wheelzoom.destroy", destroy);

	

  Object.keys(defaults).forEach(function (key) {
    settings[key] = defaults[key];
  });

  if (img.complete) {
    load();
    
  }

  img.addEventListener("load", load);
}

window.wheelzoom = (function () {
  var defaults = {
    zoom: 0.1,
    maxZoom: false,
    initialZoom: 1,
    initialX: 0.5,
    initialY: 0.5,
  };

  var main = function (img, options) {
    if (!img || !img.nodeName || img.nodeName !== "IMG") {
      return;
    }

    var settings = {};
    var width;
    var height;
    var bgWidth;
    var bgHeight;
    var bgPosX;
    var bgPosY;
    var previousEvent;
    var transparentSpaceFiller;

    function setSrcToBackground(img) {
      img.style.backgroundRepeat = "no-repeat";
      img.style.backgroundImage = 'url("' + img.src + '")';
      transparentSpaceFiller =
        "data:image/svg+xml;base64," +
        window.btoa(
          '<svg xmlns="http://www.w3.org/2000/svg" width="' +
            img.naturalWidth +
            '" height="' +
            img.naturalHeight +
            '"></svg>'
        );
      img.src = transparentSpaceFiller;
    }

    function updateBgStyle() {
      if (bgPosX > 0) {
        bgPosX = 0;
      } else if (bgPosX < width - bgWidth) {
        bgPosX = width - bgWidth;
      }

      if (bgPosY > 0) {
        bgPosY = 0;
      } else if (bgPosY < height - bgHeight) {
        bgPosY = height - bgHeight;
      }

      img.style.backgroundSize = bgWidth + "px " + bgHeight + "px";
      img.style.backgroundPosition = bgPosX + "px " + bgPosY + "px";
    }

    function reset() {
      bgWidth = width;
      bgHeight = height;
      bgPosX = bgPosY = 0;
      updateBgStyle();
    }

    function onwheel(e) {
      var deltaY = 0;

      e.preventDefault();

      if (e.deltaY) {
        // FireFox 17+ (IE9+, Chrome 31+?)
        deltaY = e.deltaY;
      } else if (e.wheelDelta) {
        deltaY = -e.wheelDelta;
      }

      // As far as I know, there is no good cross-browser way to get the cursor position relative to the event target.
      // We have to calculate the target element's position relative to the document, and subtrack that from the
      // cursor's position relative to the document.
      var rect = img.getBoundingClientRect();
      var offsetX = e.pageX - rect.left - window.pageXOffset;
      var offsetY = e.pageY - rect.top - window.pageYOffset;

      // Record the offset between the bg edge and cursor:
      var bgCursorX = offsetX - bgPosX;
      var bgCursorY = offsetY - bgPosY;

      // Use the previous offset to get the percent offset between the bg edge and cursor:
      var bgRatioX = bgCursorX / bgWidth;
      var bgRatioY = bgCursorY / bgHeight;

      // Update the bg size:
      if (deltaY < 0) {
        bgWidth += bgWidth * settings.zoom;
        bgHeight += bgHeight * settings.zoom;
      } else {
        bgWidth -= bgWidth * settings.zoom;
        bgHeight -= bgHeight * settings.zoom;
      }

      if (settings.maxZoom) {
        bgWidth = Math.min(width * settings.maxZoom, bgWidth);
        bgHeight = Math.min(height * settings.maxZoom, bgHeight);
      }

      // Take the percent offset and apply it to the new size:
      bgPosX = offsetX - bgWidth * bgRatioX;
      bgPosY = offsetY - bgHeight * bgRatioY;

      // Prevent zooming out beyond the starting size
      if (bgWidth <= width || bgHeight <= height) {
        reset();
      } else {
        updateBgStyle();
      }
    }

    function drag(e) {
      e.preventDefault();
      bgPosX += e.pageX - previousEvent.pageX;
      bgPosY += e.pageY - previousEvent.pageY;
      previousEvent = e;
      updateBgStyle();
    }

    function removeDrag() {
      document.removeEventListener("mouseup", removeDrag);
      document.removeEventListener("mousemove", drag);
    }

    // Make the background draggable
    function draggable(e) {
      e.preventDefault();
      previousEvent = e;
      document.addEventListener("mousemove", drag);
      document.addEventListener("mouseup", removeDrag);
    }

    function load() {
      var initial = Math.max(settings.initialZoom, 1);

      if (img.src === transparentSpaceFiller) return;

      var computedStyle = window.getComputedStyle(img, null);

      width = parseInt(computedStyle.width, 10);
      height = parseInt(computedStyle.height, 10);
      bgWidth = width * initial;
      bgHeight = height * initial;
      bgPosX = -(bgWidth - width) * settings.initialX;
      bgPosY = -(bgHeight - height) * settings.initialY;

      setSrcToBackground(img);

      img.style.backgroundSize = bgWidth + "px " + bgHeight + "px";
      img.style.backgroundPosition = bgPosX + "px " + bgPosY + "px";
      img.addEventListener("wheelzoom.reset", reset);

      img.addEventListener("wheel", onwheel);
      img.addEventListener("mousedown", draggable);
    }

    var destroy = function (originalProperties) {
      img.removeEventListener("wheelzoom.destroy", destroy);
      img.removeEventListener("wheelzoom.reset", reset);
      img.removeEventListener("load", load);
      img.removeEventListener("mouseup", removeDrag);
      img.removeEventListener("mousemove", drag);
      img.removeEventListener("mousedown", draggable);
      img.removeEventListener("wheel", onwheel);

      img.style.backgroundImage = originalProperties.backgroundImage;
      img.style.backgroundRepeat = originalProperties.backgroundRepeat;
      img.src = originalProperties.src;
    }.bind(null, {
      backgroundImage: img.style.backgroundImage,
      backgroundRepeat: img.style.backgroundRepeat,
      src: img.src,
    });

    img.addEventListener("wheelzoom.destroy", destroy);

    options = options || {};

    Object.keys(defaults).forEach(function (key) {
      settings[key] = options[key] !== undefined ? options[key] : defaults[key];
    });

    if (img.complete) {
      load();
    }

    img.addEventListener("load", load);
  };

  // Do nothing in IE9 or below
  if (typeof window.btoa !== "function") {
    return function (elements) {
      return elements;
    };
  } else {
    return function (elements, options) {
      if (elements && elements.length) {
        Array.prototype.forEach.call(elements, function (node) {
          main(node, options);
        });
      } else if (elements && elements.nodeName) {
        main(elements, options);
      }
      return elements;
    };
  }
})();