/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables.records;


import com.zhuiyi.model.jooq.tables.TDialog;
import org.jooq.Record4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 机器对话流水表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TDialogRecord extends UpdatableRecordImpl<TDialogRecord> {

    private static final long serialVersionUID = -1419426230;

    /**
     * Setter for <code>yibot.t_dialog.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>yibot.t_dialog.appid</code>.
     */
    public void setAppid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.appid</code>.
     */
    public String getAppid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>yibot.t_dialog.date_month</code>.
     */
    public void setDateMonth(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.date_month</code>.
     */
    public String getDateMonth() {
        return (String) get(2);
    }

    /**
     * Setter for <code>yibot.t_dialog.exact_time</code>.
     */
    public void setExactTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.exact_time</code>.
     */
    public Timestamp getExactTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>yibot.t_dialog.date_sign</code>.
     */
    public void setDateSign(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.date_sign</code>.
     */
    public String getDateSign() {
        return (String) get(4);
    }

    /**
     * Setter for <code>yibot.t_dialog.session_id</code>.
     */
    public void setSessionId(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.session_id</code>.
     */
    public String getSessionId() {
        return (String) get(5);
    }

    /**
     * Setter for <code>yibot.t_dialog.search_id</code>.
     */
    public void setSearchId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.search_id</code>.
     */
    public String getSearchId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>yibot.t_dialog.answer_type</code>.
     */
    public void setAnswerType(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.answer_type</code>.
     */
    public String getAnswerType() {
        return (String) get(7);
    }

    /**
     * Setter for <code>yibot.t_dialog.faq_num</code>.
     */
    public void setFaqNum(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.faq_num</code>.
     */
    public Boolean getFaqNum() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>yibot.t_dialog.retcode</code>.
     */
    public void setRetcode(Boolean value) {
        set(9, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.retcode</code>.
     */
    public Boolean getRetcode() {
        return (Boolean) get(9);
    }

    /**
     * Setter for <code>yibot.t_dialog.ten_min</code>.
     */
    public void setTenMin(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.ten_min</code>.
     */
    public String getTenMin() {
        return (String) get(10);
    }

    /**
     * Setter for <code>yibot.t_dialog.source</code>.
     */
    public void setSource(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.source</code>.
     */
    public String getSource() {
        return (String) get(11);
    }

    /**
     * Setter for <code>yibot.t_dialog.account</code>.
     */
    public void setAccount(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.account</code>.
     */
    public String getAccount() {
        return (String) get(12);
    }

    /**
     * Setter for <code>yibot.t_dialog.country</code>.
     */
    public void setCountry(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.country</code>.
     */
    public String getCountry() {
        return (String) get(13);
    }

    /**
     * Setter for <code>yibot.t_dialog.province</code>.
     */
    public void setProvince(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.province</code>.
     */
    public String getProvince() {
        return (String) get(14);
    }

    /**
     * Setter for <code>yibot.t_dialog.city</code>.
     */
    public void setCity(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.city</code>.
     */
    public String getCity() {
        return (String) get(15);
    }

    /**
     * Setter for <code>yibot.t_dialog.is_reject</code>.
     */
    public void setIsReject(Boolean value) {
        set(16, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.is_reject</code>.
     */
    public Boolean getIsReject() {
        return (Boolean) get(16);
    }

    /**
     * Setter for <code>yibot.t_dialog.is_click_good</code>.
     */
    public void setIsClickGood(Boolean value) {
        set(17, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.is_click_good</code>.
     */
    public Boolean getIsClickGood() {
        return (Boolean) get(17);
    }

    /**
     * Setter for <code>yibot.t_dialog.is_click_bad</code>.
     */
    public void setIsClickBad(Boolean value) {
        set(18, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.is_click_bad</code>.
     */
    public Boolean getIsClickBad() {
        return (Boolean) get(18);
    }

    /**
     * Setter for <code>yibot.t_dialog.is_zrg</code>.
     */
    public void setIsZrg(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.is_zrg</code>.
     */
    public String getIsZrg() {
        return (String) get(19);
    }

    /**
     * Setter for <code>yibot.t_dialog.adaptor_cost</code>.
     */
    public void setAdaptorCost(UInteger value) {
        set(20, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.adaptor_cost</code>.
     */
    public UInteger getAdaptorCost() {
        return (UInteger) get(20);
    }

    /**
     * Setter for <code>yibot.t_dialog.clientip</code>.
     */
    public void setClientip(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.clientip</code>.
     */
    public String getClientip() {
        return (String) get(21);
    }

    /**
     * Setter for <code>yibot.t_dialog.biz_type</code>.
     */
    public void setBizType(Boolean value) {
        set(22, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.biz_type</code>.
     */
    public Boolean getBizType() {
        return (Boolean) get(22);
    }

    /**
     * Setter for <code>yibot.t_dialog.business_name</code>.
     */
    public void setBusinessName(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.business_name</code>.
     */
    public String getBusinessName() {
        return (String) get(23);
    }

    /**
     * Setter for <code>yibot.t_dialog.is_assistant</code>.
     */
    public void setIsAssistant(Boolean value) {
        set(24, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.is_assistant</code>.
     */
    public Boolean getIsAssistant() {
        return (Boolean) get(24);
    }

    /**
     * Setter for <code>yibot.t_dialog.channel</code>.
     */
    public void setChannel(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.channel</code>.
     */
    public String getChannel() {
        return (String) get(25);
    }

    /**
     * Setter for <code>yibot.t_dialog.cid</code>.
     */
    public void setCid(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.cid</code>.
     */
    public String getCid() {
        return (String) get(26);
    }

    /**
     * Setter for <code>yibot.t_dialog.eid</code>.
     */
    public void setEid(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.eid</code>.
     */
    public String getEid() {
        return (String) get(27);
    }

    /**
     * Setter for <code>yibot.t_dialog.lables</code>.
     */
    public void setLables(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.lables</code>.
     */
    public String getLables() {
        return (String) get(28);
    }

    /**
     * Setter for <code>yibot.t_dialog.im</code>.
     */
    public void setIm(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.im</code>.
     */
    public String getIm() {
        return (String) get(29);
    }

    /**
     * Setter for <code>yibot.t_dialog.client</code>.
     */
    public void setClient(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.client</code>.
     */
    public String getClient() {
        return (String) get(30);
    }

    /**
     * Setter for <code>yibot.t_dialog.tags</code>.
     */
    public void setTags(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.tags</code>.
     */
    public String getTags() {
        return (String) get(31);
    }

    /**
     * Setter for <code>yibot.t_dialog.gmt_create</code>.
     */
    public void setGmtCreate(Timestamp value) {
        set(32, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.gmt_create</code>.
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(32);
    }

    /**
     * Setter for <code>yibot.t_dialog.gmt_modified</code>.
     */
    public void setGmtModified(Timestamp value) {
        set(33, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.gmt_modified</code>.
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(33);
    }

    /**
     * Setter for <code>yibot.t_dialog.reserved1</code>.
     */
    public void setReserved1(String value) {
        set(34, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.reserved1</code>.
     */
    public String getReserved1() {
        return (String) get(34);
    }

    /**
     * Setter for <code>yibot.t_dialog.reserved2</code>.
     */
    public void setReserved2(String value) {
        set(35, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.reserved2</code>.
     */
    public String getReserved2() {
        return (String) get(35);
    }

    /**
     * Setter for <code>yibot.t_dialog.reserved3</code>.
     */
    public void setReserved3(String value) {
        set(36, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.reserved3</code>.
     */
    public String getReserved3() {
        return (String) get(36);
    }

    /**
     * Setter for <code>yibot.t_dialog.reserved4</code>.
     */
    public void setReserved4(String value) {
        set(37, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.reserved4</code>.
     */
    public String getReserved4() {
        return (String) get(37);
    }

    /**
     * Setter for <code>yibot.t_dialog.reserved5</code>.
     */
    public void setReserved5(String value) {
        set(38, value);
    }

    /**
     * Getter for <code>yibot.t_dialog.reserved5</code>.
     */
    public String getReserved5() {
        return (String) get(38);
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
     * Create a detached TDialogRecord
     */
    public TDialogRecord() {
        super(TDialog.T_DIALOG);
    }

    /**
     * Create a detached, initialised TDialogRecord
     */
    public TDialogRecord(String id, String appid, String dateMonth, Timestamp exactTime, String dateSign, String sessionId, String searchId, String answerType, Boolean faqNum, Boolean retcode, String tenMin, String source, String account, String country, String province, String city, Boolean isReject, Boolean isClickGood, Boolean isClickBad, String isZrg, UInteger adaptorCost, String clientip, Boolean bizType, String businessName, Boolean isAssistant, String channel, String cid, String eid, String lables, String im, String client, String tags, Timestamp gmtCreate, Timestamp gmtModified, String reserved1, String reserved2, String reserved3, String reserved4, String reserved5) {
        super(TDialog.T_DIALOG);

        set(0, id);
        set(1, appid);
        set(2, dateMonth);
        set(3, exactTime);
        set(4, dateSign);
        set(5, sessionId);
        set(6, searchId);
        set(7, answerType);
        set(8, faqNum);
        set(9, retcode);
        set(10, tenMin);
        set(11, source);
        set(12, account);
        set(13, country);
        set(14, province);
        set(15, city);
        set(16, isReject);
        set(17, isClickGood);
        set(18, isClickBad);
        set(19, isZrg);
        set(20, adaptorCost);
        set(21, clientip);
        set(22, bizType);
        set(23, businessName);
        set(24, isAssistant);
        set(25, channel);
        set(26, cid);
        set(27, eid);
        set(28, lables);
        set(29, im);
        set(30, client);
        set(31, tags);
        set(32, gmtCreate);
        set(33, gmtModified);
        set(34, reserved1);
        set(35, reserved2);
        set(36, reserved3);
        set(37, reserved4);
        set(38, reserved5);
    }
}
