package es.jasolgar.cityposts_kt.ui.splash

import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.utils.rx.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith( MockitoJUnitRunner::class)
class SplashViewModelTest {

    @Mock
    var mSplashNavigator: SplashNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null

    private var mTestScheduler: TestScheduler? = null

    private var mSplashViewModel: SplashViewModel? = null

    companion object {
        @BeforeClass @Throws(Exception::class) fun onlyOnce() { }
    }


    @Before
    @Throws(Exception::class)
    fun setUp() {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mSplashViewModel = SplashViewModel(mMockDataManager!!, testSchedulerProvider)
        mSplashViewModel?.setNavigator(mSplashNavigator!!)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mTestScheduler = null
        mSplashViewModel = null
        mSplashNavigator = null
    }

    @Test
    fun testOnFetchDataStarted() {
        Mockito.doReturn(Observable.just(true)).`when`(mMockDataManager)?.fetchPostDataByUsers()

        mSplashViewModel!!.onFetchDataStarted()
        mTestScheduler!!.triggerActions()
        Mockito.verify(mSplashNavigator)?.openMainActivity()
    }
}