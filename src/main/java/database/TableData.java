
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * The table data class.
 */
public class TableData {

	/**
	 * The database access object.
	 */
	DbAccess db;
	
	/**
	 * Instantiate a new table data object.
	 * @param db The database access
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Get distinct transactions from a table.
	 * @param table The table string
	 * @return The transactions
	 * @thrown SQLException Thrown when an sql error occurs
	 * @thrown EmptySetException Thrown when the resulting set is empty
	 */
	public List<Example> getDistinctTransactions(String table)
		throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();

		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select distinct ";
		
		for(int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);

			if(i > 0) {
				query += ", ";
			}

			query += c.getColumnName();
		}

		if(tSchema.getNumberOfAttributes() == 0) {
			throw new SQLException();
		}

		query += " from " + table;
		
		statement = db.getConnection().createStatement();

		ResultSet rs = statement.executeQuery(query);

		boolean empty = true;

		while (rs.next()) {
			empty = false;

			Example currentTuple = new Example();
			for(int i=0; i < tSchema.getNumberOfAttributes();i++) {
				if(tSchema.getColumn(i).isNumber()) {
					currentTuple.add(rs.getDouble(i + 1));
				} else {
					currentTuple.add(rs.getString(i + 1));
				}
			}

			transSet.add(currentTuple);
		}

		rs.close();
		statement.close();

		if(empty) {
			throw new EmptySetException();
		}
		
		return transSet;
	}

	/**
	 * Get a set of distinct column values.
	 * @param table The table from which get the column values
	 * @param column The column from which get the values
	 * @return A set of distinct objects
	 * @thrown SQLException Thrown when a sql exception occurs
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column)
		throws SQLException {
		Set<Object> valueSet = new TreeSet<Object>();

		Statement statement;
		TableSchema tSchema = new TableSchema(db,table);
		
		String query = "select distinct ";
		
		query += column.getColumnName();
		
		query += " from " + table;
		query += " order by " + column.getColumnName();
		
		statement = db.getConnection().createStatement();

		ResultSet rs = statement.executeQuery(query);

		while (rs.next()) {
				if(column.isNumber()) {
					valueSet.add(rs.getDouble(1));
				} else {
					valueSet.add(rs.getString(1));
				}
		}

		rs.close();
		statement.close();
		
		return valueSet;
	}

	/**
	 * Get an aggregate column value.
	 * @param table The table from which get the aggreagate column value
	 * @param column The column from which get the value
	 * @param aggregate The query type (MIN or MAX)
	 * @return An aggregate column value
	 * @thrown SQLException Thrown when a sql error occurs
	 * @thrown NoValueException Thrown when no value was found
	 */
	public Object getAggregateColumnValue(
		String table, Column column, QUERY_TYPE aggregate)
		throws SQLException, NoValueException {
		Statement statement;
		TableSchema tSchema = new TableSchema(db,table);

		Object value = null;
		String query = "select ";

		String aggregateOp = "";
		if(aggregate == QUERY_TYPE.MAX) {
			aggregateOp += "max";
		} else {
			aggregateOp += "min";
		}

		query += aggregateOp + "(" + column.getColumnName() + ") from " + table;

		statement = db.getConnection().createStatement();

		ResultSet rs = statement.executeQuery(query);

		if (rs.next()) {
				if(column.isNumber()) {
					value = rs.getFloat(1);
				} else {
					value = rs.getString(1);
				}
		}

		rs.close();
		statement.close();

		if(value == null) {
			throw new NoValueException(
				"No " + aggregateOp + " on " + column.getColumnName()
			);
		}
			
		return value;
	}
}
