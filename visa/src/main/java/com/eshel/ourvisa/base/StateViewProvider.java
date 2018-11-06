package com.eshel.ourvisa.base;

import android.view.View;

public interface StateViewProvider {
    View loadSuccess();
    View loadFailed();
    View loading();
    View loadEmpty();
}
