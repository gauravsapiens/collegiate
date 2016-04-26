package com.collegiate.core.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;

import com.collegiate.R;
import com.collegiate.core.ItemListLoader;
import com.collegiate.core.ItemizedRecyclableAdapter;
import com.collegiate.core.OnRecyclableItemClickListener;
import com.collegiate.core.RecyclableItem;

import java.util.List;


public abstract class BaseItemFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<RecyclableItem>>, ItemListLoader.ItemLoaderCallbacks<RecyclableItem>, OnRecyclableItemClickListener {

    public static final int ORIENTATION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int ORIENTATION_VERTICAL = LinearLayoutManager.VERTICAL;

    private static final String RetainDataKeyIsFetchingData = "isFetchingData";
    private static final int ITEM_LIST_LOADER_ID = 25;

    protected ViewGroup mRootView;

    private View mListContainer;
    private View mProgressContainer;
    private ViewStub mEmptyViewStub;
    private View mEmptyView;
    protected boolean mListShown;

    protected RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemizedRecyclableAdapter mAdapter;
    protected boolean mIsFetchingData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsFetchingData = savedInstanceState != null && savedInstanceState.getBoolean(RetainDataKeyIsFetchingData, false);

        mRootView = (ViewGroup) inflater.inflate(R.layout.recyclable_list, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);
        mListContainer = mRootView.findViewById(R.id.list_container);
        mProgressContainer = mRootView.findViewById(R.id.progress_container);

        //empty layout
        mEmptyViewStub = (ViewStub) mRootView.findViewById(R.id.empty_view_Stub);
        mEmptyViewStub.setLayoutResource(getEmptyLayoutId());
        mEmptyView = mEmptyViewStub.inflate();

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        int listOrientation = getListOrientation();
        mLayoutManager = new LinearLayoutManager(getActivity(), listOrientation, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setListShown(false);
        customizeView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = getRecyclableAdapter();

        initializeAdapter();
        initializeLoader();
    }

    private void initializeAdapter() {
        if (mAdapter == null) {
            return;
        }

        mAdapter.setOnItemClickListener(this);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (mAdapter.getItemCount() == 0) {
                    showEmptyView();
                } else {
                    hideEmptyView();
                }
            }
        });
        mRecyclerView.setAdapter(decorateAdapter(mAdapter));
    }

    private void initializeLoader() {
        getLoaderManager().initLoader(ITEM_LIST_LOADER_ID, null, this);
    }

    protected RecyclerView.Adapter decorateAdapter(ItemizedRecyclableAdapter adapter) {
        return adapter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RetainDataKeyIsFetchingData, mIsFetchingData);
    }

    @Override
    public Loader<List<RecyclableItem>> onCreateLoader(int i, Bundle bundle) {
        ItemListLoader loader = new ItemListLoader(getActivity());
        loader.setItemLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<RecyclableItem>> listLoader, List<RecyclableItem> recyclableItems) {
        if (!mIsFetchingData) {
            setListShown(true);
        }

        mAdapter.setRecyclableItems(recyclableItems);
        mRecyclerView.getAdapter().notifyDataSetChanged();

        onLoadFinished();
    }

    public void onRecyclableItemClicked(final RecyclableItem item, final int position) {
        onItemClicked(item, position);
    }

    @Override
    public void onLoaderReset(Loader<List<RecyclableItem>> listLoader) {

    }

    protected void onLoadFinished() {

    }

    public abstract void onItemClicked(RecyclableItem item, long position);

    protected void setListShown(boolean shown) {
        setListShown(shown, false);
    }

    private void setListShown(boolean shown, boolean animate) {
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                        android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mListContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.GONE);
        }
    }

    protected void customizeView() {
    }

    protected int getEmptyLayoutId() {
        return R.layout.list_empty;
    }

    protected void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    protected void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    public void refreshLoader() {
        refreshLoader(false);
    }

    public void refreshLoader(boolean forceRefresh) {
        if (forceRefresh) {
            getLoaderManager().restartLoader(ITEM_LIST_LOADER_ID, null, this);
        }

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected int getListOrientation() {
        return ORIENTATION_VERTICAL;
    }

    protected ItemizedRecyclableAdapter getRecyclableAdapter() {
        return new ItemizedRecyclableAdapter(getActivity());
    }

    @Override
    public List<RecyclableItem> deliverListItems(List<RecyclableItem> loadedTableItems) {
        return loadedTableItems;
    }

    public ItemizedRecyclableAdapter getAdapter() {
        return mAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
