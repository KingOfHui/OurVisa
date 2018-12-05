package com.eshel.ourvisa.ui.home.fragments.visa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import com.eshel.ourvisa.mvp.view.IVisaView;
import com.eshel.ourvisa.titles.NormalTitleHolder;
import com.eshel.ourvisa.util.ThreadUtil;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VisaFragment extends MVPFragment<NormalTitleHolder, VisaPresenter> implements IVisaView {

    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected NormalTitleHolder initTitleHolder() {
        return new NormalTitleHolder(getContext()).setTitle(R.string.dovisa).setUseCardView(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.initVisaDatas();
    }

    @Override
    public View loadSuccess() {
        View root = View.inflate(getContext(), R.layout.fragment_visa, null);
        ButterKnife.bind(this, root);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.ic_launcher_background)).into(imageView);
            }
        });
        banner.isAutoPlay(true);
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @OnClick({R.id.sign_visa, R.id.double_visa, R.id.visa_doc, R.id.help_center, R.id.about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_visa:
                break;
            case R.id.double_visa:
                break;
            case R.id.visa_doc:
                break;
            case R.id.help_center:
                break;
            case R.id.about:
                break;
        }
    }

    @Override
    public void loadBannerImage(List<? extends Object> data) {
        banner.setImages(data);
        banner.start();
        banner.startAutoPlay();
    }

    @Override
    public void initDataSuccess() {
        setState(State.STATE_SUCCESS);
    }
}

