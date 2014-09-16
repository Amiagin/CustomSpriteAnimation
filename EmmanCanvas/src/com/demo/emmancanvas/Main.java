package com.demo.emmancanvas;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class Main extends ActionBarActivity {
    private RelativeLayout spriteContainer = null;
    private EmmanCanvas spriteView = null;
    private int spriteId = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        spriteContainer = (RelativeLayout)findViewById(R.id.sprite_container);
        if (spriteContainer != null) {
        	       	
        	spriteView = new EmmanCanvas(this);

        	spriteView.loadAlbert(BitmapFactory.decodeResource(getResources(),R.drawable.albert_e_small));
        	spriteContainer.addView(spriteView);
        	spriteView.setPosition(200, 300);
        	spriteView.animateSprite(this,spriteId);
        }
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
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
		if (!spriteView.isAnimating()) {
			spriteView.animateSprite(this,spriteId % 3);
			spriteId++;
		}
    	return super.onTouchEvent(event);
    }
}
