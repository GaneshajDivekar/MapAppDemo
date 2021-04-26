package loginapp.registermodule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch
import loginapp.base.BaseActivity
import loginapp.demo.R
import loginapp.demo.databinding.ActivityMainBinding
import loginapp.demo.databinding.ActivityRegisterBinding
import loginapp.di.PreferenceStorage
import loginapp.homemodule.HomeActivity
import loginapp.loginmodule.LoginViewModel
import loginapp.loginmodule.modelclass.loginmodel.LoginPojo
import loginapp.registermodule.model.RegisterObjectClass
import loginapp.utili.DialogUtils
import javax.inject.Inject

@AndroidEntryPoint
@ActivityScoped
class RegisterActivity : BaseActivity<RegisterViewModel,ActivityRegisterBinding>(),View.OnClickListener {

    @Inject
    lateinit var preferenceStorage: PreferenceStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        clickEvent()
    }

    private fun clickEvent() {
        mViewBinding.signupBtn.setOnClickListener(this)
    }


    override val mViewModel: RegisterViewModel by viewModels()
    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.signupBtn->{
                if(mViewBinding.etMail.equals(""))
                {
                    Toast.makeText(this,"Please enter email id",Toast.LENGTH_SHORT).show()
                }else if(!isValidEmail(mViewBinding.etMail.text.toString().trim())){
                    Toast.makeText(this, "Please enter valid mail id", Toast.LENGTH_SHORT).show()
                }
                else if(mViewBinding.etFullName.equals(""))
                {
                    Toast.makeText(this,"Please enter fullname",Toast.LENGTH_SHORT).show()

                }else if (mViewBinding.etPassword.text.toString().trim().equals("")) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }else if(mViewBinding.etPassword.text.toString().trim().length<7)
                {
                    Toast.makeText(this, "Password is not less than 8 character.", Toast.LENGTH_SHORT).show()
                }else if(mViewBinding.etConfirmPassword.equals(""))
                {
                    Toast.makeText(this,"Please enter confirm password.",Toast.LENGTH_SHORT).show()

                }else if(!(mViewBinding.etPassword.text.toString().trim().equals(mViewBinding.etConfirmPassword.text.toString().trim())))
                {
                    Toast.makeText(this,"Enter Password and confirm password is not matched.Please try again.",Toast.LENGTH_SHORT).show()

                }else
                {
                    DialogUtils.startProgressDialog(this)
                    val registerObjectClass = RegisterObjectClass()
                    registerObjectClass.email = mViewBinding.etMail.text.toString().trim()
                    registerObjectClass.password = mViewBinding.etPassword.text.toString().trim()
                    registerObjectClass.display_name=mViewBinding.etFullName.text.toString().trim()
                    val gson = Gson()
                    val json = gson.toJson(registerObjectClass)
                    val parser = JsonParser()
                    val registerObject = parser.parse(json).asJsonObject

                    mViewModel.getRegisterCall(registerObject).observe(this, Observer {
                        DialogUtils.stopProgressDialog()
                        if (it != null && it.authenticationToken != null) {
                            lifecycleScope.launch {
                                preferenceStorage.setToken(it.authenticationToken)
                            }
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
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