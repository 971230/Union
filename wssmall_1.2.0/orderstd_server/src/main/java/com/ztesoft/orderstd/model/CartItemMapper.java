package com.ztesoft.orderstd.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.support.CartItem;

public class CartItemMapper implements RowMapper {


    @Override
	@SuppressWarnings("unchecked")
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getString("cart_id"));
        cartItem.setProduct_id(rs.getString("product_id"));
        cartItem.setGoods_id(rs.getString("goods_id"));
        cartItem.setName(rs.getString("name"));
        cartItem.setMktprice(rs.getDouble("mktprice"));
        cartItem.setPrice(rs.getDouble("price"));
        cartItem.setCoupPrice(rs.getDouble("price")); 
        //	cartItem.setCatid(rs.getInt("catid"));
        cartItem.setMember_lv_id(rs.getString("member_lv_id"));
        String image_default =  rs.getString("image_default");
        if(image_default==null)
            image_default = rs.getString("image_file");
        if(image_default!=null ){
            image_default  =UploadUtil.replacePath(image_default);
        }
        cartItem.setImage_default(image_default);
        cartItem.setNum(rs.getInt("num"));
        cartItem.setPoint(rs.getInt("point"));
        if(!StringUtil.isEmpty(rs.getString("spec_id")))
        	cartItem.setSpec_id(rs.getString("spec_id"));
        cartItem.setItemtype(rs.getInt("itemtype"));
        if( cartItem.getItemtype().intValue() ==  0){
            cartItem.setAddon(rs.getString("addon"));
        }
        //赠品设置其限购数量
        if( cartItem.getItemtype().intValue() ==  2){
            cartItem.setLimitnum(rs.getInt("limitnum"));
        }
		 
		/*if( cartItem.getItemtype().intValue() ==  1){
			cartItem.setSn(rs.getString("sn"));
		}*/
        try{
            cartItem.setSn(rs.getString("sn"));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
        try{
            cartItem.setCtn(rs.getInt("ctn"));
        }catch(Exception ex){
            //ex.printStackTrace();
        }
        try{
            cartItem.setUnit(rs.getString("unit"));
        }catch(Exception ex){
            //ex.printStackTrace();
        }

        if( cartItem.getItemtype().intValue() ==  0){
            String specs = rs.getString("specs");
            cartItem.setSpecs(specs);
            if(StringUtil.isEmpty(specs))
                cartItem.setName(  cartItem.getName() );
            else
                cartItem.setName(  cartItem.getName() +"("+ specs +")" );
        }

        try {
            cartItem.setIs_checked(rs.getString("is_checked"));
        } catch (Exception e) {
            // TODO: handle exception
            cartItem.setIs_checked("1");
        }

        try {
            cartItem.setStaff_no(rs.getString("staff_no")==null?"":rs.getString("staff_no"));
        } catch (Exception e) {
            // TODO: handle exception
            cartItem.setStaff_no("");
        }

        try {
            cartItem.setAgent_name(rs.getString("agent_name")==null?"":rs.getString("agent_name"));
        } catch (Exception e) {
            // TODO: handle exception
            cartItem.setAgent_name("");
        }

        if(!StringUtil.isEmpty(cartItem.getAddon())){
            JSONArray jsonArray = JSONArray.fromObject(cartItem.getAddon());
            List<HashMap> lists = (List) JSONArray.toCollection(jsonArray,HashMap.class);
            cartItem.setAddonList(lists);
        }
        //设置配件列表

        return cartItem;
    }

}