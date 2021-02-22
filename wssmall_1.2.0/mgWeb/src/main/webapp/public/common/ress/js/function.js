// JavaScript Document

/*显示城市切换效果,ID作为隐藏层的城市列表,i值控制是否显示*/
function showhidden(id,i){ 
	if(i==1){
		document.getElementById(id).style.display="block";
	}else{
		document.getElementById(id).style.display="none";
		}
}

/*导航图片的鼠标移动切换*/
/*cid为当前层的ID值，i为切换变量 1为当前显示，2为高亮显示*/
function bannerAlt(cid,i){
	var temp = document.getElementById(cid).src;
	if(i==1){
	temp = temp.replace("A","B");
	}
	if(i==2){
		temp = temp.replace("B","A");
	}
	document.getElementById(cid).src = temp;
	}

/*产品模块层的背景及字体颜色的切换*/
function productAlt(i){
	if(i==1){
	this.className = "product_model_red";
	alert("aaaa"+this.className);
	}
	if(i==2){
	this.className = "product_model";
	alert("aaaa"+this.className);
	}
	}
//公告滚动展示效果
		/*var count=0
		var index = 0;
		var t;
		var tDelay;
		var textScroll = '';
		var links = '';
		var scrollList = new Array();

		function timedCount()
		{
			if(index == links.length) index = 0;

		textScroll = scrollList[index];
		objScroller = document.getElementById('scrollAnchor');
		objScroller.innerHTML = textScroll.substring(0,count);
		objScroller.href = links[index].href ;
		 try{	
		  	hbxName = links[index].name;
			hbxName = hbxName.substring(hbxName.indexOf('=')+1,hbxName.length);
		 }
		catch(e){hbxName = '';}
		hbxName = 'javascript:_hbLink("'+hbxName+'");';
		//if(index == 2)
		   // hbxName += links[index].href.replace("javascript:","") + 'return false;';
		objScroller.onclick =  new Function(hbxName);

		count=count+1;
		if(count > textScroll.length+1)
		{
		  	
			waitTime(2);
			count = 0;
			
			if( index > scrollList.length-1)
			index = 0;
			tDelay = setTimeout("cancelDelay()",3000)
			return;
			
		}
		t=setTimeout("timedCount()",0);
		}

		function sNext()
		{
			stopCount();
			index++;
			if( index > scrollList.length-1)
				index = 0; 
			count = 0;
			timedCount();
		}
		function sPrevious()
		{
			stopCount();
			index--;
			if( index == -1)
				index = scrollList.length-1;
			count = 0;
			timedCount();
		}
		var date;
		function waitTime(mills)
		{
			stopCount();
			date = new Date();
			date.setMinutes(mills + date.getMinutes());
	
		}
		function cancelDelay()
		{
			var curDate = new Date();
			if(curDate <= date)
			{
				clearTimeout(tDelay);
				t=setTimeout("timedCount()",50);
				index = index +1;
			}
			else
			{
				tDelay = setTimeout("cancelDelay()",5000)
			}
		}

		function stopCount()
		{
		    clearTimeout(t);
		    clearTimeout(tDelay);
		}

		function pausePlay()
		{
			stopCount();
  			/*pauseButton = document.getElementById('pauseplay');
     
 			   if(pauseButton.src.indexOf('publish_pause') != -1)
 			   {
			        stopCount();
			        timedCount();
			        pauseButton.src = pauseButton.src.replace('f','pause');
        
			    }
			    else
			    {
				    stopCount();
				    pauseButton.src = pauseButton.src.replace('pause','f');
				}
		}

		
		//scrollList = document.getElementById('txt').value.split('#');
		function startScroller()
		{
		    links = document.getElementById('scrollerLinks').getElementsByTagName('a');
		    for(var i = 0;i < links.length ;i++)
		    {
		        scrollList[scrollList.length] = links[i].innerHTML;
		    }
	
		    if(scrollList.length > 0)
		        timedCount();
		}


		function seturl()
		{
			var obj = document.getElementById('verizon_prods')
			_hbLink(obj.options[obj.selectedIndex].getAttribute('hbx'));
			document.location.href = obj.options[obj.selectedIndex].getAttribute('url')

		}*/
		
//网厅公告

