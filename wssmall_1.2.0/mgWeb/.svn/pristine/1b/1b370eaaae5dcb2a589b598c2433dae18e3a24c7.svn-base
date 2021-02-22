<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
  body {
    background: url("../images/mainbg.gif") repeat-y scroll 0 0 rgba(0, 0, 0, 0);
    font-family: Arial,Helvetica,sans-serif;
    min-width:100%
   }
</style>


<form method="post" id="partserchform" action='<%=request.getContextPath()%>/core/admin/org/orgAdmin!partList.do?parent_party_id=${parent_party_id}'>
 <div class="searchformDiv"  >
 <table>
	<tr>
		<th>组织名称：</th>
		<td><input type="text" class="ipttxt"  name="org_name"  value="${org_name}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
    </tr>
  </table>
</div>
</form>
 <div class="grid" >
     <form method="POST"  id="partserchform" >
       <grid:grid from="webpage"  formId="partserchform" ajax="yes">
      <grid:header>
					  	<grid:cell width="30%">组织名称</grid:cell> 
					  	<grid:cell width="25%">组织类型</grid:cell> 
					  	<grid:cell width="15%">组织编码</grid:cell> 
					  	<grid:cell width="30%">组织简介</grid:cell> 
					  	
					</grid:header>
				    <grid:body item="obj">
					     <grid:cell>${obj.org_name}</grid:cell>
					     <grid:cell>${obj.org_type_name}</grid:cell>
					     <grid:cell>${obj.org_code}</grid:cell>
					     <grid:cell>${obj.org_content}</grid:cell>
					     <input type='hidden' name ="valueObj" parent_org_name="${obj.parent_org_name}" union_org_code="${obj.union_org_code}"  org_code ="${obj.org_code}" org_name="${obj.org_name}" org_type_name="${obj.org_type_name}" org_content= "${obj.org_content}" channel_type="${obj.channel_type}" parent_channel_type="${obj.parent_channel_type}" >
				  </grid:body>  
				</grid:grid>
</form>
</div>

<script type="text/javascript">
         
         $(".grid table tr").live("click",function(){
     	 $(".grid table tr").attr("class","");
         $(this).attr("class","clickClass");
         var obj = $(".clickClass [name='valueObj']");
         $("#parent_org_name", parent.document)
         $("#parent_org_name", parent.document).val(obj.attr("parent_org_name")); 
         $("#union_org_code", parent.document).val(obj.attr("union_org_code"));
         $("#org_code", parent.document).val(obj.attr("org_code"));
         $("#org_name", parent.document).val(obj.attr("org_name"));
         $("#org_type_name", parent.document).val(obj.attr("org_type_name"));
         $("#org_content", parent.document).val(obj.attr("org_content"));
         $("#channel_type", parent.document).val(obj.attr("parent_channel_type"));
         $("#channel_type_id", parent.document).val(obj.attr("channel_type"));
         
        });

</script>

