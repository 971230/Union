<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody id="goodsBody">
			    
			    <tr>
                    <td><div align="center"><b>商品名称</b></div></td>
                    <td><div align="center"><b>操作</b></div></td>
                </tr> 
              <div >
			    <c:forEach var ="goods" items="${goodsList}">
                  <tr id="goodsId_${goods.goods_id}">
                    <td width="20%">
                        <div align="center">${goods.name}
                            <input type='hidden' name='goodsInfo' goods_name="${goods.name}" goods_id='${goods.goods_id}' value="${goods.goods_id}" />
                        </div>
                    </td>
                    <td width="20%"><div align="center"><a href='javascript:;' name='delContract'>删除</a></div></td>
                </tr> 
               </c:forEach>
              </div>
			</tbody>
			
		</table>