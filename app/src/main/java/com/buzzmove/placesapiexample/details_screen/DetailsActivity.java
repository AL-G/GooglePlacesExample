package com.buzzmove.placesapiexample.details_screen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.buzzmove.placesapiexample.R;
import com.buzzmove.placesapiexample.data.entities.Result;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewFragment mDetailsFragment;
    private Result place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        place = getIntent().getExtras().getParcelable("place");

        mDetailsFragment = new DetailsViewFragment();
        mDetailsFragment.setPresenter(new DetailsPresenter(mDetailsFragment));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.details_frame_layout, mDetailsFragment, "DetailsFragment");
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDetailsFragment.getPresenter().displayPlace(place);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
