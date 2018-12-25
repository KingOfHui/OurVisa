package com.eshel.ourvisa.ui.home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.mvp.base.MVPActivity;
import com.eshel.ourvisa.mvp.view.IHomeView;
import com.eshel.ourvisa.titles.NormalTitleHolder;
import com.eshel.ourvisa.ui.home.fragments.shopping_cart.ShoppingCartFragment;
import com.eshel.ourvisa.ui.home.fragments.my.MyFragment;
import com.eshel.ourvisa.ui.home.fragments.notification.NotificationFragment;
import com.eshel.ourvisa.ui.home.fragments.visa.VisaFragment;
import com.eshel.ourvisa.ui.jump.JumpUtil;
import com.eshel.ourvisa.util.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends MVPActivity<NormalTitleHolder, HomePresenter> implements IHomeView {

    private static final int POSITION_VISA = 0;
    private static final int POSITION_DISCOVER = 1;
    private static final int POSITION_NOTIFICATION = 2;
    private static final int POSITION_MY = 3;

    @BindView(R.id.bottom_bar)
    BottomNavigationView bottomBar;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    private HomePageAdapter mPageAdapter;

    @Override
    public NormalTitleHolder initTitleHolder() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JumpUtil.getJump().jumpLogin(this, false);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mPageAdapter = new HomePageAdapter();
        vpHome.setAdapter(mPageAdapter);
        vpHome.setOffscreenPageLimit(4);
        bottomBar.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        vpHome.setCurrentItem(POSITION_VISA, false);
                        break;
                    case R.id.action_shopping_cart:
                        vpHome.setCurrentItem(POSITION_DISCOVER, false);
                        break;
                    case R.id.action_notification:
                        vpHome.setCurrentItem(POSITION_NOTIFICATION, false);
                        break;
                    case R.id.action_my:
                        vpHome.setCurrentItem(POSITION_MY, false);
                        break;
                        default:
                            return false;
                }
                return true;
            }
        });
    }

    public class HomePageAdapter extends FragmentPagerAdapter{

        private HomePageAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case POSITION_VISA:
                    fragment = new VisaFragment();
                    break;
                case POSITION_DISCOVER:
                    fragment = new ShoppingCartFragment();
                    break;
                case POSITION_NOTIFICATION:
                    fragment = new NotificationFragment();
                    break;
                case POSITION_MY:
                    fragment = new MyFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return POSITION_MY + 1;
        }
    }
}

