package es.jasolgar.cityposts_kt.ui.details

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides


@Module
class DetailsActivityModule {
    @Provides
    fun provideLinearLayoutManager(detailsActivity: DetailsActivity?): LinearLayoutManager {
        return LinearLayoutManager(detailsActivity)
    }

    @Provides
    fun provideCommentsAdapter(): CommentsAdapter {
        return CommentsAdapter()
    }
}
