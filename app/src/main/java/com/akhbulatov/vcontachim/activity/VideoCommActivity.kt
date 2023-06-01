package com.akhbulatov.vcontachim.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Video
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.io.Serializable

class VideoCommActivity : AppCompatActivity(R.layout.activity_video_comm) {
    private var navigator = AppNavigator(this, R.id.container_video)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoExtra: Serializable? = intent.getSerializableExtra(ARGUMENTS_VIDEO_COMM)
        val video: Video.Item = videoExtra as Video.Item

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.videoComments(video))
        }
    }

    override fun onResume() {
        super.onResume()
        VcontachimApplication.navigateHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        VcontachimApplication.navigateHolder.removeNavigator()
    }

    companion object {
        const val ARGUMENTS_VIDEO_COMM = "COMM"

        fun createFragment(context: Context, video: Video.Item): Intent {
            val intent = Intent(context, VideoCommActivity::class.java)
            intent.putExtra(ARGUMENTS_VIDEO_COMM, video)

            return intent
        }
    }
}