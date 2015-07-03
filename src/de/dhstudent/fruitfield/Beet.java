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
	
	
	public Beet(int Zeile, int Spalte, int Zustand){
		ze = Zeile;
		sp = Spalte;
		zu = Zustand;
	}
	
	
	
	public void ChangeStatus(){
		if (this.zu == 0){
			this.zu = 1;		//Wenn der Zustand auf 0 oder 1, also Erde oder Keimling steht wechselt er in dennächst höheren Zustand
			
		}
		else if (this.zu == 1){
			this.zu = 2;  // Wenn der Zustand des Beetes bereits Pflanze ist wird dieses abgeerntet und erhält wieder Erde, also 0 als Zustand
		}
		else if (this.zu == 2){
			this.zu = 0;
			
		}
		
	}
	
	public void FindNachbarn(){
		Beet beetLinks = new Beet();
		Beet beetRechts= new Beet();
		Beet beetOben = new Beet();
		Beet beetUnten = new Beet();
		
		if (this.sp -1 > 0){
			ChangeBeetLinks(beetLinks);
		}
		else if(this.sp + 1 < 5) {
			ChangeBeetRechts(beetRechts);
		}
		else if(this.ze - 1 > 0){
			ChangeBeetOben(beetOben);
		}
		else if(this.ze + 1 < 6){
			ChangeBeetUnten(beetUnten);
		}
		
		//Kommentar von superhero1:
		//Return statement fehlt
		
	}
	
	public void ChangeBeetLinks(Beet bL){
		bL.sp = this.sp - 1;
		bL.ze = this.ze;
		if (bL.zu == 0 )
		{
			bL.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bL.zu == 1)
		{
			bL.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bL.zu == 2)
		{
			bL.zu = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
	}
	
	public void ChangeBeetRechts(Beet bR){
		bR.sp = this.sp + 1;
		bR.ze = this.ze;
		if (bR.zu == 0 )
		{
			bR.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
			ImageButton button = (ImageButton)findViewById(view.getId()); //superhero1: view verursacht einen Error
	        button.setBackgroundResource(R.drawable.plant);
		}
		else if (bR.zu == 1)
		{
			bR.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bR.zu == 2)
		{
			bR.zu = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
	}
	
	
	
	public void ChangeBeetOben(Beet bO){
		bO.ze = this.ze - 1;
		bO.sp = this.sp;
		if (bO.zu == 0 )
		{
			bO.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bO.zu == 1)
		{
			bO.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bO.zu == 2)
		{
			bO.zu = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
}
	
	public void ChangeBeetUnten(Beet bU){
		bU.ze = this.ze + 1;
		bU.sp = this.sp;
		if (bU.zu == 0 )
		{
			bU.zu = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bU.zu == 1)
		{
			bU.zu = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bU.zu == 2)
		{
			bU.zu = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
}
	
	 
	
}
