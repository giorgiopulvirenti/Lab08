package it.polito.tdp.borders.model;

import java.util.ArrayList;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	UndirectedGraph<Country, DefaultEdge> grafo;
	BordersDAO dao;
	ArrayList<Country> nazioni;

	public Model() {
		dao = new BordersDAO();
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		nazioni = new ArrayList<Country>(dao.loadAllCountries());
	}

	public void generaGrafo(int anno) {
		Graphs.addAllVertices(grafo, dao.loadAllCountries());
		ArrayList<Border> bordi = new ArrayList<Border>(dao.getCountryPairs(anno));

		for (Border b : bordi) {
			if (!grafo.containsEdge(b.getNazione1(), b.getNazione2()))
				System.out.println(b.getNazione1() + " " + " " + b.getNazione2());
				grafo.addEdge(b.getNazione1(), b.getNazione2());
			
		}

	}

	public String getGrafoString() {

		return grafo.toString();
	}

}
