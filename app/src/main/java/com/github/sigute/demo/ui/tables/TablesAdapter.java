package com.github.sigute.demo.ui.tables;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sigute.demo.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TablesAdapter extends RecyclerView.Adapter<TableViewHolder> {
    @NonNull
    private List<Table> tables = new ArrayList<>();
    private TableViewHolder.Listener listener;

    @Inject
    public TablesAdapter() {
    }

    public void setTables(@NonNull @Size(min = 1) List<Table> tables, TableViewHolder.Listener listener) {
        this.tables = tables;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_table, parent, false);
        return new TableViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, int position) {
        holder.setTable(tables.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }
}
