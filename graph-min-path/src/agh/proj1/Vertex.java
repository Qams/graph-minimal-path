package agh.proj1;

/**
 * Klasa ktora przechowuje informacja do wierzcholku
 * @author Kamil Kryjak
 */
public class Vertex{
	/**Poprzednik wierzcholka */
	int prev;
	/**Priorytet wierzcholka*/
	Integer priority;
	/**Numer wierzcholka*/
	int nr;
	
	/**
	 * Konstruktor klasy Vertex, ktora przechowuje informacje o wierzcholku
	 * 
	 * @param prev numer wierzcholka poprzednika
	 * @param et etykieta wierzcholka, priotytet jaki jest mu nadany
	 * @param nr numer wierzcholka
	 */
	
	public Vertex(int prev, int et, int nr)
	{
		this.prev = prev;
		this.priority = et;
		this.nr = nr;
	}
	
}