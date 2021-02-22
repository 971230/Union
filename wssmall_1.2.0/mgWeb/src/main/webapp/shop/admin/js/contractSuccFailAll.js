
var ContractCount = $.extend({}, Eop.Grid, {init:function () {
	var self = this;
	$("a[name='succ_count']").bind("click", function () {
		var to_user_id = $(this).attr("to_user_id");
		var lan_id = $(this).attr("lan_id");
		var username = $(this).attr("username");
		var startTime = $("#starttime").attr("value");
		var endTime = $("#endtime").attr("value");
		window.location = "contract!findSuccFailCount.do?state=succ&userid="+to_user_id+"&startTime=" + startTime + "&endTime=" + endTime + "&username=" + username + "&lan_id=" + lan_id + "&show_type=detail";
	});
	$("a[name='fail_count']").bind("click", function () {
		var to_user_id = $(this).attr("to_user_id");
		var lan_id = $(this).attr("lan_id");
		var username = $(this).attr("username");
		var startTime = $("#starttime").attr("value");
		var endTime = $("#endtime").attr("value");
		window.location = "contract!findSuccFailCount.do?state=fail&userid="+to_user_id+"&startTime=" + startTime + "&endTime=" + endTime + "&username=" + username + "&lan_id=" + lan_id + "&show_type=detail";
	});
	$("a[name='all_count']").bind("click", function () {
	var to_user_id = $(this).attr("to_user_id");
		var lan_id = $(this).attr("lan_id");
		var username = $(this).attr("username");
		var startTime = $("#starttime").attr("value");
		var endTime = $("#endtime").attr("value");
		window.location = "contract!findSuccFailCount.do?userid="+to_user_id+"&startTime=" + startTime + "&endTime=" + endTime + "&username=" + username + "&lan_id=" + lan_id+ "&show_type=detail";
	});
}});
$(function () {
	ContractCount.init();
});

