package flow_permutation_algorithme_memetique;

import java.util.ArrayList;
import java.util.Random;

public class Solution {

	private Flowshop instance ; // reference a l'instance 
	private int[] ordredesjobs ; // donne l'ordre des jobs
	private int dureetot ; // donne la duree totale suivant l'ordre ordredesjobs
	
	
	
	// créer une solution à partir d'un tableau des jobs 
	public Solution(Flowshop instance,int[] ordredesjobs){
		this.instance = instance ;
		this.ordredesjobs = ordredesjobs ;
		this.setDureetotal();
	}


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
	
	
	// renvoit le job numéro i de ordredesjobs
	public Job getJob(int i){
		return this.getInstance().getJob(this.ordredesjobs[i]);
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
		int nbmachine = this.getInstance().getNbMachines();
		int nbjobs = this.getInstance().getNbJobs();
		int[] datedispo = new int[nbmachine];
		
		for(int i = 0 ; i < nbjobs ; i++ ){
			datedispo[0] = datedispo[0] + this.getJob(i).getDureeOperation(0) ;
			for(int j = 1 ; j < nbmachine ; j++ ){
				datedispo[j] = Math.max(datedispo[j-1],datedispo[j]) + this.getJob(i).getDureeOperation(j);
			}
		}
		this.dureetot = datedispo[nbmachine-1] ;
	}

	public void rechercheLocale(){
		int[] jobs = this.getOrdredesjobs();
		int [] bestJobs = jobs;
		int duree = this.getDureetotal();
		for (int i = 0; i < jobs.length - 1; i++) {
			int[] a = jobs;
			a[i+1] = jobs[i];
			a[i] = jobs[i+1];
			/*
			 *  Solution s = new Solution(a);
			 *  int mini = s.getDureetotal;
			 *  if (mini < duree) {
			 *  duree = mini;
			 *  bestJobs = a;
			 *  }
			 */
		}
		setOrdredesjobs(bestJobs);
		//setDureetotal(duree);
	}

}
