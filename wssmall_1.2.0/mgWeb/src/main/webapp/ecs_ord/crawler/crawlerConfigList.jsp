<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
<div id="crawlerBody"  >
<div ><h1>基础数据</h1></div>
<form method="post" id="basedata_from" action=''>
	<div class="searchformDiv">
		<table cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>目标地址：</th>
					<td><input type="text" class="ipttxt" id="address" name="address" is_out_id_check="${crawlerConfig.is_out_id_check }" proxy_type="${crawlerConfig.proxy_type}" cfg_id="${crawlerConfig.cfg_id}" value="${crawlerConfig.address}" /></td>
					<th>目标端口：</th>
					<td><input type="text" class="ipttxt" id="port" name="port" value="${crawlerConfig.port}" /></td>
				</tr>
				<tr>
					<th>是否单号检验：</th>
					<td>
						<input type="radio" id="is_out_id_check_N" name="is_out_id_check" value="N" <c:if test="${crawlerConfig.is_out_id_check == 'N'}">checked</c:if><c:if test="${crawlerConfig.is_out_id_check == ''}">checked</c:if><c:if test="${crawlerConfig.is_out_id_check == null}">checked</c:if> />否&nbsp;
						<input type="radio" id="is_out_id_check_Y" name="is_out_id_check" value="Y" <c:if test="${crawlerConfig.is_out_id_check == 'Y'}">checked</c:if> />是&nbsp;
					</td>
					<th>是否走代理：</th>
					<td>
						<input type="radio" id="proxy_type_N" name="proxy_type" value="N" <c:if test="${crawlerConfig.proxy_type == 'N'}">checked</c:if><c:if test="${crawlerConfig.proxy_type == ''}">checked</c:if><c:if test="${crawlerConfig.proxy_type == null}">checked</c:if> />否&nbsp;
						<input type="radio" id="proxy_type_Y" name="proxy_type" value="Y" <c:if test="${crawlerConfig.proxy_type == 'Y'}">checked</c:if> />是&nbsp;
					</td>
				</tr>
				<tr id="proxy_tr">
					<th>代理地址：</th>
					<td><input type="text" class="ipttxt" id="proxy_address" name="proxy_address" value="${crawlerConfig.proxy_address}" /></td>
					<th>代理端口：</th>
					<td><input type="text" class="ipttxt" id="proxy_port" name="proxy_port" value="${crawlerConfig.proxy_port}" /></td>
				</tr>
				<tr id="proxy_tr">
					<th>用户名：</th>
					<td><input type="text" class="ipttxt" id="proxy_user" name="proxy_user" value="${crawlerConfig.proxy_user}" /></td>
					<th>密码：</th>
					<td><input type="text" class="ipttxt" id="proxy_pwd" name="proxy_pwd" value="${crawlerConfig.proxy_pwd}" /></td>
				</tr>
				<tr >
					<th></th>
					<td rowspan="2" style="margin-right: 500px; text-align: center;">
						<input type="button" class="comBtn"  name="save" id="save" value="保存" />
						<input type="button" class="comBtn"  name="resset" id="resset" value="重置" />
					</td>
					<th</th>
					<td>
						<input type="button" class="comBtn"  name="refresh" id="refresh" value="刷新爬虫配置信息" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<!-- 
