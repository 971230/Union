<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div class="goodsList" <c:if test="${type!='batch'}">id="listTags" </c:if><c:if test="${type=='batch'}">id="listTags1" </c:if>>
<form action="javascript:;" method="post" id="">
        <div class="searchformDiv">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
         <tbody>
        <tr>
        <th>条形码</th>
        <td>
           <input type="text"  name="search_sn" <c:if test="${type!='batch'}">id="search_sn"</c:if><c:if test="${type=='batch'}">id="search_sn1"</c:if> value='${search_sn}' style="width: 188px;" maxlength="60" class="ipttxt" /> 
        </td>
        <th></th> 
        <td style="text-align:right;">
         <input class="comBtn" type="submit" name="searchTagBtn" <c:if test="${type!='batch'}">id="searchTagBtn"</c:if><c:if test="${type=='batch'}">id="searchTagBtn1"</c:if> value="搜索" style="margin-right:5px;"/>
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
                    <grid:cell>条形码</grid:cell>
                </grid:header>
                <grid:body item="esTerminal">
                    <grid:cell><input type="radio" name="checked_sn" value="${esTerminal.sn}" sn="${esTerminal.sn}"/></grid:cell>
                    <grid:cell>${esTerminal.sn} <input type="hidden" name="sn" value="${esTerminal.sn}"></grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
             <div style="text-align:center;margin-top:3px;"> 
                 <input name="btn" type="button"  <c:if test="${type!='batch'}">id="selTagsBtn"</c:if><c:if test="${type=='batch'}">id="selTagsBtn1"</c:if>  value="确定"  class="graybtn1" />
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
            var checked = $("#listTags input[name='checked_sn']:checked").val();
            $("#goods_sn").val(checked);
            Eop.Dialog.close("tagsDialog");
        },
        searchBottonClks : function() {
            var search_sn = $.trim($("#search_sn").val());
            GoodsAddEdit.showSelectTags(search_sn);
            
       }
}
selectTags.init();
</script>

