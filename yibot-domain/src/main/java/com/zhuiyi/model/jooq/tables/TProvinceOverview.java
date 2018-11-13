/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables;


import com.zhuiyi.model.jooq.Indexes;
import com.zhuiyi.model.jooq.Keys;
import com.zhuiyi.model.jooq.Yibot;
import com.zhuiyi.model.jooq.tables.records.TProvinceOverviewRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 各省faq数据热点分析统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TProvinceOverview extends TableImpl<TProvinceOverviewRecord> {

    private static final long serialVersionUID = -1541461861;

    /**
     * The reference instance of <code>yibot.t_province_overview</code>
     */
    public static final TProvinceOverview T_PROVINCE_OVERVIEW = new TProvinceOverview();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TProvinceOverviewRecord> getRecordType() {
        return TProvinceOverviewRecord.class;
    }

    /**
     * The column <code>yibot.t_province_overview.id</code>.
     */
    public final TableField<TProvinceOverviewRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.appid</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> APPID = createField("appid", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.date_sign</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> DATE_SIGN = createField("date_sign", org.jooq.impl.SQLDataType.CHAR(10).nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.is_total</code>.
     */
    public final TableField<TProvinceOverviewRecord, Boolean> IS_TOTAL = createField("is_total", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.cid</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> CID = createField("cid", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>yibot.t_province_overview.client</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> CLIENT = createField("client", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>yibot.t_province_overview.eid</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> EID = createField("eid", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>yibot.t_province_overview.lables</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> LABLES = createField("lables", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>yibot.t_province_overview.im</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> IM = createField("im", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>yibot.t_province_overview.gmt_create</code>.
     */
    public final TableField<TProvinceOverviewRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.gmt_modified</code>.
     */
    public final TableField<TProvinceOverviewRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_province_overview.data</code>.
     */
    public final TableField<TProvinceOverviewRecord, String> DATA = createField("data", org.jooq.impl.SQLDataType.VARCHAR(4000).nullable(false), this, "");

    /**
     * Create a <code>yibot.t_province_overview</code> table reference
     */
    public TProvinceOverview() {
        this(DSL.name("t_province_overview"), null);
    }

    /**
     * Create an aliased <code>yibot.t_province_overview</code> table reference
     */
    public TProvinceOverview(String alias) {
        this(DSL.name(alias), T_PROVINCE_OVERVIEW);
    }

    /**
     * Create an aliased <code>yibot.t_province_overview</code> table reference
     */
    public TProvinceOverview(Name alias) {
        this(alias, T_PROVINCE_OVERVIEW);
    }

    private TProvinceOverview(Name alias, Table<TProvinceOverviewRecord> aliased) {
        this(alias, aliased, null);
    }

    private TProvinceOverview(Name alias, Table<TProvinceOverviewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("各省faq数据热点分析统计表"));
    }

    public <O extends Record> TProvinceOverview(Table<O> child, ForeignKey<O, TProvinceOverviewRecord> key) {
        super(child, key, T_PROVINCE_OVERVIEW);
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
        return Arrays.<Index>asList(Indexes.T_PROVINCE_OVERVIEW_IDX_DS_IT, Indexes.T_PROVINCE_OVERVIEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TProvinceOverviewRecord> getPrimaryKey() {
        return Keys.KEY_T_PROVINCE_OVERVIEW_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TProvinceOverviewRecord>> getKeys() {
        return Arrays.<UniqueKey<TProvinceOverviewRecord>>asList(Keys.KEY_T_PROVINCE_OVERVIEW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TProvinceOverview as(String alias) {
        return new TProvinceOverview(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TProvinceOverview as(Name alias) {
        return new TProvinceOverview(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TProvinceOverview rename(String name) {
        return new TProvinceOverview(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TProvinceOverview rename(Name name) {
        return new TProvinceOverview(name, null);
    }
}
