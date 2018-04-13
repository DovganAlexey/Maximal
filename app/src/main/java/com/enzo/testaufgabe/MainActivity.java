package com.enzo.testaufgabe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private MainFragment mainFragment;
    public PersonFragment personFragment;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /// Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title = toolbar.findViewById(R.id.toolbar_title);
        fragmentManager = getSupportFragmentManager();
        mainFragment = (MainFragment) fragmentManager.findFragmentByTag("main_fragment");
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        personFragment = (PersonFragment) fragmentManager.findFragmentById(R.id.person_fragment);
    }

    public void setToolbarTittle(String name) {
        if (toolbar != null && title != null) {
            title.setText(name);
        }
    }

    public FragmentManager getFrManager() {
        return fragmentManager;
    }

    public boolean isTablet() {
        return (findViewById(R.id.fragment_container) == null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //
        if (fragmentManager.getFragments().size() == 0) {
            setFragment(mainFragment, "main_fragment");
        } else {
            if (fragmentManager.getFragments().get(0) instanceof PersonFragment) {
                setToolbarTittle(fragmentManager.getFragments().get(0).getTag());
            }
        }
        fragmentManager.addOnBackStackChangedListener(this);
        this.onBackStackChanged();
    }

    @Override
    protected void onPause() {
        fragmentManager.removeOnBackStackChangedListener(this);
        super.onPause();
    }

    private void setFragment(Fragment fragment, String fragmentTag) {
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragmentTag);
        transaction.commit();
    }

    @Override
    public void onBackStackChanged() {
        // manage back-button and toolbar title
        if (fragmentManager.getBackStackEntryCount() > 0) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            setToolbarTittle("USERS");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
