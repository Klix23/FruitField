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

/**
 * 
 * @author Belikov, Burgdörfer, Friedly, Prochota, Stohr
 *
 */
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
	
	
	/**
	 * Die Methode loadHighscore() lädt den bereits erreichten Highscore des Spieles. 
	 * Dazu zählt die beste Zeit und die besten Klicks
	 * @param bestZeit gibt die bisher beste Zeit an, mit der das Spiel gelöst wurde
	 * @param bestKlicks gibt den Highscore an, mit wie wenigen Klicks das spiel 
	 * bestZeit wird unterteilt in Stunden, Minuten und Sekunden
	 * Die berechneten bzw. ausgelesenen Werte werden als Text gesetzt
	 */
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
	
	
	
	/**
	 * In dieser Methode werden wird geprüft, ob die gespielte Zeit kürzer ist
	 * als der bisherige Highscore. Ist dies der Fall, so wird der neue Wert als Highscore gespeichert.
	 * Falls die gespielte Zeit der bisherigen Zeit entspricht, die Anzahl der Klicks jedoch geringer war
	 * werden diese als Highscore gespeichert
	 */
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
	
	
	/**
	 * 
	 */
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

	
	/**
	 * Die Methode changeStatus(Zeile, Spalte) bestimmt den Zustand in dem sich das einzelne Feld befindet
	 * Hierbei steht die 0 für Erde, 1 für den Keimling und 2 für die Pflanze. In dieser Reihenfolge werden 
	 * die Felder auch geändert. 
	 * @param Zeile gibt die Zeile des Arrays an
	 * @param Spalte gibt die Spalte des Arrays an
	 */
	private void changeStatus(int Zeile, int Spalte) {
		if (Spielfeld[Zeile][Spalte] == 0) {
			Spielfeld[Zeile][Spalte] = 1; 
			// Wenn der Zustand auf 0 steht
			// wechselt er von Erde in den
			// Zustand des Keimlings
		} else if (Spielfeld[Zeile][Spalte] == 1) {
			Spielfeld[Zeile][Spalte] = 2;
			// Wenn der Zustand des Beetes bereits
			// Keimling ist
			// waechst die Pflanze
		} else if (Spielfeld[Zeile][Spalte] == 2) {
			Spielfeld[Zeile][Spalte] = 0;
			// Wenn der Zustand bereits Pflanze
			// ist wird das Beet
			// abgeerntet und Erde bleibt zurueck
		}
	}

	/**
	 * Mit dieser Methode wird das Spielfeld erstmals befüllt.
	 * Mittels der verschachtelten for Schleifen wird jedes einzelne
	 * Feld mit einem zufälligen Zustand zwischen 0 und 2 belegt.
	 * Im Anschluss wird über changeImage das Bild daran angepasst
	 */
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

	
	/**
	 * Mit der Methode btnSpielen wird ein neues Spiel gestartet. 
	 * Zunächst wird die GUI geladen.
	 * Dann wird die Größe des Spielfeldes festgelegt (5x4) und das Spielfeld befüllt.
	 * Ab diesem Zeitpunkt wird auch die Stoppuhr gestartet
	 * @param view
	 */
	public void btnSpielen(View view) {
		setContentView(R.layout.game_gui); // Funktion Spielen-Button im
											// Startmenu
		Spielfeld = new int[5][4];
		Klicks = 0;
		befülleSpieldfeld();
		startTime = SystemClock.uptimeMillis();
		timeHandler.postDelayed(updateTimerThread, 0);
	}

	
	/**
	 * Mit dieser Methode wird die Funktion des Regel Buttons festgelegt.
	 * Sie ruft die GUI der Regeln auf
	 * @param view
	 */
	public void btnRegeln(View view) {
		setContentView(R.layout.rules_gui); // Funktion Regeln-Button im
											// Startmenu
	}

	/**
	 * Um von den Regeln zum Willkommensbildschirm zu gelangen gibt es einen "Zurueck" Button.
	 * Es wird wieder die Start GUI angezeigt und dazu der Highscore geladen
	 * @param view
	 */
	public void btnZurueck(View view) {
		setContentView(R.layout.welcome_gui); // Funktion Zurück-Button bei den Spielregeln
		loadHighscore();
	}

	/**
	 * Diese Methode gibt an, was bei einem Klick auf ein Feld geschehen soll. 
	 * Zunächst werden die Klicks hochgezählt. Anschließend wird die ViewId dem 
	 * String IdAsString zugewiesen. Die jeweiligen Charswerden im Anschluss in Ints gecarstet.
	 * Dass sich die umliegenden Felder ändern wird durch changeKreuz angestoßen.
	 * Sollten alle Felder den Zustand 0 (Erde) haben, wo ist das Spiel gewonnen. Die Zeit wird 
	 * gestoppt, der Highscore gespeichert und der Gewinn-Dialog angezeigt.
	 * @param view
	 */
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

	/**
	 * Diese Methode zeigt einen neuen Dialog an. Er informiert den 
	 * Spieler über den Sieg im Spiel
	 */
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

	/**
	 * Die Methode setzt je nach Zustand des Feldes das Bild der Erde, 
	 * des Keimlings oder der Pflanze als Hintergrund
	 * @param Zeile
	 * @param Spalte
	 */
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

	/**
	 * Die Methode changeKreuz prüft ob rechts, links, oberhalb und unterhalb des geklickten Feldes
	 * ein Feld vorhanden ist. Ist dies der Fall, so wird auch deren Status und Bild über den Aufruf 
	 * der Funktionen geändert.
	 * @param Zeile
	 * @param Spalte
	 */
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
	
	/**
	 * Die Methode winCheck() prüft, ob der Zustand eines jeden Feldes 0, also Erde ist.
	 * Ist dies der Fall ist das Spiel fertig und gewonnen.
	 * @return
	 */
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
	
	/**
	 * Über diese Methode werden, wie der Name bereits sagt, 
	 * die Klicks des Spielers während dem Spiel gezählt.
	 */
	private void countKlick() {
		Klicks++;
		final TextView Klicktext = (TextView) findViewById(R.id.intKlicks);
		Klicktext.setText(
		    "Klicks: " + Klicks);
	}
	
	/**
	 * Diese Methode ist die Stoppuhr während des Spieles
	 */
	private void stopTime() {
		timeSwapBuff += timeInMilliseconds;
		timeHandler.removeCallbacks(updateTimerThread);
	}

	/**
	 * Diese Methode berechnet eine ID für die einzelnen Spielfelder.
	 * @param Zeile
	 * @param Spalte
	 * @return
	 */
	private String idwriter(int Zeile, int Spalte) {
		Zeile++;
		Spalte++;
		String stringtemplate = "de.dhstudent.fruitfield:id/btnFeld";
		String result = stringtemplate + Zeile + Spalte;
		return result;

	}
}
