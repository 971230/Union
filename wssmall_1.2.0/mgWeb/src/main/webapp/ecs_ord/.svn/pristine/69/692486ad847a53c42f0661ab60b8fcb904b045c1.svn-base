<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
function disableBtn(dis){	
	$("#savePhoneBtn1").attr("disabled",dis);
	$("#savePhoneBtn2").attr("disabled",dis);
	$("#queryBtn").attr("disabled",dis);
}
</script>
<div class="searchformDiv">
	<input type="radio" name="selectDiv" value="queryNum" onclick="$('#divDiy').hide();$('#divQuery').show();$('.selradio').attr('checked',false);" checked="checked">查询选号</input>
	<input type="radio" name="selectDiv" value="diyNum"   onclick="$('#divDiy').show();$('#divQuery').hide();$('.selradio').attr('checked',false);$('#diyNumRadio').attr('checked',true);">手工录号</input>
</div>
<div class="searchformDiv" id="divDiy" style="display: none;">
	<div align="center" style="margin-top:20px;margin-bottom: 20px;">
		<input class="selradio" type="radio" name="selectNum" id="diyNumRadio" 
			  classId="" advancePay=""  advanceCom="" advanceSpe="" 
   		 	  numThawTim="" numThawPro="" lowCostChe="" timeDurChe=""  changeTagChe="" broMonPro=""
   		 	  cancelTagChe="" bremonChe="" lowCostPro="" timeDurPro="" changeTagPro="" cancelTagPro=""/>&nbsp;&nbsp;请输入手机号码：
		<input type="text" class="ipt_new" style="width:100px;" name="phoneNumByH" id ="phoneNumByH"  class="ipttxt">
		<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定" id="savePhoneBtn1" >
	</div>
</div>
<div style="display: block;" id="divQuery">
<form method="post" id="numForm">
    <div class="searchformDiv">
        <table>
   	       <tr>
   	          <th>号段：</th>
   	          <td>
   	          	<html:selectdict  curr_val="${segmentCode}"  style="width:100px;" id="segmentCode" name ="segmentCode"  attr_code="NUM_TYPR"></html:selectdict>
              </td>
   	          <th>号码关键字：</th>
   			  <td>
   			     <input type="text"  class="ipt_new" style="width:100px;" name="queryPara_03" id ="queryPara_03" value="${queryPara_03}"  style="width:70px" class="ipttxt">
   	  		  </td>
   			   <th>靓号等级：</th>
   			   <td>
   			      <select  class="ipt_new" style="width:100px;" name="queryPara_04" id ="queryPara_04" >
    	  		    <option value="">---请选择---</option>
    	  		    <option value="0">一类靓号</option>
    	  		    <option value="1">二类靓号</option>
    	  		    <option value="2">三类靓号</option>
    	  		    <option value="3">四类靓号</option>
    	  		    <option value="4">五类靓号</option>
    	  		    <option value="5">六类靓号</option>
    	  		    <option value="6">普号</option>
   	  		    </select>
   			   </td>
   			   <td>
  	               <input type="button" id="queryBtn" class="graybtn1" style="margin-left:30px;" value="查&nbsp;询"/>
   			   </td> 
   	       </tr>
   	   </table>
    	</div>  
</form>   
<form id="form_num" class="grid">
	<grid:grid from="webpage" ajax='yes' formId="numForm">
		<grid:header>
			<grid:cell></grid:cell> 
		  	<grid:cell>号码</grid:cell> 
			<grid:cell>号码等级</grid:cell> 
			<grid:cell>预存话费金额</grid:cell>
			<grid:cell>普通预存</grid:cell>
			<grid:cell>专项预存</grid:cell>
			<grid:cell>返还时长</grid:cell>
			<grid:cell>分月返还金额</grid:cell>
		</grid:header>
	    <grid:body item="obj">
	   		 <grid:cell>
	   		 	<input class="selradio" type="radio" name="selectNum" value="${obj.numId}" classId="${obj.classId}"  advancePay="${obj.advancePay}"  advanceCom="${obj.advanceCom}" advanceSpe="${obj.advanceSpe}" 
	   		 	  numThawTim="${obj.numThawTim}" numThawPro="${obj.numThawPro}" lowCostChe="${obj.lowCostChe}" timeDurChe="${obj.timeDurChe}"  changeTagChe="${obj.changeTagChe}" broMonPro="${obj.broMonPro}"
	   		 	  cancelTagChe="${obj.cancelTagChe}" bremonChe="${obj.bremonChe}" lowCostPro="${obj.lowCostPro}" timeDurPro="${obj.timeDurPro}" changeTagPro="${obj.changeTagPro}" cancelTagPro="${obj.cancelTagPro}"
	   		 	/>
	   		 </grid:cell> 
		     <grid:cell>${obj.numId}</grid:cell>
		     <grid:cell>${obj.classId}</grid:cell>
		     <grid:cell>${obj.advancePay}</grid:cell>
		     <grid:cell>${obj.advanceCom}</grid:cell>
		     <grid:cell>${obj.advanceSpe}</grid:cell>
		     <grid:cell>${obj.numThawTim}</grid:cell>
		     <grid:cell>${obj.numThawPro}</grid:cell>
	  </grid:body>
	</grid:grid>
	<div align="center" style="margin-top:20px;">
		<input type="button" style="margin-left:5px;margin-top:10px;" class="comBtn" value="确&nbsp;&nbsp;定" id="savePhoneBtn2">
	</div>
