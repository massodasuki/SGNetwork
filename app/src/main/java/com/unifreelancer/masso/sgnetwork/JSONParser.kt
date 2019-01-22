package com.unifreelancer.masso.sgnetwork

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

@Suppress("DEPRECATION")

class JSONParser(private var c: Context, private var jsonData: String, private var myGridView: GridView) : AsyncTask<Void, Void, Boolean>() {

    private lateinit var pd: ProgressDialog

    private var records = ArrayList<Records>()

    private var volumeArr = arrayOfNulls<Double>(100)
    private var yearArr = arrayOfNulls<String>(100)

    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Parse JSON")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        pd.dismiss()
        if (isParsed!!) {
            //BIND
            myGridView.adapter = mAdapter(c, records)
        } else {
            Toast.makeText(c, "Unable To Parse that data. It is a valid JSON DATA? JsonException was raised. Check Log Output.", Toast.LENGTH_LONG).show()
            Toast.makeText(c, "This is the data we try to parse :  "+ jsonData, Toast.LENGTH_LONG).show()
        }
    }


    /*
    Parse JSON data
     */
    private fun parse(): Boolean {
        try {

            val jsonObject = JSONObject(jsonData)
            var jsonArray: JSONArray

            val jsonRaw = jsonObject.optJSONObject("result");
            jsonArray = jsonRaw.getJSONArray("records")

            records.clear()
            var record: Records

            //Convert jsonArray to Array
            yearArr = saveYearToArray(jsonArray)
            //Log.d("TAG", "MA Algorithm $yearArr")

             volumeArr = saveVolumeToArray(jsonArray)
            //Log.d("TAG", "MA Algorithm $volumeArr")

            // Calculate Total Volume
            var arraySize = jsonArray.length() - 14
            val (currentYearArr, totalVolumeArr)= calculateVolumeTotal(yearArr, volumeArr, arraySize)

            // Check Decrease
            var yearArrayTest = arrayOfNulls<String>(100)
            var volumeArrayTest = arrayOfNulls<Double>(100)

            val (decrease, decrYear) = analysesDecreasing(yearArr,volumeArr, arraySize)

            arraySize = currentYearArr.size
            var decrBoolean = false

            for(i in 0 until arraySize) {

                var year = currentYearArr[i]?:"Undefined"
                var volume = totalVolumeArr[i]?:0.0

                //Log.d("TAG", "Year Loop $year")
                //Log.d("TAG", "volume Loop $volume")

                for(j in 0 until decrease.size) {

                    if (currentYearArr[i] == decrYear[j])
                    {
                        decrBoolean = decrease[j]?:false
                        var test = decrYear[j]

                        //Log.d("TAG", "Boolean $decrBoolean")
                        //Log.d("TAG", "Year $test")
                    }
                }

                record = Records(year, volume, decrBoolean)
                this.records.add(record)

            }





            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }

    // Object of result
    class Records(private var m_Year:String, private var m_Volume: Double, private var m_Boolean: Boolean) {

        fun getYear(): String {
            return m_Year
        }

        fun getVolume(): Double {
            return m_Volume
        }

        fun getBoolean(): Boolean {
            return m_Boolean
        }

    }
    class mAdapter(private var c: Context, private var records: ArrayList<Records>) : BaseAdapter() {

        override fun getCount(): Int {
            return records.size
        }

        override fun getItem(pos: Int): Any {
            return records[pos]
        }

        override fun getItemId(pos: Int): Long {
            return pos.toLong()
        }

        /*
        Inflate row_model.xml and return it
         */
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var convertView = view
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.row_model, viewGroup, false)
            }

            val yearTxt = convertView!!.findViewById<TextView>(R.id.yearTxt) as TextView
            val volumeTxt = convertView.findViewById<TextView>(R.id.volumeTxt) as TextView
            val statusTxt = convertView.findViewById<TextView>(R.id.statusTxt) as TextView
            val imgView = convertView.findViewById(R.id.imageView) as ImageView

            val record = this.getItem(i) as Records

            yearTxt.text =  record.getYear()
            volumeTxt.text = record.getVolume().toString()


            if(record.getBoolean()) {
                //imgView.visibility = VISIBLE
                imgView.setImageResource(R.drawable.decrease)
                statusTxt.text = "Decrease"
            }
            else
            {
                //imgView.visibility = GONE
                imgView.setImageResource(R.drawable.increase)
                statusTxt.text = "Increase"
            }


            convertView.setOnClickListener { Toast.makeText(c,record.getVolume().toString(),Toast.LENGTH_SHORT).show() }

            return convertView
        }
    }
}