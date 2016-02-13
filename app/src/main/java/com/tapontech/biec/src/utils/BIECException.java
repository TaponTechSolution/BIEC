package com.tapontech.biec.src.utils;

import java.util.ArrayList;
import java.util.List;

public class BIECException extends RuntimeException{
	protected List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();


	public BIECException() {

	}

	public ErrorInfo addInfo(ErrorInfo info){
		this.errorInfoList.add(info);
		return info;
	}

	public ErrorInfo addInfo(){
		ErrorInfo info = new ErrorInfo();
		this.errorInfoList.add(info);
		return info;
	}

	public List<ErrorInfo> getErrorInfoList() {
		return errorInfoList;
	}
}
