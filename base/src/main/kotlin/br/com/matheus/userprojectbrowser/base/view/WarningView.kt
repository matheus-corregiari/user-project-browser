package br.com.matheus.userprojectbrowser.base.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.matheus.userprojectbrowser.base.R
import br.com.matheus.userprojectbrowser.base.delegate.viewProvider
import br.com.matheus.userprojectbrowser.base.extension.obtain
import br.com.matheus.userprojectbrowser.base.extension.setCustomTextAppearance

class WarningView : LinearLayout {

    private val title: TextView by viewProvider(R.id.title_view)
    private val subtitle: TextView by viewProvider(R.id.subtitle_view)
    private val action: Button by viewProvider(R.id.action_view)
    private val icon: ImageView by viewProvider(R.id.icon_view)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.warningViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(context.obtainStyledAttributes(attrs, R.styleable.WarningView, defStyleAttr, defStyleRes))
    }

    init {
        orientation = VERTICAL
        View.inflate(context, R.layout.view_warning, this)
    }

    private fun setup(typedArray: TypedArray) = typedArray.obtain {
        val iconRes = getResourceId(R.styleable.WarningView_icon, 0)
        icon.visibility = if (iconRes == 0) GONE else VISIBLE
        icon.setImageResource(iconRes)
        if (hasValue(R.styleable.WarningView_iconColor))
            icon.setColorFilter(getColor(R.styleable.WarningView_iconColor, Color.BLACK))

        title.setCustomTextAppearance(getResourceId(R.styleable.WarningView_titleTextAppearance, 0))
        subtitle.setCustomTextAppearance(getResourceId(R.styleable.WarningView_subtitleTextAppearance, 0))

        setupText(getString(R.styleable.WarningView_title), title)
        setupText(getString(R.styleable.WarningView_subtitle), subtitle)
        setupText(getString(R.styleable.WarningView_actionTitle), action)
    }

    override fun setOnClickListener(listener: OnClickListener?) = action.setOnClickListener(listener)

    private fun setupText(text: String?, view: TextView) {
        view.visibility = if (text.isNullOrEmpty()) GONE else VISIBLE
        view.text = text
    }

}