package com.buzzmove.placesapiexample.search_screen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.buzzmove.placesapiexample.R;

public class SearchActivity extends AppCompatActivity {

    SearchViewFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mSearchFragment = new SearchViewFragment();
        mSearchFragment.setPresenter(new SearchPresenter(mSearchFragment));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout, mSearchFragment, "SearchFragment");
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        mSearchFragment.setSearchView((SearchView) menu.findItem(R.id.search).getActionView());

        return super.onCreateOptionsMenu(menu);

    }

}
