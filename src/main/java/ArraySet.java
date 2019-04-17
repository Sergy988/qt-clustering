
import java.util.Arrays;

public class ArraySet {

	private int size;
	private boolean set[];

	public ArraySet() {
		set = new boolean[64];
		Arrays.fill(set, false);
	}

	public boolean add(int i) {
		while(i >= set.length)
		{
			boolean tmp[] = new boolean[set.length * 2];
			Arrays.fill(tmp, false);
			System.arraycopy(set, 0, tmp, 0, set.length);
			set = tmp;
		}

		if(set[i]) {
			return false;
		}

		set[i] = true;
		size++;

		return true;
	}

	public boolean delete(int i){
		if(i < set.length) {
			if(!set[i]) {
				return false;
			}

			set[i] = false;
			size--;

			return true;
		}

		return false;
	}

	public boolean get(int i) {
		return set[i];
	}

	public int size() {
		return size;
	}

	public int[] toArray() {
		int array[] = new int[0];

		for(int i = 0; i < set.length; i++) {
			if(get(i)) {
				int tmp[] = new int[array.length + 1];
				System.arraycopy(array, 0, tmp, 0, array.length);
				array = tmp;
				array[array.length - 1] = i;
			}
		}

		return array;
	}
}
