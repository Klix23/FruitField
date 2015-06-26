package de.dhstudent.fruitfield;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
}
