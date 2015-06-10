package com.weezlabs.screenoffswitcher;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.weezlabs.screenoffswitcher.util.Utils;


public class MainActivity extends AppCompatActivity {

    private static final int ADMIN_INTENT_CODE = 11;
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private CheckBox mEnableReceiverCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(getApplicationContext(), AdminReceiver.class);

        mEnableReceiverCheckBox = (CheckBox) findViewById(R.id.enable_receiver_checkbox);
        mEnableReceiverCheckBox.setEnabled(isAdmin());
        mEnableReceiverCheckBox.setChecked(Utils.isPhoneStateReceiverWork(getApplicationContext()));

        mEnableReceiverCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isAdmin()) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_register_admin_first), Toast.LENGTH_SHORT).show();
                    mEnableReceiverCheckBox.setChecked(false);
                } else {
                    setReceiverWork(isChecked);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.toast_register_admin_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.toast_register_admin_failed), Toast.LENGTH_SHORT).show();
            }
            mEnableReceiverCheckBox.setEnabled(isAdmin());
        }
    }

    public void onClickEnableDeviceAdmin(View view) {
        if (!isAdmin()) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    getString(R.string.explanation_device_admin));
            startActivityForResult(intent, ADMIN_INTENT_CODE);
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_register_admin_already), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickDisableDeviceAdmin(View view) {
        if (isAdmin()) {
            mDevicePolicyManager.removeActiveAdmin(mComponentName);
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_register_admin_removed), Toast.LENGTH_SHORT).show();
            mEnableReceiverCheckBox.setEnabled(false);
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_register_admin_denied), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickTest(View view) {
        if (isAdmin()) {
            mDevicePolicyManager.lockNow();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.toast_register_admin_denied), Toast.LENGTH_SHORT).show();
        }

    }

    private void setReceiverWork(boolean isWork) {
        Utils.setPhoneStateReceiverWork(getApplicationContext(), isWork);
    }

    private boolean isAdmin() {
        return mDevicePolicyManager.isAdminActive(mComponentName);
    }
}
