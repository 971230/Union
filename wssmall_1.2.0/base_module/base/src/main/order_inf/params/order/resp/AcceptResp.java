package params.order.resp;

import java.util.ArrayList;
import java.util.List;

import params.ZteResponse;
import vo.AcceptVo;

/**
 * 受理返回表
 * @author wu.i
 *
 */
public class AcceptResp extends ZteResponse {
	
	List<AcceptVo> acceptVos  = new ArrayList<AcceptVo>();
	
	public List<AcceptVo> getAcceptVos() {
		return acceptVos;
	}
	public void setAcceptVos(List<AcceptVo> acceptVos) {
		this.acceptVos = acceptVos;
	}
	
	public void addAcceptVo(String table_name,String inst_id){
		acceptVos.add(new AcceptVo(table_name,inst_id));
	}
	
	
}
