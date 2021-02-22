<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<form id="procCondFrom" action=""  method="post">
	<div class="searchformDiv" id="procInfoDiv">
		<table>
			<tr>
				<th>关联进程编码：</th>
				<td>
					<input type="hidden" id="crawlerProcInfo" name="crawlerProcInfo" proc_id="${crawlerProc.proc_id}" proc_code="${crawlerProc.proc_code}" 
						proc_name="${crawlerProc.proc_name}" thresd_name="${crawlerProc.thread_name}" crawlerCondType="${crawlerCondType}"
					/>
					<span>${crawlerProc.proc_code}</span>
				</td>
			</tr>
			<tr>
				<th>关联进程名称：</th>
				<td>
					<span>${crawlerProc.proc_name}</span>
				</td>
			</tr>
			<tr>
				<th>关联线程名称：</th>
				<td>
					<span>${crawlerProc.thread_name}</span>
				</td>
			</tr>
		</table>
	</div>
</form>
	<form id="form_cond" class="grid">
            <grid:grid from="webPage" ajax="yes" formId ="gridform">
                <grid:header>
                	<grid:cell>条件类型</grid:cell>
                    <grid:cell>条件名称</grid:cell>
                    <grid:cell>条件编码</grid:cell>
                    <grid:cell>条件值名称</grid:cell>
					<grid:cell>操作</grid:cell>
                </grid:header>
                <grid:body item="condIndo">
                	<%-- <grid:cell>
                		<c:if test="${condIndo.cond_type == '1' }">订单审核</c:if>
                		<c:if test="${condIndo.cond_type == '2' }">订单分配</c:if>
                	</grid:cell> --%>
                	<grid:cell>${condIndo.cond_type}</grid:cell> 
					<grid:cell><input type="hidden" cond_id="${condIndo.cond_id}" cond_code="${condIndo.cond_code}" cond_value="${condIndo.cond_value}" cond_name="${condIndo.cond_name}" />
                    ${condIndo.cond_name}</grid:cell>         
                    <grid:cell>${condIndo.cond_code}</grid:cell>     
                    <grid:cell>${condIndo.cond_value_name}</grid:cell>
                    <grid:cell>
						<a  name="del" new_cond="N" cond_id="${condIndo.cond_id}" cond_code="${condIndo.cond_code}" >删除&nbsp;&nbsp;</a>
					</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="add" type="button" id="add" value="添加条件"  class="graybtn1" />
                <input name="save" type="button" id="save" value="保存"  class="graybtn1" />
                <input name="back" type="button" id="back" value="返回"  class="graybtn1" />
            </div>
	</form>
<script type="text/javascript">
var procCond = {
		init :function(){
			procCond.add();
			procCond.save();
			procCond.back();
			procCond.close();
		},
		
		add : function(){
			$("[name='add']").unbind("click").bind("click",function(){
				var crawlerCondType = $("#crawlerProcInfo").attr("crawlerCondType");
				
				var tr ="<tr class=\"grid-table-row-selected\" isselected=\"true\" id='new_cond_tr' >"+
						"<td><select name='cond_type'  style=\"width: 150px\">"+
								"<option value=''>请选择条件类型</option>"+
								"<c:forEach items='${crawlerCondType}' var='v'>"+
								"<option value='${v.pkey }'>${v.pname }</option>"+
								"</c:forEach></select>"+
						"</td>"+

						"<td><select name='cond_name' style=\"width: 150px\">"+
						"<option value=''>请选择条件名称</option>"+
						"</select>"+
						"</td>"+
						"<td><input type='text' name='cond_code' value='' /></td>"+
						 "<td ><div name='val_dv'><input type='text' name='cond_value' value='' /></div></td>"+
						 "<td><a name='del' new_cond='Y' >删除&nbsp;&nbsp;</a></td>"+
						 "</tr>";
						
				$("#form_cond").find('tbody').append(tr);
				initEvent();
			});			
		},
		
		save : function(){
			$("[name='save']").unbind("click").bind("click",function(){
				var proc_id = $("#crawlerProcInfo").attr("proc_id");
				var datas = "[";
				var bol = false;
				$("#form_cond tbody tr[id='new_cond_tr']").each(function() {
					bol = true;
					var cond_type = $(this).find("[name='cond_type'] option:selected").text();
					var cond_name = $(this).find("select[name='cond_name'] option:selected").text();
					var cond_code = $(this).find("[name='cond_code']").val();
					var cond_value = $(this).find("[name='cond_value']").val();
					
					var cond_value_name = "";
					var selType = $(this).find("select[name='cond_name'] option:selected").attr("selType");
					if(selType==0){//输入框
						cond_value_name = cond_value;
					}else if(selType==1){//下拉单选
						cond_value_name = $(this).find("select[name='cond_value'] option:selected").text();
					}else if(selType==2){//下拉多选
						cond_value_name = $(this).find("[name='cond_value']").attr("value_name");
					}
					
					//alert("cond_name:"+cond_name+",cond_code:"+cond_code+",cond_value:"+cond_value)
					if(cond_code=='' || cond_code==undefined || cond_name=='' || cond_name==undefined || cond_value=='' || cond_value==undefined){
						alert("参数不能为空!");
						bol = false;
						return;
					}
					
					datas +=
						"{\"cond_name\":\""+cond_name+"\"," +
						"\"cond_code\":\""+cond_code+"\"," +
						"\"cond_type\":\""+cond_type+"\"," +
						"\"cond_value_name\":\""+cond_value_name+"\"," +
						"\"cond_value\":\""+cond_value+
						"\"},";	
				});
				datas += "]";
				if(!bol){
					alert("没有新增条件，请新增条件再进行保存!");
					return;
				}
				
				var url = ctx+ "shop/admin/orderCrawlerAction!addProcCond.do?ajax=yes&&proc_id="+proc_id;
				$.ajax({
					url : url,
					data : {params:datas},
					type : "post",
					dataType : "json",
					success : function(rsq) {
						if(rsq.result=='0'){
							alert("添加成功!");
							var url = ctx + "/shop/admin/orderCrawlerAction!queryProcCond.do?ajax=yes&proc_id="+proc_id;
							Cmp.ajaxSubmit('procCondFrom','procCond',url,{},function(){
							});  
						}else if(rsp.result=="1"){
							alert(rsq.message);
						}
					},
					error : function() {
					}
				});
			});			
		},
		
		back : function(){
			$("[name='back']").unbind("click").bind("click",function(){
				Eop.Dialog.close("procCond");
				window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
			});
		},
		
		close : function(){
			$(".closeBtn").unbind("click").bind("click",function(){
				Eop.Dialog.close("procCond");
				window.location.href = ctx + "/shop/admin/orderCrawlerAction!queryCrawlerProc.do";
			});
		}
			
}

