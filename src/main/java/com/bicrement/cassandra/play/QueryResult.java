package com.bicrement.cassandra.play;

import java.util.List;

import com.datastax.driver.core.ExecutionInfo;

/**
 * A wrapper around the actual ResultSet in database
 *
 * @author Zhuochun
 *
 */
public interface QueryResult {

    /**
     * Returns information on the execution of the last query made for this ResultSet.
     *
     * @return the execution info for the last query made for this ResultSet.
     */
    public ExecutionInfo getExecutionInfo();

    /**
     * Return the execution information for all queries made to retrieve this ResultSet.
     *
     * @return a list of the execution info for all the queries made for this ResultSet.
     */
    public List<ExecutionInfo> getAllExecutionInfo();

    /**
     * If the query that produced this ResultSet was a conditional update, return whether it was successfully applied.
     *
     * @return if the query was a conditional update, whether it was applied. {@code true} for other types of queries.
     */
    public boolean wasApplied();

}
