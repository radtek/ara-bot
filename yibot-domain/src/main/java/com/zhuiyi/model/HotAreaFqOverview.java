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
 * date: 2018/07/31
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(HotAreaFqOverviewId.class)
@Table(name = "t_hot_area_fq_overview")
public class HotAreaFqOverview implements Serializable {
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
     * areaType  db_column: area_type
     */
    @Max(127)
    @Column(name = "area_type", length = 3)
    private Integer areaType;
    /**
     * areaName  db_column: area_name
     */
    @Length(max=10)
    @Column(name = "area_name", length = 10)
    private String areaName;
    /**
     * faqId  db_column: faq_id
     */
    @Max(9999999999L)
    @Column(name = "faq_id", length = 10)
    private Integer faqId;
    /**
     * faq  db_column: faq
     */
    @Length(max=200)
    @Column(name = "faq", length = 200)
    private String faq;
    /**
     * visitNum  db_column: visit_num
     */
    @NotNull @Max(9999999999L)
    @Column(name = "visit_num", length = 10, nullable = false)
    private Integer visitNum;
    /**
     * visitTrend  db_column: visit_trend
     */
    @NotBlank @Length(max=20)
    @Column(name = "visit_trend", length = 20, nullable = false)
    private String visitTrend;
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


