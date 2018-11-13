package com.zhuiyi.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.*;
import lombok.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import java.math.BigDecimal;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/08/14
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(DialogAnswerId.class)
@Table(name = "t_dialog_answer")
public class DialogAnswer implements Serializable {
    /**
     * id  db_column: id
     */
    @Id
    @Column(name = "id", length = 100, nullable = false)
    private String id;
    /**
     * appid  db_column: appid
     */
    @Id
    @Column(name = "appid", length = 30, nullable = false)
    private String appid;
    /**
     * answer  db_column: answer
     */
    @Length(max=65535)
    @Column(name = "answer", length = 65535)
    private String answer;
    /**
     * gmtCreate  db_column: gmt_create
     */
    @NotNull 
    @Column(name = "gmt_create", length = 19, nullable = false)
    private Date gmtCreate;
    /**
     * gmtModified  db_column: gmt_modified
     */
    @NotNull 
    @Column(name = "gmt_modified", length = 19, nullable = false)
    private Date gmtModified;
}


