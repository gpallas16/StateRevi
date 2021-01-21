package com.github.gpallas16.staterevi;

import androidx.annotation.LayoutRes;

public final class LoadingStateView {

    private @LayoutRes final int layoutId;

    public LoadingStateView(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
}
