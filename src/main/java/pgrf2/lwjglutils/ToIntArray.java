package pgrf2.lwjglutils;

import java.util.List;

public class ToIntArray {
	public static int[] convert(List<Integer> list) {
		if (list.isEmpty())
			return null;
		int[] result = new int[list.size()];
		int index = 0;
		for (Integer element : list) {
			result[index++] = element;
		}
		return result;
	}
}

