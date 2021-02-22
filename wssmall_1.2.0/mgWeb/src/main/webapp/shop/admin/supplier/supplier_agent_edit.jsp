<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.distributorL{ width:65%; float:left;}
.distributorR{ width:35%; float:left;}
.noborder{
  border-top-style: none;
  border-right-style: none;
  border-left-style: none;
  border-bottom-style: none;
  }
</style>
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
</style>
<div class="input">
	<form class="validate" method="post" action="" id='editAgentform'
		validate="true">
		<div class="distributorL">
			<input type="hidden" name="supplierAgent.agent_id" value="${supplierAgent.agent_id }" />
			<input type="hidden" name="supplierAgent.supplier_id" value="${supplierAgent.supplier_id }" />
			 <table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>厂商名:
						</th>
						<td>
							<input type="text" id="agent_name" name="supplierAgent.agent_name"
								value="${supplierAgent.agent_name }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>产品类别:
						</th>
						<td>
							<div id="agent_type_1"></div>
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
								<input type="hidden" name="supplierAgent.agent_attachment" id="agent_attachment"
									type="text" dataType="string"
									value="${supplierAgent.agent_attachment }" />
								<table style="width: 40%; height: auto;">
									<tr>
										<td height="179" align="left">
											<div attr='photo_div'>
												<div style='padding: 3px;' place_id='spanButtonPlaceHolder'
													attr_name='place_div'>
													<span id="spanButtonPlaceHolder"></span>
												</div>
												<div class="imgPrivew" id="imgPrivew">
													<c:if test="${supplierAgent.agent_attachment ==null}">
														<p style="margin-top: 100px">
															此处显示营业执照图片
															<br />
															[您还未上传营业执照图片！]
														</p>
													</c:if>
													<c:if test="${ supplierAgent.agent_attachment !=null}">
														<img height="220" width="240" src="${default_agent_attachment}" />
													</c:if>
												</div>
											</div>
										</td>

									</tr>
									<tr>
										<td id="uploadProgress" align="left"
											style="padding-left: 25px">
													<c:if test="${supplierAgent.agent_attachment !=null}">
														<c:forEach items="${agent_attachment_thumbnail_images}" var="url" >
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
				<tbody>
			</table>
		</div>
		<c:if test="${flag eq 'add' or flag eq 'edit'}">
			<div class="submitlist" align="center">
				<table align="right">
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input id="editAgentBtn" type="button" value=" 保存 " class="submitBtn" />
							<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="clear"></div>
	</form>
	<div id="auditDlg"></div>
</div>
<script type="text/javascript">
	var ext='${ext}';
	
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder','showimgId':'imgPrivew','imgfilePath':'agent_attachment',"uploadProgressId":'uploadProgress'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>
<script type="text/javascript"> 

$(function (){
     bindPhotoEvent();
     $("#agent_type_1").load(ctx + '/shop/supplier/supplier!showAgentType.do?ajax=yes',{}, function() {
     	 $('#agent_type').val(${supplierAgent.agent_type }) ;
     	  if('view'=='${flag}'){
	         $("input").attr("class","noborder");
	         $("#up").attr("style","display:none;");
	         $('#agent_type').attr('disabled','disabled');
     	  }
	});
   
    //添加或修改数据保存
	  var isedit=$("#is_edit").val();
	  
	  $("#editAgentform").validate();
      $("#editAgentBtn").click(function() {
            var  url = ctx+ "/shop/supplier/supplier!editAgent.do?ajax=yes";
			Cmp.ajaxSubmit('editAgentform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
							window.location=app_path+'/shop/admin/supplier!agentTab.do?is_edit=${is_edit }&supplier_id=${supplier_id }';	
					}
						
			},'json');
		})
	
  })
</script>