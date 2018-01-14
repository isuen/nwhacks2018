package proto.cuteanimals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class AnimalScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new AnimalView(this));
    }


}
