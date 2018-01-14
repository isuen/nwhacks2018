package proto.cuteanimals;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import java.util.Date;
import java.util.Random;

public class Animal {
	
	private String name;
	private int imgSrc;
	private float x;
	private float y;
	private float angle;
	private static float SPEED = 40;
	private float rotation;
	private Matrix matrix;

	private static float Y_START = -200;
	
	public Animal(String name, float x, float rotation) {
		this.name = name;
		this.imgSrc = R.drawable.doggo;

		//Random rand = new Random(System.currentTimeMillis());
		//x = rand.nextInt((max-min) + 1) + min;
        this.x = x;
		this.y = Y_START;
		this.rotation = rotation;
		angle = 0;
		matrix = new Matrix();
	}

	public String getName() {
	    return name;
    }

    public int getImgSrc() {
	    return imgSrc;
    }

    public float getX() {
	    return x;
    }

    public float getY() {
	    return y;
    }

    public float getAngle() {
	    return angle;
    }

    public Matrix getMatrix() {
	    return matrix;
    }


	public void update(int yMax, int imgw, int imgh) {
	    if ((y + SPEED) <= yMax) {

	        angle += rotation;;
            y += SPEED;
            rotateMatrix(imgw, imgh);

        }
    }

    public void rotateMatrix(int imgw, int imgh) {
        Matrix newMatrix = new Matrix();
        newMatrix.postRotate(angle, imgw/2, imgh/2);
        newMatrix.postTranslate(x, y);
        matrix.set(newMatrix);
    }



	
    
	
	
}
