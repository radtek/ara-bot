package com.zhuiyi.model.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class FeedbackLogInputDto implements Serializable {
	private static final long serialVersionUID = 1L;
	public String msg;
    public int code ;
    public String data ;






}
