var changeLog = {
	init : function() {
		var self = this;
		$("#searchBtn").click(function() {
			var beginTime = $('#beginTime').val();
			var endTime = $('#endTime').val();
			var userid = $('#userid').val();
			var url = ctx+ "/shop/admin/freeze!changeLogList.do?ajax=yes&userid=" + userid + "&beginTime="+ beginTime + "&endTime=" + endTime;
			/*Cmp.asExcute('changeLogList', url, {}, function(responseHtml){
				
			}, "html");*/
			Cmp.load('changeLogList', url);
		});
	}
}
$(function() {
	changeLog.init();
});