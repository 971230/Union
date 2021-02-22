
var uploadControls = {
	//uploadBtns:['spanButtonPlaceHolder','spanButtonPlaceHolder1'],
	uploadBtns:uploadBtns ||{},
	sel_upload_btn:null,
	init:function(){
		var me =this;
		for(var i=0;i<this.uploadBtns.length;i++){
			var uploadBtn = this.uploadBtns[i];
			
//			$("#"+uploadBtn).bind("click",function(){
//				//alert($("#SWFUpload_0").length);
//				//alert($("#SWFUpload_0").get(0).outerHTML)
//				//$("#SWFUpload_0").trigger("mousedown");
//				 SWFUpload_0.fireEvent("click")   
//				
//				//me.sel_upload_btn = uploadBtn;
//			})
			//照片处理逻辑
			initUpload(ctx,ext,uploadBtn['btnId'],uploadBtn['uploadProgressId']);
			//bindPhotoEvent();
		
		}
		$("[attr_name='place_div']").hover(function(){
			me['sel_upload_btn'] = $(this).attr("place_id");
		},function(){
		
		})
	},
	getImgId:function(sel_upload_btn){ //获取图片id
		var me =this;
		var sel_upload_btn = sel_upload_btn || me['sel_upload_btn'];
		for(var i=0;i<this.uploadBtns.length;i++){
			var uploadBtn = this.uploadBtns[i];
			if(uploadBtn['btnId'] == sel_upload_btn){
				return uploadBtn['showimgId'];
			}
		}
		return null;
	},
	imgfilePath:function(sel_upload_btn){ //获取图片文件路径
		var me =this;
		var sel_upload_btn = sel_upload_btn || me['sel_upload_btn'];
		for(var i=0;i<this.uploadBtns.length;i++){
			var uploadBtn = this.uploadBtns[i];
			if(uploadBtn['btnId'] == sel_upload_btn){
				return uploadBtn['imgfilePath'];
			}
		}
		return null;
	},
	uploadProgressId:function(sel_upload_btn){ //获取图片文件路径
		var me =this;
		var me =this;
		var sel_upload_btn = sel_upload_btn || me['sel_upload_btn'];
		for(var i=0;i<this.uploadBtns.length;i++){
			var uploadBtn = this.uploadBtns[i];
			if(uploadBtn['btnId'] == sel_upload_btn){
				return uploadBtn['uploadProgressId'];
			}
		}
		return null;
	},
	upload:function(){
	
	},
	del:function(){
	
	}
}
$(function(){
	uploadControls.init();
})
function FileProgress(c, e) {
	this.fileProgressID = c.id;
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		var b = document.createElement("div");
		b.className = "progressContainer";
		b.id = "container_" + this.fileProgressID;
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "proWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;
		var a = document.createElement("div"), d = document
				.createElement("div");
		a.className = "progressState";
		a.style.width = "0%";
		d.className = "progressText";
		this.fileProgressWrapper.appendChild(a);
		this.fileProgressWrapper.appendChild(d);
		a = document.createElement("div");
		d = document.createElement("A");
		d.href = "javascript:;";
		d.setAttribute("wrapper", b.id);
		d.className = "deleteBtn new";
		d.innerHTML = "&nbsp;";
		a.appendChild(d);
		b.appendChild(this.fileProgressWrapper);
		b.appendChild(a);
		document.getElementById(e).appendChild(b)
	}
}
FileProgress.prototype.setProgress = function(c) {
	this.fileProgressWrapper.childNodes[0].style.width = c + "%";
	this.fileProgressWrapper.childNodes[1].innerHTML = c + "%"
};
FileProgress.prototype.setStatus = function(c) {
	this.fileProgressWrapper.childNodes[1].innerHTML = c
};
FileProgress.prototype.setSuccess = function(c, e) {
	var b = document.createElement("A");
	b.href = "javascript:;";
	b.setAttribute("img", c);
	b.setAttribute("wrapper", e);
	var a = document.createElement("img");
	a.src = c;
	a.border = 0;
	a.className = "uploadImg";
	b.appendChild(a);
	a = document.createElement("input");
	a.type = "hidden";
	a.name = "picnames";
	a.id = "input_" + e;
	a.value = c;
	this.fileProgressWrapper
			.removeChild(this.fileProgressWrapper.childNodes[0]);
	this.fileProgressWrapper
			.removeChild(this.fileProgressWrapper.childNodes[0]);
	this.fileProgressWrapper.appendChild(b);
	this.fileProgressWrapper.appendChild(a)
};

