package zte.params.goods.resp;

import com.ztesoft.net.mall.core.model.Product;
import params.ZteResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-28 18:08
 * To change this template use File | Settings | File Templates.
 */
public class ProductDetailsResp extends ZteResponse {
    private List<Product> list;

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
