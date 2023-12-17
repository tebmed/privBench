package com.overperm.javareflection.vulnerable;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private CameraManager cameraManager;
    private String cameraId = null;
    // Using Object instead of CameraDevice for reflection
    private Object cameraDevice = null;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                openCamera();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                // Surface changed
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                closeCamera();
            }
        });
    }

    private void openCamera() {
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK) {
                    this.cameraId = cameraId;
                    break;
                }
            }
            if (cameraId != null) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Method openCameraMethod = cameraManager.getClass().getMethod("openCamera", String.class, CameraDeviceStateCallback.class, null);
                    openCameraMethod.invoke(cameraManager, cameraId, new CameraDeviceStateCallback(), null);
                } else {
                    // Request camera permission
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
        } catch (CameraAccessException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (cameraDevice != null) {
            try {
                Method closeMethod = cameraDevice.getClass().getMethod("close");
                closeMethod.invoke(cameraDevice);
                cameraDevice = null;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private class CameraDeviceStateCallback {
        @SuppressWarnings("unused")
        public void onOpened(Object camera) {
            cameraDevice = camera;
            // Start camera preview here
            startPreview();
        }

        @SuppressWarnings("unused")
        public void onDisconnected(Object camera) {
            closeCamera();
        }

        @SuppressWarnings("unused")
        public void onError(Object camera, int error) {
            closeCamera();
        }
    }

    private void startPreview() {
        try {
            Surface surface = surfaceHolder.getSurface();
            Method createCaptureRequestMethod = cameraDevice.getClass().getMethod("createCaptureRequest", int.class);
            Object captureRequestBuilder = createCaptureRequestMethod.invoke(cameraDevice, 0);
            Method addTargetMethod = captureRequestBuilder.getClass().getMethod("addTarget", Surface.class);
            addTargetMethod.invoke(captureRequestBuilder, surface);
            Method createCaptureSessionMethod = cameraDevice.getClass().getMethod("createCaptureSession", Iterable.class, CameraCaptureSessionStateCallback.class, null);
            createCaptureSessionMethod.invoke(cameraDevice, Collections.singletonList(surface), new CameraCaptureSessionStateCallback(), null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private class CameraCaptureSessionStateCallback {
        @SuppressWarnings("unused")
        public void onConfigured(Object session) {
            try {
                Method setRepeatingRequestMethod = session.getClass().getMethod("setRepeatingRequest", Object.class, Object.class, Object.class);
                Object captureRequestBuilder = setRepeatingRequestMethod.invoke(cameraDevice, 0);
                Method buildMethod = captureRequestBuilder.getClass().getMethod("build");
                Object captureRequest = buildMethod.invoke(captureRequestBuilder);
                setRepeatingRequestMethod.invoke(session, captureRequest, null, null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("unused")
        public void onConfigureFailed(Object session) {
            Log.d("Camera", "Failed to start the camera preview.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeCamera();
    }
}
