package com.example.adsg1.knowyourgovt;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by adsg1 on 4/6/2017.
 */

public class OfficialAdapter extends RecyclerView.Adapter<OfficialViewHolder> {


    private static final String TAG = "OfficialAdapter";
    private ArrayList<Official> officialList;
    private MainActivity mainAct;

    public OfficialAdapter(ArrayList<Official> stockList, MainActivity mainActivity) {
        this.officialList = stockList;
        mainAct = mainActivity;
    }

    @Override
    public OfficialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);


        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);


        return new OfficialViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(OfficialViewHolder holder, int position) {

        Official official = officialList.get(position);

        /* Code for party in the bracket starts here */

        StringBuffer nameWithParty = new StringBuffer();
        nameWithParty.append(official.getOfficialName());

        if(official.getParty() != null)
        {
            nameWithParty.append("( ");
            nameWithParty.append(official.getParty());
            nameWithParty.append(" )");
        }

        /* Code for party in the bracket ends here */



        holder.officialName.setText(nameWithParty);
        holder.officialDesignation.setText(official.getOfficialDesignation());
     
    }

    @Override
    public int getItemCount() {

        return officialList.size();

    }
}
