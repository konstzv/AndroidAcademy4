package com.zagulin.threads4

import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LEFT_STEP = "Left step"
        private const val RIGHT_STEP = "Right step"
    }


    private val legsController = LegsController()

    public override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_main)
        Thread(LeftLeg()).start()
        Thread(RightLeg()).start()
    }


    private inner class LeftLeg : Runnable {

        override fun run() {

            synchronized(legsController) {
                while (legsController.isRunning) {
                    while (legsController.status == LegsController.RIGHT_LEG_NEXT) {
                        legsController.wait()
                    }
                    println(LEFT_STEP)
                    runOnUiThread { activity_main_text_view.text = LEFT_STEP }
                    legsController.status = LegsController.RIGHT_LEG_NEXT
                    legsController.notifyAll()
                }
            }
        }

    }


    private inner class RightLeg : Runnable {

        override fun run() {
            synchronized(legsController) {

                while (legsController.isRunning) {
                    while (legsController.status == LegsController.LEFT_LEG_NEXT) {
                        legsController.wait()
                    }
                    println(RIGHT_STEP)
                    runOnUiThread { activity_main_text_view.text = RIGHT_STEP }
                    legsController.status = LegsController.LEFT_LEG_NEXT
                    legsController.notifyAll()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        legsController.isRunning = false
    }

    class LegsController : Object() {

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
