package com.eshel.ourvisa.ui.home.fragments.visa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.bean.local.VisaCategoryV;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import com.eshel.ourvisa.mvp.view.IVisaView;
import com.eshel.ourvisa.titles.NormalTitleHolder;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VisaFragment extends MVPFragment<NormalTitleHolder, VisaPresenter> implements IVisaView {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_visa_category)
    RecyclerView rvCategory;

    @Override
    protected NormalTitleHolder initTitleHolder() {
        return new NormalTitleHolder(getContext()).setTitle(R.string.home).setUseCardView(true);
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
        initBanner();
        initCategory();
        return root;
    }

    private void initCategory() {
        rvCategory.setLayoutManager(new GridLayoutManager(getContext(), 4));
        BaseQuickAdapter<VisaCategoryV, BaseViewHolder> adapter = new BaseQuickAdapter<VisaCategoryV, BaseViewHolder>(R.layout.item_visa_category) {

            @Override
            protected void convert(BaseViewHolder helper, VisaCategoryV item) {
                helper.setText(R.id.title_visa_category, item.getTitleResId());
                helper.setImageResource(R.id.icon_visa_category, item.getIconResId());
                helper.itemView.setOnClickListener(item.getClickListener());
            }
        };
        rvCategory.setAdapter(adapter);
        rvCategory.setNestedScrollingEnabled(false);
        rvCategory.setHasFixedSize(true);
    }

    private void initBanner() {
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).apply(RequestOptions.centerCropTransform().placeholder(R.drawable.ic_launcher_background)).into(imageView);
            }
        });
        banner.isAutoPlay(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

/*    @OnClick({R.id.sign_visa, R.id.double_visa, R.id.visa_doc, R.id.help_center, R.id.about})
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
    }*/

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

    @Override
    public void refreshCategory(List<VisaCategoryV> categorys) {
        BaseQuickAdapter<VisaCategoryV, BaseViewHolder> adapter = (BaseQuickAdapter<VisaCategoryV, BaseViewHolder>) rvCategory.getAdapter();
        if(adapter != null){
            adapter.setNewData(categorys);
        }
    }
}

