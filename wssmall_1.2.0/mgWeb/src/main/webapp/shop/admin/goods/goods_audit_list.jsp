<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
    loadScript("js/GoodsAudit.js");

</script>
<style>
    #tagspan {
        position: absolute;
        display: none;
    }

    #agentspan {
        display: none;
        position: absolute;
    }

    #searchcbox {
        float: left
    }

    #searchcbox div {
        float: left;
        margin-left: 10px
    }
</style>
<div>
    <form action="goods!list_audit.do" id="searchAuditListForm" method="post">
        <div class="searchformDiv">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tbody>
                <tr>
                    <th>商品名称:</th>
                    <td><input type="text" class="ipttxt" style="width: 90px" name="goods.name" value="${goods.name}"
                               class="searchipt"/></td>
                    <th>商品编号:</th>
                    <td><input type="text" class="ipttxt" class="searchipt" style="width: 90px" name="goods.sn"
                               value="${goods.sn}"/></td>
                    <th>创建时间:</th>
                    <td>
                        <input size="15" type="text" name="goods.start_date" id="start_date"
                               value='${goods.start_date}'
                               readonly="readonly"
                               maxlength="60" class="dateinput ipttxt" dataType="date"/>
                        <font color="#666666">至</font>
                        <input size="15" type="text" name="goods.end_date" id="end_date"
                               value='${goods.end_date}'
                               readonly="readonly"
                               maxlength="60" class="dateinput ipttxt" dataType="date"/>
                    </td>
                    <!-- 
                    <td>
                        服务类型:<select class="ipttxt" name="goods.stype_id">
                        <option value="">--请选择--</option>
                        <c:forEach var="obj" items="${stypeList}">
                            <option ${goods.stype_id eq obj.stype_id ?'selected':''} value="${obj.stype_id}">${obj.name}</option>
                        </c:forEach>
                    </select>
                    </td> -->
                    <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"
                               id="button" name="button"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
    <br/>

    <form id="gridform" class="grid">
        <grid:grid from="webpage" formId="searchAuditListForm" action="goods!list_audit.do">
            <grid:header>
                <!--<grid:cell width="50px">
                <input type="checkbox" id="toggleChk" />
            </grid:cell>-->

                <grid:cell width="250px">商品名称</grid:cell>
                <grid:cell width="180px">商品编号</grid:cell>
                <grid:cell>销售价格</grid:cell>
                <grid:cell>发布人</grid:cell>
                <grid:cell>创建时间</grid:cell>
                <grid:cell width="50px">操作</grid:cell>
            </grid:header>
            <grid:body item="goods">
                <grid:cell>&nbsp;
                    <a href="goods!showGoodsMsg.do?goods_id=${goods.goods_id}">${goods.name }</a>
                </grid:cell>
                <grid:cell>&nbsp;${goods.sn} </grid:cell>
                <grid:cell>&nbsp;
                    <fmt:formatNumber value="${goods.price}" type="currency" pattern="￥.00"/>
                </grid:cell>
                <grid:cell>${goods.apply_username}</grid:cell>
                <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat>
                </grid:cell>
                <grid:cell>
                    <a href="javascript:;" class="auditmodify" goodsid='${goods.goods_id }'
                       lanid='${goods.lan_id}'>处理</a>
                </grid:cell>

            </grid:body>

        </grid:grid>
    </form>
    <!-- 审核页面 -->
    <div id="audit_w_dialog">
    </div>
    <!-- 通过的商品发布区域页面 -->
    <div id="auditAreaOk">
    </div>
    <div style="clear: both; padding-top: 5px"></div>
</div>


<script type="text/javascript">

    $(function () {
        $.ajax({
            url: basePath + 'goods!getCatTree.do?ajax=yes',
            type: "get",
            dataType: "html",
            success: function (html) {
                var serachCatSel = $(html).appendTo("#searchCat");
                serachCatSel.removeClass("editinput").attr("name", "catid");
                serachCatSel.children(":first").before("<option value=\"\" selected>全部类别</option>");
                <c:if test="${catid!=null}">serachCatSel.val(${catid})
                </c:if>
            },
            error: function () {
                alert("获取分类树出错");
            }
        });

        $("#tag_chek").click(function () {
            if (this.checked)
                $("#tagspan").show();
            else {
                $("#tagspan").hide();
                $("#tagsel option").attr("selected", false);
            }
        });

        $("#agent_chek").click(function () {
            if (this.checked)
                $("#agentspan").show();
            else {
                $("#agentspan").hide();
                $("#agentsel option").attr("selected", false);
            }
        });
    });

</script>
