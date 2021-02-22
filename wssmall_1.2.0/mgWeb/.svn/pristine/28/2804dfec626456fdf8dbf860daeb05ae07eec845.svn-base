<form id="typeForm">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody id="stypeList">
			    
			    <tr>
                    <td><div align="center"><b>类型名称</b></div></td> 
                </tr> 
             
			    <c:forEach var ="type" items="${typeList}">
                  <tr id="typeId_${type.type_id}">
                    <td width="20%">
                        <div align="center">${type.name}
                            <input type='hidden' name='types_id' type_name="${type.name}" types_id='${type.id}' value="${type.type_id}" />
                        </div>
                    </td>
                </tr> 
               </c:forEach>
                  <tr id="btnTr">
	               <td colspan='2'>
		            <div align="center"> 
		             <input type="button" id='selStypeBtn' class="graybtn1" value="确  定" />
		            </div>
	              </td>
	              </tr>
			</tbody>
			
		</table>
</form>