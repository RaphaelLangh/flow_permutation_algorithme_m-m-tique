package flow_permutation_algorithme_memetique;

import java.util.ArrayList;

public class Population {
	
	private ArrayList<Solution> pop;
	private Flowshop instance ; 
	

	private Population(ArrayList<Solution> pop){
		this.pop = pop ; 
	}
	
	public Solution aleatgener(){
		ArrayList<Integer> hasard = new ArrayList<Integer>() ;
		for (int i = 0 ; i < this.instance.getNbJobs() ; i++){
			hasard.add(i) ; 
		}
		Solution nvsoluce = Solution(this.instance) ;
		
		
		
	}
}

	
	
