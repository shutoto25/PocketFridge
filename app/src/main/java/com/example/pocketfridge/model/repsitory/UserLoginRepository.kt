package com.example.pocketfridge.model.repsitory

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Firebase Authentication連携.
 */
class UserLoginRepository {

    /** インスタンス. */
    companion object Factory {
        val instance: UserLoginRepository
            @Synchronized get() {
                return UserLoginRepository()
            }
    }

    /** Firebase Authentication. */
    lateinit var auth: FirebaseAuth

    init {
        initConfig()
    }

    private fun initConfig() {
        // Firebase auth初期化.
        auth = Firebase.auth
    }

    /** 新規アカウント作成. */
    suspend fun onSignUp(email: String, password: String): String {
        // suspendCoroutineを呼び出すと、そこで実行を一時停止し、
        // cont.resumeまたはcont.resumeWithExceptionが呼び出されたら再開する.
        // ここではListenerのコールバックを受信したときにcont.resumeを呼び出して処理を再開させる.
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                cont.resume(if (task.isSuccessful) "EVENT_SIGN_UP" else "EVENT_ERROR")
            }
        }
    }

    /** ログイン. */
    suspend fun onSignIn(email: String, password: String): String {
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                cont.resume(if (task.isSuccessful) "EVENT_SIGN_IN" else "EVENT_ERROR")
            }
        }
    }

    /** Googleでのログイン. */
    suspend fun firebaseAuthWithGoogle(idToken: String): String {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return suspendCoroutine { cont ->
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                cont.resume(if (task.isSuccessful) "EVENT_SIGN_IN" else "EVENT_ERROR")
            }
        }
    }

    /** ログアウト. */
    fun onSignOut() {
        auth.signOut()
    }

    /** ログイン状態確認. */
    fun isLogin(): Boolean = auth.currentUser != null
}