package com.yzq.core.di.module;
import com.yzq.core.CoreApp;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final CoreApp application;

    public AppModule(CoreApp application) {
        this.application = application;
    }

    @Provides
    @Singleton
    CoreApp provideApplicationContext() {
        return application;
    }


}
