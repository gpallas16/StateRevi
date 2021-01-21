package com.github.gpallas16.staterevi;

import androidx.annotation.Nullable;

public final class StateReviResHandler {

    static LoadingStateView loadingView;
    static EmptyStateView emptyView;
    static int defaultIcon;
    static String defaultCaption;

    @Nullable
    static LoadingStateView getLoadingView() {
        return loadingView;
    }

    @Nullable
    static EmptyStateView getEmptyView() {
        return emptyView;
    }

    public static void setGlobalCustomLoadingView(LoadingStateView customLoadingView){
        StateReviResHandler.loadingView = customLoadingView;
    }

    public static void setGlobalCustomEmptyView(EmptyStateView customEmptyView){
        StateReviResHandler.emptyView = customEmptyView;
    }

    public static void setGlobalDefaultIcon(int icon) {
        defaultIcon = icon;
    }

    public static void setGlobalDefaultCaption(String caption) {
        defaultCaption = caption;
    }

}
