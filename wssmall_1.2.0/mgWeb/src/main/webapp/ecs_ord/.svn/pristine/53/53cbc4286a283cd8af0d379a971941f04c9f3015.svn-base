<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div>
<form method="post" id="serchCountyform" action='ordAuto!queryCounty.do' ajax="yes">
 <div class="searchformDiv">
	 <table>
		
			<th>区县：</th>
			<td><input type="text" class="ipttxt" id="county_name"   value="${county_name}"/></td>
			<input type="text" class="ipttxt" id="lockOrderIdStr"  value="${lockOrderIdStr}" style="display :none;"/>
			<th></th>
			<td>
			
		    
			<input style="width:40px;"  class="comBtn schConBtn" id="qryCountyBtn"
					value="搜索" />
		     <input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定"  id="querycountyIdBtn">
			</td>
		</tr>
	 </table>
  </div>
</form>

<div class="grid" >
<div class="comBtnDiv">
<grid:grid from="webpage"  formId="serchCountyform" ajax="yes">
	<grid:header>
	    <grid:cell width="10%" >选择</grid:cell>
		<grid:cell width="15%">区县编码</grid:cell>
		<grid:cell width="15%">区县名称</grid:cell>
	</grid:header>
	<grid:body item="county">
		<grid:cell><input name="countyChk" type="radio" countyid="${county.countyid}" countyname="${ county.countyname}""></grid:cell>
		<grid:cell>${county.countyid}</grid:cell>
		<grid:cell>${county.countyname}</grid:cell>
	</grid:body>
</grid:grid>
</div>
</div>
</div>
<!-- var cobj = $("input[type='radio'][name='radionames']:checked"); -->
<script type="text/javascript">
$(function() {
	   			$("#qryCountyBtn").click(function(){
	   				var county_name = $("#county_name").val();
	   				var lockOrderIdStr = $("#lockOrderIdStr").val();
	   				var url = ctx+"/shop/admin/ordAuto!queryCounty.do?ajax=yes&county_name="+county_name;
	   				
	   				Cmp.ajaxSubmit('serchUserform','queryCountyListDlg',url,
	   						{},function(){});
	   			});
	   			$("#querycountyIdBtn").click(function(){
	   				var obj = $("[name='countyChk']:checked");
	   				var  countyid = null;
	   				countyid = obj.attr("countyid");
	   				countyname = obj.attr("countyname");
	   				if(countyname==null || countyname.length==0){
	   					document.getElementById("allot_county_name").value = "";
	   					document.getElementById("allot_county_id").value = "";
	   				}else{
	   					document.getElementById("allot_county_name").value = countyname;
	   					document.getElementById("allot_county_id").value = countyid;
	   				}
	   					
	   				
	   				Eop.Dialog.close("queryCountyListDlg");
	   			});
	   	
});
</script>