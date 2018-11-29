package collect.jhjz.com.mytest.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import collect.jhjz.com.mytest.Main2Activity;
import collect.jhjz.com.mytest.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends Fragment {

    public static final String CONTENT = "content";
    private TextView mTextView;
    private TextView tv;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver mReceiver;
    private CircleImageView circleImageView;
    private View circleImageView1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.mine_fragment,null);
        Toast.makeText(getActivity(),"d",Toast.LENGTH_SHORT).show();
        tv = view.findViewById(R.id.item_one);
        circleImageView = view.findViewById(R.id.profile_image);
        circleImageView1 = view.findViewById(R.id.profile_image1);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                startActivity(intent);
            }
        });
        circleImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                startActivity(intent);
            }
        });
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        intentFilter = new IntentFilter();
        intentFilter.addAction("hello");
        intentFilter.addAction("hellow");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                if(intent.getAction().equals("hello")){
                    circleImageView1.setVisibility(View.VISIBLE);
                    circleImageView.setVisibility(View.GONE);
                }
                if(intent.getAction().equals("hellow")){
                    circleImageView1.setVisibility(View.GONE);
                    circleImageView.setVisibility(View.VISIBLE);
                }

            }
        };
        broadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    public static MineFragment getInstance(){
        return new MineFragment();
   }
   @Subscribe(threadMode = ThreadMode.MAIN)
   public void messageEvent(String s){
        if(s.equals("s")){
            circleImageView1.setVisibility(View.VISIBLE);
            circleImageView.setVisibility(View.GONE);
        }else{
            circleImageView1.setVisibility(View.GONE);
            circleImageView.setVisibility(View.VISIBLE);
        }

   }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }
}