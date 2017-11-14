package com.eexposito.restaurant.datasources;

import com.eexposito.restaurant._support.BaseTest;

public class CustomerDataSourceTest extends BaseTest {

    //    @Mock
    //    private
    //    ModelManager mModelManager;
    //
    //    @Mock
    //    private ReservationsServiceApi mApi;
    //
    //    @InjectMocks
    //    CustomerDataSource mDataSource;
    //
    //    private List<Customer> mTestCustomers;
    //
    //    @Before
    //    public void setUp() {
    //
    //        MockitoAnnotations.initMocks(this);
    //
    //        mTestCustomers = fixtureCustomerList();
    //        //Mock retrofit
    //        when(mApi.getCustomers()).thenReturn(
    //                Observable.just(fixtureCustomerList())
    //        );
    //    }
    //
    //    @After
    //    public void tearDown() {
    //
    //        Mockito.validateMockitoUsage();
    //        mDataSource = null;
    //    }
    //
    //    //////////////////////////////////////////////////////////////
    //    // getCustomers()
    //    //////////////////////////////////////////////////////////////
    //    @Test
    //    public void testGetCustomers_DefaultResult() throws Exception {
    //
    //        //Mock to force call to retrofit api return nothing
    //        when(mApi.getCustomers()).thenReturn(
    //                Observable.empty());
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomers(true);
    //        List<Customer> customers = Collections.singletonList(fixtureCustomer(1, "John", "Doe"));
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(customers));
    //    }
    //
    //    @Test
    //    public void testGetCustomers_RetrofitResult() throws Exception {
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomers(true);
    //        List<Customer> customers = mTestCustomers;
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(customers));
    //    }
    //
    //    @Test
    //    public void testGetCustomers_RealmResult() throws Exception {
    //
    //        //Although the retrofit api returns values, we get realm results
    //        //since realm query has preference.
    //        RealmResults<Customer> results = mockRealmResults(2);
    //        Mockito.doAnswer(invocation -> results)
    //                .when(mModelManager).getModels(any());
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomers(false);
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(results));
    //    }
    //
    //    //////////////////////////////////////////////////////////////
    //    // getDefaultResponse()
    //    //////////////////////////////////////////////////////////////
    //    @Test
    //    public void testGetDefaultResponse_Success() throws Exception {
    //
    //        Observable<List<Customer>> observable = mDataSource.getDefaultResponse();
    //        List<Customer> customers = Collections.singletonList(fixtureCustomer(1, "John", "Doe"));
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(customers));
    //    }
    //
    //    //////////////////////////////////////////////////////////////
    //    // getCustomerListFromRetrofit()
    //    //////////////////////////////////////////////////////////////
    //    @Ignore("To do")
    //    @Test
    //    public void testGetCustomerListFromRetrofit_NoInternet() throws Exception {
    //
    //    }
    //
    //    @Test
    //    public void testGetCustomerListFromRetrofit_RealmThrowsException() throws Exception {
    //
    //        Mockito.doThrow(new RuntimeException())
    //                .when(mModelManager).saveOrUpdateModelList(anyList());
    //        Observable<List<Customer>> observable = mDataSource.getCustomerListFromRetrofit();
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertError(RuntimeException.class);
    //        subscriber.assertNotComplete();
    //    }
    //
    //    @Test
    //    public void testGetCustomerListFromRetrofit_Success() throws Exception {
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomerListFromRetrofit();
    //
    //        List<Customer> customers = mTestCustomers;
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(customers));
    //    }
    //
    //    //////////////////////////////////////////////////////////////
    //    // getCustomerListFromRealm()
    //    //////////////////////////////////////////////////////////////
    //    @Test
    //    public void testGetCustomersFromRealm_IgnoreRealm() throws Exception {
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomersFromRealm(true);
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(0);
    //    }
    //
    //    @Test
    //    public void testGetCustomersFromRealm_Exception() throws Exception {
    //
    //        Mockito.doThrow(new RuntimeException())
    //                .when(mModelManager).getModels(any());
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomersFromRealm(false);
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertError(RuntimeException.class);
    //        subscriber.assertNotComplete();
    //    }
    //
    //    @Test
    //    public void testGetCustomersFromRealm_Success() throws Exception {
    //
    //        RealmResults<Customer> results = mockRealmResults(2);
    //        Mockito.doAnswer(invocation -> results)
    //                .when(mModelManager).getModels(any());
    //
    //        Observable<List<Customer>> observable = mDataSource.getCustomersFromRealm(false);
    //
    //        TestObserver<List<Customer>> subscriber = new TestObserver<>();
    //        observable.subscribe(subscriber);
    //
    //        subscriber.assertComplete();
    //        subscriber.assertNoErrors();
    //        subscriber.assertValueCount(1);
    //        assertThat(subscriber.values(), hasItem(results));
    //    }
}