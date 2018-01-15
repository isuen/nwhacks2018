package proto.cuteanimals;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;


public class Animal {
	
	private String name;
	private float x;
	private float y;
	private float angle;
	private float rotation;
	private Matrix matrix;
    private Bitmap img;
    private int yMax;

	private static float Y_START = 0;
    private static float SPEED = 20;
    private int imgSrc;

    /** Constructor with name of animal, start x location, and rotation change
     *  Only used by AnimalView class (extends View)
     * @param name
     * @param x
     * @param rotation
     */

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

    /** Constructs Animal with given name, starting x location, rotation, max y, and image
     *  Used to create animals in AnimalPanel (extends SurfaceView)
     *
     * @param name name of animal
     * @param x starting x location
     * @param rotation rotation change per tick
     * @param yMax maximum y location
     * @param img animal's image
     */
	public Animal(String name, float x, float rotation, int yMax, Bitmap img) {
        this.name = name;
        this.imgSrc = R.drawable.doggo;
        this.img = img;
        this.yMax = yMax;
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

    public Bitmap getBitmap() {
	    return img;
    }


    /**
     * Updates location and rotation of animal image
     * @param yMax max y location
     * @param imgw width of image
     * @param imgh height of image
     */
	public void update(int yMax, int imgw, int imgh) {
	    if ((y + SPEED) <= yMax) {

	        angle += rotation;;
            y += SPEED;
            rotateMatrix(imgw, imgh);

        }
    }

    /**
     * Updates matrix used to draw animal on canvas by creating a new matrix
     * with appropriate location and rotation
     * @param imgw width of image to be drawn
     * @param imgh height of image to be drawn
     */
    public void rotateMatrix(int imgw, int imgh) {
        Matrix newMatrix = new Matrix();
        newMatrix.postRotate(angle, imgw/2, imgh/2);
        newMatrix.postTranslate(x, y);
        matrix.set(newMatrix);

    }

    /**
     * Draws animal onto canvas
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        update(yMax, img.getWidth(), img.getHeight());
        canvas.drawBitmap(img, matrix, null);
    }



	
    
	
	
}
