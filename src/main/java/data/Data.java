
package data;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

import java.sql.SQLException;

import database.DbAccess;
import database.Example;
import database.TableData;
import database.TableSchema;
import database.DatabaseConnectionException;

/**
 * Source data class.
 */
public class Data {

	/**
	 * The source data examples.
	 */
	private List<Example> data;

	/**
	 * The attribute scheme which is based the data.
	 */
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();

	/**
	 * Instantiate a source data.
	 * @param table The name of the table of the database.
	 * @throws ClassNotFoundException Thrown when jdbc driver wasn't loaded
	 * @throws SQLException Thrown when an SQLException occurs
	 * @throws DatabaseConnectionException Thrown when an error to
	 *                                     connect to the database occurs
	 */
	public Data(String table)
		throws ClassNotFoundException,
		       SQLException,
		       DatabaseConnectionException {
		DbAccess db = new DbAccess();

		TableData tableData = new TableData(db);
		TableSchema tableSchema = new TableSchema(db, table);

		for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
			Column col = tableSchema.getColumn(i);
			String name = col.getName();

			Attribute attribute = null;

			if (name.isNumber()) {
				// TODO: get min and max
				attribute = new ContinuousAttribute(name, index, 0.0, 0.0);
			} else {
				// TODO: get the possible values
				attribute = new DiscreteAttribute(name, index, new String[] { });
			}

			explanatorySet.add(attribute);
		}

		data = tableData.getDistrinctTransations(table);

		db.closeConnection();
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
	public Object getValue(int sampleIndex, int attributeIndex) {
		return data.get(sampleIndex).get(attributeIndex);
	}

	/**
	 * Create a Tuple of a row in data.
	 * @param index The index of the row
	 * @return A new tuple
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(getNumberOfExplanatoryAttributes());

		for (int i = 0; i < getNumberOfExplanatoryAttributes(); i++) {
			Attribute attr = explanatorySet.get(i);

			if (attr.getClass() == DiscreteAttribute.class) {
				tuple.add(new DiscreteItem(attr,
					(String) getValue(index, i)), i);
			} else {
				tuple.add(new ContinuousItem(attr,
					(Double) getValue(index, i)), i);
			}
		}

		return tuple;
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
