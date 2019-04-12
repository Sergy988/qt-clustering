
class Cluster {

	private Tuple centroid;
	private ArraySet clusteredData; 

	public Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new ArraySet();
		
	}
		
	public Tuple getCentroid() {
		return centroid;
	}

	public boolean addData(int id) {
		return clusteredData.add(id);
	}
	
	public boolean contain(int id) {
		return clusteredData.get(id);
	}

	public void removeTuple(int id) {
		clusteredData.delete(id);
	}
	
	public int getSize() {
		return clusteredData.size();
	}
	
	public int[] iterator() {
		return clusteredData.toArray();
	}
	
	public String toString() {
		String str = "Centroid=(";

		for(int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i);
		}

		str += ")";

		return str;		
	}
	
	public String toString(Data data) {
		String str= "Centroid=(";

		for(int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}

		str += ")\nExamples:\n";

		int array[] = clusteredData.toArray();

		for(int i = 0; i < array.length; i++) {
			str += "[";
			for(int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++) {
				str += data.getValue(array[i], j) + " ";
			}

			str += "] dist=" + getCentroid().getDistance(data.getItemSet(array[i])) + "\n";		
		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, array);

		return str;		
	}
}
