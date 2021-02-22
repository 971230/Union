/**
 * @author sky
 * @date 2015-07-02
 * @description this class just used to save 轉兌包 information to database so that fuck 稽覈 system can lookup those information
 */
package com.ztesoft.net.outter.inf.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztesoft.net.eop.sdk.database.BaseJdbcDaoSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * @author sky
 * @version 1.0
 */
public final class ZhuanDuiBaoUtil
{
	private static Logger logger=LoggerFactory.getLogger(ZhuanDuiBaoUtil.class);
	private static final String SQL_SEARCH="select a.* from es_goods a  join es_pmt_goods b on a.goods_id=b.goods_id join es_promotion c on c.pmt_id=b.pmt_id and c.pmts_id='gift' and c.promotion_type='010' join es_promotion_activity d on d.id=c.pmta_id where a.type_id='10010'  and d.pmt_code=? and a.source_from=?";
	private static final String SQL_INSERT="insert into es_gd_zhuanduibao(out_tid,pmt_code,goods_id) values(?,?,?)";
	
	/**
	 * this is the only function which used to save the information of 轉兌包
	 * @param out_tid: the order if of the outer system
	 * @param pmt_codes: the activities about this order. Delimited by comma 
	 */
	public static void save(String out_tid,String pmt_code_s)
	{
		if(null==out_tid || "".equalsIgnoreCase(out_tid)
				|| null == pmt_code_s || "".equalsIgnoreCase(pmt_code_s))
			return;
		
		try
		{			
			BaseJdbcDaoSupport dbUtil=(BaseJdbcDaoSupport)SpringContextHolder.getBean("baseDaoSupport");
			if(null==dbUtil)
			{
				logger.info("the dbUtils CAN NOT be got in Spring Context");
				logger.info("the dbUtils CAN NOT be got in Spring Context");
				return;
			}
			
			String[] pmt_codes=pmt_code_s.split(",");
			for(String pmt_code : pmt_codes)
			{
				List<Map> result=dbUtil.queryForList(SQL_SEARCH, pmt_code,ManagerUtils.getSourceFrom());
				for(Map item : result)
				{
					dbUtil.execute(SQL_INSERT, out_tid,pmt_code,item.get("goods_id"));
				}
			}							
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}				
	}
}
