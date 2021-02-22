<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Member.js"></script>
<div class="grid">
<form action="member!memberlist.do" method="post">
	
	<div class="searchformDiv">
		
		<div style="">&nbsp;&nbsp;&nbsp;&nbsp;用户名:<input type="text" class="ipttxt"  style="width:150px" name="uname" value="${uname }"/>&nbsp;&nbsp;&nbsp;&nbsp;姓名或公司名称:<input type="text" class="ipttxt"  style="width:150px" name="name" value="${name }"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="comBtn" value="搜索"></div>
		<div style="clear:both"></div>
	</div>
	<div class="comBtnDiv">
		<a href="member!add_member.do" style="margin-right:10px;" class="graybtn1" id="addMember" ><span>添加</span></a>
		<a href="javascript:;" id="delMBtn" style="margin-right:10px;" class="graybtn1" ><span>删除</span></a>
	</div>
</form>
<form method="POST" id="gridform">
<grid:grid  from="webpage" >

	<grid:header>
	<grid:cell ><input type="checkbox" id="toggleChk" /></grid:cell> 
	<grid:cell sort="uname">用户名</grid:cell> 
	<grid:cell sort="mobile">手机</grid:cell> 
	<grid:cell sort="lv_id">会员等级</grid:cell> 
	<grid:cell sort="email">电子邮件</grid:cell> 
	<grid:cell sort="regtime">注册时间</grid:cell> 
	<grid:cell>上次登录日期</grid:cell>
	<grid:cell>本月登录次数</grid:cell>
	<grid:cell sort="province|city">城市</grid:cell> 
	<grid:cell sort="sex">性别</grid:cell> 
	<grid:cell >登录</grid:cell> 
	<grid:cell>操作</grid:cell>
	</grid:header>

  <grid:body item="member">
	 <grid:cell><input type="checkbox" name="id" value="${member.member_id}" /></grid:cell>
     <grid:cell >${member.uname }</grid:cell>
     <grid:cell >${member.mobile} </grid:cell> 
     <grid:cell >${member.lv_name} </grid:cell>
     <grid:cell >${member.email} </grid:cell>
     <grid:cell ><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${member.regtime}"></html:dateformat> </grid:cell>
     <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${member.lastlogin}"/></grid:cell>
     <grid:cell>${member.logincount}</grid:cell>      
	<grid:cell>${member.province}-${member.city} </grid:cell>
    <grid:cell><c:if test="${member.sex==1}">男</c:if><c:if test="${member.sex==0}">女</c:if></grid:cell> 
    <grid:cell><a href="member!sysLogin.do?name=${member.uname }" target="_blank" />登录</a> </grid:cell>
    <grid:cell><a href="member!detail.do?member_id=${member.member_id}" ><img class="modify" src="images/transparent.gif" ></a></grid:cell>
  </grid:body>  
  
</grid:grid>  
</form>	
<DIV style="clear:both;margin-top:5px;"></DIV>

</div>


 
 

