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

import java.util.Collections;

public class DiffUtilFragment extends Fragment implements MainActivity.EventHandler {

    private DiffUtilReviAdapter diffUtilAdapter;
    private StateRevi recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        diffUtilAdapter = new DiffUtilReviAdapter();

        recyclerView = view.findViewById(R.id.stateRecyclerView);
        recyclerView.setAdapter(diffUtilAdapter);

        postDiffList();
    }

    private void postDiffList() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> diffUtilAdapter.setList(Helper.getSampleList()), 1000);
        new Handler(Looper.getMainLooper()).postDelayed(() -> diffUtilAdapter.setList(Helper.getSampleList().subList(0,3)), 2000);
    }

    @Override
    public void onEmptyClicked() {
        recyclerView.setState(StateRevi.State.LOADING);
        new Handler(Looper.getMainLooper()).postDelayed(() -> diffUtilAdapter.setList(Collections.emptyList()), 2000);
    }

    @Override
    public void onDataClicked() {
        recyclerView.setState(StateRevi.State.LOADING);
        postDiffList();
    }
}
