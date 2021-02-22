package zte.net.ecsord.params.ecaop.req.vo;

import java.util.List;

/**
 * @author zengxianlian
 * @version BSS
 *
 */
public class BSSVacInfosVo {
	private List<BSSVacinfoVo> Vacinfo;

	public List<BSSVacinfoVo> getVacinfo() {
		if (Vacinfo==null || Vacinfo.size() <= 0) return null;
		return Vacinfo;
	}

	public void setVacinfo(List<BSSVacinfoVo> vacinfo) {
		this.Vacinfo = vacinfo;
	}
}
