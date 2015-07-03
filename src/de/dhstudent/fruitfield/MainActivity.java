package de.dhstudent.fruitfield;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	int[][] Spielfeld = null;

	private int ChangeStatus(int Zeile, int Spalte) {
		if (Spielfeld[Zeile][Spalte] == 0) {
			Spielfeld[Zeile][Spalte] = 1; // Wenn der Zustand auf 0 steht wechselt er in den
							// Zustand des Keimlings
		} else if (Spielfeld[Zeile][Spalte] == 1) {
			Spielfeld[Zeile][Spalte] = 2; // Wenn der Zustand des Beetes bereits Keimling ist
							// waechst die Pflanze
		} else if (Spielfeld[Zeile][Spalte] == 2) {
			Spielfeld[Zeile][Spalte] = 0; // Wenn der Zustand bereits Pflanze ist wird das Beet
							// abgeerntet und Erde bleibt zurueck
		}

		FindeNachbarn(Zeile, Spalte);
		return Spielfeld[Zeile][Spalte];
	}

	private void FindeNachbarn(int Zeile, int Spalte) {

		if (Spalte - 1 > 0) {
			//beetLinks
			ChangeStatus(Spalte - 1,Zeile);
		}

		if (Spalte + 1 < 5) {
			//beetRechts
			ChangeStatus(Spalte + 1,Zeile);
		}

		if (Zeile- 1 > 0) {
			//beetOben
			ChangeStatus(Spalte, Zeile - 1);
		}

		if (Zeile + 1 < 6) {
			//beetUnten
			ChangeStatus(Spalte, Zeile + 1);
		}
	}

	void befülleSpieldfeld() {
		// Erzeugen eines zweidminensionalen Arrays "Spielfeld"
		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 5; j++) {

				// Berechnung eines zufälligen Zustandes
				Random rand = new Random();
				int intrandomZustand = rand.nextInt((2 - 0) + 1) + 0;
				// Setzen des Beetes mit zufälligem Zustand. Zeile
				// und Spalte aus den Schleifenwerten
				Spielfeld[i][j] = intrandomZustand;
			}
		}
	}

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

	public void btnSpielen(View view) {
		setContentView(R.layout.game_gui); // Funktion Spielen-Button im
											// Startmenu
		Spielfeld = new int[4][5];
		befülleSpieldfeld();
	}

	public void btnRegeln(View view) {
		setContentView(R.layout.rules_gui); // Funktion Regeln-Button im
											// Startmenu
	}

	public void btnZurueck(View view) {
		setContentView(R.layout.welcome_gui); // Funktion Zurück-Button bei den
												// Spielregeln
	}

	public void Klick(View view) {
		// View-Id wird dem String IdAsString zugewiesen
		String IdAsString = view.getResources().getResourceName(view.getId());
		// ImageButton button = (ImageButton)findViewById(view.getId());
		// button.setBackgroundResource(R.drawable.plant);

		// Kürzen von IdAsString auf die letzten zwei Chars
		String stringKoordinaten = IdAsString.substring(2);

		// Casten der jeweiligen Chars in Int
		int Zeile = Character.getNumericValue(stringKoordinaten.charAt(1));
		int Spalte = Character.getNumericValue(stringKoordinaten.charAt(2));

		changeKreuz(view, Zeile, Spalte);

	}

	public void changeImage(View view, int Zeile, int Spalte) {
		int zustand = Spielfeld[Spalte][Zeile];

		if (zustand == 0) {
			ImageButton button = (ImageButton) findViewById(view.getId());
			button.setBackgroundResource(R.drawable.soil);
		} else if (zustand == 1) {
			ImageButton button = (ImageButton) findViewById(view.getId());
			button.setBackgroundResource(R.drawable.plant);
		} else if (zustand == 2) {
			ImageButton button = (ImageButton) findViewById(view.getId());
			button.setBackgroundResource(R.drawable.strawberry);
		}
	}

	public void changeKreuz(View view, int Zeile, int Spalte) {
		changeImage(view, Zeile, Spalte);

		String StringId = idwriter(view, Zeile, Spalte);
		int intId = getResources().getIdentifier(StringId, "id",
				"de.dhstudent.fruitfield");
		view = findViewById(intId);
		changeImage(view, Zeile, Spalte);

		if (Spalte - 1 > 0) {
			String StringId1 = idwriter(view, Zeile, Spalte - 1);
			int intId1 = getResources().getIdentifier(StringId1, "id",
					"de.dhstudent.fruitfield");
			view = findViewById(intId1);
			changeImage(view, Zeile, Spalte - 1);
		}
		if (Spalte + 1 < 6) {
			String StringId1 = idwriter(view, Zeile, Spalte + 1);
			int intId1 = getResources().getIdentifier(StringId1, "id",
					"de.dhstudent.fruitfield");
			view = findViewById(intId1);
			changeImage(view, Zeile, Spalte + 1);
		}
		if (Zeile - 1 > 0) {
			String StringId1 = idwriter(view, Zeile - 1, Spalte);
			int intId1 = getResources().getIdentifier(StringId1, "id",
					"de.dhstudent.fruitfield");
			view = findViewById(intId1);
			changeImage(view, Zeile - 1, Spalte);
		}
		if (Zeile + 1 < 5) {
			String StringId1 = idwriter(view, Zeile + 1, Spalte);
			int intId1 = getResources().getIdentifier(StringId1, "id",
					"de.dhstudent.fruitfield");
			view = findViewById(intId1);
			changeImage(view, Zeile + 1, Spalte);
		}
	}

	public String idwriter(View view, int Zeile, int Spalte) {
		String IdAsString = view.getResources().getResourceName(view.getId());
		String stringtemplate = IdAsString
				.substring(0, IdAsString.length() - 2);
		String result = stringtemplate + Zeile + Spalte;
		return result;

	}
}
