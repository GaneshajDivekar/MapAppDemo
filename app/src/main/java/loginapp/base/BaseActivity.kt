

package loginapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.util.*
import javax.inject.Inject

/**
 * Abstract Activity which binds [ViewModel] [VM] and [ViewBinding] [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected abstract val mViewModel: VM

    protected lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()
    }

    /**
     * It returns [VB] which is assigned to [mViewBinding] and used in [onCreate]
     */
    abstract fun getViewBinding(): VB
    fun setLanguage(langCode: String) { //setting new configuration
        var language = langCode
        System.out.println("Language"+language)
        var locale = Locale(language)
        var res = resources
        var dm = res.displayMetrics
        val config = res.configuration
        config.locale = locale
        res.updateConfiguration(config, dm)
    }


}
