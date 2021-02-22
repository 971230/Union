<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
	.treeList li.curr {
		background: #def1ff;
	}
	.clk{
		cursor: pointer;
	}
	
	/*定义列表容器中所有公用元素样式，需要重定义的，在下面再重新定义*/
	.cms_grid ul {
		list-style-type: none;
		text-align: center;
	}
	
	.cms_grid ul li {
		float: left;
	}
	
	.cms_grid .page {
		padding: 5px 0 0 10px;
	}
	
	.cms_grid .page .info {
		border: 1px solid #cdcdcd;
		background: #f8f8f8;
		padding: 3px 6px;
		margin: 0px 2px 2px 2px;
		color: #444;
		float: left;
	}
	
	/*定义列表容器中分页的样式，使用ul元素*/
	.cms_grid .page ul {
		color: #FFF;
		float: right;
		line-height: 30px;
	}
	
	/*定义列表容器中分页的样式，使用li元素*/
	.cms_grid .page ul li {
		
	}
	
	/*定义列表容器中分页链接的样式，使用li元素*/
	.cms_grid .page li a {
		border: 1px solid #cdcdcd;
		background: #f8f8f8;
		padding: 2px 6px;
		margin: 2px;
		color: #444;
		text-decoration: none;
		min-width: 28px;
		width: auto;
	}
	
	/*定义列表容器中选中页的样式，使用li元素*/
	.cms_grid .page .selected {
		font-weight: bold;
		color: #FFF;
		background: #6497d4;
		border: 1px solid #6497d4;
	}
	.searchformDiv table th {
		width: 70px;
	}


