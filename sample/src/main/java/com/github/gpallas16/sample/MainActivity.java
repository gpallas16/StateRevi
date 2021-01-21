package com.github.gpallas16.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.gpallas16.staterevi.StateReviResHandler;

public class MainActivity extends AppCompatActivity {

    interface EventHandler {
        void onEmptyClicked();

        void onDataClicked();
    }

    //apply defaults
    {
        StateReviResHandler.setGlobalDefaultCaption("Global caption");
        StateReviResHandler.setGlobalDefaultIcon(R.drawable.ic_global);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SimpleFragment()).commit();

        Button emptyButton = findViewById(R.id.emptyButton);
        Button dataButton = findViewById(R.id.dataButton);

        emptyButton.setOnClickListener(v -> {
            ((EventHandler) getSupportFragmentManager().getFragments().get(0)).onEmptyClicked();

        });

        dataButton.setOnClickListener(v -> {
            ((EventHandler) getSupportFragmentManager().getFragments().get(0)).onDataClicked();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.global)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GlobalFragment()).commit();
        else if (item.getItemId() == R.id.simple)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SimpleFragment()).commit();
        else if (item.getItemId() == R.id.custom)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustomFragment()).commit();
        else if (item.getItemId() == R.id.diff)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiffUtilFragment()).commit();
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GlobalFragment()).commit();
    }
}