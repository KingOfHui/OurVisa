package com.eshel.ourvisa.ui.home.fragments.shopping_cart;

import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.IShoppingCartModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IShoppingCartModleCallback;

public class ShoppingCartModle extends Modle<IShoppingCartModleCallback> implements IShoppingCartModle {

    public ShoppingCartModle(IShoppingCartModleCallback callback) {
        super(callback);
    }

    @Override
    protected void onClose() {

    }
}