var initEvent = function (){
		$("[name='del']").unbind("click").bind("click",function(){
			var cond_code = $(this).attr("cond_code");
			var new_cond = $(this).attr("new_cond");
			var proc_id = $("#crawlerProcInfo").attr("proc_id");
			if(new_cond=='Y'){
				$(this).parent().parent().remove();
			}else if(new_cond=='N'){
				if(!confirm("确定删除该条件吗？")){
					return;
				}
				var cond_id = $(this).attr("cond_id");
				$.ajax({
					url : ctx + "/shop/admin/orderCrawlerAction!forbidCond.do?ajax=yes&cond_id="+cond_id,
					type : "post",
					dataType : "json",
					success : function(rsq) {
						if(rsq.result=='0'){
							alert(rsq.message);
							var url = ctx + "/shop/admin/orderCrawlerAction!queryProcCond.do?ajax=yes&proc_id="+proc_id;
							Cmp.ajaxSubmit('procCondFrom','procCond',url,{},function(){
							});  
						}else if(rsq.result=='1'){
							alert(rsq.message);
						}
					},
					error : function() {
						alert("操作失败!");
					}
				});
			}
			
			initEvent();
		});
		
 		//改变条件类型下拉时触发
 		$("[name='cond_type']").unbind().bind("change",function(){
 			var value_html = "<input type='text' name='cond_value' value='' />";
 			var code_html = "<input type='text' name='cond_code' value='' />";
 			$(this).parents("tr").find("[name='cond_code']").val('');
 			$(this).parents("tr").find("[name='val_dv']").html(value_html);
 			
			var code_obj = $(this).parents("tr").find("select[name='cond_name']");
			
			var condType = $(this).parents("tr").find("select[name='cond_type'] option:selected").val();
			if(condType=='' || condType==null){
				alert("请先选择条件类型！");
				return;
			} 
			
			$.ajax({
				url : ctx + "/shop/admin/orderCrawlerAction!getCondCode.do?ajax=yes&condType="+condType,
				type : "post",
				dataType : "json",
				success : function(rsq) {
					var rsq = eval(rsq);
	 				var options = "<option value=''>请选择条件名称</option>";
					for(var i=0;i<rsq.length;i++){
						options +="<option value="+rsq[i].pkey+" selType="+rsq[i].selType+" condValueCode="+rsq[i].condValueCode+" >"+rsq[i].pname+"</option>";
					}
					code_obj.html(options); 
					initEvent();
				},
				error : function() {
					alert("获取条件编码数据失败！");
				}
			});
 		});
    	
 		//改变条件名称下拉时触发
    	$("[name='cond_name']").unbind().bind("change",function(){
    		var name_obj = $(this).parents("tr").find("select[name='cond_name'] option:selected");
    		var cond_code = name_obj.attr("value");
    		var selType = name_obj.attr("selType");
    		var condValueCode = name_obj.attr("condValueCode");
    		
    		$(this).parents("tr").find("input[name='cond_code']").val(cond_code);
    		$(this).parents("tr").find("input[name='cond_code']").attr({readonly:'true'});
    		
    		var value_obj = $(this).parents("tr").find("[name='val_dv']");
    		
    		if(selType==0){
    			var inner = "<input type='text' name='cond_value' value='' />";
    			value_obj.html(inner);
    			return;
    		}
			$.ajax({
				url : ctx + "/shop/admin/orderCrawlerAction!getCondValueInfo.do?ajax=yes&condValueCode="+condValueCode,
				type : "post",
				dataType : "json",
				success : function(rsq) {
					var rsq = eval(rsq);
					var options ="";
					if(selType==1){//单选
						options += "<select name='cond_value' style=\"width: 150px\">";
						options += "<option value=''>请选择条件值</option>";
						for(var i=0;i<rsq.length;i++){
							options +="<option value="+rsq[i].pkey+" >"+rsq[i].pname+"</option>";
						}
						options +="</select>"
					}
					if(selType==2){//多选
		                options += "<span class='selBox' style='width:150px;'>"+
		                    	"<input type='text' name='region_ivp'  value='' class='ipt' readonly='readonly'>"+
		                    	"<input type='hidden' name='cond_value' value='' value_name='' />"+
		                    	"<a  name='region_a' href='javascript:void(0);' class='selArr'></a>"+
		                        "<div  name='region_div' class='selOp' style='display:none;'>"+
		                        	"<div class='allSel'>"+
		                            	"<label><input type='checkbox' name='regioncheckAll' >全选</label>"+
		                                "<label><a href='javascript:void(0);' class='aCancel' name='regionCancel' >关闭</a></label>"+
		                                "<label><a href='javascript:void(0);' class='aClear' name='regionCancel2'></a></label>"+
		                            "</div>"+
		                            "<div class='listItem'>"+
		                            	"<ul>";
	                            	
						for(var i=0;i<rsq.length;i++){
							options +="<li><input type='checkbox' name='region_id' value="+rsq[i].pkey+" c_name="+rsq[i].pname+" ><span name='region_li'>"+rsq[i].pname+"</span></li>";
						}
						options +="</ul></div></div></span>";       
					}
	 				
					value_obj.html(options); 
					initSel();
				},
				error : function() {
					alert("获取条件编码数据失败！");
				}
			});
			
    	});
    	
		
    	
}

