//package com.dicoding.submissionMade
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.dicoding.submissionMade.activity.SettingActivity
//import com.dicoding.submissionMade.fragment.FavoriteFragment
//import com.dicoding.submissionMade.fragment.MovieFragment
//import com.dicoding.submissionMade.fragment.TvShowFragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import kotlinx.android.synthetic.main.activity_main.*
//
//public class MainAcitivity : AppCompatActivity() {
//
//    private lateinit var fragment: Fragment
//
////    Setting navigation that will used
//    private val mOnNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when(item.itemId) {
//            R.id.navigation_movie -> {
//                fragment = MovieFragment()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_tv_show -> {
//                fragment = TvShowFragment()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_favorites -> {
//                fragment = FavoriteFragment()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
//            else -> {
//                fragment = MovieFragment()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        nav_view.setOnNavigationItemSelectedListener(mOnNavigationListener)
//
//        if (savedInstanceState == null ) nav_view.selectedItemId = R.id.navigation_movie
//    }
//
//    /**
//     * A function to add fragment and replace it with current fragment
//     * this function is usually used in OnNavigationItemSelectedListener
//     *
//     * @param   fragment what fragment you will replace in layout
//     * @return  void     this function is doesn't need to return something
//     *
//     */
//    private fun addFragment(fragment: Fragment) {
//        supportFragmentManager
//                .beginTransaction()
////                TODO: add animation maybe?
////                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//                .replace(R.id.container, fragment, fragment.javaClass.simpleName)
//                .commit()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_item_settings ) startActivity(Intent(this, SettingActivity::class.java))
//        return super.onOptionsItemSelected(item)
//    }
//
//    public fun setActionBarTitle(title: String ) {
//        supportActionBar?.title = title
//    }
//}
//
//package com.dicoding.submissionMade
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import com.dicoding.submissionMade.activity.SettingActivity
//import com.dicoding.submissionMade.fragment.FavoriteFragment
//import com.dicoding.submissionMade.fragment.MovieFragment
//import com.dicoding.submissionMade.fragment.TvShowFragment
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import java.util.*
//
//class MainActivity : AppCompatActivity() {
//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        val fragment: Fragment
//        when (item.itemId) {
//            R.id.navigation_movie -> {
//                fragment = MovieFragment()
//                supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
//                        .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_tv_show -> {
//                fragment = TvShowFragment()
//                supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
//                        .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_favorites -> {
//                fragment = FavoriteFragment()
//                supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
//                        .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        if (savedInstanceState == null) {
//            navView.selectedItemId = R.id.navigation_movie
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.menu_item_settings) {
//            val settingIntent = Intent(this, SettingActivity::class.java)
//            startActivity(settingIntent)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    fun setActionBarTitle(title: String?) {
//        Objects.requireNonNull(supportActionBar).title = title
//    }
//}