<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="goodsN/css/style.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/goodsN/css/uploadImg.css" type="text/css" />
<script type="text/javascript" src="goodsN/js/goods_input.js"></script>

<div class="gridWarp">
	<form method="post" name="goodsEditForm" action="${actionName}" id="goodsEditForm" class="validate" enctype="multipart/form-data">
	<div class="new_right">
       	<div class="right_warp">
        	<div class="grid_n_div">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>商品信息</h2>
                <div class="grid_n_cont">
                    <div class="grid_n_cont_sub">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_b">
                            ${goodstypeN}<!-- 商品类型、分类 -->
                            <tr>
                              <th><span class="red">*</span>商品名称：</th>
                              <td>
                              	<input name="goods.name" type="text" class="ipt_new" id="name" style="width:200px;" required="true" value="${goodsView.name }"/>
                              	<input name="goods.goods_id" id="goods_id" type="hidden" value="${goodsView.goods_id }"/>
                              	<input name="goodsExtData.is_copy" id="is_copy" type="hidden" value="${is_copy }"/>
                              </td>
                              <th>商品简称：</th>
                              <td><input name="goods.simple_name" type="text" class="ipt_new" id="simple_name" style="width:200px;" value="${goodsView.simple_name }"/></td>
                              <th>条形码：</th>
                              <td><input name="goods.sn" type="text" class="ipt_new" id="sn" style="width:200px;" required="true" value="${goodsView.sn }"/></td>
                            </tr>
                            <tr id="goodsPriceTr">
                              <th><span class="red">*</span>市场价：</th>
                              <td><input name="goods.mktprice" type="text" class="ipt_new" id="mktprice" style="width:200px;" required="true" value="${goodsView.mktprice }"/></td>
                              <th <c:if test="${goodsView.have_spec=='1' }">style="visibility:hidden"</c:if>><span class="red">*</span>销售价：</th>
                              <td <c:if test="${goodsView.have_spec=='1' }">style="visibility:hidden"</c:if>><input name="goods.price" type="text" class="ipt_new" id="price" style="width:200px;" required="true" value="${goodsView.price }"/></td>
                              <th <c:if test="${goodsView.have_spec=='1' }">style="visibility:hidden"</c:if>>库存：</th>
                              <td <c:if test="${goodsView.have_spec=='1' }">style="visibility:hidden"</c:if>><input name="goods.store" type="text" class="ipt_new" id="store" style="width:200px;" value="${goodsView.store }"/></td>
                            </tr>
                            
                            <tr>
                              <th><span class="red">*</span>价格公开权限：</th>
                              	<td colspan="5">
                              	<div class="grid_div_line">
                              	  <c:forEach items="${lvList}" var="lv">
									<label>
										<input type="checkbox" value="${lv.lv_id }" name="goodsExtData.lvidArray"
											<c:if test="${lv.selected == '00A'}">checked="checked"</c:if>>
										${lv.name }
									</label>
								  </c:forEach>
    							</div>
    							${goods_priceN }<!-- 商品价格  -->
                              </td>
                            </tr>
                            <tr>
                              <th><span class="red">*</span>是否上架：</th>
                              <td colspan="5">
                                <label>
                                  <input name="goods.market_enable" type="radio" id="RadioGroup1_0" value="1" <c:if test="${goodsView.market_enable == 1 || goods.market_enable=='' || goods.market_enable==null}">checked="true"</c:if>/>
                                  	是</label>
                                <label>
                                  <input type="radio" name="goods.market_enable" value="0" id="RadioGroup1_1" <c:if test="${goodsView.market_enable == 0}">checked="true"</c:if>/>
                                  	否</label>
                              </td>
                            </tr>
                            <tr>
                              <th>商品规格：</th>
                              <td colspan="5">
                              	<div class="grid_div_line">
                                	<a href="#" class="gray_btn" id="openSpec">开启规格</a>
                                	<a href="#" class="gray_btn" id="closeSpec">关闭规格</a>
                                	
                                </div>
                                ${goodsspecN }<!-- 商品规格 -->
                              </td>
                            </tr>
                            ${goodstagN }<!-- 商品标签 -->
                        </table>
                    </div>
                </div>
            </div>
            <div class="grid_n_div" id="goods_props">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>商品属性</h2>
                <div class="grid_n_cont">
                	${props_inputN }
                </div>
            </div>
            <div class="grid_n_div" id="goods_params">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>商品参数</h2>
              	<div class="grid_n_cont">
                  	${params_inputN }
       	  	  	</div>
   	  	  	</div>
   	  	  	<div class="grid_n_div" id="goodsRelDiv" style="display:none;">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>合约关联套餐</h2>
                ${goodsRelN }
            </div>
          	<div class="grid_n_div" id="goodsComplexDiv">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>商品绑定</h2>
            	${goodscomplexN }
   	  	  	</div>
            <div class="grid_n_div" id="goodsAdjunctDiv">
            	<h2><a href="javascript:void(0);" class="closeArrow"></a>商品配件</h2>
            	${goodsadjunctN }
   	  	  	</div>
         	<div class="item_box">
            	${platformInfoN }
            </div>
            <div class="comBtns">
            	<a href="javascript:void(0);" class="dobtn" id="addSaveBtn" style="margin-right:5px;">保存</a>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    </form>
