/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package flow_permutation_algorithme_memetique;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	
	private ArrayList<Solution> pop;
	private Flowshop instance ;
	private int nbréinit;
	
	
	public Population(Flowshop instance){
		this.pop = new ArrayList<Solution>() ;
		this.instance = instance ;
		this.nbréinit = 0 ;
	}

	public Population(ArrayList<Solution> pop,Flowshop instance){
		this.pop = pop ;
		this.instance = instance ;
		this.nbréinit = 0 ;
	}
	
	public Population(int nbindividusouhaite,Flowshop instance){
		this.instance = instance ;
		this.pop = new ArrayList<Solution>();
		for(int i = 0 ; i < nbindividusouhaite ; i++){
			this.add(new Solution(instance));
		}
		pop.sort(Solution.SolutionSort.SORTBYDUREECROISSANT);
		this.nbréinit = 0 ;
		}
	
	
	public int getNbréinit() {
		return nbréinit;
	}
	public void setNbréinit(int nbréinit) {
		this.nbréinit = nbréinit;
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
	
	public Solution getSolution(int i){
		return this.getPop().get(i);
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
	
	public void recherchelocalepop(){
		int n = this.pop.size();
		for(int i=0;i<n;i++){
			pop.get(i).rechercheLocale();
		}
	}
	
	public Solution getminduree(){
		int n=this.pop.size();
		Solution res=this.pop.get(0);
		for(int i=1;i<n;i++){
			if(this.pop.get(i).getDureetotal()<res.getDureetotal()){
				res=this.pop.get(i);
			}
		}
		return res;
	}
	
	public Solution getmaxduree(){
		int n=this.pop.size();
		Solution res=this.pop.get(0);
		for(int i=1;i<n;i++){
			if(this.pop.get(i).getDureetotal()>res.getDureetotal()){
				res=this.pop.get(i);
			}
		}
		return res;
	}
	
	public double getmoyenneduree(){
		int n=this.pop.size();
		double res=0;
		for(int i=0;i<n;i++){
			res+=this.pop.get(i).getDureetotal();
		}
		res=res/n;
		return res;
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
	
	public void injectionnvsolutionduel(Solution[] solutions){
		int n = solutions.length ;
		int tirage ;
		for(int i = 0 ; i < n ; i++){
			Random rand = new Random();
			tirage = rand.nextInt(this.size());
			if(solutions[i].getDureetotal()<this.getPop().get(tirage).getDureetotal()){
				this.remove(tirage);
				int j = 0 ;
				while(j<n&&this.getSolution(j).getDureetotal()<solutions[i].getDureetotal()){
					j++;
				}
				this.getPop().add(j,solutions[i]);
			}
		}
	}
	
	
	// avec croisement 1, injection avec duel et choix du parametre de croisement k aléatoire 
	public void next(double propreinit){
		int n = this.getPop().size() ;
		Random rand = new Random();
		int tirage1 = rand.nextInt(n);
		int tirage2 = rand.nextInt(n);
		int tirageK = rand.nextInt(this.getInstance().getNbJobs()/2);
		while(tirage2==tirage1){
			tirage2 = rand.nextInt(n);
		}
		Solution fils = this.getSolution(tirage1).croisement2pts(tirageK,this.getSolution(tirage2));
		fils.rechercheLocale();
		fils.mutation();
		this.injectionnvsolutionduel(new Solution[]{fils});
		if(this.getSolution(0).getDureetotal()==this.getSolution((int) (n*propreinit)).getDureetotal()){
			this.nbréinit++;
			Solution meilleur = this.getSolution(0);
			int nbindividusouhaite = this.size();
			this.pop = new ArrayList<Solution>();
			for(int i = 0 ; i < nbindividusouhaite ; i++){
				this.add(new Solution(instance));
			}
			this.recherchelocalepop();
			this.injectionnvsolutionduel(new Solution[]{meilleur});
			pop.sort(Solution.SolutionSort.SORTBYDUREECROISSANT);		
		}
				
	}
	
	
	
	
	
	

}

	
	
