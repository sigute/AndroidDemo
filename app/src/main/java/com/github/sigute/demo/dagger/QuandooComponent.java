package com.github.sigute.demo.dagger;

import com.github.sigute.demo.background.ReservationsCleanUpJob;
import com.github.sigute.demo.ui.customers.CustomersActivity;
import com.github.sigute.demo.ui.tables.TablesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {QuandooModule.class})
public interface QuandooComponent {
    void inject(CustomersActivity activity);

    void inject(TablesActivity activity);

    void inject(ReservationsCleanUpJob job);
}
