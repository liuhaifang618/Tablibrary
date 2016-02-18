package com.safewaychina.tabmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liu_haifang
 * @version 1.0
 * @Title:SAFEYE@
 * @Description:
 * @date 2015-08-12
 */
public class FragmentUtils {

    /**
     * fragment切换
     * @param from
     * @param to
     * @param fragmentManager
     * @param layoutId
     */
    protected  static void switchFragment(Fragment from, Fragment to,FragmentManager fragmentManager, int layoutId) {
        if (fragmentManager == null || layoutId <= 0 || from == to) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (to != null) {
            if (from != null) {
                transaction.hide(from);
            }
            if (!to.isAdded()) {
                transaction.add(layoutId, to, to.getClass().getSimpleName());
            } else {
                transaction.show(to);
            }
        }
        transaction.commit();
    }

    /**
     * 创建fragment 解决fragment重爹问题
     * @param savedInstanceState
     * @param fragmentManager
     * @param clazz
     * @param args
     * @return
     */
    protected static Fragment newFragment(Bundle savedInstanceState, FragmentManager fragmentManager, Class clazz, Bundle args) {
        if (savedInstanceState != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(clazz.getSimpleName());
            if (fragment == null) {
                return reflectNewFragment(clazz, args);
            }
        }
        return reflectNewFragment(clazz, args);

    }

    protected static Fragment reflectNewFragment(Class clazz, Bundle args){
        try {
            Method m = clazz.getDeclaredMethod("newInstance",new Class[]{Bundle.class});
            Object object = m.invoke(clazz.newInstance(), args);
            if (object == null){
                throw  new RuntimeException("fragment has newInstance(bunld) method");
            }
            return  (Fragment)object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
