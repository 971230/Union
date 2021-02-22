<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>


<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected">
						<span class="word">银行帐户信息</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div style="display: block; height: 700px;" class="partner_detail">
	<div class="input">
		<form class="validate" method="post" action="" id='addform'
			validate="true">
			<table cellspacing="1" cellpadding="3" width="100%"
				class="form-table">

				<tr>

					<th>
						开户行类型：
					</th>
					<td>
						<html:selectdict name="partnerAccount.account_type"  curr_val="${partnerAccount.account_type}" attr_code="DC_BANK_TYPE"></html:selectdict>
					<td>
				</tr>
				<tr>
					<th>
						<label class="text">
							<span class='red'>* </span>开户行名称:
						</label>
					</th>
					<td>
						<input type="text" class="ipttxt" name="partnerAccount.account_name"
							id="shop_code" value="${partnerAccount.account_name}" maxlength="60"
							dataType="string" required="true" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="text">
							<span class='red'>* </span>开户行帐号:
						</label>
					</th>
					<td>
						<input type="text" class="ipttxt" name="partnerAccount.account_code"
							id="shop_name" value="${partnerAccount.account_code}" maxlength="60"
							dataType="string" required="true" />
					</td>
				</tr>
				<tr>
			</table>
			<div class="submitlist" align="center">
				<table>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="button" id="saveBtn" value=" 确定 " class="submitBtn"
								name='submitBtn' />
							<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<div id="refPartnerDlg"></div>
<script type="text/javascript"> 
$(function (){
	  $("#addform").validate();
      $("#saveBtn").click(function() {
			var url = ctx+ "/shop/admin/account!save_add.do?ajax=yes";
			Cmp.ajaxSubmit('addform', '', url, {}, function(responseText){
					if (responseText.result == 0) {
					   alert(responseText.message);
					   window.location=app_path+'/shop/admin/account!list_act.do';
					}
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					
			},'json');
		});
  })
</script>