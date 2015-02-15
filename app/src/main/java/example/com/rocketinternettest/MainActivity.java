package example.com.rocketinternettest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import example.com.rocketinternettest.network.StreamApi;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by Kavya Shree, 13-02-2015.
 */
@EActivity(R.layout.stream_list)
public class MainActivity extends ActionBarActivity implements SortDialog.SortDialogClosed, FilterDialog.FilterDialogClosed, OnRefreshListener {
    private final static String TAG = "MainActivity";
    StreamApi streamApi;
    StreamAdapter streamAdapter;

    @ViewById(R.id.streamListView)
    ListView list;
    @ViewById(R.id.refresh_list)
    PullToRefreshLayout pullToRefreshLayout;
    @ViewById(R.id.streamListRL)
    RelativeLayout streamListRL;
    @ViewById(R.id.sortLayout)
    LinearLayout sortLayout;

    @AfterViews
    void setupPullToRefresh() {
        ActionBarPullToRefresh.from(this)
                .theseChildrenArePullable(list)
                .listener(this)
                .setup(pullToRefreshLayout);
        pullToRefreshLayout.setRefreshing(true);
    }

    @Click(R.id.sort)
    void sortList() {
        SortDialog sortDialog = new SortDialog();
        sortDialog.setListener(this);
        sortDialog.showDialog(this);
    }

    @Click(R.id.filter)
    void filterList() {
        LayoutInflater layoutInflater = getLayoutInflater();
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.setListener(this);
        filterDialog.showDialog(this, layoutInflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        streamApi = new StreamApi(this);
        streamAdapter = new StreamAdapter(this, new ArrayList<Stream>());
        downloadStream(false);
    }

    @Background
    void downloadStream(boolean isForcedDownload) {
        try {
            List<Stream> stream = streamApi.getStream(isForcedDownload);
            updateListView(stream);
            stopRefreshAnimation();
        } catch (IOException | SQLException e) {
            Log.e(TAG, "Exception: ", e);
        }
    }

    @UiThread
    void updateListView(List<Stream> stream) {
        streamAdapter.setElements(stream);
        list.setAdapter(streamAdapter);
        streamAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshStarted(View view) {
        downloadStream(true);
    }

    @UiThread
    void stopRefreshAnimation() {
        pullToRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFilterDialogClosed() {
        streamAdapter.filter();
    }

    @Override
    public void onSortDialogClosed() {
        streamAdapter.sort();
    }
}