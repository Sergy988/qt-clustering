
package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * The scheme of a table class.
 */
public class TableSchema {

	/**
	 * The database access object.
	 */
	DbAccess db;

	/**
	 * The column inner class.
	 */
	public class Column {

		/**
		 * The name of the column.
		 */
		private String name;

		/**
		 * The type of the column.
		 */
		private String type;

		/**
		 * Instantiate a new column.
		 * @param name The name of the column
		 * @param type The type of the column
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Get the column name.
		 * @return The column name
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Check if the column is numerical.
		 * @return true if the column type is a number,
		 *         false otherwise
		 */
		public boolean isNumber(){
			return type.equals("number");
		}

		/**
		 * Convert the column to a string.
		 * @return The string rappresentation of the column
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	/**
	 * The table schema object (array of columns).
	 */
	List<Column> tableSchema = new ArrayList<Column>();
	
	/**
	 * Instantiate a table schema.
	 * @param The database access
	 * @param tableName The name of the table
	 * @throws SQLException Thrown whene a sql exception occurs
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;

		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		HashMap<String,String> sqlTypesMap = new HashMap<String, String>();
		sqlTypesMap.put("CHAR", "string");
		sqlTypesMap.put("VARCHAR", "string");
		sqlTypesMap.put("LONGVARCHAR", "string");
		sqlTypesMap.put("BIT", "string");
		sqlTypesMap.put("SHORT", "number");
		sqlTypesMap.put("INT", "number");
		sqlTypesMap.put("LONG", "number");
		sqlTypesMap.put("FLOAT", "number");
		sqlTypesMap.put("DOUBLE", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if(sqlTypesMap.containsKey(res.getString("TYPE_NAME"))) {
	        	tableSchema.add(new Column(
	        		res.getString("COLUMN_NAME"),
	        		sqlTypesMap.get(res.getString("TYPE_NAME")))
	        	);
	        }
		}

		res.close();    
	}
	  
	/**
	 * Get the number of attributes.
	 * @return The number of attributes
	 */	
	public int getNumberOfAttributes(){
		return tableSchema.size();
	}
	
	/**
	 * Get the column at index i.
	 * @param i The index
	 * @return The column at i
	 */
	public Column getColumn(int index){
		return tableSchema.get(index);
	}	
}
