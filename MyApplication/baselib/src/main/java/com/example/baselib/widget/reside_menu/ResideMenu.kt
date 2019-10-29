package com.example.baselib.widget.reside_menu

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.*
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.baselib.R
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator
import com.nineoldandroids.view.ViewHelper


class ResideMenu : FrameLayout {

    companion object{
        const val DIRECTION_LEFT = 0
        const val DIRECTION_RIGHT = 1
    }
    private val PRESSED_MOVE_HORIZONTAL = 2
    private val PRESSED_DOWN = 3
    private val PRESSED_DONE = 4
    private val PRESSED_MOVE_VERTICAL = 5

    private var imageViewShadow: ImageView? = null
    private var imageViewBackground: ImageView? = null
    private var layoutLeftMenu: LinearLayout? = null
    private var layoutRightMenu: LinearLayout? = null
    private var scrollViewLeftMenu: View? = null
    private var scrollViewRightMenu: View? = null
    private var scrollViewMenu: View? = null
    /**
     * Current attaching activity.
     */
    private var activity: Activity? = null
    /**
     * The DecorView of current activity.
     */
    private var viewDecor: ViewGroup? = null
    private var viewActivity: TouchDisableView? = null
    /**
     * The flag of menu opening status.
     */
    private var isOpened: Boolean = false
    private var shadowAdjustScaleX: Float = 0.toFloat()
    private var shadowAdjustScaleY: Float = 0.toFloat()
    /**
     * Views which need stop to intercept touch events.
     */
    private var ignoredViews: MutableList<View>? = null
    private var leftMenuItems: MutableList<ResideMenuItem>? = null
    private var rightMenuItems: MutableList<ResideMenuItem>? = null
    private val displayMetrics = DisplayMetrics()
    private var menuListener: OnMenuListener? = null
    private var lastRawX: Float = 0.toFloat()
    private var isInIgnoredView = false
    private var scaleDirection = DIRECTION_LEFT
    private var pressedState = PRESSED_DOWN
    private val disabledSwipeDirection = ArrayList<Int>()
    // Valid scale factor is between 0.0f and 1.0f.
    private var mScaleValue = 0.5f

    private var mUse3D: Boolean = false
    private val ROTATE_Y_ANGLE = 10


    /**
     * This constructor provides you to create menus with your own custom
     * layouts, but if you use custom menu then do not call addMenuItem because
     * it will not be able to find default views
     */
    constructor(context: Context) : super(context) {
        initViews(context, -1, -1)
    }

    constructor(context: Context, customLeftMenuId: Int, customRightMenuId: Int) : super(context) {
        initViews(context, customLeftMenuId, customRightMenuId)
    }

