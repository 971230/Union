package rule;

import zte.net.common.params.req.ZteInstQueryRequest;

import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wu.i
 * 业务操作处理器
 *
 */
public interface IZteBusiRequestHander {


	/**
	 * 数据存储处理
	 */
	@NotDbField
	public  <T> T store();

	
	/**
	 * 数据库操作执行
	 */
	@NotDbField
	public <T>T load(ZteInstQueryRequest instParam);
	
}
