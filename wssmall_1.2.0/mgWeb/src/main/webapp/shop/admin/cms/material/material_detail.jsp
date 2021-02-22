<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/cms/js/material.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">素材详情</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<div id="brandInfo">
		<form method="post" action="material!save.do" id="materialForm" class="validate"
			name="termForm" enctype="multipart/form-data" >
			<table cellspacing="1" cellpadding="3" width="100%"
				class="form-table">
				<tr>
					<th><label class="text">素材名称：</label><textarea style="display:none;" id="textarea_hidden" rows="4" cols="20">${material.attribute }</textarea><input type="hidden" id="hiddenMaterialId" name="material.aid" value="${material.aid}"></th>
					<td><input type="text" class="ipttxt" name="material.aname"
						maxlength="60" value="${material.aname }" id="materialNameCheck" dataType="string" required="true" /><b id="checkResult" ></b></td>
					<th><label class="text">素材编码：</label></th>
					<td><input type="text" class="ipttxt" name="material.attachment" id="notChinese"
						maxlength="60" value="${material.attachment }" readonly="readonly" /><font color="red">*系统自动生成</font></td>
				</tr>
				<tr>
					<th><label class="text">素材大类：</label></th>
					<td><input type="hidden" value="${material.atype }" id="hiddenBigType" />
					<html:selectdict name='material.atype' style="width:154px"  id="select_big_type"  attr_code="DC_MATERIAL_TYPE_BIG"  ></html:selectdict>
					</td>
					<th><label class="text">素材小类：</label></th>
					<td><input type="hidden" value="${material.subtype }" id="hiddenSmallType" />
						<span id="type_small_1"><html:selectdict  style="width:154px"  name="material.subtype"  attr_code="DC_MATERIAL_TYPE_1"  ></html:selectdict></span>
						<span id="type_small_2" style="display:none;"><html:selectdict  style="width:154px"    attr_code="DC_MATERIAL_TYPE_2"  ></html:selectdict></span>
						<span id="type_small_3" style="display:none;"><html:selectdict  style="width:154px"    attr_code="DC_MATERIAL_TYPE_3"  ></html:selectdict></span>
						<span id="type_small_4" style="display:none;"><html:selectdict  style="width:154px"    attr_code="DC_MATERIAL_TYPE_4"  ></html:selectdict></span>
						<span id="type_small_5" style="display:none;"><html:selectdict  style="width:154px"    attr_code="DC_MATERIAL_TYPE_5"  ></html:selectdict></span>
					</td>
				</tr>
				<tr>
					<th><label class="text">素材类型：</label></th>
					<td><select name="material.staff_no" >
						<option value="1">公有素材</option>
						<option value="0">私有素材</option>
					</select></td>
					<th><label class="text">素材URL：</label></th>
					<td><input type="text" class="ipttxt" name="material.url" size="40" value="${material.url }" dataType="string"/></td>
				</tr>
				<tr>
					<th>素材内容：</th>
					<td><input type="text" class="ipttxt" name="material.contact" size="40" value="${material.contact }" dataType="string" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				<tr><td><span style="margin-left:50px;"><b>个性化属性：</b></span></td><td><input type="hidden" name="material.attribute" value="" id="attribute_hidden" /></td><td></td><td></td></tr>
				<tr class="shiping">
					<th><label class="text">视频分辨率：</label></th>
					<td><input type="text" class="ipttxt" name="resolution" value="" /><font color="red">&nbsp;&nbsp;*格式：800*600</font></td>
					<th><label class="text">视频文件格式：</label></th>
					<td><input type="text" class="ipttxt" name="format" value="" /></td>
				</tr>
				<tr class="shiping">
					<th><label class="text">视频大小：</label></th>
					<td><input type="text" class="ipttxt" name="size" value="" /></td>
					<th><label class="text">视频时长：</label></th>
					<td><input type="text" class="ipttxt" name="timeLength" value="" /></td>
				</tr>
				<tr class="shiping">
					<th><label class="text">视频文件：</label></th>
					<td colspan="3">
					<input type="file" class="ipttxt" id="file" name="video" /><font color="red">&nbsp;&nbsp;*文件不能大于20M&nbsp;&nbsp;&nbsp;&nbsp;目前支持的格式：SWF,AVI,3GP,MP4,RMVB,RM,MPG,MPEG,MPE,WMV,ASF,ASX,M4V,MKV,FLV</font></td>
					<!-- <th>&nbsp;</th>
					<td>&nbsp;</td> -->
				</tr>
				<tr class="tupian" style="display:none;">
					<th><label class="text">图片分辨率：</label></th>
					<td><input type="text" class="ipttxt" name="resolution" value="" /></td>
					<th><label class="text">图片大小：</label></th>
					<td><input type="text" class="ipttxt" name="size" value="" /></td>
				</tr>
				<tr class="tupian" style="display:none;">
					<th><label class="text">上传图片：</label></th>
					<td><input type="file" class="ipttxt" /><font color="red">&nbsp;&nbsp;格式：GIF,JPG,BMP,SWF,JPEG,PNG</font></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${material.atturl !=null}">
				<tr class="tupian" style="display:none;">
					<th style="width:140px">&nbsp;</th>
					<td><img width="300" height="200"class="material" src="${material.atturl }"  /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				</c:if>   
				<tr class="yingyong" style="display:none;">
					<th><label class="text">应用内容：</label></th>
					<td><input type="text" class="ipttxt" name="resolution" value="" /></td>
					<th><label class="text">上传图片：</label></th>
					<td><input type="file" class="ipttxt" /><font color="red">&nbsp;&nbsp;格式：GIF,JPG,BMP,SWF,JPEG,PNG</font></td>
				</tr>
				<c:if test="${material.atturl !=null}">
				<tr class="yingyong" style="display:none;">
					<th style="width:140px">&nbsp;</th>
					<td><img width="300" height="200"class="material" src="${material.atturl }"  /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				</c:if>   
				<tr class="xinwen" style="display:none;">
					<th><label class="text">新闻标题：</label></th>
					<td><input type="text" class="ipttxt" name="title" value="" /></td>
					<th><label class="text">新闻内容：</label></th>
					<td><input type="text" class="ipttxt" name="content" value="" /></td>
				</tr>
				<tr class="xinwen" style="display:none;">
					<th><label class="text">生效时间：</label></th>
					<td><input size="20" type="text"  name="eff_date" id="startDate_1"
								value='${startDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/>  
					</td>
					<th><label class="text">失效时间：</label></th>
					<td>
					<input size="20" type="text"  name="exp_date" id="endDate_1"
								value='${startDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/>  
					</td>
				</tr>
				<tr class="xinwen" style="display:none;">
					<th><label class="text">新闻显示模板：</label></th>
					<td><input type="text" class="ipttxt" name="model" value="" /></td>
					<th><label class="text">新闻状态：</label></th>
					<td><select style="width:154px" id="news_state_sel">
						<option value="1">正常</option>
						<option value="0">停用</option>
					</select>
					<input type="hidden" class="ipttxt" name="state" value="1" /></td>
				</tr>
				<tr class="xinwen" style="display:none;">
					<th><label class="text">上传图片：</label></th>
					<td><input type="file" class="ipttxt" /><font color="red">&nbsp;&nbsp;格式：GIF,JPG,BMP,SWF,JPEG,PNG</font></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				<c:if test="${material.atturl !=null}">
				<tr class="xinwen" style="display:none;">
					<th style="width:140px">&nbsp;</th>
					<td><img width="300" height="200"class="material" src="${material.atturl }"  /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				</c:if> 
				<tr class="gonggao" style="display:none;">
					<th><label class="text">公告标题：</label></th>
					<td><input type="text" class="ipttxt" name="title" value="" /></td>
					<th><label class="text">公告内容：</label></th>
					<td><input type="text" class="ipttxt" name="content" value="" /></td>
				</tr>
				<tr class="gonggao" style="display:none;">
					<th><label class="text">生效时间：</label></th>
					<td><input size="20" type="text"  name="eff_date" id="startDate_2"
								value='${startDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/>  
					</td>
					<th><label class="text">失效时间：</label></th>
					<td>
					<input size="20" type="text"  name="exp_date" id="endDate_2"
								value='${startDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/>  
					</td>
				</tr>
				<tr class="gonggao" style="display:none;">
					<th><label class="text">公告显示模板：</label></th>
					<td><input type="text" class="ipttxt" name="model" value="" /></td>
					<th><label class="text">公告状态：</label></th>
					<td>
						<select style="width:154px" id="bbs_state_sel">
							<option value="1">正常</option>
							<option value="0">停用</option>
						</select>
					<input type="hidden" class="ipttxt" name="state" value="1" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><a href="javascript:void(0);" class="searchBtn" id="materialConfirmBtn" ><span>确&nbsp;定</span></a></td>
				</tr>
			</table>

		</form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("form.validate").validate();
	});
</script>