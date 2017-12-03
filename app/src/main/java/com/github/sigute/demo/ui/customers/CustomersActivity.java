package com.github.sigute.demo.ui.customers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sigute.demo.R;
import com.github.sigute.demo.api.QuandooService;
import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.background.ReservationsJobService;
import com.github.sigute.demo.dagger.DaggerWrapper;
import com.github.sigute.demo.utils.Database;
import com.github.sigute.demo.utils.Preferences;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CustomersActivity extends AppCompatActivity {
    @Inject
    public QuandooService quandooService;

    @Inject
    public CustomersAdapter customersAdapter;

    @Inject
    public Preferences preferences;

    @Inject
    public Database database;

    private View customersLayout;
    private EditText customerSearch;
    private RecyclerView customersList;
    private ProgressBar progressBar;
    private View errorLayout;
    private TextView error;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        DaggerWrapper.getQuandooComponent(getApplication()).inject(this);
        setContentView(R.layout.activity_customers);

        setTitle(R.string.activity_customers);

        customersLayout = findViewById(R.id.customer_layout);
        customerSearch = findViewById(R.id.customer_search);
        customersList = findViewById(R.id.customer_list);
        progressBar = findViewById(R.id.progress_bar);
        errorLayout = findViewById(R.id.error_layout);
        error = findViewById(R.id.error);
        retryButton = findViewById(R.id.retry);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showView(progressBar);
                retrieveCustomers();
            }
        });

        customersList.setAdapter(customersAdapter);
        customersList.setLayoutManager(new LinearLayoutManager(this));

        customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //no action
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //no action
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customersAdapter.setFilter(s.toString());
            }
        });

        ReservationsJobService.scheduleReservationsCleanUpJob(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date lastRefreshed = preferences.getLastRefreshedDate();
        if (lastRefreshed == null) {
            showView(progressBar);
            retrieveCustomers();
        } else {
            showView(customersLayout);
            customersAdapter.setCustomers(database.getCustomers());
        }
    }

    private void showView(@NonNull View view) {
        progressBar.setVisibility(View.GONE);
        customersLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    private void retrieveCustomers() {
        quandooService.getCustomers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceSingleObserver<List<Customer>>() {
                    @Override
                    public void onSuccess(List<Customer> customers) {
                        database.storeCustomers(customers);
                        if (!customers.isEmpty()) {
                            customersAdapter.setCustomers(customers);
                        }
                        retrieveTables();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showView(errorLayout);
                    }
                });
    }

    private void retrieveTables() {
        quandooService.getTables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceSingleObserver<List<Boolean>>() {
                    @Override
                    public void onSuccess(List<Boolean> tables) {
                        database.storeTables(tables);
                        showView(customersLayout);
                        preferences.setRefreshed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showView(errorLayout);
                    }
                });
    }
}
