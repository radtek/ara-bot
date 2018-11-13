
package com.zhuiyi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/31
 * description:
 * own: zhuiyi
 */

@Data
@NoArgsConstructor
public class HotFaqOverviewId implements Serializable {
    public Long id;
    public String appid;
}