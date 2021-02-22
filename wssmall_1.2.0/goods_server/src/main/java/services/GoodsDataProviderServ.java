package services;

import com.ztesoft.net.mall.core.model.support.GoodsView;
import com.ztesoft.net.mall.widget.goods.list.GoodsDataProvider;
import com.ztesoft.net.mall.widget.goods.list.Term;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 19:25
 * To change this template use File | Settings | File Templates.
 */
public class GoodsDataProviderServ implements GoodsDataProviderInf {

    @Resource
    private GoodsDataProvider goodsDataProvider;

    @Override
    public List<GoodsView> list(Term term, int num) {
        return this.goodsDataProvider.list(term,num);
    }
}
