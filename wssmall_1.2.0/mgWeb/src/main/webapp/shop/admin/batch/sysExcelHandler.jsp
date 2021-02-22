<%@page import="com.ztesoft.net.mall.core.consts.Consts"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form>
	来源:&nbsp;&nbsp;<select class="ipttxt" id="source_from" style="width: 100px;">
		<option value="0">淘宝</option>
		<option value="1">京东</option>
	</select>
	<p class="attrDef"></p>
	<p></p>
	<input type="button" value="初始化加载" id="initPage"/>
	<input type="button" value="加载扩展属性" id="initAttrDef"/>
	<input type="button" value="保存模板" id="saveTemp"/>
	<input type="button" value="下载模板" id="exportTemp"/>
	<input type="button" value="上传模板" id="uploadTemp"/>
</form>
<form id="gridform" class="grid">
	<grid:grid from="webpage" ajax="yes">
		<grid:header>
			<grid:cell style="width:100px;">选择</grid:cell>
			<grid:cell style="width:100px;">id</grid:cell>
			<grid:cell style="width:100px;">名称</grid:cell>
		</grid:header>
		<grid:body item="TempConfig">
			<grid:cell>&nbsp;
				<input type="radio" name="temp_choose" attr="${TempConfig.temp_id}" />
			</grid:cell>
			<grid:cell>&nbsp;
				${TempConfig.temp_id} 
			</grid:cell>
			<grid:cell>&nbsp;
				${TempConfig.temp_name} 
			</grid:cell>
		</grid:body>
	</grid:grid>
</form>
<div id="attr_def_div" ></div>
<div class="input" align="center">
	<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		<tr id="uploadTr">
			<th>请选择上传文件</th>
			<td width="80%" style="vertical-align:text-bottom;" >
				<div style="width:80%;float:left;">
					<iframe name="uploadIframe" src="UpLoadFile.jsp" width="100%" 
						height="30px" frameborder="0" scrolling="no" style="margin:0px;padding:0px;overflow:hidden;">
					</iframe>
				</div>
				<br/>
				<p>
					<span name="dealResult">
					</span>
				</p>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	//单个扩展属性对象
	
	function attrSigngle(){
		this.field_name = "";
		this.c_name = "";
		this.e_name = "";
		this.setParam = function(field_name,c_name,e_name){
			this.field_name = field_name;
			this.c_name = c_name;
			this.e_name = e_name;
		}
	}

	$(document).ready(function(){
		$("#initPage").bind("click",function(){
			window.location.href = ctx+ "/shop/admin/systemExcelHandler!listTempConf.do";
		});
		
		$("#initAttrDef").bind("click",function(){
			var url =ctx+"/shop/admin/systemExcelHandler!getAttrDefInfo.do?ajax=yes&attr_spec_id=201309186950000700";
			Cmp.ajaxSubmit('attr_def_div','',url,{},SysExcel.jsonBack,'json');
		});
		
		$("#saveTemp").bind("click",function(){
			
			var attrDefSpec = [];
			$("input[type='checkBox']:checked").each(function(){
				var attrSingleSpec = new attrSigngle();
				var attr = $(this).attr("attr").split("_");
				attrSingleSpec.setParam(attr[0],attr[1],attr[2]);
				attrDefSpec.push(attrSingleSpec);
			});
			attrDefSpec = SysExcel.Obj2Str(attrDefSpec);
			
			var temp_source_from = $("#source_from").val();
			
			var temp_id = $("input[name='temp_choose']:checked").attr("attr");
			
			var data = {};
			data["attr_spec_data"] = attrDefSpec;
			data["temp_source_from"] = temp_source_from;
			data["temp_inst_id"] = "201309186950000700";
			data["temp_id"] = temp_id;
			$.ajax({
				type:"post",
				url:ctx+"/shop/admin/systemExcelHandler!saveTemp.do",
				data:data,
				success:function(){
					
				}
			});
			
		});
		
		
		$("#exportTemp").bind("click",function(){
			var trjQ = $("#uploadTr");
			var iframeDocument = $(trjQ.find("[name='uploadIframe']").get(0).contentWindow.document);
			
			iframeDocument.find("form").attr("action",ctx +"/servlet/SystemExcelHandlerServlet?servType=export&temp_inst_id=201309186950000700&source_from=0").submit();
		});
		
		$("#uploadTemp").bind("click",function(){
			var trjQ = $("#uploadTr");
			var iframeDocument = $(trjQ.find("[name='uploadIframe']").get(0).contentWindow.document); // 获取当前tr的iframe
			var fileName = iframeDocument.find("[id='uploadFile']").val();
		
			if (fileName == null || fileName == "") {
				alert("请选择模板单文件！");
				return false;
			} else if (fileName.substring(fileName.length - 3, fileName.length) != "xls") {
				alert("请选择Excel文件！");
				return false;
			}
				
			iframeDocument.find("form").attr("action",ctx +"/servlet/SystemExcelHandlerServlet?servType=import&temp_inst_id=201309186950000700&source_from=0").submit();
			return true;	
		});
	
	});
	
	var SysExcel = {
		jsonBack:function(responseText){
			var list = responseText.attr_def_list;
			var html = "";
			for(var i=0;i<list.length;i++){
				var attrDef = list[i];
				html += '<input type="checkbox"  attr="'+attrDef.field_name+'_'+attrDef.field_desc+'_'+attrDef.field_attr_id+'" />'+attrDef.field_desc+'';
			}
			$(".attrDef").after(html);
			$.Loading.hide();
		},
		
		Obj2Str:function(obj){
			switch(typeof(obj)){ 
			    case 'object': 
			    var ret = []; 
				    if (obj instanceof Array){ 
				    for (var i = 0, len = obj.length; i < len; i++){ 
				       ret.push(SysExcel.Obj2Str(obj[i])); 
				     } 
				    return '[' + ret.join(',') + ']'; 
			    } 
			    else if (obj instanceof RegExp){ 
			      return obj.toString(); 
			    } 
			    else{ 
			      for (var a in obj){ 
			       ret.push(a + ':' + SysExcel.Obj2Str(obj[a])); 
			      } 
			      return '{' + ret.join(',') + '}'; 
			    } 
			    case 'function': 
			     return 'function(){}'; 
			    case 'number': 
			     return obj.toString(); 
			    case 'string': 
			     return "\"" + obj.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function(a) {return ("\n"==a)?"\\n":("\r"==a)?"\\r":("\t"==a)?"\\t":"";}) + "\""; 
			    case 'boolean': 
			     return obj.toString(); 
			    default: 
			     return obj.toString(); 
		 	} 
		}
	}
	
</script>