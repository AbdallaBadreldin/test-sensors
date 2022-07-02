package com.fstech.sensorstest

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null

    private lateinit var publicText:TextView
    private lateinit var valueOne: TextView
    private lateinit var valueTwo: TextView
    private lateinit var valueThree: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init sensors
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        publicText = findViewById(R.id.publicText)
        valueOne = findViewById(R.id.valueOne)
        valueTwo = findViewById(R.id.valueTwo)
        valueThree = findViewById(R.id.valueThree)

        //get lsit of all sensors in this device
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sens in deviceSensors) {
            Log.v("SENSOR_HUB", sens.name)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            // Success! There's a magnetometer.
            Log.v(
                "SENSOR_HUB_maxRange",
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).maximumRange.toString()
            )
        } else {
            // Failure! No magnetometer.
        }

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.v("SENSOR_HUB_accuracy", "${sensor.name} = $accuracy")

        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        publicText.text = event.timestamp.toString()
        valueOne.text = event.values[0].toString()
        valueTwo.text = event.values[1].toString()
        valueThree.text = event.values[2].toString()
        // Do something with this sensor value.
    }

    override fun onResume() {
        super.onResume()
        mLight.apply { }
        mLight?.also { light ->
            sensorManager.registerListener(
                this,
                light,
                SensorManager.SENSOR_STATUS_UNRELIABLE
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}