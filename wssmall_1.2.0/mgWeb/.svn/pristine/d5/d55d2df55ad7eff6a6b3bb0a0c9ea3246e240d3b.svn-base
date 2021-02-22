<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据权限</title>
</head>
<body>
	<input type="hidden" id="dataJSONObj" name="dataJSONObj" value="${roleDataJSONStr}" />	
	<div class="new_right">
		<div class="right_warp">
			
		<div class="roleWarp">
			<div class="grid_n_div">
				<h2>角色数据权限授权<span>(带勾为已经选中的)</span></h2>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权环节：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${nodeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectNodeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权地市：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${regionList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectRegionItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权来源：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${originList}">
		                	<label><input type="checkbox" value="${obj.other_field_value}" name="_selectOriginItem"> ${obj.field_value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
<%-- 				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权子来源：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${sonOrderTypeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectSonOrderTypeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div> --%>
				<%-- <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权特殊商品类型：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${specialprodTypeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectSpecialprodTypeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div> --%>
				<%-- <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权锁定状态：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${lockStatusList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectLockStatusItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div> --%>
		        <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权生产模式：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${order_model_list}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectOrderModel"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权配送方式：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${carryModeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectCarryModeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>是否正常单：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${normalFlagList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectNormalFlagItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>是否预约单：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${bespokeFlagList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectBespokeFlagItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权社会机：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${societyFlagList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectSocietyFlagItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权产品类型：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${productSubTypeList}">
		                	<label><input type="checkbox" value="${obj.field_value}" name="_selectProductSubTypeItem"> ${obj.field_value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权支付类型：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${payModeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectPayModeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权处理类型：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${handleTypeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectHandleTypeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权合约类型：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${productBusiTypeList}">
		                	<label><input type="checkbox" value="${obj.value}" name="_selectProductBusiTypeItem"> ${obj.value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权归属渠道：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${developAttributeList}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectDevelopAttributeItem"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
				<div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权商品品牌：</h3>
		                <div class="roleLabel clearfix">
		                <c:forEach var="obj" items="${productBrandList}">
		                	<label><input type="checkbox" value="${obj.value}" name="_selectProductBrandItem"> ${obj.value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
		        
		        <div class="grid_n_cont" style="display:none;">
		            <div class="grid_n_cont_sub">
		                <h3>授权代理商：</h3>
		                <div class="subTit">已经授权的代理商列表</div>
		              <div class="roleCont">
		                	<table cellspacing="0" cellpadding="0" width="100%" border="0" class="tab_form">
		                      <tbody><tr>
		                        <th>代理商级别：</th>
		                        <td><input type="text" style="width:160px;" class="ipt_new" id="textfield" name="textfield"></td>
		                        <th>代理商编码：</th>
		                        <td><input type="text" style="width:160px;" class="ipt_new" id="textfield" name="textfield"></td>
		                        <th>代理商名称：</th>
		                        <td>
		                            <input type="text" style="width:160px;" class="ipt_new" id="textfield" name="textfield">
		                        </td>
		                      </tr>
		                    </tbody></table>
		                </div>
		              <div class="toolbar">
		                    <a href="#"><i class="icon_add"></i>新增代理商</a>
		                    <a href="#"><i class="icon_del"></i>移除代理</a>
		                </div>
		                <div>
		                	<table cellspacing="0" cellpadding="0" width="100%" border="0" class="grid_a">
		                      <tbody><tr>
		                        <th>操作</th>
		                        <th>代理商编码</th>
		                        <th>代理商名称</th>
		                        <th>代理商级别</th>
		                        <th>代理商电话</th>
		                        <th>代理商公司</th>
		                        <th>代理商地址</th>
		                      </tr>
		                      <tr>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                      </tr>
		                      <tr class="curr">
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                        <td>&nbsp;</td>
		                      </tr>
		                    </tbody></table>
		                </div>
		            </div>
		        </div>
		        
		        <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>异常单归类：</h3>
		                <div class="roleLabel clearfix">
		                <label><input type="checkbox" value="1" name="_selectOrderexpSpecCatalogItem">全部</label>
		                <c:forEach var="obj" items="${orderexp_sprc_catalog}">
		                	<label><input type="checkbox" value="${obj.catalog_id}" name="_selectOrderexpSpecCatalogItem"> ${obj.catalog_name}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
		        
		        <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>领取工号：</h3>
		                <div class="roleLabel clearfix">
		                <label><input type="checkbox" value="1" name="_selectOrderReceiveUser">全部</label>
		                <c:forEach var="obj" items="${receiveOrderUsers}">
		                	<label><input type="checkbox" value="${obj.value}" name="_selectOrderReceiveUser"> ${obj.value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
		        <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>锁定工号：</h3>
		                <div class="roleLabel clearfix">
		                <label><input type="checkbox" value="1" name="_selectOrderLockUser">全部</label>
		                <c:forEach var="obj" items="${lockOrderUsers}">
		                	<label><input type="checkbox" value="${obj.value}" name="_selectOrderLockUser"> ${obj.value_desc}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
		        <div class="grid_n_cont">
		            <div class="grid_n_cont_sub">
		                <h3>授权县份：</h3>
		                <div class="roleLabel clearfix">
		                <label><input type="checkbox" value="1" name="_selectOrderBusicty">全部</label>
		                <c:forEach var="obj" items="${orderBusicty}">
		                	<label><input type="checkbox" value="${obj.pkey}" name="_selectOrderBusicty"> ${obj.pname}</label>
		                </c:forEach>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>




			<div class="comBtns" style="display:none;">
				<a href="#" class="dobtn" style="margin-right:5px;">请发货</a>
				<a href="#" class="dobtn" style="margin-right:5px;">保存</a>
				<a href="#" class="dobtn" style="margin-right:5px;">挂起</a>
				<a href="#" class="dobtn" style="margin-right:5px;">委托</a>
				<a href="#" class="dobtn" style="margin-right:5px;">退单</a>
				<a href="#" class="dobtn" style="margin-right:5px;">驳回</a>
			</div>
			<div class="clear"></div>
		</div>

	</div>


</body>