package com.example.tuner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class TuneListElementAdapter(
    private val context: Context,
    private val dataSource: ArrayList<TunerListView>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

        val chooseButton = rowView.findViewById(R.id.tuning_list_element_choose_button) as Button

        chooseButton.setOnClickListener {

            DbBasicTunerHandler(context).setTheHighestOrder(dataSource[position].id!!)
            DbInstrumentHandler(context).setTheHighestOrder(dataSource[position].instrumentId)

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        val deleteButton =
            rowView.findViewById(R.id.tuning_list_element_delete_button) as Button

        deleteButton.setOnClickListener {
            if (dataSource[position].id !== null) {
                DbBasicTunerHandler(context).deleteById(dataSource[position].id!!)
            }

            dataSource.remove(dataSource[position])

            notifyDataSetChanged()
        }

        val editButton = rowView.findViewById(R.id.tuning_list_element_edit_button) as Button

        editButton.setOnClickListener {
            val intent = Intent(context, AddEditActivity::class.java)
            intent.putExtra("tuning_id", dataSource[position].id())
            context.startActivity(intent)
        }

        if (!dataSource[position].customTuning()) {
            deleteButton.visibility = View.INVISIBLE
            editButton.visibility = View.INVISIBLE
        }

        val tuner = getItem(position) as TunerListView

        titleTextView.text = tuner.name()
        subtitleTextView.text = tuner.tuning()

        return rowView
    }
}
