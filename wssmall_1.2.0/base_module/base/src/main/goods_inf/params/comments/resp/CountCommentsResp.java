package params.comments.resp;

import params.ZteResponse;

public class CountCommentsResp extends ZteResponse {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
