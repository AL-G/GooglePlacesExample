package com.buzzmove.placesapiexample.search_screen;


import com.buzzmove.placesapiexample.Utils;
import com.buzzmove.placesapiexample.data.DataSourceInterface;
import com.buzzmove.placesapiexample.data.Repository;
import com.buzzmove.placesapiexample.data.entities.Result;
import com.buzzmove.placesapiexample.data.entities.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class SearchPresenterTest {

    @Mock
    private SearchContract.View mSearchView;

    @Mock
    private Repository mRepository;

    private SearchPresenter mSearchPresenter;

    private SearchResult results;

    @Before
    public void setupTasksPresenter() {

        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mSearchPresenter = new SearchPresenter(mSearchView, mRepository);

        // load test data
        Gson gson = new GsonBuilder().create();
        try {
            results = gson.fromJson(Utils.readJsonFile(getClass(), "text_search_result.json"),
                    SearchResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createPresenter_setsThePresenterToView() {

        // attach presenter to mock view
        mSearchView.setPresenter(mSearchPresenter);

        verify(mSearchView).setPresenter(mSearchPresenter);

    }


    @Test
    public void loadResultsFromRepositoryAndLoadIntoView() {

        // make callback method return test data
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                DataSourceInterface.GetPlacesCallback callback = (DataSourceInterface.GetPlacesCallback) invocation.getArguments()[0];
                callback.onLoaded(results.getResults());
                return null;

            }
        }).when(mRepository).searchPlaces((DataSourceInterface.GetPlacesCallback) anyObject(), anyString());

        // perform search
        mSearchPresenter.search("gym");

        // verify update method in view called
        verify(mSearchView).updateSearchResult((List<Result>) results.getResults());

    }

}