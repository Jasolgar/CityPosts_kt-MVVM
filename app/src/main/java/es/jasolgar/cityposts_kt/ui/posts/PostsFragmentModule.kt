package es.jasolgar.cityposts_kt.ui.posts

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import es.jasolgar.cityposts_kt.ui.base.BaseActivity
import es.jasolgar.cityposts_kt.ui.main.MainActivity


@Module
class PostsFragmentModule {

    @Provides
    fun provideBaseActivity(fragment: PostsFragment): BaseActivity<*, *> {
        return fragment.activity as BaseActivity<*, *>
    }

    @Provides
    fun provideLinearLayoutManager(fragment: PostsFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }

    @Provides
    fun provideOpenSourceAdapter(baseActivity: MainActivity): PostsAdapter {
        return PostsAdapter(baseActivity)
    }
}