<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/rule/css/tree.css" type="text/css" />
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/tree.js"></script>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/search_rule_cond.js"></script>
<script src="<%=request.getContextPath()%>/shop/admin/rule/js/search_attr_factory.js" type="text/javascript"></script>
<!-- 搜索条件弹出框 -->
<div id="show_search_dlg"></div>
<div class="dzSearch" id="rule_search">
	<!-- 规则名称： <span class="newIpts"> <i class="ic_search"></i> <input
		type="text" name="textfield" id="searchname" value="" />
	</span>  -->
	方案名称：
	<select id="plan_name_id">
		<option value="-1">--请选择方案--</option>
		<c:if test="${!empty ruleObjPlanList }">
			<c:forEach items="${ruleObjPlanList }" var="ruleObjPlanVo">
				<option value="${ruleObjPlanVo.plan_id }">${ruleObjPlanVo.plan_name }</option>
			</c:forEach>
		</c:if>
	</select>
	目录名称：
	<select id="dir_name_id">
		<option value="-1">--请选择目录--</option>
		<c:if test="${!empty ruleObjDirList}">
			<c:forEach items="${ruleObjDirList }" var="ruleObjDirVo">
				<c:if test="${ruleObjDirVo.id != '0' }"> <!-- id==0是根目录 -->
					<option value="${ruleObjDirVo.id }">${ruleObjDirVo.name }</option>
				</c:if>
			</c:forEach>
		</c:if>
	</select>
	<a class="graybtn" href="javascript:void(0);" cond_type="cal_cond" name="add_search" rule_id="${rule_id}"><i class="ic_add"></i>搜索</a>
</div>
<div class="gridWarp">
	<div class="treeBox" id="rule_page_left">
		<div class="tree" id="tree_div" style="height:1200px; overflow:auto;"></div>
		<div style="display: none;" class="tree" id="search_tree_div"></div>
	</div>
	<div class="treeRightCon" id="rule_page_right"></div>
	<div class="clear"></div>
</div>
<div class="contextMenu" id="rightMenu">
	<ul id="menu0" style="display: none;">
		<li><em class=""></em><a id="chakanmulu" href="javascript:void(0);">查看目录</a></li>
	    <li><em class=""></em><a id="xinzengmulu" href="javascript:void(0);">新增目录</a></li>
	    <li><em class=""></em><a id="xiugaimulu" href="javascript:void(0);">修改目录</a></li>
	</ul>
	<ul id="menu1" style="display: none;">
		<li><em class=""></em><a id="chakanmulu1" href="javascript:void(0);">查看目录</a></li>
	    <li><em class=""></em><a id="xinzengfangan" href="javascript:void(0);">新增方案</a></li>
	    <li><em class=""></em><a id="xiugaimulu1" href="javascript:void(0);">修改目录</a></li>
	</ul>
	<ul id="menu2" style="display: none;">
		<li><em class=""></em><a id="chakanfangan" href="javascript:void(0);">查看方案</a></li>
	    <li><em class=""></em><a id="xinzengguize" href="javascript:void(0);">新增规则</a></li>
	    <li><em class=""></em><a id="xiugaifangan" href="javascript:void(0);">修改方案</a></li>
	    <li><em class=""></em><a id="bianjiguize" href="javascript:void(0);">编辑规则</a></li>
	    <li><em class=""></em><a id="shanchufangan" href="javascript:void(0);">删除方案</a></li>	    
	</ul>
	<ul id="menu3" style="display: none;">
		<li><em class=""></em><a id="chakanguize" href="javascript:void(0);">查看规则</a></li>
	    <li><em class=""></em><a id="xinzengguize0" href="javascript:void(0);">新增规则</a></li>
	    <li><em class=""></em><a id="xiugaiguize" href="javascript:void(0);">修改规则</a></li>
	    <li><em class=""></em><a id="bianjiziguize" href="javascript:void(0);">编辑子规则</a></li>
	    <li><em class=""></em><a id="shanchuguize" href="javascript:void(0);">删除规则</a></li>
	</ul>
</div>
<input type="hidden" id="planid" value="" /> 
<input type="hidden" id="nodeid" value="" />
<div id="showDlg"></div>
<script type="text/javascript">
(function($,h,c){var a=$([]),e=$.resize=$.extend($.resize,{}),i,k="setTimeout",j="resize",d=j+"-special-event",b="delay",f="throttleWindow";e[b]=250;e[f]=true;$.event.special[j]={setup:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.add(l);$.data(this,d,{w:l.width(),h:l.height()});if(a.length===1){g()}},teardown:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.not(l);l.removeData(d);if(!a.length){clearTimeout(i)}},add:function(l){if(!e[f]&&this[k]){return false}var n;function m(s,o,p){var q=$(this),r=$.data(this,d);r.w=o!==c?o:q.width();r.h=p!==c?p:q.height();n.apply(this,arguments)}if($.isFunction(l)){n=l;return m}else{n=l.handler;l.handler=m}}};function g(){i=h[k](function(){a.each(function(){var n=$(this),m=n.width(),l=n.height(),o=$.data(this,d);if(m!==o.w||l!==o.h){n.trigger(j,[o.w=m,o.h=l])}});g()},e[b])}})(jQuery,this); 
</script>
<script>
$(function() {
	RuleCond.init();
	var hight = $(".gridWarp").height(); //高度
	$("#rule_page_right").resize(function() {
		/* var c = $("#rule_page_right");
		var h = c.height();
		var top = $("#tree_div").scrollTop();
		if(h>500){
			$("#tree_div").css("overflow", "auto");
			$("#tree_div").css("height", h+"px");
			$("#tree_div").scrollTop(top);
		}
		else{
			$("#tree_div").css("overflow", "auto");
			$("#tree_div").css("height", "500px");
		} */
	});
	/* style="width:200px; height:200px; overflow:auto; " */
});
</script>
