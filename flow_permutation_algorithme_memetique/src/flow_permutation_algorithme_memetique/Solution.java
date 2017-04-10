package flow_permutation_algorithme_memetique;

public class Solution {
	
	private Flowshop instance ; // ref�rence � l'instance 
	private int[] ordredesjobs ; // donne l'ordre des jobs
	private int dureetotal ; // donne la dur�e totale suivant l'ordre ordredesjobs 
	
	
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
		return dureetotal;
	}
	
	
	public void setDureetotal() {
		// a completer
	}
	
}
