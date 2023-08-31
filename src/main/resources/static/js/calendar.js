
/*
document.addEventListener("DOMContentLoaded", function () {
	var calendarEl = $('#calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
    	initialDate: formatDate(new Date()),
	    editable: true,
	    selectable: true,
	    businessHours: true,
	    dayMaxEvents: true, // allow "more" link when too many events
	    events: function(info, successCallback, failureCallback) {
	    	url = contextPath + "api/operate/calendar/list/1/2023";
	    	$.ajax({
				type: "GET",
				url: url,
				beforeSend: function(xhr){
					$('body').addClass("loading");
					//xhr.setRequestHeader(csrfHeader, csrfToken);
				}
			}).done(function(responseJson){
				console.log(responseJson);
				var events = [];
	            
	            successCallback(events);
			}).fail(function(error){
				console.log('ERROR', error);
				console.log("error loadDistrict");
			});
			
	    },
	    
	    select: function(start, end, allDays){
			console.log(start);
	      	//console.log(moment(start).format('yyyy-MM-dd'));
	    }
    });
	calendar.render();
});
*/

document.addEventListener("DOMContentLoaded", function () {
        var calendarEl = document.getElementById("calendar");

        var calendar = new FullCalendar.Calendar(calendarEl, {
          headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: "",
          },
          initialDate: formatDate(new Date()),
	    	businessHours: true,
	    	dayMaxEvents: true, // allow "more" link when too many events
          selectable: true,
          selectMirror: true,
          select: function (arg) {
            goToDate(formatDate(arg.start));
            calendar.unselect();
          },
          eventClick: function (arg) {
          	
            loadEvent(arg.event._def.publicId);
            
            //if (confirm("Are you sure you want to delete this event?")) {
            //  arg.event.remove();
            //}
          },
          editable: false,
          dayMaxEvents: true, // allow "more" link when too many events
          events: function (info, successCallback, failureCallback) {
          	var dObject = getMonthYear(info.start);
          	console.log("date object ", dObject);
            url = contextPath + "api/operate/calendar/list/"+dObject.month+"/"+dObject.year;
            $.ajax({
              type: "GET",
              url: url,
              beforeSend: function (xhr) {
                $("body").addClass("loading");
                //xhr.setRequestHeader(csrfHeader, csrfToken);
              },
            })
              .done(function (responseJson) {
                console.log(responseJson);
                var events = [];
				$.each(responseJson, function(index, operate){
					events.push({
						id: operate.id,
                        title: operate.title_acronym,
                        start: operate.start,
                        end: operate.end,
                        color: operate.color,
                    });
				})
                successCallback(events);
                $('body').removeClass("loading");
              })
              .fail(function (error) {
                console.log("ERROR", error);
                console.log("error loadDistrict");
                $('body').removeClass("loading");
              });
          },
        });

        calendar.render();
	
});

