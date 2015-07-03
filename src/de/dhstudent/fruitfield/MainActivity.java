package de.dhstudent.fruitfield;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	int[][] Spielfeld = null;

	private void changeStatus(int Zeile, int Spalte) {
		if (Spielfeld[Zeile][Spalte] == 0) {
			Spielfeld[Zeile][Spalte] = 1; // Wenn der Zustand auf 0 steht
											// wechselt er in den
			// Zustand des Keimlings
		} else if (Spielfeld[Zeile][Spalte] == 1) {
			Spielfeld[Zeile][Spalte] = 2; // Wenn der Zustand des Beetes bereits
											// Keimling ist
			// waechst die Pflanze
		} else if (Spielfeld[Zeile][Spalte] == 2) {
			Spielfeld[Zeile][Spalte] = 0; // Wenn der Zustand bereits Pflanze
											// ist wird das Beet
			// abgeerntet und Erde bleibt zurueck
		}
	}

	void befülleSpieldfeld() {
		// Erzeugen eines zweidminensionalen Arrays "Spielfeld"
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				// Berechnung eines zufälligen Zustandes
				Random rand = new Random();
				int intrandomZustand = rand.nextInt(3);
				// Setzen des Beetes mit zufälligem Zustand. Zeile
				// und Spalte aus den Schleifenwerten
				Spielfeld[i][j] = intrandomZustand;
				changeImage(i, j);
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
		return false;
	}

	public void btnSpielen(View view) {
		setContentView(R.layout.game_gui); // Funktion Spielen-Button im
											// Startmenu
		Spielfeld = new int[5][4];
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

		// Casten der jeweiligen Chars in Int
		int Zeile = Integer.parseInt(IdAsString.substring(IdAsString.length() - 2, IdAsString.length() - 1));
		int Spalte = Integer.parseInt(IdAsString.substring(IdAsString.length() - 1));

		changeKreuz(Zeile - 1, Spalte - 1);
	}

	public void changeImage(int Zeile, int Spalte) {
		int zustand = Spielfeld[Zeile][Spalte];

		String StringId = idwriter(Zeile, Spalte);
		int intId = getResources().getIdentifier(StringId, "id",
				"de.dhstudent.fruitfield");
		View view = findViewById(intId);

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

	public void changeKreuz(int Zeile, int Spalte) {
		changeStatus(Zeile, Spalte);
		changeImage(Zeile, Spalte);
		
		if (Spalte - 1 > 0) {
			changeStatus(Zeile, Spalte - 1);
			changeImage(Zeile, Spalte - 1);
		}
		if (Spalte + 1 < 4) {
			changeStatus(Zeile, Spalte + 1);
			changeImage(Zeile, Spalte + 1);
		}
		if (Zeile - 1 > 0) {
			changeStatus(Zeile - 1, Spalte);
			changeImage(Zeile - 1, Spalte);
		}
		if (Zeile + 1 < 5) {
			changeStatus(Zeile + 1, Spalte);
			changeImage(Zeile + 1, Spalte);
		}
	}

	public String idwriter(int Zeile, int Spalte) {
		Zeile++;
		Spalte++;
		String stringtemplate = "de.dhstudent.fruitfield:id/btnFeld";
		String result = stringtemplate + Zeile + Spalte;
		return result;

	}
}
