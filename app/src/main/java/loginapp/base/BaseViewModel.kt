package loginapp.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val context = getApplication<Application>().applicationContext
}