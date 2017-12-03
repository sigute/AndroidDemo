package com.github.sigute.demo.dagger;

import android.app.Application;
import android.support.annotation.NonNull;

public final class DaggerWrapper {
    private DaggerWrapper() {
    }

    private static QuandooComponent component;

    public static QuandooComponent getQuandooComponent(@NonNull Application application) {
        if (component == null) {
            initComponent(application);
        }

        return component;
    }

    private static void initComponent(@NonNull Application application) {
        component = DaggerQuandooComponent
                .builder()
                .quandooModule(new QuandooModule(application))
                .build();
    }
}
