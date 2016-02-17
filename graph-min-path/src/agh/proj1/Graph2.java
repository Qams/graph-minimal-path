package agh.proj1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Graph2 {
	
	private File file;
	private FileReader fr;
	private BufferedReader br;
	private Scanner sc;
	//private GraphGenerator gg;
	//private int[][] tab;
	private List<Vertex> list;
	private List<Vertex> list3;
	private Queue<Vertex> q;
	private Queue<Vertex> q2;
	private ComparatorVertex cv = new ComparatorVertex();
	private ArrayList<LinkedList<Pair>> tab;
	
	public Graph2()
	{
		Random r = new Random();
		int ilej = 10000;
		//gg = new GraphGenerator(1000, "graf2.txt");
		//tab = new Vertex[10][10];
		tab = new ArrayList<LinkedList<Pair>>();
		list = new ArrayList<>();
		q = new PriorityQueue<>(cv);
		for(int i=0;i<ilej;i++)
		{
			tab.add(new LinkedList<Pair>());
			Vertex v = new Vertex(-1, Integer.MAX_VALUE, i);
			list.add(v);
			list3.add(v);
			q.add(v);
		}
	}
	
	public void graphParse(String p) throws IOException
	{
		try{
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
				//tab[k][m] = l;
				tab.get(k).add(new Pair(m,l));
				
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
		
	}
	
	
	private void changePriority(int pre, int val, int x)
	{
		q2 = new PriorityQueue<>(cv);
		for(Vertex v : q)
		{
			if(v.nr == x)
			{
				Vertex v2 = new Vertex(pre, val, x);
				list.set(x, v2);
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
	
	
	
	public void dijkstra(int start, int meta)
	{
		changePriority(-1, 0, start);
		while(!(q.isEmpty()))
		{
			Vertex tmp = q.poll();
			for(Pair p : tab.get(tmp.nr))
			{
					if(getPriority(p.to) > getPriority(tmp.nr)+p.value)
					{
						int z = getPriority(tmp.nr)+p.value;
						changePriority(tmp.nr, z, p.to);
						
					}
				}
			}
		
		
		System.out.println("SCIEZKA MIEDZY " + start + " a " + meta + " WYNOSI: " + list.get(meta).priority);
		printWay(start,meta);
	}
	
	private void printWay(int start, int meta)
	{
		System.out.println("KOLEJNE WIERZCHOLKI SCIEZKI: ");
		Stack<Integer> s = new Stack<>();
		Vertex tmp = list.get(meta);
		s.push(tmp.nr);
		while(tmp.nr!=start)
		{
			s.push(tmp.prev);
			//System.out.print(tmp.prev + " ");
			tmp = list.get(tmp.prev);
		}
		while(!s.isEmpty())
		{
			System.out.print(s.pop() + " ");
		}
	}
	
	private int getPriority(int x)
	{
		return list.get(x).priority;
	}
	
	

	public static void main(String[] args) throws IOException {
		Graph2 gr = new Graph2();
		System.out.println("Parsowanie");
		gr.graphParse("graf2.txt");
		System.out.println("Parsowanie completed");
		long start = System.currentTimeMillis();
		gr.dijkstra(1, 67);
		//gr.wypisz();
		long stop = System.currentTimeMillis();
		System.out.println("Czas: " + (stop-start));
	}

}