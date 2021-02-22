package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import params.adminuser.req.UserPermissionReq;
import params.adminuser.req.UserRoleReq;
import params.adminuser.resp.UserRoleResp;
import params.member.req.MemberAddReq;
import params.member.req.MemberPasswordReq;
import params.member.resp.MemberAddResp;
import params.member.resp.MemberPasswordResp;
import services.AdminUserInf;
import services.FlowInf;
import services.GoodsCatInf;
import services.MemberInf;
import services.ModHandlerInf;
import services.PermissionInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.ISupplierManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.workflow.core.FlowType;
import com.ztesoft.net.mall.core.workflow.core.InitFlowParam;
import com.ztesoft.net.mall.core.workflow.util.ModVO;

public class SupplierManager extends BaseSupport<Supplier> implements
        ISupplierManager {

    private HttpServletRequest request;
    private PermissionInf permissionServ;
    private AdminUserInf adminUserServ;
    private FlowInf flowServ;
    private ModHandlerInf modInfoHandler;
    private GoodsCatInf goodCatsServ;
    private MemberInf memberServ;
    
    @Override
    public List<Supplier> findSupplieridByAccount_number(String number) {
        String sql = "select t.supplier_id,t.company_name,t.parent_id,t.account_number,t.user_name,t.id_card,t.email,t.phone from es_supplier t where t.account_number=? ";
        List<Supplier> list=null;
        try{
           list=this.daoSupport.queryForList(sql, Supplier.class, number);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List isAbroad() {
        String sql = "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=8886";

        return this.daoSupport.queryForList(sql);
    }

    @Override
    public List showCurrency() {
        String sql = "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=8887";

        return this.daoSupport.queryForList(sql);
    }

    @Override
    public List showEnterpriseType() {
        String sql = "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=8885";

        return this.daoSupport.queryForList(sql);
    }

    @Override
    public List showTicketType() {
        String sql = "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=8884";

        return this.daoSupport.queryForList(sql);
    }

    @Override
    public List showAgentType() {
        String sql = "select p.pkey value,p.pname value_desc from es_dc_public p where p.stype=8883";

        return this.daoSupport.queryForList(sql);
    }

    @Override
    public boolean isCompanyNameExits(String companyName) {
        String sql = "select supplier_id, company_name from ES_SUPPLIER where company_name=? ";
        List list = this.baseDaoSupport.queryForList(sql, companyName);
        return list.size() > 0 ? true : false;
    }

    @Override
    public boolean isAccountNumberExits(String accountNumber) {
        String sql = "select supplier_id, account_number from ES_SUPPLIER where account_number=? ";
        List list = this.baseDaoSupport.queryForList(sql, accountNumber);
        return list.size() > 0 ? true : false;
    }

    @Override
    public String getSupplierSequences() {
        return this.daoSupport.getSequences("S_ES_SUPPLIER");
    }

    @Override
    public String add(Supplier supplier) {
        this.daoSupport.insert("ES_SUPPLIER", supplier);
        return supplier.getSupplier_id();
    }

    @Override
    public Page supplierList(String user_name, String phone, int page, int pageSize) {
        StringBuffer sql = new StringBuffer(
                "SELECT SUPPLIER_ID,COMPANY_NAME,account_number,EMAIL,QQ,USER_NAME,SEX,ID_CARD,PHONE,REGISTER_TIME,supplier_state,supplier_type FROM ES_SUPPLIER  WHERE  DELETEFLAG=0 and supplier_state=1 and supplier_type=1");

        List partm = new ArrayList();

        StringBuffer sqlAccount = new StringBuffer(" ");

//		String currUserId = ManagerUtils.getUserId();
//		
//		if(StringUtils.isNotEmpty(currUserId)){
//			sqlAccount.append(" and userid = ?");
//			partm.add(currUserId);
//		}


        if (StringUtils.isNotEmpty(user_name)) {
            sqlAccount.append(" and user_name like ?");
            partm.add("%" + user_name.trim() + "%");
        }

        if (StringUtils.isNotEmpty(phone)) {
            sqlAccount.append(" and phone like ?");
            partm.add("%" + phone.trim() + "%");
        }

        sqlAccount.append(" order by REGISTER_TIME desc");

        sql.append(sqlAccount);
        return this.baseDaoSupport.queryForPage(sql.toString(), page, pageSize, partm.toArray());
    }

    @Override
    public Page list(String company_name, String user_name, String phone,
                     String state, String supplier_type, String is_administrator, String date_type,
                     String remd, String suppler_id, int page, int pageSize) {
        StringBuffer sql = new StringBuffer(
                "SELECT SUPPLIER_ID,COMPANY_NAME,EMAIL,QQ,USER_NAME,SEX,ID_CARD,PHONE,REGISTER_TIME,supplier_state,supplier_type FROM ES_SUPPLIER  WHERE  DELETEFLAG=0");

        ArrayList partm = new ArrayList();

        StringBuffer sqlAccount = new StringBuffer(" ");
        if (!SupplierStatus.SUPPLIER_STATE_ALL.equals(state)) {
            sqlAccount.append(" and supplier_state=?");
            partm.add(state);
        }

        if (SupplierStatus.DATE_TYPE_1.equals(date_type)) {
            sqlAccount.append(" AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.current(),1)+" ");
        }

        if (SupplierStatus.DATE_TYPE_2.equals(date_type)) {
            sqlAccount.append(" AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(-1),1)+" ");
        }

        if (SupplierStatus.is_administrator_0.equals(is_administrator)) {
            String currUserId = ManagerUtils.getUserId();
            sqlAccount.append(" and userid = ?");
            partm.add(currUserId);
        }
        if (StringUtils.isNotEmpty(company_name)) {
            sqlAccount.append(" and company_name like ?");
            partm.add("%" + company_name.trim() + "%");
        }

        if (StringUtils.isNotEmpty(supplier_type)) {
            sqlAccount.append(" and supplier_type = ?");
            partm.add(supplier_type);
        }


        if (StringUtils.isNotEmpty(user_name)) {
            sqlAccount.append(" and user_name like ?");
            partm.add("%" + user_name.trim() + "%");
        }

        if (StringUtils.isNotEmpty(phone)) {
            sqlAccount.append(" and phone like ?");
            partm.add("%" + phone.trim() + "%");
        }


        if (StringUtils.isNotEmpty(remd)) {
            sqlAccount.append(" and remd =?");
            partm.add(remd);
        }

        if (StringUtils.isNotEmpty(suppler_id)) {
            sqlAccount.append(" and SUPPLIER_ID =?");
            partm.add(suppler_id);
        }


        String countSql = "select count(*) FROM ES_SUPPLIER  WHERE DELETEFLAG=0 "
                + sqlAccount.toString();

        sql.append(sqlAccount);

        Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page,
                pageSize, Supplier.class, countSql, partm.toArray());

        sqlAccount.delete(0, sqlAccount.length());

        return webpage;
    }

    @Override
    public Page findExamineSupplier(int page, int pageSize, String username,
                                    String company_name) {
        StringBuffer sql = new StringBuffer(
                "SELECT SUPPLIER_ID,COMPANY_NAME,EMAIL,QQ,USER_NAME,SEX,ID_CARD,PHONE,REGISTER_TIME,supplier_state FROM ES_SUPPLIER  WHERE SUPPLIER_TYPE=1  and supplier_state=1 AND DELETEFLAG=0 ");

        String countSql = "select count(*) FROM ES_SUPPLIER  WHERE SUPPLIER_TYPE=1  and supplier_state=1 AND DELETEFLAG=0 ";

        if (username != null && !"".equals(username)) {
            sql.append("  and USER_NAME like '%" + username + "%'");
            countSql += " and USER_NAME like '%" + username + "%'";
        }
        if (company_name != null && !"".equals(company_name)) {
            sql.append("  and COMPANY_NAME like '%" + company_name + "%'");
            countSql += " and COMPANY_NAME like '%" + company_name + "%'";
        }
        ArrayList partm = new ArrayList();
        sql.append(" order by REGISTER_TIME desc");
        countSql += " order by REGISTER_TIME desc";
        Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page,
                pageSize, Supplier.class, countSql, partm.toArray());

        sql.delete(0, sql.length());

        return webpage;
    }

    // 删除
    @Override
    public int delete(String id) {
        if (id == null || id.equals("")) {
            return 0;
        } else {
            Map param = new HashMap();
            param.put("deleteflag", 1);
            this.baseDaoSupport.update("es_supplier", param, "supplier_id in ("
                    + id + ")");
            return 1;
        }
    }

    // 注销
    @Override
    public int cancellation(String id) {
        if (id == null || id.equals("")) {
            return 0;
        } else {
            Map param = new HashMap();
            param.put("supplier_state", 2);
            this.baseDaoSupport.update("es_supplier", param, "supplier_id in ("
                    + id + ")");
            return 1;
        }
    }

    @Override
    public Page auditlist(String company_name, String user_name,
                          String supplier_type, String phone, int page, int pageSize) {
        StringBuffer sql = new StringBuffer(
                "SELECT SUPPLIER_ID,COMPANY_NAME,EMAIL,QQ,USER_NAME,SEX,ID_CARD,PHONE,REGISTER_TIME,supplier_state FROM ES_SUPPLIER  WHERE  supplier_state=0 and DELETEFLAG=0");

        ArrayList partm = new ArrayList();

        StringBuffer sqlAccount = new StringBuffer(" ");

        if (StringUtils.isNotEmpty(company_name)) {
            sqlAccount.append(" and company_name like ?");
            partm.add("%" + company_name.trim() + "%");
        }

        if (StringUtils.isNotEmpty(user_name)) {
            sqlAccount.append(" and user_name like ?");
            partm.add("%" + user_name.trim() + "%");
        }

        if (StringUtils.isNotEmpty(supplier_type)) {
            sqlAccount.append(" and supplier_type = ?");
            partm.add(supplier_type);
        }

        if (StringUtils.isNotEmpty(phone)) {
            sqlAccount.append(" and phone like ?");
            partm.add("%" + phone.trim() + "%");
        }

        sqlAccount.append(" order by REGISTER_TIME desc");

        String countSql = "select count(*) FROM ES_SUPPLIER  WHERE supplier_state=0 AND DELETEFLAG=0 "
                + sqlAccount.toString();

        sql.append(sqlAccount);

        Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page,
                pageSize, Supplier.class, countSql, partm.toArray());

        sqlAccount.delete(0, sqlAccount.length());

        return webpage;
    }

    /**
     * 获取待审核还未生效的供货商资料
     */
    @Override
    public Supplier getSupplierSequM1AndState0(String supplier_id) {
        String sql = "select * from ES_SUPPLIER where supplier_id=? and supplier_state='0' ";
        return this.baseDaoSupport.queryForObject(sql, Supplier.class,
                supplier_id);
    }

    /**
     * 审核
     *
     * @return
     */
    @Override
    public int saveAuditSupplier(String supplier_id, String supplier_state) {

        Supplier old = this.getSupplierSequM1AndState0(supplier_id); // 基本信息

        if (old != null) {

            String roles = "";
            UserRoleResp userRoleResp = new UserRoleResp();
            if (SupplierStatus.SUPPLIER_STATE_4.equals(supplier_state)) {// 审核不通过
                if (old.getSupplier_type().equals(SupplierStatus.TYPE_1)) {// 供货商
                    UserRoleReq userRoleReq = new UserRoleReq();
                    userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98763);
                    userRoleReq.setPkey(Consts.DC_PUBLIC_98763_PKEY);

                    userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                    if (userRoleResp != null) {
                        roles = userRoleResp.getRoles();
                    }
                } else {
                    UserRoleReq userRoleReq = new UserRoleReq();
                    userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98764);
                    userRoleReq.setPkey(Consts.DC_PUBLIC_98764_PKEY);

                    userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                    if (userRoleResp != null) {
                        roles = userRoleResp.getRoles();
                    }
                }
            } else if (SupplierStatus.SUPPLIER_STATE_2.equals(supplier_state)) {
                if (old.getSupplier_type().equals(SupplierStatus.TYPE_1)) {// 供货商
                    UserRoleReq userRoleReq = new UserRoleReq();
                    userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98762);
                    userRoleReq.setPkey(Consts.DC_PUBLIC_98762_PKEY);

                    userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                    if (userRoleResp != null) {
                        roles = userRoleResp.getRoles();
                    }
                } else {
                    UserRoleReq userRoleReq = new UserRoleReq();
                    userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98764);
                    userRoleReq.setPkey(Consts.DC_PUBLIC_98764_PKEY);

                    userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                    if (userRoleResp != null) {
                        roles = userRoleResp.getRoles();
                    }
                }
            }

            // old.setLast_update_date(DBTUtil.current());
            old.setSupplier_state(supplier_state);

            UserPermissionReq userPermissionReq = new UserPermissionReq();
            userPermissionReq.setUserid(old.getUserid());
            userPermissionReq.setRoleids(new String[]{roles});

            permissionServ.giveRolesToUser(userPermissionReq);
            // old.setAudit_idea(partner.getAudit_idea());
            //
            // if(partner.getState().equals(Consts.PARTNER_STATE_NORMAL)){//ͨ��
            // old.setSequ(Consts.PARTNER_SEQ_0);//0
            // }else{//��ͨ��
            // old.setSequ(Consts.PARTNER_SEQ_W2);//-2
            // }
            this.editSupplier(old);

            return 1;
        }
        return 0;
    }

    /**
     * 修改供货商状态
     */
    public int editSupplier(Supplier supplier) {
        this.baseDaoSupport.update("ES_SUPPLIER", supplier, " source_from ='"+ManagerUtils.getSourceFrom()+"' and  supplier_id='"
                + supplier.getSupplier_id() + "' and supplier_state='0' ");
        return 1;
    }

    @Override
    public void modifySupplier(String supplier_id, String company_name,
                               String parent_id, String email, String id_card, String other_phone,
                               String qq, String sex, String user_name, String account_number,
                               String phone, String supplier_type) {

        Supplier supplier = new Supplier();
        supplier.setSupplier_id(supplier_id);
        supplier.setCompany_name(company_name);
        supplier.setParent_id(parent_id);
        supplier.setEmail(email);
        supplier.setId_card(id_card);
        supplier.setOther_phone(other_phone);
        supplier.setQq(qq);
        supplier.setSex(sex);
        supplier.setUser_name(user_name);
        supplier.setAccount_number(account_number);
        supplier.setPhone(phone);
        supplier.setSupplier_type(supplier_type);
        this.baseDaoSupport.update("ES_SUPPLIER", supplier, "supplier_id='"
                + supplier_id + "'");
    }

    /**
     * 获取供货商
     */
    @Override
    public List<Supplier> getSupplierByCatId(String cat_id) {
        String whereCond = "";
        if (!StringUtil.isEmpty(cat_id)) {
            String cond = goodCatsServ._getComplexGoodsByCatId(cat_id);
            whereCond = " and supplier_id in (select g.supper_id from es_goods g where 1=1 " + cond + ")";
        }
        String sql = "select * from ES_SUPPLIER where SUPPLIER_STATE ='1' " + whereCond;

        return this.baseDaoSupport.queryForList(sql, Supplier.class, null);
    }

    /**
     * 根据ID得到供货商
     */
    @Override
    public Supplier getSupplierById(String id) {
        String sql = "select * from ES_SUPPLIER where supplier_id=? ";
        return this.baseDaoSupport.queryForObject(sql, Supplier.class, id);
    }

    /**
     * 根据登录用户获取供货商
     */
    @Override
    public Supplier getSupplierByUserId(String userid) {
        String sql = "select * from ES_SUPPLIER where userid=? ";
        return this.baseDaoSupport.queryForObject(sql, Supplier.class, userid);
    }

    /**
     * 是否已操作
     */
    @Override
	public boolean isSaveExits(String supplierId, String supplierState) {
        StringBuffer sb = new StringBuffer(1000);
        sb.append("select p.supplier_state,p.supplier_id, p.user_name ");
        sb.append(" from ES_SUPPLIER p");
        sb.append(" where");
        sb.append("  p.supplier_id=? ");
        if (supplierState != null) {
            sb.append(" and p.supplier_state=" + supplierState);
        }
        List list = this.daoSupport.queryForList(sb.toString(), supplierId);
        sb.delete(0, sb.length());
        return list.size() > 0 ? true : false;
    }

    @Override
    public String dataDeal(String supplier_id, String company_name,
                           String parent_id, String email, String id_card, String other_phone,
                           String password, String qq, String sex, String user_name,
                           String account_number, String phone, String adminUserId,
                           String supplier_type) {
        Supplier supplier = new Supplier();
        supplier.setSupplier_id(supplier_id);
        supplier.setCompany_name(company_name);
        supplier.setParent_id(parent_id);
        supplier.setEmail(email);
        supplier.setId_card(id_card);
        supplier.setOther_phone(other_phone);
        supplier.setPassword(StringUtil.md5(password));
        supplier.setQq(qq);
        supplier.setSex(sex);
        supplier.setUser_name(user_name);
        supplier.setAccount_number(account_number);
        supplier.setPhone(phone);
        supplier.setRegister_time(DBTUtil.current());
        supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_1);
        supplier.setSupplier_type(supplier_type);
        supplier.setUserid(adminUserId);
        return add(supplier);
    }

    /**
     * 查询优质供货商
     */
    @Override
	@SuppressWarnings("unused")
    public Page searchHotSupper(int page, int pageSize) {

        String sql = "select s.company_name,s.supplier_id,NVL(sum(i.ship_num),0) as sales_count from es_supplier s left" +
                " join es_goods g on(s.supplier_id=g.supper_id) left join es_order_items i on(i.goods_id=g.goods_id) WHERE " +
                "S.Source_From=? group by s.company_name,s.supplier_id order by sales_count DESC";
        String countSql = "select count(1) from es_supplier WHERE Source_From=?";
        Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize,
                Supplier.class, countSql, new Object[]{ManagerUtils.getSourceFrom()});
        return webpage;
    }

    @Override
	public Supplier findSupplierIdBycurrUserId(String currUserId) {
        String sql = "SELECT a.* FROM ES_SUPPLIER A, ES_ADMINUSER B WHERE A.USERID = B.USERID"
                + " AND a.userid=? ";
        return this.baseDaoSupport.queryForObject(sql, Supplier.class,
                currUserId);
    }

    @Override
	public int findSupplierByUserName(String userName) {
        String sql = "SELECT supplier_state FROM es_supplier a,es_adminuser b WHERE a.userid =b.userid AND  b.username=?";

        List list = this.daoSupport.queryForList(sql, userName);

        if (list.size() > 0) {
            Map map = (Map) list.get(0);

            if (map.get("supplier_state").equals(
                    SupplierStatus.SUPPLIER_STATE_4)) {// 审核不通过
                return -1;
            }
            if (map.get("supplier_state").equals(
                    SupplierStatus.SUPPLIER_STATE_2)) {// 正常
                return 1;
            }

            if (map.get("supplier_state").equals(
                    SupplierStatus.SUPPLIER_STATE_1)) {// 待审核
                return 2;
            }
        }
        return 0;// 不存在
    }

    @Override
	public void addMember(Supplier supplier) {
        request = ThreadContextHolder.getHttpRequest();
        Member member = new Member();

        request = ThreadContextHolder.getHttpRequest();
        String registerip = request.getRemoteAddr();

        String memberId = this.baseDaoSupport.getSequences("s_es_adminuser");
        member.setMember_id(memberId);
        member.setName(supplier.getUser_name());
        member.setSex(Integer.parseInt(supplier.getSex()));
        member.setTel(supplier.getPhone());
        member.setQq(supplier.getQq());
        member.setLv_id("2");
        member.setUname(supplier.getAccount_number());
        member.setPassword(supplier.getPassword());
        member.setEmail(supplier.getEmail());
        member.setRegtime(DBTUtil.current());
        member.setRegisterip(registerip);

        MemberAddReq req = new MemberAddReq();
		
		req.setMember(member);
		MemberAddResp addResp = memberServ.addNewMember(req);
		
		MemberPasswordReq req1 = new MemberPasswordReq();
		req1.setMember_id(memberId);
		req1.setPassword(supplier.getPassword());
		MemberPasswordResp updResp = memberServ.updateMemberPassword(req1);
		
    }

    // 基本信息的修改
    @Override
	public void edit(Supplier supplier) {
        // if (SupplierStatus.SUPPLIER_STATE_2
        // .equals(supplier.getSupplier_state())) {// 如果是审核后修改
        //
        // } else {
        // supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_1);
        // this.baseDaoSupport.update("es_supplier", supplier,
        // " supplier_id='" + supplier.getSupplier_id()
        // + "' and supplier_state='-1'");
        // }

        // 查找审核字段
        // String field_name ="";
        // List columnAudit=this.columnAuditList();
        // boolean flag=false;

        Map supplierMap = null;

        supplierMap = ReflectionUtil.po2Map(supplier);

        List<ModVO> modInfos = new ArrayList<ModVO>();
        ModVO modVO = new ModVO();

        modVO.setTableName("ES_SUPPLIER");
        modVO.setObj(supplier);

        modInfos.add(modVO);

       /* ModParams param = new ModParams(FlowType.SUPP_MOD_ESSENTIAL,
                ManagerUtils.getAdminUser().getUserid(), supplier
                .getSupplier_id(), modInfos);*/

        //this.modInfoHandler.checkAndStartFlow(param);

        if (modVO.getChangedFields() != null) {
            for (String afield_name : modVO.getChangedFields()) {
                if (StringUtils.isNotEmpty((String) supplierMap
                        .get(afield_name))) {
                    supplierMap.remove(afield_name);
                }
            }
            supplierMap.put("supplier_state", 0);
        }

        this.baseDaoSupport.update("es_supplier", supplierMap, "supplier_id='"
                + supplierMap.get("supplier_id") + "'");
    }

    @Override
	public void registerApply(String adminUserId, String supplier_id) {
        // 起流程
    	flowServ.startFlow(new InitFlowParam(FlowType.REGISTER_APPLY,
                adminUserId, supplier_id));
    }

    @Override
    public Supplier findSupplierById(String supplier) {
        String sql = "select * from  ES_SUPPLIER where SUPPLIER_ID=?";
        List<Supplier> suppliers = this.daoSupport.queryForList(sql, Supplier.class, supplier);
        return (suppliers==null || suppliers.size()==0)?null:suppliers.get(0);
    }

    /**
     * 查找审核字段
     *
     * @param partid
     * @param sequR
     * @return
     */
    public List columnAuditList() {

        List list = this.baseDaoSupport
                .queryForList("select p.pkey,p.stype,p.pname  from es_dc_public p where p.stype=8819 order by p.codea");

        return list;
    }

    /*
     *
     * 待审核供货商
     */
    @Override
	public int waitAuditSupplier() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE t.supplier_state=0 AND t.supplier_type=1 and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 待审核供货商
     */
    @Override
	public int waitAuditAgency() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=0 AND supplier_type=2 and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 合作中的供货商
     */
    @Override
	public int cooperationSupplier() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=1 AND supplier_type=1 and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 今已终止供货商
     */
    @Override
	public int endSupplier() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=2 AND supplier_type=1 and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 今日新增供货商
     */
    @Override
	public int todayNewSupplier() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=0 AND supplier_type=1 AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.current(), 1)+" and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 昨日新增供货商
     */
    @Override
	public int yestodayNewSupplier() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=0 AND supplier_type=1 AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(-1),1)+" and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 今日新增经销商
     */
    @Override
	public int todayNewAgency() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*) FROM es_supplier t WHERE supplier_state=0 AND supplier_type=2 AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.current(), 1)+" and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    /*
     *
     * 昨日新增经销商
     */
    @Override
	public int yestodayNewAgency() {
        AdminUser au = ManagerUtils.getAdminUser();
        /**
         * 供货商管理员ID
         */
        String userid = au.getUserid();
        if (Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
            userid = au.getParuserid();
        boolean isSupplier = ((Consts.SUPPLIER_FOUNDER == au.getFounder()) || (5 == au
                .getFounder()));
        String sql = "SELECT COUNT(*)  FROM es_supplier t WHERE supplier_state=0 AND supplier_type=2 AND "+DBTUtil.to_char("register_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(-1),1)+" and DELETEFLAG=0";
        if (isSupplier) {
            sql += " and t.userid='" + userid + "'";
        }
        int totalAmount = this.baseDaoSupport.queryForInt(sql);
        return totalAmount;
    }

    @Override
	public void RegisterApplyAudit(String supplier_id) {
        Supplier supplier = this.getSupplierById(supplier_id);
        String roles = "";
        UserRoleResp userRoleResp = new UserRoleResp();
        if (supplier != null) {
            if (supplier.getSupplier_type().equals(SupplierStatus.TYPE_1)) {// 供货商
                UserRoleReq userRoleReq = new UserRoleReq();
                userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98762);
                userRoleReq.setPkey(Consts.DC_PUBLIC_98762_PKEY);

                userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                if (userRoleResp != null) {
                    roles = userRoleResp.getRoles();
                }
            } else {// 经销商
                UserRoleReq userRoleReq = new UserRoleReq();
                userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98764);
                userRoleReq.setPkey(Consts.DC_PUBLIC_98764_PKEY);

                userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
                if (userRoleResp != null) {
                    roles = userRoleResp.getRoles();
                }
                if (supplier.getSupplier_state().equals(
                        SupplierStatus.SUPPLIER_STATE_4)) {
                    this.addMember(supplier);
                }
            }

            supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_2);

            UserPermissionReq userPermissionReq = new UserPermissionReq();
            userPermissionReq.setUserid(supplier.getUserid());
            userPermissionReq.setRoleids(new String[]{roles});

            permissionServ.giveRolesToUser(userPermissionReq);

            this.baseDaoSupport.update("ES_SUPPLIER", supplier,
                    " supplier_id='" + supplier.getSupplier_id() + "'");
        }
    }
    
    @Override
    public List listSupplierSalesRank(){
    	String sql = "select * from (select a.supplier_id, a.company_name,a.email,a.qq,a.phone, sum(b.buy_count) buy_count "+
    				 " from es_supplier a, es_goods b "+
    				" where a.supplier_id = b.supper_id "+
    				" and a.deleteflag = 0 and a.supplier_state='1' "+
    				" group by a.supplier_id, a.company_name,a.email,a.qq,a.phone "+
    				" order by sum(b.buy_count) desc) "+
    				" where rownum <= 10";
    	return this.baseDaoSupport.queryForList(sql, null);
    }
    
    @Override
	public List getSupplierByCond(HashMap params) {
    	String sql = "select * from es_supplier t where t.deleteflag=0 and t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.supplier_state='1' ";
    	
		String company_name = (String) params.get("company_name");
		if(!StringUtil.isEmpty(company_name)){
			sql = sql + " and t.company_name like '%"+company_name+"%'";
		}
		List suppliers = this.baseDaoSupport.queryForList(sql, Supplier.class,null);
		
		return suppliers;
	}

    public PermissionInf getPermissionServ() {
        return permissionServ;
    }

    public void setPermissionServ(PermissionInf permissionServ) {
        this.permissionServ = permissionServ;
    }

    public AdminUserInf getAdminUserServ() {
        return adminUserServ;
    }

    public void setAdminUserServ(AdminUserInf adminUserServ) {
        this.adminUserServ = adminUserServ;
    }


    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public ModHandlerInf getModInfoHandler() {
        return modInfoHandler;
    }

    public void setModInfoHandler(ModHandlerInf modInfoHandler) {
        this.modInfoHandler = modInfoHandler;
    }

	public GoodsCatInf getGoodCatsServ() {
		return goodCatsServ;
	}

	public void setGoodCatsServ(GoodsCatInf goodCatsServ) {
		this.goodCatsServ = goodCatsServ;
	}

	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

	public FlowInf getFlowServ() {
		return flowServ;
	}

	public void setFlowServ(FlowInf flowServ) {
		this.flowServ = flowServ;
	}

	@Override
	public Page list_check(String companyName, String username, String qq,
			String email,String beginTime,String endTime,int page, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
                "SELECT SUPPLIER_ID, COMPANY_NAME, PARENT_ID, ACCOUNT_NUMBER, EMAIL, QQ, USER_NAME, SEX, ID_CARD, PHONE,");
		sql.append(" REGISTER_TIME, DECODE(STATE,'0','待审核','1','审核通过','2','已注销','-1','审核不通过') STATE, USERID FROM ES_SUPPLIER_AUDIT  WHERE STATE='0' AND SOURCE_FROM='FJ' ");

        ArrayList partm = new ArrayList();

        StringBuffer sqlAccount = new StringBuffer(" ");
        if(StringUtils.isNotEmpty(companyName)){
        	sqlAccount.append(" AND COMPANY_NAME=?");
        	partm.add(companyName);
        }
        if(StringUtils.isNotEmpty(username)){
        	sqlAccount.append(" AND USER_NAME=?");
        	partm.add(username);
        }
        if(StringUtils.isNotEmpty(qq)){
        	sqlAccount.append(" AND QQ=?");
        	partm.add(qq);
        }
        if(StringUtils.isNotEmpty(email)){
        	sqlAccount.append(" AND EMAIL=?");
        	partm.add(email);
        }
        if(StringUtils.isNotEmpty(beginTime)){
        	sqlAccount.append(" AND REGISTER_TIME > to_date(?,'yyyy-mm-dd')");
        	partm.add(beginTime);
        }
        if(StringUtils.isNotEmpty(endTime)){
        	sqlAccount.append(" AND REGISTER_TIME < to_date(?,'yyyy-mm-dd')");
        }

        String countSql = "select count(*) FROM ES_SUPPLIER_AUDIT  WHERE STATE='0' AND SOURCE_FROM='FJ' "
                + sqlAccount.toString();

        sql.append(sqlAccount);

        Page webpage = this.baseDaoSupport.queryForCPage(sql.toString(), page,
                pageSize, Supplier.class, countSql, partm.toArray());

        sqlAccount.delete(0, sqlAccount.length());

        return webpage;
	}

	@Override
	public boolean setSupplierState(String supplier_id) {
		// TODO Auto-generated method stub
		String sql = "UPDATE ES_SUPPLIER_AUDIT SET STATE='1' WHERE SUPPLIER_ID='"+supplier_id+"'";
		this.baseDaoSupport.execute(sql);
		return true;
	}

}
