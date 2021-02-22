package zte.net.workCustomBean.common;

import java.io.StringReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class JSBusiBean extends BasicBusiBean {
	
	/**
	 * 获取订单树JSON字符串
	 * @param orderTree
	 * @return
	 * @throws Exception
	 */
	private String getOrderTreeJsonStr(OrderTreeBusiRequest orderTree) throws Exception{

		JSONObject json = JSONObject.fromObject(orderTree);
		
		JSONArray itemArr = json.getJSONArray("orderItemBusiRequests");
		
		if(itemArr!=null){
			for(int i=0;i<itemArr.size();i++){
				JSONObject item = (JSONObject)itemArr.get(i);
				JSONObject itemExt = (JSONObject)item.get("orderItemExtBusiRequest");
				
				if(itemExt!=null){
					//影响json字符串转化为JS对象，去除card_data
					itemExt.put("card_data", "");
				}
				
				JSONArray printArr = item.getJSONArray("orderItemsAptPrintsRequests");
				
				if(printArr!=null){
					for(int j=0;j<printArr.size();j++){
						JSONObject print = (JSONObject)printArr.get(j);
						//影响json字符串转化为JS对象，去除acceptance_html
						print.put("acceptance_html", "");
						print.put("acceptance_html_2", "");
						print.put("acceptance_html_3", "");
					}
				}
			}
		}
		
		return json.toString();
	}

	@Override
	public String doBusi() throws Exception {
		StringBuilder  jsBuilder = new StringBuilder();
		
		if(this.getFlowData()==null || StringUtils.isBlank(this.getNode_id()))
			return "";
		
		OrderTreeBusiRequest orderTree = this.getFlowData().getOrderTree();
		
		if(orderTree == null)
			return "";
		
		String js = "";
		
		if(!this.getFlowData().getInsMap().containsKey(this.getNode_id()))
			return "";
		
		js = this.getFlowData().getInsMap().get(this.getNode_id()).getDeal_param();
		
		if(StringUtils.isBlank(js))
			return "";
		
		//获取订单树JSON字符串
		String orderTreeJson = this.getOrderTreeJsonStr(orderTree);
		
		//在JS中加入订单树对象
		jsBuilder.append(" var orderTree = JSON.parse('"+orderTreeJson+"'); ");
		//配置的JS
		jsBuilder.append(js);
		
		String scriptResult ="";
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");//得到脚本引擎
		
        try {
            //引擎读取 脚本字符串
            engine.eval(new StringReader(jsBuilder.toString()));

            //将引擎转换为Invocable，这样才可以掉用js的方法
            Invocable invocable = (Invocable) engine;

            //使用 invocable.invokeFunction掉用js脚本里的方法，第一個参数为方法名，后面的参数为被调用的js方法的入
            scriptResult = String.valueOf(invocable.invokeFunction("doBusi"));
        }catch(Exception e){
            throw e;
        }
		
		return scriptResult;
	}
}
