<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='editParShopform' validate="true">
  <div class="distributorL">
 <input type="hidden" id="is_edit" value="${is_edit }">
 <input type="hidden" id="partner_id" name="partShopView.partner_id" value="${partner_id}" />
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>*</span> 网店名字:</label></th>
       <td><input type="text" class="ipttxt"  name="partShopView.name" id="name" value="${partShopView.name }"  maxlength="60" size="40" dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 网址:</label></th>
       <td><input type="text" class="ipttxt"  name="partShopView.address" id="address"  value="${partShopView.address }" maxlength="60" size="40"  dataType="string" required="true"/><span class='red'>&nbsp;注:需要审核的字段 </span>
       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 网店量级:</label></th>
       <td>
       <c:if test="${flag eq 'add' or flag eq 'edit'}">
        <html:selectdict name="partShopView.star" curr_val="${partShopView.star}" attr_code="dc_partner_shop_star"></html:selectdict>
       </c:if>
       <c:if test="${flag eq 'view'}">
       ${partShopView.star_desc}
       </c:if>
       </td>
     </tr>
     
     <tr> 
       <th><label class="text"><span class='red'>*</span> 邮寄地址:</label></th>
       <td><input type="text" class="ipttxt"  name="partShopView.send_address" id="send_address"  value="${partShopView.send_address }" size="40"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 联系人:</label></th>
       <td><input type="text" class="ipttxt"  name="partShopView.linknam" id="linknam"  value="${partShopView.linknam }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
   <tr> 
       <th><label class="text"><span class='red'>*</span> 联系电话:</label></th>
       <td><input type="text" class="ipttxt"  name="partShopView.phone_no" id="phone_no"  value="${partShopView.phone_no }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 生效时间:</label></th>
       <td>
       <input type="text"   name="partShopView.eff_date" id="eff_date"   value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partShopView.eff_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 失效时间:</label> 
       </th>
       <td>
      <input type="text"  name="partShopView.exp_date" id="exp_date"  value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partShopView.exp_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/>       </td>
     </tr>
     <tr>
        <th><span class='red'>*</span>店铺详情图片：</th>
        <td>
          <input name="partShopView.shop_detail_image" type="hidden" value ="${partShopView.shop_detail_image}">
          <input type="hidden" name="partShopView.shop_default_image" id="uploadImage1"  dataType="string"
									value="${shopMapping.shop_default_image}" />
           <!-- 图片控件开始 -->
          <div class="form-table albumbox"  id="imgLoadDiv" >
								
									<table style="width: 40%; height: auto;">
											<tr>
												<td height="179" align="left">
													<div attr='photo_div'>
														<div style='padding: 3px;' place_id='spanButtonPlaceHolder1'
															attr_name='place_div'>
															<span id="spanButtonPlaceHolder1"></span>
														</div>
														<div class="imgPrivew" id="imgPrivew1">
															<c:if test="${partShopView.shop_default_image ==null}">
																<p style="margin-top: 100px">
																	此处显示店铺图片
																	<br />
																	[您还未上传店铺图片！]
																</p>
															</c:if>
															<c:if test="${ partShopView.shop_default_image !=null}">
																<img height="220" width="240" src="${partShopView.shop_default_image}" />
															</c:if>
														</div>
													</div>
												</td>
		
											</tr>
											<tr>
										<td id="uploadProgress1" align="left"
											style="padding-left: 25px"  >
											
											<c:if test="${partShopView.shop_detail_image !=null}">
												<c:forEach items="${detail_imgage_arr}" var="url" >
													
												<div class="progressContainer" >
															<div class="proWrapper" >
																<a img='${url }' href="javascript:;">
																<img border="0" src="${url }" class="uploadImg"/>
																</a>
																<input type="hidden" name="picnames" value="${url }"/>
															</div>
															<div><a  href="javascript:;" class="deleteBtn" name="old_deleteBtn">&nbsp;</a></div>
												</div>	
														
												</c:forEach>
											</c:if>
											
															
										</td>
									</tr>
								</table>
							</div>
           <!-- 图片控件结束 -->
        </td>
     </tr>
     <tr> 
       <th><label class="text">详情:</label></th>
       <td>
            <textarea class="textareaClass" name="partShopView.shop_desc"
			    id='shop_desc' cols="100" rows="8" style="width: 98%; height: 300px;">
	            <c:out value="${partShopView.shop_desc}" escapeXml="false"></c:out>
	        </textarea>
	   </td>  
     </tr>
	 </table> 
	 <c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	   <input type="hidden" name="partShopView.state" value="${partShopView.state }"> 
		 	    <input type="hidden" name="partShopView.sequ" value="${partShopView.sequ }"> 
		 	    <c:if test="${!is_aubtn}">
	           <input  type="button"  id="editPartnerShopBtn" value=" 保存 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/></c:if>
		   </td>
		</tr>
	 </table>
	</div>  
	</c:if> 
	</div>
   </form>
 </div>
  <script type="text/javascript">
    $('#shop_desc').ckeditor();
	var ext='${ext}';
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder1','showimgId':'imgPrivew1','imgfilePath':'uploadImage1',"uploadProgressId":'uploadProgress1'}]
	function setImgVal(){
		var picNamePicArr = $("#imgLoadDiv").find("[class='uploadImg']");
		var picNamePicStrArr =[];
		
		if(picNamePicArr.length>0){
			for(var i=0;i<picNamePicArr.length;i++){
				picNamePicStrArr[i]=picNamePicArr[i].src;
			}
			
			var picNamePicStr = picNamePicStrArr.join(",");
				$("[name='partShopView.shop_detail_image']").val(picNamePicStr);
			}	
	}
	function delImg(){
		$("[name='old_deleteBtn']").each(function(){
			  $(this).click(function(){
				  var deleteUL  = $(this).closest(".progressContainer");
				  var src =deleteUL.find("img").attr("src");
				  deleteUnSavePic(src)
				  deleteUL.remove();
				  var liLength = $(".progressContainer").length;
				  if(liLength==0){
					  $("#imgPrivew1").html("<p style='margin-top: 100px'>此处显示店铺图片<br />[您还未上传店铺图片！]</p>");
				  }
			  });
		}); 
	}
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
 <script type="text/javascript"> 
$(function (){
	 delImg();
	 if('view'=='${flag}'){
         $("input").attr("class","noborder");
     }
	  var isedit=$("#is_edit").val();
	  $("#editParShopform").validate();
      $("#editPartnerShopBtn").click(function() {
			
			if(!isedit){
			   if($("#partner_id").val()==""){
			       alert('请先保存分销商基本资料！');
                   return false;
			    }
			}
			var url = ctx+ "/shop/admin/partner!savePartnerShop.do?is_edit="+isedit+"&ajax=yes";
			setImgVal();
			Cmp.ajaxSubmit('editParShopform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 //PartnerDetail.showShop();
						 alert(responseText.message);
						 window.location=app_path+'/shop/admin/partner!list.do?';	
					}
					if (responseText.result == 2 || responseText.result == 3) {
						//2审核 3变更
							alert(responseText.message);
							//window.location=app_path+'/shop/admin/partner!auditlist.do?';
							window.location=app_path+'/shop/admin/partner!list.do?';	
					}
		
					
			},'json');
		});
	
		
  })
</script>