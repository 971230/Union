<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单处理</title>
</head>
<body>
<div class="gridWarp">
	<div class="treeBox">
    	<div class="tree_l" style="overflow:auto;">
        	<ul>
            	<li class="open"><a href="#"><i class="treeic1"></i>订单管理</a>
                	<ul>
                    	<li><a href="#"><i class="treeic7"></i>订单预处理</a>
                        <li><a href="#"><i class="treeic7"></i>客户回访</a></li>
                        <li><a href="#"><i class="treeic7"></i>预拣货【新】</a></li>
                        <li><a href="#"><i class="treeic7"></i>ESS一键开户</a></li>
                        <li><a href="#"><i class="treeic7"></i>手动开户</a></li>
                        <li><a href="#"><i class="treeic7"></i>写卡</a></li>
                        <li><a href="#"><i class="treeic7"></i>BSS业务办理</a></li>
                        <li><a href="#"><i class="treeic7"></i>质检稽核</a></li>
                        <li><a href="#"><i class="treeic7"></i>发货</a></li>
                        <li><a href="#"><i class="treeic7"></i>回单</a></li>
                        <li><a href="#"><i class="treeic7"></i>预拣货</a></li>
                        <li class="open"><a href="#"><i class="treeic1"></i>预拣货</a>
                        	<ul>
                            	<li><a href="#"><i class="treeic3"></i>内部订单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>外部正常订单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>外部异常订单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>外部差价单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>异动订单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>待业务办理</a></li>
                            	<li><a href="#"><i class="treeic3"></i>订单查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>同步状态查询</a></li>
                            	<li><a href="#"><i class="treeic3"></i>接口异常查询</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="treeic7"></i>订单解挂/锁</a></li>
                    </ul>
                </li>
            	<li class="close"><a href="#"><i class="treeic1"></i>BSS业务受理</a>
                	<ul>
                    	<li class="open"><a href="#"><i class="treeic1"></i>订单拣货环节</a>
                        	<ul>
                            	<li class="open"><a href="#"><i class="treeic2"></i>集中生产</a>
                                	<ul>
                                    	<li><a href="#"><i class="treeic3"></i>拣货通知WSM</a></li>
                                    	<li><a href="#"><i class="treeic3"></i>获取WSM货品</a></li>
                                    	<li><a href="#"><i class="treeic3"></i>WSM货品通知总部</a></li>
                                    	<li><a href="#"><i class="treeic3"></i>跳转下一个环节</a></li>
                                    </ul>
                                </li>
                            	<li class="open"><a href="#"><i class="treeic4"></i>自动化模式</a>
                                	<ul>
                                    	<li><a href="#"><i class="treeic3"></i>接收总部捡货通知</a></li>
                                    	<li><a href="#"><i class="treeic3"></i>跳转下一个环节</a></li>
                                    </ul>
                                </li>
                            	<li class="open"><a href="#"><i class="treeic5"></i>流程启动</a>
                                	<ul>
                                    	<li><a href="#"><i class="treeic3"></i>广东流程启动</a></li>
                                    	<li><a href="#"><i class="treeic3"></i>深圳流程启动</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
	<div class="treeRightCon">
   		<div class="tabbar">
        	<ul>
            	<li><a href="#"><span>待处理订单</span></a></li>
            	<li class="curr"><a href="#"><span>我的订单</span></a></li>
            	<li><a href="#"><span>更改开户信息</span></a></li>
            	<li><a href="#"><span>更改订单信息</span></a></li>
            	<li><a href="#"><span>异常订单</span></a></li>
            </ul>
        </div>
        <div class="searchBx">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
              <tr>
                <th>订单来源：</th>
                <td>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="textfield" id="textfield" value="请选择" class="ipt" />
                    	<a href="#" class="selArr"></a>
                        <div class="selOp" style="display:none;">
                        	<div class="allSel">
                            	<label><input type="checkbox" name="checkbox" id="checkbox" />全选</label>
                                <label><a href="#" class="aCancel">取消</a></label>
                                <label><a href="#" class="aClear"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                                	<li class="curr"><input type="checkbox" name="checkbox" id="checkbox" />沃右富</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />佛山自营商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />佛山本地沃云购</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />广州天猫</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />惠州本地沃云购</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />网盟集团客户</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />理财通</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />联通商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />网盟店铺</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />集客农行</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />南网沃店</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />拍拍商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />深圳拍拍</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />深圳自营商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />淘宝商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />淘宝分销</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />电话营销</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />电话商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />U惠站</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />VIP商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />沃商城WAP</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />外部导入</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />沃财富</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />沃货架</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />网盟其他来源</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />网盟统一接口</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />沃商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />微商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />异业联盟</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />总部商城</li>
                                	<li><input type="checkbox" name="checkbox" id="checkbox" />线上中小网盟</li>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                <th>外部单号：</th>
                <td>
                	<input type="text" name="textfield" id="textfield" class="ipt_new" style="width:100px;" />
                </td>
                <th>买家昵称：</th>
                <td>
                    <input type="text" name="textfield" id="textfield" class="ipt_new" style="width:100px;" />
                    <a href="#" class="dobtn" style="margin-left:5px;">查询</a>
                </td>
              </tr>
              <tr>
                <th>内部单号：</th>
                <td>
                	<input type="text" name="textfield" id="textfield" class="ipt_new" style="width:100px;" />
                </td>
                <th>创建时间：</th>
                <td>
                    <input type="text" name="textfield" id="textfield" class="ipt_new" style="width:100px;" />-
                    <input type="text" name="textfield" id="textfield" class="ipt_new" style="width:100px;" />
                </td>
                <th>&nbsp;</th>
                <td>&nbsp;</td>
              </tr>
            </table>
        </div>
        <div class="right_warp">
        	<div class="right_l">
            	<div class="tabBg">
                    <div class="stat_graph">
                        <h3>
                            <div class="graph_tab">
                                <ul>
                                    <li id="show_click_1" class="selected"><span class="word">我的待处理</span><span class="bg"></span></li>
                                    <li id="show_click_2" class=""><span class="word">我的已处理</span><span class="bg"></span></li>
                                    <div class="clear"></div>
                                </ul>
                            </div>
                        </h3>
                    </div>
                </div>
            	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_n">
                  <tr>
                    <th>状态</th>
                    <th>内部订单编号</th>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>
                    	<div class="grid_con">DGJK20120904885237
                        	<div class="grid_con_f" style="display:none;">
                            	<span class="arr_up"></span>
                            FSTBFX20130730693924已超时：受理时间是：2013年07月30日14时36分06秒，已经超时10天啦，请马上处理！</div>
                        </div>
                    </td>
                  </tr>
                  <tr class="curr">
                    <td><i class="lock"></i></td>
                    <td><div class="grid_con">CZPP20121030941206</div></td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td><div class="grid_con">GZTB20130511395393</div></td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td><div class="grid_con">GZJK20130622616102</div></td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>GZJK20130713657926</td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>GZJK20130717666780</td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>HZWMQT2013071766</td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>YJTBFX2013072568561</td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>GZTSC20130729691901</td>
                  </tr>
                  <tr>
                    <td><i class="lock"></i></td>
                    <td>FSTBFX20130730693924</td>
                  </tr>
                </table>
                <div class="pageBox">
                	<div class="pagecon">共 <span>1008</span> 条 <span>1</span>/<span>101</span></div>
                    <div class="pageA"><a href="#" class="disabled">首页</a><a href="#" class="disabled">上一页</a><a href="#">下一页</a><a href="#">末页</a></div>
                </div>
            </div>
        	<div class="right_r">
            	<div class="tabBg">
                    <div class="stat_graph">
                        <h3>
                            <div class="graph_tab">
                                <ul>
                                    <li id="show_click_1" class="selected"><span class="word">我的工作台</span><span class="bg"></span></li>
                                    <li id="show_click_2" class=""><span class="word">买家留言</span><span class="bg"></span></li>
                                    <li id="show_click_3" class=""><span class="word">卖家留言</span><span class="bg"></span></li>
                                    <li id="show_click_4" class=""><span class="word">处理意见</span><span class="bg"></span></li>
                                    <div class="clear"></div>
                                </ul>
                            </div>
                        </h3>
                    </div>
                </div>
                <div class="form_g">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
                      <tr>
                        <th>内部订单编号：</th>
                        <td colspan="3"><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:200px;" value="CZPP20121030941206" readonly="readonly" /><a href="#" class="dobtn" style="margin-left:5px;">查看资料</a></td>
                      </tr>
                      <tr>
                        <th>归属区域：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="广州" readonly="readonly" />
                		</td>
                        <th>外部状态：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="等待商家发货" readonly="readonly" /></td>
                      </tr>
                      <tr>
                        <th>订单来源：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="拍拍商城" readonly="readonly" />
                		</td>
                        <th>订单子来源：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="其他非网盟来源" readonly="readonly" /></td>
                      </tr>
                      <tr>
                        <th>配送方式：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="卖场包邮" readonly="readonly" /></td>
                        <th>物流公司：</th>
                        <td>
                        	<select name="select" id="select" style="width:100px;">
                              <option value="1">请选择</option>
                            </select>
                        </td>
                      </tr>
                      <tr>
                        <th>取件人：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="" readonly="readonly" /></td>
                        <th>取件人电话：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                      </tr>
                      <tr>
                        <th>发展人：</th>
                        <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                        <th>老带新号码：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" />
                		</td>
                      </tr>
                      <tr>
                        <th>支付方式：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="全额支付" readonly="readonly" /></td>
                        <th>老用户推荐人：</th>
                        <td>
                            <input name="textfield" type="text" class="ipt_new" id="textfield" style="width:150px;" />
                		</td>
                      </tr>
                      <tr>
                        <th>联系人：</th>
                        <td><input type="text" name="textfield" id="textfield" value="李旭" style="width:150px;" class="ipt_new" /></td>
                        <th>联系电话：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="18773555444" readonly="readonly" /></td>
                      </tr>
                      <tr>
                        <th>联系地址：</th>
                        <td><textarea name="textarea" id="textarea" cols="30" rows="5" style="width:150px;" >0020
                        </textarea></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <th>资料回收方式：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" />
                		</td>
                        <th>资料上传：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="已上传 " readonly="readonly" />
                		</td>
                      </tr>
                      <tr>
                        <th>发票打印：</th>
                        <td>
                            <input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="一次性打印" readonly="readonly" />
                		</td>
                        <th>发票名称：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="1" readonly="readonly" /></td>
                      </tr>
                      <tr>
                        <th>发展人编码：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                        <th>订单发展归属：</th>
                        <td>
                        	<select name="select" id="select" style="width:100px;">
                              <option value="1">请选择</option>
                            </select>
                        </td>
                      </tr>
                      <tr>
                        <th>集团编码：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="" readonly="readonly" /></td>
                        <th>所属用户：</th>
                        <td><input type="text" name="textfield" id="textfield" value="" style="width:150px;" class="ipt_new" /></td>
                      </tr>
                      <tr>
                          <th>推广渠道：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                        <th>业务凭证号：</th>
                        <td>
                        	<input type="text" name="textfield" id="textfield" value="" style="width:150px;" class="ipt_new" />
                        </td>
                      </tr>
                      <tr>
                        <th>三方协议编码：</th>
                        <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="" readonly="readonly" /></td>
                        <th>&nbsp;</th>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                </div>
                <div class="ps_div">
                	<h2>入网信息(商品包数量:1)</h2>
               	  	<div class="netWarp">
                  		<a href="#" class="icon_open">展开</a>
                    	<div class="goodBar">
                    	  <label>
                              <input type="checkbox" name="checkbox2" id="checkbox2" />
                              商品包1
                          </label>
                          <label>
                              商品包类型：
                              套卡
                          </label>
               		  </div>
                   	  <div class="goodTit">商品信息(实物产品)<a href="#" class="icon_del"></a></div>
						<div class="goodCon">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              <tr>
                                <th>商品名称：</th>
                                <td><textarea name="textarea2" id="textarea2" cols="45" rows="5" style="width:200px;" >[今日特价]联通3G卡四五折 送660元话费 2400兆流量 正品包邮</textarea></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>品牌：</th>
                                <td><input type="text" name="textfield" id="textfield" value="三星" style="width:150px;" class="ipt_new" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>机型：</th>
                                <td><input type="text" name="textfield" id="textfield" value="Galaxy Note" style="width:150px;" class="ipt_new" /></td>
                                <th>颜色：</th>
                                <td><input type="text" name="textfield" id="textfield" value="" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>活动类型：</th>
                                <td><select name="select3" id="select3" style="width:150px;">
                                  <option value="1">线上促销</option>
                                </select></td>
                                <th>大小卡：</th>
                                <td><select name="select3" id="select3" style="width:150px;">
                                  <option value="1">请选择</option>
                                </select></td>
                              </tr>
                            </table>
                      	</div>
                        <div class="goodTit">商品信息(号码产品)<a href="#" class="icon_del"></a></div>
						<div class="goodCon">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              <tr>
                                <th>商品名称：</th>
                                <td><textarea name="textarea2" id="textarea2" cols="45" rows="5" style="width:200px;" >号码产品
                                </textarea></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>所选号码：</th>
                                <td><input type="text" name="textfield" id="textfield" value="18680581370" style="width:150px;" class="ipt_new" /></td>
                                <th>套餐名称：</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>是否靓号：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>靓号单编码：</th>
                                <td><input type="text" name="textfield" id="textfield" value="" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>是否已补差价：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>合约期：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="100月" readonly="readonly" /></td>
                              </tr>
                              <tr>
                                <th>首月资费方式：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="套餐包外资费" readonly="readonly" /></td>
                                <th>网别：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                              </tr>
                              <tr>
                                <th>卡串号：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>应收：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                                <th>实收：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                              </tr>
                              <tr>
                                <th>BSS开户工号：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>BSS应收金额(元)：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>BSS折扣金额(元)：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>优惠券金额(元)：</th>
                                <td><input type="text" name="textfield" id="textfield" style="width:150px;" class="ipt_new" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                            </table>
                      	</div>
                        <div class="goodTit">开户信息</div>
						<div class="goodCon">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              <tr>
                                <th>客户姓名：</th>
                                <td><input type="text" name="textfield" id="textfield" value="张三" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>客户证件类型：</th>
                                <td><input type="text" name="textfield" id="textfield" value="18位身份证" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>客户证件有效期：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:150px;" value="2014-08-26 13:44:07" /></td>
                              </tr>
                              <tr>
                                <th>客户证件号码：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:150px;" value="431011198807114785" /></td>
                              </tr>
                              <tr>
                                <th>客户证件地址：</th>
                                <td><textarea name="textarea2" id="textarea2" cols="45" rows="5" style="width:200px;" >广东省广州市

                                </textarea></td>
                              </tr>
                            </table>
                      	</div>
                        <div class="goodTit">托收信息</div>
						<div class="goodCon">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              <tr>
                                <th>是否托收：</th>
                                <td><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" value="否" readonly="readonly" /></td>
                                <th>银行名称：</th>
                                <td><input type="text" name="textfield" id="textfield" value="招商银行" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                              <tr>
                                <th>银行账号：</th>
                                <td colspan="3"><input name="textfield" type="text" disabled="disabled" class="ipt_new" id="textfield" style="width:150px;" readonly="readonly" /></td>
                              </tr>
                            </table>
                      	</div>
           	  	  	</div>
              	</div>
                <div class="ps_div">
                	<h2>赠送信息</h2>
           	  	  	<div class="netWarp">
                  		<a href="#" class="icon_close">展开</a>
                    	<div class="goodTit">礼品1<a href="#" class="icon_del"></a></div>
						<div class="goodCon">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
                              <tr>
                                <th>礼品种类：</th>
                                <td><input type="text" name="textfield" id="textfield" value="三星" style="width:150px;" class="ipt_new" /></td>
                                <th>礼品类型：</th>
                                <td><select name="select3" id="select3" style="width:150px;">
                                  <option value="1">类型</option>
                                </select></td>
                              </tr>
                              <tr>
                                <th>礼品内容：</th>
                                <td><textarea name="textarea2" id="textarea2" cols="45" rows="5" style="width:200px;" ></textarea></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>面值：</th>
                                <td><input type="text" name="textfield" id="textfield" value="100" style="width:150px;" class="ipt_new" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              </tr>
                              <tr>
                                <th>是否补寄：</th>
                                <td><select name="select3" id="select3" style="width:150px;">
                                  <option value="1">是</option>
                                  <option value="2">否</option>
                                </select></td>
                                <th>数量：</th>
                                <td><input type="text" name="textfield" id="textfield" value="100" style="width:150px;" class="ipt_new" /></td>
                              </tr>
                            </table>
                   	  </div>
               	  </div>
                </div>
              	<div class="comBtns" style="display:none;"><a href="#" class="dobtn" style="margin-right:5px;">请发货</a><a href="#" class="dobtn" style="margin-right:5px;">保存</a><a href="#" class="dobtn" style="margin-right:5px;">挂起</a><a href="#" class="dobtn" style="margin-right:5px;">委托</a></div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
</body>
</html>
