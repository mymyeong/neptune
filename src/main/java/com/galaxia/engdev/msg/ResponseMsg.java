package com.galaxia.engdev.msg;

public interface ResponseMsg {

	public void setResponseCode(String responseCode);

	public void setResponseMsg(String responseMsg);

	public void setDetailCode(String detailCode);

	public void setDetailMsg(String detailMsg);

	public String getResponseCode();

	public String getResponseMsg();

	public String getDetailCode();

	public String getDetailMsg();
}
