package nifteam.kotlinuserdemoapp2

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.nifty.cloud.mb.core.NCMB
import kotlinx.android.synthetic.main.activity_main.*
import nifteam.kotlinuserdemoapp2.Fragment.AnonymousFragment
import nifteam.kotlinuserdemoapp2.Fragment.EmailPwdFragment
import nifteam.kotlinuserdemoapp2.Fragment.IDPwdFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var prevMenuItem: MenuItem? = null
    private var idPwdFragment: IDPwdFragment? = null
    private var emailPwdFragment: EmailPwdFragment? = null
    private var anonymousFragment: AnonymousFragment? = null
    internal var title: TextView? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setNavigationTitle(0, title!!)
                viewpager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                setNavigationTitle(1, title!!)
                viewpager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                setNavigationTitle(2, title!!)
                viewpager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NCMB.initialize(this.applicationContext, "「YOUR_NCMB_APPLICATION_KEY」", "「YOUR_NCMB_CLIENT_KEY」")

        setContentView(R.layout.activity_main)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.actionbar)
        title = findViewById<View>(resources.getIdentifier("action_bar_title", "id", packageName)) as TextView
        title!!.text = resources.getText(R.string.id_pw_title)


        for (i in 0 until navigation.childCount) {
            val viewItem = navigation.getChildAt(i)
            viewItem.y = -32f
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    navigation.menu.getItem(0).isChecked = false
                    setNavigationTitle(0, title!!)
                }
                navigation.menu.getItem(position).isChecked = true
                setNavigationTitle(position, title!!)
                prevMenuItem = navigation.menu.getItem(position)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        //Disable ViewPager Swipe
        viewpager.setOnTouchListener({ _, _ -> true })
        setupViewPager(viewpager)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith("android.webkit.")) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                (Objects.requireNonNull(this.getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager).hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        idPwdFragment = IDPwdFragment()
        emailPwdFragment = EmailPwdFragment()
        anonymousFragment = AnonymousFragment()
        adapter.addFragment(idPwdFragment!!)
        adapter.addFragment(emailPwdFragment!!)
        adapter.addFragment(anonymousFragment!!)
        viewPager.adapter = adapter
    }

    private fun setNavigationTitle(position: Int, textView: TextView) {
        when (position) {
            0 -> textView.text = resources.getText(R.string.id_pw_title)
            1 -> textView.text = resources.getText(R.string.email_pw_title)
            2 -> textView.text = resources.getText(R.string.anonymous_title)
        }
    }
}
