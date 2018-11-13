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
@IdClass(TouchOverviewId.class)
@Table(name = "t_touch_overview")
public class TouchOverview implements Serializable {
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
     * dateSign  db_column: date_sign
     */
    @NotBlank @Length(max=10)
    @Column(name = "date_sign", length = 10, nullable = false)
    private String dateSign;
    /**
     * isTotal  db_column: is_total
     */
    @NotNull @Max(127)
    @Column(name = "is_total", length = 3, nullable = false)
    private Integer isTotal;
    /**
     * cid  db_column: cid
     */
    @Length(max=200)
    @Column(name = "cid", length = 200)
    private String cid;
    /**
     * client  db_column: client
     */
    @Length(max=200)
    @Column(name = "client", length = 200)
    private String client;
    /**
     * eid  db_column: eid
     */
    @Length(max=200)
    @Column(name = "eid", length = 200)
    private String eid;
    /**
     * lables  db_column: lables
     */
    @Length(max=200)
    @Column(name = "lables", length = 200)
    private String lables;
    /**
     * im  db_column: im
     */
    @Length(max=200)
    @Column(name = "im", length = 200)
    private String im;
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


