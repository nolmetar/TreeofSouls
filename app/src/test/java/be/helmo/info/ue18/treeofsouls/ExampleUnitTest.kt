package be.helmo.info.ue18.treeofsouls


import be.helmo.info.ue18.treeofsouls.utils.DateTypeConvertor
import org.junit.Test

import org.junit.Assert.*
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun toDateTest(){
        val dateTypeConvertor = DateTypeConvertor()

        assertEquals(Date(1999,10,27), dateTypeConvertor.toDate(19991027))


    }
}