package flow_permutation_algorithme_memetique;

import java.util.ArrayList;
import java.util.Random;

public class Solution {

	private Flowshop instance ; // reference a l'instance 
	private int[] ordredesjobs ; // donne l'ordre des jobs
	private int dureetot ; // donne la duree totale suivant l'ordre ordredesjobs
	
	// cr�er une solution � partir d'un tableau des jobs 
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
				if(hasard.size()==1){
					tirage = 0 ;
				}
				else{
					tirage = tirage%(hasard.size()) ;
				}
				this.getOrdredesjobs()[i] = hasard.get(tirage);
				hasard.remove(tirage);
			}
			this.setDureetotal();

		}


	
	// renvoit le job num�ro i de ordredesjobs
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
	
	public String toString(){
		String s = "[" ;
		for(int i = 0 ; i < this.getInstance().getNbJobs(); i++ ){
			s = s + this.ordredesjobs[i] + ", " ;
		}
		s = s + "]" ;
		return s ;
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
		int[] jobs = this.getOrdredesjobs().clone();
		int [] bestJobs = jobs.clone();
		int minduree = this.getDureetotal();
		for ( int i = 1; i < jobs.length; i++) {
			int[] a = jobs.clone();
			a[0] = jobs[i];
			for (int j = 0; j < i-1; j++) {
				a[j+1] = jobs[i];
			}
			Solution s = new Solution(this.instance,a);
			int duree = s.getDureetotal();
			if (duree < minduree) {
				bestJobs = a;
				minduree = duree;
			}
		}
		this.dureetot = minduree;
		this.ordredesjobs = bestJobs;
	}
	/*
	public Solution croisement(Solution s) {
		
	}
	*/
}
