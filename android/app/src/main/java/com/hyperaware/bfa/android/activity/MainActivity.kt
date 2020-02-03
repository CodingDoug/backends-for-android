package com.hyperaware.bfa.android.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyperaware.bfa.android.R
import com.hyperaware.bfa.android.config.Config
import com.hyperaware.bfa.android.fragment.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Config.set(Config(getString(R.string.cloud_run_root)))

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
