<%@ page contentType="text/html; charset=UTF-8" %>
<%

  String d_type = (String)request.getAttribute("d_type");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>
<%@ include file="/commons/taglibs.jsp"%>
                    <!-- 商品基本信息开始 -->
                    
                    <div class="grid_n_cont_sub">
                        <h3>基本信息：</h3>
                        <input type="hidden"  id="specValidateMsg"  value="${specValidateMsg}"/>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                               <th>商品名称：</th>
                               <td >
                                  <html:orderattr  disabled="disabled" order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr>
                               	  <input type="button" value="查询" id="selNewGoodsBtn" class="graybtn1"/>
                               </td>
                               
                               <th>原商品名称：</th>
                               <td>
                                  <html:orderattr  disabled="disabled" order_id="${order_id}" field_name="oldGoodsName"  field_desc ="旧商品名称" field_type="input"></html:orderattr></td>
                               </td>
                            </tr>
                            <tr>
                                <th><span>*</span>商品编号：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="out_package_id"  field_desc ="商品编号" field_type="input"></html:orderattr></td>
                              <%-- <th><span>*</span>商品名称：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr></td>
                                --%> 
                                <th><span>*</span>商品类型：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr></td>
                                <th>是否老用户：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="is_old"  field_desc ="是否老用户" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>产品品牌：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="DIC_BRAND_TYPE"  order_id="${order_id}" field_name="pro_brand"  field_desc ="产品品牌" field_type="select"  ></html:orderattr>
                                </td>
                                <th><span>*</span>是否总部合约：</th>
                                <td><html:orderattr disabled="disabled"  attr_code="DIC_IS_GROUP_CONTRACT" order_id="${order_id}" field_name="is_group_contract"  field_desc ="是否总部合约" field_type="select"  ></html:orderattr></td>
                                <th><span>*</span>商品价格（元）：</th>
                                <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="sell_price"  field_desc ="商品价格" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th><span>*</span>优惠价格（元）：</th>
                                <td>
                                <html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="discountrange"  field_desc ="优惠价格" field_type="input"></html:orderattr></td>
                                <th><span>*</span>实收价格（元）：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="pro_realfee"  field_desc ="实收价格" field_type="input"></html:orderattr></td>
                                <th><span>*</span>商品数量：</th>
                                <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="pro_num"  field_desc ="商品数量" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                            	<th>开户流水号：</th>
	                          	<td><html:orderattr order_id="${order_id}" field_name="active_no"  field_desc ="开户流水号" field_type="input"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
                    <!-- 商品基本信息结束 -->
                    
		            <!-- 用户证件信息开始 -->
		            <div class="grid_n_cont_sub">
						<h3>
							用户证件信息：<span id="certValidateMsg" style="color: red;">
					
								${pageDatas.get('valiMsg') }</span> | <span style="color: red;">预校验信息：
								${pageDatas.get('validateMsg') } 
								<c:if test="${pageDatas.get('noteSend')=='true'}">
									<input type="button" id="noteSend" value="短信通知"
										onclick="sms3NetSend('','${order_id}')" class="graybtn1" />
								</c:if>
							</span><input type="text" value="no" id="certIsEdit" style="display: none">
						</h3>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>证件类型：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_CARD_TYPE" disabled="${pageDatas.get('isCertOnly') }"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select" ></html:orderattr>
                                </td>
                                <th>证件号码：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="${pageDatas.get('isCertOnly') }" field_name="cert_card_num"  field_desc ="证件号码" field_type="input" ></html:orderattr></td>
                                <th>证件地址：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>客户名称：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="${pageDatas.get('isCertOnly') }" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input" ></html:orderattr></td>
                                <th>客户类型：</th>
                                <td>
                                   <html:orderattr attr_code="DIC_CUSTOMER_TYPE" disabled="${pageDatas.get('isCertOnly') }"   order_id="${order_id}" field_name="CustomerType"  field_desc ="客户类型" field_type="select"  ></html:orderattr>
                                </td>
                                <th>证件有效期：</th>
                                <td><html:orderattr  order_id="${order_id}" disabled="${pageDatas.get('isCertOnly') }" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
                    <div class="grid_n_cont_sub"  id="certPhotoDiv" style="display:none">
                    <h3>用户证件照片校验：</h3>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                    <tr>
                    <td><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr><td><img id="gztPhoto" src="" title="国政通图片"/></td></tr>
                    <tr><td><span id="imgTitle1"></span></td></tr>
                    <tr><td><span id="imgTitle2"></span></td></tr>
                    <tr><td><span id="imgTitle3"></span></td></tr>
                    </table></td>
                    <td><img id="userPhoto1" src="" width="200px"/></td>
                    <td><img id="userPhoto2" src="" width="200px"/></td>
                    <td><img id="userPhoto3" src="" width="200px"/></td>
                    </tr>
                    <tr>
                    <td colspan="4" style="text-align:center">
                    <input type="button" id="photoAgree" value="图片一致" onclick="valiPhotoLog('${order_id}','1')" class="graybtn1">&nbsp;&nbsp;
                    <!-- input type="button" id="photoUnAgree" value="图片不一致" onclick="valiPhotoLog('${order_id}','0')" class="graybtn1">&nbsp;&nbsp; -->
                    <input type="button" id="noteSend"  value="不一致，短信通知" onclick="sms3NetSend('0','${order_id}')" class="graybtn1"/>
                    </td>
                    </tr>
                    </table>
                    </div>
                   
                    <script>
                    $("input[field_name='cert_card_num']").blur(function(){
                    	certEdit();
                    });
                    $("input[field_name='phone_owner_name']").blur(function(){
                    	certEdit();
                    });
                    </script>
            		<!-- 用户证件信息结束 -->
   <script type="text/javascript">         		
   $(function(){
	   Eop.Dialog.init({id:"selGoodsDlg",modal:true,title:"查询商品",width:'800px'});
	   //查询商品selNewGoodsBtn
	     $("#selNewGoodsBtn").bind("click",function(){
	       var stdOrderId = $("#stdOrderId").val();
	       var url = app_path+"/shop/admin/orderWarningAction!queryGoodsList.do?ajax=yes&stdOrderId="+stdOrderId;
	 			$("#selGoodsDlg").load(url,{},function(){});
	 			 Eop.Dialog.open("selGoodsDlg");
	 		});
   });
   </script>