<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.progressContainer {
	float: left;
	padding-left: 10px;
	width: 50px;
}

.proWrapper {
	border: solid 1px #BBDDE5;
	width: 50px;
	height: 50px;
	float: left;
	margin: 5px 5px 5px 5px;
}

.uploadImg {
	width: 50px;
	height: 50px;
}

.progressState {
	margin-top: 15px;
	height: 20px;
	background-color: #E8F2FE;
}

.progressText {
	margin-top: -18px;
	padding-left: 30px
}

.imgPrivew {
	background: #F7F6F6;
	border: 2px solid #5CA647;
	margin-bottom: 10px;
	padding: 2px;
	width: 280px;
	height: 250px;
	text-align: center;
}

.deleteBtn{
	letter-spacing:25px;
	width:50px;
	height:25px;
	background-image: url("${context}/images/icon_drop.gif");
	background-repeat:no-repeat;
	background-position:top center ;

}
* html .deleteBtn{ display:block;}

ul,li {
	list-style: none;
}

.input_panel {
	float: left
}

.input_panel ul {
	margin: 1px;
	width: 100%;
	float: left;
}

.input_panel ul li {
	float: left;
	border: 1px solid #EFEFEF;
}

.input_panel ul li.text {
	background-color: #F8FAFC;
	width: 150px;
	text-align: right;
	line-height: 25px;
}

.input_panel ul li.input {
	border-left: 0;
	padding-left: 5px;
	width: 350px;
	line-height: 25px;
}

.input_panel ul li.adj_input {
	border-left: 0;
	padding-left: 5px;
	width: 450px;
	line-height: 25px;
}

.input_panel .input_text {
	width: 200px
}

.groupname {
	font-size: 14px
}

.albumbox table tr {
	border-bottom: 0;
}
</style>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>


	<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">店铺映射</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div style="display: block;height:700px;" class="partner_detail">
<div class="input">
 <form  class="validate" method="post" action="" id='addform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>* </span>店铺编号:</label></th>
       <td><input type="text" class="ipttxt"  name="shopMapping.shop_code" id="shop_code"  value="${shopMapping.shop_code }" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>店铺名称:</label></th>
       <td><input type="text" class="ipttxt"  name="shopMapping.shop_name" id="shop_name"  value="${shopMapping.shop_name }" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
   <tr> 
   <tr> 
      <!--  <th><label class="text"><span class='red'>* </span>店铺类型:</label></th>
       <td>
        <html:selectdict attr_code="DC_PARTER_SHOP_STYPE" curr_val="${shopMapping.shop_type}" name="shopMapping.shop_type" ></html:selectdict>
    </td> -->
    <th>店铺类型：</th>
				<td>
						<select  class="ipttxt"  style="width: 155px" id=shop_type name="shop_type" >
							<c:forEach var="shopMapping" items="${shopMappingList}">
								<option  value="${shopMapping.shop_type }" ${shop_type == shopMapping.shop_type ? ' selected="selected" ' : ''}>${shopMapping.shop_type_name }</option>
							</c:forEach>
					    </select>
				<td>
     </tr>
     
     <tr>
        <th><span class='red'>*</span>店铺详情图片：</th>
        <td>
          <input name="shopMapping.shop_detail_image" type="hidden" value ="${shopMapping.shop_detail_image}">
          <input type="hidden" name="shopMapping.shop_default_image" id="uploadImage1"  dataType="string"
									value="${shopMapping.shop_default_image}" />
           <!-- 图片控件开始 -->
          <div class="form-table albumbox"  id="imgLoadDiv" >
								
									<table style="width: 40%; height: auto;">
											<tr>
												<td height="179" align="left">
													<div attr='photo_div'>
														<div style='padding: 3px;' place_id='spanButtonPlaceHolder'
															attr_name='place_div'>
															<span id="spanButtonPlaceHolder"></span>
														</div>
														<div class="imgPrivew" id="imgPrivew">
															<c:if test="${shopMapping.shop_default_image ==null}">
																<p style="margin-top: 100px">
																	此处显示店铺图片
																	<br />
																	[您还未上传店铺图片！]
																</p>
															</c:if>
															<c:if test="${ shopMapping.shop_default_image !=null}">
																<img height="220" width="240" src="${shopMapping.shop_default_image}" />
															</c:if>
														</div>
													</div>
												</td>
		
											</tr>
											<tr>
										<td id="uploadProgress" align="left"
											style="padding-left: 25px"  >
											
											<c:if test="${shopMapping.shop_detail_image !=null}">
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
            <textarea class="textareaClass" name="shopMapping.shop_desc"
			    id='shop_desc' cols="100" rows="8" style="width: 98%; height: 300px;">
	            <c:out value="${shopMapping.shop_desc}" escapeXml="false"></c:out>
	        </textarea>
	   </td>  
     </tr>
   </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	  <input  type="button"  id="saveBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div></div>
 <div  id="refPartnerDlg"></div>
 <script type="text/javascript">
    $('#shop_desc').ckeditor();
	var ext='${ext}';
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder','showimgId':'imgPrivew','imgfilePath':'uploadImage1',"uploadProgressId":'uploadProgress'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
 
  <script type="text/javascript"> 
function setImgVal(){
	var picNamePicArr = $("#imgLoadDiv").find("[class='uploadImg']");
	var picNamePicStrArr =[];
	
	if(picNamePicArr.length>0){
		for(var i=0;i<picNamePicArr.length;i++){
			picNamePicStrArr[i]=picNamePicArr[i].src;
		}
		
		var picNamePicStr = picNamePicStrArr.join(",");
			$("[name='shopMapping.shop_detail_image']").val(picNamePicStr);
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
				  $("#imgPrivew").html("<p style='margin-top: 100px'>此处显示店铺图片<br />[您还未上传店铺图片！]</p>");
			  }
		  });
	}); 
}
$(function (){
	  $("#addform").validate();
	  delImg();
      $("#saveBtn").click(function() {
    	    setImgVal();
			var url = ctx+ "/shop/admin/shopMapping!save.do?ajax=yes";
			Cmp.ajaxSubmit('addform', '', url, {}, function(responseText){
			        
					if (responseText.result == 0) {
						//保存成功后
						
						   alert(responseText.message);
						  
							window.location=app_path+'/shop/admin/shopMapping!list.do';
					}
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					
			},'json');
		});
	
  })
</script>