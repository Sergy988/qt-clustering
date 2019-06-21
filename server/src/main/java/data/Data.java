
package data;

import java.util.Set;
import java.util.List;
import java.util.LinkedList;

import java.sql.SQLException;

import database.QueryType;
import database.DbAccess;
import database.Example;
import database.TableData;
import database.TableSchema;
import database.NoValueException;
import database.EmptySetException;
import database.DatabaseConnectionException;

/**
 * Source data class.
 */
public class Data {

	/**
	 * The source database table name.
	 */
	private String tableName;

	/**
	 * The source data tuples.
	 */
	private List<Tuple> data = new LinkedList<Tuple>();

	/**
	 * The attribute scheme which is based the data.
	 */
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();

	/**
	 * Instantiate a source data.
	 * @param table The name of the table of the database.
	 * @throws ClassNotFoundException Thrown when jdbc driver wasn't loaded
	 * @throws SQLException Thrown when an SQLException occurs
	 * @throws NoValueException Thrown when no value was found
	 * @throws DatabaseConnectionException Thrown when an error to
	 *                                     connect to the database occurs
	 * @throws EmptySetException Thrown when the dataset is empty
	 */
	public Data(String table)
		throws ClassNotFoundException,
		       SQLException,
		       NoValueException,
		       DatabaseConnectionException,
		       EmptySetException {
		this.tableName = table;

		DbAccess db = new DbAccess();
		db.initConnection();

		TableData tableData = new TableData(db);
		TableSchema tableSchema = new TableSchema(db, table);

		// Build the explanatory set
		for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
			TableSchema.Column column = tableSchema.getColumn(i);

			Attribute attribute = null;
			String name = column.getName();

			if (column.isNumber()) {
				double min = (float) tableData.getAggregateColumnValue(
					table, column, QueryType.MIN
				);

				double max = (float) tableData.getAggregateColumnValue(
					table, column, QueryType.MAX
				);

				attribute = new ContinuousAttribute(name, i, min, max);
			} else {
				Set<Object> values = tableData.getDistinctColumnValues(
					table, column
				);

				attribute = new DiscreteAttribute(
					name, i, values.toArray(new String[] {})
				);
			}

			explanatorySet.add(attribute);
		}

		// Build the dataset as a list of tuples
		List<Example> transactions = tableData.getDistinctTransactions(table);

		for (Example ex : transactions) {
			int size = ex.size();
			Tuple tuple = new Tuple(size);

			for (int i = 0; i < size; i++) {
				Attribute attr = explanatorySet.get(i);

				if (attr instanceof DiscreteAttribute) {
					tuple.add(
						new DiscreteItem(
							(DiscreteAttribute) attr,
							(String) ex.get(i)
						), i
					);
				} else {
					tuple.add(
						new ContinuousItem(
							(ContinuousAttribute) attr,
							(Double) ex.get(i)
						), i
					);
				}
			}

			data.add(tuple);
		}

		// Close the connection to the database
		db.closeConnection();
	}

	/**
	 * Get the source database table name.
	 * @return A database table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Get the number of examples.
	 * @return The number of examples
	 */
	public int getNumberOfExamples() {
		return data.size();
	}

	/**
	 * Get the scheme which is based the source data.
	 * @return The attribute scheme
	 */
	public List<Attribute> getExplanatorySet() {
		return explanatorySet;
	}

	/**
	 * Get the number of attributes.
	 * @return The size of the attribute scheme
	 */
	public int getNumberOfExplanatoryAttributes() {
		return explanatorySet.size();
	}

	/**
	 * Get an attribute.
	 * @param i The attribute index
	 * @return The attribute at position i in the attribute scheme
	 */
	public Attribute getAttribute(int i) {
		return explanatorySet.get(i);
	}

	/**
	 * Get an attribute value from the source data.
	 * @param sampleIndex The index of the sample
	 * @param attributeIndex The index of the attribute
	 * @return The attribute value from the source data
	 */
	public Item getValue(int sampleIndex, int attributeIndex) {
		return data.get(sampleIndex).get(attributeIndex);
	}

	/**
	 * Get a tuple from the dataset.
	 * @param i The index of the row
	 * @return The tuple at index i
	 */
	public Tuple getTuple(int i) {
		return data.get(i);
	}

	/**
	 * Convert the source data to a string.
	 * @return The textual rappresentation of the source data
	 */
	public String toString() {
		String output = "";

		for (Attribute attr : explanatorySet) {
			output += attr + " ";
		}

		for (int i = 0; i < getNumberOfExamples(); i++) {
			output += "\n" + (i + 1) + ". ";

			for (int j = 0; j < getNumberOfExplanatoryAttributes(); j++) {
				output += getValue(i, j) + " ";
			}
		}

		return output;
	}
}
