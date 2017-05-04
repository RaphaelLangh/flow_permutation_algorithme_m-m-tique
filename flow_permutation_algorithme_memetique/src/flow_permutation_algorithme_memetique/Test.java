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
	
	Job j1 = new Job(1,new int[]{2,8,4,7}) ;
	Job j2 = new Job(2,new int[]{3,9,1,3}) ;
	Job j3 = new Job(3,new int[]{6,8,3,4}) ;
	Job j4 = new Job(4,new int[]{5,1,0,5}) ;
	Job j5 = new Job(5,new int[]{4,6,9,3}) ;
	Job j6 = new Job(6,new int[]{2,5,6,9}) ;
	
	Flowshop instancetest = new Flowshop(new Job[]{j1,j2,j3,j4,j5,j6},4) ;
	
	

}
