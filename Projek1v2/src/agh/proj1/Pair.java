package agh.proj1;

/**
 * Klasa przechowywuje pary wartosci etykiety krawedzi, oraz numeru wierzcholka jaki ta krawedz laczy
 * @author Kamil Kryjak
 *
 */
public class Pair {
	
	
	/** jaki wierzcholek laczy krawedz*/
	int to;
	/** waga wierzcholka*/
	int value;
	
	/**
	 * 
	 * @param t argument przechowujacy informacje o kolejnym elemencie, czyli takim do ktorego jest polaczona dana krawedz
	 * @param v argument przechowujacy wage krawedzi
	 */
	public Pair(int t, int v)
	{
		to = t;
		value = v;
	}
}