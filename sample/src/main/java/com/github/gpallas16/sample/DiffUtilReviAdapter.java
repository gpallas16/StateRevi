package com.github.gpallas16.sample;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffUtilReviAdapter extends SampleReviAdapter {

    @Override
    protected DiffUtil.DiffResult getDiffResult(List<MockModel> oldList, List<MockModel> newList) {
        return DiffUtil.calculateDiff(new IDiffUtilCallback(oldList, newList));
    }

}
