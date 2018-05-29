package scholar.kromfo.Helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import scholar.kromfo.Classes.LockScreenActivity;

public class MotionSensors extends Service {
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

    private Context context = null;

    private boolean supportProximity=false;
    private boolean supportGyroscope=false;
    private boolean supportAccelerameter=false;
    private boolean supportLight=false;
    private boolean supportTemperature=false;




    private SessionManager sessionManager;



    float oldValue = -1, newValue = -1;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        context = this;
        sessionManager=new SessionManager(context);
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
        if (mProximitySensor == null) { supportProximity=false; } else { supportProximity=true; }


        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float[] g = new float[3];
                g = event.values.clone();

                double norm = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);
                g[0] /= norm;
                g[1] /= norm;
                g[2] /= norm;
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(g[2])));
                if(sessionManager.isFlatSense()==true){
                    if (inclination < 25 || inclination > 155) {

                    } else {

                        Intent i = new Intent(context,LockScreenActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);


                    }

                }else{

                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

        }, mAccelerameterSensor, SensorManager.SENSOR_DELAY_FASTEST);

        if(supportProximity){
            mSensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float maxValue = event.sensor.getMaximumRange();
                    if(oldValue < 0){
                        oldValue = event.values[0];
                    } else {

                        newValue = event.values[0];
                        if(oldValue != newValue){
                            Intent i = new Intent(context,LockScreenActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);

                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }

            }, mProximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

        return START_NOT_STICKY;
    }

    private double[] convertValuesToDouble(float[] value) {
        if (value == null)
            return null;

        double[] output = new double[value.length];

        for (int i = 0; i < value.length; i++)
            output[i] = value[i];

        return output;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(mAccelerameterListener);
        mSensorManager.unregisterListener(mProximityListener);
        super.onDestroy();
    }
}