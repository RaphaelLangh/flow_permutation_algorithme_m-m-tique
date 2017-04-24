package flow_permutation_algorithme_memetique;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	
	private ArrayList<Solution> pop;
	private Flowshop instance ; 
	
	
	public Population(Flowshop instance){
		this.pop = new ArrayList<Solution>() ;
		this.instance = instance ;
	}

	public Population(ArrayList<Solution> pop,Flowshop instance){
		this.pop = pop ;
		this.instance = instance ;
	}
	
	public Population(int nbindividusouhaite,Flowshop instance){
		Population popu = new Population(instance);
		for(int i = 0 ; i < nbindividusouhaite ; i++){
			popu.add(new Solution(instance));
		}
		}

	public ArrayList<Solution> getPop() {
		return pop;
	}
	public void setPop(ArrayList<Solution> pop) {
		this.pop = pop;
	}
	public Flowshop getInstance() {
		return instance;
	}
	public void setInstance(Flowshop instance) {
		this.instance = instance;
	}
	
	public void add(Solution s){
		this.pop.add(s) ;
	}
	public void remove(Solution s){
		this.pop.remove(s) ;
	}
	public void remove (int i){
		this.pop.remove(i);
	}
	public int size(){
		return this.getPop().size() ;
	}
	
	public void injectionnvsolutionramdom(Solution[] solutions){
		int n = solutions.length ;
		int tirage ;
		for(int i = 0 ; i < n ; i++){
			Random rand = new Random();
			tirage = rand.nextInt(this.size());
			this.remove(tirage);
		}
		for(int i = 0 ; i < n ; i++){
			this.add(solutions[i]);
		}
	}
	
	
	
	
	
	

}

	
	