</form>
    <form method="post" id="savePhoneInfoFukaForm">
      <input name="phoneInfoMap.classId" id="classId_dlg" type="hidden"/>	
	  <input name="phoneInfoMap.advancePay" id="advancePay_dlg"type="hidden" />	
	  <input name="phoneInfoMap.lowCostChe" id="lowCostChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.numThawPro" id="numThawPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.numThawTim" id="numThawTim_dlg" type="hidden" />	
	  <input name="phoneInfoMap.advanceSpe" id="advanceSpe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.advanceCom" id="advanceCom_dlg" type="hidden" />	
	  <input name="phoneInfoMap.lowCostPro" id="lowCostPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.bremonChe" id="bremonChe_dlg"  type="hidden" />	
	  <input name="phoneInfoMap.cancelTagChe" id="cancelTagChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.broMonPro" id="broMonPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.changeTagChe" id="changeTagChe_dlg" type="hidden" />	
	  <input name="phoneInfoMap.timeDurChe" id="timeDurChe_dlg" type="hidden" />							   
	  <input name="phoneInfoMap.timeDurPro" id="timeDurPro_dlg" type="hidden" />	
	  <input name="phoneInfoMap.changeTagPro" id="changeTagPro_dlg" type="hidden" />						  
	  <input name="phoneInfoMap.cancelTagPro" id="cancelTagPro_dlg" type="hidden" />
      <input name="phoneInfoMap.phone_num" id="phone_num_dlg" type="hidden" />
      
    </form>
