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

import com.github.gpallas16.staterevi.StateRevi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleFragment extends Fragment implements MainActivity.EventHandler {

    private SampleReviAdapter sampleAdapter;
    private StateRevi recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sampleAdapter = new SampleReviAdapter();

        recyclerView = view.findViewById(R.id.stateRecyclerView);
        recyclerView.setAdapter(sampleAdapter);

        postList(sampleAdapter, getSampleList());
    }

    private void postList(SampleReviAdapter sampleAdapter, List<MockModel> list) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> sampleAdapter.setList(list), 2000);
    }

    private List<MockModel> getSampleList() {
        return Arrays.asList(
                new MockModel("Lorem"),
                new MockModel("Ipsum"),
                new MockModel("Dolor"),
                new MockModel("Sit"),
                new MockModel("Amet")
        );
    }

    @Override
    public void onEmptyClicked() {
        recyclerView.setState(StateRevi.State.LOADING);
        postList(sampleAdapter, Collections.emptyList());
    }

    @Override
    public void onDataClicked() {
        sampleAdapter.setList(null);
        postList(sampleAdapter, getSampleList());
    }
}
