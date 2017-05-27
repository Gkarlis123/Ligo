package karlis_grintals.ligo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FloatingActionButton checkList = (FloatingActionButton) findViewById(R.id.checkList);
//        checkList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder checkListBuilder =  new AlertDialog.Builder(MainActivity.this);
//                View chechListView = getLayoutInflater().inflate(R.layout.checklist, null);
//            }
//        });
    }

}
