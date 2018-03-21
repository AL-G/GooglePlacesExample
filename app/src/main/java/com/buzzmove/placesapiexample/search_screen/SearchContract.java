package com.buzzmove.placesapiexample.search_screen;

import android.content.Context;

import com.buzzmove.placesapiexample.BasePresenter;
import com.buzzmove.placesapiexample.BaseView;
import com.buzzmove.placesapiexample.data.entities.Result;

import java.util.List;

/**
 * Specifies the contract between the view and the presenter.
 */
public interface SearchContract {

    interface View extends BaseView<Presenter> {

        void updateSearchResult(List<Result> results);

        void showMessage(String message);

        void showEmpty();

    }

    interface Presenter extends BasePresenter {

        void search(String search);

        void handleError(Throwable t);

    }
}
