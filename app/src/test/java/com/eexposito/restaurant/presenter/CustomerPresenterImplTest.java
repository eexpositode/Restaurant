package com.eexposito.restaurant.presenter;

import com.eexposito.restaurant._support.BaseTest;
import com.eexposito.restaurant._support.fixtures.TestCustomerView;
import com.eexposito.restaurant.datasources.CustomerDataSource;
import com.eexposito.restaurant.realm.RealmService;
import com.eexposito.restaurant.realm.models.Customer;
import com.eexposito.restaurant.utils.RxSchedulerConfiguration;

import java.lang.ref.WeakReference;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static com.eexposito.restaurant._support.fixtures.FixtureModels.fixtureCustomerList;
import static com.eexposito.restaurant._support.fixtures.FixtureModels.mockRealmResults;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CustomerPresenterImplTest extends BaseTest {

    @Mock
    RealmService mService;

    @Mock
    RxSchedulerConfiguration mSchedulersConfiguration;

    @Mock
    private CustomerDataSource mDataSource;

    @Spy
    private TestCustomerView mView;

    @InjectMocks
    private CustomerPresenterImpl mPresenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        // To make it sync
        when(mSchedulersConfiguration.getComputationThread())
                .then(invocation -> AndroidSchedulers.mainThread());
        when(mSchedulersConfiguration.getMainThread())
                .then(invocation -> AndroidSchedulers.mainThread());
    }

    @After
    public void tearDown() {

        mPresenter = null;
    }

    @Test
    public void testBind_RealmSuccess() throws Exception {

        // Mocking returned values from the RealmService
        Observable<RealmResults<RealmObject>> observable = Observable.just(mockRealmResults(4));
        when(mService.getAllModelsAsync(any()))
                .thenReturn(observable);

        // Mocking data source to return an empty list
        List<Customer> results = fixtureCustomerList();
        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.just(results));

        mPresenter.bind(mView);

        assertThat(mPresenter.mViewWeakReference, notNullValue());
        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataStarted();
        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }

    @Test
    public void testBind_RealmError() throws Exception {

        // Mocking returned values from the RealmService
        RuntimeException realmError = new RuntimeException();
        when(mService.getAllModelsAsync(any()))
                .thenReturn(Observable.error(realmError));

        // Mocking data source to return an empty list
        List<Customer> results = fixtureCustomerList();
        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.just(results));

        mPresenter.bind(mView);

        assertThat(mPresenter.mViewWeakReference, notNullValue());
        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataStarted();
        inOrder.verify(mView, times(1)).onFetchDataError(realmError);
        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }

    @Test
    public void testBind_DataError() throws Exception {

        // Mocking returned values from the RealmService
        RuntimeException realmError = new RuntimeException();
        when(mService.getAllModelsAsync(any()))
                .thenReturn(Observable.error(realmError));

        // Mocking data source to return an empty list
        List<Customer> results = fixtureCustomerList();
        RuntimeException dataError = new RuntimeException();
        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.error(dataError));

        mPresenter.bind(mView);

        assertThat(mPresenter.mViewWeakReference, notNullValue());
        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataStarted();
        inOrder.verify(mView, times(1)).onFetchDataError(realmError);
        inOrder.verify(mView, times(1)).onFetchDataError(dataError);
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // loadData()
    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void loadDataSuccess() throws Exception {

        mPresenter.mViewWeakReference = new WeakReference<>(mView);

        // Mocking data source to return a list of results
        List<Customer> results = fixtureCustomerList();
        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.just(results));

        mPresenter.loadData();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }

    @Test
    public void loadDataError() throws Exception {

        mPresenter.mViewWeakReference = new WeakReference<>(mView);

        // Mocking data source to return an exception
        when(mDataSource.getCustomers(anyBoolean())).
                thenReturn(Observable.error(new RuntimeException()));

        mPresenter.loadData();

        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataError(any());
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }
}