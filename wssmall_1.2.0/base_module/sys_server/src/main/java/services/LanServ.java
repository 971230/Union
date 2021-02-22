package services;

import java.util.List;

import javax.annotation.Resource;

import com.ztesoft.net.app.base.core.model.Lan;
import com.ztesoft.net.mall.core.service.ILanManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 09:28
 * To change this template use File | Settings | File Templates.
 */
public class LanServ implements LanInf {
    @Resource
    private ILanManager lanManager;

    @Override
    public List<String> list(String lanids) {
        return this.lanManager.list(lanids);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveRels(String goodsid, Integer[] lanids) {
        this.lanManager.saveRels(goodsid,lanids);
    }

    @Override
    public List<Lan> listEdit() {
        return this.lanManager.listEdit();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Lan> listLan() {
        return this.lanManager.listLan();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String add(Lan lan) {
        return this.lanManager.add(lan);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String lanid) {
        this.lanManager.delete(lanid);
    }

    @Override
    public void update(Lan lan) {
       this.lanManager.update(lan);
    }

    @Override
    public String getLanNameByID(String lan_id) {
        return this.lanManager.getLanNameByID(lan_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Lan getLanByID(String lan_id) {
        return this.lanManager.getLanByID(lan_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List queryTreeList(String arg) {
        return this.lanManager.queryTreeList(arg);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List queryFirstList() {
        return this.lanManager.queryFirstList();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
