package com.meteorology.swat.junk;

import java.util.Scanner;

public class MAznTest {
	
	public static void main(String[] args)
	{
		int cityLength = 10;
		int cityWidth = 5;
		int lockerXCoordinates[] = new int[10];
		int lockerYCoordinates[] = new int[10];
		
		int[][] city = new int[cityWidth][cityLength];
		int i,j = 0;
		for(i = 0; i < cityWidth ; i++){
			for( j =0 ; j < cityLength ; j++){
				
				city[i][j] = 100;
			}
		}
		
		for(i=0 ;i < lockerXCoordinates.length; i++){
			city[lockerXCoordinates[i]][lockerYCoordinates[i]] = 0;
		}
		
		for(i = 1; i < cityWidth ; i++){
			for( j =1 ; j < cityLength ; j++){
				
		System.out.println(city[i][j]);
			}
		}
		
		//Distance 
		
		for(i = 0; i < cityWidth ; i++ ){
			for(j = 0 ; j < cityLength ; j++){
				
			}
		}
			
		
		
	}
	
	


}
