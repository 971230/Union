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
<title>免填单</title>
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
      <div style="margin-top: 78px; height: 643px; font-style: normal">
        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">客户基本信息：</td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">客户名称</td>
            <td bgcolor="#FFFFFF" width="212px;" id="custName"/>
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">客户类型</td>
            <td bgcolor="#FFFFFF" width="212px;" id="custType">公众</td>
          </tr>

          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">证件类型</td>
            <td bgcolor="#FFFFFF" width="212px;" id="paperType"/>
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">证件号码</td>
            <td bgcolor="#FFFFFF" width="212px;" id="paperNo"/>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">证件有效期</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="paperExpr"/>
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">证件地址</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="paperAddr"/>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">联系人</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="contactMan" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">联系电话</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="contactPhone"/>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">通信地址</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="contactAddr" />
            <td bgcolor="#FFFFFF" class="secondtitle" style="vertical-align: text-top;">电子邮箱</td>
            <td bgcolor="#FFFFFF" style="vertical-align: text-top;" id="emailAddr" />
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid;">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">账户信息：</td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">账户名称</td>
            <td bgcolor="#FFFFFF" width="212px;" id="acctName" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">付费方式</td>
            <td bgcolor="#FFFFFF" width="212px;" id="payMethod"/>
          </tr>
          <tr height="20px" style="display:none;" id="bankOne">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">托收银行名称</td>
            <td bgcolor="#FFFFFF" id="bankName" />
            <td bgcolor="#FFFFFF" class="secondtitle">银行代码</td>
            <td bgcolor="#FFFFFF" id="bankCode" />
          </tr>
          <tr height="30px" style="display:none;" id="bankTwo">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">银行户名</td>
            <td bgcolor="#FFFFFF" id="bankAcctName" />
            <td bgcolor="#FFFFFF" class="secondtitle">银行账号</td>
            <td bgcolor="#FFFFFF" id="bankAcct" />
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid;">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">业务受理信息：</td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr >
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">用户类型</td>
            <td bgcolor="#FFFFFF" width="212px;" id="userType" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">用户号码</td>
            <td bgcolor="#FFFFFF" width="212px;" id="userNo"/>
          </tr>
          
          <tr >
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" width="100px;">套餐信息</td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentOne" width="524px;"/>
          </tr>
          
          <tr style="display:none;" id="contentOne">
            <td bgcolor="#FFFFFF" width="15px;"/>
            <td bgcolor="#FFFFFF" class="secondtitle">终端信息</td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentThree" width="524px;"/>
           </tr>
           
           <tr style="display:none;" id="contentTwo">
            <td bgcolor="#FFFFFF" width="15px;"/>
            <td bgcolor="#FFFFFF" class="secondtitle">优惠活动信息</td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentFour" width="524px;"/>
           </tr>
           
           <tr style="display:none;" id="contentThree">
            <td bgcolor="#FFFFFF" width="15px;"/>
            <td bgcolor="#FFFFFF" class="secondtitle">靓号信息</td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentFive" width="524px;"/>
           </tr>
           
          <tr>
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" width="100px;">基本业务功能</td>
            <td bgcolor="#FFFFFF" colspan="3" id="mainContentTwo" width="524px;"/>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"
          style="border-top: #000000 1px solid; " id='warmTable' >
            <tr height="50px">
                <td bgcolor="#FFFFFF" width="15px;"/>
                <td bgcolor="#FFFFFF" class="secondtitle" width="100px;">温馨提示：</td>
                <td bgcolor="#FFFFFF" colspan="3" id="warmTips" width="524px;">${warmTips}</td>
            </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px" style="border-top:#000000 1px solid;display:none;" id="agentTwo">
          <tr>
            <td bgcolor="#FFFFFF" class="firsttitle">代理信息：</td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px" style="display:none;" id="agentOne">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">代理人姓名</td>
            <td bgcolor="#FFFFFF" width="212px;" id="agentName" />
            <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">代理人联系电话</td>
            <td bgcolor="#FFFFFF" width="212px;" id="agentPhone" />
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle">代理人证件类型</td>
            <td bgcolor="#FFFFFF" id="agentPaperType" />
            <td bgcolor="#FFFFFF" class="secondtitle">代理人证件号码</td>
            <td bgcolor="#FFFFFF" id="agentPaperNo" />
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px" style="border-top:#000000 1px solid;display:none;" id="assureTwo">
    <tr>
      <td bgcolor="#FFFFFF" class="firsttitle">担保信息：</td>
    </tr>
  </table>
  
  <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px" style="display:none;" id="assureOne">
    <tr height="20px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">担保人姓名</td>
      <td bgcolor="#FFFFFF" width="212px;" id="assureName"/>
      <td bgcolor="#FFFFFF" width="100px;" class="secondtitle">担保人联系电话</td>
      <td bgcolor="#FFFFFF" width="212px;" id="assurePhone"/>
    </tr>
    <tr height="25px">
      <td bgcolor="#FFFFFF" width="15px;"/>
      <td bgcolor="#FFFFFF" class="secondtitle">担保人证件类型</td>
      <td bgcolor="#FFFFFF" id="assurePaperType"/>
      <td bgcolor="#FFFFFF" class="secondtitle">担保人证件号码</td>
      <td bgcolor="#FFFFFF" id="assurePaperNo"/>
    </tr>
  </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"  style="border-top: 1px solid #000000; border-bottom: solid 1px #000000;">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" colspan="4" width="624">本人证实上述资料属实,并已阅读及同意本登记表内容和背面所载之协议。</td>
          </tr>
        </table>

        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;">
              &nbsp;
            </td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">客户签字（盖章）</td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">受理单位（盖章）</td>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;" />
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">代理人签字（盖章）</td>
            <td bgcolor="#FFFFFF" class="secondtitle" width="100px">受理人及工号</td>
            <td bgcolor="#FFFFFF" width="192px;">${bss_operator }</td>
          </tr>
          <tr height="20px">
            <td bgcolor="#FFFFFF" width="15px;"/>
            <td bgcolor="#FFFFFF" class="secondtitle" width="312px" colspan="2">担保人签字（盖章）</td>
            <td bgcolor="#FFFFFF" width="100px;"></td>
            <td bgcolor="#FFFFFF" width="212px;"/>
           </tr>
        </table>
        <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px">
          <tr height="40px">
            <td bgcolor="#FFFFFF" width="15px;" rowspan="2" />
            <td height="38" rowspan="2" bgcolor="#FFFFFF" style="text-align: right; font-size: 13px" width="200px" colspan="2">
              <br />
              <span id="year1Id"></span>年<span id="month1Id"></span>月<span id="day1Id"></span>日
            </td>
            <td rowspan="2" bgcolor="#FFFFFF" style="text-align: right; font-size: 13px" colspan="2" width="200px;">
              <br />
              <span id="year2Id"></span>年<span id="month2Id"></span>月<span id="day2Id"></span>日
            </td>
          </tr>
        </table>
         <table cellpadding="0" cellspacing="0" bgcolor="#000000" border="0px;" width="639px"> 
          <tr height="20px">
            <td rowspan="1" bgcolor="#FFFFFF" style="text-align:center;font-weight:bold;font-size:12px;" colspan="4" width="639px;">微型USIM卡需要终端支持，如果您将该卡放入不支持微型USIM卡的终端使用，由此造成的风险及损失将由您自行承担</td>
          </tr>
          <tr height="40px">
            <td colspan="4" bgcolor="#FFFFFF" width="639px" style="text-align:center;font-weight:bold;font-size:12px;">执行机卡比对政策，详见业务协议。</td>
          </tr>
         <tr style="display:none;" id="blank" height="320px" ><td bgcolor="#FFFFFF">&nbsp;</td></tr>
          
        <tr style="display:none;" id="idcard">
            <td colspan="4" style="background:#fff;">
                <div class="idcard">
                  <div id="pic"   style="position:absolute;margin-left:210px;margin-top:30px;width:102;height:126;" >
                    <img id="picImage" width="102" height="126" />
                  </div>
                  <div id="name" style="position:absolute;margin-left:60px;margin-top:26px"></div>
                  <div id="sex" style="position:absolute;margin-left:60px;margin-top:52px"></div>
                  <div id="national" style="position:absolute;margin-left:135px;margin-top:52px"></div>
                  <div id="yy" style="position:absolute;margin-left:60px;margin-top:77px"></div>
                  <div id="mm" style="position:absolute;margin-left:113px;margin-top:77px"></div>
                  <div id="dd" style="position:absolute;margin-left:145px;margin-top:77px"></div>
                  <div id="address" 
                    style="position:absolute;margin-left:60px;margin-top:105px;width:140px;height:50px;" ></div>
                  <div id="idnumber" style="position:absolute;margin-left:105px;margin-top:172px"></div>
                  <div id="organ" style="position:absolute;margin-left:130px;margin-top:367px"></div>
                  <div id="validity" style="position:absolute;margin-left:130px;margin-top:394px"></div>
                </div>
            </td>
        </tr>
        </table>
      </div>
    </div>
    
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
        if(warmTips!=undefined && warmTips!=""){
             $("#warmTable").show();
        }
putData(retInfo);
</script>
</body>

</html>