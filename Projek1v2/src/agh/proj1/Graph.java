package agh.proj1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Klasa przechowujaca graf
 * @author Kamil Kryjak
 *
 */
public class Graph {
	
	/**plik*/
	private File file;
	/**odczyt z pliku*/
	private FileReader fr;
	/**buforowany odczyt*/
	private BufferedReader br;
	/**skaner do oczytywania kolejnych zmiennych typu int z pliku*/
	private Scanner sc;
	/**macierz do przechowywania grafu (dla algorytmu Dijkstry)*/
	private int[][] tab;
	/**lista do przechowywania priorytetow wierzcholkow dla algorytmu Dijkstry)*/
	private List<Vertex> list;
	/**lista do przechowywania priorytetow wierzcholkow dla algorytmu Bellmana-Forda)*/
	private List<Vertex> list2;
	/**lista do przechowywania krawedzi (dla algorytmu Bellmana-Forda)*/
	private List<Triple> edges;
	/**kolejka priorytetowa (dla algorytmu Dijkstry)*/
	private Queue<Vertex> q;
	/**tymczasowa kolejka priorytetowa gdzie chwilowo przepisywane sa elementy*/
	private Queue<Vertex> q2;
	/**comparator dla klasy Vertex*/
	private ComparatorVertex cv = new ComparatorVertex();
	/**zmienna przechowujaca informacje o liczbie wierzcholkow w grafie*/
	public static int ile;
	/**dlugosc sciezki (dla algorytmu Dijkstry)*/
	int pathDij;
	/**dlugosc sciezki (dla algorytmu Bellmana-Forda)*/
	int pathBf;
	/**string przechowujacy sciezke*/
	String sPath="";
	
	/**
	 * Konstruktor grafu
	 * @param n liczba wierzcholkow w grafie
	 * @param kraw liczba krawedzi w grafie
	 */
	
	public Graph(int n, int kraw)
	{
		ile = kraw;
		tab = new int[kraw][kraw];
		// dla algorytmu dijkstry
		list = new ArrayList<>();
		// dla bellmana-forda
		list2 = new ArrayList<>();
		edges = new ArrayList<>();
		q = new PriorityQueue<>(cv);
		for(int i=0;i<kraw;i++)
		{
			Vertex v = new Vertex(-1, Integer.MAX_VALUE, i);
			list.add(v);
			Vertex v2 = new Vertex(-1, Integer.MAX_VALUE, i);
			list2.add(v2);
			q.add(v);
		}
	}
	
	/**
	 * Funkcja parsuje plik z zapisanym grafem i odpowiednio umieszcza dane w strukturach danych grafu
	 * @param p plik z zapisanym grafem
	 * @throws IOException zwiazany z plikiem lub bledem podczas jego przetwarzania
	 */
	public void graphParse(String p) throws IOException
	{
		try{
			System.out.println("Parsowanie...");
			file = new File(p);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String tmp = "";
			
			while((tmp = br.readLine())!= null)
			{
				String t2 = "";
				for(int i=0;i<tmp.length();i++)
				{
					if((tmp.charAt(i)!='(') && (tmp.charAt(i)!=')') && (tmp.charAt(i)!=','))
					{
						t2 += tmp.charAt(i);
					}
				}
				
				sc = new Scanner(t2);
				int k = sc.nextInt();
				int l = sc.nextInt();
				int m = sc.nextInt();
				tab[k][m] = l;
				edges.add(new Triple(k,m,l));
				
			}
		}
		finally
		{
			try
			{
				br.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Nie udalo sie zamknac");
			}
		}
		System.out.println("Parsowanie ukonczone");
		
	}
	
	/**
	 * Funkcja zmienia priorytet wierzcholka. Jest to funkcja wykorzystywana przez algorytm Dijkstry.
	 * @param pre poprzednik wierzcholka
	 * @param val nowy priorytet wierzcholka
	 * @param x numer wierzcholka ktorego priorytet jest zmieniany
	 * @param l lista, struktura danych w ktorej przechowywane sa priorytety (albo dla Dijkstry albo Bellmana-Forda)
	 */
	
	private void changePriority(int pre, int val, int x, List<Vertex> l)
	{
		q2 = new PriorityQueue<>(cv);
		for(Vertex v : q)
		{
			if(v.nr == x)
			{
				Vertex v2 = new Vertex(pre, val, x);
				l.set(x, v2);
				q2.add(v2);
				//q.poll();
			}
			else
			{
				q2.add(v);
			}
		}
		q.clear();
		q.addAll(q2);
		q2.clear();
		
	}
	
