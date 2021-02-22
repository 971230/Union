<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderFileBusiRequest"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ztesoft.soc.fastdfs.IDfsManager"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情信息</title>

<link href="<%=request.getContextPath() %>/public/common/control/css/lrtk.css" rel="stylesheet" type="text/css" />
<link href="/mgWebthemes/default/css/style_n.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath() %>/ecs_ord/order/detail/js/order_detail.js"></script>

<%
  	String order_id = (String)request.getAttribute("order_id");
	String isWirteCard = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
	String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
	OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
	String order_model = orderExt.getOrder_model();
	Boolean isShowWriteCardContents = true;//控制是否展示写卡信息以及脚本加载
	Boolean isShowOpenAcct = true;//控制是否显示开户按钮
	Boolean isShowWriteCardBtn = true;//控制是否显示写卡按钮
	Boolean isShowRefreshBtn = true;//控制是否显示写卡按钮
	Boolean isShowGetICCID = false;//控制是否显示获取卡信息按钮
	Boolean isGetCardInfo = false;//控制是否获取卡信息
	if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExt.getAbnormal_type())) { //自动化异常订单不允许写卡 
		isShowWriteCardBtn =  false;
	}
	if (!EcsOrderConsts.OPER_MODE_PCSG.equals(order_model) && !EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())) { //非写卡环节不允许写卡 
		isShowRefreshBtn =  false;
	}
	if (!EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())) { //非写卡环节不允许写卡 
		isShowWriteCardBtn =  false;
	}
	
	
 	if(orderExt.getOrder_from().equals("10070")
 			||orderExt.getOrder_from().equals("10012")
 			||orderExt.getOrder_from().equals("10092")
 			||orderExt.getOrder_from().equals("10100")||orderExt.getOrder_from().equals("10097")
 			|| orderExt.getOrder_from().equals("100105")){//订单来源为浙江码上购
		isGetCardInfo = true;
 	}
	
	
	if (!EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())) { //非开户环节不允许开户
		isShowOpenAcct =  false;
	}
	if (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB)) { //总商通道不显示写卡
		isShowWriteCardContents =  false;
	}
	if (!StringUtils.equals(isWirteCard, EcsOrderConsts.IS_DEFAULT_VALUE)){//不需要写卡的不显示写卡
		isShowWriteCardContents =  false;
	}
	if(StringUtils.isNotBlank(goods_type) && EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type)){//如果是单宽带订单不显示写卡
		isShowWriteCardContents =  false;
	}
	if (EcsOrderConsts.OPER_MODE_XK.equals(order_model)) { //集中写卡模式不显示写卡信息
		isShowWriteCardContents =  false;
	}
%>

<style>
.clickClass{
   background:#f7f7f7
}

.table_td_width{
	width: 200px;
}

.table_select{
	width: 162px;
	height: 24px;
}

.grid_form .grid_form_td{
	color:#999;
	padding-top:3px;
	padding-bottom:3px;
	width: 200px;
}

.grid_form_input{
	width:200px;
	background: none;
	border: 0px;
}

.grid_form_select{
	width:200px;
	background: none;
}

.grid_form_input_remark{
	width:1200px;
	background: none;
}

</style>

</head>

