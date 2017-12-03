package com.github.sigute.demo.ui.customers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.sigute.demo.R;
import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.ui.tables.TablesActivity;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    private TextView name;

    public CustomerViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.customer_display_name);
    }

    public void setCustomer(@NonNull final Customer customer) {
        name.setText(itemView.getContext().getString(R.string.customer_display_name,
                customer.getFirstName(), customer.getLastName()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TablesActivity.getIntent(view.getContext(), customer);
                view.getContext().startActivity(intent);
            }
        });
    }
}
