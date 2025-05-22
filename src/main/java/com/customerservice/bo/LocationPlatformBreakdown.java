package com.customerservice.bo;

public class LocationPlatformBreakdown {
    private long mobile;
    private long web;

    public LocationPlatformBreakdown() {}

    public LocationPlatformBreakdown(long mobile, long web) {
        this.mobile = mobile;
        this.web = web;
    }

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public long getWeb() {
		return web;
	}

	public void setWeb(long web) {
		this.web = web;
	}

}
