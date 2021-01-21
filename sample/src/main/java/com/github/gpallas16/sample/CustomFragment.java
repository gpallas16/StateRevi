package com.github.gpallas16.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.gpallas16.staterevi.EmptyStateView;
import com.github.gpallas16.staterevi.LoadingStateView;
import com.github.gpallas16.staterevi.StateRevi;

import java.util.Collections;
import java.util.List;

public class CustomFragment extends Fragment implements MainActivity.EventHandler {

    private StateRevi recyclerView;
    private SampleReviAdapter sampleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sampleAdapter = new SampleReviAdapter();

        recyclerView = view.findViewById(R.id.stateRecyclerView);

        recyclerView.setEmptyStateView(
                new EmptyStateView(
                        R.layout.view_custom_empty,
                        R.id.customCaption,
                        R.id.customImage
                )
        );

        recyclerView.setLoadingStateView(
                new LoadingStateView(
                        R.layout.view_custom_loading
                )
        );

        recyclerView.setAdapter(sampleAdapter);

        postList(Helper.getSampleList());
    }

    private void postList(List<MockModel> list) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> sampleAdapter.setList(list), 2000);
    }

    @Override
    public void onEmptyClicked() {
        recyclerView.setState(StateRevi.State.LOADING);
        postList(Collections.emptyList());
    }

    @Override
    public void onDataClicked() {
        recyclerView.setState(StateRevi.State.LOADING);
        postList(Helper.getSampleList());
    }

}
