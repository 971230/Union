<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			
                 <c:forEach var="pro" items="${property}">
                    <tr id="complex_${pro.goods_id}">
                        <td>${pro.field_attr_id}</td>
                        <td>${pro.attr_spec_type}</td>
                        <td>${pro.field_name}</td>
                        <td>${pro.field_desc}</td>
                        <td><a href='javascript:;' name="edit" attr_spec_id="${pro.attr_spec_id}" field_attr_id="${pro.field_attr_id}">修改</a>&nbsp;&nbsp;<a href='javascript:;' name="delProperty" field_attr_id=${pro.field_attr_id}>删除</a></td>
                    </tr>
                  </c:forEach>
           

