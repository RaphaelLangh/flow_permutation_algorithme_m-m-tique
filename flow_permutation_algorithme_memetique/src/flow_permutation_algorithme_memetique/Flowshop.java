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

import java.util.*; // nÃ©cessaire pour exo 2
import java.io.*; //

public class Flowshop {
    private int nbJobs; 		// nombre de jobs
    private int nbMachines; 	// nombre de machines
    private Job[] jobs; 		// tableau des jobs
    
    // constructeur par dÃ©faut
    public Flowshop() {
    	nbJobs = 0;
    	nbMachines = 0;
    	jobs = null;
    }
    
    // crÃ©e un problÃ¨me Ã  m machines Ã  partir d'un tableau de jobs
    public Flowshop(Job[] t, int m) {
    	nbMachines = m;
    	nbJobs = t.length;
    	jobs = new Job[nbJobs]; // on rÃ©serve la place pour les jobs
        for(int i = 0; i < nbJobs; i++) {
        	jobs[i] = t[i];
        }
    }
    
    // crÃ©e un problÃ¨me Ã  partir d'un fichier
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
    		
    		jobs = new Job[nbJobs]; // on rÃ©serve la place pour les jobs
    		int []d = new int[nbMachines];
    		int i = 0; // indice du job
    		int j = 0; // indice de l'opÃ©ration
    		while (scanner.hasNextInt()) {
    			d[j] = scanner.nextInt();
    			System.out.println(j + " " + d[j]);
    			if (j < nbMachines - 1) {
    				j++; // opÃ©ration suivante
    			}
    			else { // sinon on crÃ©e le job et on passe au suivant
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
    
/*    // crÃ©e une liste correspondant au tableau des jobs
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
    	tri.trierDureesDecroissantes(); //on crée la liste qui sera triée selon l'ordre NEH
    	ListeJobs respartiel=new ListeJobs();
    	ListeJobs test=new ListeJobs(); //on crée une liste qui correspondra à l'ajout du job suivant à différentes positions
    	
    	for(int i=0;i<tri.nombreJobs();i++){
    		int durmax=Integer.MAX_VALUE;//on cherche à minimiser la durée, on prend donc un max pour commencer
    		int nb=respartiel.nombreJobs();
    		test=respartiel.clone();
    		for (int j=0;j<=nb;j++){
    			test.ajouterJob(tri.getJob(i), j); //on ajoute à l'ordonnancement partiel un job qui aura une place précise pour chaque passage dans la boucle for
    				
    			Ordonnancement testeur=new Ordonnancement(test,this.getNbMachines());
    				
    			testeur.ordonnancer(test);
    				
    			if(testeur.getDuree()<durmax){
    				respartiel=test.clone();
    				durmax=testeur.getDuree();//si on trouve une durée plus petite que celle gardée en mémoire, on la sauvegarde
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
    	k--;j--;//cette ligne sert à repasser en indices java (au lieu des indices machine)
		int res=0;
		for(int i=0;i<k;i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute simplement toutes les opérations jusque k
		}
		return res;
		
    }
    
    // calcul de q_kj
    public int calculerDureeLatence(int k, int j) {
    	k--;j--;
		int res=0;
		for(int i=k+1;i<this.getNbMachines();i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute simplement les durées des opérations après k
		}
		return res;
    }
    
    // calcul de la somme des durÃ©es des opÃ©rations exÃ©cutÃ©es sur la machine k
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
			
			int res1=Integer.MAX_VALUE;//res1 correspondra à la date dispo
			
			for(int i=1;i<=this.getNbJobs();i++){
				if(res1>this.calculerDateDispo(k, i)){
					res1=this.calculerDateDispo(k, i);
				}
			}
			
			int res2=Integer.MAX_VALUE;//res2 correspondra à la durée de latence
			
			for(int i=1;i<=this.getNbJobs();i++){
				if(res2>this.calculerDureeLatence(k, i)){
					res2=this.calculerDureeLatence(k, i);
				}
			}
			
			int res3=this.calculerDureeJobs(k);
			
			if(res1+res2+res3>res){
				res=res1+res2+res3;//on somme les 3 pour obtenir la borne inférieure
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
		res+=o.getDateDisponibilite(0);//on part de la disponibilité de la première machine après l'ordonnancement o
		for(int i=0;i<k;i++){
			res+=this.getJob(j).getDureeOperation(i);//on ajoute de quoi obtenir le nouveau r_kj
		}
		return Math.max(res,o.getDateDisponibilite(k));//si le résultat trouvé est plus court que ce qu'il y avait avec l'ordonnancement de départ, on garde logiquement le résultat précédent
	}
    
    // calcul de la somme des durÃ©es des opÃ©rations d'une liste
    // exÃ©cutÃ©es sur la machine k
    public int calculerDureeJobs(int k, ListeJobs l) {
		int res=0;
		for(int i=0;i<l.nombreJobs();i++){
			res+=l.getJob(i).getDureeOperation(k);//on ajoute simplement les durées des opérations restantes
		}
		return res;
    }

    // calcul de la borne infÃ©rieure en tenant compte d'un ordonnancement en cours
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
    
	// procÃ©dure par Ã©valuation et sÃ©paration
    public void EvaluationSeparation() {
		// A REMPLIR
    }
}