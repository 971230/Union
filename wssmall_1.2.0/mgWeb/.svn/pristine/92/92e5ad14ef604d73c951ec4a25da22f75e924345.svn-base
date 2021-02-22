<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
	
/* CSS Document */
.remind_div{ width:550px; margin:20px auto;}
.remind_con{ font-size:24px; font-family:"微软雅黑"; padding-top:10px; padding-bottom:10px;}
.remind_pic{ background:url(../images/remindimg.jpg) no-repeat; width:84px; height:84px; vertical-align:middle; display:inline-block;}
.remind_txt{ line-height:28px; padding-left:30px; font-size:14px;}
.remind_txt table th{ text-align:right; padding-right:10px;}
.remind_txt table td span{ color:#F00; font-weight:bold; font-family:Tahoma, Geneva, sans-serif;}

.remind_btn{ text-align:center; padding-top:30px;}
.submitbtn{ background:url(../images/submitbtn.gif) repeat-x; height:22px; line-height:22px; color:#fff; border:1px solid #14912d; display:inline-block; padding-left:15px; padding-right:15px; margin-left:3px; margin-right:3px;}

</style>
<div class="remind_div" style="width: 100%;height: 100%;">
	<div class="remind_con"><i class="remind_pic"></i>尊敬的客户，您本次支付<c:out value='${result.resultMsg}'/>！</div>
    <c:if test="${result.dealResult==0}">
    <div class="remind_txt">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
          <th style='width:250px;'>本次交费：</th>
          <td><span><c:out value='${result.money}'/></span>元</td>
        </tr>
        <tr>
          <th style='width:250px;'>本次订单号：</th>
          <td><span><c:out value='${result.transaction_id}'/></span></td>
        </tr>
      </table>
    </div>
    </c:if>
  <div class="remind_btn"><a href="index.html" class="submitbtn" style="background: url(../admin/images/submitbtn.gif) repeat-x;">返回首页</a></div>
</div>
