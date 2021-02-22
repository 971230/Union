<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<form id="buyWayForm">
    <div class="grid">
        <%--    <form action="goodsContractAction!query.do?ajax=yes" method="get">--%>
        <div class="searchformDiv">
            <div style="">&nbsp;&nbsp;&nbsp;&nbsp;
                名称:<input type="text" name="name" class="ipttxt" maxlength="30" value="${name}"/>
                <input type="button" id="searchBtn" class="comBtn" value="搜索">
            </div>
            <div style="clear:both"></div>
        </div>
        <%--    </form>--%>

        <form method="POST" id="gridform">
            <grid:grid from="webpage" ajax="yes">
                <grid:header>
                    <grid:cell  width="50px"><input type="checkbox" id="toggleChk" onChange="selectChange()" /></grid:cell>
                    <grid:cell>编码</grid:cell>
                    <grid:cell>名称</grid:cell>
                    <grid:cell>服务类型</grid:cell>
                </grid:header>
                <grid:body item="item">
                    <grid:cell><input type="checkbox" name="goodsid" value="${item.goods_id }" /></grid:cell>
                    <grid:cell>${item.goods_id}</grid:cell>
                    <grid:cell>${item.name}</grid:cell>
                    <grid:cell>${item.service_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
          
	          
        </form>
        <div style="clear:both;margin-top:5px;"></div>
        <div align="center" style="margin-top:5px;margin-bottom:5px;">
			<input type="hidden"  name="adminUser.paruserid" value="${currUserId }" >
			<input name="btn" type="button" value="确定"  style="margin-right:10px;" class="submitBtn comBtn" />
		</div>
    </div>
</form>
<script type="text/javascript">
    function selectChange(){
        $("#gridform").find("input[name=goodsid]").attr("checked",$("#toggleChk").is(":checked"));
    }
</script>