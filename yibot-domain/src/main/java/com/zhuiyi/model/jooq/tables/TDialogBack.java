/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq.tables;


import com.zhuiyi.model.jooq.Indexes;
import com.zhuiyi.model.jooq.Keys;
import com.zhuiyi.model.jooq.Yibot;
import com.zhuiyi.model.jooq.tables.records.TDialogBackRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 原始dialog数据记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TDialogBack extends TableImpl<TDialogBackRecord> {

    private static final long serialVersionUID = -1890002556;

    /**
     * The reference instance of <code>yibot.t_dialog_back</code>
     */
    public static final TDialogBack T_DIALOG_BACK = new TDialogBack();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TDialogBackRecord> getRecordType() {
        return TDialogBackRecord.class;
    }

    /**
     * The column <code>yibot.t_dialog_back.id</code>.
     */
    public final TableField<TDialogBackRecord, ULong> ID = createField("id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>yibot.t_dialog_back.appid</code>.
     */
    public final TableField<TDialogBackRecord, String> APPID = createField("appid", org.jooq.impl.SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>yibot.t_dialog_back.exact_time</code>.
     */
    public final TableField<TDialogBackRecord, Timestamp> EXACT_TIME = createField("exact_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_dialog_back.gmt_create</code>.
     */
    public final TableField<TDialogBackRecord, Timestamp> GMT_CREATE = createField("gmt_create", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_dialog_back.gmt_modified</code>.
     */
    public final TableField<TDialogBackRecord, Timestamp> GMT_MODIFIED = createField("gmt_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>yibot.t_dialog_back.data</code>.
     */
    public final TableField<TDialogBackRecord, String> DATA = createField("data", org.jooq.impl.SQLDataType.VARCHAR(4000).nullable(false), this, "");

    /**
     * Create a <code>yibot.t_dialog_back</code> table reference
     */
    public TDialogBack() {
        this(DSL.name("t_dialog_back"), null);
    }

    /**
     * Create an aliased <code>yibot.t_dialog_back</code> table reference
     */
    public TDialogBack(String alias) {
        this(DSL.name(alias), T_DIALOG_BACK);
    }

    /**
     * Create an aliased <code>yibot.t_dialog_back</code> table reference
     */
    public TDialogBack(Name alias) {
        this(alias, T_DIALOG_BACK);
    }

    private TDialogBack(Name alias, Table<TDialogBackRecord> aliased) {
        this(alias, aliased, null);
    }

    private TDialogBack(Name alias, Table<TDialogBackRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("原始dialog数据记录表"));
    }

    public <O extends Record> TDialogBack(Table<O> child, ForeignKey<O, TDialogBackRecord> key) {
        super(child, key, T_DIALOG_BACK);
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
        return Arrays.<Index>asList(Indexes.T_DIALOG_BACK_IDX_EXACT_TIME, Indexes.T_DIALOG_BACK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TDialogBackRecord> getPrimaryKey() {
        return Keys.KEY_T_DIALOG_BACK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TDialogBackRecord>> getKeys() {
        return Arrays.<UniqueKey<TDialogBackRecord>>asList(Keys.KEY_T_DIALOG_BACK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TDialogBack as(String alias) {
        return new TDialogBack(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TDialogBack as(Name alias) {
        return new TDialogBack(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TDialogBack rename(String name) {
        return new TDialogBack(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TDialogBack rename(Name name) {
        return new TDialogBack(name, null);
    }
}