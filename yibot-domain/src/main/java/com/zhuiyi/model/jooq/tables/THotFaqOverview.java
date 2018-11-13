/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables;


import com.zhuiyi.model.jooq.Indexes;
import com.zhuiyi.model.jooq.Keys;
import com.zhuiyi.model.jooq.Yibot;
import com.zhuiyi.model.jooq.tables.records.THotFaqOverviewRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


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
public class THotFaqOverview extends TableImpl<THotFaqOverviewRecord> {

    private static final long serialVersionUID = -1654477161;

    /**
     * The reference instance of <code>yibot.t_hot_faq_overview</code>
     */
    public static final THotFaqOverview T_HOT_FAQ_OVERVIEW = new THotFaqOverview();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<THotFaqOverviewRecord> getRecordType() {
        return THotFaqOverviewRecord.class;
    }

    /**
     * The column <code>yibot.t_hot_faq_overview.id</code>.
     */
    public final TableField<THotFaqOverviewRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.appid</code>.
     */
    public final TableField<THotFaqOverviewRecord, String> APPID = createField("appid", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.date_sign</code>.
     */
    public final TableField<THotFaqOverviewRecord, String> DATE_SIGN = createField("date_sign", org.jooq.impl.SQLDataType.CHAR(10).nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.faq_id</code>.
     */
    public final TableField<THotFaqOverviewRecord, UInteger> FAQ_ID = createField("faq_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.faq</code>.
     */
    public final TableField<THotFaqOverviewRecord, String> FAQ = createField("faq", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.visit_num</code>.
     */
    public final TableField<THotFaqOverviewRecord, Integer> VISIT_NUM = createField("visit_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.visit_trend</code>.
     */
    public final TableField<THotFaqOverviewRecord, String> VISIT_TREND = createField("visit_trend", org.jooq.impl.SQLDataType.CHAR(20).nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.gmt_create</code>.
     */
    public final TableField<THotFaqOverviewRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_hot_faq_overview.gmt_modified</code>.
     */
    public final TableField<THotFaqOverviewRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>yibot.t_hot_faq_overview</code> table reference
     */
    public THotFaqOverview() {
        this(DSL.name("t_hot_faq_overview"), null);
    }

    /**
     * Create an aliased <code>yibot.t_hot_faq_overview</code> table reference
     */
    public THotFaqOverview(String alias) {
        this(DSL.name(alias), T_HOT_FAQ_OVERVIEW);
    }

    /**
     * Create an aliased <code>yibot.t_hot_faq_overview</code> table reference
     */
    public THotFaqOverview(Name alias) {
        this(alias, T_HOT_FAQ_OVERVIEW);
    }

    private THotFaqOverview(Name alias, Table<THotFaqOverviewRecord> aliased) {
        this(alias, aliased, null);
    }

    private THotFaqOverview(Name alias, Table<THotFaqOverviewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("热点FAQ数据统计表"));
    }

    public <O extends Record> THotFaqOverview(Table<O> child, ForeignKey<O, THotFaqOverviewRecord> key) {
        super(child, key, T_HOT_FAQ_OVERVIEW);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Yibot.YIBOT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.T_HOT_FAQ_OVERVIEW_IDX_DS_IT, Indexes.T_HOT_FAQ_OVERVIEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<THotFaqOverviewRecord> getPrimaryKey() {
        return Keys.KEY_T_HOT_FAQ_OVERVIEW_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<THotFaqOverviewRecord>> getKeys() {
        return Arrays.<UniqueKey<THotFaqOverviewRecord>>asList(Keys.KEY_T_HOT_FAQ_OVERVIEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverview as(String alias) {
        return new THotFaqOverview(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public THotFaqOverview as(Name alias) {
        return new THotFaqOverview(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public THotFaqOverview rename(String name) {
        return new THotFaqOverview(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public THotFaqOverview rename(Name name) {
        return new THotFaqOverview(name, null);
    }
}
