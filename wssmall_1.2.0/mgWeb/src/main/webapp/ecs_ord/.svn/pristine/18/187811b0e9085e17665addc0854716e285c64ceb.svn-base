<%@ page contentType="text/html;charset=UTF-8"%>
<form id="newForm">
<table width="100%" border="0" cellSpacing="0" cellPadding="0" id="newQueue" >
<tr>
	<td style="text-align:center; width:6%;vertical-align:middle">
	<input name="queueManagevo.id" value="${queueManagevo.id}" type="hidden" class="ipt_new" />
	<a href="javascript:void(0);" name="single_cancle" class="dobtn" onclick="removeNew('${queueManagevo.id}')">取消</a></td>
	<td width="13%">
		<ul>
			<li style="padding-top:3px">队列编码：</li>
			<li style="padding-left: 20px">${queueManagevo.queue_no}</li>
			<li style="padding-top:: 3px">队列描述：</li>
			<li><input name="queueManagevo.queue_desc" value="${queueManagevo.queue_desc}" type="text" class="ipt_new" /></li>
			<li style="padding-top:: 3px">最大写卡机数量：</li>
			<li><input name="queueManagevo.avail_card_mac_num"  value="${queueManagevo.avail_card_mac_num}" type="text" class="ipt_new" maxlength="8"/></li>
		</ul>
	</td>
	<td width="22%">
		<ul>
			<li style="padding-top:: 3px">开户系数：</li>
			<li><input name="queueManagevo.open_coeff"  value="${queueManagevo.open_coeff}" type="text" class="ipt_new" maxlength="8"/></li>
			<li style="color: red">开户系数必须在0-${queueManagevo.max_open_num}</li>
			<li style="color: red">队列最大自动开户数&lt;开户系数*队列写卡机数</li>
		</ul>
	</td>
	<td width="19%">
		<ul>
			<li style="padding-top:: 3px">队列关闭条件（开户失败）：</li>
			<li><input name="queueManagevo.open_max_failed_num"  value="${queueManagevo.open_max_failed_num}" type="text" class="ipt_new" maxlength="8"/></li>
			<li style="padding-top:: 3px">当前连续失败数量：</li>
			<li>${queueManagevo.conti_open_failed_num}</li>
		</ul>
	</td>
	<td width="17%">
		<ul>
			<li style="padding-top:: 3px">队列关闭条件（写卡失败）：</li>
			<li><input name="queueManagevo.card_max_failed_num"  value="${queueManagevo.card_max_failed_num}" type="text" class="ipt_new" maxlength="8"/></li>
			<li style="padding-top:: 3px">当前连续失败数量：</li>
			<li>${queueManagevo.conti_card_failed_num}</li>
		</ul>
	</td>
	<td width="14%">
		<ul>
			<li style="padding-top:: 3px">状态：</li>
			<li>
			<select name="queueManagevo.queue_switch" id="queue_switch_edit"  class="ipt_new" style="width:150px;">
             	<option value="" ${queueManagevo.queue_switch==''?'selected':'' }>--请选择--</option>
             	<option value="0" ${queueManagevo.queue_switch=='0'?'selected':'' } >开启</option>
             	<option value="1" ${queueManagevo.queue_switch=='1'?'selected':'' }>关闭</option>
			</select></li>
			<li style="padding-top:: 3px">关闭原因：</li>
			<li><input name="queueManagevo.deal_reason"  value="${queueManagevo.deal_reason}" id="deal_reason_edit" type="text" class="ipt_new"  maxlength="100"/></li>
		</ul>
	</td>
	<td width="8%"><a href="javascript:void(0);" name="single_save" class="dobtn" onclick="saveSigle()">保存</a></td>
</tr>
</table>
</form>