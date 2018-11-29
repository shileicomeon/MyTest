package collect.jhjz.com.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import collect.jhjz.com.mytest.fragment.HomeFragment;
import collect.jhjz.com.mytest.fragment.HotPageFragment;
import collect.jhjz.com.mytest.fragment.MineFragment;
import collect.jhjz.com.mytest.fragment.VideoFragment;

public class FragmentManagerActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FrameLayout mFlContent;
    private BottomBarLayout mBottomBarLayout;
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private HotPageFragment hotPageFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmnet_manger);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
    }

    private void initData() {

        mFragmentList.add(homeFragment);
        mFragmentList.add(videoFragment);
        mFragmentList.add(hotPageFragment);
        mFragmentList.add(mineFragment);
        setFragment(0); //默认显示第一页
    }

    private void initListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);

              setFragment(currentPosition);

                if (currentPosition == 0) {
                    //如果是第一个，即首页
                    if (previousPosition == currentPosition) {
                        //如果是在原来位置上点击,更换首页图标并播放旋转动画
                        bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_loading);//更换成加载图标
                        bottomBarItem.setStatus(true);

                        //播放旋转动画
                        if (mRotateAnimation == null) {
                            mRotateAnimation = new RotateAnimation(0, 360,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                    0.5f);
                            mRotateAnimation.setDuration(800);
                            mRotateAnimation.setRepeatCount(-1);
                        }
                        ImageView bottomImageView = bottomBarItem.getImageView();
                        bottomImageView.setAnimation(mRotateAnimation);
                        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                        //模拟数据刷新完毕
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean tabNotChanged = mBottomBarLayout.getCurrentItem() == currentPosition; //是否还停留在当前页签
                                bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换成首页原来选中图标
                                bottomBarItem.setStatus(tabNotChanged);//刷新图标
                                cancelTabLoading(bottomBarItem);
                            }
                        }, 3000);
                        return;
                    }
                }
                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换为原来的图标
                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });

        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字
    }

    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }
    //设置Fragment
    private void setFragment(int dex) {
        //开启一个事务
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();

        if (homeFragment != null) {
            beginTransaction.hide(homeFragment);
        }
        if (videoFragment != null) {
            beginTransaction.hide(videoFragment);
        }
        if (hotPageFragment != null) {
            beginTransaction.hide(hotPageFragment);
        }
        if (mineFragment != null) {
            beginTransaction.hide(mineFragment);
        }

        switch (dex) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    //加入事物
                    beginTransaction.add(R.id.fl_content, homeFragment);
                } else {
                    //否则就显示
                    beginTransaction.show(homeFragment);
                }
                break;

            case 1:
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                    //加入事物
                    beginTransaction.add(R.id.fl_content, videoFragment);
                } else {
                    //否则就显示
                    beginTransaction.show(videoFragment);
                }
                break;

            case 2:
                if (hotPageFragment == null) {
                    hotPageFragment = new HotPageFragment();
                    //加入事物
                    beginTransaction.add(R.id.fl_content, hotPageFragment);
                } else {
                    //否则就显示
                    beginTransaction.show(hotPageFragment);
                }
                break;

            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    //加入事物
                    beginTransaction.add(R.id.fl_content, mineFragment);
                } else {
                    //否则就显示
                    beginTransaction.show(mineFragment);
                }
                break;
        }
        //执行
        beginTransaction.commit();
    }
    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }
}