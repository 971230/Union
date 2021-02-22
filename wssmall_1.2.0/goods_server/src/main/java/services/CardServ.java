package services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.service.ICardManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 17:09
 * To change this template use File | Settings | File Templates.
 */
public class CardServ implements CardInf {
    @Resource
    private ICardManager cardManager;


    /*@Override
    public void transfer_card(OrderRequst orderRequst, OrderResult orderResult) {
        this.cardManager.transfer_card(orderRequst, orderResult);
    }*/

    @Override
    public Page transfer_list(int pageNO, int pageSize, Object... args) {
        //return this.cardManager.transfer_list(pageNO, pageSize, args);
    	return null;
    }

    @Override
    public Card getCardByUserIdAndMoney(String userid, String money, String type_code, String goodsId) {
        //return this.cardManager.getCardByUserIdAndMoney(userid, money, type_code, goodsId);
    	return null;
    }

    @Override
    public Card getCardByOrderId(String order_id) {
        //return this.cardManager.getCardByOrderId(order_id);
    	return null;
    }

    @Override
    public Card getCardNoTnsByUserIdAndMoney(String userid, String money, String type_code) {
       // return this.cardManager.getCardNoTnsByUserIdAndMoney(userid, money, type_code);
    	return null;
    }

    @Override
    public void resetFailCardByOrderId() {
        //this.cardManager.resetFailCardByOrderId();
    }

    @Override
    public String getLanIdByAccNbr(String acc_nbr) {
       // return this.cardManager.getLanIdByAccNbr(acc_nbr);
    	return null;
    }

    @Override
    public String getTransferPrice(String cardIds) {
        //return this.cardManager.getTransferPrice(cardIds);
    	return null;
    }

    @Override
    public void updateCardByOrderId(String orderId) {
       // this.cardManager.updateCardByOrderId(orderId);
    }

    @Override
    public void updateCard(Card card) {
        //this.cardManager.updateCard(card);
    }

    @Override
    public void resetCardByOrderId(String orderId) {
       // this.cardManager.resetCardByOrderId(orderId);
    }

    @Override
    public Map uvcRecharge(Card card, CardInfRequest cardInfo) {
        //return this.cardManager.uvcRecharge(card, cardInfo);
    	return null;
    }

    @Override
    public List list(String orderId) {
        //return this.cardManager.list(orderId);
    	return null;
    }

    @Override
    public List<Card> getCardList(String rateIds) {
       // return this.cardManager.getCardList(rateIds);
    	return null;
    }

    @Override
    public Map listc(Card card) {
       // return this.cardManager.listc(card);
    	return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String returnedCards(String sec_order_id) {
        //return this.cardManager.returnedCards(sec_order_id);
    	return null;
    }

    @Override
    public List<Card> checkUnavailableCard(String cardIds) {
        //return this.cardManager.checkUnavailableCard(cardIds);
    	return null;
    }

    @Override
    public String occupyCards(String cardIds, String sec_order_id) {
        //return this.cardManager.occupyCards(cardIds, sec_order_id);
    	return null;
    }

    @Override
    public String getGoodsIdByCardId(String cardid) {
       // return this.cardManager.getGoodsIdByCardId(cardid);
    	return null;
    }

    @Override
    public BatchResult importCard(List inList, String cardType) {
       // return this.cardManager.importCard(inList, cardType);
    	return null;
    }

    @Override
    public Map listAvialableC(Card card) {
      //  return this.cardManager.listAvialableC(card);
    	return null;
    }

    @Override
    public Page transfer_allList(int pageNO, int pageSize, Object... args) {
       // return this.cardManager.transfer_allList(pageNO, pageSize, args);
    	return null;
    }

    @Override
    public BatchResult importCardByTxt(Map map) {
        //return this.cardManager.importCardByTxt(map);
    	return null;
    }

    @Override
    public int isExistsExCard(List list, String cardType) {
        //return this.cardManager.isExistsExCard(list, cardType);
    	return 0;
    }

    @Override
    public int isExistsTxtCard(Map map) {
        //return this.cardManager.isExistsTxtCard(map);
    	return 0;
    }

    @Override
    public List getGoodsList(String type_code) {
        //return this.cardManager.getGoodsList(type_code);
    	return null;
    }
}
