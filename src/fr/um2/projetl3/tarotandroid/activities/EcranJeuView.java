package fr.um2.projetl3.tarotandroid.activities;

import static fr.um2.projetl3.tarotandroid.activities.Contexts.TAG;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class EcranJeuView extends SurfaceView 
{
	private ShapeDrawable shD;
	
	protected void onDraw(Canvas canvas)
	{
		shD.draw(canvas);
	}
	
	public void init()
	{
		int x = 10;
		int y = 10;
		int width = 300;
		int height = 50;
		Log.d(TAG, "Avant dessin");
		shD = new ShapeDrawable(new OvalShape());
		shD.getPaint().setColor(0xff74AC23);
		shD.setBounds(x, y, x + width, y + height);
		Log.d(TAG, "Après dessin");
	}
	
	public EcranJeuView(Context context)
	{
		super(context);
		init();
		int x = 10;
		int y = 10;
		int width = 300;
		int height = 50;
		Log.d(TAG, "Avant dessin");
		shD = new ShapeDrawable(new OvalShape());
		shD.getPaint().setColor(0xff74AC23);
		shD.setBounds(x, y, x + width, y + height);
		Log.d(TAG, "Après dessin");
	}
	
	public EcranJeuView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public EcranJeuView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
}
