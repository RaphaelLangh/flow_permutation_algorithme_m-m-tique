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
/*
* Nom de classe : Flowshop

*
* Description :
*
* Version : 1.0
*
* Date : 21/09/2010
*
* Auteur : Chams LAHLOU
*/

import java.util.*; // nécessaire pour exo 2
import java.io.*; //

public class Flowshop {
    private int nbJobs; 		// nombre de jobs
    private int nbMachines; 	// nombre de machines
    private Job[] jobs; 		// tableau des jobs
    
    // constructeur par défaut
    public Flowshop() {
    	nbJobs = 0;
    	nbMachines = 0;
    	jobs = null;
    }
    
    // crée un problème à m machines à partir d'un tableau de jobs
    public Flowshop(Job[] t, int m) {
    	nbMachines = m;
    	nbJobs = t.length;
    	jobs = new Job[nbJobs]; // on réserve la place pour les jobs
        for(int i = 0; i < nbJobs; i++) {
        	jobs[i] = t[i];
        }
    }
    
    // crée un problème à partir d'un fichier
    public Flowshop(String s) {
    	try {
    		Scanner scanner = new Scanner(new FileReader(s));
    		
    		// lecture du nombre de jobs
    		if (scanner.hasNextInt()) {
    			nbJobs = scanner.nextInt();
    		}
    		
    		// lecture du nombre de machines
    		if (scanner.hasNextInt()) {
    			nbMachines = scanner.nextInt();
    		}
    		
    		jobs = new Job[nbJobs]; // on réserve la place pour les jobs
    		int []d = new int[nbMachines];
    		int i = 0; // indice du job
    		int j = 0; // indice de l'opération
    		while (scanner.hasNextInt()) {
    			d[j] = scanner.nextInt();
    			System.out.println(j + " " + d[j]);
    			if (j < nbMachines - 1) {
    				j++; // opération suivante
    			}
    			else { // sinon on crée le job et on passe au suivant
    				jobs[i] = new Job(i, d);
    				i++;
    				j = 0;
    			}
    		}
    		scanner.close();
    	}
    	catch (IOException e) {
    		System.err.println("Erreur : " + e.getMessage()) ;
    		System.exit(2) ;
    	}
    }
    
    public int getNbJobs() {
    	return nbJobs;
    }
    
    public int getNbMachines() {
        return nbMachines;
    }
    
    public Job getJob(int i) {
    	return jobs[i];
    }
    
/*    // crée une liste correspondant au tableau des jobs
    public ListeJobs creerListeJobs() {
    	ListeJobs l = new ListeJobs();
    	for (int i = 0; i < nbJobs; i++) {
    		l.ajouterJob(jobs[i].clone());
    	}
    	return l;
    }*/
    
    /************************************************
    / exo 5
    /************************************************/
    
  /*  public ListeJobs creerListeNEH() { // renvoie une liste selon l'ordre NEH
    	ListeJobs tri=this.creerListeJobs();
    	tri.trierDureesDecroissantes(); //on cr�e la liste qui sera tri�e selon l'ordre NEH
    	ListeJobs respartiel=new ListeJobs();
    	ListeJobs test=new ListeJobs(); //on cr�e une liste qui correspondra � l'ajout du job suivant � diff�rentes positions
    	
    	for(int i=0;i<tri.nombreJobs();i++){
    		int durmax=Integer.MAX_VALUE;//on cherche � minimiser la dur�e, on prend donc un max pour commencer
    		int nb=respartiel.nombreJobs();
    		test=respartiel.clone();
    		for (int j=0;j<=nb;j++){
    			test.ajouterJob(tri.getJob(i), j); //on ajoute � l'ordonnancement partiel un job qui aura une place pr�cise pour chaque passage dans la boucle for
    				
    			Ordonnancement testeur=new Ordonnancement(test,this.getNbMachines());
    				
    			testeur.ordonnancer(test);
    				
    			if(testeur.getDuree()<durmax){
    				respartiel=test.clone();
    				durmax=testeur.getDuree();//si on trouve une dur�e plus petite que celle gard�e en m�moire, on la sauvegarde
    			}
    			test.supprimerJob(j);
    		}
    		
    	}
    	
    	return respartiel;
    }
    */
    /************************************************
    / exo 6
    /************************************************/
    
