package com.github.gpallas16.staterevi;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public class EmptyStateView {

    public static final int UNDEFINED_ID = -1;

    private final int layoutId;
    private final int captionId;
    private final int iconId;

    public EmptyStateView(@LayoutRes int layoutId, @IdRes int captionId, @IdRes int iconId) {
        this.layoutId = layoutId;
        this.captionId = captionId;
        this.iconId = iconId;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getCaptionId() {
        return captionId;
    }

    public int getIconId() {
        return iconId;
    }
}
