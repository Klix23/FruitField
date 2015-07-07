package de.dhstudent.fruitfield;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int[][] Spielfeld = null;
	private int Klicks = 0;
	
	private long startTime = 0L;

	private Handler timeHandler = new Handler();
	AlertDialog.Builder dialog = null;
	
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	long bestZeit = 0L;
	int bestKlicks = 0;
	
	private void loadHighscore() {
		final SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		
		bestZeit = settings.getLong("bestZeit", 0L);
		bestKlicks = settings.getInt("bestKlicks", 0);
		
		int secs = (int) (bestZeit / 1000);
		int mins = secs / 60;
		int hrs = mins / 60;
		secs = secs % 60;

		final TextView Werte = (TextView) findViewById(R.id.werte);
		Werte.setText("Zeit: " + String.format("%02d", hrs) + ":" + String.format("%02d", mins) + ":"
				+ String.format("%02d", secs) + "   Klicks: " + bestKlicks);
	}
	
	private void saveHighscore() {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = settings.edit();

		if(timeSwapBuff < bestZeit || bestZeit == 0) {
			editor.putLong("bestZeit", updatedTime);
			editor.putInt("bestKlicks", Klicks);
		} else if (updatedTime == bestZeit && Klicks < bestKlicks) {
			editor.putInt("bestKlicks", Klicks);
		}
		editor.commit();
	}
	
	private Runnable updateTimerThread = new Runnable() {

		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			int hrs = mins / 60;
			secs = secs % 60;

			final TextView Zeittext = (TextView) findViewById(R.id.dateZeit);
			Zeittext.setText("Time: " + String.format("%02d", hrs) + ":" + String.format("%02d", mins) + ":"
					+ String.format("%02d", secs));
			timeHandler.postDelayed(this, 0);
		}

	};

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

	private void befülleSpieldfeld() {
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
		loadHighscore();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	public void btnSpielen(View view) {
		setContentView(R.layout.game_gui); // Funktion Spielen-Button im
											// Startmenu
		Spielfeld = new int[5][4];
		Klicks = 0;
		befülleSpieldfeld();
		startTime = SystemClock.uptimeMillis();
		timeHandler.postDelayed(updateTimerThread, 0);
	}

	public void btnRegeln(View view) {
		setContentView(R.layout.rules_gui); // Funktion Regeln-Button im
											// Startmenu
	}

	public void btnZurueck(View view) {
		setContentView(R.layout.welcome_gui); // Funktion Zurück-Button bei den Spielregeln
		loadHighscore();
	}

	public void Klick(View view) {
		//Klick zaehlen
		countKlick();
		
		// View-Id wird dem String IdAsString zugewiesen
		String IdAsString = view.getResources().getResourceName(view.getId());

		// Casten der jeweiligen Chars in Int
		int Zeile = Integer.parseInt(IdAsString.substring(IdAsString.length() - 2, IdAsString.length() - 1));
		int Spalte = Integer.parseInt(IdAsString.substring(IdAsString.length() - 1));

		changeKreuz(Zeile - 1, Spalte - 1);
		if (winCheck()) {
			stopTime();
			saveHighscore();
			showWinDialog();
		}
	}

	private void showWinDialog() {
		dialog = new AlertDialog.Builder(this);
		dialog.setCancelable(false);
		dialog.setTitle("Gewonnen!");
		dialog.setMessage("Fantastisch, du hast es geschafft!");
		dialog.setNeutralButton("OK", new OnClickListener() {
			
			public void onClick(DialogInterface arg0, int arg1) {
				dialog.create().dismiss();
				setContentView(R.layout.welcome_gui);
				loadHighscore();
			}
		});
		dialog.create().show();
	}

	private void changeImage(int Zeile, int Spalte) {
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

	private void changeKreuz(int Zeile, int Spalte) {
		changeStatus(Zeile, Spalte);
		changeImage(Zeile, Spalte);
		
		if (Spalte - 1 >= 0) {
			changeStatus(Zeile, Spalte - 1);
			changeImage(Zeile, Spalte - 1);
		}
		if (Spalte + 1 < 4) {
			changeStatus(Zeile, Spalte + 1);
			changeImage(Zeile, Spalte + 1);
		}
		if (Zeile - 1 >= 0) {
			changeStatus(Zeile - 1, Spalte);
			changeImage(Zeile - 1, Spalte);
		}
		if (Zeile + 1 < 5) {
			changeStatus(Zeile + 1, Spalte);
			changeImage(Zeile + 1, Spalte);
		}
	}
	
	private boolean winCheck() {
		int check = 0;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				check += Spielfeld[i][j];
			}
		}
		if(check == 0){
			return true;
		} else {
			return false;
		}
	}
	
	private void countKlick() {
		Klicks++;
		final TextView Klicktext = (TextView) findViewById(R.id.intKlicks);
		Klicktext.setText(
		    "Klicks: " + Klicks);
	}
	
	private void stopTime() {
		timeSwapBuff += timeInMilliseconds;
		timeHandler.removeCallbacks(updateTimerThread);
	}

	private String idwriter(int Zeile, int Spalte) {
		Zeile++;
		Spalte++;
		String stringtemplate = "de.dhstudent.fruitfield:id/btnFeld";
		String result = stringtemplate + Zeile + Spalte;
		return result;

	}
}
