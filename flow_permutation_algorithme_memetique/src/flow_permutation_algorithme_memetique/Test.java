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

public class Test {
	
	
	public static void main(String[] args) {
	
	Job j0 = new Job(0,new int[]{2,8,4,7}) ;
	Job j1 = new Job(1,new int[]{3,9,1,3}) ;
	Job j2 = new Job(2,new int[]{6,8,3,4}) ;
	Job j3 = new Job(3,new int[]{5,1,0,5}) ;
	Job j4 = new Job(4,new int[]{4,6,9,3}) ;
	Job j5 = new Job(5,new int[]{2,5,6,9}) ;
	
	Flowshop instancetest = new Flowshop(new Job[]{j0,j1,j2,j3,j4,j5},4) ;
	
	Solution s1 = new Solution(instancetest);
	Solution s2 = new Solution(instancetest);
	Solution s3 = new Solution(instancetest);
	Solution s4 = new Solution(instancetest);
	Solution s5 = new Solution(instancetest);
	
	s1.setDureetotal();
	System.out.println(s1.toString());
	System.out.println(s1.getDureetotal());
	s1.rechercheLocale();
	System.out.println(s1.toString());
	System.out.println(s1.getDureetotal());
	s2.setDureetotal();
	System.out.println(s2.toString());
	System.out.println(s2.getDureetotal());
	Solution enfant=s1.croisement(s2);
	System.out.println(enfant);
	System.out.println(enfant.getDureetotal());
	
	/*
	System.out.println(s1.toString());
	System.out.println(s2.toString());
	System.out.println(s3.toString());
	System.out.println(s4.toString());
	System.out.println(s5.toString());
	*/
	
	}

}
