package net.buffalo.service.invoker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于BuffaloInvoker反射时使用
 * 核心版本整理s
 * 【1】是否启动事务
 * 【2】需要绑定什么上下文
 * @author zhou.jundi
 *
 */
//加载在VM中，在运行时进行映射
@Retention(RetentionPolicy.RUNTIME)
//限定此annotation只能标示方法
@Target(ElementType.METHOD)
public @interface ContextMeta {
	String cls();  //上下文对象
}
