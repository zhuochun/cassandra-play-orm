package com.bicrement.cassandra.play;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Statement;

public interface BaseQuery {

	/**
	 * Get query in its raw string
	 * 
	 * @return
	 */
    public String getQuery();

    /**
     * Get prepared statement of the query string
     * 
     * @return
     */
    public PreparedStatement getPreparedStatement();

    /**
     * Get a complete bounded statement
     * 
     * @return Bounded statement
     */
    public Statement getCompleteStatement();

}
