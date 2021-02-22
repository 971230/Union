
var GoodsBuyWay={
    conid:undefined,
    init:function(conid,onConfirm)	{
        this.conid = conid;
        var self  = this;
        $("#searchBtn").click(function(){
            self.search(onConfirm);
        });

        $("#toggleChk").click(function(){
            $("input[name=goodsid]").attr("checked",this.checked);
        });
        $("#"+conid+" .submitBtn").click(function(){
            self.getGoodsList(onConfirm);
            Eop.Dialog.close(conid);
        });
    },
    search:function(onConfirm){
        var self = this;
        var options = {
            url :ctx+"/shop/admin/goodsContractAction!queryBuyWay.do?ajax=yes",
            type : "GET",
            dataType : 'html',
            success : function(result) {
                $("#"+self.conid).empty().append(result);
                self.init(self.conid,onConfirm);
            },
            error : function(e) {
                alert("出错啦:(");
            }
        };

        $("#buyWayForm").ajaxSubmit(options);
    },
    getGoodsList:function(callback){
        var options = {
            url :ctx+"/shop/admin/goodsContractAction!listBuyWay.do?ajax=yes",
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

        $("#buyWayForm").ajaxSubmit(options);
    },
    open:function(conid,onConfirm){
        var self= this;
        $("#"+conid).load(ctx+'/shop/admin/goodsContractAction!queryBuyWay.do?ajax=yes',function(){
            self.init(conid,onConfirm);
        });
        Eop.Dialog.open(conid);
    }
}