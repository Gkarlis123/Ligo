package karlis_grintals.ligo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import layout.InfoBlock;

public class MainActivity extends AppCompatActivity {

    AppHelper appHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        appHelper = new AppHelper(this);
        SQLiteDatabase sqLiteDatabase = appHelper.getWritableDatabase();
    }


    @OnClick({R2.id.humanButtonOne, R2.id.humanButtonTwo, R2.id.humanButtonThree}) void showInfo (View v) {

        String buttonTag = (String) v.getTag();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.infoBlockFragment);

        if (fragment != null) {

            String fragmentTag = (String) fragment.getTag();

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();

            getSupportFragmentManager().popBackStack();

            if (fragmentTag.equals(buttonTag)) {
                return;
            }
        }

        Bundle buttonBundle = new Bundle();
        InfoBlock newInfBlock = new InfoBlock();

        buttonBundle.putString("buttonTag", buttonTag);
        newInfBlock.setArguments(buttonBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .addToBackStack(buttonTag)
                .replace(R.id.infoBlockFragment, newInfBlock, buttonTag)
                .commit();
    }
}
