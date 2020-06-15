package es.jasolgar.cityposts_kt.ui.details

import es.jasolgar.cityposts_kt.data.DataManager
import es.jasolgar.cityposts_kt.data.model.db.Comment
import es.jasolgar.cityposts_kt.data.model.db.Post
import es.jasolgar.cityposts_kt.data.model.db.User
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
class DetailsViewModelTest {

    @Mock
    var mDetailsNavigator: DetailsNavigator? = null

    @Mock
    var mMockDataManager: DataManager? = null

    private var mTestScheduler: TestScheduler? = null

    private var mDetailsViewModel: DetailsViewModel? = null

    companion object{
        @BeforeClass @Throws(Exception::class) fun onlyOnce() { }
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mTestScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler!!)
        mDetailsViewModel = DetailsViewModel(mMockDataManager!!, testSchedulerProvider)
        mDetailsViewModel?.setNavigator(mDetailsNavigator!!)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mTestScheduler = null
        mDetailsViewModel = null
        mDetailsNavigator = null
    }

    @Test
    fun testFetchRepo() {
        val commentList: List<Comment> = ArrayList()
        Mockito.doReturn(Observable.just(User())).`when`(mMockDataManager)?.retrieveUserById(1)
        Mockito.doReturn(Observable.just(Post())).`when`(mMockDataManager)?.retrievePostsById(1)
        Mockito.doReturn(Observable.just(commentList)).`when`(mMockDataManager)?.requestCommentsByPostId(1)
        mDetailsViewModel!!.notifyBundlePostId(1)
        mTestScheduler!!.triggerActions()
    }

}