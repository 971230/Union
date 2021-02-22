<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<!-- <link rel="stylesheet" href="css/reset.css" type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" /> -->
<script src="<%=request.getContextPath() %>/jx/order/js/jxorder.js"></script>
</head>

<body>
<div class="titlenew">
	<div class="tabBar">
    	<ul>
       	  	<li class="curr"><a href="#"><span>首页</span></a></li>
        	<li class="closeTrue"><a href="#"><span>指标发展指导模型</span></a><a href="#" class="tabClose">关闭</a></li>
        	<li class="closeTrue"><a href="#"><span>指标发展指导模型</span></a><a href="#" class="tabClose">关闭</a></li>
        </ul>
    </div>
</div>
<div class="searchWarp">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="searchTable">
      <tr>
        <td style="width:175px;">
        	<div class="searchTit">操作区（全部订单）</div>
            <ul class="btnList clearfix">
            	<li><a href="#" class="orderbtn">订单补录</a></li>
            	<li><a href="#" class="orderbtn">待客下单</a></li>
            	<li><a href="#" class="orderbtn">订单备注</a></li>
            	<li><a href="#" class="orderbtn">订单异常</a></li>
            </ul>
        </td>
        <td style="width:175px;">
        	<div class="searchTit">订单管理</div>
            <ul class="btnList clearfix">
            	<li><a href="#" class="orderbtn">异常单(<span>10</span>)</a></li>
            	<li><a href="#" class="orderbtn">失败单(<span>5</span>)</a></li>
            	<li><a href="#" class="orderbtn">待审核单(<span>2</span>)</a></li>
            	<li><a href="#" class="orderbtn">待开户单(<span>2</span>)</a></li>
            </ul>
        </td>
        <td style="width:175px;">
        	<div class="searchTit">发货管理</div>
            <ul class="btnList clearfix">
            	<li><a href="#" class="orderbtn">待备发货(<span>10</span>)</a></li>
            	<li><a href="#" class="orderbtn">待退货单(<span>5</span>)</a></li>
            	<li><a href="#" class="orderbtn">待发货单(<span>2</span>)</a></li>
            	<li><a href="#" class="orderbtn">待换货单(<span>2</span>)</a></li>
            </ul>
        </td>
        <td style="width:175px;">
        	<div class="searchTit">财务管理</div>
            <ul class="btnList clearfix">
            	<li><a href="#" class="orderbtn">待支付单(<span>10</span>)</a></li>
            	<li><a href="#" class="orderbtn">退费支付(<span>5</span>)</a></li>
            	<li><a href="#" class="orderbtn">退货支付(<span>2</span>)</a></li>
            </ul>
        </td>
        <td style="width:175px;">
        	<div class="searchTit">售后管理</div>
            <ul class="btnList clearfix">
            	<li><a href="#" class="orderbtn">退货申请(<span>10</span>)</a></li>
            	<li><a href="#" class="orderbtn">换货申请(<span>5</span>)</a></li>
            	<li><a href="#" class="orderbtn">退费申请(<span>2</span>)</a></li>
            </ul>
        </td>
        <td style="width:190px;">
        	<div class="searchTit">搜索<a href="#"  class="seniorBtn">高级搜索</a></div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="seniorTable">
              <tr>
                <td><input type="text" name="textfield" id="textfield" style="width:120px;" /></td>
                <td rowspan="2" style="width:60px; text-align:left;"><a href="#" class="searchBig">搜索</a></td>
              </tr>
              <tr>
                <td><input type="text" name="textfield" id="textfield" style="width:120px;" /></td>
              </tr>
            </table>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
    <div class="seniorPop" style="display:none;">
    	<div class="popTit"><h2>高级搜索</h2><a href="#" class="popClose">关闭</a></div>
        <div class="seniorPopCon">
        	<div>
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="seniorCond">
                  <tr>
                    <td>
                    	<div class="seniorSelTit">订单基础状态</div>
                        <div class="seniorSel">
                        	<select name="select" size="8" id="select">
                        	  <option value="1">任意活动</option>
                        	  <option value="2">活动订单</option>
                        	  <option value="3">已经完成</option>
                        	  <option value="4">暂停作废</option>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">订单基础状态</div>
                        <div class="seniorSel">
                        	<select name="select" size="8" id="select">
                        	  <option value="1">任意支付状态</option>
                        	  <option value="2">未支付</option>
                        	  <option value="3">已处理</option>
                        	  <option value="4">处理中</option>
                        	  <option value="5">部分付款</option>
                        	  <option value="6">未支付</option>
                        	  <option value="7">已处理</option>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">订单基础状态</div>
                        <div class="seniorSel">
                        	<select name="select" size="8" id="select">
                        	  <option value="1">任意状态</option>
                        	  <option value="2">未发货</option>
                        	  <option value="3">部分发货</option>
                        	  <option value="4">已发货</option>
                        	  <option value="4">部分乘退货</option>
                        	  <option value="4">已退货</option>
                        	  <option value="4">退货中</option>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">订单基础状态</div>
                        <div class="seniorSel">
                        	<select name="select" size="8" id="select">
                        	  <option value="1">任意配送方式</option>
                        	  <option value="2">快递-申通深圳</option>
                        	  <option value="3">快递-顺风上海</option>
                        	  <option value="4">上海同城快递</option>
                        	  <option value="4">平邮</option>
                        	  <option value="4">EMS</option>
                        	  <option value="4">圆通</option>
							</select>
                        </div>
                    </td>
                    <td>
                    	<div class="seniorSelTit">订单基础状态</div>
                        <div class="seniorSel">
                        	<select name="select" size="8" id="select">
                        	  <option value="1">商城</option>
							</select>
                        </div>
                    </td>
                  </tr>
                </table>
            </div>
            <div class="seniorForm">
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                  <tr>
                    <th>订单编号：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                    <th>收货人：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                    <th>联系电话：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                    <th>物流单号：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="14785652" /></td>
                  </tr>
                  <tr>
                    <th>开始时间：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                    <th>结束：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                    <th>商品名称：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                    <th>&nbsp;</th>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <th>入网号码：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未开户" /></td>
                    <th>终端串码：</th>
                    <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                    <th>&nbsp;</th>
                    <td>&nbsp;</td>
                    <th>&nbsp;</th>
                    <td>&nbsp;</td>
                  </tr>
                </table>
            </div>
            <div class="btns">
            	<a href="#" class="blueBigBtn">搜索</a>
            </div>
        </div>
    </div>
