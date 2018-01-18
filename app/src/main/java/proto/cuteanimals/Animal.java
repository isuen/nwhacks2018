package proto.cuteanimals;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;


public class Animal {
	
	private String name;
	private float x;
	private float y;
	private float angle;
	private float rotation;
	private Matrix matrix;
    private Bitmap img;
    private boolean stopped;

    private static final float SPEED = 20;

    /** Constructs Animal with given name, starting x location, rotation, max y, and image
     *  Used to create animals in AnimalPanel (extends SurfaceView)
     *
     * @param name name of animal
     * @param x starting x location
     * @param rotation rotation change per tick
     * @param img animal's image
     */
	public Animal(String name, float x, float rotation, Bitmap img) {
        this.name = name;
        this.img = img;
        //Random rand = new Random(System.currentTimeMillis());
        //x = rand.nextInt((max-min) + 1) + min;
        this.x = x;
        this.y = 0;
        this.rotation = rotation;
        angle = 0;
        matrix = new Matrix();
        stopped = false;
    }

	public String getName() {
	    return name;
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

    public Bitmap getBitmap() {
	    return img;
    }

    public boolean isStopped() {
	    return stopped;
    }


    /**
     * Updates location and rotation of animal image
     *
     * First checks if update results in being close to any animal in animals
     *  if yes, try rolling right/left
     *  check again to see if rolling will lead to collision with another animal
     * Finally if did not collide (was not stopped)
     *  set x, y, and rotation to new values
     *
     * @param animals list of animals to check promixity against
     */
	public void update(ArrayList<Animal> animals) {
        // try update
        // check if update results in being too close to other animal
        //   if yes, try rolling right/left
        //   check again to see if is too close
        //      if yes, keep update
        //      if no, change y to rest on top of close neighbour, set stopped=true;
        // if has not been stopped
        //   set x and y and rotation to new values

        if (!stopped) {
            float xNext = x;
            float yNext = y + SPEED;
            for (Animal a : animals) {
                if (this != a) {
                    if (tooClose(x, a.getX(), y, a.getY())) {
                        xNext = tryRoll(a.getX());
                        yNext = y + (SPEED / 4);

                        float testY = yAfterProxCheck(xNext, yNext, a, animals);
                        if (testY != yNext) {
                            yNext = testY;
                            stopped = true;
                        }


                    }
                }
            }


            if (!stopped) {
                x = xNext;
                y = yNext;
                angle += rotation;
                rotateMatrix();
            }
        }

    }

    /**
     * Changes x and y to 0 or xMax/yMax if outside of given bounds
     * also changes stopped to true if y is at yMax (bottom of screen)
     * @param xMax upper x bound (right of screen)
     * @param yMax upper y bound (bottom of screen)
     */
    public void checkBounds(int xMax, int yMax) {
	    if (y < 0) {
	        y = 0;
        }
	    if (y > yMax) {
	        y = yMax;
	        stopped = true;
        }
        if (x < -img.getWidth()) {
            x = -img.getWidth();
        }
        if (x > xMax) {
            x = xMax;
        }

    }

    /**
     * Produces y coordinates after checking proximity with all animals in given list excluding
     * current animal and animal a.
     *
     * If any animal is too close, place y above that animal's y-coordinates
     * Otherwise produce given y
     * @param xNext x coordinates to check
     * @param yNext y coordinates to check
     * @param a animal not being checked
     * @param animals list of animals to check against
     * @return new y coordinate
     */
    private float yAfterProxCheck(float xNext, float yNext, Animal a, ArrayList<Animal> animals) {

        for (Animal test : animals) {
            if (test != a && test != this) {
                if (tooClose(xNext, test.getX(), yNext, test.getY()))
                    return test.getY() + img.getHeight();
            }
        }
        return yNext;
    }

    /**
     * Checks to see which way to roll: if x >= x2 (on the right), roll right; otherwise roll left
     * Also sets rotation to negative value if rolling right
     * @param x2 position of other animal this is rolling against
     * @return new x value
     */
    private float tryRoll(float x2) {
	    float rollDistance = img.getWidth()/10;
	    if (x >= x2)
	        return x + rollDistance;
        else {
            if (rotation > 0) {
                rotation = -1 * rotation;
            }
            return x - rollDistance;
        }

    }

    /**
     * Checks to see if animals at (x1, y1) and (x2, y2) are too close
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return true if points are too close, false otherwise
     */
    private boolean tooClose(float x1, float x2, float y1, float y2) {
        return distance(x1, x2, y1, y2) < (img.getWidth()-img.getWidth()/6);
    }


    /**
     *
     * @param x1 x-coordinate of point 1
     * @param x2 x-coodrinate of point 2
     * @param y1 y-coordinate of point 1
     * @param y2 y-coordinate of point 2
     * @return distance between two points (x1, y1) and (x2, y2)
     */

    private double distance(float x1, float x2, float y1, float y2) {
	    return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    /**
     * Updates matrix used to draw animal on canvas by creating a new matrix
     * with appropriate location and rotation
     */
    public void rotateMatrix() {
        Matrix newMatrix = new Matrix();
        newMatrix.postRotate(angle, img.getWidth()/2, img.getHeight()/2);
        newMatrix.postTranslate(x, y);
        matrix.set(newMatrix);

    }

    /**
     * Draws animal onto canvas
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(img, matrix, null);
    }


    /*
    // returns true if animal at x, y would result in collision with any animal in animals,
    // excluding this and a
    private boolean hitOtherAnimal(float x, float y, Animal a, ArrayList<Animal> animals) {
        for (Animal test : animals) {
            if (test != a && test != this) {
                if (tooClose(x, test.getX(), y, test.getY()))
                    return true;
            }
        }
        return false;
    }

    // check to see if this animal overlaps with other animal if the other animal has
    // reached a stopped state
    // roll right if this.x is the same or to the right of a.x, left otherwise
    // ASSUME: this and a are not the same animal
    public void checkProxInit(Animal a) {
        if (a.isStopped()) {
            // calculate overlap based on width - assume animals are circles
            if (distance(x, a.getX(), y, a.getY()) < img.getWidth()) {
                if (x >= a.getX()) {
                    rollRight();
                } else {
                    rollLeft();
                }
            }
        }
    }


    public void checkProxFin(Animal a) {
        if (a.isStopped()) {
            // calculate overlap based on width - assume animals are circles
            if (distance(x, a.getX(), y, a.getY()) < img.getWidth()) {

                stopped = true;
            }
        }
    }

    private void rollRight() {
	    x += (img.getWidth()/4);
	    y += (SPEED/2);
    }

    private void rollLeft() {
        x -= (img.getWidth()/4);
        y += (SPEED/2);
    }*/








}
