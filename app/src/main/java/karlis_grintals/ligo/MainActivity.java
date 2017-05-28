package karlis_grintals.ligo;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R2.id.humanButtonOne, R2.id.humanButtonTwo, R2.id.humanButtonThree}) void showInfo (View v) {

        InfoBlock fragment = (InfoBlock) getSupportFragmentManager().findFragmentById(R.id.infoBlockFragment);

        Bundle buttonBundle = new Bundle();
        String buttonTag = (String) v.getTag();



        if (fragment != null) {
            String fragTag = (String) fragment.getTag();


                buttonBundle.putString("buttonTag", buttonTag);

                InfoBlock newInfBlock = new InfoBlock();
                newInfBlock.setArguments(buttonBundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                        .replace(fragment.getId(), newInfBlock, buttonTag)
                        .commit();

        } else {
            buttonBundle.putString("buttonTag", buttonTag);

            InfoBlock newInfBlock = new InfoBlock();
            newInfBlock.setArguments(buttonBundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                    .add(R.id.infoBlockFragment, newInfBlock, buttonTag)
                    .commit();
        }
    }
}
