
var GoodsContract={
    conid:undefined,
    init:function(conid,onConfirm)	{
        this.conid = conid;
        var self  = this;
        $("#"+conid+" .submitBtn").click(function(){
            self.getGoodsList(onConfirm);
            Eop.Dialog.close(conid);
        });



    },
    getGoodsList:function(callback){
        var options = {
            url :ctx+"/shop/admin/goodsPropertyAction!list.do?ajax=yes",
            type : "GET",
            dataType : 'json',
            success : function(result) {
                if(callback){
                    callback(result);
                }
            },
            error : function(e,b) {
                //console.log(e);
                alert("出错啦:(");
            }
        };

        $("#addParStaffform").ajaxSubmit(options);
    },
    open:function(conid,onConfirm){
            var self= this;
            $("#goodsProperty").load(ctx+'/shop/admin/goodsPropertyAction!saveProperty.do?ajax=yes', function () {
				self.init(conid,onConfirm);
            });
            Eop.Dialog.open("goodsProperty");
    }
}