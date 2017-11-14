package com.eexposito.restaurant.presenter;

import com.eexposito.restaurant._support.BaseTest;

public class CustomerPresenterImplTest extends BaseTest {
    //
    //    @Mock
    //    private CustomerDataSource mDataSource;
    //
    //    @Mock
    //    CustomerListContract.View mView;
    //
    //    @InjectMocks
    //    private CustomerPresenterImpl mPresenter;
    //
    //    @Before
    //    public void setUp() {
    //
    //        MockitoAnnotations.initMocks(this);
    //
    //        // To make it sync
    //        when(RxSchedulerConfiguration.getComputationThread())
    //                .then(invocation -> AndroidSchedulers.mainThread());
    //        when(RxSchedulerConfiguration.getMainThread())
    //                .then(invocation -> AndroidSchedulers.mainThread());
    //    }
    //
    //    @After
    //    public void tearDown() {
    //
    //        mPresenter = null;
    //    }
    //
    //    @Test
    //    public void testBind_RealmSuccess() throws Exception {
    //
    //        // Mocking returned values from the RealmService
    //        Observable<RealmResults<RealmObject>> observable = Observable.just(mockRealmResults(4));
    //        when(mDataSource.getCustomersFromRealm(any()))
    //                .thenReturn(observable);
    //
    //        // Mocking data source to return an empty list
    //        List<Customer> results = fixtureCustomerList();
    //        when(mDataSource.getCustomers(any())).thenReturn(Observable.just(results));
    //
    //        mPresenter.bind(mView);
    //
    //        assertThat(mPresenter.mViewWeakReference, notNullValue());
    //        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));
    //
    //        InOrder inOrder = inOrder(mView);
    //        inOrder.verify(mView, times(1)).onFetchDataStarted();
    //        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
    //        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    //    }
    //
    //    @Test
    //    public void testBind_RealmError() throws Exception {
    //
    //        // Mocking returned values from the RealmService
    //        RuntimeException realmError = new RuntimeException();
    //        when(mService.getAllModelsAsync(any()))
    //                .thenReturn(Observable.error(realmError));
    //
    //        // Mocking data source to return an empty list
    //        List<Customer> results = fixtureCustomerList();
    //        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.just(results));
    //
    //        mPresenter.bind(mView);
    //
    //        assertThat(mPresenter.mViewWeakReference, notNullValue());
    //        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));
    //
    //        InOrder inOrder = inOrder(mView);
    //        inOrder.verify(mView, times(1)).onFetchDataStarted();
    //        inOrder.verify(mView, times(1)).onFetchDataError(realmError);
    //        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
    //        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    //    }
    //
    //    @Test
    //    public void testBind_DataError() throws Exception {
    //
    //        // Mocking returned values from the RealmService
    //        RuntimeException realmError = new RuntimeException();
    //        when(mService.getAllModelsAsync(any()))
    //                .thenReturn(Observable.error(realmError));
    //
    //        // Mocking data source to return an empty list
    //        List<Customer> results = fixtureCustomerList();
    //        RuntimeException dataError = new RuntimeException();
    //        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.error(dataError));
    //
    //        mPresenter.bind(mView);
    //
    //        assertThat(mPresenter.mViewWeakReference, notNullValue());
    //        assertThat(mPresenter.mViewWeakReference.get(), equalTo(mView));
    //
    //        InOrder inOrder = inOrder(mView);
    //        inOrder.verify(mView, times(1)).onFetchDataStarted();
    //        inOrder.verify(mView, times(1)).onFetchDataError(realmError);
    //        inOrder.verify(mView, times(1)).onFetchDataError(dataError);
    //        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    //    }
    //
    //    /////////////////////////////////////////////////////////////////////////////////////
    //    // loadData()
    //    /////////////////////////////////////////////////////////////////////////////////////
    //    @Test
    //    public void loadDataSuccess() throws Exception {
    //
    //        mPresenter.mViewWeakReference = new WeakReference<>(mView);
    //
    //        // Mocking data source to return a list of results
    //        List<Customer> results = fixtureCustomerList();
    //        when(mDataSource.getCustomers(anyBoolean())).thenReturn(Observable.just(results));
    //
    //        mPresenter.loadData();
    //
    //        InOrder inOrder = inOrder(mView);
    //        inOrder.verify(mView, times(1)).onFetchDataSuccess(results);
    //        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    //    }
    //
    //    @Test
    //    public void loadDataError() throws Exception {
    //
    //        mPresenter.mViewWeakReference = new WeakReference<>(mView);
    //
    //        // Mocking data source to return an exception
    //        when(mDataSource.getCustomers(anyBoolean())).
    //                thenReturn(Observable.error(new RuntimeException()));
    //
    //        mPresenter.loadData();
    //
    //        InOrder inOrder = inOrder(mView);
    //        inOrder.verify(mView, times(1)).onFetchDataError(any());
    //        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    //    }
}