<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>队列开户写卡状态</title>
</head>
<body>
	<table class="blueTable" width="78%" style="margin-top: 10px;margin-left: 5px">
		<tr height="50" >
			<td width="40%" style="padding-top: 10px">队列信息</td>
			<td width="20%" style="padding-top: 10px">待开户订单</td>
			<td width="20%" style="padding-top: 10px">待写卡订单</td>
		</tr>
		<c:forEach var="each_queue" items="${list}">
			<tr>
				<td>
					<fieldset>
						<legend>队列编码：${each_queue.queueVo.queue_no}
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;队列状态：<html:dictCode2Name attr_code="DC_QUEUE_STATUS" pkey="${each_queue.queueVo.queue_switch}"></html:dictCode2Name>
						</legend>
						<table class="newQuick" name="each_station">
							<tr>
								<th>写卡机编码</th>
								<c:forEach var="machine" items="${each_queue.queueWriteCardList }" varStatus="status">
									<td>${machine.card_mate_code }</td>
								</c:forEach>
								<c:if test="${each_queue.queue_write_card_num==0 }"><td>无</td></c:if>
							</tr>
							<tr>
								<th>写卡机状态</th>
								<c:forEach var="machine" items="${each_queue.queueWriteCardList }">
									<td><c:choose>
											<c:when test="${machine.card_mate_status=='1' }">正常</c:when>
											<c:when test="${machine.card_mate_status=='2' }">写卡中</c:when>
											<c:when test="${machine.card_mate_status=='3' }">空闲</c:when>
											<c:when test="${machine.card_mate_status=='4' }">异常</c:when>
										</c:choose></td>
								</c:forEach>
								<c:if test="${each_queue.queue_write_card_num==0 }"><td>无</td></c:if>
							</tr>
							<tr>
								<th name="writing_order">当前订单号</th>
								<c:forEach var="machine" items="${each_queue.queueWriteCardList }">
									<td name="writing_order">${machine.curr_order_id }</td>
								</c:forEach>
								<c:if test="${each_queue.queue_write_card_num==0 }"><td>无</td></c:if>
							</tr>
						</table>
					</fieldset>
				</td>
				<td>
					<fieldset>
						<legend>
							待开户订单(<font color="blue">${each_queue.waiting_open_num }</font>)：
						</legend>
						<textarea class="newQuick" style="height:80px;width: 220px"><c:forEach var="orderOpenAcc" items="${each_queue.boxesOpenAccList}">${orderOpenAcc.order_id},
</c:forEach>
						</textarea>
					</fieldset>
				</td>
				<td>
					<fieldset>
						<legend>
							待写卡订单(<font color="blue">${each_queue.waiting_write_card_num }</font>)：
						</legend>
						<textarea class="newQuick" style="height:80px;width: 220px"><c:forEach var="orderWtiteCard" items="${each_queue.boxesWriteCardList }">${orderWtiteCard.order_id},
</c:forEach>
						</textarea>
					</fieldset>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>