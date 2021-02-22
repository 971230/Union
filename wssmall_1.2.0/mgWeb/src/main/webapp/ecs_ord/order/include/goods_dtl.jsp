<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.AttrPackageBusiRequest"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  String customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  boolean custFlag = EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType);
  String d_type = (String)request.getAttribute("d_type");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>
					<tbody>
					<tr><input type="button"  value="点击展开" onclick="OpenDiv()" class="graybtn1"></th></tr>
					</tbody>
					<div class="grid_n_cont_sub" id="wo_table" style="display:none">
                    <div class="grid_n_cont_sub" >
                    	<h3>群组信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>179集团ID：</th>
                                <td>${orderDtl.group_code }</td>
                                <th>群组名：</th>
                                <td>${orderDtl.group_name }</td>
                                <th>行业来源：</th>
                                <td>${orderDtl.industry_source }</td>
                            </tr>
                    	</table>
                    </div>
                   
                   <div class="grid_n_cont_sub">
                      	<h3>宽带信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                            <th>宽带账号：</th>
                            <td>${orderDtl.adsl_account }</td>
                            <th>宽带号码：</th>
                            <td>${orderDtl.adsl_number }</td>
                            <th>标准地址：</th>
                            <td>${orderDtl.adsl_addr }</td>
                            </tr>
                            <tr>
                            <th>速率：</th>
                            <td>${orderDtl.adsl_speed }</td>
                            <th>用户种类：</th>
                            <td>${orderDtl.adsl_usertype }</td>
                            <th>商企网络：</th>
                            <td>${orderDtl.adsl_grid }</td>
                            </tr>
                            <tr>
                            <th>聚类市场信息：</th>
                            <td>${orderDtl.cust_building_id }</td>
                            <th>宽带总费用：</th>
                         	<td>${orderDtl.adsl_total_fee }</td>
                         	<th></th>
                         	<td></td>
                            </tr>
                      	</table>
                  	</div>
                  	
                  	<div class="grid_n_cont_sub">
                      	<h3>固话信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                            <th>固话号码：</th>
                            <td>${orderDtl.gw_service_num }</td>
                            <th>是否主号码：</th>
                            <td>${orderDtl.is_main }</td>
                            <th>固话总费用：</th>
                         	<td>${orderDtl.gw_total_fee }</td>
                            </tr>
                      	</table>
                  	</div>
                  	
                  	<div class="grid_n_cont_sub">
                      	<h3>一信通信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                            <th>服务号码：</th>
                            <td>${orderDtl.yxt_service_num }</td>
                            <th>SIM卡号：</th>
                            <td>${orderDtl.simid }</td>
                            <th>是否白卡：</th>
                            <td>${orderDtl.sim_type }</td>
                            </tr>
                      	</table>
                  	</div>
                  	</div>
                    