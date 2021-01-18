package com.holcvart.androidptut.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;

import java.util.List;

public class SpinnerClientAdapter extends ArrayAdapter<Client> {
    private List<Client> mClients;

    public SpinnerClientAdapter(@NonNull Context context, int resource, @NonNull List<Client> objects) {
        super(context, resource, objects);
        this.mClients = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Client client = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_client, parent, false);
        }
        TextView firstNameTextView = convertView.findViewById(R.id.textViewAdapterFirstName);
        firstNameTextView.setText(client.getFirstName() + " " + client.getName());
        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Client client = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_client, parent, false);
        }
        TextView firstNameTextView = convertView.findViewById(R.id.textViewAdapterFirstName);
        firstNameTextView.setText(client.getFirstName() + " "+ client.getName());
        return convertView;
    }



    @Nullable
    @Override
    public Client getItem(int position) {
        return mClients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mClients.get(position).getId();
    }
}
