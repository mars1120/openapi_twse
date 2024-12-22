package com.stock.twse.utils

import StockDayAvgAll
import StockDayAvgAllItem
import com.stock.twse.StockDayAll

//time complexity:O(n + m)  space Complexity:O(1)
object ArrayUtils {
    fun mergeSortedArrays(array1: StockDayAll?, array2: StockDayAvgAll?): StockDayAvgAll? {
        val result = mutableListOf<StockDayAvgAllItem>()
        var i = 0
        var j = 0
        if (array1 == null || array2 == null)
            return null
        while (i < array1.size && j < array2.size) {
            val code1 = array1[i].Code
            val code2 = array2[j].Code

            when {
                code1 == code2 -> {
                    result.add(
                        array2[j]
                    )
                    i++
                    j++
                }

                code1 < code2 -> i++
                else -> j++
            }
        }

        return result
    }
}