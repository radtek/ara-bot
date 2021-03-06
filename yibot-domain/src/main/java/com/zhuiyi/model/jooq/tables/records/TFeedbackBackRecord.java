/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables.records;


import com.zhuiyi.model.jooq.tables.TFeedbackBack;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 原始feedback数据记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TFeedbackBackRecord extends UpdatableRecordImpl<TFeedbackBackRecord> implements Record6<ULong, String, Timestamp, Timestamp, Timestamp, String> {

    private static final long serialVersionUID = -2072389175;

    /**
     * Setter for <code>yibot.t_feedback_back.id</code>.
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.id</code>.
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>yibot.t_feedback_back.appid</code>.
     */
    public void setAppid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.appid</code>.
     */
    public String getAppid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>yibot.t_feedback_back.exact_time</code>.
     */
    public void setExactTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.exact_time</code>.
     */
    public Timestamp getExactTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>yibot.t_feedback_back.gmt_create</code>.
     */
    public void setGmtCreate(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.gmt_create</code>.
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>yibot.t_feedback_back.gmt_modified</code>.
     */
    public void setGmtModified(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.gmt_modified</code>.
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>yibot.t_feedback_back.data</code>.
     */
    public void setData(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>yibot.t_feedback_back.data</code>.
     */
    public String getData() {
        return (String) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<ULong, String, Timestamp, Timestamp, Timestamp, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<ULong, String, Timestamp, Timestamp, Timestamp, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return TFeedbackBack.T_FEEDBACK_BACK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return TFeedbackBack.T_FEEDBACK_BACK.APPID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return TFeedbackBack.T_FEEDBACK_BACK.EXACT_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return TFeedbackBack.T_FEEDBACK_BACK.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return TFeedbackBack.T_FEEDBACK_BACK.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return TFeedbackBack.T_FEEDBACK_BACK.DATA;
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
    public Timestamp component3() {
        return getExactTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component5() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getData();
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
    public Timestamp value3() {
        return getExactTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value2(String value) {
        setAppid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value3(Timestamp value) {
        setExactTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value4(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value5(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord value6(String value) {
        setData(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TFeedbackBackRecord values(ULong value1, String value2, Timestamp value3, Timestamp value4, Timestamp value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TFeedbackBackRecord
     */
    public TFeedbackBackRecord() {
        super(TFeedbackBack.T_FEEDBACK_BACK);
    }

    /**
     * Create a detached, initialised TFeedbackBackRecord
     */
    public TFeedbackBackRecord(ULong id, String appid, Timestamp exactTime, Timestamp gmtCreate, Timestamp gmtModified, String data) {
        super(TFeedbackBack.T_FEEDBACK_BACK);

        set(0, id);
        set(1, appid);
        set(2, exactTime);
        set(3, gmtCreate);
        set(4, gmtModified);
        set(5, data);
    }
}
