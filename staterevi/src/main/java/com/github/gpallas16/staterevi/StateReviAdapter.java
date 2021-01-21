package com.github.gpallas16.staterevi;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class StateReviAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final ObservableList<T> list = new ObservableArrayList<>();
    private StateRevi recyclerView;
    private boolean dataArrived;
    private boolean changeApplied;

    public List<T> getList() {
        return list;
    }

    boolean isDataArrived() {
        return dataArrived;
    }

    public final void setList(@Nullable List<T> items) {
        changeApplied = false;
        dataArrived = items != null;

        final DiffUtil.DiffResult diffResult = getDiffResult(this.list, items);

        this.list.clear();

        if (items != null)
            this.list.addAll(items);

        if (diffResult == null)
            notifyDataSetChanged();
        else
            diffResult.dispatchUpdatesTo(this);

        if (!changeApplied)
            updateRecyclerView();
    }

    protected DiffUtil.DiffResult getDiffResult(List<T> oldList, List<T> newList) {
        return null;
    }

    public StateReviAdapter() {
        list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                changeApplied = true;
                updateRecyclerView();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                changeApplied = true;
                updateRecyclerView();
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                changeApplied = true;
                updateRecyclerView();
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                changeApplied = true;
                updateRecyclerView();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                changeApplied = true;
                updateRecyclerView();
            }
        });
    }

    void setRecyclerView(StateRevi recyclerView) {
        this.recyclerView = recyclerView;
    }

    private void updateRecyclerView() {
        if (recyclerView == null)
            return;

        recyclerView.setState(getItemCount() == 0 ? dataArrived ? StateRevi.State.EMPTY : StateRevi.State.LOADING : StateRevi.State.DATA);
    }

}
