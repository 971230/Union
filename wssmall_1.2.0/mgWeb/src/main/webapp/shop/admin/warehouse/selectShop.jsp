<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/warehouse/js/selectShop.js"></script>
<style>
<!--
div {
    display: block;
}
.cate-wrapper {
    margin: auto;
    padding: 10px;
    width: 100%;
}

#cate-container {
    background: none repeat scroll 0 0 #F5F9FF;
    border: 1px solid #D5E4FA;
    height: 298px;
}


#cate-path {
    color: #444444;
    display: block;
    height: 54px;
    line-height: 22px;
    padding-top: 9px;
    position: relative;
}

#cate-path dl {
    background-color: #FFFAF2;
    border: 1px solid #FEDBAB;
    padding: 4px 27px;
}
dl, dd {
    margin: 0;
}
.clearfix {
    clear: both;
    display: block;
    height: 22px;
}

#cate-path dt {
    font-weight: 900;
}
#cate-path dt, #cate-path dd {
    float: left;
    margin: 0;
    padding: 0;
}


ul, ol {
    list-style: none outside none;
    margin: 0;
    padding: 0;
}

li{
   list-style: none;
}

#cateBottom {
    padding: 5px 0 24px;
    text-align: center;
}


#cate-container li.selected {
    background-color: #DFF1FB;
    border: 1px solid #9CD7FC;
}

#cate-container .cat_box {
    border-right: 2px solid #D5E4FA;
    display: block;
    float: left;
    height: 100%;
    margin-left: 5px;
    overflow-x: hidden;
    overflow-y: auto;
    width: 223px;
}

#cate-container .cat_box li {
    cursor: pointer;
    height: 20px;
    line-height: 20px;
    overflow: hidden;
    padding: 0 16px 0 14px;
}
.mid{
	vertical-align:middle;
}
li span{
	margin-left:5px;
}
-->
</style>
<div class="cate-wrapper">
	<div id="cate-container" class="grid">

		<table style="background-color: #F5F9FF;">
			<tr style="display:none;">
				<th style="width:200px;">省</th>
				<th style="width:200px;">地市</th>
				<th style="width:200px;">区县</th>
				<th style="width:200px;">商圈</th>
			</tr>
			<tr style="height: 268px;background-color: #F5F9FF;">
				<td style="height: 268px;width:200px;">
					<div id="box_0" class="cat_box">
						<!-- 一级节点 -->
					</div>
				</td>
				<td style="height: 268px;width:200px;">
					<div id="box_86" class="cat_box">
						<!-- 二级节点 -->
					</div>
				</td>
				<td style="height: 268px;width:200px;">
					<div id="box_91" class="cat_box">
						<!-- 三级节点 -->
					</div>
				</td>
				<td style="height: 268px;width:200px;">
					<div id="box_none" class="cat_box">
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div id="cate-path" style="display:none;">
		<dl>
			<div class="clearfix">
				<dt>您当前选择的是：</dt>
				<dd>
					<ol class="category-path">
					</ol>
				</dd>
			</div>
		</dl>

	</div>

	<div id="cateBottom">
		<input type="button" style="" class="comBtn" value="确&nbsp;定" id="confirmButton" name="button">
	</div>
</div>
