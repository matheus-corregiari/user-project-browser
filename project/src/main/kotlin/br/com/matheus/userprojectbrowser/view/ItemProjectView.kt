package br.com.matheus.userprojectbrowser.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.matheus.userprojectbrowser.R
import br.com.matheus.userprojectbrowser.base.ViewBinder
import br.com.matheus.userprojectbrowser.base.delegate.viewProvider
import br.com.matheus.userprojectbrowser.base.extension.loadUrl
import br.com.matheus.userprojectbrowser.base.extension.obtain
import br.com.matheus.userprojectbrowser.base.extension.setCustomTextAppearance
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO

class ItemProjectView : RelativeLayout, ViewBinder<ProjectVO> {

    private val icon: ImageView by viewProvider(R.id.project_icon)
    private val name: TextView by viewProvider(R.id.project_name)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.itemProjectViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        setup(context.obtainStyledAttributes(attrs, R.styleable.ItemProjectView, defStyleAttr, defStyleRes))
    }

    init {
        id = R.id.item_product
        View.inflate(context, R.layout.view_item_project, this)
    }

    private fun setup(typedArray: TypedArray) = typedArray.obtain {
        name.setCustomTextAppearance(getResourceId(R.styleable.ItemProjectView_nameTextAppearance, 0))
    }

    override fun bind(model: ProjectVO) {
        icon.loadUrl(model.logo, R.drawable.ic_placeholder)
        name.text = model.name
    }

}