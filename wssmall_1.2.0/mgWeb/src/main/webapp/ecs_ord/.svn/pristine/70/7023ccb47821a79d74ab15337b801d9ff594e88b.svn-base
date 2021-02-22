var RwCard = {};
var load_rwcard_acx = true;
var readerName ="";
var whiteCardOcx  =document.getElementById("Ocxtest"); 
var writeReDoTag;
var OldUSIMInfo ={};
var currStep;

  /**
   * 获取写卡器名称
   */
  
  RwCard.getReaderName = function() {
    var strReaderString = "";
    try {
      strReaderString = whiteCardOcx.ListCard(); // 列出所有写卡器
      if ((null == strReaderString) || ("" == strReaderString)) {
        alert("访问写卡器出错，请检查写卡器是否连接，白卡是否插入！");
    	//RwCard.process(currStep, 'err', "访问写卡器出错，请检查写卡器是否连接，白卡是否插入！");
        RwCard.rwButObj.removeAttr('disabled');
        return false;
      }
    } catch (e) {
      alert("访问写卡器出错，请检查写卡器是否连接，白卡是否插入！");
      
      //RwCard.rwButObj.removeAttr('disabled');
      return false;
    }

    var readList = strReaderString.split(";");

    var connetResult;
    var list = document.getElementById("cardList");
    
    for(var i = list.options.length-1; i >= 0; i--){
		list.remove(i);
	}
    // 尝试连接每个读卡器，并把所有成功中的最后一个读卡器的名称存到全局变量readerName中
    for ( var i = 0, l = readList.length; i < l; i++) {
      connetResult = whiteCardOcx.ConnectCard(readList[i]);
      if ("0" == connetResult) {// 成功
        readerName = readList[i];
       connetResult = whiteCardOcx.DisconnectCard(readerName); // 关闭
        var newOption = document.createElement("option");
		newOption.setAttribute("value", readList[i]);
		newOption.appendChild(document.createTextNode(readList[i]));
		list.appendChild(newOption);
        return true;
      }
    }
    return false;
  }

  /**
   * 连接写卡器
   */
  RwCard.ConnetReader = function() {
    var connetResult;
    connetResult = whiteCardOcx.ConnectCard(readerName);
    return connetResult;
  }

  /**
   * 断开写卡器
   */
  RwCard.DisConnetReader = function() {
    var DisconResult;
    DisconResult = whiteCardOcx.DisconnectCard(readerName);
    RwCard.readerTag = 0;
    return DisconResult;
  }

  /**
   * 执行写卡命令,一次一条命令
   */
  RwCard.TransmitCard = function(Command) {
    var TransmitResult;
    TransmitResult = whiteCardOcx.TransmitCard(Command, readerName);
    return TransmitResult;
  }
  /**
   * 读取白卡卡号
   */
  RwCard.queryUsimNo = function() {

    var result;

    // 读取白卡标识
    result = RwCard.TransmitCard('A0A40000023F00');

    result = RwCard.TransmitCard('A0A40000022FE2');

    result = RwCard.TransmitCard('A0B000000A');

    if (result.length != 36) {
      alert("查询ICCID标识错误！");
     // RwCard.process(currStep, 'err', "查询ICCID标识错误！");
      RwCard.DisConnetReader();
      RwCard.rwButObj.removeAttr('disabled');
      return -1;
    }

    var IccIdList = result.split(" ");
    var IccIdListNum = IccIdList.length;

    if (IccIdListNum != 13) { // 最后加1
      alert("获取ICCID位数错误，请重新插卡！");
     // RwCard.process(currStep, 'err', "获取ICCID位数错误，请重新插卡！");
      RwCard.DisConnetReader();
      RwCard.rwButObj.removeAttr('disabled');
      return -1;
    }
    if (IccIdList[11] != "00" || IccIdList[10] != "90") { // 状态9000为正常状态
      alert("获取ICCID状态出错！");
     // RwCard.process(currStep, 'err', "获取ICCID状态出错！");
      RwCard.DisConnetReader();
      RwCard.rwButObj.removeAttr('disabled');
      return -1;
    }
    var IccIdNo = "";
    for ( var i = 0; i < 10; i++) {
      IccIdNo = IccIdNo + IccIdList[i].substr(1, 1) + IccIdList[i].substr(0, 1);
    }
    return IccIdNo;
  }

  /**
   * 读取卡中是否存在IMSI
   */
  RwCard.queryCardImsi = function() {

    var imsiResult = "";
    imsiResult = RwCard.TransmitCard('A0A40000023F00');
    imsiResult = RwCard.TransmitCard('A0A40000027F20');
    imsiResult = RwCard.TransmitCard('A0A40000026F07');
    imsiResult = RwCard.TransmitCard('A0B0000009');
    imsiResult = RwCard.trim(imsiResult);

    if (imsiResult.length != 22) {
      RwCard.DisConnetReader();
      return -1;
    }

    var imsiResultPrefix = imsiResult.substr(0, 18);
    imsiResultPrefix = imsiResultPrefix.toUpperCase();

    var imsiResultSuffix = imsiResult.substr(18, 4);

    if ("FFFFFFFFFFFFFFFFFF" != imsiResultPrefix) {
      RwCard.DisConnetReader();
      return -1;
    }

    if ("9000" != imsiResultSuffix) {
      RwCard.DisConnetReader();
      return -1;
    }

    imsiResult = RwCard.TransmitCard('A0A40000023F00');
    imsiResult = RwCard.TransmitCard('A0A40000027FF0');
    imsiResult = RwCard.TransmitCard('A0C0000016');
    imsiResult = RwCard.TransmitCard('A0A40000026F07');
    imsiResult = RwCard.TransmitCard('A0B0000009');
    imsiResult = RwCard.trim(imsiResult);

    if (imsiResult.length != 22) {
      RwCard.DisConnetReader();
      return -1;
    }

    imsiResultPrefix = imsiResult.substr(0, 18);
    imsiResultPrefix = imsiResultPrefix.toUpperCase();
    imsiResultSuffix = imsiResult.substr(18, 4);

    if ("FFFFFFFFFFFFFFFFFF" != imsiResultPrefix) {
      RwCard.DisConnetReader();
      return -1;
    }

    if ("9000" != imsiResultSuffix) {
      RwCard.DisConnetReader();
      return -1;
    }
    return;
  }

  /**
   * 去掉所有的空格,这边不能用$.trim()
   */
  RwCard.trim = function(str) {
    return str.replace(/\s+/g, "");
  }

  /**
   * 初始化重复读卡
   */
  RwCard.initReReadCard = function() {
    RwCard.readerTag = 0;
    RwCard.usimNoInput.val("").removeAttr('disabled');
    currStep = 0;
    RwCard.rwButObj.removeAttr('disabled');
    RwCard.DisConnetReader();
  }

  /**
   * 处理单条写卡命令
   */
  RwCard.execOneOrder = function(commond) {
    var numResult = RwCard.TransmitCard(commond);
    if (numResult.substr(0, 2).toUpperCase() != "9F") {
      RwCard.TransmitCardErr($SimCardNo, numResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
  }

  /**
   * 写卡错误函数
   */
  RwCard.TransmitCardErr = function(IccId, ErrCode, ErrInfo) {

    // 关闭写卡组件
    if (RwCard.readerTag == 1) {
      var result = RwCard.DisConnetReader();

      // 关闭写卡组件,重置 整个页面
      if (result != "0") {
        RwCard.initReReadCard();
        alert("关闭写卡器连接失败！");
        //RwCard.process(currStep, 'err', "关闭写卡器连接失败！");
        return;
      }
    }
    alert("error=="+whiteCardOcx.GetErrMsg());
    //OldUSIMInfo.ReasonID = ErrCode.replace(/ /g, "");
    //OldUSIMInfo.ErrorComments = ErrInfo;

    //RwCard.process(currStep, 'err', ErrInfo);
    // 写卡失败后调用: 更新卡状态表, 记录错误信息
//    currStep = 5;
//    RwCard.writeCardFail();

  }

  /**
   * 写卡函数(用于写卡组件的集中调用，针对多行命令的处理 1.执行写卡脚本 2.写入IMSI 3.写入手机号码)
   */
  RwCard.insertCard = function(SimCardNo, Imsi, Option) {

    $SimCardNo = SimCardNo;

    // 确认读卡器连接是否正常
    if (!RwCard.getReaderName()) {
      return -1;
    }
    
    var result = whiteCardOcx.ConnectCard(readerName);
    if ("0" != result) {
      alert("读卡器连接异常，请检查后重新写卡！");
    	//RwCard.process(currStep, 'err', "读卡器连接异常，请检查后重新写卡！");
      RwCard.initReReadCard();
      return -1;
    }
    
    writeReDoTag++;// 写卡次数加1
    // 执行写卡脚本
    var option = Option;
  
    var optionList = option.split("!");
    var tempList, temp, tem;
    var commond, ret;
    var result;
   
    for ( var i = 1; i < optionList.length; i++) {
      temp = optionList[i];
      tem = temp.indexOf(",,");
      if (-1 == tem) {
        RwCard.TransmitCardErr(SimCardNo, "-1", "写卡脚本格式解析返回字符错误！");
        return -1;
      } else {
        ret = RwCard.trim(temp.substr(tem + 2));
      }
     
      temp = temp.substr(0, tem);
      tem = temp.indexOf(",");
      if (-1 == tem) {
        RwCard.TransmitCardErr(SimCardNo, "-1", "写卡脚本格式解析脚本命令错误！");
        return -1;
      } else {
        commond = temp.substr(0, tem);
      }
      result = RwCard.TransmitCard(commond); // 执行写卡脚本
      
      if (ret == "9FXX") {
        if (result.substr(0, 2).toUpperCase() != "9F") {
          RwCard.TransmitCardErr(SimCardNo, result, whiteCardOcx.GetErrMsg());
          return -1;
        }
      } else {
        var trueResult = "";
        tempList = result.split(" ");
        for ( var l = 0; l < tempList.length; l++) {
          trueResult = RwCard.trim(trueResult + tempList[l]);
        }
        if (trueResult != ret) {
          RwCard.TransmitCardErr(SimCardNo, result, whiteCardOcx.GetErrMsg());
          return -1;
        }
      }
    }
    
    // 加入IMSI
    var imsiCommond = "A0F4000012";
    var imsiString = "809" + Imsi;
    var imsiCode = "";
    var imsiNum_2 = imsiString.length / 2;
    for ( var i = 0; i < imsiNum_2; i++) {
      imsiCode = imsiCode + imsiString.substr(1, 1) + imsiString.substr(0, 1);
      imsiString = imsiString.substring(2);
    }
    imsiCommond = imsiCommond + imsiCode + imsiCode;
    result = RwCard.TransmitCard(imsiCommond); // 把IMSI写入卡中,错误码 如果不是 90 00

    if (result != "90 00 ") {
      RwCard.TransmitCardErr(SimCardNo, result, whiteCardOcx.GetErrMsg());
      return -1;
    }
   
    // 写入手机号码
    RwCard.execOneOrder('A0A40000023F00');
    RwCard.execOneOrder('A0A40000027F10');
    RwCard.execOneOrder('A0A40000026F40');

    var numCommond = "A0DC01041C";
    var numCodePre = "FFFFFFFFFFFFFFFFFFFFFFFFFFFF089168";
    var custNum =  $("#custNum").html();//获取页面号码//"18566441455";//OldUSIMInfo.NumID;

    if (custNum == null || custNum.length != 11) {
      RwCard.TransmitCardErr(SimCardNo, "-3", "手机号码错误");
      return -1;
    }
    custNum = custNum + "F";
    for ( var i = 0; i < custNum.length - 1; i = i + 2) {
      numCodePre = numCodePre + custNum.substr(i + 1, 1) + custNum.substr(i, 1);
    }
    
    var numCodeSuf = "FFFFFFFFFF";
    var numCode = numCodePre + numCodeSuf;
    numCommond = numCommond + numCode;
    numResult = RwCard.TransmitCard(numCommond);
    if (numResult != "90 00 ") {
      RwCard.TransmitCardErr(SimCardNo, numResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
   
    // 验证IMSI
    var imsiWriteResult = "";
    var imsiWriteResultPrefix;
    var imsiWriteResultSuffix;

    RwCard.execOneOrder('A0A40000023F00');
    RwCard.execOneOrder('A0A40000027F20');
    RwCard.execOneOrder('A0A40000026F07');

    imsiWriteResult = RwCard.TransmitCard('A0B0000009');
    imsiWriteResult = RwCard.trim(imsiWriteResult);
    if (imsiWriteResult.length != 22) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
   
    imsiWriteResultPrefix = imsiWriteResult.substr(0, 18);
    imsiWriteResultSuffix = imsiWriteResult.substr(18, 4);
    if ("9000" != imsiWriteResultSuffix) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
   
    if (imsiCode != imsiWriteResultPrefix) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    var imsiWriteResultTwo = "";
    var imsiWriteResultTwoPrefix;
    var imsiWriteResultTwoSuffix;

    RwCard.execOneOrder('A0A40000023F00');
    RwCard.execOneOrder('A0A40000027FF0');
    RwCard.execOneOrder('A0A40000026F07');
    
    imsiWriteResultTwo = RwCard.TransmitCard('A0B0000009');
    imsiWriteResultTwo = RwCard.trim(imsiWriteResultTwo);
    if (imsiWriteResultTwo.length != 22) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResultTwo, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    imsiWriteResultTwoPrefix = imsiWriteResultTwo.substr(0, 18);
    imsiWriteResultTwoSuffix = imsiWriteResultTwo.substr(18, 4);

    if ("9000" != imsiWriteResultTwoSuffix) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResultTwo, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    if (imsiCode != imsiWriteResultTwoPrefix) {
      RwCard.TransmitCardErr(SimCardNo, imsiWriteResultTwo, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    // 验证短信中心号码
    var smsWriteResult = "";
    var smsWriteResultPrefix;
    var smsWriteResultSuffix;
    
    RwCard.execOneOrder('A0A40000023F00');
    RwCard.execOneOrder('A0A40000027F10');
    RwCard.execOneOrder('A0A40000026F42');

    smsWriteResult = RwCard.TransmitCard('A0B2010428');
    smsWriteResult = RwCard.trim(smsWriteResult);
   
    if (typeof (commond) != "undefined" && commond.length == 90) {
      commond = commond.substr(10, 80);
      commond = commond.toUpperCase();
    } else {
      RwCard.TransmitCardErr(SimCardNo, "-2", "短信中心号码写入失败");
      return -1;
    }
    
    if (smsWriteResult.length != 84) {
      RwCard.TransmitCardErr(SimCardNo, smsWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    smsWriteResultPrefix = smsWriteResult.substr(0, 80);
    smsWriteResultPrefix = smsWriteResultPrefix.toUpperCase();
    smsWriteResultSuffix = smsWriteResult.substr(80, 4);
    if ("9000" != smsWriteResultSuffix) {
      RwCard.TransmitCardErr(SimCardNo, smsWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
    if (commond != smsWriteResultPrefix) {
      RwCard.TransmitCardErr(SimCardNo, smsWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    // 电话号码验证
    var numWriteResult = "";
    var numWriteResultPrefix;
    var numWriteResultSuffix;

    RwCard.execOneOrder('A0A40000023F00');
    RwCard.execOneOrder('A0A40000027F10');
    RwCard.execOneOrder('A0A40000026F40');
   
    numWriteResult = RwCard.TransmitCard('A0B201041C');
    numWriteResult = RwCard.trim(numWriteResult);
    numWriteResultPrefix = numWriteResult.substr(0, 56);
    numWriteResultPrefix = numWriteResultPrefix.toUpperCase();
    numWriteResultSuffix = numWriteResult.substr(56, 4);
    if ("9000" != numWriteResultSuffix) {
      RwCard.TransmitCardErr(SimCardNo, numWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
   
    if (numCode != numWriteResultPrefix) {
      RwCard.TransmitCardErr(SimCardNo, numWriteResult, whiteCardOcx.GetErrMsg());
      return -1;
    }
    
    // 写卡成功断开写卡器
    RwCard.DisConnetReader();
   
    return 0;
  }
  
  /**
   * 写卡进度
   */
  RwCard.process = function(step, succTag, expMsg) {
    if ('succ' == succTag) {
    	$(".step0"+step).empty().html("<i class='finish'></i>");
    } else if ('err' == succTag) {
    	$(".step0"+step).empty().html(expMsg);
    } else if('init'== succTag){// 初始化
    	$(".step0"+step).empty().html("正在"+expMsg+"...");
    }else if('clear'==succTag){
    	// 清除样式
    	$(".step01").empty().html(expMsg);
    	$(".step02").empty().html(expMsg);
    	$(".step03").empty().html(expMsg);
    	$(".step04").empty().html(expMsg);
    }
  };
 
