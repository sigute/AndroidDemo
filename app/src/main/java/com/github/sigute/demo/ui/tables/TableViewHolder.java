package com.github.sigute.demo.ui.tables;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sigute.demo.R;

public class TableViewHolder extends RecyclerView.ViewHolder {
    public interface Listener {
        void tableSelected(@NonNull Table table);
    }

    private TextView number;
    private ImageView indicator;
    private TextView description;

    public TableViewHolder(View itemView) {
        super(itemView);
        number = itemView.findViewById(R.id.table_number);
        indicator = itemView.findViewById(R.id.table_indicator);
        description = itemView.findViewById(R.id.table_description);
    }

    public void setTable(@NonNull final Table table, @NonNull final Listener listener) {
        number.setText(String.valueOf(table.getId()));

        if (!table.available()) {
            description.setText(itemView.getContext().getText(R.string.table_reserved));
            number.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary_text));
            description.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.secondary_text));
            indicator.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_group));
            indicator.setContentDescription(itemView.getContext().getString(R.string.table_reserved));
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.primary));
            itemView.setOnClickListener(null);
            return;
        }

        description.setText(itemView.getContext().getText(R.string.table_available));
        number.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.light_text));
        description.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.light_text));
        indicator.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_group_add));
        indicator.setContentDescription(itemView.getContext().getString(R.string.tap_to_reserve_table));
        itemView.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.accent_with_states));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.tableSelected(table);
            }
        });
    }
}
