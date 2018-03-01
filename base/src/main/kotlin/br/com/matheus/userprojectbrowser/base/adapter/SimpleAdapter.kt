package br.com.matheus.userprojectbrowser.base.adapter

import android.content.Context
import android.view.View
import br.com.matheus.userprojectbrowser.base.BaseRecyclerAdapter
import br.com.matheus.userprojectbrowser.base.ViewBinder

class SimpleAdapter<MODEL, VIEW>(creator: (context: Context) -> VIEW) :
        BaseRecyclerAdapter<MODEL>({ context, _ -> creator.invoke(context) })
        where VIEW : View, VIEW : ViewBinder<MODEL>