    // calcul de r_kj
    public int calculerDateDispo(int k, int j) {
    	k--;j--;//cette ligne sert � repasser en indices java (au lieu des indices machine)
		int res=0;
		for(int i=0;i<k;i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute simplement toutes les op�rations jusque k
		}
		return res;
		
    }
    
    // calcul de q_kj
    public int calculerDureeLatence(int k, int j) {
    	k--;j--;
		int res=0;
		for(int i=k+1;i<this.getNbMachines();i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute simplement les dur�es des op�rations apr�s k
		}
		return res;
    }
    
    // calcul de la somme des durées des opérations exécutées sur la machine k
    public int calculerDureeJobs(int k) {
    	k--;
		int res=0;
		for(int i=0;i<this.getNbJobs();i++){
			res+=this.getJob(i).getDureeOperation(k);
		}
		return res;
    }
    
/*    public int calculerBorneInf(ListeJobs lJobs) {
		int res=0;
		for (int k=1;k<=this.getNbMachines();k++){
			
			int res1=Integer.MAX_VALUE;//res1 correspondra � la date dispo
			
			for(int i=1;i<=this.getNbJobs();i++){
				if(res1>this.calculerDateDispo(k, i)){
					res1=this.calculerDateDispo(k, i);
				}
			}
			
			int res2=Integer.MAX_VALUE;//res2 correspondra � la dur�e de latence
			
			for(int i=1;i<=this.getNbJobs();i++){
				if(res2>this.calculerDureeLatence(k, i)){
					res2=this.calculerDureeLatence(k, i);
				}
			}
			
			int res3=this.calculerDureeJobs(k);
			
			if(res1+res2+res3>res){
				res=res1+res2+res3;//on somme les 3 pour obtenir la borne inf�rieure
			}
		}
		return res;
	}

    /************************************************
    / exo 7
    /************************************************/
    
    // calcul de r_kj tenant compte d'un ordo en cours
/*    public int calculerDateDispo(Ordonnancement o, int k, int j) {
    	k--;j--;
		int res=0;
		res+=o.getDateDisponibilite(0);//on part de la disponibilit� de la premi�re machine apr�s l'ordonnancement o
		for(int i=0;i<k;i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute de quoi obtenir le nouveau r_kj
		}
		return Math.max(res,o.getDateDisponibilite(k));//si le r�sultat trouv� est plus court que ce qu'il y avait avec l'ordonnancement de d�part, on garde logiquement le r�sultat pr�c�dent
	}
    
    // calcul de la somme des durées des opérations d'une liste
    // exécutées sur la machine k
    public int calculerDureeJobs(int k, ListeJobs l) {
		int res=0;
		for(int i=0;i<l.nombreJobs();i++){
			res+=l.getJob(i).getDureeOperation(k);//on ajoute simplement les dur�es des op�rations restantes
		}
		return res;
    }

    // calcul de la borne inférieure en tenant compte d'un ordonnancement en cours
    public int calculerBorneInf(Ordonnancement o, ListeJobs l) {
    	
    	int borneInf = 0;//ce sera la borne inf totale
    	
		for (int k=0;k<this.getNbMachines();k++){
			int borneInfMachineK = this.calculerDureeJobs(k,l);
			int minR = Integer.MAX_VALUE;
			int minQ = Integer.MAX_VALUE;
			for (Job j : l){
				minR=Math.min(minR, this.calculerDateDispo(o,k, j.getNumero()));//correspond aux r_jk
				minQ=Math.min(minQ, this.calculerDureeLatence(k, j.getNumero()));//correspond aux q_jk
			}
			borneInf=Math.max(borneInf, minR+minQ+borneInfMachineK);//on additionne les 3 pour avoir la bore totale
		}
		return borneInf;
		
	}
    
    /************************************************
	 / exo 8
	 /************************************************/
    
	// procédure par évaluation et séparation
    public void EvaluationSeparation() {
		// A REMPLIR
    }
}