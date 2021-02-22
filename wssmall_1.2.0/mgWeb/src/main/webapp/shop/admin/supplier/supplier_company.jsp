<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script src="${staticserver}/js/common/jquery.validate.js"
	type="text/javascript"></script>
<link href="${staticserver}/js/common/validate/validate.css"
	rel="stylesheet" type="text/css" />

<script>var mainpage ='';var ctx='${ctx}'</script>

<script type="text/javascript"
	src="${staticserver}/js/common/lhgcore/lhgcore.js"></script>
<script type="text/javascript"
	src="${staticserver}/js/common/lhgcore/lhgcalendar/lhgcalendar.js"></script>
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

<!-- 
<link href="${ctx}/mgWebthemes/default/css/global.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/mgWebthemes/default/css/dialog.css" rel="stylesheet"
	type="text/css" />
 -->

<script type="text/javascript" src="${ctx}/shop/js/ShopUtil.js"></script>
<style>
.red {
	color: red;
}
</style>
<div class="input">
	<form class="validate" method="post" action="" id='editCompanyform'
		validate="true">
		<div class="distributorL">
			<input type="hidden" name="companyInfo1.company_id" value="${companyInfo1.company_id }" />
			<input type="hidden" name="companyInfo1.supplier_id" value="${companyInfo1.supplier_id }" />
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>公司全称：
						</th>
						<td>
							<input maxlength="50" id="company_name" name="companyInfo1.company_name"
								value="${companyInfo1.company_name }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>公司英文名称：
						</th>
						<td>
							<input maxlength="50" id="english_name" name="companyInfo1.english_name"
								value="${companyInfo1.english_name }" dataType="string"
								class="resigterIpt" type="text" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>企业类型：
						</th>
						<td>
							<div id="typeEnterprise">
							</div>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>公司简称：
						</th>
						<td>
							<input maxlength="50" id="abbreviation" name="companyInfo1.abbreviation"
								value="${companyInfo1.abbreviation }" dataType="string"
								class="resigterIpt" type="text" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>公司网址：
						</th>
						<td>
							<input maxlength="50" id="website" name="companyInfo1.website"
								value="${companyInfo1.website }" dataType="url"
								class="resigterIpt" autocomplete="on" type="text"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>是否境外企业：
						</th>
						<td>
							<div id="isAbroad">
							</div>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册地址：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.register_address"
								id="register_address" value="${companyInfo1.register_address }"
								dataType="string" type="text" class="resigterIpt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册时间：
						</th>
						<td>
							<input type="text" name="companyInfo1.register_date" id="eff_date"
								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${companyInfo1.register_date }" />'
								maxlength="60" class="dateinput ipttxt" dataType="date"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>法人代表/负责人：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.legal_name" id="legal_name"
								value="${companyInfo1.legal_name }" dataType=""
								class="resigterIpt _x_ipt" autocomplete="on" type="text"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>法人代表身份证号：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.legal_id_card" id="legal_id_card"
								value="${companyInfo1.legal_id_card }" dataType="id_card"
								class="resigterIpt _x_ipt" type="text" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册资金：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.register_funds" id="register_funds"
								value="${companyInfo1.register_funds }" dataType="float"
								class="resigterIpt" type="text" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>币种：
						</th>
						<td>
							<div id="currency1"></div>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>营业执照有效期：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.period_validity" required="true"
								dataType="date" onclick="WdatePicker()"
								class="dateinput ipttxt" type="text" id="period_validity"
								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${companyInfo1.period_validity }" />' autocomplete="on" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>营业执照注册号：
						</th>
						<td>
							<input maxlength="50" id="license_number" name="companyInfo1.license_number"
								value="${companyInfo1.license_number }" dataType="string"
								class="resigterIpt" type="text" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>营业执照附件：
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
								<input type="hidden" name="companyInfo1.license_url" id="license_url"
									type="text" dataType="string"
									value="${companyInfo1.license_url }" />
								<table style="width: 40%; height: auto;">
									<tr>
										<td height="179" align="left">
											<div attr='photo_div'>
												<div style='padding: 3px;' place_id='spanButtonPlaceHolder'
													attr_name='place_div'>
													<span id="spanButtonPlaceHolder"></span>
												</div>
												<div class="imgPrivew" id="imgPrivew">
													<c:if test="${companyInfo1.license_url ==null}">
														<p style="margin-top: 100px">
															此处显示营业执照图片
															<br />
															[您还未上传营业执照图片！]
														</p>
													</c:if>
													<c:if test="${ companyInfo1.license_url !=null}">
														<img height="220" width="240" src="${default_license_url}" />
													</c:if>
												</div>
											</div>
										</td>

									</tr>
									<tr>
										<td id="uploadProgress" align="left"
											style="padding-left: 25px">
													<c:if test="${companyInfo1.license_url !=null}">
														<c:forEach items="${license_url_thumbnail_images}" var="url" >
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
							<span class='red'>*</span>国家税务登记号码：
						</th>
						<td>
							<input maxlength="50" name="companyInfo1.country_registr_number"
								id="country_registr_number"
								value="${companyInfo1.country_registr_number }"
								dataType="string" class="resigterIpt _x_ipt" autocomplete="on"
								required="true" type="text" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>一般纳税人证件附件：
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
								<input type="hidden" name="companyInfo1.general_tax_url" id="general_tax_url"
									type="text" dataType="string"
									value="${companyInfo1.general_tax_url }" />
								<table style="width: 40%; height: auto;">
									<tr>
										<td height="179" align="left">
											<div attr='photo_div'>
												<div style='padding: 3px;' place_id='spanButtonPlaceHolder1'
													attr_name='place_div'>
													<span id="spanButtonPlaceHolder1"></span>
												</div>
												<div class="imgPrivew" id="imgPrivew1">
													<c:if test="${companyInfo1.general_tax_url ==null}">
														<p style="margin-top: 100px">
															此处显示营业执照图片
															<br />
															[您还未上传营业执照图片！]
														</p>
													</c:if>
													<c:if test="${ companyInfo1.general_tax_url !=null}">
														<img height="220" width="240" src="${default_general_tax_url}" />
													</c:if>
												</div>
											</div>
										</td>

									</tr>
									<tr>
										<td id="uploadProgress1" align="left"
											style="padding-left: 25px">
												<c:if test="${companyInfo1.general_tax_url !=null}">
														<c:forEach items="${general_tax_url_thumbnail_images}" var="url" >
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
							<span class='red'>*</span>地方税务登记号码：
						</th>
						<td>
							<input maxlength="50" id="place_registr_number"
								name="companyInfo1.place_registr_number"
								value="${companyInfo1.place_registr_number }" dataType=""
								type="text" class="resigterIpt _x_ipt" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>地方税务登记号附件：
						</th>
						<td>
							<div id="album_tab" class="form-table albumbox">
								<input type="hidden" name="companyInfo1.place_registr_url"
									id="place_registr_url" type="text" dataType="string"
									value="${companyInfo1.place_registr_url }" />
								<table style="width: 40%; height: auto;">

									<tr>
										<td height="179" align="left">
											<div attr='photo_div'>
												<div style='padding: 3px;' place_id='spanButtonPlaceHolder2'
													attr_name='place_div'>
													<span id="spanButtonPlaceHolder2"></span>
												</div>
												<div class="imgPrivew" id="imgPrivew2">
													<c:if test="${companyInfo1.place_registr_url ==null}">
														<p style="margin-top: 100px">
															此处显示营业执照图片
															<br />
															[您还未上传营业执照图片！]
														</p>
													</c:if>
													<c:if test="${ companyInfo1.place_registr_url !=null}">
														<img height="220" width="240" src="${default_place_registr_url}" />
													</c:if>
												</div>
											</div>
										</td>

									</tr>
									<tr>
										<td id="uploadProgress2" align="left"
											style="padding-left: 25px">
												<c:if test="${companyInfo1.place_registr_url !=null}">
														<c:forEach items="${place_registr_url_thumbnail_images}" var="url" >
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
							<span class='red'>*</span>组织机构代码：
						</th>
						<td>
							<input maxlength="20" name="companyInfo1.organ_code" id="organ_code"
								value="${companyInfo1.organ_code }" type="text"
								dataType="string" class="resigterIpt _x_ipt" autocomplete="on"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>开票类型：
						</th>
						<td>
							<div id="ticketType"></div>
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
							<input type="hidden" id="supplier_id" name="supplier_id" value="${supplier_id }"/>
						 	 <input type="hidden" id="supplier_type" name="supplier_type" value="${supplier_type }"/>
						 	 <input type="hidden" id="is_edit" name="is_edit"value="${is_edit }">
						 	 <input type="hidden" id="flag" name="flag" value="${flag }">
							<input id="editCompanyBaseBtn" type="button" value=" 保存 "
								class="submitBtn" />
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
<div id="refAgentDlg"></div>

