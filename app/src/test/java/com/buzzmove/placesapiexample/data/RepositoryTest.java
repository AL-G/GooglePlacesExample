package com.buzzmove.placesapiexample.data;

import com.buzzmove.placesapiexample.data.entities.Result;
import com.buzzmove.placesapiexample.data.entities.SearchResult;
import com.buzzmove.placesapiexample.data.remote.RemoteSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.buzzmove.placesapiexample.Utils.readJsonFile;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class RepositoryTest {

    private Repository mRepository;

    @Mock
    private RemoteSource mRemoteSource;

    SearchResult results;

    @Before
    public void setupTasksPresenter() {

        MockitoAnnotations.initMocks(this);

        mRepository = new Repository(mRemoteSource);

        // load test data
        Gson gson = new GsonBuilder().create();
        try {
            results = gson.fromJson(readJsonFile(getClass(), "text_search_result.json"),
                    SearchResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchPlaces() throws Exception {

        assertNotNull(mRepository);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                DataSourceInterface.GetPlacesCallback callback = (DataSourceInterface.GetPlacesCallback) invocation.getArguments()[0];
                callback.onLoaded(results.getResults());
                return null;

            }
        }).when(mRemoteSource).searchPlaces((DataSourceInterface.GetPlacesCallback) anyObject(), anyString());

        // pass mock callback to search method
        MockCallback callback = new MockCallback();
        mRepository.searchPlaces(callback, "Bars in London");

        // verify data source was called
        verify(mRemoteSource).searchPlaces(callback, "Bars in London");

        assertTrue(callback.results.size() > 0);


    }

    /**
     * Mock callback implementing DataSourceInterface.GetPlacesCallback, to pass to data sources in
     * repository
     */
    static class MockCallback implements DataSourceInterface.GetPlacesCallback {

        public List<Result> results = new ArrayList();

        @Override
        public void onLoaded(List<Result> results) {
            this.results = results;
            synchronized (this) {
                notifyAll();
            }
        }

        @Override
        public void onDataNotAvailable() {
            synchronized (this) {
                notifyAll();
            }
        }
    }

}
