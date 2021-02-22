<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	
	.deleteBtn {
		letter-spacing: 25px;
		width: 50px;
		height: 25px;
		background-image: url('${ctx}/themes/default/images/icon_drop.gif');
		background-repeat: no-repeat;
		background-position: top center;
	}
	
	* html .deleteBtn {
		display: block;
	}
	
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
	
	#fourth_step .resig_table_div table th{ width:170px; text-align:right; padding-top:8px; padding-bottom:8px; line-height:24px;}
	
	
	</style>
<div class="input">
 <form  class="validate" method="post" action="" id='addCertificateform' validate="true">
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>证书编号:
						</th>
						<td>
							<input type="text" id="certificate_number"
								value="${supplierCertificate.certificate_number }"
								name="supplierCertificate.certificate_number" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>证书名称:
						</th>
						<td>
							<input type="text" id="certificate_name" name="supplierCertificate.certificate_name"
								value="${supplierCertificate.certificate_name }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>发证机关:
						</th>
						<td>
							<input type="text" id="license_issuing_organs"
								value="${supplierCertificate.license_issuing_organs }"
								name="supplierCertificate.license_issuing_organs" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>认证证书附件:
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
									<input type="hidden" name="supplierCertificate.certificate_url" id="certificate_url" type="text" dataType="string"  />
									<table style="width: 40%; height: auto;">
										<tr>
											<td height="179" align="left">
												<div attr='photo_div'>
													<div style='padding: 3px;'
														place_id='spanButtonPlaceHolder6' attr_name='place_div'>
														<span id="spanButtonPlaceHolder6"></span>
													</div>
													<div class="imgPrivew" id="imgPrivew6">
														<p style="margin-top: 100px">
															此处显示认证证书附件图片
															<br />
															[您还未上传认证证书附件图片！]
														</p>
													</div>
												</div>
											</td>
	
										</tr>
										<tr>
											<td id="uploadProgress6" align="left"
												style="padding-left: 25px">
											</td>
										</tr>
									</table>
								</div>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>有效期:
						</th>
						<td>
							<input type="text" name="supplierCertificate.certificate_period_validity" id="certificate_period_validity"
								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${supplierCertificate.certificate_period_validity }" />'
								maxlength="60" class="dateinput ipttxt" dataType="date"
								required="true" />
						</td>
					</tr>
				<tbody>
			</table>
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="supplier_id" value="${supplier_id }">
		 	
	           <input  type="button"  id="saveSupplierCertificateBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
   <script type="text/javascript">
	var ext='${ext}';
	
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder6','showimgId':'imgPrivew6','imgfilePath':'certificate_url',"uploadProgressId":'uploadProgress6'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#addCertificateform").validate();
      $("#saveSupplierCertificateBtn").click(function() {
			var url = ctx+ "/shop/admin/supplier!saveAddCertificate.do?ajax=yes";
			Cmp.ajaxSubmit('addCertificateform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						alert(responseText.message);
						SupplierDetail.showcertificateTab();
					}
					CertificateAdd.page_close();
			},'json');
		})
		
  })
</script>