</div>
<!-- 商品规格弹出框  -->
<div id="pop_div" class="pop_div" style="z-index: 2000;width:770px;display:none;">
	<div class="pop_tit">
    	<h2>选择商品规格</h2>
    	<a href="#" class="pop_close">关闭</a>
    </div>
    <div class="pop_cont">
        <div class="tabBg">
            <div class="stat_graph">
                <h3>
                    <div class="graph_tab">
                        <ul>
                        	<c:forEach var="spec" items="${specList}" varStatus="idx">
	                    		<li <c:if test="${idx.index==0 }">class="selected"</c:if> tabid="${idx.index }">
	                    			<span class="word" specid="${spec.spec_id }">${spec.spec_name }</span>
	                    			<span class="bg"></span>
	                    		</li>
	                    	</c:forEach>
                            <div class="clear"></div>
                        </ul>
                    </div>
                </h3>
            </div>
        </div>
    	<div class="pop_warp" style="margin-top:10px;">
    		<input type="hidden" name="spec_type_num" id="spec_type_num" value="${fn:length(specList)}"/>
    		<c:forEach var="spec" items="${specList}" varStatus="idx2">
                <c:if test="${spec.spec_type==1 }">
	                <div id="tab${idx2.index }" class="size_list" <c:if test="${idx2.index!=0 }">style="display:none;"</c:if>>		            	
	                	<ul class="clearfix">
	                		<c:if test="${fn:length(spec.valueList)!=0 }">
			                	<c:forEach var="specv" items="${spec.valueList}" varStatus="idxv">
			                		<li specid="${spec.spec_id}" spectype="${spec.spec_type}" specvid="${specv.spec_value_id }" val="${specv.spec_value }" imgurl="${specv.spec_image }"><img height="20" width="34" src="${specv.spec_image }" alt="${specv.spec_value}" title="${specv.spec_value}"></img></li>
			                	</c:forEach>
		                	</c:if>
		                	<c:if test="${fn:length(spec.valueList)==0 }">
		                		<div class="remind_n_div" style="margin:0px;"><span><img src="images/ic_remind.png" width="16" height="16" /></span><p>此规格没有配置规格值！</a></p></div>
		                	</c:if>
		                </ul>
		                <div class="clear_box">
		                	<a href="#" class="gray_btn">全部添加</a>
		                	<a href="#" class="blue_a" style="margin-left:5px;">清空</a>
		                	<input type="hidden" name="selected_specs" id="spec_${spec.spec_id}"
		                		specid="${spec.spec_id}" specname="${spec.spec_name }" spectype="${spec.spec_type}" specimg=""/><!-- 隐藏域，用于保存选择的商品规格，用于生成货品  -->
		                </div>
		            </div>
                </c:if>
                <c:if test="${spec.spec_type==0 }">
                	<div id="tab${idx2.index }" class="size_list" <c:if test="${idx2.index!=0 }">style="display:none;"</c:if>>
		            	<ul class="clearfix">
		            		<c:if test="${fn:length(spec.valueList)!=0 }">
			                	<c:forEach var="specv" items="${spec.valueList}" varStatus="idxv">
			                		<li specid="${spec.spec_id}"  spectype="${spec.spec_type}" specvid="${specv.spec_value_id }" val="${specv.spec_value }">${specv.spec_value }</li>
			                	</c:forEach>
		                	</c:if>
		                	<c:if test="${fn:length(spec.valueList)==0 }">
		                		<div class="remind_n_div" style="margin:0px;"><span><img src="images/ic_remind.png" width="16" height="16" /></span><p>此规格没有配置规格值！</a></p></div>
		                	</c:if>
		                </ul>
		                <div class="clear_box">
		                	<a href="#" class="gray_btn">全部添加</a>
		                	<a href="#" class="blue_a" style="margin-left:5px;">清空</a>
		                	<input type="hidden" name="selected_specs" id="spec_${spec.spec_id}"
		                		specid="${spec.spec_id}" specname="${spec.spec_name }" spectype="${spec.spec_type}" specimg=""/><!-- 隐藏域，用于保存选择的商品规格，用于生成货品  -->
		                </div>
		            </div>
                </c:if>
            </c:forEach>
        	
          	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g_n">
              	<tr>
                	<th>系统规格</th>
                	<th>关联商品相册图片</th>
                	<th>操作</th>
              	</tr>
          	</table>
        </div>
        <div class="pop_btn_box">
            <a href="#" class="blueBtns"><span>生成所有货品</span></a>
        </div>
    </div>
