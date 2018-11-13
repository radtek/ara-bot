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
@IdClass(SessionId.class)
@Table(name = "t_session")
public class Session implements Serializable {
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
     * startTime  db_column: start_time
     */
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", length = 19, nullable = false)
    private Date startTime;
    /**
     * endTime  db_column: end_time
     */
    @NotNull
    @Column(name = "end_time", length = 19, nullable = false)
    private Date endTime;
    /**
     * dateSign  db_column: date_sign
     */
    @NotBlank @Length(max=10)
    @Column(name = "date_sign", length = 10, nullable = false)
    private String dateSign;
    /**
     * roundNum  db_column: round_num
     */
    @NotNull @Max(9999999999L)
    @Column(name = "round_num", length = 10, nullable = false)
    private Integer roundNum;
    /**
     * account  db_column: account
     */
    @Length(max=100)
    @Column(name = "account", length = 100)
    private String account;
    /**
     * userIp  db_column: user_ip
     */
    @Length(max=50)
    @Column(name = "user_ip", length = 50)
    private String userIp;
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
     * senLevel  db_column: sen_level
     */
    @Max(127)
    @Column(name = "sen_level", length = 3)
    private Integer senLevel;
    /**
     * judgeType  db_column: judge_type
     */
    @Max(127)
    @Column(name = "judge_type", length = 3)
    private Integer judgeType;
    /**
     * clickGoodNum  db_column: click_good_num
     */
    @Max(9999999999L)
    @Column(name = "click_good_num", length = 10)
    private Integer clickGoodNum;
    /**
     * clickBadNum  db_column: click_bad_num
     */
    @Max(9999999999L)
    @Column(name = "click_bad_num", length = 10)
    private Integer clickBadNum;
    /**
     * source  db_column: source
     */
    @Length(max=30)
    @Column(name = "source", length = 30)
    private String source;
    /**
     * zrgType  db_column: zrg_type
     */
    @Length(max=6)
    @Column(name = "zrg_type", length = 6)
    private String zrgType;
    /**
     * zrgAt  db_column: zrg_at
     */
    @Max(127)
    @Column(name = "zrg_at", length = 3)
    private Integer zrgAt;
    /**
     * businessName  db_column: business_name
     */
    @Length(max=30)
    @Column(name = "business_name", length = 30)
    private String businessName;
    /**
     * checkTag  db_column: check_tag
     */
    @Length(max=10)
    @Column(name = "check_tag", length = 10)
    private String checkTag;
    /**
     * hasHanxuan  db_column: has_hanxuan
     */
    @Max(127)
    @Column(name = "has_hanxuan", length = 3)
    private Integer hasHanxuan;
    /**
     * hasReject  db_column: has_reject
     */
    @Max(127)
    @Column(name = "has_reject", length = 3)
    private Integer hasReject;
    /**
     * hasAns1  db_column: has_ans1
     */
    @Max(127)
    @Column(name = "has_ans1", length = 3)
    private Integer hasAns1;
    /**
     * hasAns3  db_column: has_ans3
     */
    @Max(127)
    @Column(name = "has_ans3", length = 3)
    private Integer hasAns3;
    /**
     * hasAssistant  db_column: has_assistant
     */
    @Max(127)
    @Column(name = "has_assistant", length = 3)
    private Integer hasAssistant;
    /**
     * hasAdopted  db_column: has_adopted
     */
    @Max(127)
    @Column(name = "has_adopted", length = 3)
    private Integer hasAdopted;
    /**
     * hasSecondedit  db_column: has_secondedit
     */
    @Max(127)
    @Column(name = "has_secondedit", length = 3)
    private Integer hasSecondedit;
    /**
     * hasAbandoned  db_column: has_abandoned
     */
    @Max(127)
    @Column(name = "has_abandoned", length = 3)
    private Integer hasAbandoned;
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
}


