
import java.util.List;
import java.util.ArrayList;

import javafx.scene.chart.XYChart;
import javafx.collections.ModifiableObservableListBase;

/**
 * The plot series data.
 */
class SeriesData
	extends ModifiableObservableListBase<XYChart.Series<Number, Number>> {

	/**
	 * The actual data structure.
	 */
	private List<XYChart.Series<Number, Number>> delegate;

	/**
	 * Construct a SeriesData.
	 */
	SeriesData() {
		delegate = new ArrayList<XYChart.Series<Number, Number>>();
	}

	@Override
	public XYChart.Series<Number, Number> get(int i) {
		return delegate.get(i);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public void doAdd(int i, XYChart.Series<Number, Number> e) {
		delegate.add(i, e);
	}

	@Override
	public XYChart.Series<Number, Number> doSet(
		int i, XYChart.Series<Number, Number> e) {
		return delegate.set(i, e);
	}

	@Override
	public XYChart.Series<Number, Number> doRemove(int index) {
		return delegate.remove(index);
	}
}

