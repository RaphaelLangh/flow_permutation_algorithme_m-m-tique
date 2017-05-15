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
	
	
	
	
	
	

}

	
	
