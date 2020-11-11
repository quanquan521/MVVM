package com.yzq.mvvm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.yzq.core.CoreApp;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/9/16.
 * <p>
 * 描述：
 */
 public class App extends CoreApp {
    @Override
    public String setBaseUrl() {
        return "https://gank.io/api/";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String dexPath= Environment.getExternalStorageDirectory().getAbsolutePath().concat("/patch_dex.jar");
        File file=new File(dexPath);
        if (file.exists()){
            inject(dexPath);
        }
    }

    private void inject(String path) {
        try {
            Class<?>c1=Class.forName("dalvik.system.BaseDexClassLoader");
            Object pathList=getField(c1,"pathList",getClassLoader());
            Object baseElements=getField(pathList.getClass(),"dexElements",pathList);
            String dexopt=getDir("dexopt",0).getAbsolutePath();
            DexClassLoader dexClassLoader=new DexClassLoader(path,dexopt,dexopt,getClassLoader());
            Object obj=getField(c1,"pathList",dexClassLoader);
            Object dexElements=getField(obj.getClass(),"dexElements",obj);
            Object combineElements=combineArray(dexElements,baseElements);
            setField(pathList.getClass(), "dexElements",pathList, combineElements);
            Object object = getField(pathList.getClass(), "dexElements", pathList);
            int length = Array.getLength(object);
            Log.e("BugFixApplication", "length = " + length);







        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void setField(Class<?> cl, String fieldName, Object pathList, Object combineElements) throws NoSuchFieldException, IllegalAccessException {

        Field field = cl.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(pathList, combineElements);
    }

    private Object combineArray(Object dexElements, Object baseElements) {
        int firstLength= Array.getLength(dexElements);
        int secondLength=Array.getLength(baseElements);
        int length=firstLength+secondLength;
        Class<?> componentType =dexElements.getClass().getComponentType();
        Object newArr = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            if (i < firstLength) {
                Array.set(newArr, i, Array.get(dexElements, i));
            }else {
                Array.set(newArr, i, Array.get(baseElements, i - firstLength));
            }
        }
        return newArr;
    }

    private Object getField(Class<?> c1, String fielname,Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field=c1.getDeclaredField(fielname);
        field.setAccessible(true);
        return field.get(object);
    }
}
