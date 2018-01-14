package proto.cuteanimals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;


public class AnimalScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = new AnimalView(this);
        v.setBackgroundResource(R.drawable.background);

        setContentView(v);
    }


}
