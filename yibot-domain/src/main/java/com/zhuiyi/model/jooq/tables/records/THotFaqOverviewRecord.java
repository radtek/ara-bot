/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables.records;


import com.zhuiyi.model.jooq.tables.THotFaqOverview;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 热点FAQ数据统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class THotFaqOverviewRecord extends UpdatableRecordImpl<THotFaqOverviewRecord> implements Record9<ULong, String, String, UInteger, String, Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1715513463;

    /**
     * Setter for <code>yibot.t_hot_faq_overview.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.id</code>.
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.appid</code>.
     */
    public void setAppid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.appid</code>.
     */
    public String getAppid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.date_sign</code>.
     */
    public void setDateSign(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.date_sign</code>.
     */
    public String getDateSign() {
        return (String) get(2);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.faq_id</code>.
     */
    public void setFaqId(UInteger value) {
        set(3, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.faq_id</code>.
     */
    public UInteger getFaqId() {
        return (UInteger) get(3);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.faq</code>.
     */
    public void setFaq(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.faq</code>.
     */
    public String getFaq() {
        return (String) get(4);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.visit_num</code>.
     */
    public void setVisitNum(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.visit_num</code>.
     */
    public Integer getVisitNum() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.visit_trend</code>.
     */
    public void setVisitTrend(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.visit_trend</code>.
     */
    public String getVisitTrend() {
        return (String) get(6);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.gmt_create</code>.
     */
    public void setGmtCreate(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.gmt_create</code>.
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>yibot.t_hot_faq_overview.gmt_modified</code>.
     */
    public void setGmtModified(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>yibot.t_hot_faq_overview.gmt_modified</code>.
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<ULong, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, String, String, UInteger, String, Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<ULong, String, String, UInteger, String, Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.APPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.DATE_SIGN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field4() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.FAQ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.FAQ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.VISIT_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.VISIT_TREND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return THotFaqOverview.T_HOT_FAQ_OVERVIEW.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getAppid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDateSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger component4() {
        return getFaqId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getFaq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getVisitNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getVisitTrend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAppid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDateSign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value4() {
        return getFaqId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getFaq();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getVisitNum();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getVisitTrend();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value2(String value) {
        setAppid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value3(String value) {
        setDateSign(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value4(UInteger value) {
        setFaqId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value5(String value) {
        setFaq(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value6(Integer value) {
        setVisitNum(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value7(String value) {
        setVisitTrend(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value8(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord value9(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverviewRecord values(ULong value1, String value2, String value3, UInteger value4, String value5, Integer value6, String value7, Timestamp value8, Timestamp value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached THotFaqOverviewRecord
     */
    public THotFaqOverviewRecord() {
        super(THotFaqOverview.T_HOT_FAQ_OVERVIEW);
    }

    /**
     * Create a detached, initialised THotFaqOverviewRecord
     */
    public THotFaqOverviewRecord(ULong id, String appid, String dateSign, UInteger faqId, String faq, Integer visitNum, String visitTrend, Timestamp gmtCreate, Timestamp gmtModified) {
        super(THotFaqOverview.T_HOT_FAQ_OVERVIEW);

        set(0, id);
        set(1, appid);
        set(2, dateSign);
        set(3, faqId);
        set(4, faq);
        set(5, visitNum);
        set(6, visitTrend);
        set(7, gmtCreate);
        set(8, gmtModified);
    }
}
