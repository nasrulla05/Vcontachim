package com.akhbulatov.vcontachim.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.model.Item
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.io.Serializable

class PhotoActivity : AppCompatActivity(R.layout.activity_photo) {
    private var navigator = AppNavigator(this, R.id.photo_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photoExtra: Serializable? = intent.getSerializableExtra(ARGUMENTS_PHOTO)
        val photo: Item = photoExtra as Item

        if (savedInstanceState == null) {
            VcontachimApplication.router.replaceScreen(Screens.photoFr(photo))
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
        const val ARGUMENTS_PHOTO = "PHOTO"

        fun createActivity(context: Context, itemPhoto: Item): Intent {
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra(ARGUMENTS_PHOTO, itemPhoto)
            return intent
        }
    }

}