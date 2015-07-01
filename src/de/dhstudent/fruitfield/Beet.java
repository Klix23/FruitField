/**
 * 
 */
package de.dhstudent.fruitfield;

/**
 * @author Ronja
 *
 */
public class Beet {

	/**
	 * @param args
	 *  author Ronja Burgdoerfer
	 */
	
	int Zustand;  //gibt den aktuellen Zustand Erde, Keimling oder Pflanze an
	int Spalte;  // gibt die Spalte des Beetes innerhalb des Spielfelds an
	int Zeile; //  gibt die Zeile des Beetes innerhalb des Spielfelds an
	
	public void ChangeStatus(){
		if (this.Zustand == 0){
			this.Zustand = 1;		//Wenn der Zustand auf 0 oder 1, also Erde oder Keimling steht wechselt er in dennächst höheren Zustand
			
		}
		else if (this.Zustand == 1){
			this.Zustand = 2;  // Wenn der Zustand des Beetes bereits Pflanze ist wird dieses abgeerntet und erhält wieder Erde, also 0 als Zustand
		}
		else if (this.Zustand == 2){
			this.Zustand = 0;
			
		}
		
	}
	
	public Beet FindNachbarn(){
		Beet beetLinks = new Beet();
		Beet beetRechts= new Beet();
		Beet beetOben = new Beet();
		Beet beetUnten = new Beet();
		
		if (this.Spalte -1 > 0){
			ChangeBeetLinks(beetLinks);
		}
		else if(this.Spalte + 1 < 5) {
			ChangeBeetRechts(beetRechts);
		}
		else if(this.Zeile - 1 > 0){
			ChangeBeetOben(beetOben);
		}
		else if(this.Zeile + 1 < 6){
			ChangeBeetUnten(beetUnten);
		}
		
		
		
	}
	
	public void ChangeBeetLinks(Beet bL){
		bL.Spalte = this.Spalte - 1;
		bL.Zeile = this.Zeile;
		if (bL.Zustand == 0 )
		{
			bL.Zustand = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bL.Zustand == 1)
		{
			bL.Zustand = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bL.Zustand == 2)
		{
			bL.Zustand = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
	}
	
	public void ChangeBeetRechts(Beet bR){
		bR.Spalte = this.Spalte + 1;
		bR.Zeile = this.Zeile;
		if (bR.Zustand == 0 )
		{
			bR.Zustand = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
			ImageButton button = (ImageButton)findViewById(view.getId());
	        button.setBackgroundResource(R.drawable.plant);
		}
		else if (bR.Zustand == 1)
		{
			bR.Zustand = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bR.Zustand == 2)
		{
			bR.Zustand = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
	}
	
	
	
	public void ChangeBeetOben(Beet bO){
		bO.Zeile = this.Zeile - 1;
		bO.Spalte = this.Spalte;
		if (bO.Zustand == 0 )
		{
			bO.Zustand = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bO.Zustand == 1)
		{
			bO.Zustand = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bO.Zustand == 2)
		{
			bO.Zustand = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
}
	
	public void ChangeBeetUnten(Beet bU){
		bU.Zeile = this.Zeile + 1;
		bU.Spalte = this.Spalte;
		if (bU.Zustand == 0 )
		{
			bU.Zustand = 1;		//Wenn der Zustand auf 0  steht wechselt er in den Zustand des Keimlings
			// Hier muss noch das Keimling Bild rein
		}
		else if (bU.Zustand == 1)
		{
			bU.Zustand = 2;  // Wenn der Zustand des Beetes bereits Keimling wächst die Pflanze
			// Hier muss das Pflanze Bild rein
		}
		else if (bU.Zustand == 2)
		{
			bU.Zustand = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet abgeerntet und Erde bleibt zurück
			// Hier muss das Erde Bild rein
		}
}
	
	 
	
}