(function($) {
$.fn.marquee = function(o) {
   //获取滚动内容内各元素相关信息
   o = $.extend({
    speed:   parseInt($(this).attr('speed')) || 30, // 滚动速度
    step:   parseInt($(this).attr('step')) || 1, // 滚动步长=li宽度
    direction: $(this).attr('direction') || 'up', // 滚动方向
    pause:   parseInt($(this).attr('pause')) || 1000 // 停顿时长
   }, o || {});
   var dIndex = jQuery.inArray(o.direction, ['right', 'down']);
   if (dIndex > -1) {
    o.direction = ['left', 'up'][dIndex];
    o.step = -o.step;
   }
   var mid;
   var div    = $(this); // 容器对象
   var divWidth = div.innerWidth(); // 容器宽
   var divHeight = div.innerHeight(); // 容器高
   var ul     = $("ul", div);
   var li     = $("li", ul);
   var liSize    = li.size(); // 初始元素个数
   var liWidth = li.width(); // 元素宽
   var liHeight = li.height(); // 元素高
   var width    = liWidth * liSize;
   var height    = liHeight * liSize;
   if ((o.direction == 'left' && width > divWidth) || 
    (o.direction == 'up' && height > divHeight)) {
    // 元素超出可显示范围才滚动
    if (o.direction == 'left') {
     ul.width(2 * liSize * liWidth);
     if (o.step < 0) div.scrollLeft(width);
    } else {
     ul.height(2 * liSize * liHeight);
     if (o.step < 0) div.scrollTop(height);
    }
    ul.append(li.clone()); // 复制元素
    mid = setInterval(_marquee, o.speed);
    div.hover(
     function(){clearInterval(mid);},
     function(){mid = setInterval(_marquee, o.speed);}
    );
   }
   function _marquee() {
    // 滚动
    if (o.direction == 'left') {
     var l = div.scrollLeft();
     if (o.step < 0) {
      div.scrollLeft((l <= 0 ? width : l) + o.step);
     } else {
      div.scrollLeft((l >= width ? 0 : l) + o.step);
     }
     if (l % liWidth == 0) _pause();
    } else {
     var t = div.scrollTop();
     if (o.step < 0) {
      div.scrollTop((t <= 0 ? height : t) + o.step);
     } else {
      div.scrollTop((t >= height ? 0 : t) + o.step);
     }
     if (t % liHeight == 0) _pause();
    }
   }
   function _pause() {
    // 停顿
    if (o.pause > 0) {
     var tempStep = o.step;
     o.step = 0;
     setTimeout(function() {
      o.step = tempStep;
     }, o.pause);
    }
   }
};
})(jQuery);
$(document).ready(function(){
$(".marquee").each(function() {
   $(this).marquee();
});
});

/*快捷服务的展开特效*/
	
function producecloseall(){
	document.getElementById("index_cheapandprom").style.display = "none";
	document.getElementById("index_hotsell").style.display = "none";
	document.getElementById("index_tyterminal").style.display = "none";
	document.getElementById("index_lovemusic").style.display = "none";
	

}
function produceshow(pid){
	producecloseall();
	document.getElementById(pid).style.display = '';
}
function tshow(tid){
	document.getElementById('t1').className = 'index_promotions_title_nohover';
	document.getElementById('t2').className = 'index_promotions_title_nohover';
	document.getElementById('t3').className = 'index_promotions_title_nohover';
	document.getElementById('t4').className = 'index_promotions_title_nohover';
	
	document.getElementById(tid).className = 'index_promotions_title_hover';	
}




//业务列表的JS效果
function g(o){return document.getElementById(o);}
function hoverLi(n){
//如果有N个标签,就将i<=N;
for(var i=1;i<=5;i++){
	g('tb_'+i).className='product_list_nohover floatLeft';
	g('tbc_'+i).style.display='none';
	g('tbd_'+i).style.display='none';
	g('tba_'+i).className='index_titletext1';
	}
	g('tbc_'+n).style.display='block';
	g('tbd_'+n).style.display='block';
	g('tb_'+n).className='product_list_hover floatLeft';
	g('tba_'+n).className='index_titletext1_over';
}

function outLi(){
//如果有N个标签,就将i<=N;
for(var i=1;i<=5;i++){
	g('tb_'+i).className='product_list_nohover floatLeft';
	g('tbc_'+i).style.display='none';
	g('tbd_'+i).style.display='none';
	g('tba_'+i).className='index_titletext1';
	}
}