/*
var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
document.addEventListener("DOMContentLoaded", function () {
        var calendarEl = document.getElementById("calendar");

        var calendar = new FullCalendar.Calendar(calendarEl, {
          header: {
            left: "title",
            center: "agendaDay,agendaWeek,month",
            right: "prev,next today",
          },
          editable: true,
          firstDay: 1, //  1(Monday) this can be changed to 0(Sunday) for the USA system
          selectable: true,
          defaultView: "month",

          axisFormat: "h:mm",
          columnFormat: {
            month: "ddd", // Mon
            week: "ddd d", // Mon 7
            day: "dddd M/d", // Monday 9/7
            agendaDay: "dddd d",
          },
          
          allDaySlot: false,
          selectHelper: true,
          select: function (start, end, allDay) {
            var title = prompt("Event Title:");
            if (title) {
              calendar.fullCalendar(
                "renderEvent",
                {
                  title: title,
                  start: start,
                  end: end,
                  allDay: allDay,
                },
                true // make the event "stick"
              );
            }
            calendar.fullCalendar("unselect");
          },
          droppable: true, // this allows things to be dropped onto the calendar !!!
          drop: function (date, allDay) {
            // this function is called when something is dropped

            // retrieve the dropped element's stored Event Object
            var originalEventObject = $(this).data("eventObject");

            // we need to copy it, so that multiple events don't have a reference to the same object
            var copiedEventObject = $.extend({}, originalEventObject);

            // assign it the date that was reported
            copiedEventObject.start = date;
            copiedEventObject.allDay = allDay;

            // render the event on the calendar
            // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
            $("#calendar").fullCalendar("renderEvent", copiedEventObject, true);

            // is the "remove after drop" checkbox checked?
            if ($("#drop-remove").is(":checked")) {
              // if so, remove the element from the "Draggable Events" list
              $(this).remove();
            }
          },

          events: [
            {
              title: "All Day Event",
              start: new Date(y, m, 1),
            },
            {
              id: 999,
              title: "Repeating Event",
              start: new Date(y, m, d - 3, 16, 0),
              allDay: false,
              className: "info",
            },
            {
              id: 999,
              title: "Repeating Event",
              start: new Date(y, m, d + 4, 16, 0),
              allDay: false,
              className: "info",
            },
            {
              title: "Meeting",
              start: new Date(y, m, d, 10, 30),
              allDay: false,
              className: "important",
            },
            {
              title: "Lunch",
              start: new Date(y, m, d, 12, 0),
              end: new Date(y, m, d, 14, 0),
              allDay: false,
              color: "#f73636",
              className: "important",
            },
            {
              title: "Birthday Party",
              start: new Date(y, m, d + 1, 19, 0),
              end: new Date(y, m, d + 1, 22, 30),
              allDay: false,
              className: "important",
            },
            {
              title: "Click for Google",
              start: new Date(y, m, 28),
              end: new Date(y, m, 29),
              url: "http://google.com/",
              className: "success",
            },
          ],
        });

        calendar.render();
	
});

*/

function loadEvent(eventId){
	url = contextPath + "api/operate/calendar/event/" + eventId;
	
            $.ajax({
              type: "GET",
              url: url,
              beforeSend: function (xhr) {
                $("body").addClass("loading");
                //xhr.setRequestHeader(csrfHeader, csrfToken);
              },
            })
              .done(function (responseJson) {
                console.log(responseJson);
                if(!jQuery.isEmptyObject(responseJson)){
                	$('#modalInfoEventTitle').text(responseJson.title);
                	$('#startTime').val(convertDatetime(responseJson.start));
                	$('#endTime').val(convertDatetime(responseJson.end));
                	convertTime(responseJson.processingTime);
                	$('#status').val(responseJson.status);
                	$('#status').css('background-color', responseJson.color);
                	$('#note').val(responseJson.note);
                	$('#btnViewInfo').attr("href", contextPath+"operate/edit/"+responseJson.id);
                	$('#modalInfoEvent').modal('show');
                	console.log("show modal");
                }
				
                $('body').removeClass("loading");
              })
              .fail(function (error) {
                console.log("ERROR", error);
                console.log("error loadDistrict");
                $('body').removeClass("loading");
              });
	
}

function closeModel(){
	$('#modalInfoEventTitle').text('');
    $('#startTime').val('');
    $('#endTime').val('');
    $('#processingTime').val('');
    $('#status').val('');
    //$('#status').css('background-color', responseJson.color);
    $('#note').val('');
    $('#btnViewInfo').attr("href", "#");
    $('#modalInfoEvent').modal('toggle');
}


function getMonthYear(starDate) {
	var d = new Date(starDate),
    	month = d.getMonth() + 1,
        day = d.getDate(),
        year = d.getFullYear();
    var objectDate = {};
    if (day == 1) {
    	objectDate["month"] = month;
        objectDate["year"] = year;
    } else if (month == 12) {
    	objectDate["month"] = 1;
        objectDate["year"] = year + 1;
    } else {
    	objectDate["month"] = month + 1;
        objectDate["year"] = year;
    }
    return objectDate;
}

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function convertDatetime(localDateTime){
	return localDateTime.replace("T", " ");
}

function goToDate(date){
	location.href = contextPath + 'operate/list/'+date;
}