package com.example.tuner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class TuningAddTuneListAdapter(private val context: Context,
                             private val dataSource: ArrayList<TuneModel>) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.tuning_add_tune_element, parent, false)

        val tuningName = rowView.findViewById(R.id.tuning_add_tune_tune) as TextView

        val tuningFrequency = rowView.findViewById(R.id.tuning_add_tune_frequency) as TextView

        val deleteTuningButton = rowView.findViewById(R.id.tuning_add_tune_delete_button) as Button

        val tuner = getItem(position) as TuneModel

        tuningName.text = tuner.name
        tuningFrequency.text = tuner.exactFrequency.toString()

        return rowView
    }
}
