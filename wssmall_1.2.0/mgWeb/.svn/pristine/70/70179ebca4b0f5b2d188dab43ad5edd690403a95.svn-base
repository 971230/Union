<%@page import="com.ztesoft.common.util.SequenceTools"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head lang="en">
	    <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <link rel="stylesheet" href="style/style.css"/>
	    <title>产品详情</title>
	    <script type="text/javascript">
	    	$(function(){
	    		$("#submit").unbind("click").bind("click",function(){
	    			if($("#prod_offer_codes").val() == ""){
	    				alert("商品编码不能为空！");
	    				return false;
	    			}
	    			if($("#prod_offer_nums").val() == ""){
	    				alert("商品数量不能为空！");
	    				return false;
	    			}
	    			if($("#prod_offer_nums").val() <= 0){
	    				alert("商品数量需要大于零！");
	    				return false;
	    			}
	    			if($("#name").val() == ""){
	    				alert("请输入机主姓名！");
	    				return false;
	    			}
	    			if($("#certi_num").val() == ""){
	    				alert("请输入18位身份证号码！");
	    				return false;
	    			}
	    			if($("#ship_name").val() == ""){
	    				alert("请输入收件人名！！");
	    				return false;
	    			}
	    			if($("#ship_tel").val() == ""){
	    				alert("请输入联系方式！");
	    				return false;
	    			}
	    			
	    			var json_data = $("#theForm").serialize();
	    			$.ajax({
	    				 type: "POST",
	    				 url: ctx+"/servlet/centerMallServlet",
	    				 data: json_data,
	    				 dataType:"json",
	    				 success: function(result){
	    					 
	    					 if (result.respmesg.RespCode == '0000') {
	    						 //window.location.href = ctx + '/shop/admin/keyrule!list.do';
	    						 //TODO 支付
	    						 alert("下单成功...");
	    						 $("#out_tid_em").text(result.orderIds);
	    						 $("#out_tid_div").show();
	    				     } else {
	    				    	 alert(result.respmesg.RespDesc);
	    				     }
	    				 },
	    				 error:function(){
	    					 alert("操作失败，请重试");
	    				 }
	    			});
	    		});
	    	});
	    </script>
	</head>
	<body>
		<div class="wrap">
		    <div class="head-wrap">
		        <div class="head-main">
		            <div class="hd-search-box fr">
		                <button class="hd-search-btn" type="button">搜</button>
		                <label>
		                    <input type="text" class="hd-search-text"/>
		                </label>
		            </div>
		            <div class="logo"><img src="images/logo.png" alt=""/></div>
		            <div class="project-name">中国联通授权销售网点(社会渠道)</div>
		        </div>
		        <!-- head-main end -->
		    </div>
		    <!-- head-wrap end -->
		
		    <div class="nav-wrap">
		        <div class="nav-main">
		            <ul class="nav-list">
		                <li class="nav-item"><a href="javascript:void(0);" class="nav-link active">首页</a></li>
		                <li class="nav-item"><a href="javascript:void(0);" class="nav-link">号卡</a></li>
		                <li class="nav-item"><a href="javascript:void(0);" class="nav-link">合约机</a></li>
		                <li class="nav-item"><a href="javascript:void(0);" class="nav-link">上网卡</a></li>
		                <li class="nav-item"><a href="javascript:void(0);" class="nav-link">配件</a></li>
		            </ul>
		        </div>
		    </div>
		    <!-- nav-wrap end -->
		
		    <div class="main">
		
		        <div class="crumbs">
		            <ul>
		                <li><a href="javascript:void(0);">首页</a></li>
		                <li class="mlr5">&gt;</li>
		                <li><a href="javascript:void(0);">号卡</a></li>
		                <li class="mlr5">&gt;</li>
		                <li><a href="javascript:void(0);">96元A套餐</a></li>
		            </ul>
		        </div>
		        <!-- crumbs end -->
				<form method="post" name="theForm" id="theForm">
			        <div class="product-det clear-fix">
			
			            <div class="product-from fr">
			                <div class="product-from-tit">
			                    <p><span class="red">【存150送1150】</span>96元A套餐,40%返还,预存150元赠送1150元每月赠送38元</p>
			                    <p class="red">预存款一次性到账,赠款分月返还,次月起赠,赠款抵扣套餐月费的40%。</p>
			                </div>
			                <%--<div class="product-from-price">
			                    	价格：<em class="font16 red">￥150</em>
			                </div>
			                <ul class="product-from-con">
			                    <li class="product-from-item">
			                        <em class="product-from-th">配送选择：</em>
			                        <a href="#" class="product-deliver-type">物流</a>
			                    </li>
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">号码选择：</em>
			                            <input type="text" class="product-inp-text"/>
			                        </label>
			                    </li>
			                </ul> --%>
			                <div class="dashed-border mtb20"></div>
			                <div class="product-from-sub-tit">商品信息</div>
			                <ul class="product-from-con">
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">商品编码：</em>
			                            <input type="text" name="prod_offer_codes" id="prod_offer_codes" class="product-inp-text"/>
			                        </label>
			                    </li>
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">商品数量：</em>
			                            <input type="text" name="prod_offer_nums" id="prod_offer_nums" class="product-inp-text"/>
			                        </label>
			                    </li>
			                </ul>
			                <div class="dashed-border mtb20"></div>
			                <div class="product-from-sub-tit">开户信息</div>
			                <ul class="product-from-con">
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">机主姓名：</em>
			                            <input type="text" name="name" id="name" class="product-inp-text"/>
			                        </label>
			                    </li>
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">身份证号：</em>
			                            <input type="text" name="certi_num" id="certi_num" class="product-inp-text"/>
			                        </label>
			                    </li>
			                </ul>
			                <div class="dashed-border mtb20"></div>
			                <div class="product-from-sub-tit">收货信息</div>
			                <ul class="product-from-con">
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">收件人名：</em>
			                            <input type="text" name="ship_name" id="ship_name" class="product-inp-text"/>
			                        </label>
			                    </li>
			                    <li class="product-from-item">
			                        <label>
			                            <em class="product-from-th">联系方式：</em>
			                            <input type="text" name="ship_tel" id="ship_tel" class="product-inp-text"/>
			                        </label>
			                    </li>
			                </ul>
			                <div id="out_tid_div" style="display:none;">
				                <div class="dashed-border mtb20"></div>
				                <div class="product-from-sub-tit">外部订单号:</div>
				                <ul class="product-from-con">
				                    <li class="product-from-item">
				                        <label id="out_tid_em">
				                            
				                        </label>
				                    </li>
				                </ul>
			                </div>
			                <div class="mt20">
			                    <button type="button" id="submit" class="product-submit-btn">提交</button>
			                </div>
			            </div>
			            <!-- product-from end -->
			
			            <div class="product-img">
			                <div class="product-big-img">
			                    <img src="images/product-img.png" alt=""/>
			                </div>
			                <!-- product-big-img end -->
			                <div class="product-img-main">
			                    <a href="#" class="product-img-prev"></a>
			                    <a href="#" class="product-img-next"></a>
			                    <div class="product-img-list">
			                        <ul>
			                            <li><img src="images/product-img.png" alt=""/></li>
			                            <li><img src="images/product-img.png" alt=""/></li>
			                            <li><img src="images/product-img.png" alt=""/></li>
			                            <li><img src="images/product-img.png" alt=""/></li>
			                        </ul>
			                    </div>
			                </div>
			            </div>
			            <!-- product-img end -->
			
			        </div>
			        <!-- product-det end -->
				</form>
		    </div>
		    <!-- main end -->
		
		    <div class="foot-wrap">
		        <div class="foot-content">
		            <p>Copyright  1999-2014  中国联通 版权所有</p>
		            <p>中华人民共和国增值电信业务经营许可证 经营许可证编号：A2.B1.B2-20090003</p>
		            <p class="mt10"><img src="images/copyright-logo1.png" alt="" class="vertical-middle"/><span class="mlr5 vertical-middle">经营性网站备案信息</span><img src="images/copyright-logo2.png" alt=""  class="vertical-middle"/></p>
		        </div>
		    </div>
		    <!-- foot-wrap end -->
		
		</div>
		<!-- wrap end -->
	</body>
</html>