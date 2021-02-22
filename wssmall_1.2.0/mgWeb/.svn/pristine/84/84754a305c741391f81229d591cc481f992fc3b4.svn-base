<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<form action="javascript:;" method="post" id="choiceform1">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th></th>
	    <th>规则标识</th>
	    <td>
		   <input type="text"  name="rule_id" id="cres" value='${rule_id}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td>
		 <input class="comBtn" type="submit" name="searchBtn" id="serButton" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
</form>	
</form> 
        <form  id="gridform" class="grid">
            <grid:grid from="webpage" formId="choiceform1" ajax="yes">
                <grid:header>
                    <grid:cell width="50px"></grid:cell>
                    <grid:cell>规则标识</grid:cell>
                    <grid:cell>规则编码</grid:cell>
                    <grid:cell>规则名称</grid:cell>
                </grid:header>
                <grid:body item="rule">
                    <grid:cell><input type="radio"  name="rule_id" value="${rule.rule_id }" rule_name="${rule.rule_name }"/></grid:cell>
                    <grid:cell>${rule.rule_id}</grid:cell>
                    <grid:cell>${rule.rule_code}</grid:cell>
                    <grid:cell>${rule.rule_name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br>
            <div style="margin-left: auto;margin-right: auto;" align="center">
                <input name="btn" type="button" id="selRulesBtn" value="确定"  class="graybtn1" />
            </div>
        </form>
<script type="text/javascript">

var addGoods= {
        init:function(){
            var me=this;
            $("#selRulesBtn").unbind("click").bind("click",function() {
		      me. addSelectGoods();
	        });
            $("#serButton").bind("click",function() {
                me.searchBottonClks();
           });
        },
        addSelectGoods:function(){
        	var rule=$("input[name=rule_id][type=radio]:checked");
        	if(!rule || rule.length<1){
    			alert("请选择规则标识！");
    			return ;
    		}
        	var ruleId = rule.attr("value");
        	var ruleName = rule.attr("rule_name");
            var tr= "<tr >";
            tr +="<input type='hidden' name='ruleIdArray' value='"+ruleId+"'>";
            tr += "<td  style='text-align:center;' name='rule_id'><a name=\"edit_rule_a\" href=\"javascript:void(0);return false;\">"+ruleId+"</a></td>";
            tr += "<td  style='text-align:center;'>"+ruleName+"</td>";
	        tr += "<td style='text-align:center;display:none;'><input type='text'  class='ipttxt' name='activation_group' /></td>";
	        tr+="<td style='text-align:center;display:none;'><select name='auto_focus' class='ipttxt' style='text-align: left; width: 150px;'><option value='true'>true</option>"+
			"<option value='false'>false</option></select></td>";
	        tr += "<td style='text-align:center;'><input type='text' class='ipttxt'  name='priority' /></td>";
			tr += "<td style='text-align:center;'><a href='javascript:void(0);'  name='deletedes'>删除</a></td>";
            tr += "</tr>";
            $("#inputtext").append(tr); 
			Eop.Dialog.close("ruleDiv");
    },
 searchBottonClks : function() {
        var me = this;
        var options = {
                  url :'schemeAction!ruleList.do?ajax=yes',
                  type : "POST",
                  dataType : 'html',            
                  success : function(result) {  
                       $("#ruleDiv").empty().append(result);
                       me.init();
                  },
                  error : function(e,b) {
                       alert("出错啦:(");
                  }
        };
        $("#choiceform1").ajaxSubmit(options);
   }
}
addGoods.init();
</script>

