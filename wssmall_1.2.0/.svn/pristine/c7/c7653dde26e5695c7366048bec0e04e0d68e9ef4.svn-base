package com.ztesoft.common.util;


/**
 * 可将VO转成XML中的item节点的接口.
 * 所有需要将自身转换为xml格式的item节点传回前端用于TreeList组件的实体均需要实现该接口
 * 
 * @author shi.xiangsheng
 *
 */
public interface XMLItem
{
    /**
     * 设VO的字段有fld1，fld2，...; 值val1, val2, ...
     * 转换后的格式为&ltitem fld1='val1', fld2='val2', ...&gt
     * @param sbXml 用于填充转换后xml片断的StringBuffer
     * @return 填充转换后xml片断后的StringBuffer， 同sbXml
     */
    StringBuffer toXmlItemUnclosed(StringBuffer sbXml);
    /**
     * 获取自组织树结构VO的树路径值
     * @param vo 自组织树结构VO
     * @return 树路径值, ""表示根节点, null表示非树结构
     */
    String pathInTree();
}
