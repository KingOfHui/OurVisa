package com.eshel.ourvisa.ui.home.fragments.shopping_cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.State;
import com.eshel.ourvisa.mvp.base.MVPFragment;
import com.eshel.ourvisa.mvp.view.IShoppingCartView;
import com.eshel.ourvisa.titles.NormalTitleHolder;
import com.eshel.ourvisa.util.ThreadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShoppingCartFragment extends MVPFragment<NormalTitleHolder, ShoppingCartPresenter> implements IShoppingCartView {

    @BindView(R.id.confim_order)
    Button confimOrder;
    @BindView(R.id.cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.ct_sellect_all)
    CheckedTextView ctSellectAll;
    @BindView(R.id.show_amount)
    TextView showAmount;
    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoodsList;

    @Override
    protected NormalTitleHolder initTitleHolder() {
        return new NormalTitleHolder(getContext()).setTitle(R.string.bottom_shopping_cart).setUseCardView(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View loadSuccess() {
        View root = View.inflate(getContext(), R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void fristResume() {
        super.fristResume();
        ThreadUtil.postMainDelayed(new Runnable() {
            @Override
            public void run() {
                setState(State.STATE_SUCCESS);
            }
        }, mConfig.getBaseLoadingTime());
    }

    @OnClick(R.id.confim_order)
    public void onViewClicked() {
    }
}

