/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables.records;


import com.zhuiyi.model.jooq.tables.TSession;
import org.jooq.Record4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 会话表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TSessionRecord extends UpdatableRecordImpl<TSessionRecord> {

    private static final long serialVersionUID = -545137640;

    /**
     * Setter for <code>yibot.t_session.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>yibot.t_session.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>yibot.t_session.appid</code>.
     */
    public void setAppid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>yibot.t_session.appid</code>.
     */
    public String getAppid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>yibot.t_session.date_month</code>.
     */
    public void setDateMonth(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>yibot.t_session.date_month</code>.
     */
    public String getDateMonth() {
        return (String) get(2);
    }

    /**
     * Setter for <code>yibot.t_session.start_time</code>.
     */
    public void setStartTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>yibot.t_session.start_time</code>.
     */
    public Timestamp getStartTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>yibot.t_session.end_time</code>.
     */
    public void setEndTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>yibot.t_session.end_time</code>.
     */
    public Timestamp getEndTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>yibot.t_session.date_sign</code>.
     */
    public void setDateSign(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>yibot.t_session.date_sign</code>.
     */
    public String getDateSign() {
        return (String) get(5);
    }

    /**
     * Setter for <code>yibot.t_session.round_num</code>.
     */
    public void setRoundNum(Boolean value) {
        set(6, value);
    }

    /**
     * Getter for <code>yibot.t_session.round_num</code>.
     */
    public Boolean getRoundNum() {
        return (Boolean) get(6);
    }

    /**
     * Setter for <code>yibot.t_session.account</code>.
     */
    public void setAccount(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>yibot.t_session.account</code>.
     */
    public String getAccount() {
        return (String) get(7);
    }

    /**
     * Setter for <code>yibot.t_session.user_ip</code>.
     */
    public void setUserIp(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>yibot.t_session.user_ip</code>.
     */
    public String getUserIp() {
        return (String) get(8);
    }

    /**
     * Setter for <code>yibot.t_session.country</code>.
     */
    public void setCountry(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>yibot.t_session.country</code>.
     */
    public String getCountry() {
        return (String) get(9);
    }

    /**
     * Setter for <code>yibot.t_session.province</code>.
     */
    public void setProvince(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>yibot.t_session.province</code>.
     */
    public String getProvince() {
        return (String) get(10);
    }

    /**
     * Setter for <code>yibot.t_session.city</code>.
     */
    public void setCity(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>yibot.t_session.city</code>.
     */
    public String getCity() {
        return (String) get(11);
    }

    /**
     * Setter for <code>yibot.t_session.sen_level</code>.
     */
    public void setSenLevel(Boolean value) {
        set(12, value);
    }

    /**
     * Getter for <code>yibot.t_session.sen_level</code>.
     */
    public Boolean getSenLevel() {
        return (Boolean) get(12);
    }

    /**
     * Setter for <code>yibot.t_session.judge_type</code>.
     */
    public void setJudgeType(Boolean value) {
        set(13, value);
    }

    /**
     * Getter for <code>yibot.t_session.judge_type</code>.
     */
    public Boolean getJudgeType() {
        return (Boolean) get(13);
    }

    /**
     * Setter for <code>yibot.t_session.click_good_num</code>.
     */
    public void setClickGoodNum(UInteger value) {
        set(14, value);
    }

    /**
     * Getter for <code>yibot.t_session.click_good_num</code>.
     */
    public UInteger getClickGoodNum() {
        return (UInteger) get(14);
    }

    /**
     * Setter for <code>yibot.t_session.click_bad_num</code>.
     */
    public void setClickBadNum(UInteger value) {
        set(15, value);
    }

    /**
     * Getter for <code>yibot.t_session.click_bad_num</code>.
     */
    public UInteger getClickBadNum() {
        return (UInteger) get(15);
    }

    /**
     * Setter for <code>yibot.t_session.source</code>.
     */
    public void setSource(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>yibot.t_session.source</code>.
     */
    public String getSource() {
        return (String) get(16);
    }

    /**
     * Setter for <code>yibot.t_session.zrg_type</code>.
     */
    public void setZrgType(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>yibot.t_session.zrg_type</code>.
     */
    public String getZrgType() {
        return (String) get(17);
    }

    /**
     * Setter for <code>yibot.t_session.zrg_at</code>.
     */
    public void setZrgAt(Boolean value) {
        set(18, value);
    }

    /**
     * Getter for <code>yibot.t_session.zrg_at</code>.
     */
    public Boolean getZrgAt() {
        return (Boolean) get(18);
    }

    /**
     * Setter for <code>yibot.t_session.business_name</code>.
     */
    public void setBusinessName(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>yibot.t_session.business_name</code>.
     */
    public String getBusinessName() {
        return (String) get(19);
    }

    /**
     * Setter for <code>yibot.t_session.check_tag</code>.
     */
    public void setCheckTag(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>yibot.t_session.check_tag</code>.
     */
    public String getCheckTag() {
        return (String) get(20);
    }

    /**
     * Setter for <code>yibot.t_session.has_hanxuan</code>.
     */
    public void setHasHanxuan(Boolean value) {
        set(21, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_hanxuan</code>.
     */
    public Boolean getHasHanxuan() {
        return (Boolean) get(21);
    }

    /**
     * Setter for <code>yibot.t_session.has_reject</code>.
     */
    public void setHasReject(Boolean value) {
        set(22, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_reject</code>.
     */
    public Boolean getHasReject() {
        return (Boolean) get(22);
    }

    /**
     * Setter for <code>yibot.t_session.has_ans1</code>.
     */
    public void setHasAns1(Boolean value) {
        set(23, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_ans1</code>.
     */
    public Boolean getHasAns1() {
        return (Boolean) get(23);
    }

    /**
     * Setter for <code>yibot.t_session.has_ans3</code>.
     */
    public void setHasAns3(Boolean value) {
        set(24, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_ans3</code>.
     */
    public Boolean getHasAns3() {
        return (Boolean) get(24);
    }

    /**
     * Setter for <code>yibot.t_session.has_assistant</code>.
     */
    public void setHasAssistant(Boolean value) {
        set(25, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_assistant</code>.
     */
    public Boolean getHasAssistant() {
        return (Boolean) get(25);
    }

    /**
     * Setter for <code>yibot.t_session.has_adopted</code>.
     */
    public void setHasAdopted(Boolean value) {
        set(26, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_adopted</code>.
     */
    public Boolean getHasAdopted() {
        return (Boolean) get(26);
    }

    /**
     * Setter for <code>yibot.t_session.has_secondedit</code>.
     */
    public void setHasSecondedit(Boolean value) {
        set(27, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_secondedit</code>.
     */
    public Boolean getHasSecondedit() {
        return (Boolean) get(27);
    }

    /**
     * Setter for <code>yibot.t_session.has_abandoned</code>.
     */
    public void setHasAbandoned(Boolean value) {
        set(28, value);
    }

    /**
     * Getter for <code>yibot.t_session.has_abandoned</code>.
     */
    public Boolean getHasAbandoned() {
        return (Boolean) get(28);
    }

    /**
     * Setter for <code>yibot.t_session.channel</code>.
     */
    public void setChannel(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>yibot.t_session.channel</code>.
     */
    public String getChannel() {
        return (String) get(29);
    }

    /**
     * Setter for <code>yibot.t_session.cid</code>.
     */
    public void setCid(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>yibot.t_session.cid</code>.
     */
    public String getCid() {
        return (String) get(30);
    }

    /**
     * Setter for <code>yibot.t_session.eid</code>.
     */
    public void setEid(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>yibot.t_session.eid</code>.
     */
    public String getEid() {
        return (String) get(31);
    }

    /**
     * Setter for <code>yibot.t_session.lables</code>.
     */
    public void setLables(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>yibot.t_session.lables</code>.
     */
    public String getLables() {
        return (String) get(32);
    }

    /**
     * Setter for <code>yibot.t_session.im</code>.
     */
    public void setIm(String value) {
        set(33, value);
    }

    /**
     * Getter for <code>yibot.t_session.im</code>.
     */
    public String getIm() {
        return (String) get(33);
    }

    /**
     * Setter for <code>yibot.t_session.client</code>.
     */
    public void setClient(String value) {
        set(34, value);
    }

    /**
     * Getter for <code>yibot.t_session.client</code>.
     */
    public String getClient() {
        return (String) get(34);
    }

    /**
     * Setter for <code>yibot.t_session.gmt_create</code>.
     */
    public void setGmtCreate(Timestamp value) {
        set(35, value);
    }

    /**
     * Getter for <code>yibot.t_session.gmt_create</code>.
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(35);
    }

    /**
     * Setter for <code>yibot.t_session.gmt_modified</code>.
     */
    public void setGmtModified(Timestamp value) {
        set(36, value);
    }

    /**
     * Getter for <code>yibot.t_session.gmt_modified</code>.
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(36);
    }

    /**
     * Setter for <code>yibot.t_session.reserved1</code>.
     */
    public void setReserved1(String value) {
        set(37, value);
    }

    /**
     * Getter for <code>yibot.t_session.reserved1</code>.
     */
    public String getReserved1() {
        return (String) get(37);
    }

    /**
     * Setter for <code>yibot.t_session.reserved2</code>.
     */
    public void setReserved2(String value) {
        set(38, value);
    }

    /**
     * Getter for <code>yibot.t_session.reserved2</code>.
     */
    public String getReserved2() {
        return (String) get(38);
    }

    /**
     * Setter for <code>yibot.t_session.reserved3</code>.
     */
    public void setReserved3(String value) {
        set(39, value);
    }

    /**
     * Getter for <code>yibot.t_session.reserved3</code>.
     */
    public String getReserved3() {
        return (String) get(39);
    }

    /**
     * Setter for <code>yibot.t_session.reserved4</code>.
     */
    public void setReserved4(String value) {
        set(40, value);
    }

    /**
     * Getter for <code>yibot.t_session.reserved4</code>.
     */
    public String getReserved4() {
        return (String) get(40);
    }

    /**
     * Setter for <code>yibot.t_session.reserved5</code>.
     */
    public void setReserved5(String value) {
        set(41, value);
    }

    /**
     * Getter for <code>yibot.t_session.reserved5</code>.
     */
    public String getReserved5() {
        return (String) get(41);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record4<String, Timestamp, String, String> key() {
        return (Record4) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TSessionRecord
     */
    public TSessionRecord() {
        super(TSession.T_SESSION);
    }

    /**
     * Create a detached, initialised TSessionRecord
     */
    public TSessionRecord(String id, String appid, String dateMonth, Timestamp startTime, Timestamp endTime, String dateSign, Boolean roundNum, String account, String userIp, String country, String province, String city, Boolean senLevel, Boolean judgeType, UInteger clickGoodNum, UInteger clickBadNum, String source, String zrgType, Boolean zrgAt, String businessName, String checkTag, Boolean hasHanxuan, Boolean hasReject, Boolean hasAns1, Boolean hasAns3, Boolean hasAssistant, Boolean hasAdopted, Boolean hasSecondedit, Boolean hasAbandoned, String channel, String cid, String eid, String lables, String im, String client, Timestamp gmtCreate, Timestamp gmtModified, String reserved1, String reserved2, String reserved3, String reserved4, String reserved5) {
        super(TSession.T_SESSION);

        set(0, id);
        set(1, appid);
        set(2, dateMonth);
        set(3, startTime);
        set(4, endTime);
        set(5, dateSign);
        set(6, roundNum);
        set(7, account);
        set(8, userIp);
        set(9, country);
        set(10, province);
        set(11, city);
        set(12, senLevel);
        set(13, judgeType);
        set(14, clickGoodNum);
        set(15, clickBadNum);
        set(16, source);
        set(17, zrgType);
        set(18, zrgAt);
        set(19, businessName);
        set(20, checkTag);
        set(21, hasHanxuan);
        set(22, hasReject);
        set(23, hasAns1);
        set(24, hasAns3);
        set(25, hasAssistant);
        set(26, hasAdopted);
        set(27, hasSecondedit);
        set(28, hasAbandoned);
        set(29, channel);
        set(30, cid);
        set(31, eid);
        set(32, lables);
        set(33, im);
        set(34, client);
        set(35, gmtCreate);
        set(36, gmtModified);
        set(37, reserved1);
        set(38, reserved2);
        set(39, reserved3);
        set(40, reserved4);
        set(41, reserved5);
    }
}
