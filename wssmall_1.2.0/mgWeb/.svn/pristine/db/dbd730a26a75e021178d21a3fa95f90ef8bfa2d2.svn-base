//leftfu.style.visibility='hidden'; 
//sorollDiv1.style.visibility='hidden';
var lastScrollY=0;
var setup=0.02
function heartBeat2(){ 
	try{
	diffY=document.documentElement.scrollTop; 
	percent=setup*(diffY-lastScrollY); 
	if(percent>0)percent=Math.ceil(percent); 
	else percent=Math.floor(percent); 
	//document.getElementById("leftfu").style.top=parseInt(document.getElementById("leftfu").style.top)+percent+"px";
	//document.getElementById("rightfu").style.top=parseInt(document.getElementById("rightfu").style.top)+percent+"px";
	document.getElementById("myQuickBox").style.top=parseInt(document.getElementById("myQuickBox").style.top)+percent+"px";
	lastScrollY=lastScrollY+percent; 
	}catch(e){}
}
function hideOrShow(){
	$('#myQuickList').toggle();		
}


$(document).ready(function(){
	//获取显示器分辨率，如果小于等于 1024乘以768时，把快捷导航缩起来
	var width = screen.width;
	if(width<=1024){
		$('#myQuickList').hide();
	}
	
	window.setInterval("heartBeat2()",1);
});

