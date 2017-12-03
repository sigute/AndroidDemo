package com.github.sigute.demo.utils;

import android.support.annotation.NonNull;

import com.github.sigute.demo.api.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    @NonNull
    public static List<Customer> filterCustomers(@NonNull List<Customer> customers, String searchFilter) {
        List<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getFirstName().toLowerCase().contains(searchFilter.toLowerCase())
                    || customer.getLastName().toLowerCase().contains(searchFilter.toLowerCase())) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;
    }
}
