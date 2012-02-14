package fr.um2.projetl3.tarotandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.um2.projetl3.tarotandroid.R;

public class TarotAndroidActivity extends Activity
{
	private String data; // ? (?)

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button boutonTest = (Button) findViewById(R.id.boutontest);
		boutonTest.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Toast myToast = Toast.makeText(getApplicationContext(),
						"C’est un toast.", 2);
				myToast.show();
				finish(); // ne ferme que cette activité, pas les autres, ni les
							// threads ou autres trucs
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
		case R.id.itemprefs:
			Intent myIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
			startActivity(myIntent);
			
			return true;
		case R.id.dummy:
			Toast.makeText(getApplicationContext(), "Texte bidon", 2).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// useless atm
	}
}
