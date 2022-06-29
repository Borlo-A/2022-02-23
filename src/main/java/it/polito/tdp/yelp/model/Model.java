package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Review, DefaultWeightedEdge> grafo;
	private List<Review> vertici;
	private List<Arco> archi;
	private List<Stamp> daStampare;
	private List<List<Review>> soluzione;
	//private int pos;
	
	public Model()
	{
		dao = new YelpDao();
		daStampare = new ArrayList<Stamp>();
		soluzione = new ArrayList<List<Review>>();
		//pos=1;
	}
	
	public void creaGrafo(Business business)
	{
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
// Aggiungere i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getReviewsByBusiness(business));
		vertici = new ArrayList<Review>(this.dao.getReviewsByBusiness(business));
		
// Aggiungere gli archi
		archi = new ArrayList<>();
		
		for (int i=0; i<vertici.size(); i++)
		{
			for(int j=i+1; j<vertici.size(); j++)
			{
				long differenza = ChronoUnit.DAYS.between(vertici.get(i).getDate(), vertici.get(j).getDate());
				if(differenza>0)
				{
					Graphs.addEdgeWithVertices(this.grafo, vertici.get(i), vertici.get(j), differenza);
					archi.add(new Arco(vertici.get(i), vertici.get(j), differenza));
				}
			}
		}
		int max =0;
		int count;
		
		for (int i=0; i<vertici.size(); i++)
		{
			count=0;
			
			for(int j=0; j<archi.size(); j++)
			{
				if(vertici.get(i).equals(archi.get(j).getR1()))
				{
					count++;
				}
			}

			if(count>max)
			{
				max=count;
				daStampare.add(new Stamp(vertici.get(i), count));
			}
			
		}
		for(int i=0; i<daStampare.size(); i++)
		{
			if(daStampare.get(i).contatore!=max)
			{
				daStampare.remove(i);
				i=-1;
			}
		}
	}
	
	public List<Review> calcolaPercorso()
	{
		List<Review> parziale = new ArrayList<>();
		ricorsiva(parziale, 1);
		int max=0;
		
		for(List<Review> l : soluzione)
		{
			if(l.size()>max)
				max=l.size();
		}
		for(List<Review> l : soluzione)
		{
			if(l.size()==max)
				return l;
		}
		return null;
		
	}
	
	public void ricorsiva(List<Review> parziale, int L)
	{
// Casi terminali
		
		
// Generazione sottoproblemi
		//int c=0;
		
//		int start=0;
//		int finish=0;
		
		for(int j=L; j<vertici.size(); j++)
		{
//			while(vertici.get(j).getStars()>=vertici.get(j-1).getStars())
//			{
//				parziale.add(vertici.get(j));
//			}
			if(j==L)
				parziale.add(vertici.get(j-1));
			
			if(vertici.get(j).getStars()>=vertici.get(j-1).getStars())
			{
				parziale.add(vertici.get(j));
				//c++;
			}
			
//			ricorsiva(parziale, L+1);
			else if(parziale.size()>1)
			{
				L=j;
				soluzione.add(parziale);
				ricorsiva(new ArrayList<Review>(), L+1);	
			}
			
			else 
			{
				L=j;
				parziale.remove(0);
				ricorsiva(new ArrayList<Review>(), L+1);
			}
			
			if(j==vertici.size()-1)
			{
				if(parziale.size()==1)
					parziale.remove(0);
				
				else soluzione.add(parziale);
				
				j=vertici.size();
			}
			
		}	
	}
	
	public int getSizeVertici()
	{
		return this.vertici.size();
	}

	public int getSizeArchi()
	{
		return this.archi.size();
	}
	
	public List<Stamp> getDaStampare() {
		return daStampare;
	}

	public List<Arco> getArchi() {
		return archi;
	}

	public List<String> getCities()
	{
		return this.dao.getCities();
	}
	
	public List<Business> getBusinessByCity(String city)
	{
		return this.dao.getBusinessByCity(city);
	}
	
	public List<Review> getReviewsByBusiness(Business business)
	{
		return this.dao.getReviewsByBusiness(business);
	}
}
