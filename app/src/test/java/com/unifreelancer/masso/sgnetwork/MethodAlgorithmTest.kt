package com.unifreelancer.masso.sgnetwork

import org.json.JSONArray
import org.junit.Test

import org.junit.Assert.*
import org.json.JSONObject
import org.json.JSONException



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MethodAlgorithmTest {

    @Test
    fun isSaveVolumeJSONtoArrayValid() {


        val data_a = JSONObject()
        try {
            data_a.put("quater", "2016")
            data_a.put("volume_of_mobile_data", "50")


        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


        val data_b = JSONObject()
        try {
            data_b.put("quater", "2017")
            data_b.put("volume_of_mobile_data", "100")


        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        val jsonArray = JSONArray()

        jsonArray.put(data_a)
        jsonArray.put(data_b)


        val expected = arrayOf(50,100)

        assertNotEquals(expected, saveVolumeToArray(jsonArray))

    }

    @Test
    fun isSaveYearJSONtoArrayValid() {


        val data_a = JSONObject()
        try {
            data_a.put("quater", "2016")
            data_a.put("volume_of_mobile_data", "50")


        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


        val data_b = JSONObject()
        try {
            data_b.put("quater", "2017")
            data_b.put("volume_of_mobile_data", "100")


        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        val jsonArray = JSONArray()

        jsonArray.put(data_a)
        jsonArray.put(data_b)


        val expected = arrayOf("2016","2017")
            assertNotEquals(expected, saveYearToArray(jsonArray))

    }

    //fun volumeTotal(yearArray: Array<String>, volumeArray: Array<Double>, length: Int): Pair<String, Double>

    @Test
    fun isTotalVolumeAndYearValid() {

        var yearArray = arrayOfNulls<String>(100)
        var volumeArray = arrayOfNulls<Double>(100)

        yearArray.set(0,"2016")
        yearArray.set(1,"2017")

        volumeArray.set(0,10.52)
        volumeArray.set(1,50.0)

        var length = 1;

        val expected = arrayOf("2016",10.52, "2017", 50.0)
            assertNotEquals(expected, calculateVolumeTotal(yearArray, volumeArray,length))

    }


    @Test
    fun isDecreasing() {



        var yearArrayTest = arrayOfNulls<String>(100)
        var volumeArrayTest = arrayOfNulls<Double>(100)

        var length = 100;

        yearArrayTest.set(0,"2018")
        yearArrayTest.set(1,"2018")
        yearArrayTest.set(2,"2018")
        yearArrayTest.set(3,"2018")

        volumeArrayTest.set(0,100.0)
        volumeArrayTest.set(1,70.0)
        volumeArrayTest.set(2,60.0)
        volumeArrayTest.set(3,50.0)

        var boolYear = arrayOfNulls<String>(100)
        var boolean = arrayOfNulls<Boolean>(100)

        boolYear.set(0,"2018")
        boolYear.set(1,null)
        boolYear.set(2,"2018")

        boolean.set(0,true)
        boolean.set(1,null)
        boolean.set(2,true)


        val expected = Pair(boolean, boolYear)
        assertNotEquals(expected, analysesDecreasing(yearArrayTest, volumeArrayTest,length))

    }



}
