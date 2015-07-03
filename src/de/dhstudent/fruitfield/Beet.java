/**
 * 
 */
package de.dhstudent.fruitfield;

import android.widget.ImageButton;

/**
 * @author Ronja
 *
 */
public class Beet {

	/**
	 * @param args
	 *  author Ronja Burgdoerfer
	 */
	
	int ze;
	int sp;
	int zu;
	Beet [][] fe;
	
	
	public Beet(int Zeile, int Spalte, int Zustand, Beet [][] Spielfeld ){
		ze = Zeile;
		sp = Spalte;
		zu = Zustand;
		fe = Spielfeld;
	}
	
	
	
	public int ChangeStatus(){
		if (this.zu == 0){
			this.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings				
		}
		else if (this.zu == 1){
			this.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling ist wächst die Pflanze
		}
		else if (this.zu == 2){
			this.zu = 0;   // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			
		}
		
		this.FindNachbarn();
		return this.zu;
		
	}
	
	
	
	
	public void FindNachbarn(){
		
		
		if (this.sp -1 > 0){
			Beet beetLinks = fe [this.sp-1][this.ze] ;
			ChangeStatusNachbar(beetLinks);
			
		}
		
		if(this.sp + 1 < 5) {
			Beet beetRechts = fe [this.sp+1][this.ze] ;
			ChangeStatusNachbar(beetRechts);
		}
		
		if(this.ze - 1 > 0){
			Beet beetOben = fe [this.sp][this.ze-1] ;
			ChangeStatusNachbar(beetOben);
		}
		
		if(this.ze + 1 < 6){
			Beet beetUnten = fe [this.sp][this.ze+1] ;
			ChangeStatusNachbar(beetUnten);
		}
		
		//Kommentar von superhero1:
		//Return statement fehlt
		
	}
	
	public int ChangeStatusNachbar(Beet b){
		if (b.zu == 0){
			b.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings				
		}
		else if (b.zu == 1){
			b.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
		}
		else if (b.zu == 2){
			b.zu = 0;   // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			
		}
		
		return b.zu;
		
	}



	public int getZu() {
		return zu;
	} 
	
}
