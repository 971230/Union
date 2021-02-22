package com.ztesoft.net.sqls;

public class Sqls_Goods_Local extends Sqls{
	
	
	public Sqls_Goods_Local(){
		//SqlUtil.initSqls(Sqls_Goods.class, this , sqls) ;}
	}
	
	@Override
	public String getSql(String getName, Sqls cInst) {
		String sql = Sqls.getPropSql(getName, this, cInst);
		return sql;
	}

}
