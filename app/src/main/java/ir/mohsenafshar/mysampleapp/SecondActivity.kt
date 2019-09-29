package ir.mohsenafshar.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class SecondActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }
}
