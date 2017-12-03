package com.github.sigute.demo.background;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class ReservationsJobService extends JobService {
    private static final String TAG_RESERVATIONS_CLEAN_UP_JOB = "reservation-clean-up";
    private static final int WINDOW = 15 * 60; // 15 minutes

    @Override
    public boolean onStartJob(JobParameters job) {
        new ReservationsCleanUpJob(getApplication()).cleanUp();
        scheduleReservationsCleanUpJob(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    public static void scheduleReservationsCleanUpJob(@NonNull Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job job = dispatcher.newJobBuilder()
                .setService(ReservationsJobService.class)
                .setTag(TAG_RESERVATIONS_CLEAN_UP_JOB)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.executionWindow(WINDOW, WINDOW + 60))
                .build();
        dispatcher.mustSchedule(job);
    }
}