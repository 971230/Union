<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/shop/admin/goodsN/css/uploadImg.css"
	type="text/css" />

<div class="tab_n">
	<ul class="clearfix" id="ul_tab">
		<li class="curr" ele="pc_cont"><a href="javascript:void(0);"><img
				src="<%=request.getContextPath()%>/shop/admin/goodsN/images/ic_pc.png"
				width="16" height="16" />商品详情·PC版</a>
		</li>
		<li ele="pad_cont"><a href="javascript:void(0);"><img
				src="goodsN/images/ic_pad.png" width="16" height="16" />商品详情·触屏版</a>
		</li>
		<li ele="app_cont"><a href="javascript:void(0);"><img
				src="goodsN/images/ic_phone.png" width="16" height="16" />商品详情·APP版</a>
		</li>
	</ul>
</div>
<!-- PC版图片 -->
<div class="item_box_cont" name="img_upload_cont" id="pc_cont">
	<div class="title">
		商品图片·PC版<span style="color:red;">(最多上传五张)</span>
	</div>
	<!-- 图片上传按钮 -->
	<div id="album_tab" class="form-table albumbox">
		<input type="hidden" name="goodsExtData.pc_image_file"
			id="goodsExtData.pc_image_file" type="text" dataType="string"
			value="${goodsExtData.pc_image_file}" />
		<div style='padding-left: 30px;' place_id='spanButtonPlaceHolder'
			attr_name='place_div'>
			<span id="spanButtonPlaceHolder"></span>
		</div>
		<div style="display:none;" id="imgPrivew"></div>
	</div>
	<div class="img_list" divName="PC版图片">
		<ul class="clearfix" id="uploadProgress" name="pc_ul"
			valueName="goodsExtData.pc_image_file">

			<c:if test="${goodsExtData.pc_image_file !=null}">
				<c:forEach items="${pc_image_file_list}" var="url">
					<li>
						<div class="img_box">
							<div class="img_div">
								<a img='${url}' href="javascript:;"> <img border="0"
									src="${url }" style="width: 116px;height: 116px;"
									class="uploadImg" /> </a> <input type="hidden" name="picnames"
									value="${url }" />
							</div>
							<div class="btn_box">
								<a href="javascript:;" name="old_deleteBtn" class="gray_b">删除</a>
							</div>
						</div></li>
				</c:forEach>
			</c:if>

			<li id="img_box_model" style="display:none;">
				<div class="img_box">
					<div class="img_div">
						<img src="goodsN/images/img_nor.jpg" width="116" height="116" />
					</div>
					<div class="btn_box">
						<p style="margin-left: 5px;color: red;">您还未上传商品PC版图片</p>
					</div>
				</div></li>
			<c:if test="${pc_image_file_list ==null}">
				<script type="text/javascript">
					$("[valueName='goodsExtData.pc_image_file']").find(
							"#img_box_model").show();
				</script>
			</c:if>
		</ul>
	</div>
</div>
<!-- PC版图片结束 -->
<!-- pad版图片 -->
<div class="item_box_cont" name="img_upload_cont" id="pad_cont"
	style="display: none;">
	<div class="title">
		商品图片·触屏版<span style="color:red;">(最多上传五张)</span>
	</div>
	<!-- 图片上传按钮 -->
	<div id="album_tab" class="form-table albumbox">
		<input type="hidden" name="goodsExtData.mbl_image_file"
			id="goodsExtData.mbl_image_file" type="text" dataType="string"
			value="${goodsExtData.mbl_image_file}" />
		<div style='padding-left: 30px;' place_id='spanButtonPlaceHolder1'
			attr_name='place_div'>
			<span id="spanButtonPlaceHolder1"></span>
		</div>
		<div style="display:none;" id="imgPrivew1"></div>
	</div>
	<div class="img_list" divName="触屏版图片">
		<ul class="clearfix" id="uploadProgress1"
			valueName="goodsExtData.mbl_image_file">
			<c:if test="${goodsExtData.mbl_image_file !=null}">
				<c:forEach items="${mbl_image_file_list}" var="url">
					<li>
						<div class="img_box">
							<div class="img_div">
								<a img='${url}' href="javascript:;"> <img border="0"
									src="${url }" style="width: 116px;height: 116px;"
									class="uploadImg" /> </a> <input type="hidden" name="picnames"
									value="${url }" />
							</div>
							<div class="btn_box">
								<a href="javascript:;" name="old_deleteBtn" class="gray_b">删除</a>
							</div>
						</div></li>
				</c:forEach>
			</c:if>
			<li id="img_box_model" style="display:none;">
				<div class="img_box">
					<div class="img_div">
						<img src="goodsN/images/img_nor.jpg" width="116" height="116" />
					</div>
					<div class="btn_box">
						<p style="margin-left: 5px;color: red;">您还未上传商品触屏版图片</p>
					</div>
				</div></li>
			<c:if test="${pc_image_file_list ==null}">
				<script type="text/javascript">
					$("[valueName='goodsExtData.mbl_image_file']").find(
							"#img_box_model").show();
				</script>
			</c:if>

		</ul>
	</div>
