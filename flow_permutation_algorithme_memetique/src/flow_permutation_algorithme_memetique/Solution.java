package flow_permutation_algorithme_memetique;

import java.util.ArrayList;
import java.util.Random;

public class Solution {
	
	private Flowshop instance ; // reference a l'instance 
	private int[] ordredesjobs ; // donne l'ordre des jobs
	private int dureetot ; // donne la duree totale suivant l'ordre ordredesjobs
	
	
	// creer une solution aleatoirement 
	public Solution(Flowshop instance){
		this.instance = instance ;
		this.ordredesjobs = new int[this.instance.getNbJobs()];
		
		ArrayList<Integer> hasard = new ArrayList<Integer>() ;
		for (int i = 0 ; i < instance.getNbJobs() ; i++){
			hasard.add(i) ; 
		}
		for (int i = 0 ; i < instance.getNbJobs() ; i++){
			Random rand = new Random();
			int tirage = rand.nextInt(instance.getNbJobs());
			tirage = tirage%(hasard.size()-1) ;
			this.getOrdredesjobs()[i] = hasard.get(tirage);
			hasard.remove(tirage);
		}
		this.setDureetotal();
				
	}
	
	
	public Flowshop getInstance() {
		return instance;
	}
	public void setInstance(Flowshop instance) {
		this.instance = instance;
	}
	public int[] getOrdredesjobs() {
		return ordredesjobs;
	}
	public void setOrdredesjobs(int[] ordredesjobs) {
		this.ordredesjobs = ordredesjobs;
	}
	public int getDureetotal() {
		return dureetot;
	}
	
	
	public void setDureetotal() {
		// a completer
	}
	
}
