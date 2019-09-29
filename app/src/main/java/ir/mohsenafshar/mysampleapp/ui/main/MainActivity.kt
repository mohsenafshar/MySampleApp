package ir.mohsenafshar.mysampleapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import ir.mohsenafshar.mysampleapp.R
import ir.mohsenafshar.mysampleapp.SecondActivity
import ir.mohsenafshar.mysampleapp.databinding.ActivityMainBinding
import timber.log.Timber
import java.lang.NullPointerException
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w("getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token
                Timber.w(token)

                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
//                Timber.w(msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })


        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        val disposable = binding.tvRemove.clicks()
            .subscribe({
                viewModel.setHeaderVisibility(false)
            }, {})

        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()


        Handler().postDelayed({
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name")
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }, 4000)
    }

    fun goToNext(view: View) {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}
