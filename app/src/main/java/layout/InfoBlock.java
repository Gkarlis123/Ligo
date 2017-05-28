package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import karlis_grintals.ligo.R;

public class InfoBlock extends Fragment {

    @BindView(R.id.infoText) TextView infoText;

    public InfoBlock() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_block, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        String buttonTag = bundle.getString("buttonTag");

        infoText.setText(buttonTag);
        // Inflate the layout for this fragment
        return view;
    }
}
