<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.AttrPackageBusiRequest"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
%>
   <!-- 号码信息开始 -->
    <form  method="post"  id="phoneInfoForm">
    <div class="grid_n_cont_sub">
       <h3>号码基本信息：</h3>
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
            <input type="hidden" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)%>" id="old_phone_num">
             <tr>
                  <th>用户号码：</th>
                  <td id="phone_td">
                  <span id="phone_td2" style="color:#999999"><html:orderattr order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input" style="width: 130px;"></html:orderattr></span>
                  <span><input type="button" style="display:none" value="变更号码" id="changePhoneBtn" class="graybtn1"/></span>              
                   </td>
                  <th>操作状态：</th>
                  <td>
					<input type="hidden" name="orderPhoneInfo.operatorState"  value="${orderPhoneInfo.operatorState }"/>
		  			<span <c:if test="${orderPhoneInfo.operatorState!=null && orderPhoneInfo.operatorState=='2'}">style="color:black"</c:if>
		  			 id="spanOperState_<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)%>">
		  				<c:forEach items="${operatorStateList }" var="pm">
		  					<c:if test="${pm.pkey == orderPhoneInfo.operatorState}">${pm.pname }</c:if>
						</c:forEach>
		  			</span>                 
				</td>
 				  <th></th>
                  <td></td>
              </tr> 
               <script type="text/javascript">
               $("#phone_td [field_name='phone_num']").attr("readonly","readonly");
               var is_old = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.IS_OLD)%>';
               var is_aop = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.IS_AOP)%>';
               var phone_length = $("#phone_td [field_name='phone_num']").length;
               var old =  '<%=ZjEcsOrderConsts.IS_OLD_1%>';
               var openChannel =  '<%=ZjEcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB%>';
               ///if(phone_length>0&&old!=is_old) {
              	 $("#changePhoneBtn").show();
               ///}
               </script>  

       </table>
    </div>
   <%--
     <div class="grid_n_cont_sub">
       <h3>靓号信息：</h3>
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
            <tr>
                 <th>预存话费金额：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.advancePay" id="advancePay" value="${orderPhoneInfo.advancePay}" disabled="disabled"></td>
                 <th>普通预存：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.advanceCom" id="advanceCom" value="${orderPhoneInfo.advanceCom}" disabled="disabled"></td>
                 <th>专项预存：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.advanceSpe" id="advanceSpe" value="${orderPhoneInfo.advanceSpe}" disabled="disabled"></td>
            </tr>   
            <tr>
                 <th>返还时长：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.numThawTim" id="numThawTim" value="${orderPhoneInfo.numThawTim}" disabled="disabled"></td>
                 <th>分月返还金额：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.numThawPro" id="numThawPro" value="${orderPhoneInfo.numThawPro}" disabled="disabled"></td>
                 <th>号码等级：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.classId" id="classId" value="${orderPhoneInfo.classId}" disabled="disabled"></td>
            </tr>  
            <tr>
                 <th>考核期最低消费：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.lowCostChe" id="lowCostChe" value="${orderPhoneInfo.lowCostChe}" disabled="disabled"></td>
                 <th>考核时长：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.timeDurChe" id="timeDurChe" value="${orderPhoneInfo.timeDurChe}" disabled="disabled"></td>
                 <th>考核期是否过户：</th>
                 <td>
                   <select  class='ipt_new' style="width:200px;" name="orderPhoneInfo.changeTagChe" id="changeTagChe" value="${orderPhoneInfo.changeTagChe}" disabled="disabled">
                       <option value="">---请选择---</option>、
                       <option value="0">允许</option>
                       <option value="1">不允许</option>
                   </select>
                  </td>
            </tr> 
            <tr>
                 <th>考核期是否销户：</th>
                 <td>
                   <select  class='ipt_new' style="width:200px;" name="orderPhoneInfo.cancelTagChe" id="cancelTagChe" value="${orderPhoneInfo.cancelTagChe}" disabled="disabled">
                       <option value="">---请选择---</option>、
                       <option value="0">允许</option>
                       <option value="1">不允许</option>
                   </select>
                  </td>
                 <th>考核期违约金月份：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.bremonChe" id="bremonChe" value="${orderPhoneInfo.bremonChe}" disabled="disabled"></td>
                 <th>协议期最低消费：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.lowCostPro" id="lowCostPro" value="${orderPhoneInfo.lowCostPro}" disabled="disabled"></td>
            </tr> 
            <tr>
                 <th>协议时长：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.timeDurPro" id="timeDurPro" value="${orderPhoneInfo.timeDurPro}" disabled="disabled"></td>
                 <th>协议期是否过户：</th>
                 <td>
                 <select  class='ipt_new' style="width:200px;" name="orderPhoneInfo.changeTagPro" id="changeTagPro" value="${orderPhoneInfo.changeTagPro}" disabled="disabled">
                       <option value="">---请选择---</option>、
                       <option value="0">允许</option>
                       <option value="1">不允许</option>
                    </select>
                 </td>
                 <th>协议期是否销户：</th>
                 <td>
                 <select  class='ipt_new' style="width:200px;" name="orderPhoneInfo.cancelTagPro" id="cancelTagPro" value="${orderPhoneInfo.cancelTagPro}" disabled="disabled">
                       <option value="">---请选择---</option>、
                       <option value="0">允许</option>
                       <option value="1">不允许</option>
                    </select>
                 </td>
            </tr> 
            <tr>
                 <th>协议期违约金月份：</th>
                 <td><input type="text" class='ipt_new' style="width:200px;" name="orderPhoneInfo.broMonPro" id="broMonPro" value="${orderPhoneInfo.broMonPro}" disabled="disabled"></td>
            </tr>  
       </table>
     </div> --%>
                    <script type="text/javascript">
           	  	        $("[name='orderPhoneInfo.cancelTagPro'] option[value='${orderPhoneInfo.cancelTagPro}']").attr("selected","selected");
           	  	        $("[name='orderPhoneInfo.changeTagPro'] option[value='${orderPhoneInfo.changeTagPro}']").attr("selected","selected");
           	  	        $("[name='orderPhoneInfo.cancelTagChe'] option[value='${orderPhoneInfo.cancelTagChe}']").attr("selected","selected");
      	  	            $("[name='orderPhoneInfo.changeTagChe'] option[value='${orderPhoneInfo.changeTagChe}']").attr("selected","selected");
        	  	    </script>
     </form>
      
   <!-- 号码信息结束 -->

