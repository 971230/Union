<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/ecs_ord/common/jslibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath() %>/ecs_ord/order/print_templates/css/n_order.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css" media="print">
	#printButton{display: none;}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/order/print_templates/js/receipt.js"></script>
<title>业务受理单</title>
<style type="text/css">
    <!--
    .idcard {
        height: 432px;
        width: 340px;
        font-size: 12px;
        font-family: Arial, Helvetica, sans-serif;
        font-weight: bold;
        margin-right: auto;
        margin-left: auto;
    }
    .idcard #idnumber {
        font-size: 16px;
        font-weight: bold;
        font-family: Arial, Helvetica, sans-serif;
    }
    -->
    </style>

  </head>
  <body>
    <div id="receiptDivId">

  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px">
    <tr height="36px">
      <td width="884px" colspan="5" rowspan="2" align="center" bgcolor="#FFFFFF" class="titlebox" height="19px">
        <div style="display:inline; width:85px; height:56pxpx; float:left;">
        </div>
        <div style="display:inline;"> 
          <div class="titleword" style="margin-top:20px;">中国联合网络通信有限公司<u><span id="provinceId"></span></u>分公司客户入网登记单/业务受理单（□移动 □固网）</div>
          <div style="text-align:center;font-size:14px; display:inline; margin-left:300px; font-weight:bold;">
            <input name="" type="checkbox" value="" disabled="disabled"/>号码携带用户
          </div>
          <div style="text-align:center; margin-left:100px; font-size:14px; display:inline;font-weight:bold;">受理编号：
            <span></span>
          </div>
        </div>
      </td>
    </tr>
    
    <tr height="19px"></tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px">
    <tr >
      <td bgcolor="#FFFFFF" class="firsttitle">客户基本信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle" >客户名称</td>
      <td bgcolor="#FFFFFF" width="257px;" id="custName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">客户类型</td>
      <td bgcolor="#FFFFFF" width="317px;" id="custType"/>
    </tr>
    
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">证件类型</td>
      <td bgcolor="#FFFFFF" id="paperType"/>
      <td bgcolor="#FFFFFF" class="secondtitle">证件号码</td>
      <td bgcolor="#FFFFFF" id="paperNo"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF"  class="secondtitle" style="vertical-align:text-top;">证件有效期</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="paperExpr"/>
      <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align:text-top;">证件地址</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="paperAddr"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF"  class="secondtitle" style="vertical-align:text-top;">联系人</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="contactMan" />
      <td bgcolor="#FFFFFF"  class="secondtitle" style="vertical-align:text-top;">联系电话</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="contactPhone"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF"  class="secondtitle" style="vertical-align:text-top;">通信地址</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="contactAddr" />
      <td bgcolor="#FFFFFF"  class="secondtitle" style="vertical-align:text-top;">电子邮箱</td>
      <td bgcolor="#FFFFFF" style="vertical-align:text-top;" id="emailAddr"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="border-top:#000000 1px solid;">
    <tr>
      <td bgcolor="#FFFFFF" class="firsttitle">账户信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle" >账户名称</td>
      <td bgcolor="#FFFFFF" width="257px;" id="acctName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">付费方式</td>
      <td bgcolor="#FFFFFF" width="317px;" id="payMethod"/>
    </tr>
    <tr height="25px" style="display:none;" id="bankOne">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle" >托收银行名称</td>
      <td bgcolor="#FFFFFF" width="257px;" id="bankName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">银行代码</td>
      <td bgcolor="#FFFFFF" width="317px;" id="bankCode"/>
    </tr>
    <tr height="25px" style="display:none;" id="bankTwo">
      <td bgcolor="#FFFFFF" width="65px;"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle" >银行户名</td>
      <td bgcolor="#FFFFFF" width="257px;" id="bankAcctName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">银行账号</td>
      <td bgcolor="#FFFFFF" width="317px;" id="bankAcct"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="border-top:#000000 1px solid;">
    <tr>
      <td bgcolor="#FFFFFF" class="firsttitle">业务受理信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px">
    <tr>
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" width="190px;" class="secondtitle">用户类型</td>
      <td bgcolor="#FFFFFF" width="257px;" id="userType" />
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">用户号码</td>
      <td bgcolor="#FFFFFF" width="317px;" id="userNo"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4"></td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">套餐信息</td>
      <td bgcolor="#FFFFFF" colspan="3" id="mainContentOne"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4"></td>
    </tr>
    <tr style="display:none;" id="contentOne">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">终端信息</td>
      <td bgcolor="#FFFFFF" colspan="3" id="mainContentThree"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4"></td>
    </tr>
    <tr style="display:none;" id="contentTwo">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">优惠活动信息</td>
      <td bgcolor="#FFFFFF" colspan="3" id="mainContentFour"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4"></td>
    </tr>
    <tr style="display:none;" id="contentThree">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">靓号信息</td>
      <td bgcolor="#FFFFFF" colspan="3" id="mainContentFive"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4"></td>
    </tr>
    <tr>
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">基本业务功能</td>
      <td bgcolor="#FFFFFF" colspan="3" id="mainContentTwo"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="border-top:#000000 1px solid;display:none;" id='warmTable'>
    <tr height="50px">
      <td bgcolor="#FFFFFF" class="firsttitle">温馨提示：</td>
      <td bgcolor="#FFFFFF" colspan="3" id="warmTips"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="border-top:#000000 1px solid;display:none;" id="agentTwo">
    <tr>
      <td bgcolor="#FFFFFF" class="firsttitle">代理信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="display:none;" id="agentOne">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" width="190px;" class="secondtitle">代理人姓名</td>
      <td bgcolor="#FFFFFF" width="257px;" id="agentName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">代理人联系电话</td>
      <td bgcolor="#FFFFFF" width="317px;" id="agentPhone"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">代理人证件类型</td>
      <td bgcolor="#FFFFFF" id="agentPaperType"/>
      <td bgcolor="#FFFFFF" class="secondtitle">代理人证件号码</td>
      <td bgcolor="#FFFFFF" id="agentPaperNo"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="border-top:#000000 1px solid;display:none;" id="assureTwo">
    <tr>
      <td bgcolor="#FFFFFF" class="firsttitle">担保信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" style="display:none;" id="assureOne">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" width="190px;" class="secondtitle">担保人姓名</td>
      <td bgcolor="#FFFFFF" width="257px;" id="assureName"/>
      <td bgcolor="#FFFFFF" width="140px;" class="secondtitle">担保人联系电话</td>
      <td bgcolor="#FFFFFF" width="317px;" id="assurePhone"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">担保人证件类型</td>
      <td bgcolor="#FFFFFF" id="assurePaperType"/>
      <td bgcolor="#FFFFFF" class="secondtitle">担保人证件号码</td>
      <td bgcolor="#FFFFFF" id="assurePaperNo"/>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px"  style=" border-top:1px solid #000000; border-bottom:solid 1px #000000;">
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" colspan="4">本人证实上述资料属实,并已阅读及同意本登记表内容和背面所载之协议。</td>
    </tr>
  </table>
  
  <table  cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="884px" >
    <tr height="20px">
      <td bgcolor="#FFFFFF" width="15px;">&nbsp;</td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="450px">客户签字（盖章）</td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="190px">受理单位（盖章）</td>
      <td bgcolor="#FFFFFF" width="317px;"></td>
    </tr>
    <tr height="20px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" width="450px">代理人签字（盖章）</td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="190px">受理人及工号</td>
      <td bgcolor="#FFFFFF" width="317px;">${bss_operator }</td>
    </tr>
    <tr height="20px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle" width="450px">担保人签字（盖章）</td>
      <td bgcolor="#FFFFFF" class="secondtitle" width="190px"></td>
      <td bgcolor="#FFFFFF" width="317px;" id="staffInfo"></td>
    </tr>
    <tr height="20px">
      <td bgcolor="#FFFFFF" width="15px;" rowspan="2"/>
      <td height="38" rowspan="2" bgcolor="#FFFFFF" style="text-align:right;font-size:14px" width="412px" ><br />
        <span id="year1Id"></span>年<span id="month1Id"></span>月<span id="day1Id"></span>日
      </td>
      <td rowspan="2" bgcolor="#FFFFFF" style="text-align:right;font-size:14px" colspan="2" ><br />
        <span id="year2Id"></span>年<span id="month2Id"></span>月<span id="day2Id"></span>日
      </td>
    </tr>
    
    <tr height="25"></tr>
    
    <tr height="70px">
      <td colspan="4" bgcolor="#FFFFFF" class="wordMid" style="font-weight:bold;">微型USIM卡需要终端支持，如果您将该卡放入不支持微型USIM卡的终端使用，由此造成的风险及损失将由您自行承担</td>
    </tr>
    <tr height="70px">
      <td colspan="4" bgcolor="#FFFFFF" class="wordMid" style="font-weight:bold;">执行机卡比对政策，详见业务协议。</td>
    </tr>
    
     <tr style="display:none;" id="idcard">
      <td colspan="4" style="background:#fff;">
        <div class="idcard">
          <div id="pic"   style="position:absolute;margin-left:210px;margin-top:30px;width:102;height:126;" >
            <img id="picImage" width="102" height="126"  src=""/>
          </div>
          <div id="name" style="position:absolute;margin-left:60px;margin-top:26px"></div>
          <div id="sex" style="position:absolute;margin-left:60px;margin-top:52px"></div>
          <div id="national" style="position:absolute;margin-left:135px;margin-top:52px"></div>
          <div id="yy" style="position:absolute;margin-left:60px;margin-top:77px"></div>
          <div id="mm" style="position:absolute;margin-left:113px;margin-top:77px"></div>
          <div id="dd" style="position:absolute;margin-left:145px;margin-top:77px"></div>
          <div id="address" style="position:absolute;margin-left:60px;margin-top:105px;width:140px;height:50px;" ></div>
          <div id="idnumber" style="position:absolute;margin-left:105px;margin-top:172px"></div>
          <div id="organ" style="position:absolute;margin-left:130px;margin-top:367px"></div>
          <div id="validity" style="position:absolute;margin-left:130px;margin-top:394px"></div>
        </div>
      </td>
    </tr>
    
    <tr height="70px">
      <td colspan="4" bgcolor="#FFFFFF" class="wordMid" style="font-weight:bold;">身份证复印件粘贴处</td>
    </tr>
    <tr height="25" >
      <td height="25" colspan="4" bgcolor="#FFFFFF" class="wordMidtwo"><span style="text-decoration:underline">客服热线：10010  充值专线:10011</span><br />
      登陆中国联通网上营业厅www.10010.com,查询、交费、办业务，方便又实惠</td>
    </tr>
  </table>