<div><h3>进程配置<h3></div>
	<form id="form_proc" class="grid">
            <grid:grid from="webPage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell>进程编码</grid:cell>
                    <grid:cell>进程名称</grid:cell>
                    <grid:cell>运行机器ip</grid:cell>
                    <grid:cell>运行端口</grid:cell>
                    <grid:cell>线程名称</grid:cell>
                    <grid:cell>状态</grid:cell>
					<grid:cell>操作</grid:cell>
                </grid:header>
                <grid:body item="crawlerProc">
                    <grid:cell><input type="hidden" proc_id="${crawlerProc.proc_id}" proc_code="${crawlerProc.proc_code}" proc_name="${crawlerProc.proc_name}" ip="${crawlerProc.ip}" port="${crawlerProc.port}" />
                    ${crawlerProc.proc_code}</grid:cell>
                    <grid:cell>${crawlerProc.proc_name}</grid:cell>
                    <grid:cell>${crawlerProc.ip}</grid:cell>
                    <grid:cell>${crawlerProc.port}</grid:cell>
                    <grid:cell>${crawlerProc.thread_name}</grid:cell>
                    <grid:cell>${crawlerProc.status =='0' ? '正常':'失效'}</grid:cell>
                    <grid:cell>
                    	<c:if test='${crawlerProc.status =="0"}'><a id="forbid" name="forbid" proc_id="${crawlerProc.proc_id}" >禁用</a>|</c:if>
						<c:if test='${crawlerProc.status =="1"}'><a id="restart" name="restart" proc_id="${crawlerProc.proc_id}" >启用</a>|</c:if>
						<a id="cond" name="cond" proc_id="${crawlerProc.proc_id}" >条件配置</a>
					</grid:cell>
                </grid:body>
            </grid:grid>
            </br>

			 <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="addProc" type="button" id="addProc" value="添加进程"  class="graybtn1" />
            </div>
	</form>
	 -->
	<form id="form_proc" class="grid">
		<table>
			<thead>
				<tr>
					<th>进程编码</th>
					<th>进程名称</th>
					<th>运行机器ip</th>
					<th>运行端口</th>
					<th>线程名称</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="crawlerProc" items="${list}" > 
				<tr isselected="true">
					<td>
						<input type="hidden" proc_id="${crawlerProc.proc_id}" proc_code="${crawlerProc.proc_code}" proc_name="${crawlerProc.proc_name}" ip="${crawlerProc.ip}" port="${crawlerProc.port}" />
						${crawlerProc.proc_code}
					</td>
					<td>${crawlerProc.proc_name}</td>
					<td>${crawlerProc.ip}</td>
					<td>${crawlerProc.port}</td>
					<td>${crawlerProc.thread_name}</td>
					<td>${crawlerProc.status =='0' ? '正常':'失效'}</td>
					<td>
						<c:if test='${crawlerProc.status =="0"}'><a id="forbid" name="forbid" proc_id="${crawlerProc.proc_id}" >禁用</a>|</c:if>
						<c:if test='${crawlerProc.status =="1"}'><a id="restart" name="restart" proc_id="${crawlerProc.proc_id}" >启用</a>|</c:if>
						<a id="cond" name="cond" proc_id="${crawlerProc.proc_id}" >条件配置</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</br></br>
		<div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="addProc" type="button" id="addProc" value="添加进程"  class="graybtn1" />
        </div>
	</form>
	
<div><h3>操作地址配置<h3></div>
	<form id="form_url" class="grid">
            <grid:grid from="webPage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell>操作编码</grid:cell>
                    <grid:cell>操作名称</grid:cell>
                    <grid:cell>操作连接</grid:cell>
					<grid:cell>操作</grid:cell>
                </grid:header>
                <grid:body item="urlConfig">
                    <grid:cell><input type="hidden" cuc_id="${urlConfig.cuc_id}" operation_code="${urlConfig.operation_code}" operation_name="${urlConfig.operation_name}" operation_url="${urlConfig.operation_url}" />
                    ${urlConfig.operation_code}</grid:cell>
                    <grid:cell>${urlConfig.operation_name}</grid:cell>
                    <grid:cell>${urlConfig.operation_url}</grid:cell>
                    <grid:cell>
						<a id="delUrl" name="delUrl" new_opt_url="N" cuc_id="${urlConfig.cuc_id}" operation_code="${urlConfig.operation_code}" >删除&nbsp;&nbsp;</a>
					</grid:cell>
                </grid:body>
            </grid:grid>
            </br></br></br>
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="addUrl" type="button" id="addUrl" value="添加"  class="graybtn1" />
                <input name="saveUrl" type="button" id="saveUrl" value="保存"  class="graybtn1" />
            </div>
	</form>
</div>
<div id="procEdit">
<div id="procEditDialog"></div>
</div>
<div id="procCond">
<div id="procCondDialog"></div>
</div>
<div id="adddProc">
<div id="adddProcDialog"></div>
</div>

