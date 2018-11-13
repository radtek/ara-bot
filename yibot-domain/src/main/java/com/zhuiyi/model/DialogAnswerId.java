
package com.zhuiyi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/14
 * description:
 * own: zhuiyi
 */

@Data
@NoArgsConstructor
public class DialogAnswerId implements Serializable {
    public String id;
    public String appid;
}