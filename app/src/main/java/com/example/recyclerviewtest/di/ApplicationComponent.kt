package com.example.recyclerviewtest.di

import com.example.recyclerviewtest.MainActivity
import com.example.recyclerviewtest.presentation.view.TestFragment
import com.example.recyclerviewtest.presentation.view.TimelineFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ViewModelBindings::class
    ]
)
interface ApplicationComponent {
    fun inject(target: DependencyApp)
    fun inject(target: MainActivity)
    fun inject(target: TestFragment)
    fun inject(target: TimelineFragment)
}
