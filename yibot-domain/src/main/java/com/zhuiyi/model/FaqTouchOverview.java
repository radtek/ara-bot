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
@IdClass(FaqTouchOverviewId.class)
@Table(name = "t_faq_touch_overview")
public class FaqTouchOverview implements Serializable {
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
     * faqId  db_column: faq_id
     */
    @NotNull @Max(9999999999L)
    @Column(name = "faq_id", length = 10, nullable = false)
    private Integer faqId;
    /**
     * faq  db_column: faq
     */
    @Length(max=500)
    @Column(name = "faq", length = 500)
    private String faq;
    /**
     * categoryId  db_column: category_id
     */
    @Length(max=20)
    @Column(name = "category_id", length = 20)
    private String categoryId;
    /**
     * touchNum  db_column: touch_num
     */
    @Max(9999999999L)
    @Column(name = "touch_num", length = 10)
    private Integer touchNum;
    /**
     * directNum  db_column: direct_num
     */
    @Max(9999999999L)
    @Column(name = "direct_num", length = 10)
    private Integer directNum;
    /**
     * recommendNum  db_column: recommend_num
     */
    @Max(9999999999L)
    @Column(name = "recommend_num", length = 10)
    private Integer recommendNum;
    /**
     * recommendAnsNum  db_column: recommend_ans_num
     */
    @Max(9999999999L)
    @Column(name = "recommend_ans_num", length = 10)
    private Integer recommendAnsNum;
    /**
     * clickGood  db_column: click_good
     */
    @Max(9999999999L)
    @Column(name = "click_good", length = 10)
    private Integer clickGood;
    /**
     * clickBad  db_column: click_bad
     */
    @Max(9999999999L)
    @Column(name = "click_bad", length = 10)
    private Integer clickBad;
    /**
     * touchRate  db_column: touch_rate
     */
    @Length(max=30)
    @Column(name = "touch_rate", length = 30)
    private String touchRate;
    /**
     * zdZrg  db_column: zd_zrg
     */
    @Max(9999999999L)
    @Column(name = "zd_zrg", length = 10)
    private Integer zdZrg;
    /**
     * bdZrg  db_column: bd_zrg
     */
    @Max(9999999999L)
    @Column(name = "bd_zrg", length = 10)
    private Integer bdZrg;
    /**
     * totalZrg  db_column: total_zrg
     */
    @Max(9999999999L)
    @Column(name = "total_zrg", length = 10)
    private Integer totalZrg;
    /**
     * clickGoodRate  db_column: click_good_rate
     */
    @Length(max=30)
    @Column(name = "click_good_rate", length = 30)
    private String clickGoodRate;
    /**
     * clickBadRate  db_column: click_bad_rate
     */
    @Length(max=30)
    @Column(name = "click_bad_rate", length = 30)
    private String clickBadRate;
    /**
     * bizType  db_column: biz_type
     */
    @Length(max=10)
    @Column(name = "biz_type", length = 10)
    private String bizType;
    /**
     * totalClick  db_column: total_click
     */
    @Max(9999999999L)
    @Column(name = "total_click", length = 10)
    private Integer totalClick;
    /**
     * zrgRate  db_column: zrg_rate
     */
    @Length(max=30)
    @Column(name = "zrg_rate", length = 30)
    private String zrgRate;
    /**
     * labels  db_column: labels
     */
    @Length(max=100)
    @Column(name = "labels", length = 100)
    private String labels;
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


