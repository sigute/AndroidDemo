package com.github.sigute.demo.background;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.sigute.demo.dagger.DaggerWrapper;
import com.github.sigute.demo.utils.Database;

import javax.inject.Inject;

public class ReservationsCleanUpJob {
    @Inject
    public Database database;

    public ReservationsCleanUpJob(@NonNull Application application) {
        DaggerWrapper.getQuandooComponent(application).inject(this);
    }

    public void cleanUp() {
        Log.d("", "clean up");
        database.resetTableAvailability();
    }
}
