package agh.proj1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa glowna, tworzaca GUI.
 * 
 * @author Kamil Kryjak
 *
 */
public class Main extends JFrame {
	/** Przycisk */
	JButton algDij, algBf, graphGen;
	/** Podpis */
	JLabel lStart, lMeta, lDodKraw, lVertices;
	/** Pole tekstowe */
	JTextField tfStart, tfMeta, tfDodKraw, tfVertices;
	/** Pole wynikowe, gdzie wypisywane sa biezace informacje */
	JTextArea taWynik;
	/** Panel w ktorym umieszczone sa poszczegolne komponenty */
	JPanel panel;
	/** Umieszcza w polu wynikowym scrollbar */
	JScrollPane scrollPane;
	/** Graf */
	Graph g;
	/**
	 * String wynikowy, znajduje sie w nim to co wypisywane bedzie w polu
	 * wynikowym
	 */
	String s = "";
	/**
	 * Flaga majaca na celu uniemozliwenie wykonania operacji znajdowania
	 * najktoszej sciezki przed generacja grafu po raz pierwszy
	 */
	boolean flag = false;

	/**
	 * Glowna funkcja
	 */
	public static void main(String[] args) {

		new Main();
	}

	/**
	 * Konstruktor klasy Main
	 */
	public Main() {
		panel = new JPanel();
		setSize(390, 300);
		setTitle("The shortest path");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ListenForButton lForBut = new ListenForButton();

		lStart = new JLabel("Start: ");
		tfStart = new JTextField(4);
		lMeta = new JLabel("Meta: ");
		tfMeta = new JTextField(4);
		lDodKraw = new JLabel("Additional edges: ");
		tfDodKraw = new JTextField(6);
		lVertices = new JLabel("Number of vertices: ");
		tfVertices = new JTextField(4);
		graphGen = new JButton("Generate graph");
		graphGen.addActionListener(lForBut);
		algDij = new JButton("Dijkstra");
		algDij.addActionListener(lForBut);
		algBf = new JButton("Bellman-Ford");
		algBf.addActionListener(lForBut);
		taWynik = new JTextArea(10, 32);
		taWynik.setText("Fill in all fields and generate your graph");
		taWynik.setLineWrap(true);
		taWynik.setWrapStyleWord(true);
		scrollPane = new JScrollPane(taWynik, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		panel.add(lStart);
		panel.add(tfStart);
		panel.add(lMeta);
		panel.add(tfMeta);
		panel.add(lDodKraw);
		panel.add(tfDodKraw);
		panel.add(lVertices);
		panel.add(tfVertices);
		panel.add(graphGen);
		panel.add(algDij);
		panel.add(algBf);
		panel.add(scrollPane);
		add(panel);
		setVisible(true);

	}

	private class ListenForButton implements ActionListener {

		public void actionPerformed(ActionEvent ev) {
			int x=0;
			int y=0;
			int z=0;
			int q=0;
			boolean f = true;
			if (ev.getSource() == graphGen) {
				try{
				x = Integer.parseInt(tfVertices.getText());
				y = Integer.parseInt(tfDodKraw.getText());
				z = Integer.parseInt(tfStart.getText());
				q = Integer.parseInt(tfMeta.getText());
				}
				catch(Exception e)
				{
					// jesli wyrzucony zostaje wyjatek flaga zmienia sie na false, niepoprawne dane wejsciowe
					f=false;
				}

				/*
				 * 1) czy flaga ustawiona na true
				 * 2) czy liczba mozliwych polaczen wieksza od liczby krawedzi
				 * 3) czy liczba wierzcholkow wieksza od wierzcholka startowego(bo wierzcholki numeruje od 0)
				 * 4) czy liczba wierzcholkow wieksza od wierzcholka koncowego
				 * 5) czy start wiekszy lub rowny 0
				 * 6) czy meta wieksza lub rowna 0
				 * 
				 */
				if (((f)&&((x * x) - 2 * x) > y)&&(z<x)&&(q<x)&&(z>=0)&&(q>=0)) {
					System.out.println("x = " + x + " y = " + y);
					GraphGenerator gg = new GraphGenerator(x, y, "graf2.txt");
					g = new Graph(x, y);
					try {
						taWynik.setText("Parsing...");
						g.graphParse(gg.file);
						taWynik.setText("Parsing completed");
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					flag = true;
				}
				

				else {
					s = "Invalid input data. \nTry again.";
					taWynik.setText(s);
					
				}

			}
				

			if ((ev.getSource() == algDij) && (flag)) {
				//long start = System.currentTimeMillis();
				g.dijkstra(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfMeta.getText()));
				//long stop = System.currentTimeMillis();
				//long time = stop - start;
				s = "[Dijkstra] Path from " + tfStart.getText() + " to " + tfMeta.getText() + ": " + g.pathDij + "\n"
						+ "PATH: " + g.sPath + "\n";
				taWynik.setText(s);
			}
			if ((ev.getSource() == algBf) && (flag)) {
				//long start = System.currentTimeMillis();
				g.bellmanFord(Integer.parseInt(tfStart.getText()), Integer.parseInt(tfMeta.getText()));
				//long stop = System.currentTimeMillis();
				//long time = stop - start;
				s += "[Bellman-Ford] Path from " + tfStart.getText() + " to " + tfMeta.getText() + ": " + g.pathBf
						+ "\n" + "PATH: " + g.sPath + "\n";
				taWynik.setText(s);
			}
			

		}

	
	}
	
}