// confirm("\u60a8\u5c1a\u672a\u4fdd\u5b58\u5546\u54c1\u6570\u636e\uff0c\u786e\u5b9a\u8981\u79bb\u5f00\u5417\uff1f")
function fileDialogComplete(a) {
	this.debug("-------\u9009\u62e9\u4e86\u4e0a\u4f20\u6587\u4ef6 " + a
			+ "\u4e2a");
	this.startUpload()
}
function uploadStart(a) {
	(new FileProgress(a, this.customSettings.progressTarget))
			.setStatus("\u4e0a\u4f20" + a.name);
	this.debug("-------\u4e0a\u4f20\u6587\u4ef6 " + a.name)
}
function uploadProgress(a, b, d) {
	b = Math.ceil(b / d * 100);
	(new FileProgress(a, this.customSettings.progressTarget)).setProgress(b);
	this.debug("-------\u6b63\u5728\u4e0a\u4f20\u6587\u4ef6 " + a.name
			+ " file id is " + a.id)
}
function uploadSuccess(a, b) {
	var d = b.split(",");
	(new FileProgress(a, this.customSettings.progressTarget)).setSuccess(d[1],
			a.id);
	var e = a.id;
	e.substring(e.length - 2, e.length) == "_0" && viewPic(d[0],uploadControls.sel_upload_btn);
	bindPhotoEvent(uploadControls.uploadProgressId(),uploadControls.sel_upload_btn);
	this.debug("-------\u4e0a\u4f20\u6587\u4ef6 " + a.name + " \u5b8c\u6210 "
			+ b)
}
function uploadComplete(a) {
	this.debug("-------\u4e0a\u4f20\u6587\u4ef6111111 " + a.name
			+ " \u5b8c\u6210 " + this.getStats().files_queued)
}
function bindPhotoEvent(context,sel_upload_btn) {
	var scope =$("body");
	if(context)
		scope =$("#"+context);
	$(".proWrapper>a",scope).unbind("click");
	$(".proWrapper>a",scope).click(function() {
				var a = $(this);
				viewPic(a.children("img").attr("src"),a.attr("sel_upload_btn"))
			}).attr("sel_upload_btn",sel_upload_btn);
	$(".progressContainer .deleteBtn",scope).unbind("click");
	$(".progressContainer .deleteBtn",scope).click(function() {
				deletePic($(this))
			}).attr("sel_upload_btn",sel_upload_btn);
}
function viewPic(a,sel_upload_btn) {
	var b = $("<img src='" + a + "' width='240' height='220' border=0 />");
	$("#"+uploadControls.getImgId(sel_upload_btn)).empty().append(b);
	$("#"+uploadControls.imgfilePath(sel_upload_btn)).val(a)
}
function deletePic(a) {
	var sel_upload_btn = a.attr("sel_upload_btn");
	var b = $("#"+uploadControls.getImgId(sel_upload_btn)), d = $("#"+uploadControls.getImgId(sel_upload_btn)+">img"), e = a
			.closest("div.progressContainer").find("img.uploadImg").attr("src");

	var url = ctx + "/shop/uploadPhoto!delPhoto." + ext;
	$.ajax({
		type : "get",
		url : url,
		data : "photoName=" + e + "&rdm=" + (new Date).getTime()+"&sel_upload_btn="+sel_upload_btn, // 设置模块对象
		dataType : "json",
		async : "false",// 同步操作
		success : function(c) {
			if (c.result == 0) {
				a.closest(".progressContainer").remove();
				if (d.attr("src") == e) {
					c = document.getElementById(uploadControls.uploadProgressId(sel_upload_btn))
							.getElementsByTagName("input");
					if (c.length > 0)
						viewPic(c[0].value,sel_upload_btn);
					else if (c.length == 0) {
						b
								.html('<p style="margin-top:100px">\u6b64\u5904\u663e\u793a\u5546\u54c1\u9875\u9ed8\u8ba4\u56fe\u7247<br/>[\u60a8\u8fd8\u672a\u4e0a\u4f20\u5546\u54c1\u56fe\u7247\uff01]</p>');
						document.getElementById(uploadControls.imgfilePath(sel_upload_btn)).value = ""
					}
				}
			} else
				alert(c.message)
		},
		error : function(c) {
			alert("\u8bf7\u6c42\u5931\u8d25" + c)
		}
	})
}
function initUpload(a, b,btn_id,uploadProgressId) {
	swf = new SWFUpload({
		flash_url : a
				+ "/appres/com/ztesoft/net/mall/plugin/standard/album/js/swfupload.swf",
		upload_url : a + "/shop/uploadPhoto." + b,
		post_params : {
			a : "a"
		},
		file_size_limit : "300 KB",
		file_types : "*.gif;*.jpg;*.bmp",
		file_types_description : "gif jpg bmp",
		file_upload_limit : 5,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : uploadProgressId,
			cancelButtonId : "btnCancel"
		},
		debug : false,
		button_image_url : a
				+ "/appres/com/ztesoft/net/mall/plugin/standard/album/images/XPButtonUploadText_61x22.png",
		button_width : "61",
		button_height : "22",
		button_placeholder_id : btn_id,
		button_text_left_padding : 12,
		button_text_top_padding : 3,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete
	})
}
function deleteUnSavePic(a) {
	var url = ctx + "/shop/uploadPhoto!delPhoto." + ext;
	$.ajax({
				type : "get",
				url : url,
				data : "photoName=" + a + "&rdm=" + (new Date).getTime(),
				dataType : "json",
				success : function() {
				}
			})
}
Eop.onRedirect = function() {
	if (true) {
		unSavePhotos = [];
		$(".progressContainer .deleteBtn.new").each(function() {
			var a = document.getElementById($(this).attr("wrapper"))
					.getElementsByTagName("img")[0].src;
			deleteUnSavePic(a)
		});
		return true
	} else
		return false
};
