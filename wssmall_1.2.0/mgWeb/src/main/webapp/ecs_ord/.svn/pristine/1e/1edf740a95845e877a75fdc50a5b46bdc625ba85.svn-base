<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

   <!-- 号码信息开始 -->
    <form  method="post"  id="phoneInfoForm">
    <div class="grid_n_cont_sub">
       <h3>号码基本信息：</h3>
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
            <input type="hidden" value="${phone_num }" id="old_phone_num">
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
		  			 id="spanOperState_${phone_num }">
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
               <%-- var is_old = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.IS_OLD)%>';
               var is_aop = '<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.IS_AOP)%>';
               var phone_length = $("#phone_td [field_name='phone_num']").length;
               var old =  '<%=ZjEcsOrderConsts.IS_OLD_1%>';
               var openChannel =  '<%=ZjEcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB%>'; --%>
               ///if(phone_length>0&&old!=is_old) {
              	 $("#changePhoneBtn").show();
               ///}
               </script>  

       </table>
    </div>
   
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
	 Eop.Dialog.init({id:"selPhoneDlgZb",modal:true,title:"总部号码选择",width:'800px'});
	 Eop.Dialog.init({id:"selPhoneDlgProvince",modal:true,title:"省份号码选择",width:'800px'});
	 selPhoneChannel();
});

function selPhoneChannel(){
	$("#changePhoneBtn").unbind("click").click(function(){
		var is_aop = "${orderTree.orderExtBusiRequest.is_aop}";
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