package zte.net.ecsord.params.busi.req;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;
import java.util.regex.Pattern;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="member_id",table_name="es_member")
public class MemberBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	@ZteSoftCommentAnnotationParam(name="会员id",type="String",isNecessary="Y",desc="会员id")
    private String member_id;
	@RequestFieldAnnontion()
    private String lv_id;
	@RequestFieldAnnontion()
    private String uname;
	@RequestFieldAnnontion()
    private String email;
	@RequestFieldAnnontion()
    private String password;
	@RequestFieldAnnontion()
    private String regtime;
	@RequestFieldAnnontion()
    private String pw_answer;
	@RequestFieldAnnontion()
    private String pw_question;
	@RequestFieldAnnontion()
    private String name;
	@RequestFieldAnnontion()
    private Integer sex;
	@RequestFieldAnnontion()
    private String birthday;
	@RequestFieldAnnontion()
    private Double advance;
	@RequestFieldAnnontion()
    private Integer province_id;
	@RequestFieldAnnontion()
    private Integer city_id;
	@RequestFieldAnnontion()
    private Integer region_id;
	@RequestFieldAnnontion()
    private String province;
	@RequestFieldAnnontion()
    private String city;
	@RequestFieldAnnontion()
    private String region;
	@RequestFieldAnnontion()
    private String address;
	@RequestFieldAnnontion()
    private String zip;
	@RequestFieldAnnontion()
    private String mobile;
	@RequestFieldAnnontion()
    private String tel;

 
	@RequestFieldAnnontion()
    private Integer point;
	@RequestFieldAnnontion()
    private String qq;
	@RequestFieldAnnontion()
    private String msn;
	@RequestFieldAnnontion()
    private String remark;
	@RequestFieldAnnontion()
    private String lastlogin;
	@RequestFieldAnnontion()
    private Integer logincount;
	@RequestFieldAnnontion()
    private int mp;  //消费积分 
    
    //会员等级名称，非数据库字段
	@RequestFieldAnnontion()
    private String lvname;
	@RequestFieldAnnontion()
    private String lv_name;
	@RequestFieldAnnontion()
    private String parentid; //父商户id
	@RequestFieldAnnontion()
    private String agentid; //商户id
	@RequestFieldAnnontion()
    private int is_cheked; //是否已验证
	@RequestFieldAnnontion()
    private String registerip; // 注册IP
	@RequestFieldAnnontion()
    private Integer audit_status;
	
    private boolean isSave = false;
	@RequestFieldAnnontion()
    private String sessionId ;
	@RequestFieldAnnontion()
    private String member_type;
	@RequestFieldAnnontion()
    private String col1;
	@RequestFieldAnnontion()
    private String col2;
	@RequestFieldAnnontion()
    private String col3;
	@RequestFieldAnnontion()
    private String col4;
	@RequestFieldAnnontion()
    private String col5;
	@RequestFieldAnnontion()
    private String col6;
	@RequestFieldAnnontion()
    private String col7;
	@RequestFieldAnnontion()
    private String col8;
	@RequestFieldAnnontion()
    private String col9;
	@RequestFieldAnnontion()
    private String col10;
	@RequestFieldAnnontion()
    private String buyer_uid;
	@RequestFieldAnnontion()
    private String ship_area;
	@RequestFieldAnnontion()
    private String cert_card_num;
	@RequestFieldAnnontion()
    private String cert_address;
	@RequestFieldAnnontion()
    private String cert_failure_time;
	@RequestFieldAnnontion()
    private String cert_type;
	@RequestFieldAnnontion()
    private String customertype;
	@RequestFieldAnnontion()
    private String plat_type;
    
    @NotDbField
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String source_from;
    
    public String getSource_from() {
        return source_from;
    }

    public void setSource_from(String source_from) {
        this.source_from = source_from;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String memberId) {
        member_id = memberId;
    }

    public String getLv_id() {
        //lv_id = lv_id==null?0:lv_id;
        if(!isSave){
            String lvid = "0";
            try{
                /**
                 * 从session获取用户选择的等级
                 */
                String session_lv_id = (String) ThreadContextHolder.getSessionContext().getAttribute("SESSION_MEMBER_LV");
                Pattern pl = Pattern.compile(session_lv_id+",|,"+session_lv_id, Pattern.CASE_INSENSITIVE);
                if(session_lv_id!=null && !"".equals(session_lv_id) && pl.matcher(lv_id).find()){
                    return session_lv_id;
                }
            }catch(Exception ex){
            }
            
            if(lv_id!=null){
                String [] lvs = lv_id.split(",");
                for(String s:lvs){
                    if(s!=null && !"".equals(s)){
                        lvid = s.trim();
                        break ;
                    }
                }
            }
            return lvid;
        }else{
            return lv_id;
        }
        //return lv_id;
    }
    
    @NotDbField
    public boolean getSave() {
        return isSave;
    }

    public void setSave(boolean isSave) {
        this.isSave = isSave;
    }

    @NotDbField
    public String getLv_ids(){
        if(lv_id==null)
            return "";
        return lv_id;
    }

    public void setLv_id(String lvId) {
        lv_id = lvId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getPw_answer() {
        return pw_answer;
    }

    public void setPw_answer(String pwAnswer) {
        pw_answer = pwAnswer;
    }

    public String getPw_question() {
        return pw_question;
    }

    public void setPw_question(String pwQuestion) {
        pw_question = pwQuestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Integer getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Integer provinceId) {
        province_id = provinceId;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer cityId) {
        city_id = cityId;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Integer regionId) {
        region_id = regionId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public Integer getPoint() {
        if(point==null) point=0;
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @NotDbField
    public String getLvname() {
        return lvname;
    }

    public void setLvname(String lvname) {
        this.lvname = lvname;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Integer getLogincount() {
        return logincount;
    }

    public void setLogincount(Integer logincount) {
        this.logincount = logincount;   }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public int getIs_cheked() {
        return is_cheked;
    }

    public void setIs_cheked(int isCheked) {
        is_cheked = isCheked;
    }

    public String getRegisterip() {
        return registerip;
    }

    public void setRegisterip(String registerip) {
        this.registerip = registerip;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public String getLv_name() {
        return lv_name;
    }

    public void setLv_name(String lv_name) {
        this.lv_name = lv_name;
    }
    
    @NotDbField
    public String getCacheLv_id(){
        String e_lv_id = "0";
        String lvids = lv_id==null?"0":lv_id;
        String [] ids = lvids.split(",");
        boolean flag = false;
        /*for(String s:ids){
            if(Const.MEMBER_LV_CHINA_TELECOM_DEP.equals(s) || Const.MEMBER_LV_CHINA_TELECOM_STAFF.equals(s)){
                e_lv_id = s;
                flag = true;
                break ;
            }
        }*/
        if(!flag && ids.length>0){
            e_lv_id = ids[0];
        }
        return e_lv_id;
    }

    public Integer getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(Integer audit_status) {
        this.audit_status = audit_status;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }

    public String getCol6() {
        return col6;
    }

    public void setCol6(String col6) {
        this.col6 = col6;
    }

    public String getCol7() {
        return col7;
    }

    public void setCol7(String col7) {
        this.col7 = col7;
    }

    public String getCol8() {
        return col8;
    }

    public void setCol8(String col8) {
        this.col8 = col8;
    }

    public String getCol9() {
        return col9;
    }

    public void setCol9(String col9) {
        this.col9 = col9;
    }

    public String getCol10() {
        return col10;
    }

    public void setCol10(String col10) {
        this.col10 = col10;
    }

    public boolean isSave() {
        return isSave;
    }

    public String getBuyer_uid() {
        return buyer_uid;
    }

    public void setBuyer_uid(String buyer_uid) {
        this.buyer_uid = buyer_uid;
    }

    public String getShip_area() {
        return ship_area;
    }

    public void setShip_area(String ship_area) {
        this.ship_area = ship_area;
    }

    public String getCert_card_num() {
        return cert_card_num;
    }

    public void setCert_card_num(String cert_card_num) {
        this.cert_card_num = cert_card_num;
    }

    public String getCert_address() {
        return cert_address;
    }

    public void setCert_address(String cert_address) {
        this.cert_address = cert_address;
    }

    public String getCert_failure_time() {
        return cert_failure_time;
    }

    public void setCert_failure_time(String cert_failure_time) {
        this.cert_failure_time = cert_failure_time;
    }

    public String getCert_type() {
        return cert_type;
    }

    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getPlat_type() {
        return plat_type;
    }

    public void setPlat_type(String plat_type) {
        this.plat_type = plat_type;
    }

	/**
     * 只插入数据库，不更新缓存，在标准化入库时执行
     */
    @NotDbField
    public ZteResponse  insertNoCache(){
        return RequestStoreExector.getInstance().insertZteRequestInst(this);
    }

    @Override
    @NotDbField
    public <T>T store() {
        ZteInstUpdateRequest<MemberBusiRequest> updateRequest = new ZteInstUpdateRequest<MemberBusiRequest>(); 
        return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
    }

    @Override
    @NotDbField
    public <T> T load(ZteInstQueryRequest instQuery) {
        //构造参数
        QueryResponse<MemberBusiRequest> resp = new QueryResponse<MemberBusiRequest>();
        return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this);
    }

    @Override
    @NotDbField
    public void check() throws ApiRuleException {
        // TODO Auto-generated method stub
        
    }

    @Override
    @NotDbField
    public String getApiMethodName() {
        // TODO Auto-generated method stub
        return null;
    }
	
}
