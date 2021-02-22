<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div id='left_panel' style='float:left;width:20%;'>
	<ul class="treeList">
        <li style="cursor:pointer;" class="last clk" url="${pageContext.request.contextPath}/shop/admin/ruleObject!getDatePage.do?ajax=yes"><a><span></span>日期</a></li>
        <li style="cursor:pointer;" class="opend" ><a><span></span>常量值</a>
        	 <ul>
                <li class="last clk" url="${pageContext.request.contextPath}/shop/admin/ruleObject!getConstPage.do?ajax=yes"><a><span></span>固定值</a></li>
                <li class="last clk" url="${pageContext.request.contextPath}/shop/admin/ruleObject!getTextPage.do?ajax=yes"><a><span></span>输入文本</a></li>
            </ul>
        </li>
        <li style="cursor:pointer;" class="opend"><a><span></span>弹出框</a>
            <ul>
                <li class="last clk" url="${pageContext.request.contextPath}/shop/admin/ruleObject!getGoodsPage.do?ajax=yes"><a><span></span>商品弹出</a></li>
                <li class="last clk" url="${pageContext.request.contextPath}/shop/admin/ruleObject!getPartnerPage.do?ajax=yes"><a><span></span>分销商弹出</a></li>
            </ul>
        </li>
         <li style="cursor:pointer;" class="opend"><a><span></span>变量值</a>
            <ul id="select_obj_uls">
                
            </ul>
        </li>
    </ul>
</div>

<div id='right_panel' style='float:right;width:79%;'></div>
<div class="submitlist" align="center" style="padding-top:220px;display:none;">
 <table>
 <tr>
 	<td>
   		<input type="button" id="confirm_btn" value=" 确定 " class="submitBtn" />
   	</td>
   </tr>
 </table>
</div>
<script type="text/javascript">
	var rule_obj = {
		initGoodsClk:function(){
		 	$("#searchGoodsListForm #goods_search").unbind("click").click(function(){
				
				var url = ctx + "/shop/admin/ruleObject!getGoodsPage.do?ajax=yes" ;
				var goods_name = $("#searchGoodsListForm #goods_name").val();
 				Cmp.ajaxSubmit('searchGoodsListForm', 'right_panel', url, {name:goods_name}, rule_obj.initGoodsClk);
			});
			
  		},
  		initPartnerClk:function(){
  			$("#searchPartnerListForm #partner_search").unbind("click").click(function(){
				
				var url = ctx + "/shop/admin/ruleObject!getPartnerPage.do?ajax=yes" ;
				var partner_name = $("#searchPartnerListForm #partner_name").val();
 				Cmp.ajaxSubmit('searchPartnerListForm', 'right_panel', url, {partner_name:partner_name}, rule_obj.initPartnerClk);
			});
  		}
	};
</script>