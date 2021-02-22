<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/card_charge.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">充值卡充值</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
<div class="input">
	<form class="validate" method="post" action="" id='card_charge_form' validate="true">
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			<tr>
				<th>
					选择商品
				</th>
				<td>
					<select class="ipttxt" name="goodsList" id="goodsList"
						style="width: 150px;">
						<option value="">
							请选择
						</option>
						<c:forEach items="${goodsList}" var="goodsList">
							<option value="${goodsList.goods_id}_${goodsList.goods_price}">
								${goodsList.goods_name}
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						业务号码
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt"  name="accNbr" id="accNbr" maxlength="60"
						value="${accNbr}" dataType="string" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						充值金额
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt"  name="amount" id="amount" maxlength="60"
						value="${orderAmount}" dataType="int" required="true" />
				</td>
			</tr>
		</table>
		<div class="submitlist" align="center">
			<table>
				<tr>
					<td>
						<input type="button" value=" 充值 " class="submitBtn" name='submit' />
						<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>