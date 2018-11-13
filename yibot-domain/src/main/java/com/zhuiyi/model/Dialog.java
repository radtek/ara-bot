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
 * date: 2018/08/01
 * description:
 * own: zhuiyi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(DialogId.class)
@Table(name = "t_dialog")
public class Dialog implements Serializable {
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
     * dateMonth  db_column: date_month
     */
    @Id
    @Column(name = "date_month", length = 10, nullable = false)
    private String dateMonth;
    /**
     * exactTime  db_column: exact_time
     */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exact_time", length = 19, nullable = false)
    private Date exactTime;
    /**
     * dateSign  db_column: date_sign
     */
    @NotBlank @Length(max=10)
    @Column(name = "date_sign", length = 10, nullable = false)
    private String dateSign;
    /**
     * sessionId  db_column: session_id
     */
    @Length(max=100)
    @Column(name = "session_id", length = 100)
    private String sessionId;
    /**
     * searchId  db_column: search_id
     */
    @Length(max=50)
    @Column(name = "search_id", length = 50)
    private String searchId;
    /**
     * answerType  db_column: answer_type
     */
    @Length(max=10)
    @Column(name = "answer_type", length = 10)
    private String answerType;
    /**
     * faqNum  db_column: faq_num
     */
    @Max(127)
    @Column(name = "faq_num", length = 3)
    private Integer faqNum;
    /**
     * retcode  db_column: retcode
     */
    @Max(127)
    @Column(name = "retcode", length = 3)
    private Integer retcode;
    /**
     * tenMin  db_column: ten_min
     */
    @NotBlank @Length(max=10)
    @Column(name = "ten_min", length = 10, nullable = false)
    private String tenMin;
    /**
     * source  db_column: source
     */
    @Length(max=30)
    @Column(name = "source", length = 30)
    private String source;
    /**
     * account  db_column: account
     */
    @Length(max=100)
    @Column(name = "account", length = 100)
    private String account;
    /**
     * country  db_column: country
     */
    @Length(max=50)
    @Column(name = "country", length = 50)
    private String country;
    /**
     * province  db_column: province
     */
    @Length(max=50)
    @Column(name = "province", length = 50)
    private String province;
    /**
     * city  db_column: city
     */
    @Length(max=50)
    @Column(name = "city", length = 50)
    private String city;
    /**
     * isReject  db_column: is_reject
     */
    @Max(127)
    @Column(name = "is_reject", length = 3)
    private Integer isReject;
    /**
     * isClickGood  db_column: is_click_good
     */
    @Max(127)
    @Column(name = "is_click_good", length = 3)
    private Integer isClickGood;
    /**
     * isClickBad  db_column: is_click_bad
     */
    @Max(127)
    @Column(name = "is_click_bad", length = 3)
    private Integer isClickBad;
    /**
     * isZrg  db_column: is_zrg
     */
    @Length(max=6)
    @Column(name = "is_zrg", length = 6)
    private String isZrg;
    /**
     * adaptorCost  db_column: adaptor_cost
     */
    @Max(9999999999L)
    @Column(name = "adaptor_cost", length = 10)
    private Integer adaptorCost;
    /**
     * clientip  db_column: clientip
     */
    @Length(max=30)
    @Column(name = "clientip", length = 30)
    private String clientip;
    /**
     * bizType  db_column: biz_type
     */
    @NotNull @Max(127)
    @Column(name = "biz_type", length = 3, nullable = false)
    private Integer bizType;
    /**
     * businessName  db_column: business_name
     */
    @Length(max=30)
    @Column(name = "business_name", length = 30)
    private String businessName;
    /**
     * isAssistant  db_column: is_assistant
     */
    @Max(127)
    @Column(name = "is_assistant", length = 3)
    private Integer isAssistant;
    /**
     * channel  db_column: channel
     */
    @Length(max=100)
    @Column(name = "channel", length = 100)
    private String channel;
    /**
     * cid  db_column: cid
     */
    @Length(max=200)
    @Column(name = "cid", length = 200)
    private String cid;
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
     * client  db_column: client
     */
    @Length(max=30)
    @Column(name = "client", length = 30)
    private String client;
    /**
     * tags  db_column: tags
     */
    @Length(max=30)
    @Column(name = "tags", length = 30)
    private String tags;
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
     * reserved1  db_column: reserved1
     */
    @Length(max=100)
    @Column(name = "reserved1", length = 100)
    private String reserved1;
    /**
     * reserved2  db_column: reserved2
     */
    @Length(max=100)
    @Column(name = "reserved2", length = 100)
    private String reserved2;
    /**
     * reserved3  db_column: reserved3
     */
    @Length(max=100)
    @Column(name = "reserved3", length = 100)
    private String reserved3;
    /**
     * reserved4  db_column: reserved4
     */
    @Length(max=100)
    @Column(name = "reserved4", length = 100)
    private String reserved4;
    /**
     * reserved5  db_column: reserved5
     */
    @Length(max=100)
    @Column(name = "reserved5", length = 100)
    private String reserved5;

    @Transient
    private String indirectFaqId;

    @Transient
    private String directFaqId;

    @Transient
    private String directFaq;

    @Transient
    private String indirectFaq;
}


