<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<div>
<form  class="validate" method="post" name="initPolicyform" id="initPolicyform">
  
  <div class="submitlist" align="left">
		  <table>
			<tr>
			 <td> 
		     	<input  type="button" id="initPolicyConfirm"	onclick="initPolicy()"  value="一键初始化策略"  class="submitBtn"  />
		     </td>
		     <td> 
		     	<input  type="button" id="initRunPolicyConfirm"	onclick="initRunPolicy()"  value="一键同步数据库的状态到tenginx"  class="submitBtn"  />
		     </td>
		     <td> 
		     	 解耦开关状态：<span id="separate_status" style="color: red" ></span>
		     	<input  type="button" id="separateOnConfirm"	onclick="separateSet('yes')"  value="开"  class="submitBtn"  />
		     	<input  type="button" id="separateOffConfirm"	onclick="separateSet('no')"  value="关"  class="submitBtn"  />
		     </td>
		   </tr>
		  </table>
	</div> 
</form>
	<div class="grid" id="cfglist">
	    <form method="POST" id="rStrategyCfgFrm" >
	        <grid:grid from="webpage" formId="serchform" action="rGrategyAdmin.do">
	            <grid:header>
	               <grid:cell width="250px">ID</grid:cell>
	                <grid:cell width="250px">适用环境</grid:cell>
	                <grid:cell width="250px">策略名称</grid:cell>
	                <grid:cell width="100px">策略id</grid:cell>
	                <%-- <grid:cell width="200px">策略配置说明</grid:cell> --%>
	                <grid:cell width="100px">运行状态</grid:cell>
	                <grid:cell width="100px">灰度时机</grid:cell>
	                <%-- <grid:cell width="200px">运行功能说明</grid:cell> --%>
	                <grid:cell width="200px">操作</grid:cell>
	            </grid:header>
	            <grid:body item="cfg">
	            <grid:cell>&nbsp;${cfg.cfg_id}</grid:cell>
	                <grid:cell>&nbsp;${cfg.env}</grid:cell>
	                <grid:cell>&nbsp;${cfg.grategy_name}</grid:cell>
	                <grid:cell>&nbsp;${cfg.policyid}</grid:cell>
	                <%-- <grid:cell>&nbsp;${cfg.instruction}</grid:cell> --%>
	                <grid:cell>
						<c:if test="${cfg.run_status=='00A'}"><span style="color: red">运行</span></c:if>
	                	<c:if test="${cfg.run_status=='00X'}">挂起</c:if>
	                </grid:cell>
	                <grid:cell><c:if test="${cfg.gray_moment=='all_gray'}">全灰</c:if>
	                	<c:if test="${cfg.gray_moment=='part_gray'}">部分灰</c:if>
	                </grid:cell>
	                <%-- <grid:cell>&nbsp;${cfg.remarks}</grid:cell> --%>
	                <grid:cell>
						<c:if test="${cfg.run_status==null}"><a title ="切换运行状态" onclick="operPolicy('${cfg.cfg_id}','${cfg.env_url}','set','${ cfg.env_type}','${cfg.env_code}','${cfg.policyid}','${cfg.gray_moment}')" class="p_prted">设置</a></c:if>
	                	<c:if test="${cfg.run_status=='00A'}"><span title ="切换运行状态" >挂起</span></c:if>
	                	<c:if test="${cfg.run_status=='00X'}"><a title ="切换运行状态" onclick="operPolicy('${cfg.cfg_id}','${cfg.env_url}','run','${ cfg.env_type}','${cfg.env_code}','${cfg.policyid}','${cfg.gray_moment}')" class="p_prted">运行</a></c:if>
					</grid:cell>
	            </grid:body>
	        </grid:grid>
	    </form>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){ 
	qrySeparateOnOff();
});

	function operPolicy(cfg_id,env_url,run_status,env_type,env_code,policyid,gray_moment){
    	var self =this;
	    if(window.confirm('确认要切换此状态？')){
	    	
	    	var url =  "rGrategyAdmin!operPolicy.do?ajax=yes&cfg_id="+cfg_id+"&env_url="+env_url+"&run_status="+run_status+"&env_type="+env_type+"&env_code="+env_code+"&policyid="+policyid+"&gray_moment="+gray_moment;
		    Cmp.ajaxSubmit('rStrategyCfgFrm', '', url, {},  function jsonback(responseText) { // json回调函数
				if (responseText.result == 1) {
					alert(responseText.message);
				    window.location.reload();
				}
				if (responseText.result == 0) {
					alert(responseText.message);	
				    window.location="rGrategyAdmin.do";
				}
			}, 'json');

		}
	} 
	
	
	
	
	function initPolicy(){
    	var self =this;
	    if(window.confirm('确认重置tenginx的所有策略？')){
	    	
	    	var url =  "rGrategyAdmin!initPolicy.do?ajax=yes";
		    Cmp.ajaxSubmit('initPolicyform', '', url, {},  function jsonback(responseText) { // json回调函数
				if (responseText.result == 1) {
					alert(responseText.message);
				    window.location.reload();
				}
				if (responseText.result == 0) {
					alert(responseText.message);	
				    window.location="rGrategyAdmin.do";
				}
			}, 'json');

		}
	} 
	function initRunPolicy(){
    	var self =this;
	    if(window.confirm('确认同步运行时策略到tenginx？')){
	    	
	    	var url =  "rGrategyAdmin!initRunPolicy.do?ajax=yes";
		    Cmp.ajaxSubmit('initPolicyform', '', url, {},  function jsonback(responseText) { // json回调函数
				if (responseText.result == 1) {
					alert(responseText.message);
				    window.location.reload();
				}
				if (responseText.result == 0) {
					alert(responseText.message);	
				    window.location="rGrategyAdmin.do";
				}
			}, 'json');

		}
	} 
	
	function separateSet(separate){
    	var self =this;
	    if(window.confirm('确认更改解耦开关状态吗？')){
	    	var url =  "rGrategyAdmin!separateSet.do?ajax=yes&separate_status="+separate;
		    Cmp.ajaxSubmit('initPolicyform', '', url, {},  function jsonback(responseText) { // json回调函数
				if (responseText.result == 1) {
					alert(responseText.message);
				    window.location.reload();
				}
				if (responseText.result == 0) {
					alert(responseText.message);	
				    window.location="rGrategyAdmin.do";
				}
			}, 'json');

		}
	}
	function qrySeparateOnOff(){
    	var self =this;
	    	var url =  "rGrategyAdmin!qrySeparateOnOff.do?ajax=yes";
		    Cmp.ajaxSubmit('initPolicyform', '', url, {},  function jsonback(responseText) { // json回调函数
				if (responseText.result == 1) {
					var status =responseText.message;
					if(responseText.message == 'yes'){
						status = '开';
						$("#separateOnConfirm").hide();
					}else if (responseText.message == 'no'){
						status = '关';
						$("#separateOffConfirm").hide();
					}
					$("#separate_status").text(status);
				}
				if (responseText.result == 0) {
					$("#separate_status").text(responseText.message);
				}
			}, 'json');
	}
</script>