<script type="text/javascript">
	var ext='${ext}';
	
	var uploadBtns = [{'btnId':'spanButtonPlaceHolder','showimgId':'imgPrivew','imgfilePath':'license_url',"uploadProgressId":'uploadProgress'},
	{'btnId':'spanButtonPlaceHolder1','showimgId':'imgPrivew1','imgfilePath':'general_tax_url',"uploadProgressId":'uploadProgress1'},
	{'btnId':'spanButtonPlaceHolder2','showimgId':'imgPrivew2','imgfilePath':'place_registr_url',"uploadProgressId":'uploadProgress2'}]
	
</script>
<script src="${staticserver}/js/common/upload.js" type="text/javascript"></script>

<script type="text/javascript"> 
$(function (){
		$("form.validate").validate();
		//initUpload('${ctx}','${ext}');
		bindPhotoEvent();
		$("#typeEnterprise").load(
					ctx + '/shop/supplier/supplier!showEnterpriseType.do?ajax=yes',
					{}, function() {
				$('#enterprise_type').val(${companyInfo1.enterprise_type }) ;
			});
			$("#isAbroad").load(
					ctx + '/shop/supplier/supplier!isAbroad.do?ajax=yes',
					{}, function() {
					$('#is_abroad').val(${companyInfo1.is_abroad }) ;
					});
					
			$("#ticketType").load(
					ctx + '/shop/supplier/supplier!showTicketType.do?ajax=yes',
					{}, function() {
					 $('#ticket_type').val(${companyInfo1.ticket_type }) ;
					});
			
			$("#currency1").load(
					ctx + '/shop/supplier/supplier!showCurrency.do?ajax=yes',
					{}, function() {
					 $('#currency').val(${companyInfo1.currency }) ;
					});
			
			 
			
     if('view'=='${flag}'){
         $("input").attr("class","noborder");
         $("#up").attr("style","display:none;");
     }
     
     //修改数据保存
	  var isedit=$("#is_edit").val();
	  
	  $("#editCompanyform").validate();
      $("#editCompanyBaseBtn").click(function() {
      		var supplier_id=$("input[name='supplier_id']").val();
      		if(supplier_id==""){
             alert('请先保存供货商基本资料！');
             return false;
         	}
            var  url = ctx+ "/shop/supplier/supplier!editCompany.do?ajax=yes";
			Cmp.ajaxSubmit('editCompanyform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
						    $('#editCompanyBaseBtn').attr('disabled',"true");
							//window.location=app_path+'/shop/admin/supplier!list.do?is_administrator=0';	
					}
						
			},'json');
		})
  })
</script>
