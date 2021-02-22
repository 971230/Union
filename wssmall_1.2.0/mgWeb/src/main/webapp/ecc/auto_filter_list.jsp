<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<div>
	<form action="/core/admin/ecc/checkFilter!showCheckFilterList.do" method="post" id="qryFrm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>业务类型：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.biz_name" /> 
	    	      </td>
	    	      <th>条件名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.attr_name" /> 
	    	      </td>
	    	      <th>条件编码：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="params.attr_code" /> 
	    	      </td>		
	    	      <th>状态：</th>
	    	      <td>
						<select class="ipttxt" name="params.status" style="width: 155px">
						<option value="">--请选择--</option>
						<option value="00A">生效</option>
						<option value="00X">失效</option>
						</select>
	    	      </td>
	    	      <th></th>
	    	      <td></td>
	    	      <th></th>
	    	      <td>
	    	          <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      </td> 
	    	      <td>
	    	      	<a href="/core/admin/ecc/checkFilter!toAdd.do"><input type="button" style="margin-right:10px;" class="comBtn" value="新&nbsp;增"  id="" name="button"></a>
	    	      </td>      
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>		
	</form>

    <form action="" id="filter_list_fm" class="grid">
				<grid:grid from="webpage" formId="aito_filter_f" >
					<grid:header>
						<grid:cell >业务类型</grid:cell>
						<grid:cell >序号</grid:cell>
						<grid:cell >条件名称</grid:cell>
						<grid:cell >条件编码</grid:cell>
						<grid:cell >条件值</grid:cell>
						<grid:cell >条件值说明</grid:cell>
						<grid:cell >条件关系</grid:cell>
						<grid:cell >状态</grid:cell>
						<grid:cell >操作</grid:cell>
					</grid:header>
					
					<grid:body item="filter">
						<grid:cell>${filter.biz_name}</grid:cell>
						<grid:cell>${filter.opt_index}</grid:cell>
						<grid:cell>${filter.attr_name}</grid:cell>
						<grid:cell>${filter.attr_code}</grid:cell>
						<grid:cell>${filter.z_value}</grid:cell>
						<grid:cell>${filter.z_cvalue}</grid:cell>
						<grid:cell>${filter.pre_log}</grid:cell>
						<grid:cell>${filter.status=='00A'?'生效':'失效'}</grid:cell>
						<grid:cell>
							<a href="/core/admin/ecc/checkFilter!showCheckFilterList.do?params.required_id=${filter.required_id}&params.type=qryEdit">操作</a>
						</grid:cell>						
					</grid:body>
				</grid:grid>
			</form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>



