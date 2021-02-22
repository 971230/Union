/**
 * 
 */
package com.ztesoft.ibss.wb.Filter;

/**
 * @author Musoon
 *
 */
public class SecurityFilterVo {

	//请求URL后缀
	private String urlPostfix;
	//有限时间
	private int limitTime;
	//限制次数
	private int limitCount;
	
	/**
	 * @return the urlPostfix
	 */
	public String getUrlPostfix() {
		return urlPostfix;
	}
	/**
	 * @param urlPostfix the urlPostfix to set
	 */
	public void setUrlPostfix(String urlPostfix) {
		this.urlPostfix = urlPostfix;
	}
	/**
	 * @return the limitTime
	 */
	public int getLimitTime() {
		return limitTime;
	}
	/**
	 * @param limitTime the limitTime to set
	 */
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	/**
	 * @return the limitCount
	 */
	public int getLimitCount() {
		return limitCount;
	}
	/**
	 * @param limitCount the limitCount to set
	 */
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
	
	
	
}
