package com.bicrement.cassandra.play.clause;

public abstract class Clause {
    final String key;

    private Clause(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract Object getValue();

    public abstract Object[] getValues();

    @Override
    public abstract String toString();

    public static class SimpleClause extends Clause {
        final Object val;
        final String op;

        SimpleClause(String key, String op, Object val) {
            super(key);
            this.op = op;
            this.val = val;
        }

        @Override
        public Object getValue() {
            return val;
        }

        @Override
        public Object[] getValues() {
            return new Object[] { val };
        }

        @Override
        public String toString() {
            return new StringBuilder(key).append(op).append("?").toString();
        }
    }

    public static class EqualityClause extends Clause {
        final Object[] vals;

        EqualityClause(String key, Object val) {
            super(key);
            this.vals = new Object[] { val };
        }

        EqualityClause(String key, Object[] vals) {
            super(key);
            this.vals = vals;
        }

        @Override
        public Object getValue() {
            return vals[0];
        }

        @Override
        public Object[] getValues() {
            return vals;
        }

        @Override
        public String toString() {
            if (vals.length == 1) {
                return eqClause();
            }

            return inClause();
        }

        private String eqClause() {
            return new StringBuilder(key).append("=?").toString();
        }

        private String inClause() {
            StringBuilder sb = new StringBuilder(key).append(" IN ");
            sb.append("(");
            for (int i = 0; i < vals.length; i++) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            return sb.toString();
        }
    }

}
