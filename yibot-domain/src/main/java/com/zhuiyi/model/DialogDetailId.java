
package com.zhuiyi.model;

import lombok.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/01
 * description:
 * own: zhuiyi
 */

@Data
@NoArgsConstructor
public class DialogDetailId implements Serializable {
    public String id;
    public String appid;
    public String dateMonth;
    public Date exactTime;
}