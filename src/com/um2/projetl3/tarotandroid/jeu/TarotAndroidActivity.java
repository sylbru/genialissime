package com.um2.projetl3.tarotandroid.jeu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TarotAndroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bExit = (Button) findViewById(R.id.b_exit);
        bExit.setOnClickListener(new View.OnClickListener() {
        	
        	Activity parent;
			
			@Override
			public void onClick(View v) {
				parent.finish();
				
			}
		});
    }
    
    public void exit()
    {
    	
    }
}