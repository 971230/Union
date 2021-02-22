package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.File;



/**
 * Brand generated by MyEclipse - Hibernate Tools
 */

/**
 * @author lzf
 * version 1.0<br/>
 * 2010-6-17&nbsp;下午02:31:13
 */
public class Brand  implements java.io.Serializable {


    // Fields    

     private String brand_id;
     private String name;
     private String logo;
     private String keywords;
     private String brief;
     private String url;
     private Integer disabled;
     private String brand_code;
     private String brand_outer_code;
     private String brand_group_code;
     

    private File file;
    private String fileFileName;
    
    // Constructors

    /** default constructor */
    public Brand() {
    }

   
    // Property accessors

 

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return this.logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return this.brief;
    }
    
    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }


	public Integer getDisabled() {
		return disabled;
	}


	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}




	public String getBrand_id() {
		return brand_id;
	}




	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	
	@NotDbField
	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}

	
	@NotDbField
	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getBrand_code() {
		return brand_code;
	}


	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	
	public String getBrand_outer_code() {
		return brand_outer_code;
	}


	public void setBrand_outer_code(String brand_outer_code) {
		this.brand_outer_code = brand_outer_code;
	}

	
	public String getBrand_group_code() {
		return brand_group_code;
	}


	public void setBrand_group_code(String brand_group_code) {
		this.brand_group_code = brand_group_code;
	}

 


}