</div>
<div class="gridSper"></div>
<div class="gridWarp">
	<div>
    	<form class="grid" id="gridform">
		<div gridid="1400486995086" class="gridbody">
        <table width="100%">
			<thead>
            	<tr>
                    <th width="30px">选择</th>
                    <th width="250px">商品名称</th>
                    <th>商品类型</th>
                    <th>商品分类</th>
                    <th>商品品牌</th>
                    <th>销售价格(元)</th>
                    <th>市场价格(元)</th>
                    <th>发布状态</th>
                    <th>状态</th>
                    <th><a href="#">创建时间</a></th>
                    <th width="200px">操作</th>
				</tr>
            </thead>
			<tbody>
            	<tr>
					<td>
						<input type="radio" nowuserfounder="1" nowuserid="1" goodsauthor="" goods_id="2014032723867" value="2014032723867" name="id" onclick="cclick();">
				</td>
				<td>
					<span>
						<a href="goods!goodsEditEcs.do?goods_id=2014032723867&amp;selfProdType=T&amp;store=&amp;have_stock=1">3G-20元预付费套餐</a> </span>
				</td>
				<td>&nbsp;号卡 </td>
				<td>&nbsp;预付套餐3G号卡 </td>
				<td>&nbsp;中国联通 </td>
				<td>&nbsp;
					0.0
				</td>
				<td></td>
				<td>
				    <input type="hidden" value="1" name="publish_status">
					<div onclick="cclick();" class="relaDiv">
					    <a>
					      
					      已发布
					      
					      
					    </a>
					</div>				
				</td>
				<td>&nbsp;
					启用
				</td>
				<td>&nbsp;
				</td>
				<td>
				    <a goodsname="3G-20元预付费套餐" mkval="1" gid="2014032723867" name="market_enable_action" href="javascript:void(0)">
                        停用
      	            </a><span class="tdsper"></span>
					<a href="#">修改 </a><span class="tdsper"></span>
					<a href="#">查看</a>					       
				</td>
			</tr>
			</tbody>
		</table></div>
		<input type="hidden" value="false" name="join_suced">
	</form>
    </div>
	<div id="order_detail_div" class="gridInfoWarp" style="display:;">
    	<div class="expland"><a href="javascript:void(0);" id="order_detail_ev" class="foldR"></a></div>
        <div class="proInfoWarp">
        	<div class="tabBg">
                <div class="stat_graph">
                    <h3>
                        <div class="graph_tab">
                            <ul>
                                <li id="show_click_1" class="selected"><span class="word">基本信息</span><span class="bg"></span></li>
                                <li id="show_click_2" class=""><span class="word">订单明细</span><span class="bg"></span></li>
                                <li id="show_click_3" class=""><span class="word">收退款记录</span><span class="bg"></span></li>
                                <li id="show_click_4" class=""><span class="word">发退货记录</span><span class="bg"></span></li>
                                <li id="show_click_5" class=""><span class="word">优惠方案</span><span class="bg"></span></li>
                                <li id="show_click_6" class=""><span class="word">订单日志</span><span class="bg"></span></li>
                                <li id="show_click_7" class=""><span class="word">订单附言</span><span class="bg"></span></li>
                                <div class="clear"></div>
                            </ul>
                        </div>
                    </h3>
                </div>
            </div>
            <div class="btnWarp">
            	<a href="#" class="dobtn" style="margin-right:5px;">操作</a>
            	<a href="#" class="dobtn" style="margin-right:5px;">操作</a>
            	<a href="#" class="dobtn" style="margin-right:5px;">操作</a>
            	<a href="#" class="dobtn" style="margin-right:5px;">操作</a>
            	<a href="#" class="dobtn" style="margin-right:5px;">操作</a>
            </div>
            <div class="formWarp">
            	<div class="tit"><a href="#" class="closeArrow"></a>基本信息<div class="dobtnDiv"><a href="#" class="dograybtn">操作</a></div></div>
                <div class="formGridDiv">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                      <tr>
                        <th class="red">订单编号：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                        <th>订单类型：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>生成类型：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>订单来源：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                      <tr>
                        <th>支付状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                        <th class="red">发货状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>订单状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>购买方式：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                      </tr>
                      <tr>
                        <th>开户状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未开户" /></td>
                        <th class="red">异常状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                        <th>外订单编号：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                        <th>CRM订单编号：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                      </tr>
                      <tr>
                        <th>记录状态：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="暂停" /></td>
                        <th>支付方式：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="在线支付" /></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
            </div>
            <div class="formWarp">
            	<div class="tit"><a href="#" class="closeArrow"></a>商品价格
           	  <div class="dobtnDiv"><a href="#" class="dograybtn">操作</a></div></div>
                <div class="formGridDiv">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                      <tr>
                        <th>订购数量：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                        <th>商品总额：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>生成类型：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>优惠金额：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                      <tr>
                        <th>支付金额：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                        <th class="red">订单总额：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
            </div>
            <div class="formWarp">
            	<div class="tit"><a href="#" class="closeArrow"></a>收货人信息
           	  <div class="dobtnDiv"><a href="#" class="dograybtn">操作</a></div></div>
                <div class="formGridDiv" style="display:none;">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                      <tr>
                        <th>购买人ID：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                        <th>购买人：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>姓名：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>电话：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                      <tr>
                        <th>手机：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                        <th>邮编：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>E-mail：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>地区：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                      </tr>
                      <tr>
                        <th>地址：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未开户" /></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
            </div>
            <div class="formWarp">
            	<div class="tit"><a href="#" class="closeArrow"></a>配送信息
           	  <div class="dobtnDiv"><a href="#" class="dograybtn">操作</a></div></div>
                <div class="formGridDiv">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                      <tr>
                        <th class="red">配送方式：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                        <th class="red">是否保价：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>配送保价：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>商品重量：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                      <tr>
                        <th class="red">配送时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                        <th class="red">发票类型：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>发票抬头：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>单位名称：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                    </table>
                </div>
            </div>
            <div class="formWarp">
            	<div class="tit"><a href="#" class="openArrow"></a>推荐人信息<div class="dobtnDiv"><a href="#" class="dograybtn">操作</a></div></div>
                <div class="formGridDiv">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
                      <tr>
                        <th>推荐人姓名：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="2014898883242388" /></td>
                        <th>手机：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>编号：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                        <th>发展编号：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="订购单" /></td>
                      </tr>
                      <tr>
                        <th>发展人姓名：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未支付" /></td>
                        <th>获取时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>确认时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                        <th>付款时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未发货" /></td>
                      </tr>
                      <tr>
                        <th>开户时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="未开户" /></td>
                        <th>发货时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                        <th>归档时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                        <th>回访时间：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="不缺货" /></td>
                      </tr>
                      <tr>
                        <th>下单人：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="暂停" /></td>
                        <th>审核人：</th>
                        <td><input name="textfield" type="text" class="formIpt" id="textfield" value="在线支付" /></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>