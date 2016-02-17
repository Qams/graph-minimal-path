package agh.proj1;
/**
 * Klasa przechowuje trójki krawedziowe. Odpowiednio od jakiego wierzcholka zaczyna sie krawedz, jaki wierzcholek laczy, jaka ma etykiete
 * @author Kamil Kryjak
 *
 */
public class Triple {
	
	/**od jakiego wierzcholka zaczyna sie krawedz*/
	int from;
	/**jaki wierzcholek laczy*/
	int to;
	/**waga wierzcholka*/
	int value;
	
	/**
	 * Konstruktor klasy Triple
	 * @param f z jakiego wierzcholka wychodzi krawedz
	 * @param t jaki drugi wierzcholek laczy
	 * @param v waga tej krawedzi
	 */
	public Triple(int f, int t, int v)
	{
		from = f;
		to = t;
		value = v;
	}
}