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
 <form  class="validate" method="post" action="" id='addAgentform' validate="true">
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>厂商名:
						</th>
						<td>
							<input type="text" id="open_bank" name="supplierAgent.agent_name"
								value="${supplierAgent.agent_name }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true" maxlength="20"></input>
							<span class='red'>*字符长度不能超过20</span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>产品类别:
						</th>
						<td>
							<div id="agent_type"></div>
						</td>

					</tr>
					<tr>
						<th>
							<span class='red'>*</span>代理授权证书编号:
						</th>
						<td>
							<input type="text" id="agent_certificate_number"
								value="${supplierAgent.agent_certificate_number}" name="supplierAgent.agent_certificate_number"
								dataType="string" class="resigterIpt" autocomplete="on"
								required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>代理原厂商附件:
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
									<input type="hidden" name="supplierAgent.agent_attachment" id="agent_attachment" type="text" dataType="string" />
									<table style="width: 40%; height: auto;">
	
										<tr>
											<td height="179" align="left">
												<div attr='photo_div'>
													<div style='padding: 3px;'
														place_id='spanButtonPlaceHolder5' attr_name='place_div'>
														<span id="spanButtonPlaceHolder5"></span>
													</div>
													<div class="imgPrivew" id="imgPrivew5">
														<p style="margin-top: 100px">
															此处显示代理原厂商附件图片
															<br />
															[您还未上传代理原厂商附件图片！]
														</p>
													</div>
												</div>
											</td>
	
										</tr>
										<tr>
											<td id="uploadProgress5" align="left"
												style="padding-left: 25px">
											</td>
										</tr>
									</table>
								</div>
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
		 	
	           <input  type="button"  id="saveSupplierAgentBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
  <script type="text/javascript">
	var ext='${ext}';
	
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder5','showimgId':'imgPrivew5','imgfilePath':'agent_attachment',"uploadProgressId":'uploadProgress5'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
 <script type="text/javascript"> 
$(function (){
	  $("#agent_type").load(
		ctx + '/shop/supplier/supplier!showAgentType.do?ajax=yes',
		{}, function() {
	});
	  $("#addAgentform").validate();
      $("#saveSupplierAgentBtn").click(function() {
			var url = ctx+ "/shop/admin/supplier!saveAddAgent.do?ajax=yes";
			Cmp.ajaxSubmit('addAgentform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						alert(responseText.message);
						SupplierDetail.showagentTab();
					}
					AgentAdd.page_close();
			},'json');
		})
		
  })
</script>
