/*
 * This file is generated by jOOQ.
 */
package com.zhuiyi.model.jooq;


import com.zhuiyi.model.jooq.routines.TestInsert;
import org.jooq.Configuration;

import javax.annotation.Generated;


/**
 * Convenience access to all stored procedures and functions in yibot
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Routines {

    /**
     * Call <code>yibot.test_insert</code>
     */
    public static void testInsert(Configuration configuration, Integer cNum) {
        TestInsert p = new TestInsert();
        p.setCNum(cNum);

        p.execute(configuration);
    }
}