<body style="min-width: 150px;background:white;">
	
	<div class="grid_n_div" style="margin-top: 60px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_order_base"></a>订单基本信息</h2>
		
		<div id="div_order_base" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>内部单号：</th>
							<td class="grid_form_td"><input id="order_id" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>外部单号：</th>
							<td class="grid_form_td"><input id="out_tid" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>业务系统单号：</th>
                      		<td class="grid_form_td"><input id="zb_inf_id" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
                      		<th>下单时间：</th>
	                        <td	class="grid_form_td"><input id="tid_time" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>地市：</th>
	                        <td	class="grid_form_td">
								<select id="order_city_code" class="ipt_new grid_form_select"  disabled="disabled">
	                             	
	                            </select>
	                        </td>
	                         
	                        <th>县分：</th>
	                        <td	class="grid_form_td">
	                        	<select id="district_code" class="ipt_new grid_form_select" not_null="true" disabled="disabled">
	                             	
	                            </select>
	                        </td>
	                         
	                        <th>订单来源：</th>
	                        <td	class="grid_form_td">
								<select id="order_from" class="ipt_new grid_form_select"  disabled="disabled">
	                             	
	                            </select>
	                        </td>
	                         
	                        <th>业务号码：</th>
	                        <td class="grid_form_td">
	                        	<input id="phone_num" class="ipt_new grid_form_input"  disabled="disabled"/>
	                        </td>
	                   	</tr>
	                   	
	                   	<tr>
	                        <th>主卡号码：</th>
	                        <td class="grid_form_td"><input id="mainNumber" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                        
	                        <th>宽带账号：</th>
	                        <td class="grid_form_td"><input id="kd_account" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                        
	                        <th>订单状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="order_state" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value=""></option>
	                             	<option value="00">正常</option>
	                             	<option value="01">退单</option>
	                             	<option value="02">外呼</option>
	                             	<option value="03">异常</option>
	                            </select>
	                        </td>
	                        
	                        <th>证件照上传状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="if_Send_Photos" class="ipt_new grid_form_select"  disabled="disabled">
	                        		
	                            </select>
	                        </td>
	                   	</tr>
	                   	
	                   	<tr>
	                        <th>激活状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="active_flag" class="ipt_new grid_form_select"  disabled="disabled">
	                        		
	                            </select>
	                        </td>
	                        
	                        <th>退单状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="refund_status" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value=""></option>
	                        		<option value="00">正常单</option>
	                        		<option value="01">退单确认</option>
	                        		<option value="02">退单确认</option>
	                        		<option value="03">退单确认</option>
	                        		<option value="04">已退单</option>
	                        		<option value="05">已退单</option>
	                        		<option value="06">已退单</option>
	                        		<option value="07">退单确认</option>
	                        		<option value="08">退单申请</option>
	                            </select>
	                        </td>
	                        
	                        <th>订单签收状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="sign_status" class="ipt_new grid_form_select"  disabled="disabled">
	                        		
	                            </select>
	                        </td>
	                        
	                        <th>稽核状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="audit_type" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value="">暂无稽核信息</option>
	                        		<option value="0">稽核退单</option>
	                        		<option value="1">稽核退款</option>
	                        		<option value="2">稽核补单</option>
	                        		<option value="3">稽核补款</option>
	                        		<option value="4">稽核退现金</option>
	                            </select>
	                        </td>
	                   	</tr>
	                   	
	                   	<tr>
	                        <th>对应王卡状态：</th>
	                        <td class="grid_form_td">
	                        	<select id="kingcard_status" class="ipt_new grid_form_select"  disabled="disabled">

	                            </select>
	                        </td>
	                   	</tr>
	                   	
	                   	<tr>
	                        <th>订单备注：</th>
	                        <td colspan="7" class="grid_form_td">
	                        	<textarea rows="5" cols="130" id="service_remarks" disabled="disabled"></textarea>
	                        </td>
	                   	</tr>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_contact_info"></a>联系人信息</h2>
		
		<div id="div_contact_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>联系人姓名：</th>
							<td class="grid_form_td"><input id="ship_name" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	                         
							<th>联系电话：</th>
							<td class="grid_form_td"><input id="reference_phone" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	
                      		<th>联系电话2：</th>
	                        <td	class="grid_form_td"><input id="receiver_mobile" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	                        
	                        <th></th>
                      		<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
	                   		<th>联系地址：</th>
                      		<td class="grid_form_td" colspan="7">
                      			<textarea style="height:20px" rows="5" cols="130" id="show_ship_addr_too" not_null="true" disabled="disabled"></textarea>
                      		</td>
	                   	</tr>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_customer_info"></a>客户信息</h2>
		
		<div id="div_customer_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>是否实名：</th>
							<td class="grid_form_td">
								<select id="is_realname" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value=""></option>
	                        		<option value="0">未实名</option>
	                        		<option value="1">已实名</option>
	                        	</select>
							</td>
	                         
							<th>客户姓名：</th>
							<td class="grid_form_td"><input id="phone_owner_name" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	                         
							<th>证件类型：</th>
                      		<td class="grid_form_td">
                      			<select id="certi_type" class="ipt_new grid_form_select" not_null="true" disabled="disabled">
	                        		<option value=""></option>
	                        		<option value="SFZ18" selected="">身份证</option>
	                        	</select>
                      		</td>
                      		
                      		<th>证件号码：</th>
	                        <td	class="grid_form_td"><input id="cert_card_num" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
	                   		<th>生日：</th>
	                        <td	class="grid_form_td"><input id="birthday" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                        
	                        <th>性别：</th>
                      		<td class="grid_form_td">
                      			<select id="certi_sex" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value="M">男</option>
	                        		<option value="F">女</option>
	                        	</select>
                      		</td>
	                        
							<th>民族：</th>
							<td class="grid_form_td"><input id="cert_card_nation" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>失效时间：</th>
							<td class="grid_form_td"><input id="cert_failure_time" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
	                   		<th>生效时间：</th>
                      		<td class="grid_form_td"><input id="cert_eff_time" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>签发机关：</th>
							<td class="grid_form_td"><input id="cert_issuer" class="ipt_new grid_form_input"  disabled="disabled"/></td>

							<th>客户类型：</th>
							<td class="grid_form_td">
								<select id="CustomerType" class="ipt_new grid_form_select"  disabled="disabled">
	                        		
	                        	</select>
							</td>
                      		
                      		<th>客户编号：</th>
							<td class="grid_form_td"><input id="cust_id" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>客户电话：</th>
	                        <td	class="grid_form_td"><input id="phone_num" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>收入归集集团：</th>
                      		<td class="grid_form_td"><input id="group_code" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
                      		<th>出入境号：</th>
	                        <td	class="grid_form_td"><input id="cert_num2" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                        
	                        <th></th>
	                        <td	class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
	                   		<th>证件审核状态：</th>
                      		<td class="grid_form_td">
                      			<select id="cert_pic_flag" class="ipt_new grid_form_select"  disabled="disabled">
	                        		<option value=""></option>
	                        		<option value="0">校验失败</option>
	                        		<option value="1">校验成功</option>
	                        		<option value="2">证件照图片校验失败</option>
	                        		<option value="3">证件照图片校验成功</option>
	                        	</select>
                      		</td>
                      		
							<th>短信最近发送时间：</th>
							<td class="grid_form_td"><input id="messages_send_lasttime" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>短信发送次数：</th>
							<td class="grid_form_td"><input id="messages_send_count" class="ipt_new grid_form_input"  disabled="disabled"/></td>

							<th></th>
                      		<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>证件地址：</th>
		                   		<c:if test="${cat_id=='90000000000000901'}">
									<td class="grid_form_td" colspan="7">
										<textarea style="height:20px" rows="5" cols="130" id="show_ship_addr_too" not_null="false" disabled="disabled"></textarea>
	                               </td>	
	                            </c:if>
	                   			<c:if test="${cat_id!='90000000000000901'}">
									<td class="grid_form_td" colspan="7">
										<textarea  style="height:20px" rows="5" cols="130" id="cert_address" not_null="true" disabled="disabled"></textarea>
									</td>
								</c:if>
	                   	</tr>
                    </tbody>
				</table>
			</div>
			
			<div class="grid_n_cont_sub"  id="certPhotoDiv">
				<h3>用户证件照片：</h3>
				<%
					List<String> img_list = new ArrayList<String>();
					List<OrderFileBusiRequest> orderFileBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderFileBusiRequests();
					if (orderFileBusiRequests != null && orderFileBusiRequests.size() > 0) {
						for (OrderFileBusiRequest obj : orderFileBusiRequests) {
							String status = obj.getStatus();
							if("1".equals(status)){
								String imgPath = request.getContextPath() + "/servlet/OrderShowPhotoServlet?file_path=" + obj.getFile_path();
								img_list.add(imgPath);
							}
						}
					}
					request.setAttribute("img_list", img_list);
				%>
				<div class="picListDiv" id="show_id_cart">
					<ul class="imgList">
						<c:forEach items="${img_list}" var="img">
							<td>
								<a class="artZoom" href="${img}" rel="${img}">
									<img src="${img}" style="width: 180px;height: 180px" attr="${img}"/>
								</a>
							</td>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_goods_info"></a>商品信息</h2>
		
		<div id="div_goods_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>商品大类：</th>
                      		<td class="grid_form_td">
                      			<select id="goods_type" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
                      		</td>
                      		
							<th>商品小类：</th>
                      		<td class="grid_form_td">
                      			<select id="goods_cat_id" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
                      		</td>
                      		
                      		<th>商品：</th>
							<td class="grid_form_td">
								<select id="GoodsID" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
							</td>
                      		
                      		<th>商品价格:</th>
							<td class="grid_form_td"><input id="sell_price" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	<c:if test="${cat_id=='90000000000000901'}">
							<tr>
                              <th>终端名称：</th>
                               <td>
                               	   ${terminal_name}
                               </td>
                            </tr>
						</c:if>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_developer_info"></a>发展人信息</h2>
		
		<div id="div_developer_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>发展人编码：</th>
							<td class="grid_form_td"><input id="develop_code_new" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>发展人名称：</th>
							<td class="grid_form_td"><input id="develop_name_new" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>发展点编码：</th>
                      		<td class="grid_form_td"><input id="develop_point_code_new" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>发展点名称：</th>
                      		<td class="grid_form_td"><input id="development_point_name" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>下单人编码：</th>
							<td class="grid_form_td"><input id="out_operator" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>下单人名称：</th>
							<td class="grid_form_td"><input id="out_operator_name" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>下单点编码：</th>
                      		<td class="grid_form_td"><input id="out_office" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>下单点名称：</th>
                      		<td class="grid_form_td"><input id="out_office_name" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>受理人编码：</th>
							<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>受理人名称：</th>
							<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>cbss发展点编码：</th>
                      		<td class="grid_form_td"><input id="cbss_development_point_code" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>cbss发展人编码：</th>
                      		<td class="grid_form_td"><input id="cbss_develop_code" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_pay_info"></a>支付信息</h2>
		
		<div id="div_pay_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>订单总价：</th>
							<td class="grid_form_td"><input id="order_origfee" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>支付时间：</th>
							<td class="grid_form_td"><input id="pay_time" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>实收金额：</th>
                      		<td class="grid_form_td"><input id="order_realfee" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>支付状态：</th>
                      		<td class="grid_form_td">
                      			<select id="pay_status" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
                      		</td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>支付类型：</th>
							<td class="grid_form_td">
								<select id="paytype" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
							</td>
	                         
							<th>支付方式：</th>
							<td class="grid_form_td">
								<select id="pay_method" class="ipt_new grid_form_select"  disabled="disabled">
								
	                        	</select>
							</td>
	                         
							<th>支付发起流水：</th>
                      		<td class="grid_form_td"><input id="pay_sequ" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>支付返回流水：</th>
                      		<td class="grid_form_td"><input id="pay_back_sequ" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div class="grid_n_div" style="margin-top: 30px;">
		<h2><a href="javascript:void(0);" class="closeArrow expand_btn" target_div="div_delivery_info"></a>物流信息</h2>
		
		<div id="div_delivery_info" class="grid_n_cont">
			<div class="grid_n_cont_sub">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  	<tbody>
	                   	<tr>
							<th>物流单号：</th>
							<td class="grid_form_td"><input id="logi_no" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>应收运费（元）：</th>
							<td class="grid_form_td"><input id="post_fee" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>实收运费（元）：</th>
                      		<td class="grid_form_td"><input id="n_shipping_amount" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th>物流公司名称：</th>
                      		<td class="grid_form_td"><input id="shipping_company" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>手机号码：</th>
							<td class="grid_form_td"><input id="show_receiver_mobile_too" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
	                         
							<th>配送时间：</th>
							<td class="grid_form_td"><input id="shipping_time" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                         
							<th>收货人姓名：</th>
                      		<td class="grid_form_td"><input id="contact_name" class="ipt_new grid_form_input" not_null="true" disabled="disabled"/></td>
                      		
							<th>收货省份：</th>
                      		<td class="grid_form_td">
                      		<!-- 新增全部省份可选择 -->
                      		 <select field_desc="收货省份" id="province_id" class="ipt_new grid_form_input" not_null="true">
                              	     
                              </select>
                      		</td>
	                   	</tr>
	                   	
	                   	<tr>
							<th>收货地市：</th>
							<td class="grid_form_td">
								<select id="city_code" class="ipt_new grid_form_select" not_null="true" >
	                             	
	                            </select>
							</td>
	                         
							<th>收货区县：</th>
							<td class="grid_form_td">
								<select id="area_code" class="ipt_new grid_form_select" not_null="true" >
	                             	
	                            </select>
							</td>
	                         
							<th></th>
                      		<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
                      		
							<th></th>
                      		<td class="grid_form_td"><input id="" class="ipt_new grid_form_input"  disabled="disabled"/></td>
	                   	</tr>
	                   	
	                   	<tr>
	                   		<th>详细地址：</th>
                      		<td class="grid_form_td" colspan="7">
                      			<textarea rows="5" cols="130" id="ship_addr" not_null="true" disabled="disabled"></textarea>
                      		</td>
	                   	</tr>
                    </tbody>
				</table>
			</div>
		</div>
	</div>
	
	<jsp:include page="/ecs_ord/order/order_handler.jsp?order_id=${order_id }&hide_div=handle_input_area"/>
</body>

