// SortTools.java
/*
 * EE422C Project 1 submission by
 * Tanner Craig
 * tsc733
 * Spring 2017
*/

package assignment1;

public class SortTools {
	
	
	public static boolean isSorted(int[] x, int n) {
		if(x.length == 0 || n == 0){
			return false;
		}
		if(x.length < 2){
			return true;
		}
		int first = x[n-1];
		for(int i = n - 2; i > 0; i--){
			if(x[i] < first){
				first = x[i];
			} else {
				return false;
			}
		}
		return true;
	}
	
	
	public static int find(int[]x, int n, int v){
		for(int i = 0; i < n; i++){
			if(x[i] == v){
				return i;
			}
		}
		return -1;
	}
	
	
	public static int[] insertGeneral(int[] x, int n, int v){
		int[] array = new int[x.length];
		for(int i = 0; i < n; i++){
			array[i] = x[i];
		}
		for(int j = 0; j < n; j++){
			if(array[j] == v){
				return array;			// if v was already in x
			}
		}
		int[] correctarray = new int[x.length + 1];		// if v was not in x
		for(int k = 0; k < n + 1; k++){
			if(array[k] < v){
				correctarray[k] = array[k];
			}
			correctarray[k] = v;
		}
		return correctarray;
	}
	
	
	public static int insertInPlace(int[] x, int n, int v){
		for(int i = 0; i < n; i++){			// if v is already in x, do nothing
			if(x[i] == v){
				return n;
			}
		}									// if v is NOT already in x
		for(int j = 0; j < n; j++){
			if(x[j] > v){
				x[j] = v;
			}
		}
		return n+1;
	}
	
	
	public static void insertSort(int[] x, int n){
		int count = 1;
		while(count != 0){
			for(int i = 1; i < n; i ++){
				if(x[i] < x[i - 1]){
					x[i - 1] = x[i];
					count += 1;
				}
			}
		}
	}
}