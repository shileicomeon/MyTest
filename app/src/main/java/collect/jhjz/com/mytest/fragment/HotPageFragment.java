package collect.jhjz.com.mytest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import collect.jhjz.com.mytest.R;

public class HotPageFragment extends Fragment {

    public static final String CONTENT = "content";
    private TextView mTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.hotpage_fragment,null);
        Toast.makeText(getActivity(),"c",Toast.LENGTH_SHORT).show();

        return view;
    }
    public static HotPageFragment getInstance(){
        return new HotPageFragment();
    }


}