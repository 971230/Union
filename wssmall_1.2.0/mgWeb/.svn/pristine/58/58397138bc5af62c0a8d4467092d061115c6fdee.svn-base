// JavaScript Document
//yeleft
//ywright

$(function(){
	var indexLen=$("#justforshow blockquote").length;
	var showingNow=4;
	var myWidth=223;//就是BLACKQUOTE加上PADDING的宽度
	var myTotal=indexLen-showingNow;
	var myCount=0;
	
	$("#yeleft").click(function(){
		if (myCount<myTotal){
			myCount++;
			$("#justforshow").animate({"margin-left":-myCount*myWidth},300);
		}
	});
	$("#ywright").click(function(){
		if (myCount>0){
			myCount--;	
			$("#justforshow").animate({"margin-left":-myCount*myWidth},300);
		}
	});
	
	var scrollLen=$(".jfjlb_ztjlb_box_div .myscroll ul").length;
	var myShowScroll=6;
	var myScrollWidth=148;
	var scrollTotal=scrollLen-myShowScroll;
	var scrollCount=0;
	
	$(".jfjlb_ztjlb_box_jt:eq(0) img").click(function(){
		if (scrollCount<scrollTotal){
			scrollCount++;
			$(".jfjlb_ztjlb_box_div .myscroll:eq(0)").animate({"margin-left":-myScrollWidth*scrollCount},300);
		}
	});
	$(".jfjlb_ztjlb_box_jt:eq(1) img").click(function(){
		if (scrollCount>0){
			scrollCount--;
			$(".jfjlb_ztjlb_box_div .myscroll:eq(0)").animate({"margin-left":-myScrollWidth*scrollCount},300);
		}
	});
	
	
});