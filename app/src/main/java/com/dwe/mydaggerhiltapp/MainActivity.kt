package com.dwe.mydaggerhiltapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
    }
}

@AndroidEntryPoint
class MyFragment : Fragment() {
}

class SomeClass @Inject constructor(
    private val someInterfaceImpl: SomeInterface,
    private val Gson: Gson
) {

    fun doAThing(): String {
        return "Look i got: ${someInterfaceImpl.getAThing()}"
    }
}

class SomeInterfaceImpl @Inject constructor(
    private val someDependency: String
) : SomeInterface {
    override fun getAThing(): String = "A thing, $someDependency"
}

interface SomeInterface {
    fun getAThing(): String
}

// Use Binds to tell Hilt how to create an instance of an interface
//@InstallIn(ActivityComponent::class)
//@Module
//abstract class MyModule {
//    @ActivityScoped
//    @Binds
//    abstract fun bindSomeDependency(someImpl: SomeInterfaceImpl): SomeInterface
//
//    @ActivityScoped
//    @Binds
//    abstract fun bindGson(gson: Gson): Gson
//}

// Use Provides to tell Hilt how to create an instance of an interface
@InstallIn(SingletonComponent::class)
@Module
class MyModule {
    @Singleton
    @Provides
    fun provideSomeString(): String {
        return "Some String"
    }

    @Singleton
    @Provides
    fun provideSomeInterface(
        someString: String
    ): SomeInterface = SomeInterfaceImpl(someString)

    @Singleton
    @Provides
    fun providesSomeGson(): Gson = Gson()
}


