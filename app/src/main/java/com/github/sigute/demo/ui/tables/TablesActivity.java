package com.github.sigute.demo.ui.tables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.sigute.demo.R;
import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.dagger.DaggerWrapper;
import com.github.sigute.demo.utils.Database;

import javax.inject.Inject;

public class TablesActivity extends AppCompatActivity implements TableViewHolder.Listener {
    private static final String EXTRA_CUSTOMER = "EXTRA_CUSTOMER";

    public static Intent getIntent(@NonNull Context context, @NonNull Customer customer) {
        Intent intent = new Intent(context, TablesActivity.class);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        return intent;
    }

    @Inject
    public TablesAdapter tablesAdapter;

    @Inject
    public Database database;

    private RecyclerView tablesList;

    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        DaggerWrapper.getQuandooComponent(getApplication()).inject(this);
        setContentView(R.layout.activity_tables);

        tablesList = findViewById(R.id.table_list);

        tablesList.setAdapter(tablesAdapter);
        tablesList.setLayoutManager(new GridLayoutManager(this, 2));

        customer = getIntent().getParcelableExtra(EXTRA_CUSTOMER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tablesAdapter.setTables(database.getTables(), this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void tableSelected(@NonNull Table table) {
        table.setAvailable(false);
        database.setTableAvailability(table);
        Toast.makeText(this,
                getString(R.string.table_reservation_made, String.valueOf(table.getId()), customer.getFirstName(), customer.getLastName()),
                Toast.LENGTH_SHORT)
                .show();
        tablesAdapter.setTables(database.getTables(), this);
    }
}