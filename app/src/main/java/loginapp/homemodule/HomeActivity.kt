package loginapp.homemodule

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import loginapp.base.BaseActivity
import loginapp.demo.R
import loginapp.demo.databinding.ActivityHomeBinding
import loginapp.homemodule.homefragmentmodule.MapsFragment
import loginapp.homemodule.profilefragmentmodule.ProfileMapsFragment

@AndroidEntryPoint
@ActivityScoped
class HomeActivity : BaseActivity<HomeViewModel,ActivityHomeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
        mViewBinding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(MapsFragment());
    }

    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_map -> {
                        openFragment(MapsFragment())
                        return true
                    }
                    R.id.dryclean -> {
                        openFragment(ProfileMapsFragment())
                        return true
                    }
                }
                return false
            }
        }

    override val mViewModel: HomeViewModel by viewModels()
    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)


}