</div>
<!-- pad版图片结束 -->
<!-- app版图片 -->
<div class="item_box_cont" id="app_cont" name="img_upload_cont"
	style="display: none;">
	<div class="title">
		商品图片·APP版<span style="color:red;">(最多上传五张)</span>
	</div>
	<!-- 图片上传按钮 -->
	<div id="album_tab" class="form-table albumbox">
		<input type="hidden" name="goodsExtData.wx_image_file"
			id="goodsExtData.wx_image_file" type="text" dataType="string"
			value="${goodsExtData.wx_image_file}" />
		<div style='padding-left: 30px;' place_id='spanButtonPlaceHolder2'
			attr_name='place_div'>
			<span id="spanButtonPlaceHolder2"></span>
		</div>
		<div style="display:none;" id="imgPrivew2"></div>
	</div>
	<div class="img_list" divName="APP版图片">
		<ul class="clearfix" id="uploadProgress2"
			valueName="goodsExtData.wx_image_file">
			<c:if test="${goodsExtData.wx_image_file !=null}">
				<c:forEach items="${wx_image_file_list}" var="url">
					<li>
						<div class="img_box">
							<div class="img_div">
								<a img='${url}' href="javascript:;"> <img border="0"
									src="${url }" style="width: 116px;height: 116px;"
									class="uploadImg" /> </a> <input type="hidden" name="picnames"
									value="${url }" />
							</div>
							<div class="btn_box">
								<a href="javascript:;" name="old_deleteBtn" class="gray_b">删除</a>
							</div>
						</div></li>
				</c:forEach>
			</c:if>
			<li id="img_box_model" style="display:none;">
				<div class="img_box">
					<div class="img_div">
						<img src="goodsN/images/img_nor.jpg" width="116" height="116" />
					</div>
					<div class="btn_box">
						<p style="margin-left: 5px;color: red;">您还未上传商品APP版图片</p>
					</div>
				</div></li>
			<c:if test="${wx_image_file_list ==null}">
				<script type="text/javascript">
					$("[valueName='goodsExtData.mbl_image_file']").find(
							"#img_box_model").show();
				</script>
			</c:if>
		</ul>
	</div>
</div>
<!-- app版图片结束 -->
<div class="item_box_cont">
	<div class="title">
		商品图片<span>简要的文字描述，不超过150个字</span>
	</div>
	<div class="ipt_div_cont">
		<textarea class='ipttxt' name="goodsExtData.other_remark"
			id="textarea" cols="45" rows="5" style="width:100%;height:80px;">${goodsExtData.other_remark}</textarea>
	</div>
</div>
<div class="item_box_cont">
	<div class="title">商品详情</div>
	<div class="ipt_div_cont">
		<textarea class="textareaClass" name="goodsExtData.other_intors"
			id='intro' cols="100" rows="8" style="width: 98%; height: 300px;">
	            <c:out value="${goodsExtData.other_intors}"
				escapeXml="false"></c:out>
	        </textarea>
	</div>
</div>

<script type="text/javascript">
	$('#intro').ckeditor();
	var ext = '${ext}';
	var uploadBtns = [ {
		'btnId' : 'spanButtonPlaceHolder',
		'showimgId' : 'imgPrivew',
		'imgfilePath' : 'goodsExtData.pc_image_file',
		"uploadProgressId" : 'uploadProgress'
	}, {
		'btnId' : 'spanButtonPlaceHolder1',
		'showimgId' : 'imgPrivew1',
		'imgfilePath' : 'goodsExtData.mbl_image_file',
		"uploadProgressId" : 'uploadProgress1'
	}, {
		'btnId' : 'spanButtonPlaceHolder2',
		'showimgId' : 'imgPrivew2',
		'imgfilePath' : 'goodsExtData.wx_image_file',
		"uploadProgressId" : 'uploadProgress2'
	} ]
</script>



