package karlis_grintals.ligo.fragments.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import android.widget.CheckBox;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import karlis_grintals.ligo.AppDatabaseAdapter;
import karlis_grintals.ligo.R;
import karlis_grintals.ligo.R2;

/**
 * Created by karlis on 02/06/2017.
 */

public class ChecklistDialogFragment extends DialogFragment {

    @BindView(R2.id.checklistDialog) LinearLayout layout;
    AppDatabaseAdapter helper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialog = inflater.inflate(R.layout.dialog_fragment_checklist, container, false);
        ButterKnife.bind(this, dialog);

        helper = new AppDatabaseAdapter(getActivity());

        List<Bundle> tasks = helper.getTasks();
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        int counter = 1;

        for (Bundle task : tasks) {

            CheckBox cb = new CheckBox(getActivity());

            cb.setText( task.getString("task"));
            cb.setChecked(task.getBoolean("status"));
            cb.setLayoutParams(lparams);
            cb.setPadding(0, 15, 0, 15);
            cb.setTag("checkbox_" + counter);

            layout.addView(cb);
            counter++;
        }

        return dialog;
    }

    @OnClick({R.id.saveTasks}) public void updateTaskStatus (View v) {

        ArrayList<Bundle> checkboxList = new ArrayList<Bundle>();
        LinearLayout checkboxWrapperLayout = (LinearLayout) v.getParent();
        LinearLayout checkboxWrapper = (LinearLayout) checkboxWrapperLayout.getChildAt(1);

        final int childCount = checkboxWrapper.getChildCount();

        for (int i = 1; i <= childCount; i++) {
            CheckBox checkbox = (CheckBox)(getView().findViewWithTag("checkbox_" + i));
            Bundle checkboxBundle = new Bundle();

            Boolean checked = (Boolean) checkbox.isChecked();

            checkboxBundle.putBoolean("status", checked);
            checkboxBundle.putInt("checkbox_id", i);

            checkboxList.add(checkboxBundle);
        }

        helper.updateTasks(checkboxList);

        getDialog().dismiss();
        Toast.makeText(v.getContext(), "Jāņu Uzdevumi Saglabāti!", Toast.LENGTH_SHORT).show();
    }
}
