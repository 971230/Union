<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
a{
TEXT-DECORATION:none;
}

ul {
list-style-type:none;
}
.mt {
margin-top: 10px;
}
.powerbox h1 {
background: #f5f5f5;
font: bold 14px/36px "";
height: 36px;
width: 100%;
}
.powerbox h1 span {
float: left;
padding: 0 0 0 10px;
}
.powerbox h1 .p_t_r {
float: right;
font: 12px/36px "";
height: 36px;
}
.powercon {
padding: 10px;
line-height: 24px;
}
.unit, .unit_current {
margin-left:5px;
display: block;
width: 100%;
}
#sckz ul{
cursor:hand;
cursor:pointer;
}
</style>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>素材列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="material!getList.do" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>素材名称：</th>
					<td><input type="text" name="material.aname"
						id="app_name" value="${material.aname}" class="searchipt" /></td>
					<th><input type="hidden" name="material.atype"
						id="atype_hidden_input" value="" /></th>
					<td><input type="hidden" name="material.user_id"
						id="user_id_hidden_input" value="" /></td>
					<%-- <th>素材类别：</th>
					<td><select name="material.staff_no" value="${material.staff_no }" class="searchipt" >
							<option value="">--请选择--</option>
							<option value="1">公有素材</option>
							<option value="0">私有素材</option>
						</select>
						<input type="hidden" value="${material.staff_no }" id="listIsPubReq" />
					</td> --%>
					<td style="text-align:center;"><a href="javascript:void;"
						onclick="document.forms.qryForm.submit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="comBtnDiv">
		<a href="material!detail.do" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a href="javascript:void(0);" style="margin-right:10px;display:none;" class="graybtn1"><span>审核</span></a>
	</div>
	<div class="powerbox mt" style="margin-top:5px;margin-left:5px;width:9%;display: inline;float:left;border-bottom:solid 0px #cdcdcd; border-left:solid 0px #cdcdcd; border-right:solid 1px #cdcdcd; border-top:solid 1px #cdcdcd">
		<div class="powercon" style="height:320px;">
			<div class="right_box">
				<div class="unit" id="sckz">
					<b>公共素材</b>
					<ul value="1" class="list_ul" style="margin-left:20px;">
						<li class="leve" value="1"><a href="javascript:void(0);" title="视频类"></a>视频类</li>
						<li class="leve" value="2"><a href="javascript:void(0);" title="图片类"></a>图片类</li>
						<li class="leve" value="3"><a href="javascript:void(0);" title="应用类"></a>应用类</li>
						<li class="leve" value="4"><a href="javascript:void(0);" title="新闻类"></a>新闻类</li>
					</ul>
					<b>私有素材</b>
					<ul value="0" class="list_ul"  style="margin-left:20px;">
						<li class="leve" value="1"><a href="javascript:void(0);" title="视频类"></a>视频类</li>
						<li class="leve" value="2"><a href="javascript:void(0);" title="图片类"></a>图片类</li>
						<li class="leve" value="3"><a href="javascript:void(0);" title="应用类"></a>应用类</li>
						<li class="leve" value="4"><a href="javascript:void(0);" title="新闻类"></a>新闻类</li>
					</ul>
					<div class="clearBoth"></div>
				</div>
			</div>
		</div>
	</div>
	<div style="width:90%;float:right;">
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>素材名称</grid:cell>
				<grid:cell>素材类别</grid:cell>
				<grid:cell>素材URL</grid:cell>
				<grid:cell>素材内容</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell><input type="checkbox" value="${obj.aid}" /></grid:cell>
				<grid:cell>${obj.aname}</grid:cell>
				<grid:cell><c:if test="${obj.staff_no==0}">私有素材</c:if><c:if test="${obj.staff_no==1}">公有素材</c:if> </grid:cell>
				<grid:cell>${obj.url }</grid:cell>
				<grid:cell>${obj.contact }</grid:cell>
				
				<grid:cell><a href="material!detail.do?material.aid=${obj.aid}">修改</a>
				<c:if test="${founder==0 || founder==1}">|<a href="javascript:void(0);" class="delBtn" aid="${obj.aid}">删除</a></c:if><!-- 超级所有都出来 -->
				
				<c:if test="${(founder==3 || founder==4) && obj.staff_no !=1}">|<a href="javascript:void(0);" class="delBtn" aid="${obj.aid}">删除</a></c:if>
				
				</grid:cell>
			</grid:body>

		</grid:grid>
		
	</form>
	</div>
</div>
<div id="del_app_div"></div>
<script type="text/javascript">
$(function(){
	$("#modifyBtn").bind("click",function(){
		var len = $("#gridform").find("input[type='checkbox']:checked").length;
		if(len>0){
			var id=$("#gridform").find("input[type='checkbox']:checked").eq(0).val();
			if(len>1){
				alert("提示：选择多条记录，默认修改第一条。");
			}
			$(this).attr("href","material!detail.do?material.aid="+id);
			//var url = ctx + "/shop/admin/cms/material/material!detail.do?material.id="+id;
			//Cmp.ajaxSubmit('del_app_div', '', url, {}, MaterialList.delJsonBack,'html');
		}else{
			alert("请选择要修改的记录！");
		}
	});
	$(".delBtn").bind("click",function(){
		if(confirm("您确定要删除此条记录？")){
			var id=$(this).attr("aid");
			var url = "material!deleteMaterial.do?ajax=yes&ids="+id;
			Cmp.ajaxSubmit('del_app_div', '', url, {}, MaterialList.delJsonBack,'json');
		}
	});
	//还原搜索项
	//$("#listIsPubReq").parent().find("select").find("option[value='"+$("#listIsPubReq").val()+"']").attr("selected","selected");
	//点击快捷类型
	$("#sckz").find("li").each(function(i,data){
		$(data).bind("click",function(){
			$("#atype_hidden_input").val($(this).attr("value"));
			$("#user_id_hidden_input").val($(this).parent().attr("value"));
			document.forms.qryForm.submit();
		});
	});
})

var MaterialList = {
	delJsonBack:function(responseText){
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	}
}
</script>
