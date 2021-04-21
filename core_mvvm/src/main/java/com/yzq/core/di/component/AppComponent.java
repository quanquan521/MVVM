package com.yzq.core.di.component;

import com.yzq.core.CoreApp;
import com.yzq.core.di.module.AppModule;


import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    CoreApp getContext();
}
