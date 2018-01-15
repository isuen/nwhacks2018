/* code from Daniel Nadeau
 * http://blog.danielnadeau.io/2012/01/android-canvas-beginners-tutorial.html
 */

package proto.cuteanimals;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


class PanelThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private AnimalPanel panel;
    private boolean run = false;


    public PanelThread(SurfaceHolder surfaceHolder, AnimalPanel panel) {
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }


    public void setRun(boolean run) { //Allow us to stop the thread
        this.run = run;
    }


    @Override
    public void run() {
        Canvas c;
        while (run) {     //When setRun(false) occurs, run is
            c = null;      //set to false and loop ends, stopping thread


            try {


                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    //Insert methods to modify positions of items in onDraw()
                    panel.postInvalidate();


                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}

