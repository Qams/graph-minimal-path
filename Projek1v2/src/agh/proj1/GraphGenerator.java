package agh.proj1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Klasa generujaca graf i zapisujaca go do pliku
 * @author Kamil Kryjak
 *
 */
public class GraphGenerator {
	
	/**macierz, struktura danych gdzie przechowywane sa informacje o tym czy krawedz pomiedzy danymi dwoma wierzcholkami istnieje*/
	public int[][] tab1;
	/**liczba wierzcholkow grafu*/
	private int wCount;
	/**plik do ktorego zapisywany bedzie graf*/
	private File f;
	/**file writer*/
	private FileWriter fw;
	/**buforowanie zapisu*/
	private BufferedWriter bw;
	/**etykieta dla krawedzi*/
	private int etykieta;
	/**zmienna losowe*/
	private Random  r2;
	public static int rr=0;
	String file;
	
	/**
	 * Konstruktor klasy GraphGenerator
	 * @param ile informuje ile wierzcholkow ma posiadac wygenerowany graf
	 * @param kraw informuje ile dodatkowych krawedzi (poza krawedziami ktore czynia graf spojnym) ma posiadac graf
	 * @param plik nazwa pliku do ktorego zostanie zapisany graf
	 */
	public GraphGenerator(int ile, int kraw, String plik)
	{
		file = plik;
		wCount = ile;
		tab1 = new int[wCount][wCount];
		f = new File(plik);
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			edgeGenerator();
			otherGenerator(kraw);
		} catch (IOException e) {
			System.out.println("FILE NOT FOUND");
		}
		finally
		{
			try
			{
				bw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Nie udalo sie zamknac");
			}
		}
		
	}
	
	/**
	 * Funkcja dzieki ktorej graf jest spojny, laczy kolejne wierzcholki grafu
	 * @throws IOException wyjatek zwiazany z blednym plikiem lub bledem podczas operacji na nim
	 */
	
	public void edgeGenerator() throws IOException
	{	
		for(int i=0;i<wCount;i++)
		{
			Random ran = new Random();
			etykieta = ran.nextInt(100)+1;
			if(i!=wCount-1)
			{
				if(saveToTable(i, i+1))
					writeToFile(i, i+1);
				etykieta = ran.nextInt(100)+1;
				if(saveToTable(i+1,i))
					writeToFile(i+1,i);
			}
			else
			{
				if(saveToTable(i, 0))
					writeToFile(i, 0);
			}
		}
		
	}
	
	/**
	 * Funkcja ktora generuje dodatkowe krawedzie dla grafu
	 * @param kraw liczba dodatkowo wygenerowanych krawedzi
	 * @throws IOException wyjatek zwiazany z blednym plikiem lub bledem podczas operacji na nim
	 */
	
	public void otherGenerator(int kraw) throws IOException
	{
		r2 = new Random();
		//int q = r.nextInt(1000)+ (500);
		//int q = r.nextInt(10)+1;
		//System.out.println("q = " + q);
		for(int i=0;i<kraw;i++)
		{
			int tmp = r2.nextInt(wCount);
			int tmp2 = r2.nextInt(wCount);
			etykieta = r2.nextInt(100)+1;
			//if(saveToTable(tmp, tmp2))
				//writeToFile(tmp, tmp2);
			while(!saveToTable(tmp, tmp2))
			{
				tmp = r2.nextInt(wCount);
				tmp2 = r2.nextInt(wCount);
			}
			writeToFile(tmp, tmp2);
		}
	}
	
	/**
	 * Zapis do macierzy danych z grafu. Informacja o tym ktore wierzcholki sa juz polaczone, a ktore nie.
	 * @param from numer wierzcholka od ktorego wychodzi krawedz
	 * @param to numer wierzcholka ktory laczy krawedz
	 * @return zwraca typ boolean w zaleznosci od tego czy krawedz pomiedzy dwoma wierzcholkami istnieje czy nie
	 */
	public boolean saveToTable(int from, int to)
	{
		if(tab1[from][to] == 0)
		{
			tab1[from][to] = 1;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return zwraca ile wierzcholkow ma graf
	 */
	
	public int getCountVertex()
	{
		return wCount;
	}
	
	/**
	 * Funkcja ktora zapisuje do pliku krawedzie grafu w postaci (wierzcholek1, etykieta, wierzcholek2)
	 * @param from od jakiego wierzcholka zaczyna sie krawedz
	 * @param target jaki wierzcholek laczy krawedz
	 * @throws IOException Wyrzuca wyjatek gdy podany plik jest bledny
	 */
	
	public void writeToFile(int from, int target) throws IOException
	{
		bw.write("(" + from + ", " + etykieta + ", " + target + ")");
		bw.newLine();
	}
	
}