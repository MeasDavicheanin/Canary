package com.cmput301w23t47.canary.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t47.canary.R;
import com.cmput301w23t47.canary.view.contract.SnapshotContract;

/** CaptureSnapshot
 * @author Dhruvraj Singh
 */

public class CaptureSnapshotActivity extends CameraBaseActivity {
    public static final String TAG = "CaptureSnapshotActivity";
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_snapshot);
        init();
    }

    protected void init() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        Bitmap img = (Bitmap)data.getExtras().get("data");
                        Intent intent = new Intent();
                        intent.putExtra(SnapshotContract.RESPONSE_TAG, img);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
        checkPermissions();
    }

    @Override
    protected void openCamera() {
        Log.d(TAG, "openCamera: ");
        Intent camera = new Intent();
        camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(camera);
    }
}
