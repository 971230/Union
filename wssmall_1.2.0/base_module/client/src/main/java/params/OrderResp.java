package params;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-21 16:17
 * To change this template use File | Settings | File Templates.
 */
public class OrderResp implements Serializable {
    private String deal_flag;//标识符

    private String deal_msg;//描述信息

    private String ord_code;//订单编号


    public String getDeal_flag() {
        return deal_flag;
    }

    public void setDeal_flag(String deal_flag) {
        this.deal_flag = deal_flag;
    }

    public String getDeal_msg() {
        return deal_msg;
    }

    public void setDeal_msg(String deal_msg) {
        this.deal_msg = deal_msg;
    }

    public String getOrd_code() {
        return ord_code;
    }

    public void setOrd_code(String ord_code) {
        this.ord_code = ord_code;
    }
}
