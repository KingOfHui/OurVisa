package com.eshel.ourvisa.ui.home.fragments.shopping_cart;

import com.eshel.ourvisa.mvp.base.Presenter;
import com.eshel.ourvisa.mvp.presenters.IShoppingCartPresenter;
import com.eshel.ourvisa.mvp.view.IShoppingCartView;

public class ShoppingCartPresenter extends Presenter<IShoppingCartView,ShoppingCartModle> implements IShoppingCartPresenter {

    @Override
    protected void onClose() {

    }
}

