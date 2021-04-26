package loginapp.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import loginapp.base.BaseActivity
import loginapp.demo.databinding.ActivityMainBinding
import loginapp.di.PreferenceStorage
import loginapp.homemodule.HomeActivity
import loginapp.loginmodule.LoginViewModel
import loginapp.loginmodule.modelclass.loginmodel.LoginPojo
import loginapp.registermodule.RegisterActivity
import loginapp.utili.DialogUtils
import javax.inject.Inject


@AndroidEntryPoint
@ActivityScoped
class MainActivity : BaseActivity<LoginViewModel, ActivityMainBinding>(), View.OnClickListener {

    @Inject
    lateinit var preferenceStorage: PreferenceStorage
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        initView()
        fetchlocation()
    }

    private fun fetchlocation() {
        if (ActivityCompat.checkSelfPermission(
               this, Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }
    }

    private fun initView() {
        mViewBinding.loginBtn.setOnClickListener(this)
        mViewBinding.signUP.setOnClickListener(this)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {

                }
            }
        }
    }

    override val mViewModel: LoginViewModel by viewModels()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.signUP->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.loginBtn -> {
                if(mViewBinding.etMail.text.toString().trim().equals(""))
                {
                    Toast.makeText(this, "Please enter mail id", Toast.LENGTH_SHORT).show()
                }
                else if(!isValidEmail(mViewBinding.etMail.text.toString().trim())){
                    Toast.makeText(this, "Please enter valid mail id", Toast.LENGTH_SHORT).show()
                }
                else if (mViewBinding.etPassword.text.toString().trim().equals("")) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }else if(mViewBinding.etPassword.text.toString().trim().length<7)
                {
                    Toast.makeText(this, "Password is not less than 8 character.", Toast.LENGTH_SHORT).show()
                }
                else {
                    DialogUtils.startProgressDialog(this)
                    val loginPojo = LoginPojo()
                    loginPojo.email = mViewBinding.etMail.text.toString().trim()
                    loginPojo.password = mViewBinding.etPassword.text.toString().trim()
                    val gson = Gson()
                    val json = gson.toJson(loginPojo)
                    val parser = JsonParser()
                    val loginObject = parser.parse(json).asJsonObject
                    mViewModel.getLoginCall(loginObject).observe(this, Observer {
                        DialogUtils.stopProgressDialog()
                        if (it != null && it.authenticationToken != null) {
                            lifecycleScope.launch {
                                preferenceStorage.setToken(it.authenticationToken)
                            }
                            val intent = Intent(this,HomeActivity::class.java)
                            startActivity(intent)
                            System.out.println("response" + it)
                        } else {
                            Toast.makeText(this, "" + it.msg, Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

}