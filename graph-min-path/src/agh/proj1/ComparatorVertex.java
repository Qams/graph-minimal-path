package agh.proj1;


import java.util.Comparator;
/**
 * Klasa implementuje interfejs Comparator dzieki czemu
 * moze zaimplementowac warunek dla porownywania obiektow klasy Vertex
 * @author Kamil Kryjak
 */
public class ComparatorVertex implements Comparator<Vertex>{
	
	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v1.priority.compareTo(v2.priority);
	}

}