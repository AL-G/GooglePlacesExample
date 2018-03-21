package com.buzzmove.placesapiexample.details_screen;

import android.support.annotation.NonNull;

import com.buzzmove.placesapiexample.data.entities.Result;

public class DetailsPresenter implements DetailsContract.Presenter {

    private final DetailsContract.View view;

    public DetailsPresenter(@NonNull DetailsViewFragment view) {
        this.view = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void displayPlace(Result place) {
        view.displayPlace(place);
    }

    @Override
    public void handleError(Throwable t) {
        view.showMessage(t.getLocalizedMessage());
    }
}
