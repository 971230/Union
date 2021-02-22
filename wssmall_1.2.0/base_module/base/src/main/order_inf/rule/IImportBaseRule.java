package rule;

import zte.params.req.MidDataProcessReq;
import zte.params.resp.MidDataProcessResp;

public interface IImportBaseRule {

	public MidDataProcessResp process(MidDataProcessReq req);
}
