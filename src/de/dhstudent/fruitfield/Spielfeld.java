package de.dhstudent.fruitfield;


import java.util.Random;

public class Spielfeld {
	
		
	 public void befülleSpieldfeld(){
		 //Erzeugen eines zweidminensionalen Arrays "Spielfeld"
	 Beet[][] Spielfeld = new Beet[4][5]; 
		 	for (int i=0;i<4;i++){
		 		
				 for(int j=0; j<5;j++){
					 
					 
					 	// Berechnung eines zufälligen Zustandes
					 	Random rand = new Random();
					 	int intrandomZustand = rand.nextInt((2 - 0) + 1) + 0;
					    // Erstellen eines neuen Beetes mit zufälligem Zustand. Zeile und Spalte aus den Schleifenwerten
					    Beet beet = new Beet(intrandomZustand,i,j);
					   // Zuweisung des erstellten Beetes zum Spielfeld
					    Spielfeld[i][j]= beet;
				 }
		 
				 
			 }
			
	 }
		 
	 }
