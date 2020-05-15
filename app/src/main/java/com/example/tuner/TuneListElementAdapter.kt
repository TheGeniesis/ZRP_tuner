package com.example.tuner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TuneListElementAdapter(private val context: Context,
                             private val dataSource: ArrayList<TunerListView>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }

    //2
    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    //3
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //4
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.tune_list_element, parent, false)

        val titleTextView = rowView.findViewById(R.id.tune_list_element_name) as TextView

        val subtitleTextView = rowView.findViewById(R.id.tune_list_element_tuning) as TextView

        val tuner = getItem(position) as TunerListView


        titleTextView.text = tuner.name()
        subtitleTextView.text = tuner.tuning()

        return rowView
    }
}
