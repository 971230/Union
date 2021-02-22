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
 <form  class="validate" method="post" action="" id='addAccountform' validate="true">
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>开户银行:
						</th>
						<td>
							<input type="text" id="open_bank" name="supplierAccount.open_bank"
								value="${supplierAccount.open_bank }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>开户银行地址:
						</th>
						<td>
							<input type="text" id="address" name="supplierAccount.address"
								value="${supplierAccount.address }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>

					</tr>
					<tr>
						<th>
							<span class='red'>*</span>开户名:
						</th>
						<td>
							<input type="text" id="accoun_name"
								value="${supplierAccount.accoun_name }" name="supplierAccount.accoun_name"
								dataType="string" class="resigterIpt" autocomplete="on" maxlength="10"
								required="true"></input>
							<span class='red'> *字符长度不能超过10。</span>
						</td>
					<tr>
						<th>
							<span class='red'>*</span>账号:
						</th>
						<td>
							<input type="text" id="bank_account"
								value="${supplierAccount.bank_account }" name="supplierAccount.bank_account "
								dataType="string" class="resigterIpt" autocomplete="on"
								required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>开户许可证件:
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
								<input type="hidden" name="supplierAccount.account_attachment" id="account_attachment"
									type="text" dataType="string"
									value="${supplierAccount.account_attachment }" />
								<table style="width: 40%; height: auto;">
									<tr>
										<td height="179" align="left">
											<div attr='photo_div'>
												<div style='padding: 3px;' place_id='spanButtonPlaceHolder4'
													attr_name='place_div'>
													<span id="spanButtonPlaceHolder4"></span>
												</div>
												<div class="imgPrivew" id="imgPrivew4">
													<c:if test="${supplierAccount.account_attachment ==null}">
														<p style="margin-top: 100px">
															此处显示开户许可证件图片
															<br />
															[您还未上传开户许可证件图片！]
														</p>
													</c:if>
													<c:if test="${ supplierAccount.account_attachment !=null}">
														<img height="220" width="240" src="${default_account_attachment}" />
													</c:if>
												</div>
											</div>
										</td>

									</tr>
									<tr>
										<td id="uploadProgress4" align="left"
											style="padding-left: 25px">
													<c:if test="${supplierAccount.account_attachment !=null}">
														<c:forEach items="${account_attachment_thumbnail_images}" var="url" >
														<div class="progressContainer" >
															<div class="proWrapper" >
																<a img='${url }' href="javascript:;">
																<img border="0" src="${url }" class="uploadImg"/>
																</a>
																<input type="hidden" name="picnames" value="${url }"/>
															</div>
															<div><a  href="javascript:;" class="deleteBtn">&nbsp;</a></div>
															</div>	
														</c:forEach>
													</c:if>
										</td>
									</tr>

								</table>
							</div>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>是否默认账号:
						</th>
						<td>
							<select id="is_default" name="supplierAccount.is_default" dataType=''>
								<option value="1">
									是
								</option>
								<option value="2">
									否
								</option>
							</select>
						</td>
					</tr>
				<tbody>
			</table>
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="supplierAccount.supplier_id" value="${supplierAccount.supplier_id }">
		 	<input type="hidden" name="supplier_id" value="${supplier_id }">
		 	
	           <input  type="button"  id="saveSupplierAccountBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
 <script type="text/javascript">
	var ext='${ext}';
	
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder4','showimgId':'imgPrivew4','imgfilePath':'account_attachment',"uploadProgressId":'uploadProgress4'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#addAccountform").validate();
      $("#saveSupplierAccountBtn").click(function() {
			var url = ctx+ "/shop/admin/supplier!saveAddAccount.do?ajax=yes";
			Cmp.ajaxSubmit('addAccountform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						alert(responseText.message);
						SupplierDetail.showaccountTab();
					}
					AccountAdd.page_close();
			},'json');
		})
		
  })
</script>
