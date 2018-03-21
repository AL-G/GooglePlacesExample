package com.buzzmove.placesapiexample.details_screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buzzmove.placesapiexample.R;
import com.buzzmove.placesapiexample.data.entities.Result;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsViewFragment extends Fragment implements DetailsContract.View {

    private DetailsContract.Presenter mPresenter;
    private View root;

    @BindView(R.id.details_icon)
    ImageView details_icon;

    @BindView(R.id.details_name)
    TextView details_name;

    @BindView(R.id.details_address)
    TextView details_address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            root = inflater.inflate(R.layout.details_fragment, container, false);
        }
        ButterKnife.bind(this, root);

        return root;
    }


    @Override
    public void setPresenter(@NonNull DetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public DetailsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void displayPlace(Result place) {

        details_name.setText(place.getName());
        details_address.setText(place.getFormatted_address());

        Picasso.with(getContext())
                .load(place.getIcon())
                .into(details_icon);

    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

}
