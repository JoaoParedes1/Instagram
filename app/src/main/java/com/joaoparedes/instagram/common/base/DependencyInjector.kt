package com.joaoparedes.instagram.common.base

import android.content.Context
import com.joaoparedes.instagram.add.data.AddFakeRemoteDataSource
import com.joaoparedes.instagram.add.data.AddLocalDataSource
import com.joaoparedes.instagram.add.data.AddRepository
import com.joaoparedes.instagram.add.data.FireAddDataSource
import com.joaoparedes.instagram.home.data.FeedMemoryCache
import com.joaoparedes.instagram.home.data.HomeDataSourceFactory
import com.joaoparedes.instagram.home.data.HomeRepository
import com.joaoparedes.instagram.login.data.FireLoginDataSource
import com.joaoparedes.instagram.login.data.LoginRepository
import com.joaoparedes.instagram.post.data.PostLocalDataSource
import com.joaoparedes.instagram.post.data.PostRepository
import com.joaoparedes.instagram.profile.data.PostListMemoryCache
import com.joaoparedes.instagram.profile.data.ProfileDataSourceFactory
import com.joaoparedes.instagram.profile.data.ProfileMemoryCache
import com.joaoparedes.instagram.profile.data.ProfileRepository
import com.joaoparedes.instagram.register.data.FireRegisterDataSource
import com.joaoparedes.instagram.register.data.RegisterRepository
import com.joaoparedes.instagram.search.data.FireSearchDataSource
import com.joaoparedes.instagram.search.data.SearchRepository
import com.joaoparedes.instagram.splash.data.FireSplashDataSource
import com.joaoparedes.instagram.splash.data.SplashRepository

object DependencyInjector {

    fun splashRepository() : SplashRepository {
        return SplashRepository(FireSplashDataSource())
    }

    fun loginRepository() : LoginRepository {
        return LoginRepository(FireLoginDataSource())
    }

    fun registerEmailRepository() : RegisterRepository {
        return RegisterRepository(FireRegisterDataSource())
    }

    fun profileRepository() : ProfileRepository {
        return ProfileRepository(ProfileDataSourceFactory(ProfileMemoryCache, PostListMemoryCache))
    }

    fun homeRepository() : HomeRepository {
        return HomeRepository(HomeDataSourceFactory(FeedMemoryCache))
    }

    fun AddRepository() : AddRepository {
        return AddRepository(FireAddDataSource(), AddLocalDataSource())
    }

    fun PostRepository(context: Context) : PostRepository {
        return PostRepository(PostLocalDataSource(context))
    }

    fun SearchRepository() : SearchRepository {
        return SearchRepository(FireSearchDataSource())
    }

}