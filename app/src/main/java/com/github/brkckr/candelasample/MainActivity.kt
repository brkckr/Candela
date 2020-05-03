package com.github.brkckr.candelasample

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.brkckr.candela.ProgressListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var currentProgress = 0;
    var maxProgress = 0;

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        candela.setCurrentProgress(200)
        currentProgress = candela.getCurrentProgress()
        maxProgress = candela.getMaxProgress()

        setBrightness(currentProgress)

        candela.setSunlightStrokeWidth(10)

        candela.setProgressListener(object : ProgressListener {
            override fun onProgressChange(progress: Int) {
                setBrightness(progress)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission()
    {
        if (Settings.System.canWrite(this))
        {
            // change setting here
        } else {
            //Migrate to Setting write permission screen.
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.setData(Uri.parse("package:" + getPackageName()))
            startActivity(intent)
        }
    }

    private fun setBrightness(brightness: Int) {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 1)
        val layoutpars = window.attributes

        layoutpars.screenBrightness =  brightness.toFloat() / maxProgress.toFloat()

        window.attributes = layoutpars
    }
}
