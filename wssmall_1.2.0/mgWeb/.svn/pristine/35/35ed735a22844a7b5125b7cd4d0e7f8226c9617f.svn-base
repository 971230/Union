<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
 <form  class="validate" method="post" action="" id='addform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>* </span>联系人:</label></th>
       <td><input type="text" class="ipttxt"  name="paraddr.name" id="name"  value="${paraddr.name }" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>邮政编码:</label></th>
       <td><input type="text" class="ipttxt"  name="paraddr.zip" id="zip"  value="${paraddr.zip }" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>电话:</label></th>
       <td><input type="text" class="ipttxt"  name="paraddr.tel" id="tel"  value="${paraddr.tel }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
   <tr> 
       <th><label class="text"><span class='red'>* </span>手机:</label></th>
       <td><input type="text" class="ipttxt"  name="paraddr.mobile" id="mobile"  value="${paraddr.mobile }"  maxlength="60"  dataType="string" required="true" />       </td>
     </tr>
    <tr> 
       <th><label class="text"><span class='red'>* </span>地址:</label></th>
   <td><input type="text" class="ipttxt"  name="paraddr.addr" id="addr" size="50" value="${paraddr.addr }"  maxlength="60"  dataType="string" required="true" />       </td> </tr>
	 </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td>
		 		<input type="hidden" attr="begin_nbr"  value="${begin_nbr }">
		 		<input type="hidden" attr="end_nbr" value="${end_nbr }">
		 		<input type="hidden" attr="goods_num" value="${goods_num }">
		 		<input type="hidden" attr="apply_desc" value="${apply_desc }">
		 		<input type="hidden" id="goodsid" value="${goods_id }">
		 		<input type="hidden" name="paraddr.addr_id" value="${paraddr.addr_id }">
	           <input  type="button"  id="saveBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
 <div  id="refPartnerDlg"></div>
  <script type="text/javascript"> 
$(function (){
	  $("#addform").validate();
      $("#saveBtn").click(function() {
			var url = ctx+ "/shop/admin/parAddr!saveAddress.do?ajax=yes";
			Cmp.ajaxSubmit('addform', '', url, {}, function(responseText){
			        
					if (responseText.result == 0) {
						//保存成功后
					    alert(responseText.message);
					    Eop.Dialog.close("newAddr_dialog");
					    Eop.Dialog.close("apply_w_dialog");
					    var goodsid=$('#goodsid').val();
					    Eop.Dialog.open("apply_w_dialog");
					    var begin_nbr =$("[attr='p_begin_nbr']").val();
						var end_nbr=$("[attr='p_end_nbr']").val();
						var goods_num =$("[attr='goods_num']").val();
						var apply_desc =$("[attr='apply_desc']").val();
						var lan_id = $("#out_lan_id").val();
						apply_desc=encodeURI(encodeURI(apply_desc,true),true);//解决乱码
						var url = ctx + "/shop/admin/goods!showGoodsApply.do?ajax=yes&goods_id=" + 
									goodsid+"&begin_nbr="+begin_nbr+"&end_nbr="+end_nbr
									+"&goods_num="+goods_num+"&apply_desc="+apply_desc+"&lan_id="+lan_id ;
						$("#apply_w_dialog").load(url, function () {
						});
					}
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					
			},'json');
		});
	
		
  })
</script>