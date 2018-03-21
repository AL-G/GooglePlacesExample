package com.buzzmove.placesapiexample.details_screen;

import com.buzzmove.placesapiexample.BasePresenter;
import com.buzzmove.placesapiexample.BaseView;
import com.buzzmove.placesapiexample.data.entities.Result;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 */
public interface DetailsContract {

    interface View extends BaseView<Presenter> {

        void displayPlace(Result place);

        void showMessage(String message);

    }

    interface Presenter extends BasePresenter {

        void displayPlace(Result place);

        void handleError(Throwable t);

    }
}
