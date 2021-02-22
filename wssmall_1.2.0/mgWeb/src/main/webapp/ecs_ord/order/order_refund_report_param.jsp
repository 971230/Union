<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.searchformDiv table th {
	width: 90px;
}
</style>
<div class="searchBx">
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
             
             <tr>
                 <th>处理日期：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	                 &nbsp;
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		             &nbsp;
		             <input class="comBtn" type="button" name="excel" id="excelOrd" value="导出" style="margin-right:10px;"/>
	                
                   </td>
             </tr>
             </tbody>
            </table>
  </div>

       