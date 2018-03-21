package com.buzzmove.placesapiexample.search_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buzzmove.placesapiexample.R;
import com.buzzmove.placesapiexample.data.entities.Result;
import com.buzzmove.placesapiexample.details_screen.DetailsActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, SearchContract.View {

    private SearchContract.Presenter mPresenter;

    private PlacesAdapter mRecyclerviewAdapter;

    private View root;

    @BindView(R.id.places_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.no_results_view)
    View no_results_view;

    private SearchView searchView;

    private GoogleMap mMap;

    public static final int DEFAULT_ZOOM = 9;

    public SearchViewFragment() {

    }

    @Override
    public void setPresenter(@NonNull SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public SearchContract.Presenter getPresenter() {
        return mPresenter;
    }

    public void setSearchView(SearchView search_view) {
        this.searchView = search_view;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void search(String search) {
        mPresenter.search(search);
        searchView.clearFocus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            root = inflater.inflate(R.layout.search_overview_fragment, container, false);
        }
        ButterKnife.bind(this, root);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mRecyclerviewAdapter = new PlacesAdapter(getContext(), this);
        recyclerView.setAdapter(mRecyclerviewAdapter);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.recyclerview_divider));

        recyclerView.addItemDecoration(itemDecorator);

        FragmentManager fm = getChildFragmentManager();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void updateSearchResult(List<Result> results) {

        mMap.clear();

        if (results != null) {

            List<Marker> markers = new ArrayList<>();

            // add the rest of the markers
            for (int i = 0; i < results.size(); i++) {
                Result result = results.get(i);
                markers.add(mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(result.getGeometry().getLocation().getLat(),
                                result.getGeometry().getLocation().getLng()))));
            }

            // set the tag to the index position for later identification
            for (int i = 0; i < markers.size(); i++) {
                markers.get(i).setTag(i);
            }

            // If one result zoom to 8 else zoom to encompass all markers
            if (results.size() == 1) {
                // move map focus to first location
                Marker first_result = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(results.get(0).getGeometry().getLocation().getLat(),
                                results.get(0).getGeometry().getLocation().getLng())));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(first_result.getPosition(), DEFAULT_ZOOM));
            } else {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 10; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cu);
            }


            mRecyclerviewAdapter.setData(results);
            mRecyclerviewAdapter.notifyDataSetChanged();
        }

        // show the recyclerview
        recyclerView.setVisibility(View.VISIBLE);
        no_results_view.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmpty() {
        mMap.clear();
        mRecyclerviewAdapter.clear();
        recyclerView.removeAllViews();
        // hide the recyclerview
        recyclerView.setVisibility(View.GONE);
        no_results_view.setVisibility(View.VISIBLE);
    }

    public GoogleMap getMap() {
        return mMap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in London and move the camera
        LatLng london = new LatLng(51, 0);
        mMap.addMarker(new MarkerOptions().position(london));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // start detail activity
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("place", mRecyclerviewAdapter.getItem((int) marker.getTag()));
        startActivity(intent);

        return true;
    }
}
