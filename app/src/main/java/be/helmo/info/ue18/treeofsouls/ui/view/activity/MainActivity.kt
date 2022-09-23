package be.helmo.info.ue18.treeofsouls.ui.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication

import be.helmo.info.ue18.treeofsouls.databinding.ActivityMainBinding
import be.helmo.info.ue18.treeofsouls.ui.view.fragment.*
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        var topNavigationView: MaterialToolbar = findViewById(R.id.top_navigation)

        bottomNavigationView.menu.getItem(0).isCheckable = true
        setFragment(MemoryFragment())

        bottomNavigationView.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.nav_home -> {
                    setFragment(MemoryFragment())
                    true
                }
                R.id.nav_category -> {
                    setFragment(CategoryFragment())
                    true
                }
                R.id.nav_friend -> {
                    setFragment(FriendFragment())
                    true
                }
                else -> false
            }
        }

        topNavigationView.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.nav_settings -> {
                    setActivity(SettingsActivity())
                    true
                }
                else -> false
            }
        }
    }

    fun setFragment(fr : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        frag.replace(R.id.fragment_container,fr)
        frag.commit()
    }

    fun setActivity(ac: Activity){
        val intent = Intent(this, ac::class.java)
        startActivity(intent)
    }
}