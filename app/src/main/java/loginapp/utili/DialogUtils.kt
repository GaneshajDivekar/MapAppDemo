package loginapp.utili

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import loginapp.demo.R

public class DialogUtils @Throws(ClassCastException::class)
constructor(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_dialog_trans)
    }

    companion object {
        private var customeProgress: DialogUtils? = null
        fun startProgressDialog(context: Context): DialogUtils {
            customeProgress = DialogUtils(context)
            customeProgress?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customeProgress?.setCancelable(false)
            customeProgress?.show()
            return customeProgress!!
        }

        fun stopProgressDialog() {
            if (customeProgress != null) {
                customeProgress?.dismiss()
                customeProgress = null
            } else if (customeProgress == null) {

            } else {
                customeProgress?.dismiss()
                customeProgress = null
            }
        }
    }
}
