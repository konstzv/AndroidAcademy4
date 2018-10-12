package com.zagulin.threads4

import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val locker = Locker()

    public override fun onStart() {
        super.onStart()
        Thread(LeftLeg()).start()
        Thread(RightLeg()).start()
    }

    private inner class LeftLeg : Runnable {

        override fun run() {

            synchronized(locker) {
                while (locker.isRunning) {
                    while (locker.status == Locker.RIGHT_LEG_NEXT) {
                        locker.wait()
                    }
                    println("Left step")
                    locker.status = Locker.RIGHT_LEG_NEXT
                    locker.notifyAll()
                }
            }
        }

    }

    private inner class RightLeg : Runnable {

        override fun run() {
            synchronized(locker) {

                while (locker.isRunning) {
                    while (locker.status == Locker.LEFT_LEG_NEXT) {
                        locker.wait()
                    }
                    println("Right step")
                    locker.status = Locker.LEFT_LEG_NEXT
                    locker.notifyAll()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locker.isRunning = false
    }

    class Locker : Object() {

        companion object {
            const val LEFT_LEG_NEXT = 0
            const val RIGHT_LEG_NEXT = 1
        }

        @Volatile
        var status = LEFT_LEG_NEXT

        @Volatile
        var isRunning = true

    }

}
