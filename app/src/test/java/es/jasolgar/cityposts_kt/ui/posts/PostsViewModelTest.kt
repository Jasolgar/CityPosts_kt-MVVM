package es.jasolgar.cityposts_kt.ui.posts

import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.data.model.others.PostInfo
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


@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @Mock
    var mPostsNavigator: PostsNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null

    private var mTestScheduler: TestScheduler? = null

    private var mPostsViewModel: PostsViewModel? = null

    companion object {
        @BeforeClass  @Throws(Exception::class) fun onlyOnce() {  }
    }


    @Before
    @Throws(Exception::class)
    fun setUp() {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mPostsViewModel = PostsViewModel(mMockDataManager!!, testSchedulerProvider)
        mPostsViewModel!!.setNavigator(mPostsNavigator!!)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mTestScheduler = null
        mPostsViewModel = null
        mPostsNavigator = null
    }

    @Test
    fun testFetchRepo() {
        val postInfoList: List<PostInfo> = ArrayList()
        Mockito.doReturn(Observable.just(postInfoList)).`when`(mMockDataManager)
            ?.retrieveAllPostsInfo()
        mPostsViewModel!!.fetchData()
        mTestScheduler!!.triggerActions()
    }


}