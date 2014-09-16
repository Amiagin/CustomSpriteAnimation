package com.demo.emmancanvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class EmmanCanvas extends View {
	private HashMap<Integer,List<Bitmap>>sprites = null;
	private List<Bitmap>eyes = null;
	private int index = 0, spriteId = 0;
    private boolean stopAnimation = false;
    private Director director = null;
	private int xpos = 0,ypos = 0;
	
	public EmmanCanvas(Context context) {
		super(context);
	}

	public EmmanCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EmmanCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	protected void onDraw(Canvas canvas) {
		if (sprites != null) {
			canvas.drawBitmap(sprites.get(spriteId%3).get(index % sprites.get(spriteId%3).size()), xpos,ypos, null);
		    if (!stopAnimation) {
			    if (index > sprites.get(spriteId%3).size()) {
			    	stopAnimation = true;
			    } else {
			        index++;
			    }
		    }
		}
		super.onDraw(canvas);
	}
		
	public void loadEyes(Bitmap bitmap) {
		eyes = new ArrayList<Bitmap>();
		int h = bitmap.getHeight()/3;
		int w = bitmap.getWidth()/2;
		
		eyes.add(Bitmap.createBitmap(bitmap, 0,   0, w, h));
		eyes.add(Bitmap.createBitmap(bitmap, w,   0, w, h));
		eyes.add(Bitmap.createBitmap(bitmap, 0,   h, w, h));
		eyes.add(Bitmap.createBitmap(bitmap, w,   h, w, h));
		eyes.add(Bitmap.createBitmap(bitmap, 0, h*2, w, h));
		eyes.add(Bitmap.createBitmap(bitmap, w, h*2, w, h));

		index = 0;
	}
	
	public void loadAlbert(Bitmap bitmap) {
		sprites = new HashMap<Integer,List<Bitmap>>();
		int h = bitmap.getHeight()/3;
		int w = bitmap.getWidth()/7;
		int i = 0, n = 0;
		List<Bitmap>images = null;
		int map[] = {6,7,5};
		while (n < 3) {
			images = new ArrayList<Bitmap>();
			i = 0;
			while (i < map[n]) {
			    images.add(Bitmap.createBitmap(bitmap,   w*i,   h*n, w, h));
			    i++;
			}
			sprites.put(n, images);
			n++;
		}
		index = 0;
		spriteId = 0;
	}
	
	public void setPosition(int x, int y) {
		xpos = x;
		ypos = y;
	}
	
	public void animateSprite(Activity parent,int id) {
		spriteId = id % 3;
		index = 0;
		if (director != null) {
			stopAnimation = true;
			try {
			    Thread.sleep(200);
			} catch (Exception exception) {
			} finally {
				director = null;
			}
		}
		
		director = new Director(parent);
		director.start();
	}
	
	public boolean isAnimating() {
		return !stopAnimation;
	}
	
    class Director extends Thread {
    	private Activity parent = null;
    	public Director(Activity parent) {
    		this.parent = parent;
    	}
    	
    	public void run() {
    		stopAnimation = false;
    		while (!stopAnimation) {
    			parent.runOnUiThread(new Runnable() {
    				public void run() {
    		   			invalidate();   						
    				}
    			});
    			try {
    			    Thread.sleep(200);
    			} catch (Exception exception) {
    			}
    		}
    	}
    }
}
