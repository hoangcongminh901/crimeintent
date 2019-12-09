package com.gamecodeschool.crimeintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"
private const val NORMAL_ROW = 0
private const val POLICE_ROW = 1

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel : CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
        crimeRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var crime: Crime

        private val titleTextView = itemView.findViewById<View>(R.id.crime_title) as TextView
        private val dateTextView = itemView.findViewById<View>(R.id.crime_date) as TextView
        private val solvedImageView = itemView.findViewById<View>(R.id.crime_solved) as ImageView


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.US).format(this.crime.date)
            solvedImageView.visibility = if (crime.isSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }
//
//    private inner class PoliceHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private lateinit var crime: Crime
//
//        val titleTextView = itemView.findViewById<View>(R.id.crime_police_title) as TextView
//        val dateTextView = itemView.findViewById<View>(R.id.crime_police_date) as TextView
//        val button = itemView.findViewById<View>(R.id.button) as Button
//
//        init {
//            button.setOnClickListener {
//                Toast.makeText(context, "contacted police", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        fun bind(crime: Crime) {
//            this.crime = crime
//            titleTextView.text = this.crime.title
//            dateTextView.text = this.crime.date.toString()
//        }
//    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

//        override fun getItemViewType(position: Int) = if (crimes[position].policeRequired) POLICE_ROW else NORMAL_ROW

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            return CrimeHolder(layoutInflater.inflate(R.layout.list_item_crime, parent, false))
//            } else {
//                PoliceHolder(layoutInflater.inflate(R.layout.list_item_crime_police, parent, false))
//            }
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
//            if (getItemViewType(position) == POLICE_ROW) {
//                (holder as PoliceHolder).bind(crime)
//            } else {
//                (holder as CrimeHolder).bind(crime)
//            }
        }

        override fun getItemCount() = crimes.size
    }
}