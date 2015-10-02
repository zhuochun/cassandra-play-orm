package com.bicrement.cassandra.play.clause;

import com.bicrement.cassandra.play.clause.Clause.EqualityClause;

public class Where {

    public static EqualityClause eq(String key, Object val) {
        return new EqualityClause(key, val);
    }

    public static EqualityClause in(String key, Object... vals) {
        return new EqualityClause(key, vals);
    }

}