/*4月10日新增样式*/
.remind_new{ border:1px solid #ffcc00; background:#fcf9e5 url(../images/remind.png) 10px center no-repeat; height:34px; line-height:34px; color:#9e5701; padding-left:30px; position:relative;}
.remind_new .remind_close{ background:url(../images/remind_close.gif) no-repeat; width:16px; height:16px; position:absolute; right:10px; top:50%; margin-top:-8px; text-indent:-9999px; overflow:hidden; display:block;}
.mtt{ margin-top:10px;}
.mk_div{}
.mk_div .mk_title{ background:#6497d4; height:27px; line-height:27px; position:relative;}
.mk_div .mk_title h2{ color:#fff; font-weight:bold; padding-left:10px; line-height:27px;}
.mk_div .mk_content{ border:1px solid #d8d8d8; border-top:none; padding:10px;}
.mk_div .mk_blue_content{ border:1px solid #6497d4; border-top:none; padding:10px;}
.mk_div .mk_content .item{ padding-top:4px; padding-bottom:4px;}
.mk_div .mk_content .item span{ width:75px; float:left; color:#666; font-weight:bold; height:22px; line-height:22px;}
.mk_div .mk_content .item ul{ width:670px; float:left;}
.mk_div .mk_content .item ul li{ margin-right:3px; float:left;}
.mk_div .mk_content .item ul li a{ display:block; height:20px; line-height:20px; padding-left:10px; padding-right:10px; border:1px solid #e6e6e6; color:#444;}
.mk_div .mk_content .item ul li.curr a, .mk_div .mk_content .item ul li a:hover{ border:1px solid #ff9000; background:url(../images/class_sel.png) right bottom no-repeat;}

.mb_list_table{ padding-left:20px;}
.mb_list_table table td{ width:230px; padding-left:5px; padding-right:5px; padding-top:5px; height:300px; background:#f7f7f7;border:13px solid #fff;}
.mb_list_table table td .mb_img{ width:230px; height:230px; text-align:center;margin:0 auto; background:#e8e7e7; margin-top:5px;display: table-cell;vertical-align:middle;}
.mb_list_table table td .mb_img img{ vertical-align:middle;}
.mb_list_table table td p{ text-align:center; color:#444; line-height:30px;}
.mb_list_table table td a{ color:#444;}
.mb_list_table table td span{ margin-left:3px; margin-right:3px;}

.pro_list_table{ padding-left:10px;}
.pro_list_table table{}
.pro_list_table table td{ width:150px; border:5px solid #fff;}
.pro_list_table table td a{ display:block; padding-top:5px; padding-left:5px; padding-right:5px; color:#222; border:1px solid #e8e7e7;}
.pro_list_table table td .pro_img{ width:140px; height:140px;}
.pro_list_table table td p{ line-height:20px;}

.preview_div{ position:absolute; right:0px; top:3px;}
.mb_content{}
.mb_table{ line-height:30px; color:#666;}
.mb_table a{ color:#333;}

</style>
<div id='left_panel' style='float:left;width:20%;height: 460px;overflow-Y: auto; display: none;'>
	<ul class="treeList">
		<c:forEach items="${moduleList }" var="mw" varStatus="status">
			<li class="last ${status.index==0?'curr':'' } clk" name="left_menu_li" module_id="${mw.module_id }">
				<a><span></span>${mw.module_name }</a>
			</li>
		</c:forEach>	
	</ul>
</div>

<!-- <div style='float:right;width:79%;'> -->
<div>
	<div class="plugin_set_content_img-attr">
		<div class="has_height">
			<div class="plugin_set_content_img-attr_title">栏目标题：</div>
			<div class="plugin_set_content_img-attr_menu">
				<input type="radio" name="title_show_set" value="1" />有
				<input class="menu_text" name="title_set" value="${data.Title}" />
				<input type="radio" name="title_show_set" value="0" />无
			</div>
		</div>
		<div class="has_height">
			<div class="plugin_set_content_img-attr_title">边框：</div>
			<div class="plugin_set_content_img-attr_menu">
				<input type="radio" name="border_show_set" value="1" />有
				<input type="radio" name="border_show_set" value="0" />无
			</div>
		</div>
		<div class="has_height">
			<div class="plugin_set_content_img-attr_title">约束大小：</div>
			<div class="plugin_set_content_img-attr_menu">
				<input type="radio" name="is_wrapper" value="1" />是
				<input type="radio" name="is_wrapper" checked="checked" value="0" />否
			</div>
		</div>
	</div>
		
	<div id='right_panel'>
		<div class='mk_content'>
			<form method='post' id='cms_plugin_form'>
				<div class='searchformDiv'>
					<table width='100%' border='0' cellspacing='0' cellpadding='0'>
						<tbody>
							<tr style="padding-top: 7px;">
								<th>名称：</th>
								<td><input id="widgetname_tx" type='text' name='widget_name' value='${widget_name}'
									class='searchipt' style="width: 100px;">
									<input id="widget_module_id" type="hidden" name="module_id" value="${module_id }" /> 
								</td>
								<td style='text-align:center;'><a href='javascript:void(0);'
									class='searchBtn' id='search_plugin_btn'><span>查&nbsp;询</span></a></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="plugin_list_div" class='cms_grid pro_list_table mtt'>
					<grid:grid from='webpage' ajax='yes' formId="cms_plugin_form">
						<table cellpadding='0' cellspacing='0'>
							<tbody>
								<c:forEach items='${webpage.data}' var='data' varStatus='index'>
									<c:if test='${index.first }'>
										<tr>
									</c:if>
									<td style="width: 159px;height: 159px;">
									    <a name="widtet_plugin_a_btn" href='javascript:void(0);'>
											<div class='pro_img'>
												<img src='${staticserver}${data.widget_image}' width='140' height='140'>
											</div>
											<p style="text-align: center;">
												<input type='radio' name='widget_id' widget_image="${data.widget_image }" value="${data.widget_id}" />
												${data.widget_name}
											</p>
									</a></td>
									<c:if test='${(index.index + 1) % 5 == 0 }'>
										</tr>
										<tr>
									</c:if>
									<c:if test='${index.end }'>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</grid:grid>
				</div>
			</form>
		</div>
	</div>
</div>

<div class="popup_button_box">
    <ul class="set_button_left">
        <li></li>	
    </ul> 
    <ul class="set_button_right">
		<li><div class="set_button"  id="button_cancel">取消</div></li>
		<li><div class="set_button"  id="button_save">保存</div></li>
 	</ul>
</div>
    

<script type="text/javascript">
	$(function(){
		$("#plugin_list_div table tbody tr").unbind("click");
		$("li[name=left_menu_li]").unbind("click").bind("click",queryPage);
		$("#search_plugin_btn").unbind("click").bind("click",queryPage);
		jQuery("a[name=widtet_plugin_a_btn]").live("click",function(){
			jQuery(this).find("input[type='radio']").attr("checked","checked");
		});
		jQuery(".info").hide();
		var s_height = $("div.plugin_set_content_img-attr").height();
		$("#cms_plugin_form div.searchformDiv").height(s_height);
	});
	function queryPage(){
		var $this = $(this);
		var id = $this.attr("id");
		var url = ctx+"/shop/admin/cmsPlugin!queryPlugins.do?ajax=yes&action=right";
		var params = {};
		if("search_plugin_btn"==id){
			params["widget_name"]=$("#widgetname_tx").val();
			params["module_id"] = $("#widget_module_id").val();
		}else{
			var module_id = $this.attr("module_id");
			params["module_id"]=module_id;
		}
		$("#right_panel").load(url,params,function(data){
			$this.siblings("li").removeClass("curr");
			$this.addClass("curr");
			$("#search_plugin_btn").unbind("click").bind("click",queryPage);
		});
	}
</script>