<div id="selPhoneDlgZb"></div>
<div id="selPhoneDlgProvince"></div>


<script type="text/javascript">


$(function(){
	// Eop.Dialog.init({id:"selPhoneDlg",modal:true,title:"选择号码",width:'800px'});
	// Eop.Dialog.init({id:"selPhoneChannelDlg",modal:true,title:"选择号码",width:'300px'});
	 Eop.Dialog.init({id:"selPhoneDlgZb",modal:true,title:"总部号码选择",width:'800px'});
	 Eop.Dialog.init({id:"selPhoneDlgProvince",modal:true,title:"省份号码选择",width:'800px'});
	 selPhoneChannel();
});

function selPhoneChannel(){
	$("#changePhoneBtn").unbind("click").click(function(){
		var is_aop = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.IS_AOP) %>';
		if(is_aop=="0"){
			selPhoneZb();
			//selPhoneProvince();
		}else if (is_aop=="1"){
			selPhoneZb();
			//selPhoneProvince();
		}else if(is_aop=="2"){
			selPhoneProvince();
		}else{
			selPhoneProvince();
		}
	});
}
function selPhoneZb(){
		var url= app_path+"/shop/admin/orderFlowAction!checkLockStatus.do?ajax=yes&order_id="+'${order_id}';//检查锁定状态返回到saveBack
		var saveBack = function(reply){
			if(reply.result==1){
			    var phone_num = $("#old_phone_num").val();
			    var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=no&order_id=${order_id}&phone_num="+phone_num+"&old_phone_num="+phone_num+"&selNumChannel=zb";
			    $("#selPhoneDlgZb").load(url,{},function(){});
				Eop.Dialog.open("selPhoneDlgZb");
			}else{
				alert(reply.message);
			}
		};
		Cmp.ajaxSubmit('phoneInfoForm', '', url, {}, saveBack, 'json');
  }

function selPhoneProvince(){
		var url= app_path+"/shop/admin/orderFlowAction!checkLockStatus.do?ajax=yes&order_id="+'${order_id}';
		var saveBack = function(reply){
			if(reply.result==1){
			    var phone_num = $("#old_phone_num").val();
			    var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumList.do?ajax=yes&isQuery=no&order_id=${order_id}&phone_num="+phone_num+"&old_phone_num="+phone_num+"&selNumChannel=province";
			    $("#selPhoneDlgProvince").load(url,{},function(){});
				Eop.Dialog.open("selPhoneDlgProvince");
			}else{
				alert(reply.message);
			}
		};
		Cmp.ajaxSubmit('phoneInfoForm', '', url, {}, saveBack, 'json');
   }



</script> 