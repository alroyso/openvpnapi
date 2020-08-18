package de.blinkt.openvpn;

import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;

/**
 * Created by singun on 17/3/4.
 */

public class ConnectVpnAuthActivity extends Activity {
    public static final String KEY_CONFIG = "config";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_RETRY = "RETRY";

    private String mConfig;
    private String mUsername;
    private String mPassword;
    private String mRETRY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = getIntent().getStringExtra(KEY_CONFIG);
        mUsername = getIntent().getStringExtra(KEY_USERNAME);
        mPassword = getIntent().getStringExtra(KEY_PASSWORD);
        mRETRY = getIntent().getStringExtra(KEY_RETRY);
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            startActivityForResult(intent, 0);
        } else {
            startVpn();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            startVpn();
        }
        finish();
    }

    private void startVpn() {
        try {
            OpenVpnConnector.startVpnInternal(this, mConfig, mUsername, mPassword,mRETRY);
        } catch (RuntimeException e) {
        }
    }
}
