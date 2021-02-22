<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>
<script type="text/javascript" charset="utf-8" src="js/selector_partner.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">配送地址</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>

<div class="input">
 <form  class="validate" method="post" action="" id='addform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
   <!--    <tr> 
       <th><label class="text">分销商编号:</label></th>
       <td><input type="text" class="ipttxt"  name="paraddr.partner_id" id="partner_id" value="${paraddr.partner_id }"  maxlength="60"  dataType="string" required="true"/>
           <input type="button" id="refPartnerBtn" value="关联分销商" />
       </td>
     </tr>-->
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
       <td><input type="text" class="ipttxt"  name="paraddr.mobile" id="mobile"  value="${paraddr.mobile }"  maxlength="60"  dataType="string" />       </td>
     </tr>
    <tr> 
       <th><label class="text"><span class='red'>* </span>地址:</label></th>
   <td><input type="text" class="ipttxt"  name="paraddr.addr" id="addr" size="50" value="${paraddr.addr }"  maxlength="60"  dataType="string" />       </td> </tr>
	 </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
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
						  
							window.location=app_path+'/shop/admin/parAddr!list.do';
					}
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					
					
			},'json');
		});
	
		
  })
</script>