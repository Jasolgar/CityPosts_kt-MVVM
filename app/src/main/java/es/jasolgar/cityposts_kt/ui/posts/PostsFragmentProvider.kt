package es.jasolgar.cityposts_kt.ui.posts

import dagger.Module
import dagger.android.ContributesAndroidInjector




@Module
abstract class PostsFragmentProvider {
    @ContributesAndroidInjector(modules = [PostsFragmentModule::class])
    abstract fun providePostsFragmentFactory(): PostsFragment?
}