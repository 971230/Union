package params.resp;

import params.ZteResponse;

/**
 * Esearch
 * 
 * @author he.qilong
 *
 */
public class EsearchAddResp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8859159064879566926L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsearchAddResp [id=");
		builder.append(id);
		builder.append(", getError_code()=");
		builder.append(getError_code());
		builder.append(", getError_msg()=");
		builder.append(getError_msg());
		builder.append(", getError_stack_msg()=");
		builder.append(getError_stack_msg());
		builder.append("]");
		return builder.toString();
	}

}
