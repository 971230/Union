<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
</div>

<div class="formWarp" style="border-bottom: none;">
<div class="tit">
	<a href="javascript:void(0);" name="show_close_btn" tag_id="8800" class="closeArrow"></a>订单优惠方案
    <div class="dobtnDiv"></div>
</div>
<div id="order_tag_8800" class="formGridDiv" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>方案名称</th>
        <th>优惠内容</th>
        <th>活动类型</th>
        <th>允许参加会员</th>
        <th>赠品</th>
      </tr>
      <tbody>
      <c:forEach items="${orderPromotionList }" var="op">
      	<tr>
	        <td style="text-align: center;">${op.activity_name }</td>
	        <td style="text-align: center;">${op.pmt_describe }</td>
	        <td style="text-align: center;">
	        	<c:if test="${op.pmts_id=='enoughPriceGiveGiftPlugin' }">满就送</c:if>
	        	<c:if test="${op.pmts_id=='enoughPriceReducePrice' }">满就减</c:if>
	        	<c:if test="${op.pmts_id=='免运费' }">满就减</c:if>
	        </td>
	        <td style="text-align: center;">
	        	<c:forEach items="${op.memberLvList }" var="mlv">
		  			<span>[${mlv.name }]</span>&nbsp;
		  		</c:forEach>
	        </td>
	         <td style="text-align: center;">
	        	<c:if test="${op.pmts_id=='enoughPriceGiveGiftPlugin' }">
	        	 	<c:forEach items="${op.promotionGoodsList }" var="gs">
	        	 		<p>${gs.name }</p>
	        	 	</c:forEach>
	        	</c:if>
	        </td>
	      </tr>
      </tbody>
      </c:forEach>
    </table>
</div>
</div>
