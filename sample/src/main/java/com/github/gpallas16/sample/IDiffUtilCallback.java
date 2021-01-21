package com.github.gpallas16.sample;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class IDiffUtilCallback extends DiffUtil.Callback {

    private final List<MockModel> oldList;
    private final List<MockModel> newList;

    public IDiffUtilCallback(List<MockModel> oldList, List<MockModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getText().equals(newList.get(
                newItemPosition).getText());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getText().equals(newList.get(
                newItemPosition).getText());
    }
}
