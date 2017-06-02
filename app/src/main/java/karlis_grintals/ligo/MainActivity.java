package karlis_grintals.ligo;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import karlis_grintals.ligo.fragments.dialogs.ChecklistDialogFragment;
import layout.InfoBlock;

public class MainActivity extends AppCompatActivity {

    AppDatabaseAdapter appHelper;
    FileManager fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        appHelper = new AppDatabaseAdapter(this);
        appHelper.setDb();

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            fileHelper = new FileManager(this);
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
    }

    @OnClick({R2.id.humanButtonOne, R2.id.humanButtonTwo, R2.id.humanButtonThree,
                R2.id.natureButtonOne, R2.id.natureButtonTwo, R2.id.natureButtonThree,
                R2.id.fireButtonOne, R2.id.fireButtonTwo, R2.id.fireButtonThree,
                R2.id.waterButtonOne, R2.id.waterButtonTwo, R2.id.waterButtonThree}) public void showInfo (View v) {

        LinearLayout params = (LinearLayout) v.getParent().getParent().getParent();
        manipulateFragment ((ConstraintLayout) params.getChildAt(1), v);
    }

    @OnClick({R2.id.floatingActionButton}) public void showDialog (View v) {

        FragmentManager fragment = getSupportFragmentManager();

        ChecklistDialogFragment checklist = new ChecklistDialogFragment();

        checklist.show(fragment, "ChecklistDialogFragment");
    }


    public void manipulateFragment (ConstraintLayout fragmentLayout, View v) {

        String buttonTag = (String) v.getTag();


        Fragment fragment = getSupportFragmentManager().findFragmentById(fragmentLayout.getId());

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
                .replace(fragmentLayout.getId(), newInfBlock, buttonTag)
                .commit();

    }
}