    private fun initViews(
        context: Context, customLeftMenuId: Int,
        customRightMenuId: Int
    ) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.residemenu_custom, this)

        if (customLeftMenuId >= 0) {
            scrollViewLeftMenu = inflater.inflate(customLeftMenuId, this, false)
        } else {
            scrollViewLeftMenu = inflater.inflate(
                R.layout.residemenu_custom_left_scrollview, this, false
            )
            layoutLeftMenu = scrollViewLeftMenu!!.findViewById(R.id.layout_left_menu)
        }

        if (customRightMenuId >= 0) {
            scrollViewRightMenu = inflater.inflate(customRightMenuId, this, false)
        } else {
            scrollViewRightMenu = inflater.inflate(
                R.layout.residemenu_custom_right_scrollview, this, false
            )
            layoutRightMenu = scrollViewRightMenu!!.findViewById(R.id.layout_right_menu)
        }

        imageViewShadow = findViewById(R.id.iv_shadow) as ImageView
        imageViewBackground = findViewById(R.id.iv_background) as ImageView

        val menuHolder = findViewById(R.id.sv_menu_holder) as RelativeLayout
        menuHolder.addView(scrollViewLeftMenu)
        menuHolder.addView(scrollViewRightMenu)
    }

    /**
     * Returns left menu view so you can findViews and do whatever you want with
     */
    fun getLeftMenuView(): View? {
        return scrollViewLeftMenu
    }

    /**
     * Returns right menu view so you can findViews and do whatever you want with
     */
    fun getRightMenuView(): View? {
        return scrollViewRightMenu
    }

     override fun fitSystemWindows(insets: Rect): Boolean {
        // Applies the content insets to the view's padding, consuming that
        // content (modifying the insets to be 0),
        // and returning true. This behavior is off by default and can be
        // enabled through setFitsSystemWindows(boolean)
        // in api14+ devices.

        // This is added to fix soft navigationBar's overlapping to content above LOLLIPOP
        var bottomPadding = viewActivity!!.getPaddingBottom() + insets.bottom
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
        if (!hasBackKey || !hasHomeKey) {//there's a navigation bar
            bottomPadding += getNavigationBarHeight()
        }

        this.setPadding(
            viewActivity!!.getPaddingLeft() + insets.left,
            viewActivity!!.getPaddingTop() + insets.top,
            viewActivity!!.getPaddingRight() + insets.right,
            bottomPadding
        )
        insets.bottom = 0
        insets.right = insets.bottom
        insets.top = insets.right
        insets.left = insets.top
        return true
    }

    private fun getNavigationBarHeight(): Int {
        val resources = getResources()
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * Set up the activity;
     *
     * @param activity
     */
    fun attachToActivity(activity: Activity) {
        initValue(activity)
        setShadowAdjustScaleXByOrientation()
        viewDecor!!.addView(this, 0)
    }

    private fun initValue(activity: Activity) {
        this.activity = activity
        leftMenuItems = ArrayList<ResideMenuItem>()
        rightMenuItems = ArrayList<ResideMenuItem>()
        ignoredViews = ArrayList<View>()
        viewDecor = activity.window.decorView as ViewGroup
        viewActivity = TouchDisableView(activity)

        val mContent = viewDecor!!.getChildAt(0)
        viewDecor!!.removeViewAt(0)
        viewActivity!!.setContent(mContent)
        addView(viewActivity)

        val parent = scrollViewLeftMenu!!.getParent() as ViewGroup
        parent.removeView(scrollViewLeftMenu)
        parent.removeView(scrollViewRightMenu)
    }

    private fun setShadowAdjustScaleXByOrientation() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            shadowAdjustScaleX = 0.034f
            shadowAdjustScaleY = 0.12f
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            shadowAdjustScaleX = 0.06f
            shadowAdjustScaleY = 0.07f
        }
    }

    /**
     * Set the background image of menu;
     *
     * @param imageResource
     */
    fun setBackground(imageResource: Int) {
        imageViewBackground!!.setImageResource(imageResource)
    }

    /**
     * The visibility of the shadow under the activity;
     *
     * @param isVisible
     */
    fun setShadowVisible(isVisible: Boolean) {
        if (isVisible)
            imageViewShadow!!.setBackgroundResource(R.drawable.shadow)
        else
            imageViewShadow!!.setBackgroundResource(0)
    }

    /**
     * Add a single item to the left menu;
     *
     *
     * WARNING: It will be removed from v2.0.
     *
     * @param menuItem
     */
    @Deprecated("")
    fun addMenuItem(menuItem: ResideMenuItem) {
        this.leftMenuItems!!.add(menuItem)
        layoutLeftMenu!!.addView(menuItem)
    }

    /**
     * Add a single items;
     *
     * @param menuItem
     * @param direction
     */
    fun addMenuItem(menuItem: ResideMenuItem, direction: Int) {
        if (direction == DIRECTION_LEFT) {
            this.leftMenuItems!!.add(menuItem)
            layoutLeftMenu!!.addView(menuItem)
        } else {
            this.rightMenuItems!!.add(menuItem)
            layoutRightMenu!!.addView(menuItem)
        }
    }

    /**
     * WARNING: It will be removed from v2.0.
     *
     * @param menuItems
     */
    @Deprecated("")
    fun setMenuItems(menuItems: MutableList<ResideMenuItem>) {
        this.leftMenuItems = menuItems
        rebuildMenu()
    }

    /**
     * Set menu items by a array;
     *
     * @param menuItems
     * @param direction
     */
    fun setMenuItems(menuItems: MutableList<ResideMenuItem>, direction: Int) {
        if (direction == DIRECTION_LEFT)
            this.leftMenuItems = menuItems
        else
            this.rightMenuItems = menuItems
        rebuildMenu()
    }

    private fun rebuildMenu() {
        if (layoutLeftMenu != null) {
            layoutLeftMenu!!.removeAllViews()
            for (leftMenuItem in leftMenuItems!!)
                layoutLeftMenu!!.addView(leftMenuItem)
        }

        if (layoutRightMenu != null) {
            layoutRightMenu!!.removeAllViews()
            for (rightMenuItem in rightMenuItems!!)
                layoutRightMenu!!.addView(rightMenuItem)
        }
    }

    /**
     * WARNING: It will be removed from v2.0.
     *
     * @return
     */
    @Deprecated("")
    fun getMenuItems(): List<ResideMenuItem>? {
        return leftMenuItems
    }

    /**
     * Return instances of menu items;
     *
     * @return
     */
    fun getMenuItems(direction: Int): List<ResideMenuItem>? {
        return if (direction == DIRECTION_LEFT)
            leftMenuItems
        else
            rightMenuItems
    }

    /**
     * If you need to do something on closing or opening menu,
     * set a listener here.
     *
     * @return
     */
    fun setMenuListener(menuListener: OnMenuListener) {
        this.menuListener = menuListener
    }


    fun getMenuListener(): OnMenuListener? {
        return menuListener
    }

    /**
     * Show the menu;
     */
    fun openMenu(direction: Int) {

        setScaleDirection(direction)

        isOpened = true
        val scaleDown_activity = buildScaleDownAnimation(viewActivity, mScaleValue, mScaleValue)
        val scaleDown_shadow = buildScaleDownAnimation(
            imageViewShadow,
            mScaleValue + shadowAdjustScaleX, mScaleValue + shadowAdjustScaleY
        )
        val alpha_menu = buildMenuAnimation(scrollViewMenu, 1.0f)
        scaleDown_shadow.addListener(animationListener)
        scaleDown_activity.playTogether(scaleDown_shadow)
        scaleDown_activity.playTogether(alpha_menu)
        scaleDown_activity.start()
    }

    /**
     * Close the menu;
     */
    fun closeMenu() {

        isOpened = false
        val scaleUp_activity = buildScaleUpAnimation(viewActivity!!, 1.0f, 1.0f)
        val scaleUp_shadow = buildScaleUpAnimation(imageViewShadow!!, 1.0f, 1.0f)
        val alpha_menu = buildMenuAnimation(scrollViewMenu, 0.0f)
        scaleUp_activity.addListener(animationListener)
        scaleUp_activity.playTogether(scaleUp_shadow)
        scaleUp_activity.playTogether(alpha_menu)
        scaleUp_activity.start()
    }

    @Deprecated("")
    fun setDirectionDisable(direction: Int) {
        disabledSwipeDirection.add(direction)
    }

    fun setSwipeDirectionDisable(direction: Int) {
        disabledSwipeDirection.add(direction)
    }

    private fun isInDisableDirection(direction: Int): Boolean {
        return disabledSwipeDirection.contains(direction)
    }

    private fun setScaleDirection(direction: Int) {

        val screenWidth = getScreenWidth()
        val pivotX: Float
        val pivotY = getScreenHeight() * 0.5f

        if (direction == DIRECTION_LEFT) {
            scrollViewMenu = scrollViewLeftMenu
            pivotX = screenWidth * 1.5f
        } else {
            scrollViewMenu = scrollViewRightMenu
            pivotX = screenWidth * -0.5f
        }

        ViewHelper.setPivotX(viewActivity!!, pivotX)
        ViewHelper.setPivotY(viewActivity!!, pivotY)
        ViewHelper.setPivotX(imageViewShadow!!, pivotX)
        ViewHelper.setPivotY(imageViewShadow!!, pivotY)
        scaleDirection = direction
    }

    /**
     * return the flag of menu status;
     *
     * @return
     */
    fun isOpened(): Boolean {
        return isOpened
    }

    private val viewActivityOnClickListener = OnClickListener { if (isOpened()) closeMenu() }

    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            if (isOpened()) {
                showScrollViewMenu(scrollViewMenu)
                if (menuListener != null)
                    menuListener!!.openMenu()
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            // reset the view;
            if (isOpened()) {
                viewActivity!!.setTouchDisable(true)
                viewActivity!!.setOnClickListener(viewActivityOnClickListener)
            } else {
                viewActivity!!.setTouchDisable(false)
                viewActivity!!.setOnClickListener(null)
                hideScrollViewMenu(scrollViewLeftMenu)
                hideScrollViewMenu(scrollViewRightMenu)
                if (menuListener != null)
                    menuListener!!.closeMenu()
            }
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }
    }

    /**
     * A helper method to build scale down animation;
     *
     * @param target
     * @param targetScaleX
     * @param targetScaleY
     * @return
     */
    private fun buildScaleDownAnimation(target: View?, targetScaleX: Float, targetScaleY: Float): AnimatorSet {

        val scaleDown = AnimatorSet()
        scaleDown.playTogether(
            ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
        )

        if (mUse3D) {
            val angle = if (scaleDirection == DIRECTION_LEFT) -ROTATE_Y_ANGLE else ROTATE_Y_ANGLE
            scaleDown.playTogether(ObjectAnimator.ofFloat(target, "rotationY", angle.toFloat()))
        }

        scaleDown.setInterpolator(
            AnimationUtils.loadInterpolator(
                activity,
                android.R.anim.decelerate_interpolator
            )
        )
        scaleDown.setDuration(250)
        return scaleDown
    }

    /**
     * A helper method to build scale up animation;
     *
     * @param target
     * @param targetScaleX
     * @param targetScaleY
     * @return
     */
    private fun buildScaleUpAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {

        val scaleUp = AnimatorSet()
        scaleUp.playTogether(
            ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
        )

        if (mUse3D) {
            scaleUp.playTogether(ObjectAnimator.ofFloat(target, "rotationY",0.toFloat()))
        }

        scaleUp.duration = 250
        return scaleUp
    }

    private fun buildMenuAnimation(target: View?, alpha: Float): AnimatorSet {

        val alphaAnimation = AnimatorSet()
        alphaAnimation.playTogether(
            ObjectAnimator.ofFloat(target, "alpha", alpha)
        )

        alphaAnimation.setDuration(250)
        return alphaAnimation
    }

    /**
     * If there were some view you don't want reside menu
     * to intercept their touch event, you could add it to
     * ignored views.
     *
     * @param v
     */
    fun addIgnoredView(v: View) {
        ignoredViews!!.add(v)
    }

    /**
     * Remove a view from ignored views;
     *
     * @param v
     */
    fun removeIgnoredView(v: View) {
        ignoredViews!!.remove(v)
    }

    /**
     * Clear the ignored view list;
     */
    fun clearIgnoredViewList() {
        ignoredViews!!.clear()
    }

    /**
     * If the motion event was relative to the view
     * which in ignored view list,return true;
     *
     * @param ev
     * @return
     */
    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()
        for (v in ignoredViews!!) {
            v.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt()))
                return true
        }
        return false
    }

    private fun setScaleDirectionByRawX(currentRawX: Float) {
        if (currentRawX < lastRawX)
            setScaleDirection(DIRECTION_RIGHT)
        else
            setScaleDirection(DIRECTION_LEFT)
    }

    private fun getTargetScale(currentRawX: Float): Float {
        var scaleFloatX = (currentRawX - lastRawX) / getScreenWidth() * 0.75f
        scaleFloatX = if (scaleDirection == DIRECTION_RIGHT) -scaleFloatX else scaleFloatX

        var targetScale = ViewHelper.getScaleX(viewActivity!!) - scaleFloatX
        targetScale = if (targetScale > 1.0f) 1.0f else targetScale
        targetScale = if (targetScale < 0.5f) 0.5f else targetScale
        return targetScale
    }

    private var lastActionDownX: Float = 0.toFloat()
    private var lastActionDownY:Float = 0.toFloat()

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentActivityScaleX = ViewHelper.getScaleX(viewActivity)
        if (currentActivityScaleX == 1.0f)
            setScaleDirectionByRawX(ev.rawX)

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                isInIgnoredView = isInIgnoredView(ev) && !isOpened()
                pressedState = PRESSED_DOWN
            }

            MotionEvent.ACTION_MOVE -> {
                if (isInIgnoredView || isInDisableDirection(scaleDirection))
                   return false

                if (pressedState !== PRESSED_DOWN && pressedState !== PRESSED_MOVE_HORIZONTAL)
                    return false

                val xOffset = (ev.x - lastActionDownX)
                val yOffset = (ev.y - lastActionDownY)

                if (pressedState === PRESSED_DOWN) {
                    if (yOffset > 25 || yOffset < -25) {
                        pressedState = PRESSED_MOVE_VERTICAL
                        return false
                    }
                    if (xOffset < -50 || xOffset > 50) {
                        pressedState = PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                } else if (pressedState === PRESSED_MOVE_HORIZONTAL) {
                    if (currentActivityScaleX < 0.95)
                        showScrollViewMenu(scrollViewMenu)

                    val targetScale = getTargetScale(ev.rawX)
                    if (mUse3D) {
                        var angle = if (scaleDirection === DIRECTION_LEFT) -ROTATE_Y_ANGLE else ROTATE_Y_ANGLE
                        angle *= ((1 - targetScale) * 2).toInt()
                        ViewHelper.setRotationY(viewActivity, angle.toFloat())

                        ViewHelper.setScaleX(imageViewShadow, targetScale - shadowAdjustScaleX)
                        ViewHelper.setScaleY(imageViewShadow, targetScale - shadowAdjustScaleY)
                    } else {
                        ViewHelper.setScaleX(imageViewShadow, targetScale + shadowAdjustScaleX)
                        ViewHelper.setScaleY(imageViewShadow, targetScale + shadowAdjustScaleY)
                    }
                    ViewHelper.setScaleX(viewActivity, targetScale)
                    ViewHelper.setScaleY(viewActivity, targetScale)
                    ViewHelper.setAlpha(scrollViewMenu, (1 - targetScale) * 2.0f)

                    lastRawX = ev.rawX
                    return true
                }
            }

            MotionEvent.ACTION_UP -> {

                if (isInIgnoredView) breaking@
                if (pressedState !== PRESSED_MOVE_HORIZONTAL) breaking@

                pressedState = PRESSED_DONE
                if (isOpened()) {
                    if (currentActivityScaleX > 0.56f)
                        closeMenu()
                    else
                        openMenu(scaleDirection)
                } else {
                    if (currentActivityScaleX < 0.94f) {
                        openMenu(scaleDirection)
                    } else {
                        closeMenu()
                    }
                }
            }
        }
        lastRawX = ev.rawX
        return super.dispatchTouchEvent(ev)
    }

    fun getScreenHeight(): Int {
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun getScreenWidth(): Int {
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun setScaleValue(scaleValue: Float) {
        this.mScaleValue = scaleValue
    }

    fun setUse3D(use3D: Boolean) {
        mUse3D = use3D
    }

    interface OnMenuListener {

        /**
         * This method will be called at the finished time of opening menu animations.
         */
        fun openMenu()

        /**
         * This method will be called at the finished time of closing menu animations.
         */
        fun closeMenu()
    }

    private fun showScrollViewMenu(scrollViewMenu: View?) {
        if (scrollViewMenu != null && scrollViewMenu!!.getParent() == null) {
            addView(scrollViewMenu)
        }
    }

    private fun hideScrollViewMenu(scrollViewMenu: View?) {
        if (scrollViewMenu != null && scrollViewMenu!!.getParent() != null) {
            removeView(scrollViewMenu)
        }
    }
}
