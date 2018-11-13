package com.zhuiyi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/31
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FeedbackBackId.class)
@Table(name = "t_feedback_back")
public class FeedbackBack implements Serializable {
    /**
     * id  db_column: id
     */
    @Id
    @Column(name = "id", length = 20, nullable = false)
    private Long id;
    /**
     * appid  db_column: appid
     */
    @Id
    @Column(name = "appid", length = 30, nullable = false)
    private String appid;
    /**
     * exactTime  db_column: exact_time
     */
    @NotNull
    @Column(name = "exact_time", length = 19, nullable = false)
    private Date exactTime;
    /**
     * isRedo  db_column: is_redo
     */
    @Max(127)
    @Column(name = "is_redo", length = 3)
    private Integer isRedo;
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
    /**
     * data  db_column: data
     */
    @NotBlank @Length(max=4000)
    @Column(name = "data", length = 4000, nullable = false)
    private String data;
}


