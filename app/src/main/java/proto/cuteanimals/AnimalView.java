package proto.cuteanimals;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

public class AnimalView extends View implements View.OnTouchListener{

    private LinkedList<Animal> animals;
    private int xMax;
    private int yMax;

    private static int MAX_NUM_ANIMALS = 20;
    private static int IMG_HEIGHT = 156;


	public AnimalView(Context context) {
		super(context);

		/*Point size = new Point();
        display.getSize(size);
        yMax = size.y - IMG_HEIGHT - getNavBarHeight(context);
        */

        animals = new LinkedList<>();

        this.setOnTouchListener(this);

    }


    // returns Bitmap of animal
    public Bitmap createImg(Animal animal) {
	    return BitmapFactory.decodeResource(getResources(), animal.getImgSrc());
    }


    public void draw(Canvas canvas, Bitmap img, Matrix m) {
        canvas.drawBitmap(img, m, null);
	}


	
    @Override
    protected void onDraw(Canvas canvas) {
        for (Animal animal : animals) {
            Bitmap img = createImg(animal);
            draw(canvas, img, animal.getMatrix());
            animal.update(yMax, img.getWidth(), img.getHeight());
        }

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
        }

        invalidate();
	}

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        xMax = w - IMG_HEIGHT;
        yMax = h - IMG_HEIGHT;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
	    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            // remove animal if there are more than MAX_NUM_ANIMALS
	        /*if (animals.size() >= MAX_NUM_ANIMALS) {
                animals.remove(0);
            }*/

            float x = motionEvent.getX();
	        animals.add(new Animal("Dog", x-IMG_HEIGHT, (x%3+1)*10));

	        return true;
        }

        return false;
    }
}
