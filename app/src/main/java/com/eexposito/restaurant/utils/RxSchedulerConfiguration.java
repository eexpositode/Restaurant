package com.eexposito.restaurant.utils;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulerConfiguration {

    public Scheduler getMainThread() {

        return AndroidSchedulers.mainThread();
    }

    public Scheduler getComputationThread() {

        return Schedulers.computation();
        //        return getThreadPoolExecutor(coreNumber());
    }

    // TODO: 8/11/17 use this for retrofit
    public Scheduler getIOThread() {

        return Schedulers.io();
    }

    //    private Scheduler getThreadPoolExecutor(int coreNumber) {
    //
    //        return Schedulers.from(Executors.newFixedThreadPool(coreNumber + 1));
    //    }
    //
    //    private int coreNumber() {
    //
    //        return Runtime.getRuntime().availableProcessors();
    //    }
}
