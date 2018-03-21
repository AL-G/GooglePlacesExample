package com.buzzmove.placesapiexample.search_screen;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buzzmove.placesapiexample.R;
import com.buzzmove.placesapiexample.data.remote.RemoteSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.utils.RequestMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SearchScreenInstrumentedTest {

    @Rule
    public ActivityTestRule<SearchActivity> rule = new ActivityTestRule<>(
            SearchActivity.class,
            true,
            false);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.buzzmove.placesapiexample", appContext.getPackageName());
    }

    @Before
    public void setUp() throws Exception {
        RESTMockServer.reset(); // set up the mock api server
        RemoteSource.BASE_URL = RESTMockServer.getUrl();

    }

    /**
     * Test the search activity displays the result of a search correctly
     *
     * @throws InterruptedException sleep interrupted exception
     */
    @Test
    public void testSearchWithResults() throws Exception {

        // mock the get closest users api call
        RESTMockServer.whenGET(RequestMatchers.pathContains("textSearch/")).thenReturnFile(200,
                "search_results/text_search_result.json");

        Intent intent = new Intent();
        rule.launchActivity(intent);

        rule.getActivity().mSearchFragment.getPresenter().search("successful");

        Thread.sleep(5000); // wait for UI to load

        // check the recyclerview is loading the results into the view
        assertTrue("Adapter failed to load results", ((RecyclerView) rule.getActivity().findViewById(R.id.places_recyclerview)).getChildCount() > 0);

        // test if the no search results message is hidden
        assertTrue("No results message not hidden", (rule.getActivity().findViewById(R.id.no_results_view)).getVisibility() == View.GONE);

    }

    /**
     * Test the search activity displays the empty result of a search correctly
     *
     * @throws InterruptedException sleep interrupted exception
     */
    @Test
    public void testSearchWithoutResults() throws Exception {

        RESTMockServer.reset();

        // mock the get closest users api call
        RESTMockServer.whenGET(RequestMatchers.pathContains("textSearch/")).thenReturnFile(200,
                "search_results/text_search_empty_result.json");

        Intent intent = new Intent();
        rule.launchActivity(intent);

        rule.getActivity().mSearchFragment.getPresenter().search("unsuccessful");

        Thread.sleep(5000); // wait for UI to load

        // test if the no search results message is hidden
        assertTrue("No results message hidden", (rule.getActivity().findViewById(R.id.no_results_view)).getVisibility() == View.VISIBLE);

    }

}