<script type="text/javascript">
$(function() {
	Eop.Dialog.init({id:"procEdit",modal:true,title:"修改进程",height:'400px',width:'550px'});
	Eop.Dialog.init({id:"procCond",modal:true,title:"条件配置",height:'400px',width:'750px'});
	Eop.Dialog.init({id:"adddProc",modal:false,title:"添加进程",height:'500px',width:'550px'});
	crawler.init();
	initEvent();
	
	var proxy_type = $("#address").attr("proxy_type");
	if(proxy_type=='N' || proxy_type=='' || proxy_type==null){
		$("#basedata_from tbody tr[id='proxy_tr']").hide();
	}
});

var crawler = {
	init : function() {
	crawler.save();
	crawler.resset();
	crawler.forbid();//禁用
	crawler.edit();//修改
	crawler.cond();//条件配置
	crawler.addProc();
	crawler.restart();
	crawler.radioChange();
	crawler.addUrl();
	crawler.saveUrl();
	crawler.refreshInfo();
	},
	
	//保存基础数据
	save : function(){
		$("[name='save']").unbind("click").bind("click",function(){
			var address = $("#address").val();
			address = $.trim(address);
			var port = $("#port").val();
			port = $.trim(port);
			var proxy_type = $("input[name='proxy_type']:checked").val();
			var proxy_address = $("#proxy_address").val();
			proxy_address = $.trim(proxy_address);
			var proxy_port = $("input[name='proxy_port']").val();
			proxy_port = $.trim(proxy_port);
			var proxy_user = $("#proxy_user").val();
			var proxy_pwd = $("#proxy_pwd").val();
			var cfg_id = $("#address").attr("cfg_id");
			var is_out_id_check = $("input[name='is_out_id_check']:checked").val();
			
			if(address==''|| address==undefined || address==null ){
				alert("【目标地址】不能为空！");
				return;
			}
			
 			//校验ip地址或域名
 			var pattern_address = new RegExp("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");//校验ip格式
 			var pattern_address1 = new RegExp("[a-zA-z]+://[^\\s]*");//校验域名
			if(!pattern_address.test(address) && !pattern_address1.test(address)){
				alert("目标地址格式不正确，目标地址格式可以为【ip】或者【域名】，请重新输入!");
				return;
			}
			if(proxy_address!=null && proxy_address!='' && proxy_address!=undefined){
				if(!pattern_address.test(proxy_address)){
					alert("代理地址ip格式不正确，请重新输入!");
					return;
				}
			} 

			//校验端口
			var pattern_port = new RegExp("^([1-9]\\d{0,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])(-([1-9]\\d{0,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5]))?$");
			if(port!=null && port!='' && port!=undefined){
				if(!pattern_port.test(port)){
					alert("目标端口格式不正确，请重新输入!");
					return;
				}
			}
			if(proxy_port!=null && proxy_port!='' && proxy_port!=undefined){
				if(!pattern_port.test(proxy_port)){
					alert("代理端口格式不正确，请重新输入!");
					return;
				}
			} 
			
			if(!confirm("确定保存信息吗?")){
				return;
			}
			
/* 			if(address==''|| address==undefined || port=='' || port==undefined ){
				alert("【目标地址】或【目标地址】不能为空！");
				return;
			}
			if(proxy_type=='N'){
				if(proxy_address!='' || proxy_port!='' || proxy_user!='' || proxy_pwd!='' ){
					alert("代理相关参数不为空，请选择代理方式为走代理！!");
					return;
				}
			} 
			if(proxy_type=='Y'){
				if(proxy_address=='' || proxy_address==undefined || proxy_port=='' || proxy_port==undefined || proxy_user=='' || proxy_user==undefined || proxy_pwd=='' || proxy_pwd==undefined){
					alert("已选择走代理方式，请填写代理相关参数！!");
					return;
				}
			} */
			
			$.Loading.show('正在执行，请稍侯...');
			var url = ctx+ "shop/admin/orderCrawlerAction!saveCrawlerInfo.do?ajax=yes";
			$.ajax({
				url : url,
				data : {"crawlerConfig.address":address,
						"crawlerConfig.port":port,
						"crawlerConfig.cfg_id":cfg_id,
						"crawlerConfig.proxy_type":proxy_type,
						"crawlerConfig.is_out_id_check":is_out_id_check,
						"crawlerConfig.proxy_address":proxy_address,
						"crawlerConfig.proxy_port":proxy_port,
						"crawlerConfig.proxy_user":proxy_user,
						"crawlerConfig.proxy_pwd":proxy_pwd
					},
				type : "post",
				dataType : "json",
				success : function(rsq) {
					if(rsq.result=='0'){
						$.Loading.hide();
						alert(rsq.message);
						var url = ctx+"/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						window.location.href = url;
					}else{
						$.Loading.hide();
						alert(rsq.message);
					}
				},
				error : function() {
					$.Loading.hide();
					alert("操作失败!");
				}
			});
		});
	},

	//重置基础数据
	resset : function(){
		$("[name='resset']").unbind("click").bind("click",function(){
			//$("#address").val("");
			//$("#port").val("");
			$("#is_out_id_check_N").attr("checked","checked");
			$("#proxy_type_N").attr("checked","checked");
			$("#proxy_address").val("");
			$("#proxy_port").val("");
			$("#proxy_user").val("");
			$("#proxy_pwd").val("");
		});
	},	
	
	//禁用进程
	forbid : function(){
		$("[name='forbid']").unbind("click").bind("click",function(){
			if(!confirm("确定禁用该进程吗?")){
				return;
			}
			
			var proc_id = $(this).attr("proc_id");
			var url = ctx + "/shop/admin/orderCrawlerAction!startOrForbidProc.do?ajax=yes&proc_id="+proc_id+"&&flag=forbid";
			$.Loading.show('正在执行，请稍侯...');
			$.ajax({
				type : "post",
				url : url,
				dataType : "json",
				success : function(rsp) {
					$.Loading.hide();
						if(rsp.result==0){
							alert("禁用成功");
							window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						}
				},
				error:function(e){$.Loading.hide();alert("禁用失败！");}
			}); 			
			
		});
	},

	//修改进程
	edit : function(){
		$("[name='edit']").unbind("click").bind("click",function(){
			var proc_id = $(this).attr("proc_id");
			var url = ctx + "/shop/admin/orderCrawlerAction!queryProc.do?ajax=yes&flag=edit&proc_id="+proc_id;
			Eop.Dialog.open("procEdit");
			$("#procEditDialog").html("loading...");
			$("#procEditDialog").load(url,function(){});
		});
	},	
	
	//条件配置
	cond : function(){
		$("[name='cond']").unbind("click").bind("click",function(){
			var proc_id = $(this).attr("proc_id");
			var url = ctx + "/shop/admin/orderCrawlerAction!queryProcCond.do?ajax=yes&proc_id="+proc_id;
			Eop.Dialog.open("procCond");
			$("#procCondDialog").html("loading...");
			$("#procCondDialog").load(url,function(){});
		});
	},	
	 
	//添加进程
	addProc : function(){
		$("[name='addProc']").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/orderCrawlerAction!queryProc.do?ajax=yes&flag=add";
			Eop.Dialog.open("adddProc");
			$("#adddProcDialog").html("loading...");
			$("#adddProcDialog").load(url,function(){});
		});
	},
	
	//刷新爬虫信息配置
	refreshInfo : function(){
		$("[name='refresh']").unbind("click").bind("click",function(){
			if(!confirm("刷新将会把爬虫配置信息同步到爬虫侧，确认刷新吗？")){
				return;
			}
			
			var url = ctx + "/shop/admin/orderCrawlerAction!refreshInfo.do?ajax=yes";
			$.Loading.show('正在执行，请稍侯...');
			$.ajax({
				type : "post",
				url : url,
				dataType : "json",
				success : function(rsp) {
						$.Loading.hide();
						if(rsp.result==0){
							alert(rsp.message);
							window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						}else if(rsp.result==1){
							alert(rsp.message);
						}
				},
				error:function(e){$.Loading.hide();alert("操作失败！");}
			}); 
		});
	},
	
	//启用进程
	restart : function(){
		$("[name='restart']").unbind("click").bind("click",function(){
			if(!confirm("确定启用该进程吗?")){
				return;
			}
			
			var proc_id = $(this).attr("proc_id");
			var url = ctx + "/shop/admin/orderCrawlerAction!startOrForbidProc.do?ajax=yes&proc_id="+proc_id+"&&flag=restart";
			$.Loading.show('正在执行，请稍侯...');
			$.ajax({
				type : "post",
				url : url,
				dataType : "json",
				success : function(rsp) {
						$.Loading.hide();
						if(rsp.result==0){
							alert("启用成功");
							window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						}else if(rsp.result==1){
							alert(rsp.message);
						}
				},
				error:function(e){$.Loading.hide();alert("启用失败！");}
			}); 
		});
	},
	
	//改变是否走代理动态显示代理相关信息输入框
	radioChange : function(){
		$("input[name='proxy_type']").change(function(){
			var proxy_type = $("input[name='proxy_type']:checked").val();
			if(proxy_type == 'N'){
				$("#basedata_from tbody tr[id='proxy_tr']").hide();
			}else if(proxy_type == 'Y'){
				$("#basedata_from tbody tr[id='proxy_tr']").show();
			}
		});
	},
	
	//新增操作配置
	addUrl : function(){
		$("[name='addUrl']").unbind("click").bind("click",function(){
			//var html = $("#form_url").find('tbody').html();
			var tr = "<tr class=\"grid-table-row-selected\" isselected=\"true\" id='new_url_tr' >"+	
					 "<td><input type='text' name='operation_code' value='' /></td>"+
					 "<td><input type='text' name='operation_name' value='' /></td>"+
					 "<td><input type='text' name='operation_url' value='' /></td>"+
					 "<td><a id='delUrl' name='delUrl' new_opt_url='Y' >删除&nbsp;&nbsp;</a></td>"+
					 "</tr>";
			//html += tr;
			$("#form_url").find('tbody').append(tr);
			initEvent();
		});
	},
	
	//保存操作配置信息
	saveUrl : function(){
		$("[name='saveUrl']").unbind("click").bind("click",function(){
			var datas = "[";
			var bol = false;
			$("#form_url tbody tr[id='new_url_tr']").each(function() {
				bol = true;
				var operation_code = $(this).find("input[name='operation_code']").val();
				var operation_name = $(this).find("input[name='operation_name']").val();
				var operation_url = $(this).find("input[name='operation_url']").val();
				
				if(operation_code=='' || operation_code==undefined || operation_name=='' || operation_name==undefined || operation_url=='' || operation_url==undefined){
					alert("参数不能为空!");
					bol = false;
					return;
				}
				
				datas +=
					"{\"operation_code\":\""+operation_code+"\"," +
					"\"operation_name\":\""+operation_name+"\"," +
					"\"operation_url\":\""+operation_url+
					"\"},";	
			});
			datas += "]";
			if(!bol){
				alert("没有新增操作配置，请新增操作配置再进行保存!");
				return;
			}
			
			$.Loading.show('正在执行，请稍侯...');
			var url = ctx+ "shop/admin/orderCrawlerAction!addUrlInfo.do?ajax=yes";
			$.ajax({
				url : url,
				data : {params:datas},
				type : "post",
				dataType : "json",
				success : function(rsq) {
					if(rsq.result=='0'){
						$.Loading.hide();
						alert("添加成功!");
						var url = ctx+"/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						window.location.href = url;
					}else if(rsq.result=="1"){
						$.Loading.hide();
						alert(rsq.message);
					}
				},
				error : function() {
					$.Loading.hide();
					alert("操作失败!");
				}
			});
		});	
	}
}

var initEvent = function(){
	//删除操作配置信息
	$("[name='delUrl']").bind("click",function(){
		var operation_code = $(this).attr("operation_code");
		var cuc_id = $(this).attr("cuc_id");
		var new_opt_url = $(this).attr("new_opt_url");
		if(new_opt_url=='Y'){
			$(this).parent().parent().remove();
		}else if(new_opt_url=='N'){
			if(!confirm("确定删除该条信息吗？")){
				return;
			}
			$.Loading.show('正在执行，请稍侯...');
			$.ajax({
				url : ctx + "/shop/admin/orderCrawlerAction!delUrlConfig.do?ajax=yes&cuc_id="+cuc_id,
				type : "post",
				dataType : "json",
				success : function(rsq) {
					if(rsq.result=='0'){
						$.Loading.hide();
						alert(rsq.message);
						var url = ctx+"/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
						window.location.href = url;
					}else if(rsq.result=='1'){
						$.Loading.hide();
						alert(rsq.message);
					}
				},
				error : function() {
					$.Loading.hide();
					alert("操作失败!");
				}
			});
		}
		
		initEvent();
	});
}
</script>