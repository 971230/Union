/**
 * Classname    : Debug
 * Description  : 日志信息打印
 * Author       : cwf
 * Date         : 2004-05-31
 *
 * Last Update  : 2004-05-31
 * Author       : cwf
 * Version      : 1.0
 */

package com.ztesoft.common.dao;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This class is just a helper class to make it handy
 * to print out debug statements
 */
public final class Debug {

    private static Logger logger = Logger.getLogger("com.ztesoft");

    /**
     * @param :msg 需要打印到控制台的信息
     */
    public static void print(String msg) {
        logger.log(Level.INFO, msg);
    }

    /**
     * @param :e 需要打印的例外 ,msg 需要打印到控制台的信息
     */
    public static void print(Exception e, String msg) {
        print( (Throwable) e, msg);
    }

    /**
     * @param :e 需要打印的例外
     */
    public static void print(Exception e) {
        print(e, null);
    }

    /**
     * @param :t 需要打印的例外 ,msg 需要打印到控制台的信息
     */
    public static void print(Throwable t, String msg) {
        logger.log(Level.WARN, "Received throwable with Message: " + msg, t);
    }

    /**
     * @param :t 需要打印的例外
     */
    public static void print(Throwable t) {
        print(t, null);
    }

    //-----------------------------------------------------------------

    /**
     * @param :msg 需要打印到控制台的信息, obj 调用者的句柄
     *
     */
    public static void print(String msg, Object obj) {
        if(obj != null) {
            logger.log(Level.INFO, obj.toString() + ": " + msg);
        }
        else {
            logger.log(Level.INFO, "请把this指针传进来，如：Debug.print(msg, this)");
        }
    }

    /**
     * @param :e 需要打印的例外 ,msg 需要打印到控制台的信息, obj 调用者的句柄
     *
     */
    public static void print(Exception e, String msg, Object obj) {
        print((Throwable) e, msg, obj);
    }

    /**
     * @param :e 需要打印的例外, obj 调用者的句柄
     *
     */
    public static void print(Exception e, Object obj) {
        print(e, obj.toString(), obj);
    }

    /**
     * @param :t 需要打印的例外 ,msg 需要打印到控制台的信息, obj 调用者的句柄
     *
     */
    public static void print(Throwable t, String msg, Object obj) {
        logger.log(Level.WARN, obj.toString() + ": " + msg, t);
    }

    /**
     * @param :t 需要打印的例外, obj 调用者的句柄
     *
     */
    public static void print(Throwable t, Object obj) {
        print(t, obj.toString(), obj);
    }

}
