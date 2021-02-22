<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="rule_config_form" action="${pageContext.request.contextPath}/shop/admin/rule!ruleConfigList.do" >
<div class="searchformDiv">
<table class="form-table">
	<tr>
	<th>
		规则编码：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="rule_code"  value="${rule_code }"/>
		</td>
		<th>
		规则名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="rule_name"  value="${rule_name }"/>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" id="rule_config_searchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div class="comBtnDiv">
	<a href="${pageContext.request.contextPath}/shop/admin/rule!showConfigAdd.do" style="margin-right: 10px;" class="graybtn1"><span>添加</span></a>
<!-- 	<a href="javascript:void(0);return false;" id="reRuleObj" style="margin-right: 10px;" class="graybtn1"><span>刷新Fact对象</span></a> -->
</div>
		
<div class="grid">
<grid:grid  from="ruleConfigList" >
	<grid:header>
		<grid:cell >规则编码</grid:cell>
		<grid:cell >规则名称</grid:cell>
		<%-- <grid:cell >资源文件类型</grid:cell> --%>
		<grid:cell >描述</grid:cell> 
		<grid:cell >实现方式</grid:cell> 
		<grid:cell >生效时间</grid:cell>
		<grid:cell >失效时间</grid:cell> 
		<grid:cell >状态</grid:cell>
		<grid:cell >审核状态</grid:cell> 
		<grid:cell >创建时间</grid:cell> 
		<grid:cell >操作</grid:cell>
	</grid:header>
    <grid:body item="rc">
    	<grid:cell>${rc.rule_code}</grid:cell>
        <grid:cell>${rc.rule_name}</grid:cell>
        <%-- <grid:cell>${rc.resource_type=='DRL'?'规则流':'未知类型'}</grid:cell> --%>
        <grid:cell>${rc.rule_desc}</grid:cell>
        <grid:cell>${rc.impl_type=='CD'?'编码实现':'界面配置'}</grid:cell>
        <grid:cell>${rc.eff_date}</grid:cell>
        <grid:cell>${rc.exp_date}</grid:cell>
        <grid:cell>
        	<c:if test="${rc.status_cd=='00N' }">新建</c:if>
        	<c:if test="${rc.status_cd=='00X' }">无效</c:if>
        	<c:if test="${rc.status_cd=='00A' }">有效</c:if>
        	<c:if test="${rc.status_cd=='00M' }">审核中</c:if>
        </grid:cell>
        <grid:cell>
        	<c:if test="${rc.audit_status=='00N' }">未提交审核</c:if>
        	<c:if test="${rc.audit_status=='00X' }">审核不通过</c:if>
        	<c:if test="${rc.audit_status=='00A' }">审核通过</c:if>
        	<c:if test="${rc.audit_status=='00E' }">未提交审核</c:if>
        	<c:if test="${rc.audit_status=='00S' }">审核中</c:if>
        </grid:cell>
        <grid:cell>${rc.create_date}</grid:cell>
        <grid:cell>
        	<a href="${pageContext.request.contextPath}/shop/admin/rule!showConfigAdd.do?action=1&rule_id=${rc.rule_id}">修改</a>
        	<c:if test="${rc.audit_status=='00N'||rc.audit_status=='00E' }">
        		<a href="javascript:void(0);" name="s_audit_btn" rule_id="${rc.rule_id}">提交审核</a>
        	</c:if>
        </grid:cell>
    </grid:body>  
    <tr id="iframe_tr" style="display: none;">
  	<td colspan="11">
        <iframe style="width: 100%;height: 400px;" src="">
        </iframe>
    </td>
   </tr>
</grid:grid>  
<div style="clear:both;padding-top:5px;"></div>
</div>
</form>	


<div id="s_rule_dialog"></div>

<script type="text/javascript">
$(function(){
	Eop.Dialog.init({id:"s_rule_dialog",modal:false,title:"提交审核", height:"650px",width:"700px"});
	
	$("#rule_config_searchBtn").bind("click",function(){
		rule_config_form.submit();
	});
	
	/* $("#reRuleObj").bind("click",function(){
		if(window.confirm("确定要刷新Fact对象？")){
			var url = ctx+"/shop/admin/rule!reScanObjInfo.do?ajax=yes";
			$.ajax({
				url:url,
				type:'get',
				dataType:'json',
				success:function(data){
					alert(data.msg);
				},error:function(a,b){
					alert("失败");
				}
			});
		}
	}); */
	
	$("a[name=s_audit_btn]").bind("click",function(){
		var ruleid=$(this).attr("rule_id");
		if(window.confirm("确定要提交审核？")){
			var url = ctx+"/shop/admin/rule!sAudit.do?ajax=yes&rule_id="+ruleid;
			$.ajax({
				url:url,
				type:'get',
				dataType:'json',
				success:function(data){
					alert(data.msg);
					if(data.status==0){
						window.location.reload();
					}
				},error:function(a,b){
					alert("失败");
				}
			});
		}
	});
});
</script>
