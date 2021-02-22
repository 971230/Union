package util;

import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
@SuppressWarnings("all")
public class ChannelidProductcompRefUtil {

	public static final String key = "CHANNELID_PRODUCTCOMP";// 渠道编码 与 货主映射关系
	public static final String CORE_CACHE_TYPE = "";
	// 100以下是保留，最大值不能超过10000
	public final static int NAMESPACE_CHANNELID_PRODUCTCOMP = 1990;

	public static String getCompCodeNoDefault(String channel_id){//此方法当获取不到货主时，返回空(未有地方使用此方法)
		String comp_code = "";
		//去缓存拿数据
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		comp_code = (String) cache.get(NAMESPACE_CHANNELID_PRODUCTCOMP, key + channel_id);
		if(StringUtils.isEmpty(comp_code)){//缓存无数据则从数据库查
			comp_code = setCompCode(channel_id);
		}
		return comp_code;
	}
	
	public static String getCompCode(String channel_id){//此方法当获取不到货主时，返回默认货主
		String comp_code = "";
		//去缓存拿数据
		INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
		comp_code = (String) cache.get(NAMESPACE_CHANNELID_PRODUCTCOMP, key + channel_id);
		if(StringUtils.isEmpty(comp_code)){//缓存无数据则从数据库查
			comp_code = setCompCode(channel_id);
		}
		/**
		 * 获取不到货主信息时，取默认值
		 */
		if(StringUtils.isEmpty(comp_code)){
			comp_code = (String) cache.get(NAMESPACE_CHANNELID_PRODUCTCOMP, key + EcsOrderConsts.CHANNELID_FRO_DEFAULT_PC);
			if(StringUtils.isEmpty(comp_code)){//缓存无数据则从数据库查
				comp_code = setCompCode(EcsOrderConsts.CHANNELID_FRO_DEFAULT_PC);
			}
		}
		/**
		 * 获取不到货主信息时，取默认值
		 */
		/**
		 * 没有默认值，则取"GDLT"广东联通
		 */
		if(StringUtils.isEmpty(comp_code)){
			comp_code = EcsOrderConsts.PRODUCT_COMP_GDLT;
		}
		/**
		 * 没有默认值，则取"GDLT"广东联通
		 */
		return comp_code;
	}
	
	public static String setCompCode(String channel_id){
		String comp_code = "";
		IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select comp_code from es_channelid_productcomp a where a.channel_id ='"+ channel_id+ "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
		comp_code = support.queryForString(sql);//channel_id做了主键限制，这里不会有多记录的情况
		if(!StringUtils.isEmpty(comp_code)){//设置缓存
			INetCache cache = CacheFactory.getCacheByType(CORE_CACHE_TYPE);
			cache.set(NAMESPACE_CHANNELID_PRODUCTCOMP, key+channel_id,comp_code);
		}
		return comp_code;
	}
}