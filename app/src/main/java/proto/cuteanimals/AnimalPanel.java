package proto.cuteanimals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class AnimalPanel extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private static final int IMG_HEIGHT = 156;
    private Bitmap DOG_IMG = BitmapFactory.decodeResource(getResources(), R.drawable.doggo);


    private PanelThread loopThread;
    private ArrayList<Animal> animals;
    private int yMax;
    private int xMax;



    // setup
    public AnimalPanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        animals = new ArrayList<>();

        this.setOnTouchListener(this);

    }

    /**
     * Draws animals on the canvas
     * For each animal:
     *  First updates animal
     *  Then checks if after update, animal is in close proximity with any other animal and acts accordingly
     *  Then draws the animal
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        for (Animal animal : animals) {
            animal.update(animals);
            animal.checkBounds(xMax, yMax);
            animal.onDraw(canvas);
        }
    }


    // creates loopThread
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);


        loopThread = new PanelThread(getHolder(), this);
        loopThread.setRun(true);
        loopThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // stops loopThread
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        try {
            loopThread.setRun(false);
            loopThread.join();
        } catch (InterruptedException e) {}
    }

    // sets yMax
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        xMax = w;
        yMax = h - IMG_HEIGHT;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    /**
     * Adds new Animal when tap the screen
     * The animal will start from top of screen with different rotation and starting x
     * based on x coordinates of tap
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            // remove animal if there are more than MAX_NUM_ANIMALS
	        /*if (animals.size() >= MAX_NUM_ANIMALS) {
                animals.remove(0);
            }*/

            float x = motionEvent.getX();
            animals.add(new Animal("Dog", x-IMG_HEIGHT, (x%3+1)*10, DOG_IMG));

            return true;
        }

        return false;
    }
}


