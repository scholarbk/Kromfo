package scholar.kromfo.Helpers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MotionSensors extends AppCompatActivity {
    private SensorManager mSensorManager;

    private Sensor mLightSensor;
    private Sensor mProximitySensor;
    private Sensor mAccelerameterSensor;
    private Sensor mGyroscopeSensor;
    private Sensor mTemperatureSensor;

    private SensorEventListener mLightListener;
    private SensorEventListener mProximityListener;
    private SensorEventListener mAccelerameterListener;
    private SensorEventListener mGyroscopeListener;
    private SensorEventListener mTemoeratureListener;

    private Context context;

    private boolean supportProximity=false;
    private boolean supportGyroscope=false;
    private boolean supportAccelerameter=false;
    private boolean supportLight=false;
    private boolean supportTemperature=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mAccelerameterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);


        if (mLightSensor == null) { supportLight=false; } else { supportLight=true; }
        if (mAccelerameterSensor == null) { supportAccelerameter=false; } else { supportAccelerameter=true; }
        if (mGyroscopeSensor == null) { supportGyroscope=false; } else { supportGyroscope=true; }
        if (mTemperatureSensor == null) { supportTemperature=false; } else { supportTemperature=true; }
        if (mProximitySensor == null) { supportProximity=false; } else { supportProximity=true; } }
}