var initSel = function (){
	//XMJ修改开始
	$("[name='region_ivp'],[name='region_a'],[name='region_div']").bind("click",function(e){ //给按钮注册单击事件，点击显示DIV
		$(this).parents("span").find("[name='region_div']").show();
        e.stopPropagation();//阻止事件冒泡
	}); 
	
	$(document).unbind("click").bind("click",function(){    
        $("[name='region_div']").hide();    //隐藏DIV
  	}); 

   $("[name='regionCancel'],[name='regionCancel2']").unbind("click").bind("click",function(e){
	   $(this).parents("span").find("[name='region_div']").hide();
       e.stopPropagation();//阻止事件冒泡
	}); 
   
	//XMJ修改结束                		
	$("[name='regioncheckAll']").unbind("click").bind("click",function(){
		if(this.checked){
			$(this).parents("span").find("input[name=region_id]").attr("checked","checked");
			$(this).parents("span").find("[name='region_div'] li").addClass("curr");
		}else{
			$(this).parents("span").find("input[name=region_id]").removeAttr("checked");
			$(this).parents("span").find("[name='region_div'] li").removeClass("curr");
		}
		var obj = $(this).parents("span").find("[name='region_div']");
		selectRegions(obj);
	});
	
	
	$("input[name=region_id]").unbind("click").bind("click",function(){
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		var obj = $(this).parents("span").find("[name='region_div']");
		selectRegions(obj);
	});
	
}

$(function() {
	procCond.init();
	initEvent();
	
});


function selectRegions(obj){
	var regions = obj.parents("span").find("input[name=region_id]:checked");
	var regionNames = "";
	var regionIds = "";
	regions.each(function(idx,item){
		var name = $(item).attr("c_name");
		var rid = $(item).attr("value");
		regionNames += name+",";
		regionIds += rid+",";
	});
	if(regionIds.length>1){
		regionIds = regionIds.substr(0,regionIds.length-1);
		regionNames = regionNames.substr(0,regionNames.length-1);
	}
	obj.parents("span").find("[name='region_ivp']").val(regionNames);
	obj.parents("span").find("[name='cond_value']").val(regionIds);
	obj.parents("span").find("[name='cond_value']").attr("value_name",regionNames);
}


</script>