<%@page import="com.ztesoft.net.mall.core.consts.Consts"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  method="post" id='custReturned_query_form' action='custReturned!list.do'>
  <div class="searchformDiv">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tbody><tr>
            <th>业务号码:&nbsp;&nbsp;</th>
            <td>
              <input type="text" class="ipttxt" attr='acc_nbr' name="custReturned.acc_nbr"  value='${custReturned.acc_nbr}' class="searchipt" />
            </td>
            <th>客户名称:&nbsp;&nbsp;</th>
            <td>
              <input type="text" class="ipttxt" attr='cust_name' name="custReturned.cust_name" value="${custReturned.cust_name}" class="searchipt" />
            </td>
            <th>开始时间:&nbsp;&nbsp;</th>
            <td>
              <input size="15" type="text"  name="custReturned.startTime" id="custReturned.startTime"
								value="${custReturned.startTime}"
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
            </td>
            <th>结束时间:&nbsp;&nbsp;</th>
            <td>
              <input size="15" type="text"  name="custReturned.endTime" id="custReturned.endTime"
              					value="${custReturned.endTime}"
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
            </td>
            <th>处理结果:&nbsp;&nbsp;</th>
            <td>
                <select name="custReturned.state" > 
                   <option value="">全部</option>
                   <option value="<%=Consts.ACCEPT_STATE_SUCC%>">成功</option>
                   <option value="<%=Consts.ACCEPT_STATE_FAIL%>">失败</option>
                </select>
            </td>            
            <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
 	</div>
</form>
<form id="gridform" class="grid">
	<grid:grid from="webpage" excel="yes" ajax="yes">
		<grid:header>
			<grid:cell sort="accept_id" style="width:100px;">受理单号</grid:cell>
			<grid:cell sort="cust_name" style="width:60px;">客户名称</grid:cell>
			<grid:cell sort="realname" style="width:80px;">代理商名称</grid:cell>
			<grid:cell sort="certi_type_name" style="width:100px;">证件类型</grid:cell>
			<grid:cell sort="certi_number" style="width:100px;">证件号码</grid:cell>
			<grid:cell sort="offer_name" style="width:100px;">销售品名称</grid:cell>
			<!--<grid:cell sort="crm_offer_id">CRM销售品id</grid:cell> -->
			<grid:cell sort="acc_nbr" style="width:100px;">业务号码</grid:cell>
			<grid:cell sort="terminal_code" style="width:100px;">终端串码</grid:cell>
			<grid:cell sort="status" style="width:100px;">处理结果</grid:cell>
			<grid:cell sort="status_date" style="width:100px;">返档时间</grid:cell>
			<grid:cell sort="comments" style="width:150px;">备注</grid:cell>
		</grid:header>
		<grid:body item="custReturned">
			<grid:cell>&nbsp;
				${custReturned.accept_id} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.cust_name} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.realname} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.certi_type_name}
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.certi_number}
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.offer_name} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.acc_nbr} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.terminal_code} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.state_name} 
			</grid:cell>
			<grid:cell>&nbsp;
				${custReturned.status_date} 
			</grid:cell>
			<grid:cell>&nbsp;
				<p title="${custReturned.comments}">${custReturned.s_comments} </p>
			</grid:cell>
		</grid:body>
	</grid:grid>
</form>
<script type="text/javascript">
   $("[name='custReturned.state']").val('${custReturned.state}');
</script>