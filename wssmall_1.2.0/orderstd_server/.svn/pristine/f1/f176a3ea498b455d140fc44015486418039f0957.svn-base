package com.ztesoft.newstd.common;

public class CommonContext {
    // CommonData线程变量
    private static ThreadLocal<CommonData> dataLoacal = new ThreadLocal<CommonData>();

    /**
     * 从线程变量获取CommonData
     * 
     * @return
     */
    public static CommonData getData() {
        return dataLoacal.get();
    }

    /**
     * 把CommonData设置到线程变量
     * 
     * @param commonData
     */
    public static void setData(CommonData commonData) {
        dataLoacal.set(commonData);
    }

    /**
     * 从线程变量移除CommonData
     */
    public static void removeData() {
        dataLoacal.remove();
    }
}
