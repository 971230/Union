<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
</head>
<body>
<form action="" id="order_detail_fm">
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
        	
        	<jsp:include page="auto_flows.jsp?order_id=${order_id }"/>
        	
        	
			<div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>订单基本信息<a href="#" class="editBtn" style="display: none;">编辑</a></h2>
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="images/ic_remind.png" width="16" height="16"></div>
                    <div class="grid_n_cont_sub">
                        <h3>订单基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          	<tbody><tr>
                                <th><span>*</span>订单编号：</th>
                                <td><html:orderattr  order_id ="${order_id }" field_name="order_id"  field_desc ="发展人编码" field_type="text"></html:orderattr> </td>
                                <th><span>*</span>订单来源：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th><span>*</span>订单类型：</th>
                                <td>
                                <select name="orderTree.orderBusiRequest.order_id" id="select" style="width:200px;">
                                  <option value="1"></option>
                                </select>
                            	</td>
                          	</tr>
                          	<tr>
                                <th><span>*</span>订单类别：</th>
                                <td>
                                	<html:orderattr attr_code="DIC_SUB_ORDER_TYPE" order_id ="${order_id }" field_name="tid_category"  field_desc ="订单类别" field_type="select"></html:orderattr>
                                </td>
                                <th><span>*</span>归属地市：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th><span>*</span>渠道标记：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                          	</tr>
                          	<tr>
                                <th>发展人编码：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>发展人名称：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>推荐人手机：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                          	<tr>
                                <th>推荐人名称：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>下单时间：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                          	</tr>
                          	<tr>
                                <th><span>*</span>订单总价（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>优惠金额（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>实收金额（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                      	<h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>支付时间：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>支付类型：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                            	</td>
                              	<th><span>*</span>支付方式：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                            	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>支付状态：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                            	</td>
                              	<th>支付流水号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>支付机构编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>支付机构名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>支付渠道编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>支付渠道名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                      	<h3>物流信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>应收运费（元）：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>实收运费（元）：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>物流公司编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                            <tr>
                              	<th>物流公司名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>是否闪电送：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                            	</td>
                              	<th><span>*</span>配送方式：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                            	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>配送时间：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>收货人姓名：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>收货省份：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>收货地市：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                              	<th><span>*</span>收货区县：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                              	<th>收货商圈：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                            <tr>
                              	<th><span>*</span>详细地址：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>邮政编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>固定电话：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>手机号码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>电子邮件：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</tbody></table>
                  	</div>
                  	<div class="grid_n_cont_sub">
                      	<h3>买家信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>客户电话：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>客户手机：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>客户名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                            <tr>
                              	<th>客户联系地址：</th>
                              	<td colspan="5"><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>买家昵称：</th>
                              	<td colspan="5"><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>买家留言：</th>
                              	<td colspan="5"><textarea name="textarea" id="textarea" cols="45" rows="5"></textarea></td>
                            </tr>
                            <tr>
                              	<th>卖家留言：</th>
                              	<td colspan="5"><textarea name="textarea" id="textarea" cols="45" rows="5"></textarea></td>
                            </tr>
                            <tr>
                              	<th>订单备注：</th>
                              	<td colspan="5"><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                    	</tbody></table>
                  	</div>
                  	<div class="grid_n_cont_sub">
                    	<h3>集团客户信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>集团编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>集团名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                            <tr>
                              	<th>行业应用类别：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>应用子类别：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                        </tbody></table>
               	  	</div>
                    <div class="grid_n_cont_sub">
                    	<h3>沃百富信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>百度账号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>百度冻结流水号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>百度冻结款：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>百度冻结开始时间：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>百度冻结结束时间：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                    	<h3>资料回收方式：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>回收方式：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>回收内容：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>是否已上传：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                         	 </tr>
                      	</tbody></table>
                  	</div>
                  	<div class="grid_n_cont_sub">
                    	<h3>发票信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>发票类型：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                              	<th>发票打印方式：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                              	<th>发票抬头：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                            <tr>
                              	<th>发票内容：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>发票明细：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</tbody></table>
                  	</div>
                  	
                    
                	
                </div>
            </div>
            <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>商品信息<a href="#" class="editBtn" style="display: none;">编辑</a></h2>
              	<div class="grid_n_cont">
       		  	  	<div class="remind_div"><img src="images/ic_remind.png" width="16" height="16"></div>
                    <div class="grid_n_cont_sub">
                        <h3>基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                                <th><span>*</span>商品编号：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>商品名称：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>商品类型：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th><span>*</span>产品品牌：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th><span>*</span>是否总部合约：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>商品价格（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th><span>*</span>优惠价格（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>实收价格（元）：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>商品数量：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th><span>*</span>仓库名称：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th>多交预存款：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>减免预存标记：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th>上网时长：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>商品备注：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                        <h3>商品参数：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                                <th><span>*</span>分月返还：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>手机款：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>首月返还：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th><span>*</span>预存款：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>月送费金额：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>协议期总送费金额：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th><span>*</span>是否全国卡：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                        <h3>受理单信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                                <th><span>*</span>受理单模板编码：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>受理单打印模式：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th><span>*</span>受理单打印内容：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                        </tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                        <h3>用户证件信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                                <th>证件类型：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th>证件号码：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>证件地址：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                                <th>客户名称：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                <th>客户类型：</th>
                                <td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                                </td>
                                <th>证件有效期：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                        </tbody></table>
                    </div>
                   	<div class="subcomBtns"><a href="#" class="dobtn" style="margin-right:10px;">保存</a><a href="#" class="dobtn">取消</a></div>
              	</div>
           	</div>
            <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<a href="#" class="editBtn" style="display: none;">编辑</a></h2>
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="images/ic_remind.png" width="16" height="16"></div>
                  	<div class="grid_n_cont_sub">
                    	<h3>货品基本信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>sku：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>货品名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                               	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>货品小类：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                              	<th><span>*</span>货品说明：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>是否虚拟货品：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                    </select>
                              	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>是否赠品：</th>
                              	<td>
                                    <select name="select" id="select" style="width:200px;">
                                      <option value="1"></option>
                                	</select>
                              	</td>
                              	<th><span>*</span>货品数量：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</tbody></table>
                    </div>
                    <div class="grid_n_cont_sub">
                      	<h3>终端信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>颜色：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>容量：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>型号&nbsp;：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                              			<option value="1"></option>
                            		</select>
                            	</td>
                          	</tr>
                            <tr>
                              	<th><span>*</span>机型编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>品牌：</th>
                              	<td>
                                  	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                            	</td>
                              	<th>是否定制机：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                              			<option value="1"></option>
                            		</select>
                                </td>
                            </tr>
                            <tr>
                              	<th><span>*</span>配件：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</tbody></table>
                  	</div>
                    <div class="grid_n_cont_sub">
                      	<h3>套餐信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>ESS套餐编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>套餐档次：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>付费类型：</th>
                              	<td>
                                  	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                	</select>
                               	</td>
                          	</tr>
                            <tr>
                              	<th>套餐名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>首月资费方式：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                              			<option value="1"></option>
                            		</select>
                                </td>
                              	<th>微信沃包生效方式：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>套餐是否变更：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>是否iPhone套餐：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>4G套餐类型：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                              			<option value="1"></option>
                            		</select>
                                </td>
                            </tr>
                      	</tbody></table>
               	  	</div>
                  	<div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th><span>*</span>合约编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>合约名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th><span>*</span>合约类型：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                              			<option value="1"></option>
                            		</select>
                                </td>
                          	</tr>
                            <tr>
                              	<th><span>*</span>合约期限：</th>
                              	<td colspan="5"><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                    	</tbody></table>
                  	</div>
                  	<div class="grid_n_cont_sub">
                    	<h3>号码开户信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>用户号码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>入网地区：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>副卡号码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                          	</tr>
                            <tr>
                              	<th>亲情号码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>是否情侣号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>情侣号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>号码网别：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>是否靓号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>靓号预存：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>靓号低消：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>性别：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                               	</td>
                              	<th>出生日期：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>通讯地址：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>账户付费方式：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                               	</td>
                              	<th>上级银行编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>银行编码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>银行名称：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>银行账号：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>银行账户名：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>卡类型：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   </select>
                               	</td>
                              	<th>成卡/白卡：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                            </tr>
                            <tr>
                              	<th>信用等级：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>信用度调整：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>担保人：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>担保信息参数：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>被担保人证件类型：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>被担保人证件号码：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>账单寄送方式：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>账单寄送内容：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>账单收件人：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                            </tr>
                            <tr>
                              	<th>账单寄送地址：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>账单寄送邮编：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</tbody></table>
               	  	</div>
                    <div class="grid_n_cont_sub">
                    	<h3>上网卡硬件：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>颜色：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>品牌：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>型号：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                          	</tr>
                            <tr>
                              	<th>机型：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</tbody></table>
                  	</div>
                    <div class="grid_n_cont_sub">
                    	<h3>配件：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tbody><tr>
                              	<th>颜色：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  	<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>品牌：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                              	<th>型号：</th>
                              	<td>
                                	<select name="select" id="select" style="width:200px;">
                                  		<option value="1"></option>
                                   	</select>
                                </td>
                          	</tr>
                            <tr>
                              	<th>机型：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                          	</tr>
                      	</tbody></table>
                  	</div>
                  	<div class="grid_n_cont_sub">
                    	<h3>礼品（多个记录）：</h3>
                    	<div class="netWarp">
                        	<a href="#" class="icon_close">展开</a>
                        	<div class="goodTit">礼品1<a href="#" class="icon_del"></a></div>
                        	<div class="goodCon">
                            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              		<tbody><tr>
                                		<th>赠品类型：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                   			</select>
                                        </td>
                               			<th>赠品编码：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                		<th>赠品名称：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              		</tr>
                              		<tr>
                                		<th>赠品面值：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                		<th>赠品面值单位：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                   			</select>
                                  		</td>
                                		<th>赠品数量：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              		</tr>
                              		<tr>
                                		<th>赠品描述：</th>
                                		<td colspan="5"><textarea name="textarea" id="textarea" cols="30" rows="5"></textarea></td>
                              		</tr>
                              		<tr>
                                		<th>赠品品牌：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                			</select>
                                        </td>
                                		<th>赠品型号：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                			</select>
                                        </td>
                                		<th>赠品颜色：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                              		</tr>
                              		<tr>
                                		<th>赠品机型：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                			</select>
                                        </td>
                                		<th>是否需要加工：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                 				<option value="1"></option>
                                			</select>
                                        </td>
                                		<th>加工类型：</th>
                                		<td>
                                        	<select name="select" id="select" style="width:200px;">
                                  				<option value="1"></option>
                                   			</select>
                                        </td>
                              		</tr>
                              		<tr>
                                		<th>加工内容：</th>
                                		<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"></td>
                                		<th>&nbsp;</th>
                                		<td>&nbsp;</td>
                                		<th>&nbsp;</th>
                                		<td>&nbsp;</td>
                              		</tr>
                            	</tbody></table>
                      		</div>
                  		</div>
              		</div>
            </div>
        </div>
    		
    	<jsp:include page="order_handler.jsp?order_id=${order_id }"/>
    		
    </div>
   </div>
</div>
</form>
<script type="text/javascript">
	$(function(){
		OrdBtns.showBtns("${order_id}");
	});
</script>
</body>
</html>