package com.holcvart.androidptut.view.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Part;

import java.util.List;

public class SpinnerPartsAdapter extends ArrayAdapter<Part> {
    private List<Part> mParts;
    public SpinnerPartsAdapter(@NonNull Context context, int resource, @NonNull List<Part> objects) {
        super(context, resource, objects);
        this.mParts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Part part = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_part, parent, false);
        }
        TextView namingTextView = convertView.findViewById(R.id.textViewAdapterPartNaming);
        namingTextView.setText(part.getNaming());
        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Part part = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_part, parent, false);
        }
        TextView namingTextView = convertView.findViewById(R.id.textViewAdapterPartNaming);
        namingTextView.setText(part.getNaming());
        return convertView;
    }



    @Nullable
    @Override
    public Part getItem(int position) {
        return mParts.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d("item id by position", String.valueOf(mParts.get(position).getId()));
        return mParts.get(position).getId();
    }
}
