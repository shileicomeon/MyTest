package collect.jhjz.com.mytest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import collect.jhjz.com.mytest.R;
import collect.jhjz.com.mytest.utils.CustomPopupWindow;

public class HomeFragment extends Fragment implements View.OnClickListener {

    /*public static final String CONTENT = "content";
    private TextView mTextView;*/
    private TextView dianJi;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.home_fragment,null);
        Toast.makeText(getActivity(),"a",Toast.LENGTH_SHORT).show();
        dianJi = view.findViewById(R.id.dianji);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dianJi.setOnClickListener(this);
    }

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Override
    public void onClick(View v) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "text内容.");
        shareIntent.setType("text/plain");
        //设置标题(弹出分享列表的界面标题),
        startActivity(Intent.createChooser(shareIntent, "分享到"));
        //ARouter.getInstance().build("/activity/PersonActivity").navigation();
    }
    public void init(){
        CustomPopupWindow popupWindow = new CustomPopupWindow.Builder()
                .setContext(getActivity()) //设置 context
                .setContentView(R.layout.item_popup) //设置布局文件
                .setwidth(300) //设置宽度，由于我已经在布局写好，这里就用 wrap_content就好了
                .setheight(500) //设置高度
                .setFouse(true)  //设置popupwindow 是否可以获取焦点
                .setOutSideCancel(true) //设置点击外部取消
                .setAnimationStyle(R.style.popup_anim_style) //设置popupwindow动画
                //  .setBackGroudAlpha(mActivity,0.7f) //是否设置背景色，原理为调节 alpha
                .builder() //
                .showAtLocation(R.layout.home_fragment, Gravity.CENTER,0,0); //设置popupwindow居中显示
    }
}