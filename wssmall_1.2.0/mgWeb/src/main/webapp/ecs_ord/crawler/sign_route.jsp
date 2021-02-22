<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
 <!-- 发货单路由信息开始 -->
             <h3><a href="javascript:void(0);"></a>发货单路由信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	运单号 ：${logi_no } <span color="red">（${sign_status }）</span><!-- <a href="#" class="editBtn">编辑</a> --></h3> 
					 <div class="grid_n_cont_sub">
							<table width="60%" border="0" cellspacing="0" cellpadding="0"
								class="grid_a">
								<tr>
									<th>时间</th>
                                	<th>到达站点</th>
                                	<th>备注</th>
								</tr>
								<c:forEach var="route" items="${signRoute }">
								<tr>
									<td>${route.accept_time }</td>
									<td>${route.accept_address }</td>
									<td>${route.remark }</td>
								</tr> 
								</c:forEach>  
							</table>
						</div>
              <!-- 发货单路由信息结束 -->