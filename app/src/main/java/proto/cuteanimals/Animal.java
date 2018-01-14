package proto.cuteanimals;


import android.graphics.Matrix;

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

	// Constructor with name of animal, start x location, and rotation change
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


    // changes y coordinates by SPEED and rotates by rotation
	public void update(int yMax, int imgw, int imgh) {
	    if ((y + SPEED) <= yMax) {

	        angle += rotation;;
            y += SPEED;
            rotateMatrix(imgw, imgh);

        }
    }

    // creates new Matrix indicating location of the animal
    public void rotateMatrix(int imgw, int imgh) {
        Matrix newMatrix = new Matrix();
        newMatrix.postRotate(angle, imgw/2, imgh/2);
        newMatrix.postTranslate(x, y);
        matrix.set(newMatrix);
    }



	
    
	
	
}
