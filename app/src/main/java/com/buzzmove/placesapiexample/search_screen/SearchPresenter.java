package com.buzzmove.placesapiexample.search_screen;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.buzzmove.placesapiexample.data.DataSourceInterface;
import com.buzzmove.placesapiexample.data.Repository;
import com.buzzmove.placesapiexample.data.di.DaggerRepositoryComponent;
import com.buzzmove.placesapiexample.data.di.RepositoryComponent;
import com.buzzmove.placesapiexample.data.di.RepositoryModule;
import com.buzzmove.placesapiexample.data.entities.Result;

import java.util.List;

import javax.inject.Inject;

public class SearchPresenter implements SearchContract.Presenter {

    @Inject
    Repository repo;

    @Override
    public void start() {    }

    @VisibleForTesting
    private final SearchContract.View view;

    public SearchContract.View getView(){
        return view;
    }

    public SearchPresenter(@NonNull SearchContract.View view) {

        RepositoryComponent component = DaggerRepositoryComponent.builder().repositoryModule(new RepositoryModule()).build();
        repo = component.provideRepository();

        this.view = view;
    }

    @Inject
    public SearchPresenter(@NonNull SearchContract.View view, Repository placesRepository) {
        repo = placesRepository;
        this.view = view;
    }

    @Override
    public void search(String search) {
        repo.searchPlaces(new DataSourceInterface.GetPlacesCallback() {

            @Override
            public void onLoaded(List<Result> result) {
                // update the recyclerview
                if(result != null && result.size() > 0) {
                    view.updateSearchResult(result);
                } else {
                    view.showEmpty();
                }

            }

            @Override
            public void onDataNotAvailable() {
                view.showEmpty();

            }
        }, search);
    }

    @Override
    public void handleError(Throwable t) {
        view.showMessage(t.getLocalizedMessage());
    }

    public Repository getRepository(){
        return repo;
    }
}
