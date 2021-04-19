package com.github.gpallas16.staterevi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StateRevi extends RecyclerView {

    @IntDef(value = {State.LOADING, State.EMPTY, State.DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int NO_STATE = -1;
        int LOADING = 0;
        int EMPTY = 1;
        int DATA = 2;
    }

    private static final String LOADING = "loading";
    private static final String STATE = "state";

    private String caption;
    private Drawable icon;
    private View emptyView;
    private View loadingView;

    private LoadingStateView loadingStateView;
    private EmptyStateView emptyStateView;

    private int lastState = State.NO_STATE;

    public StateRevi(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public StateRevi(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StateRevi(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            caption = getDefaultCaption();
            icon = getDefaultIcon();
            return;
        }

        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StateRecyclerView, 0, 0);

        caption = a.getString(R.styleable.StateRecyclerView_emptyCaption);
        icon = a.getDrawable(R.styleable.StateRecyclerView_emptyIcon);

        if (caption == null)
            caption = getDefaultCaption();

        if (icon == null)
            icon = getDefaultIcon();

        emptyStateView = StateReviResHandler.getEmptyView();
        loadingStateView = StateReviResHandler.getLoadingView();

        if (emptyStateView == null)
            emptyStateView = new EmptyStateView(
                    R.layout.widget_state_recycler_view_empty,
                    R.id.stateRecyclerViewEmptyCaption,
                    R.id.stateRecyclerViewEmptyImage
            );

        if (loadingStateView == null)
            loadingStateView = new LoadingStateView(R.layout.widget_state_recycler_view_loading);

        a.recycle();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter instanceof StateReviAdapter) {
            StateReviAdapter<?, ?> stateReviAdapter = ((StateReviAdapter<?, ?>) adapter);
            stateReviAdapter.setRecyclerView(this);
            if (!stateReviAdapter.isDataArrived())
                setState(State.LOADING);
            else
                internalUpdate();
        }


        super.setAdapter(adapter);
    }

    public void setState(@State int state) {
        if (state == lastState
                || lastState == State.NO_STATE && state == State.DATA)
            return;

        ViewGroup parent = (ViewGroup) getParent();
        View viewToReplace = this;

        if (parent == null && emptyView != null) {
            parent = (ViewGroup) emptyView.getParent();
            viewToReplace = emptyView;
        }

        if (parent == null && loadingView != null) {
            parent = (ViewGroup) loadingView.getParent();
            viewToReplace = loadingView;
        }

        if (parent == null)
            return;

        switch (state) {
            case State.DATA:
                if (this.isAttachedToWindow())
                    return;
                replaceView(parent, this, viewToReplace, true);
                break;

            case State.EMPTY:
                if (emptyView == null) {
                    emptyView = LayoutInflater.from(getContext()).inflate(emptyStateView.getLayoutId(), this, false);
                    emptyView.setTag(getEmptyTag());
                }

                if (emptyStateView.getCaptionId() != EmptyStateView.UNDEFINED_ID) {
                    TextView captionView = emptyView.findViewById(emptyStateView.getCaptionId());
                    if (!captionView.getText().toString().equals(caption))
                        captionView.setText(caption);
                }

                if (emptyStateView.getIconId() != EmptyStateView.UNDEFINED_ID) {
                    ImageView iconView = emptyView.findViewById(emptyStateView.getIconId());
                    if (iconView.getDrawable() != icon)
                        iconView.setImageDrawable(icon);
                }

                if (parent.findViewWithTag(getEmptyTag()) != null)
                    return;


                replaceView(parent, emptyView, viewToReplace, true);
                break;

            case State.NO_STATE:
            case State.LOADING:
                if (loadingView == null) {
                    loadingView = LayoutInflater.from(getContext()).inflate(loadingStateView.getLayoutId(), this, false);
                    loadingView.setTag(getLoadingTag());
                }

                if (parent.findViewWithTag(getLoadingTag()) != null)
                    return;

                replaceView(parent, loadingView, viewToReplace, false);
                break;
        }

        lastState = state;
    }

    public void setLoadingStateView(LoadingStateView loadingStateView) {
        this.loadingStateView = loadingStateView;
        internalUpdate();
    }

    public void setEmptyStateView(EmptyStateView emptyStateView) {
        this.emptyStateView = emptyStateView;
        internalUpdate();
    }

    public void setEmptyCaption(String caption) {
        this.caption = caption;

        if (emptyView == null)
            return;

        TextView captionView = emptyView.findViewById(emptyStateView.getCaptionId());

        if (captionView == null)
            return;

        if (captionView.getText().toString().equals(caption))
            return;

        captionView.setText(caption);
    }

    public void setEmptyIcon(Drawable icon) {
        this.icon = icon;
        if (lastState == State.EMPTY && emptyView != null) {
            ImageView iconView = emptyView.findViewById(emptyStateView.getIconId());
            if (iconView.getDrawable() != icon)
                iconView.setImageDrawable(icon);
        }
    }

    private void internalUpdate() {
        Adapter<?> adapter = getAdapter();
        if (adapter instanceof StateReviAdapter<?, ?>)
            setState(adapter.getItemCount() == 0 ? State.EMPTY : State.DATA);
    }


    private void replaceView(ViewGroup parent, View viewToAdd, View viewToRemove, boolean animate) {
        if (viewToRemove == null)
            return;

        if (viewToAdd.getParent() != null) {
            parent.removeView(viewToAdd);
            return;
        }

        int index = parent.indexOfChild(viewToRemove);

        if (index > -1)
            parent.removeViewAt(index);

        if (animate) {
            viewToAdd.setVisibility(GONE);
            viewToAdd.setAlpha(0f);
        }

        parent.addView(viewToAdd, index, getLayoutParams());

        if (animate)
            animateAlphaView(viewToAdd);
    }

    private String getEmptyTag() {
        return this.toString() + STATE;
    }

    private String getLoadingTag() {
        return this.toString() + LOADING;
    }

    private void animateAlphaView(View v) {
        int duration = 200;
        v.setVisibility(View.VISIBLE);
        v.animate()
                .alpha(1f)
                .setDuration(duration);
    }

    private String getDefaultCaption() {
        return StateReviResHandler.defaultCaption;
    }

    private Drawable getDefaultIcon() {
        if (StateReviResHandler.defaultIcon == 0)
            return null;
        return ContextCompat.getDrawable(getContext(), StateReviResHandler.defaultIcon);
    }
}
