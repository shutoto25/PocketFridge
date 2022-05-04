package com.example.pocketfridge.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.ActivityUserLoginBinding
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.viewModel.UserLoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar

/**
 * ログイン画面
 * (LauncherActivity).
 */
class UserLoginActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "UserLoginActivity"
    }

    /** Binding. */
    private lateinit var binding: ActivityUserLoginBinding

    /** Google Sing in client. */
    private lateinit var googleSignInClient: GoogleSignInClient

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[UserLoginViewModel::class.java]
    }

    /** Googleサインイン結果. */
    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                viewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showErrorToast()
            }
        }
    }

    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_login)
        binding.apply {
            lifecycleOwner = this@UserLoginActivity
            viewModel = this@UserLoginActivity.viewModel
        }
        initGoogleSignIn()
        setEventObserver()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
        // ログイン状態判定.
        if (viewModel.isLogin()) {
            // ログイン済み状態の場合はそのまま画面遷移.
            viewModel.onAlreadyLogin()
        }
    }

    /* -------------- method. ------------------ */
    /** googleサインイン設定. */
    private fun initGoogleSignIn() {
        Log.d(TAG, "setConfigForGoogleSingIn() called")
        // クライアント初期化.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // TODO xml側でonClickが使えなかったのでひとまずこちらで実装
        binding.googleSignIn.setOnClickListener {
            // サインイン処理開始.
            val signInIntent = googleSignInClient.signInIntent
            googleLoginLauncher.launch(signInIntent)
        }
    }

    /** イベントオブザーバー. */
    private fun setEventObserver() {
        // viewModelのonSignパラメータが更新を検知して画面遷移判定を行う.
        viewModel.onSign.observe(this, EventObserver {
            Log.d(TAG, "observed event = $it")
            when (it) {
                // TODO エラーによってトースト文言分けるようにしたい.
                "EVENT_ERROR" -> showErrorToast()
                else -> transitHome()
            }
        })
    }

    /** エラートースト表示. */
    private fun showErrorToast() {
        Snackbar.make(binding.snackBarArea, "invalid input values.", Snackbar.LENGTH_SHORT).show()
    }

    /** TOP画面に遷移. */
    private fun transitHome() {
        val intent = Intent(this, BaseActivity::class.java)
        startActivity(intent)
        finish()
    }
}