package com.example.operationsunlight.modules.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.operationsunlight.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeviceSelectionFragmentView extends Fragment implements OnDeviceListener {
    private View root;
    private RecyclerView recyclerView;
    private DeviceRecyclerAdapter adapter;
    private List<Object> device_list =  new ArrayList<>();
    private OnDeviceListener onDeviceListener = this::onDeviceClick;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_device_selection_view, container, false);
        recyclerView = root.findViewById(R.id.device_recyclerview);
        adapter = new DeviceRecyclerAdapter(root.getContext(), device_list, onDeviceListener);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get List of Paired Bluetooth Device
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                DeviceInfoModel deviceInfoModel = new DeviceInfoModel(deviceName,deviceHardwareAddress);
                device_list.add(deviceInfoModel);
            }
            // Display paired device using recyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            adapter = new DeviceRecyclerAdapter(root.getContext(), device_list, onDeviceListener);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
//            View view = root.findViewById(R.id.recyclerViewDevice);
            Snackbar snackbar = Snackbar.make(view, "Activate Bluetooth or pair a Bluetooth device", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) { }
            });
            snackbar.show();
        }
    }

    @Override
    public void onDeviceClick(String device_name, String device_address) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("device_name", device_name);
        bundle.putString("device_address", device_address);
        navController.navigate(R.id.nav_greenhouse_view, bundle);
    }
}