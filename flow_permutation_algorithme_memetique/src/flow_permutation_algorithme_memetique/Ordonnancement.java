package flow_permutation_algorithme_memetique;


import java.util.Iterator;

/*
* Nom de classe : Ordonnancement
*
* Description :
*
* Version : 1.0
*
* Date : 21/09/2010
*
* Auteur : Chams LAHLOU
*/

//import java.util.*; // nécessaire pour exo 2

public class Ordonnancement implements Cloneable{
	private ListeJobs sequence;		// ordre des jobs dans l'ordonnancement
	private int nbMachines;			// nombre de machines
	private int duree;				// duree totale
	private int[] dateDisponibilite;// date de disponibilite sur chaque machine
	
	
	// constructeur par défaut
	public Ordonnancement() {
		sequence = new ListeJobs();
		nbMachines = 0;
		duree = 0;
		dateDisponibilite = null;
	}
	
	// crée un ordonnancement vierge sur m machines
	public Ordonnancement(int m) {
		sequence = new ListeJobs();
		nbMachines = m;
		duree = 0;
		dateDisponibilite = new int[nbMachines];
		for (int i = 0; i < nbMachines; i++) {
			dateDisponibilite[i] = 0; // machines disponibles à l'instant 0
		}
	}
	
	public Ordonnancement(Job[] t, int m){
		sequence = new ListeJobs();
		nbMachines = m;
		duree = 0;
		dateDisponibilite = new int[nbMachines];
		for (int i = 0; i < nbMachines; i++) {
			dateDisponibilite[i] = 0; // machines disponibles à l'instant 0
		}
	}
	
	/************************************************
	/ exo 4
	/************************************************/
	
	// crée un ordonnancement à partir d'une liste de jobs sur m machines
	// les jobs sont exécutés dans l'ordre de la liste
	public Ordonnancement(ListeJobs l, int m) {
		this(m);
		l.trierDureesDecroissantes();
		this.ordonnancer(l);
	}
	
	
	

	public void setSequence(ListeJobs sequence) {
		this.sequence = sequence;
	}

	public int[] getDateDisponibilite() {
		return dateDisponibilite;
	}
	public void setDateDisponibilite(int[] dateDisponibilite) {
		this.dateDisponibilite = dateDisponibilite;
	}

	public void update(){ // met � jour la dur�e et les dates de disponibilit�s pour la s�quence donn�e
		this.initialiser();
		Iterator<Job> it = this.getSequence().iterator();
		while(it.hasNext()){
			Job actuale = it.next();
			actuale.setDateDebut(0, this.getDateDisponibilite(0));
			this.dateDisponibilite[0] = this.getDateDisponibilite(0) + actuale.getDureeOperation(0);
			for(int i = 1; i<this.nbMachines; i++){
				actuale.setDateDebut(i, max(new int[]{this.getDateDisponibilite(i-1),this.getDateDisponibilite(i)})); 
				this.dateDisponibilite[i] = max(new int[]{this.getDateDisponibilite(i-1),this.getDateDisponibilite(i)}) + actuale.getDureeOperation(i);
				 
			}
			duree = this.dateDisponibilite[this.nbMachines-1];
		}
		}
	
	public static int max(int[] tab){
		int max = tab[0];
		for(int i : tab){
			if(i>max){
				max = i ; 
			}
		}
		return max ;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public ListeJobs getSequence() {
		return sequence;
	}
	
	public int getDateDisponibilite(int i) {
		return dateDisponibilite[i];
	}
	
	public void ajouterJob(Job j) {
		sequence.ajouterJob(j);
	}
	
	public void ajoutjobavecmodif(Job j){ // ajout un job en mettant � jour les dates de disponibilit�s et le dur�ee 
		j.setDateDebut(0, this.getDateDisponibilite(0));
		this.dateDisponibilite[0] = this.getDateDisponibilite(0) + j.getDureeOperation(0);
		for(int i = 1; i<this.nbMachines; i++){
			int datedispo = max(new int[]{this.getDateDisponibilite(i-1),this.getDateDisponibilite(i)}) ;
			j.setDateDebut(i, datedispo); 
			this.dateDisponibilite[i] = datedispo + j.getDureeOperation(i);
			}
		this.duree = this.dateDisponibilite[this.nbMachines-1];
	}

	public void initialiser() { // mise à zéro de l'ordonnancement
		for (Job j : sequence) {
			for (int i = 0; i < nbMachines; i++) {
				j.setDateDebut(i, -1); // opérations non exécutées
			}
		}
		duree = 0;
		for (int i = 0; i < nbMachines; i++) {
			dateDisponibilite[i] = 0; // machines disponibles à l'instant 0
		}
	}
	
	
	public void afficher() { // affiche l'ordonnancement
		sequence.afficher();
		for (Job j : sequence) {
			System.out.print("Job " + j.getNumero() + " : ");
			for (int i = 0; i < nbMachines; i++) {
				System.out.print("(op "+ i +" à t = " 
					+ j.getDateDebut(i) + ") ");
			}
			System.out.println();
		}
		System.out.println("Cmax = " + duree);
	}
	
	public Ordonnancement clone() {
		Ordonnancement o = null;
		try {
			o = (Ordonnancement) super.clone();
		}
		catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		// copie de la liste des jobs : nécessaire !
		o.sequence = (ListeJobs) sequence.clone();
		// copie du tableau
		o.dateDisponibilite = dateDisponibilite.clone();
		return o;
	}
	
	/************************************************
	/ exo 2
	/************************************************/
	public static int min(int[] tab){//renvoit l'indice du minimum
		int imin = 0 ;
		for(int i = 0 ; i<tab.length ; i++){
			if(tab[i]<tab[imin]){
				imin = i ;
			}
		}
		return imin ;
	}
	public void ordonnancerJob(Job j) { // ordonnance un job en fonction de ce qui est déjà ordonnancé
		int[] lesdurees = new int[this.getSequence().nombreJobs()+1];
		for(int i = 0 ; i<lesdurees.length ; i++){
			this.getSequence().ajouterJob(j, i);
			this.update();
			lesdurees[i] = this.duree ;
			this.getSequence().supprimerJob(i);
		}
		this.getSequence().ajouterJob(j, min(lesdurees));
		this.update();
		
	}
	
	/************************************************
	/ exo 3
	/************************************************/
	
	public void ordonnancer(ListeJobs l) { 
		Iterator<Job> it = l.iterator() ;
		while(it.hasNext()){
			this.ordonnancerJob(it.next());
		}
	}

	

}