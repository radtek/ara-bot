
package com.zhuiyi.model;

import lombok.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/31
 * description:
 * own: zhuiyi
 */

@Data
@NoArgsConstructor
public class FeedbackBackId implements Serializable {
    public Long id;
    public String appid;
}