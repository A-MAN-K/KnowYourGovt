package com.example.adsg1.knowyourgovt;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by adsg1 on 4/7/2017.
 */

public class OfficialViewHolder extends RecyclerView.ViewHolder {

    public TextView officialDesignation;
    public TextView officialName;


    public OfficialViewHolder(View itemView) {
        super(itemView);

        officialDesignation = (TextView) itemView.findViewById(R.id.officialDesignation);
        officialName = (TextView) itemView.findViewById(R.id.officialName);


    }
}
