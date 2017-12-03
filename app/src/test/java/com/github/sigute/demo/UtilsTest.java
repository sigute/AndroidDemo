package com.github.sigute.demo;

import com.github.sigute.demo.api.model.Customer;
import com.github.sigute.demo.utils.Utils;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilsTest {
    @Test
    public void searchFilterMatchingSurnameTest() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John", "Doe"));
        customers.add(new Customer(2, "Jane", "Doe"));
        List<Customer> filteredCustomers = Utils.filterCustomers(customers, "Doe");
        Assert.assertTrue(filteredCustomers.size() == 2);
    }

    @Test
    public void searchFilterNonMatchingTest() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John", "Doe"));
        customers.add(new Customer(2, "Jane", "Doe"));
        List<Customer> filteredCustomers = Utils.filterCustomers(customers, "Smith");
        Assert.assertTrue(filteredCustomers.size() == 0);
    }

    @Test
    public void searchFilterAllCapsTest() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John", "Doe"));
        customers.add(new Customer(2, "Jane", "Doe"));
        List<Customer> filteredCustomers = Utils.filterCustomers(customers, "DOE");
        Assert.assertTrue(filteredCustomers.size() == 2);
    }

    @Test
    public void searchFilterNameTest() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "John", "Doe"));
        customers.add(new Customer(2, "Jane", "Doe"));
        List<Customer> filteredCustomers = Utils.filterCustomers(customers, "Jane");
        Assert.assertTrue(filteredCustomers.size() == 1 && filteredCustomers.get(0).getFirstName().equals("Jane"));
    }
}
