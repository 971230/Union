package com.zte.cbss.vo;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SigninUserInfo implements UserDetails {

	private static final long serialVersionUID = 4357842500318453322L;

	private String userAccount;//登录用户账号
	
	private String username;//用户名
	
	private String password;//登录密码
	
	private final Set<GrantedAuthority> authorities;
	
    private final boolean accountNonExpired;			//账号未过期
    private final boolean accountNonLocked;				//账号未锁定
    private final boolean credentialsNonExpired;		//证书未过期
    private final boolean enabled;						//是否可用  
    
    public SigninUserInfo(String userAccount,String username,String password,boolean enabled, boolean accountNonExpired,boolean credentialsNonExpired, boolean accountNonLocked ,Set<GrantedAuthority> authorities) {
    	this.userAccount = userAccount;
    	this.username = username;
        this.password = password;
    	this.enabled = enabled; 
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }
    
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
}
