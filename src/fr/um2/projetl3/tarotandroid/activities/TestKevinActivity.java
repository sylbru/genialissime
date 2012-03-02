package fr.um2.projetl3.tarotandroid.activities;

import java.io.File;

import com.um2.projetl3.tarotandroid.R;

import fr.um2.projetl3.tarotandroid.clients.JoueurIA;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.keplerproject.luajava.*;

public class TestKevinActivity extends Activity {
	JoueurIA testy;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kevlayout);
		testy = new JoueurIA();
		final LuaState L = LuaStateFactory.newLuaState();
		L.openLibs();
		L.LdoFile("/sdcard/sl4a/scripts/init.lua");
		try {
			L.pushObjectValue(testy);
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		L.setGlobal("testy");
		final Button boutonTest = (Button) findViewById(R.id.kevsbutton1);
		boutonTest.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				
				String s = "Hello";
				try {
					L.pushObjectValue(s);
				} catch (LuaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				L.setGlobal("s");
				L.LdoFile("/sdcard/sl4a/scripts/test.lua");
				s= L.getLuaObject("s").getString();
				boutonTest.setTextSize(72);
				boutonTest.setText(s);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Rien
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return true;
		// Rien
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// useless atm
	}

}
