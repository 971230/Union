var iMouseStartX, iMouseStartY, imgTop, iMouseEndX, iMouseEndY;
var num = -1;
var outer = true;
var HotPic = {
	init : function(){
		var oDiv = document.getElementById("div1");
		var oShadow = oDiv.getElementsByTagName("span")[0];
		var oImg = oDiv.getElementsByTagName("img")[0];

		oDiv.style.height = oImg.offsetHeight + 'px';
		oDiv.style.width = oImg.offsetWidth + 'px';

		oShadow.style.position = 'absolute';
		oShadow.style.left = '0px';
		oShadow.style.top = '0px';
		oShadow.style.height = oImg.offsetHeight + 'px';
		oShadow.style.width = oImg.offsetWidth + 'px';
		oShadow.style.background = 'red';
		oShadow.style.opacity = 0;
		oShadow.style.filter = 'alpha(opacity:0)';
		oShadow.style.overflow = 'hidden';
		oDiv.onmousedown = startDrag;
	}
};
function startDrag(ev) {
	if (outer) {
		outer = false;
		var oEvent = ev || event;
		var mouse = mousePos(oEvent);
		var oDiv = document.getElementById("div1");
		
		iMouseStartX = mouse.x - oDiv.offsetLeft;
		iMouseStartY = mouse.y - oDiv.offsetTop;
		/* 这是要传递的参数 */
		// imgTop=
		var oDiv = document.getElementById("div1");
		var addDiv = document.createElement("div");
		addDiv.style.border = "3px solid red";

		addDiv.style.position = "absolute";
		addDiv.style.overflow = "visible";
		addDiv.innerHTML = '<span style="position:absolute;left:0px;top:0;z-index:3001;' + 
									'width:168px;height:107px;overflow:hidden;padding:25px 0 0 5px;' + 
									'background:url(/shop/admin/cms/mapper/image/moveDiv.png) 0 0 no-repeat;"> ' + 
								' <p>栏目名称：</p><input id="mapper_column_name"/>' + 
								' <p>栏目编码：</p><input id="mapper_column_id"/>' + 
								' <p>' + 
									' <input type="button"value="确定" onclick="argu(this)"/>' + 
									' <input type="button"value="取消" onclick="removeDlg(this)"/>' + 
								'</p>' + 
							'</span>';
		
		addDiv.style.left = iMouseStartX + "px";
		addDiv.style.top = iMouseStartY + "px";
		oDiv.appendChild(addDiv);
		var addDiv = document.createElement("div");
		if (oDiv.setCapture) {
			oDiv.onmousemove = doDrag;
			oDiv.onmouseup = stopDrag;
			oDiv.setCapture();
		} else {
			document.addEventListener("mousemove", doDrag, true);
			document.addEventListener("mouseup", stopDrag, true);
		}
	}
}
function removeDlg(obj) {
	var oDiv = document.getElementById("div1");
	oDiv.removeChild(obj.parentNode.parentNode.parentNode);
	outer = true;
}
function doDrag(ev) {
	var oEvent = ev || event;
	var mouse = mousePos(oEvent);
	var oDiv = document.getElementById("div1");
	var oImg = oDiv.getElementsByTagName("img")[0];
	var divNum = oDiv.getElementsByTagName("div");
	Width = mouse.x - iMouseStartX - oDiv.offsetLeft;
	Height = mouse.y - iMouseStartY - oDiv.offsetTop;
	if (Width < 0) {
		Width = 0;
	}
	if (mouse.x > oDiv.offsetLeft + oImg.offsetWidth) {
		Width = oImg.offsetWidth - iMouseStartX - 6;
	}
	if (Height < 0) {
		Height = 0;
	}
	if (mouse.y > oDiv.offsetTop + oImg.offsetHeight) {
		Height = oImg.offsetHeight - iMouseStartY - 6;
	}
	document.title = iMouseStartX + "|" + Width;
	num = oDiv.getElementsByTagName("div").length - 1;

	divNum[num].getElementsByTagName("span")[0].style.left = Width + 5 + "px";
	divNum[num].style.width = Width + "px";
	divNum[num].style.height = Height + "px";
}
function stopDrag(ev) {
	var oEvent = ev || event;
	var mouse = mousePos(oEvent);
	var oDiv = document.getElementById("div1");
	var oImg = oDiv.getElementsByTagName("img")[0];
	iMouseEndX = mouse.x - oDiv.offsetLeft;
	if (mouse.x > oDiv.offsetLeft + oImg.offsetWidth) {
		iMouseEndX = oImg.offsetWidth - 6;

	}
	iMouseEndY = mouse.y - oDiv.offsetTop;
	if (mouse.y > oDiv.offsetTop + oImg.offsetHeight) {
		iMouseEndY = oImg.offsetHeight - 6;

	}
	document.title = iMouseEndX + "|" + iMouseEndY;
	if (oDiv.releaseCapture) {
		oDiv.onmousemove = null;
		oDiv.onmouseup = null;
		oDiv.releaseCapture();
	} else {
		document.removeEventListener("mousemove", doDrag, true);
		document.removeEventListener("mouseup", stopDrag, true);
	}
}
function mousePos(e) {
	return {
		x : e.clientX
				+ (document.body.scrollLeft || document.documentElement.scrollLeft),
		y : e.clientY
				+ (document.body.scrollTop || document.documentElement.scrollTop)
	}
}
function argu(obj) {
	var columnName=document.getElementById("mapper_column_name");
	var columnId=document.getElementById("mapper_column_id");
	
	if(columnName.value == null || columnName.value == "" || columnName.value == "undefined"){
		alert("栏目名称不能为空");
		return false;
	}
	
	if(columnId.value == null || columnId.value == "" || columnId.value == "undefined"){
		alert("栏目编码不能为空");
		return false;
	}
	
	var oDiv = document.getElementById("div1");
	var args = iMouseStartX + "," + iMouseStartY + "," + iMouseEndX + ","
			+ iMouseEndY;
	oDiv.removeChild(obj.parentNode.parentNode.parentNode);
	outer = true;
	parent.MapperPic.addMapper(args,columnId.value, columnName.value);

}
function removeLi(obj) {
	var oUl = document.getElementById("list_ul");
	var oLiNum = oUl.getElementsByTagName("li");
	var infor_p = document.getElementById("argument");
	var oStr = infor_p.innerHTML.split("|");
	for ( var i = 0; i < oLiNum.length; i++) {
		if (oLiNum[i] == obj.parentNode) {
			oStr.splice(i, 1);

		}
	}
	infor_p.innerHTML = "";
	for ( var j = 0; j < oStr.length - 1; j++) {
		infor_p.innerHTML += oStr[j] + "|";
	}
	oUl.removeChild(obj.parentNode);

}
function photoShow(oLeft, oTop, oWidth, oHeight) {
	var oDiv2 = document.getElementById("div2");
	oDiv2.style.display = "block";
	oDiv2.style.border = "3px solid red";
	oDiv2.style.position = "absolute";
	oDiv2.style.left = oLeft + "px";
	oDiv2.style.top = oTop + "px";
	oDiv2.style.width = oWidth - oLeft + "px";
	oDiv2.style.height = oHeight - oTop + "px";
}
function photoHide() {
	var oDiv2 = document.getElementById("div2");
	oDiv2.style.display = "none";
}
$(function(){
	var pic_url = parent.document.getElementById("mapper_iframe").getAttribute("pic_url");
	$("#div1").find("img").eq(0).attr("src",pic_url);
	HotPic.init();
});