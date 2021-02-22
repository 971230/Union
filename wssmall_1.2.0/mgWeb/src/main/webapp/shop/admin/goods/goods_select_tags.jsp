<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listTags">
<form action="javascript:;" method="post" id="">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th>商品包名称</th>
	    <td>
		   <input type="text"  name="search_tag_name" id="search_tag_name" value='${search_tag_name}' style="width: 188px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td style="text-align:right;">
		 <input class="comBtn" type="submit" name="searchTagBtn" id="searchTagBtn" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
</form>	
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"><input  name="check" type="hidden" id="toggleChk" ></grid:cell>
                    <grid:cell>商品包名称</grid:cell>
                </grid:header>
                <grid:body item="tags">
                    <grid:cell><input type="radio"  name="tagsId" value="${tags.tag_id}" tag_id="${tags.tag_id}"
                       id="names" tag_name="${tags.tag_name}" /></grid:cell>
                    <grid:cell>${tags.tag_name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
	         <div style="text-align:center;margin-top:3px;"> 
	             <input name="btn" type="button" id="selTagsBtn" value="确定"  class="graybtn1" />
	         </div>
        </form>

</div>
<script type="text/javascript">
var selectTags= {
        init:function(){
            var me=this;
            $("#selTagsBtn").unbind("click").bind("click",function() {
		        me.addSelectTags();
	        });
            //单选框双击选择
        	$("#listTags").find(".gridbody tbody tr").live("dblclick",function(){
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		$(this).siblings("tr").removeClass("grid-table-row-selected");
            		$(this).addClass("grid-table-row-selected");
            		box.attr("checked",true);
            		me.addSelectTags();
            	}
        	});
            $("#searchTagBtn").unbind("click").bind("click",function() {
                me.searchBottonClks();
            });
        },
        addSelectTags:function(){
        	var checkedBox = $("#listTags input[name='tagsId']:checked");
        	var tag_ids = [];
        	var tag_names = [];
        	checkedBox.each(function(idx,item){
			   var tag_id = $(item).attr("tag_id");
			   var tag_name = $(item).attr("tag_name");
			   tag_ids.push(tag_id);
			   tag_names.push(tag_name);
	         });
        	 $("#tag_ids").val(tag_ids.join(","));
        	 $("#tag_names").val(tag_names.join(","))
			Eop.Dialog.close("tagsDialog");
        },
        searchBottonClks : function() {
        	var search_tag_name = $.trim($("#search_tag_name").val());
        	GoodsAddEdit.showSelectTags(search_tag_name);
    		
       }
}
selectTags.init();
</script>

