package com.yzq.core.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by codeest on 16/8/7.
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
    /*
    * 当时用dependencies 时   component 的scope不能相同  所以要自定义
    * sington 是依赖component  不同activity builder().的component 的不同   需要使用全局的才是同一个
    *
    * Singleton的组件不能依赖其他scope的组件，只能其他scope的组件依赖Singleton的组件
    *
    * componet 的 inject 方法接收父类型参数，而调用时传入的是子类型对象则无法注入
     component关联的modules中不能有重复的provide
     module 的 provide 方法使用了 scope ，那么 component 就必须使用同一个注解
      module 的 provide 方法没有使用 scope ，那么 component 和 module 是否加注解都无关紧要，可以通过编译
    component的dependencies与component自身的scope不能相同，即组件之间的scope不同
    Singleton的组件不能依赖其他scope的组件，只能其他scope的组件依赖Singleton的组件
    没有scope的component不能依赖有scope的component
    一个component不能同时有多个scope(Subcomponent除外)
    @Singleton 的生命周期依附于component，同一个module  provide singleton ,不同component 也是不一样
    Component注入的Activity 在其他Component中不能再去注入
    dagger2是跟着生命周期的绑定Activity（Fragment）onDestory 对象也会销毁
     创建实例的方法和引用实例都不能用private修饰
    *
    *
    * */
}
