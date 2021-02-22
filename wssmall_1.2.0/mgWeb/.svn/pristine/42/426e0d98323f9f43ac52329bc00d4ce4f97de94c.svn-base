<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>关键字统计</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<div class="searchformDiv">
		<form action="" method="post" id="order_exp_count_f">
				<table id="params_tb" width="100%" border="0" cellspacing="0"
					cellpadding="0" class="tab_form">
					<tr>
						<th>统计区间：</th>
						<td>
						<input type="text" id="start_time" name="eisInner.start_time" value="${eisInner.start_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_time\')}'})">-
						<input type="text" id="end_time" name="eisInner.end_time" value="${eisInner.end_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_time\')}'})">
						</td>
						<th>关键字内容：</th>
						<td>
						<input type="text" name="eisInner.match_content" value="${eisInner.match_content }" id="match_content" style="width:200px;" class="ipt_new">	
						</td>				
						<td>
						<a href="javascript:void(0);" id="count_query" class="dobtn" style="margin-left:5px;">统计</a>
						</td>
						<td></td>
					</tr>
				</table>
			</form>
			</div>
        <div class="grid">
        <form action="" id="orderexp_spec_list_fm" class="grid_w" method="post">
        	<grid:grid from="webpage" formId="order_exp_count_f">
				<grid:header>
					<grid:cell style="width:200px;">关键字类型</grid:cell>
					<grid:cell style="width:200px;">关键字名称</grid:cell>
					<grid:cell style="width:350px;">关键字内容</grid:cell>
					<grid:cell style="width:100px;">数量</grid:cell>
					<grid:cell style="width:150px;">操作</grid:cell>
				</grid:header>
				<grid:body item="expSpecCount">
			  	    <grid:cell>${expSpecCount.search_code }</grid:cell>
			  	    <grid:cell>${expSpecCount.search_name }</grid:cell>
			  	    <grid:cell><div  style="width:350px;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                            			<xmp>${expSpecCount.match_content}</xmp>
									</div></grid:cell>
			  	    
			  	    <grid:cell>${expSpecCount.c }</grid:cell>
			  	    <grid:cell><a name="specview" key_id='${expSpecCount.key_id }' match_content='${expSpecCount.match_content }'>查看图表</a></grid:cell>
			  </grid:body>
	      	</grid:grid>
        </form>
     </div>
  </div>
</div>
<div id="countview" style="display:none"></div>
<script type="text/javascript">
$(function(){
	$("#count_query").bind("click",function(){
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		if(start_time==""){
			alert("统计开始日期不能为空");
			return;
		}
		if(end_time==""){
			alert("统计结束日期不能为空");
			return;
		}
		$.Loading.show("正在加载所需内容，请稍侯...");
		$("#order_exp_count_f").attr("action",ctx+"/shop/admin/orderExp!specList.do").submit();
	});
	$("a[name=specview]").bind("click",function(){
		var $this = $(this);
		var key_id = $(this).attr("key_id");
		var match_content = $(this).attr("match_content");
		var start_time = '${eisInner.start_time }';
		var end_time = '${eisInner.end_time }';
		var url = ctx+"/shop/admin/orderExp!specViewList.do?"
		url = url +"key_id="+key_id+"&start_time="+start_time+"&end_time="+end_time;
		window.location.href=url;
	});
});
</script>
</body>
</html>