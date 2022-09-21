package com.dwe.mydaggerhiltapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // field injection
    @Inject
    lateinit var someClass: SomeClass

    @Inject
    lateinit var singletonClass: SingletonClass

    @Inject
    lateinit var someDependencyClass: SomeDependencyClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
        println(someClass.doSomeOtherSomething())
        println(singletonClass.doASingletonThing())
        println(someDependencyClass.doAThing())
    }
}

@AndroidEntryPoint
class MyFragment : Fragment() {
    @Inject
    lateinit var singletonClass: SingletonClass
}

class SomeClass @Inject constructor(
    // constructor injection
    private val someOtherClass: SomeOtherClass
) {
    fun doAThing(): String = "Look I did a thing!"

    fun doSomeOtherSomething(): String = someOtherClass.doSomeOtherThing()
}

class SomeOtherClass @Inject constructor() {
    fun doSomeOtherThing(): String = "Look I did some other thing!"
}

@Singleton
class SingletonClass @Inject constructor() {
    fun doASingletonThing(): String = "Look I did a singleton thing!"
}

class SomeDependencyClass @Inject constructor(
//    private val someInterface: SomeInterface,
    // error: SomeInterface cannot be provided without an @Inject constructor or an @Provides-annotated method.
    private val Gson: Gson
) {

    fun doAThing(): String {
        // error: SomeInterface cannot be provided without an @Inject constructor or an @Provides-annotated method.
//        return "Look i got: ${someInterface.getAThing()}"
        return "Something"
    }
}

class SomeInterfaceImpl @Inject constructor(): SomeInterface {
    override fun getAThing(): String = "A thing"
}

interface SomeInterface {
    fun getAThing(): String
}

