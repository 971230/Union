package vo;

/**
 * 
 * @author wui
 * 
 * 
 */
public class GoodsPlugin {

    private String stype_id;
	private String stype_code;
	private String plugin_name;
	private String plugin_class;
	private String tag_num;
	private String page_name;
	private String source_from;
	private String disabled;
	private String author;
	private String version;

    public String getStype_id() {
        return stype_id;
    }

    public void setStype_id(String stype_id) {
        this.stype_id = stype_id;
    }

    public String getStype_code() {
		return stype_code;
	}

	public void setStype_code(String stype_code) {
		this.stype_code = stype_code;
	}

	public String getPlugin_name() {
		return plugin_name;
	}

	public void setPlugin_name(String plugin_name) {
		this.plugin_name = plugin_name;
	}

	public String getPlugin_class() {
		return plugin_class;
	}

	public void setPlugin_class(String plugin_class) {
		this.plugin_class = plugin_class;
	}

	public String getTag_num() {
		return tag_num;
	}

	public void setTag_num(String tag_num) {
		this.tag_num = tag_num;
	}

	public String getPage_name() {
		return page_name;
	}

	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
