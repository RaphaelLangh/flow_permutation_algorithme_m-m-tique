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

public class Solution {

	private Flowshop instance ; // reference a l'instance 
	private int[] ordredesjobs ; // donne l'ordre des jobs
	private int dureetot ; // donne la duree totale suivant l'ordre ordredesjobs
	
	// crï¿½er une solution ï¿½ partir d'un tableau des jobs 
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

		//constructeur par copie profonde
		public Solution(Solution s){
			this.instance=s.instance;
			this.ordredesjobs = new int[this.instance.getNbJobs()];
			
			for(int i=0;i<this.ordredesjobs.length;i++){
				this.ordredesjobs[i]=s.getOrdredesjobs()[i];
			}
			this.setDureetotal();
		}

	
	// renvoit le job numï¿½ro i de ordredesjobs
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
			for (int j = 0; j < i; j++) {
				a[j+1] = jobs[j];
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
	
	public boolean notInclude ( int ajout, int[] tab) {
		boolean b = true;
		for ( int i : tab) {
			if ( i == ajout) {
				b = false;
			}
		}
		return b;
	}
	
	public Solution croisement(Solution s) {
		int n = this.ordredesjobs.length;
		int[] a = new int[n];
		int moit = n/2;
		for (int i = 0; i < moit; i++) {
			a[i] = this.ordredesjobs[i];
		}
		for (int i = 0; i < n; i++) {
			int ajout = s.ordredesjobs[i];
			if ( notInclude(ajout, a)) {
				a[moit] = ajout;
				moit++;
			}
		}
		Solution r = new Solution (this.instance,a);
		return r;
	}
	
	//croisement en conservant les k premiers jobs du 1er parent
	public Solution croisement1pt(int k,Solution s) {
		int n = this.ordredesjobs.length;
		int[] a = new int[n];
		for (int i = 0; i < k; i++) {
			a[i] = this.ordredesjobs[i];
		}
		for (int i = 0; i < n; i++) {
			int ajout = s.ordredesjobs[i];
			if ( notInclude(ajout, a)) {
				a[k] = ajout;
				k++;
			}
		}
		Solution r = new Solution (this.instance,a);
		return r;
	}
	
	//croisement en conservant les k premiers et les k derniers jobs du 1er parent
		public Solution croisement2pts(int k,Solution s) {
			int n = this.ordredesjobs.length;
			int[] a = new int[n];
			for(int i=0;i<a.length;i++){
				a[i]=-1;
			}
			for (int i = 0; i < k; i++) {
				a[i] = this.ordredesjobs[i];
			}
			for (int i = n-k; i < n; i++) {
				a[i] = this.ordredesjobs[i];
			}
			for (int i = 0; i < n; i++) {
				int ajout = s.ordredesjobs[i];
				if ( notInclude(ajout, a)) {
					a[k] = ajout;
					k++;
				}
			}
			Solution r = new Solution (this.instance,a);
			return r;
		}
	
	//introduit une mutation en échangeant 2 jobs pris au hasard
	public void mutation(){
		Solution precedent=new Solution(this);
		Random rand1 = new Random();
		int rd1 = rand1.nextInt(instance.getNbJobs());
		Random rand2 = new Random();
		int rd2 = rand2.nextInt(instance.getNbJobs());
		if(rd1==rd2&&rd1!=instance.getNbJobs()){
			rd2+=1;
		}
		else if(rd1==rd2&&rd1!=0){
			rd2-=1;
		}
		this.ordredesjobs[rd1]=this.ordredesjobs[rd2];
		this.ordredesjobs[rd2]=precedent.ordredesjobs[rd1];
		this.setDureetotal();
	}
	
}
