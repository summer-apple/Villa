package com.zhejianglehua.rzf.entity.weixin.menu;

import javax.persistence.Entity;

@Entity
public class ViewButton extends Button{
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	 
}
