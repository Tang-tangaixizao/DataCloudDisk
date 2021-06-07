window.onload=function(){
	var login_box=document.querySelector('.login_box');
	dynamics.animate(login_box,{
		opacity:1,
		scale:1
	},{
		type:dynamics.spring,
		frequency:200,
		friction:270,
		duration:1500
	});
}