<div align="center">
<input id="order_is_his" type="hidden" value="${order_is_his}"/>	
	<input id="order_id" type="hidden" value="${order_id}"/>	 	
    <input id="printButton" value="打印" type="button" style="width: 50px;" class="sbtn" onclick="doPrint();">
</div>
<script type="text/javascript">
var retInfo = ${retInfo};
        var payMethod=retInfo.payMethod;
        var mainContentThree=retInfo.mainContentThree;
        var mainContentFour=retInfo.mainContentFour;
        var mainContentFive=retInfo.mainContentFive;
        var agentName=retInfo.agentName;
        var assureName=retInfo.assureName;
        var warmTips=retInfo.warmTips;
        if(mainContentThree!=undefined && mainContentThree!=""){
            $("#contentOne").show();
        }
        if(mainContentFour!=undefined && mainContentFour!=""){
            $("#contentTwo").show();
        }
        if(mainContentFive!=undefined && mainContentFive!=""){
            $("#contentThree").show();
        }
        if(payMethod =="信用卡" || payMethod =="托收" || payMethod =="银行代扣"){
            $("#bankOne").show();
            $("#bankTwo").show();
        }
        if(agentName!=undefined && agentName!=""){
             $("#agentOne").show();
            $("#agentTwo").show();
        }
        if(assureName!=undefined && assureName!=""){
             $("#assureOne").show();
            $("#assureTwo").show();
        }
        if(warmTips!=undefined && warmTips!=""){
             $("#warmTable").show();
        }
putData(retInfo);
</script>
</body>

</html>