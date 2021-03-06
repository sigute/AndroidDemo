package com.github.sigute.demo.ui.customers;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sigute.demo.R;
import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomersAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
    @NonNull
    private List<Customer> originalCustomers = new ArrayList<>();

    @NonNull
    private List<Customer> customers = new ArrayList<>();

    @NonNull
    private String searchFilter = "";

    @Inject
    public CustomersAdapter() {
    }

    public void setCustomers(@NonNull List<Customer> customers) {
        this.originalCustomers = customers;
        this.customers = Utils.filterCustomers(originalCustomers, searchFilter);
        notifyDataSetChanged();
    }

    public void setFilter(@NonNull String searchFilter) {
        this.searchFilter = searchFilter;
        this.customers = Utils.filterCustomers(originalCustomers, searchFilter);
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customer, parent, false);
        return new CustomerViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        holder.setCustomer(customers.get(position));
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
}
