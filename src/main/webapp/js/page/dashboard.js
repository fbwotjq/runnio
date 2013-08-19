/*global $:false */

$(document).ready(function() {
	
	"use strict";
	
	// call messages
	setTimeout(callSimpleMessage, 600);
	setTimeout(callMessages, 1000);
	setTimeout(callSimpleMessage, 1400);
	
	// table sorter init
	$(".tblr").addClass("beautifulData").beautify({
		pageSize : 8,
		pagerSize : 5
	});
	
	/* char */
	var sin = [];
    
	sin.push([1, -0.5]);
	sin.push([2,0.8]);
	sin.push([3, -0.5]);
	sin.push([4,0.8]);
	sin.push([5, -0.5]);
	sin.push([6,0.8]);
	
	callChart();
	
	function callChart() {
		
		var options = {
			series: {
				lines: { show: true },
				points: { show: true }
			},
			grid: {
				hoverable: true, clickable: true, borderWidth: 0
			},
			yaxis: { min: -1.2, max: 1.2 },
			tooltip: true,
			tooltipOpts: {
				content: "'%s' of %x.1 is %y.4",
				shifts: {
					x: -60,
					y: 25
				}
			}
		};
		
		if ($(".char").length > 0) {
			var plot = $.plot($(".char"),
				[{ data: sin, label: "sin(x)", color: '#fff'}], options);
			
			$(".char").bind("plotclick", function (event, pos, item) {
				if (item) {
					plot.highlight(item.series, item.datapoint);
				}
			});
		}
	}
	
	// Calendar init
	var date = new Date();
	var d = date.getDate();
	var m = date.getMonth();
	var y = date.getFullYear();
	
	$('#calendar').fullCalendar({
		editable: true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
		events: [
			{
				title: 'All Day Event',
				start: new Date(y, m, 1)
			},
			{
				title: 'Long Event',
				start: new Date(y, m, d-5),
				end: new Date(y, m, d-2)
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d-3, 16, 0),
				allDay: false
			},
			{
				id: 999,
				title: 'Repeating Event',
				start: new Date(y, m, d+4, 16, 0),
				allDay: false
			},
			{
				title: 'Meeting',
				start: new Date(y, m, d, 10, 30),
				allDay: false
			},
			{
				title: 'Lunch',
				start: new Date(y, m, d, 12, 0),
				end: new Date(y, m, d, 14, 0),
				allDay: false
			},
			{
				title: 'Birthday Party',
				start: new Date(y, m, d+1, 19, 0),
				end: new Date(y, m, d+1, 22, 30),
				allDay: false
			},
			{
				title: 'Click for Google',
				start: new Date(y, m, 28),
				end: new Date(y, m, 29),
				url: 'http://google.com/'
			}
		]
	});
	
});

function callMessages() {
	
	"use strict";
	
	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: 'This is a regular notice!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.',
		// (string | optional) the image to display on the left
		image: 'http://placehold.it/74x70',
		// (bool | optional) if you want it to fade out on its own or just sit there
		sticky: false,
		// (int | optional) the time you want it to be alive for before fading out
		time: ''
	});
}

function callSimpleMessage() {
	
	"use strict";
	
	$.gritter.add({
		// (string | mandatory) the heading of the notification
		title: 'This is a notice without an image!',
		// (string | mandatory) the text inside the notification
		text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.'
	});
}