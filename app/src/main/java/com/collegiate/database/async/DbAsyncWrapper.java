package com.collegiate.database.async;

import android.os.Handler;
import android.os.Looper;

import com.collegiate.core.Executor;
import com.collegiate.core.Worker;

/**
 * Created by gauravarora on 23/09/15.
 */
public class DbAsyncWrapper {

    public <T> void executeGet(Worker<T> worker, DbTransactionCallbacks<T> dbTransactionCallbacks, Object... params){
        if(worker == null){
            return;
        }

        Runnable runnable = () -> {
            T result = worker.performWork(params);
            if(dbTransactionCallbacks != null){
                new Handler(Looper.getMainLooper()).post(() -> dbTransactionCallbacks.onSuccess(result));
            }
        };

        new Thread(runnable).start();
    }

    public void executeUpdate(Executor executor, DbTransactionCallbacks<Void> dbTransactionCallbacks, Object... params){
        if(executor == null){
            return;
        }

        Runnable runnable = () -> {
            executor.execute(params);
            if(dbTransactionCallbacks != null){
                new Handler(Looper.getMainLooper()).post(() -> dbTransactionCallbacks.onSuccess(null));
            }
        };

        new Thread(runnable).start();
    }

}
