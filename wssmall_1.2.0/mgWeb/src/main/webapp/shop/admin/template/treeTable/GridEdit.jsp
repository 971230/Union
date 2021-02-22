<%@ page contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑表格数据</title>
</head>
<link href="css/gridTree.css" rel="stylesheet" type="text/css" />
<body>
<form id="form1" name="form1" method="post" action="">
<h3>可编辑的表格</h3>
  <table width="698" border="0" cellpadding="0" cellspacing="0" id="tabProduct"  class="tree_table">
    <tr>
      <td width="32" align="center" bgcolor="#EFEFEF" Name="Num"><input type="checkbox" name="checkbox" value="checkbox" /></td>
      <td width="186" bgcolor="#EFEFEF" Name="Num" EditType="TextBox">序号</td>
      <td width="152" bgcolor="#EFEFEF" Name="ProductName" EditType="DropDownList" DataItems="{text:'A',value:'a'},{text:'B',value:'b'},{text:'C',value:'c'},{text:'D',value:'d'}">商品名称</td>
      <td width="103" bgcolor="#EFEFEF" Name="Amount" EditType="TextBox">数量</td>
      <td width="103" bgcolor="#EFEFEF" Name="Price" EditType="TextBox">单价</td>
      <td width="120" bgcolor="#EFEFEF" Name="SumMoney" Expression="Amount*Price" Format="#,###.00">合计</td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input type="checkbox" name="checkbox2" value="checkbox" /></td>
      <td bgcolor="#FFFFFF">1</td>
      <td bgcolor="#FFFFFF" Value="c">C</td>
      <td bgcolor="#FFFFFF">0</td>
      <td bgcolor="#FFFFFF">0</td>
      <td bgcolor="#FFFFFF">0</td>
    </tr>
    <tr>
      <td align="center" bgcolor="#FFFFFF"><input type="checkbox" name="checkbox22" value="checkbox" /></td>
      <td bgcolor="#FFFFFF">2</td>
      <td bgcolor="#FFFFFF" Value="d">D</td>
      <td bgcolor="#FFFFFF">0</td>
      <td bgcolor="#FFFFFF">0</td>
      <td bgcolor="#FFFFFF">0</td>
    </tr>
  </table>
</form>

<script language="javascript" src="js/GridEdit.js"></script>
<script language="javascript">
	var tabProduct = document.getElementById("tabProduct");
	GridEdit.editTables(tabProduct);
</script>
</body>
</html>