</div>
<script type="text/javascript"> 
var FukaPhoneList={
	 search:function(){
		 $("#queryBtn").click(function(){
			// alert($("#spanOperState18520459033").val());return;
			 disableBtn("disabled");
			 var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumFukaList.do?ajax=yes&isQuery=yes&fukaInstId="+'${fukaInstId}'+"&numType="+'${numType}'+"&old_phone_num="+'${old_phone_num}'+"&order_id="+'${order_id}';
			 Cmp.ajaxSubmit('numForm','selFukaPhoneDlg',url,{"segmentCode":$("#segmentCode").val()},FukaPhoneList.initFun);	
			 disableBtn("");
		 });
	 },
	 initFun:function(){
		 FukaPhoneList.search();
		 FukaPhoneList.check();
	 },
	 check:function(){
		 $("#savePhoneBtn1").unbind("click").bind("click",function(){
			var diyPhoneNum = $("#phoneNumByH").val();
			if(diyPhoneNum==""){
				alert("还未录入号码");
			}else{
				if (!(/^[0-9]{11}$/.test(diyPhoneNum))) {
			      alert("电话号码只能是11位的数字组成");
				  disableBtn("");
			      return false;
			 	}	
				$("#diyNumRadio").val(diyPhoneNum);
				$("#savePhoneBtn2").click();
			}; 
		});
		 
		 $("#savePhoneBtn2").unbind("click").bind("click",function(){
			disableBtn("disabled");
			var obj = $("[name='selectNum']:checked");
			var phone_num = obj.val();
			if(phone_num==null || phone_num ==""){
				alert("还未选中号码-副卡页面的");
				disableBtn("");
			    return false;
			}else{			
				var classId = obj.attr("classId");
				var advancePay = obj.attr("advancePay");
				var advanceCom = obj.attr("advanceCom");
				var advanceSpe = obj.attr("advanceSpe");
				var numThawTim = obj.attr("numThawTim");
				var numThawPro = obj.attr("numThawPro");
				var lowCostChe = obj.attr("lowCostChe");
				var timeDurChe = obj.attr("timeDurChe");
				var changeTagChe = obj.attr("changeTagChe");
				var broMonPro = obj.attr("broMonPro");
				var cancelTagChe = obj.attr("cancelTagChe");
				var bremonChe = obj.attr("bremonChe");
				var lowCostPro = obj.attr("lowCostPro");
				var timeDurPro = obj.attr("timeDurPro");
				var changeTagPro = obj.attr("changeTagPro");
				var cancelTagPro = obj.attr("cancelTagPro");
				var orderPhoneInfo = {
						     phone_num:phone_num,
						     classId:classId,
						     advancePay:advancePay,
						     advanceCom:advanceCom,
						     advanceSpe:advanceSpe,
						     numThawTim:numThawTim,
						     numThawPro:numThawPro,
						     lowCostChe:lowCostChe,
						     timeDurChe:timeDurChe,
						     changeTagChe:changeTagChe,
						     broMonPro:broMonPro,
						     cancelTagChe:cancelTagChe,
						     bremonChe:bremonChe,
						     lowCostPro:lowCostPro,
						     timeDurPro:timeDurPro,
						     changeTagPro:changeTagPro,
						     cancelTagPro:cancelTagPro
				            };
				
				 $("#cancelTagPro_dlg").val(cancelTagPro);
				 $("#changeTagPro_dlg").val(changeTagPro);
				 $("#cancelTagChe_dlg").val(cancelTagChe);
				 $("#changeTagChe_dlg").val(changeTagChe);
				 $("#phone_num_dlg").val(phone_num);
				 setValue(orderPhoneInfo,"_dlg"); 
				 disableBtn('disabled');
	             // var param = {phoneInfoMap:orderPhoneInfo};     
				 var url= app_path+"/shop/admin/orderFlowAction!changePhoneNumSinge.do?ajax=yes&numType="+'${numType}'+"&fukaInstId="+'${fukaInstId}'+"&old_phone_num="+'${old_phone_num}'+"&order_id="+'${order_id}'+"&phone_num="+phone_num;
				 var saveBack = function(reply){
					disableBtn('');
					alert(reply.message);	
					var oldPhoneNum = '${old_phone_num}';
					$("#phoneNum_"+oldPhoneNum).val(reply.view_phone_num);
					$("#spanOperState_"+oldPhoneNum).html(reply.message);
					if(reply.result==1){
						FukaPhoneList.initFun();
						Eop.Dialog.close("selFukaPhoneDlg");
					}
				 }; 
				Cmp.ajaxSubmit('savePhoneInfoFukaForm', '', url, {}, saveBack, 'json');
			}	
		});
	 }
};
$(function(){
	FukaPhoneList.initFun();
});

function setValue(orderPhoneInfo,dlg){
	if(dlg.length=0){
		$("[field_name='phone_num']").val(orderPhoneInfo.phone_num);
	//	$("[name='orderPhoneInfo.cancelTagPro'] option[value='"+orderPhoneInfo.cancelTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.changeTagPro'] option[value='"+orderPhoneInfo.changeTagPro+"']").attr("selected","selected");
	//	$("[name='orderPhoneInfo.cancelTagChe'] option[value='"+orderPhoneInfo.cancelTagChe+"']").attr("selected","selected");
	//  $("[name='orderPhoneInfo.changeTagChe'] option[value='"+orderPhoneInfo.changeTagChe+"']").attr("selected","selected");

	}
	
	//$("#classId"+dlg).val(orderPhoneInfo.classId);
	//$("#advancePay"+dlg).val(orderPhoneInfo.advancePay);
	//$("#advanceCom"+dlg).val(orderPhoneInfo.advanceCom);
	//$("#advanceSpe"+dlg).val(orderPhoneInfo.advanceSpe);
	//$("#numThawTim"+dlg).val(orderPhoneInfo.numThawTim);
	//$("#numThawPro"+dlg).val(orderPhoneInfo.numThawPro);
	//$("#lowCostChe"+dlg).val(orderPhoneInfo.lowCostChe);
	//$("#timeDurChe"+dlg).val(orderPhoneInfo.timeDurChe);
	//$("#changeTagChe"+dlg).val(orderPhoneInfo.changeTagChe);
	//$("#broMonPro"+dlg).val(orderPhoneInfo.broMonPro);
	//$("#cancelTagChe"+dlg).val(orderPhoneInfo.cancelTagChe);
	//$("#bremonChe"+dlg).val(orderPhoneInfo.bremonChe);
	//$("#lowCostPro"+dlg).val(orderPhoneInfo.lowCostPro);
	//$("#timeDurPro"+dlg).val(orderPhoneInfo.timeDurPro);
	//$("#changeTagPro"+dlg).val(orderPhoneInfo.changeTagPro);
	//$("#cancelTagPro"+dlg).val(orderPhoneInfo.cancelTagPro);
}
</script>