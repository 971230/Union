package com.ztesoft.net.mall.core.model;

import java.util.ArrayList;


/**
 * 流量卡、充值卡导入日志
 * 
 * @author wui
 */
@SuppressWarnings("serial")
public class CardImpRequest implements java.io.Serializable {
	
	CardLog cardLog;
	ArrayList cards = new ArrayList(); //7311001002070000000	730061465346755465
	public CardLog getCardLog() {
		return cardLog;
	}
	public void setCardLog(CardLog cardLog) {
		this.cardLog = cardLog;
	}
	public ArrayList getCards() {
		return cards;
	}
	public void setCards(ArrayList cards) {
		this.cards = cards;
	}
	
	

}