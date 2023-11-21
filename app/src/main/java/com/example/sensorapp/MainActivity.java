package com.example.sensorapp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener {
    private static final String TAG = "ProximitySensorApp";


    private SensorManager sensorManager;
    private Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        if (proximitySensor == null) {
            Log.e(TAG, "Proximity sensor not available.");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Desregistrar el listener cuando la actividad está en segundo plano para ahorrar recursos
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Manejar cambios en los valores del sensor de proximidad

        // Si la distancia al objeto es menor que el rango máximo del sensor
        if (event.values[0] < proximitySensor.getMaximumRange()) {
            // Cambiar el color de fondo a rojo
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        } else {
            // Si no hay un objeto cercano, cambiar el color de fondo a verde
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Manejar cambios en la precisión del sensor (puedes dejar este método vacío si no es necesario)
    }
}
