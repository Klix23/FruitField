package de.dhstudent.fruitfield;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_gui);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void btnSpielen (View view){
		setContentView(R.layout.game_gui); // Funktion Spielen-Button im Startmenu
	}
	
	public void btnRegeln (View view){
		setContentView(R.layout.rules_gui); // Funktion Regeln-Button im Startmenu
	}
	
	public void btnZurueck (View view){
		setContentView(R.layout.welcome_gui); // Funktion Zurück-Button bei den Spielregeln
	}
	
	public void Klick(View view){
		String IdAsString = view.getResources().getResourceName(view.getId());
		//ImageButton button = (ImageButton)findViewById(view.getId());
		//button.setBackgroundResource(R.drawable.plant);
		String stringKoordinaten = IdAsString.substring(2);
		
		int Zeile = Character.getNumericValue(stringKoordinaten.charAt(1));
		int Spalte = Character.getNumericValue(stringKoordinaten.charAt(2));
		
	}
		//char Zeile = (stringKoordinaten.charAt(1));
		//char Spalte =(stringKoordinaten.charAt(2));
		
	
	
	}
	

