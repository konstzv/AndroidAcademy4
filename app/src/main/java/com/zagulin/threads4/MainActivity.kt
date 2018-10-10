package com.zagulin.threads4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    public override fun onStart() {
        super.onStart()
        Thread(LeftLeg()).start()
        Thread(RightLeg()).start()
    }

    private inner class LeftLeg : Runnable {
        private val isRunning = true
        override fun run() {
            while (isRunning) println("Left step")
        }
    }

    private inner class RightLeg : Runnable {
        private val isRunning = true
        override fun run() {
            while (isRunning) println("Right step")
        }
    }

}
