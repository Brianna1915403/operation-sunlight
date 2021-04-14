package com.example.operationsunlight.modules.bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.R;

import java.util.List;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.ViewHolder> {
    private OnDeviceListener onDeviceListener;
    private Context context;
    private List<Object> deviceList;

    public DeviceRecyclerAdapter(Context context, List<Object> deviceList, OnDeviceListener onDeviceListener ) {
        this.context = context;
        this.deviceList = deviceList;
        this.onDeviceListener = onDeviceListener;
    }

    public DeviceRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_device, parent, false);
        return new ViewHolder(v, onDeviceListener);
    }

    @Override
    public void onBindViewHolder(@Nullable DeviceRecyclerAdapter.ViewHolder holder, final int position) {
        final DeviceInfoModel deviceInfoModel = (DeviceInfoModel) deviceList.get(position);
        holder.device_name = deviceInfoModel.getDeviceName();
        holder.device_address = deviceInfoModel.getDeviceHardwareAddress();
        holder.textName.setText(holder.device_name);
        holder.textAddress.setText(holder.device_address);
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String device_name;
        private String device_address;
        TextView textName, textAddress;
        LinearLayout linearLayout;
        OnDeviceListener onDeviceListener;

        public ViewHolder(View itemView, OnDeviceListener onDeviceListener) {
            super(itemView);
            this.onDeviceListener = onDeviceListener;
            textName = itemView.findViewById(R.id.textViewDeviceName);
            textAddress = itemView.findViewById(R.id.textViewDeviceAddress);
            linearLayout = itemView.findViewById(R.id.linearLayoutDeviceInfo);

            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) { onDeviceListener.onDeviceClick(device_name, device_address); }
    }
}
