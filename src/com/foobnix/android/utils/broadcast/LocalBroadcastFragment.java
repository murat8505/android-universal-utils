package com.foobnix.android.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.foobnix.android.utils.LOG;
import com.foobnix.android.utils.ResultResponse;

public abstract class LocalBroadcastFragment extends Fragment implements ResultResponse<Intent> {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LOG.d("LocalBroadcastFragment", "onReceive", intent);
            onResultRecive(intent);
        };
    };

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, LocalBroadcast.INTENT_FILTER);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

}
