package com.unifreelancer.masso.sgnetwork

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject


    fun saveYearToArray( jsonAr: JSONArray): Array<String?> {

        val yearArray = arrayOfNulls<String>(100)

        //Create JSONObject variable for one quater
        lateinit var jsonObject: JSONObject

        var size = jsonAr.length()

        var yIndex = 0
        for (i in 14 until size) {

            jsonObject = jsonAr.getJSONObject(i)
            val jsonYear = jsonObject.getString("quarter")

            val sYear = jsonYear.toString()
            yearArray[yIndex] = sYear

            ++yIndex
        }

        return yearArray
    }

    fun saveVolumeToArray(jsonAr: JSONArray): Array<Double?> {

        var volumeArray = arrayOfNulls<Double>(100)

        //Create JSONObject variable for one quater
        lateinit var jsonObject: JSONObject

        var vIndex = 0
        for (i in 14 until jsonAr.length()) {

            jsonObject = jsonAr.getJSONObject(i)
            val jsonVolume = jsonObject.getString("volume_of_mobile_data")

            val dVolume = jsonVolume.toDouble()
            volumeArray[vIndex] = dVolume

            ++vIndex

        }

        return volumeArray
    }

    fun calculateVolumeTotal(yearArray: Array<String?>, volumeArray: Array<Double?>, mSize: Int): Pair<Array<String?>, Array<Double?>> {

        var totalVolume = 0.0
        var theYear = ""
        var count = 0
        var newIndex = 0

        var currentYearArray = arrayOfNulls<String>(100)
        var totalVolumeArray = arrayOfNulls<Double>(100)

                //Minus 14 to start from 2018
                for(i in 0 until mSize)
                {
                    var tempVol = volumeArray[i]?:0.0
                    var tempYear = yearArray[i]?:"Undefined"
                    theYear = tempYear.subSequence(0, 4) as String

                    if ( count < 3 ){

                        totalVolume += tempVol!!

                        currentYearArray[newIndex] = theYear
                        totalVolumeArray[newIndex] = totalVolume

                        ++count
                    }

                    else if ( count == 3 )
                    {
                        if (tempVol != null && tempYear != null) {

                            totalVolume += tempVol

                            currentYearArray[newIndex] = theYear
                            totalVolumeArray[newIndex] = totalVolume
                        }

                        count = 0
                        ++newIndex
                    }

                }

        return Pair(currentYearArray, totalVolumeArray)

    }

    // Comparison

    fun analysesDecreasing(yearArray: Array<String?>, volumeArray: Array<Double?>, size: Int): Pair<Array<Boolean?>, Array<String?>> {

        var currentYear = ""
        var avol = 0.0
        var bvol = 0.0
        var count = 0

        var decYearArray = arrayOfNulls<String>(100)
        var booleanArray = arrayOfNulls<Boolean>(100)


        //Minus 14 to start from 2018
        for(i in 0 until size)
        {

            var tempYear = yearArray[i]
            if (tempYear != null) {
                currentYear = tempYear.subSequence(0, 4) as String
            }

            //Log.d("TAG", "Inside Fun $currentYear")
            //Log.d("TAG", "Year $test")

            if ( count < 3 ){

                if (volumeArray[i] != null) {

                    avol = volumeArray[i]?: 0.0
                    bvol = volumeArray[i+1] ?: 0.0
                }

                if (avol > bvol)
                    {
                        booleanArray[i] = true
                        decYearArray[i] = currentYear
                    }
                if (avol < bvol)
                    {
                        booleanArray[i] = false
                        decYearArray[i] = currentYear
                    }



                ++count
            }

            else if ( count == 3 ){

                count = 0
            }


            var test = booleanArray[i]
            var test2 = decYearArray[i]

            Log.d("TAG", "Inside Fun $test and $test2")
        }


        return Pair(booleanArray, decYearArray)

    }

