package com.ztesoft.rule.core.exe;

import java.util.List;
import java.util.Map;

import com.ztesoft.rule.core.module.cfg.BizPlan;


/**
 * 负载均衡策略:
 * 一.负载均衡分配的两个维度：
 * 1.资源维度：机器、端口、线程
 * 2.数据维度：方案、参与者
 * 
 * 二.当前负载均衡处理模式
 * 0.方案根据其标识hash code选择所属的主机:端口(进程)
 * 1.一个方案下,参与者作为最基础维度,则一个放下下的参与者数据,不可再分,它只能在一个线程内处理完毕
 * 2.为了方便协同处理,一个方案只能在一个进程(端口)内完成
 * 3.每个方案配置线程数,每个线程根据代理商标识的hash code处理其所属的代理商;每个线程处理完毕后,返回线程池,处理完成的标识是找不到可处理数据
 * 4.当所有线程都处理完毕后,方案也处理完毕
 * 【注】5.期间的异常信息,会被捕获掉,写retry表,做重出单独处理
 * 
 * @author easonwu 
 * @creation Dec 19, 2013
 * 
 */
public interface ILoadBalance {
	//先按一台机器处理所有方案来分解处理
	public List<Map> loadPartners(BizPlan plan ,int threadId) ;
	
	
	
}