</div>
<div id="modelTrDiv" style="display:none;">
	<tr>
      <td>
      		<input type="text" name="sns" id="sns" value="" class="ipt_new" />
      		<input type="hidden" name="specids" value=""/>
      		<input type="hidden" name="specvids" value=""/>
      </td>
      <td>
      	<div class="size_box">
     	</div>
      </td>
      <td><input type="text" name="stores" value="" class="ipt_new" /></td>
      <td><input type="text" name="prices" value="" class="ipt_new" /><a href="#" class="gray_btn" style="margin-left:5px;">会员价</a></td>
      <td><input type="text" name="weights" value="" class="ipt_new" /></td>
      <td><a href="#"><img src="${ctx }/shop/admin/goodsN/images/ico_close.png" width="16" height="16" />删除</a></td>
    </tr>
</div>
<div id="goods_img_selected">
<ul></ul>

<div class="footContent" style="">
<div style="width: 200px; height: 40px; margin: 0pt auto;"
	class="mainFoot">
<table style="margin: 0pt auto; width: auto;">
	<tbody>
		<tr>
			<div class="pop_btn_box">
	            <a href="javascript:void(0);" class="blueBtns"><span>保存</span></a>
	        </div>
		</tr>
	</tbody>
</table>
</div>
</div> 
</div>
<c:if test="${page_type=='view'}">
	<script type="text/javascript">

        $('#cat_id').attr("disabled", "disabled");//禁用下拉列表框
        $('#type_select').attr("disabled", "disabled");
        $('#brand_select').attr("disabled", "disabled");
        $('#goodsEditForm').find("input[type='text']").each(function(){
        	$(this).attr("disabled", "disabled");
        })
        $('#memberpricedlg').find(".lvPrice").each(function(){
        	$(this).attr("disabled", "disabled");
        })
        $('#goodsEditForm').find("[type='checkbox']").each(function(){
        	$(this).attr("disabled", "disabled");
        })
        $('input[type="radio"]').each(function(){
        	$(this).attr("disabled", "disabled");
        })
        
        $('div[attr_name="place_div"]').each(function(){
        	$(this).remove();
        })
        $('.item_box_cont').find("a.gray_b").each(function(){
        	$(this).remove();
        })
        $('#adjunct_content').find("a.blue_b").each(function(){
        	$(this).remove();
        })
        $('#adjunct_content').find("img.delete").each(function(){
        	$(this).parent().remove();
        })
        $('#textarea').attr("disabled", "disabled");
        $('#complexOpenDialog').remove();
        $('#adjunctAddDialog').remove();
        $('#goodsTypeQuickBtn').remove();
        $('#addBrandDialog').remove();
        $('#addSaveBtn').remove();

    </script>
</c:if>