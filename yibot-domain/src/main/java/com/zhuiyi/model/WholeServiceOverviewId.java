
package com.zhuiyi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/16
 * description:
 * own: zhuiyi
 */

@Data
@NoArgsConstructor
public class WholeServiceOverviewId implements Serializable {
    public Long id;
    public String appid;
}