package com.ztesoft.api.internal.utils;


/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-16 15:38
 * To change this template use File | Settings | File Templates.
 * 
 * 
 *add by wui静止使用此类，功能统一搬迁到CommonTools对象，Dirty太多了，去除掉，
 *
 */
@Deprecated
public class ApiTools {
//    /**
//     * bean转换为json
//     * @作者 MoChunrun
//     * @创建日期 2013-9-23
//     * @param src
//     * @return
//     */
//    public static <T> String beanToJson(T src){
//        SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
//                SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
//                SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
//                SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
//                SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
//                SerializerFeature.WriteClassName,
//        };
//        return JSON.toJSONString(src, features);
//    }
//
//    /**
//     * json转为Bean
//     * @作者 MoChunrun
//     * @创建日期 2013-9-23
//     * @param <T>
//     * @param json
//     * @param clazz
//     * @return
//     */
//    public static <T> T jsonToBean(String json,Class clazz){
//        return (T) JSON.parseObject(json, clazz);
//    }
//
//
//
//    /**
//     * json 转为list
//     * @作者 MoChunrun
//     * @创建日期 2013-9-23
//     * @param <T>
//     * @param json
//     * @param clazz
//     * @return
//     */
//    public static <T> List<T> jsonToList(String json,Class clazz){
//        return JSON.parseArray(json, clazz);
//    }

//    public static ZteError getZteError(){
//        return ApiThreadLocalHolder.getInstance().getZteCommonData().getZteError();
//    }
//    public static ZteResponse getZteResponse(){
//        return ApiThreadLocalHolder.getInstance().getZteCommonData().getZteResponse();
//    }

//    public static String getErrorStr(){
//        return ApiTools.beanToJson(getZteError());
//    }


//    public static String getParamStr(){
//        return ApiThreadLocalHolder.getInstance().getZteCommonData().getParam_json();
//    }

    /**
     * 异常类
     * @param errorEntity
     */
//    public static void addError(String errCode,String msg){
//        ZteError errorEntity=new ZteError(errCode,msg);
//        ApiThreadLocalHolder.getInstance().getZteCommonData().setZteError(errorEntity);
//        throw new ApiRunTimeException(errCode,errorEntity.getError_msg());
//    }
//
//    /**
//     * 异常类
//     * @param errorEntity
//     */
//    public static void addError(ZteError errorEntity){
//        ApiThreadLocalHolder.getInstance().getZteCommonData().setZteError(errorEntity);
//        throw new ApiRunTimeException(errorEntity.getError_code(),errorEntity.getError_msg());
//    }

//    /**
//     * 异常类
//     * @param errorEntity
//     */
//    public static void addFailError(String message){
//        if( ApiThreadLocalHolder.getInstance().getZteCommonData().getZteError() ==null)
//            ApiThreadLocalHolder.getInstance().getZteCommonData().setZteError(new ZteError(ApiConsts.ERROR_FAIL,message));
//        throw new ApiRunTimeException("-1",message);
//    }

}
