package com.ztesoft.net.mall.core.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.processor.core.freemarker.FreeMarkerPaser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.plugin.IPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.plugin.payment.PaymentPluginBundle;
import com.ztesoft.net.mall.core.service.IPaymentManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 支付方式管理
 *
 * @author kingapex
 *         2010-4-4下午02:26:19
 */
public class PaymentManager extends BaseSupport<PayCfg> implements IPaymentManager {

    //支付插件桩
    private PaymentPluginBundle paymentPluginBundle;
    //本地缓存
 	public static HashMap<String,PayCfg> localCache = new HashMap<String,PayCfg>();


    @Override
	public List list() {
        //add by wui
        //EopSite eopSite = EopContext.getContext().getCurrentSite();
        //String theme_path = eopSite.getThemepath();
        String sql = "select * from payment_cfg where 1=1 and source_from='"+ManagerUtils.getSourceFrom()+"'"; //add by wui移动用户只有货到付款

        return this.baseDaoSupport.queryForList(sql, PayCfg.class);
    }


    @Override
	public List listById(String id) {
        String sql = "select * from payment_cfg where id = ? and source_from ='"+ManagerUtils.getSourceFrom()+"' ";
        return this.baseDaoSupport.queryForList(sql, PayCfg.class, id);
    }


    @Override
	public PayCfg get(Integer id) {
        String sql = "select * from payment_cfg where id=? and source_from ='"+ManagerUtils.getSourceFrom()+"'";
        PayCfg payment = this.baseDaoSupport.queryForObject(sql, PayCfg.class, id);
        if(payment ==null){
	    	  sql = "select * from payment_cfg where id=? and source_from is not null  ";
	    	List<PayCfg>  payments = this.baseDaoSupport.queryForList(sql, PayCfg.class, id);
	    	if(!ListUtil.isEmpty(payments))
	    		payment = payments.get(0);
        }
        return payment;
    }

	@Override
	public PayCfg get(String pluginid) {
		String preKey = pluginid + "PAYMENT_CFG";
		PayCfg payment = null;
		if(null != localCache.get(preKey)) {
			return localCache.get(preKey);
		} else {
			String sql = "select * from payment_cfg where id=? and source_from ='" + ManagerUtils.getSourceFrom() + "'";
			payment = this.baseDaoSupport.queryForObject(sql, PayCfg.class, pluginid);
			if (payment == null) {
				sql = "select * from payment_cfg where id=? and source_from is not null  ";
				List<PayCfg> payments = this.baseDaoSupport.queryForList(sql, PayCfg.class, pluginid);
				if (!ListUtil.isEmpty(payments)) {
					payment = payments.get(0);
				}
			}
			localCache.put(preKey, payment);
		}
		return payment;
	}


    @Override
	public Double countPayPrice(String id) {

        return 0D;
    }


    @Override
	public void add(String name, String type, String biref,
                    Map<String, String> configParmas) {
        if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
        if (StringUtil.isEmpty(type)) throw new IllegalArgumentException("payment type is  null");
        if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");

        PayCfg payCfg = new PayCfg();
        payCfg.setName(name);
        payCfg.setType(type);
        payCfg.setBiref(biref);
        payCfg.setConfig(JSON.toJSONString(configParmas));
        this.baseDaoSupport.insert("payment_cfg", payCfg);
    }


    @Override
	public Map<String, String> getConfigParams(Integer paymentId) {
        PayCfg payment = this.get(paymentId);
        String config = payment.getConfig();
        if (null == config) {
            return new HashMap<String, String>();
        }
        return JSON.parseObject(config, Map.class);
    }


    @Override
	public Map<String, String> getConfigParams(String pluginid) {
        PayCfg payment = this.get(pluginid);
        String config = payment.getConfig();
        if (null == config) {
            return new HashMap<String, String>();
        }
        return JSON.parseObject(config, Map.class);
    }


    @Override
	public void edit(String paymentId, String name, String biref,
                     Map<String, String> configParmas) {

        if (StringUtil.isEmpty(name)) throw new IllegalArgumentException("payment name is  null");
        if (configParmas == null) throw new IllegalArgumentException("configParmas  is  null");

        PayCfg payCfg = new PayCfg();
        payCfg.setName(name);
        payCfg.setBiref(biref);
        payCfg.setConfig(JSON.toJSONString(configParmas));
        this.baseDaoSupport.update("payment_cfg", payCfg, "id=" + paymentId);
    }


    @Override
	public void delete(String[] idArray) {
        if (idArray == null || idArray.length == 0) return;

        String idStr = StringUtil.arrayToString(idArray, ",");
        String sql = "delete from payment_cfg where id in(" + idStr + ")";
        this.baseDaoSupport.execute(sql);
    }


    @Override
	public List<IPlugin> listAvailablePlugins() {

        return this.paymentPluginBundle.getPluginList();
    }


    @Override
	public String getPluginInstallHtml(String pluginId, String paymentId) {
        IPlugin installPlugin = null;
        List<IPlugin> plguinList = this.listAvailablePlugins();
        for (IPlugin plugin : plguinList) {
            if (plugin.getId().equals(pluginId)) {
                installPlugin = plugin;
                break;
            }
        }


        if (installPlugin == null) throw new ObjectNotFoundException("plugin[" + pluginId + "] not found!");
        FreeMarkerPaser fp = FreeMarkerPaser.getInstance();
        fp.setClz(installPlugin.getClass());

        if (paymentId != null) {
            Map<String, String> params = this.getConfigParams(paymentId);
            Iterator<String> keyIter = params.keySet().iterator();

            while (keyIter.hasNext()) {
                String key = keyIter.next();
                String value = params.get(key);
                fp.putData(key, value);
            }
        }
        return fp.proessPageContent();
    }


    public PaymentPluginBundle getPaymentPluginBundle() {
        return paymentPluginBundle;
    }

    public void setPaymentPluginBundle(PaymentPluginBundle paymentPluginBundle) {
        this.paymentPluginBundle = paymentPluginBundle;
    }


    @Override
    public PayCfg getPayConfig(String config_id) {
        String sql = "select t.* from es_payment_cfg t where t.id=?";
        return this.baseDaoSupport.queryForObject(sql, PayCfg.class, config_id);
    }


}
