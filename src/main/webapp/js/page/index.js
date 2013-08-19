/*global $:false, TweenLite:false, Circ:false*/

$(document).ready(function() {
	
	"use strict";
	
	// login form
	$('.form-signin button').click(function() {
		$(this).addClass('loading').html('please wait...')
		.animate({'background-position': -150},5000,'linear');
		setTimeout(function() {
			window.location.href = 'dashboard.html';
		}, 1000 );
		return false;
	});
	
	
	TweenLite.to($('.form-signin .profile'), 0, {top:"-200px", alpha:0});
	TweenLite.to($('.form-signin input.email'), 0, {left:"-200px", alpha:0});
	TweenLite.to($('.form-signin input.password'), 0, {left:"200px", alpha:0});
	TweenLite.to($('.form-signin .login-bottom'), 0, {bottom:"-50px", alpha:0});
	
	
	TweenLite.to($('.form-signin .profile'), 0.5, {top:0, alpha:1, ease:Circ.easeOut});
	TweenLite.to($('.form-signin input.email'), 0.5, {delay:0.3, alpha:1, left:0, ease:Circ.easeOut});
	TweenLite.to($('.form-signin input.password'), 0.5, {delay:0.5, alpha:1, left:0, ease:Circ.easeOut});
	TweenLite.to($('.form-signin .login-bottom'), 0.5, {delay:0.7, alpha:1, bottom:0, ease:Circ.easeOut});
	
	
});