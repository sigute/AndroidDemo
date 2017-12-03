package com.github.sigute.demo.api;

import com.github.sigute.demo.api.model.Customer;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface QuandooService {
    @GET("quandoo-assessment/table-map.json")
    Single<List<Boolean>> getTables();

    @GET("quandoo-assessment/customer-list.json")
    Single<List<Customer>> getCustomers();
}
