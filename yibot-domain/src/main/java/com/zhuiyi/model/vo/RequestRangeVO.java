package com.zhuiyi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author tree
 * @version 1.0
 * date: 2018/8/20 11:51
 * description: xxx
 * own:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRangeVO {
    /**
     * 开始时间
     */
    @NotBlank(message="startDate cannot be empty")
    private String startDate;
    /**
     * 结束时间
     */
    @NotBlank(message="endDate cannot be empty")
    private String endDate;
    /**
     * 业务标识
     */
    @NotBlank(message="bId cannot be empty")
    private String bId;
    /**
     * 渠道标识 {"cid":["user","test01"], "eid":["app","weixin"], "client":[], "labels":[], "im":[]}
     */
    private Map<String,Object> channels;
}
