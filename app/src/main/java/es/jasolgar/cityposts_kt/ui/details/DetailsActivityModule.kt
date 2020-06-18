package es.jasolgar.cityposts_kt.ui.details

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@InstallIn(ActivityComponent::class)
@Module
class DetailsActivityModule {


    @Provides
    fun provideCommentsAdapter(): CommentsAdapter {
        return CommentsAdapter()
    }
}
