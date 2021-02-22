<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
  <div class="grid" id="contractSel">
        <form method="POST" id="gridform">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell>
                    <grid:cell>编码</grid:cell>
                    <grid:cell>名称</grid:cell>
                   
                </grid:header>
                <grid:body item="item">
                    <grid:cell><input type="checkbox" name="goodsid" value="${item.goods_id }" goods_name="${item.name }" /></grid:cell>
                    <grid:cell>${item.goods_id}</grid:cell>
                    <grid:cell>${item.name}</grid:cell>
                  
                </grid:body>
            </grid:grid>
            </br>
            
            <div style="margin-top:10px;margin-bottom:5px;" align="center">
                <input name="btn" type="button" id="selContractBtn" value="确定"  class="graybtn1" />
            </div>
            
        </form>
   
</div>

<script type="text/javascript">
    function selectChange(){
        $("#gridform").find("input[name=goodsid]").attr("checked",$("#toggleChk").is(":checked"));
    }
</script>