	/**
	 * Funkcja zawierajaca algorytm Dijkstry do znajdywania najkrotszej sciezki w grafie
	 * @param start wierzcholek startowy
	 * @param meta wierzcholek koncowy
	 */
	
	public void dijkstra(int start, int meta)
	{
		changePriority(-1, 0, start,list);
		while(!(q.isEmpty()))
		{
			Vertex tmp = q.poll();
			for(int i=0;i<ile;i++)
			{
				if(tab[tmp.nr][i]!=0)
				{
					if(getPriority(i,list) > (getPriority(tmp.nr,list)+tab[tmp.nr][i]))
					{
						int z = getPriority(tmp.nr,list)+tab[tmp.nr][i];
						changePriority(tmp.nr, z, i,list);
						
					}
				}
			}
		}
		
		pathDij = list.get(meta).priority;
		System.out.println("SCIEZKA MIEDZY " + start + " a " + meta + " WYNOSI: " + list.get(meta).priority);
		printWay(start,meta,list);
	}
	
	/**
	 * Funkcja znajdujaca najkrotsza sciezke pomiedzy wierzcholkami implementujaca algorytm Bellmana-Forda
	 * @param start wierzcholek startowy
	 * @param end wierzcholek koncowy
	 */
	public void bellmanFord(int start, int end)
	{
	
		list2.get(start).priority=0;
		
		for(int i=0;i<ile-1;i++)
		{
			for(Triple t : edges)
			{
				if ((getPriority(t.from, list2) != Integer.MAX_VALUE) && ((getPriority(t.from,list2) + t.value) < getPriority(t.to, list2))){
					list2.get(t.to).priority=list2.get(t.from).priority+t.value;
					Vertex v = new Vertex(t.from, list2.get(t.to).priority,t.to);
					list2.set(t.to, v);
				}
			}
		}
		pathBf = list2.get(end).priority;
		System.out.println("SCIEZKA MIEDZY " + start + " a " + end + " WYNOSI: " + list2.get(end).priority);
		printWay(start,end, list2);
	}
	
	
	/**
	 * Funkcja ktora wypisuje najkrotsza sciezke pomiedzy wierzcholkami. Uzywana zarowno dla algotytmu Dijkstry 
	 * jak i Bellmana-Forda
	 * @param start wierzcholek startowy
	 * @param meta wierzcholek koncowy
	 * @param l odpowiednia struktura danych(lista z priorytetami wyliczonymi przez algorytm Dijkstry
	 * lub algorytm Bellmana-Forda)
	 */
	private void printWay(int start, int meta, List<Vertex> l)
	{
		sPath="";
		System.out.println("KOLEJNE WIERZCHOLKI SCIEZKI: ");
		Stack<Integer> s = new Stack<>();
		Vertex tmp = l.get(meta);
		s.push(tmp.nr);
		while(tmp.nr!=start)
		{
			s.push(tmp.prev);
			//System.out.print(tmp.prev + " ");
			tmp = l.get(tmp.prev);
		}
		while(!s.isEmpty())
		{
			int w = s.pop();
			System.out.print(w + " ");
			sPath += "" + w + " ";
		}
	}
	/**
	 * Funkcja zwracajaca priorytet dla danego wierzcholka
	 * @param x numer wierzcholka
	 * @param l odpowiednia struktura danych (lista z priorytetami wyliczonymi przez algorytm Dijkstry
	 * lub algorytm Bellmana-Forda)
	 * @return priorytet dla danego wierzcholka
	 */
	private int getPriority(int x, List<Vertex> l)
	{
		/*for(Vertex v : l)
		{
			if(v.nr == x)
			{
				return v.priority;
			}
		}*/
		return l.get(x).priority;
		//System.out.println("POZA");
		//return Integer.MAX_VALUE;
	}
	
	

	/*public static void main(String[] args) throws IOException {
		Graph gr = new Graph();
		System.out.println("Trwa parsowanie...");
		gr.graphParse(gr.gg.file);
		long start = System.currentTimeMillis();
		gr.dijkstra(45, 378);
		//gr.wypisz();
		long stop = System.currentTimeMillis();
		System.out.println("Czas: " + (stop-start));
		System.out.println("Bellman-Ford");
		long start2 = System.currentTimeMillis();
		gr.bellmanFord(45, 378);
		long stop2 = System.currentTimeMillis();
		System.out.println("Czas: " + (stop2-start2));
	}*/

}
