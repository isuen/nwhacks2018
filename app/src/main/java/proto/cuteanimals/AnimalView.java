package proto.cuteanimals;

import android.content.Context;
import android.graphics.*;
import android.view.View;

public class AnimalView extends View {
    private Paint paint;
    private Animal animal;
    private Bitmap image;

	public AnimalView(Context context) {
		super(context);
		
		paint = new Paint();
		paint.setColor(Color.BLUE);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.doggo);

    }

	public Animal addAnimal() {
	    return new Animal("Dog");
    }

	
	
    @Override
    protected void onDraw(Canvas canvas) {
	    canvas.drawColor(Color.GRAY);
	    canvas.drawBitmap(image, 0, 0, null);
	}
    
}
