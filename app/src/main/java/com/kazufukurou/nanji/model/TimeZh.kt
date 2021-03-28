/*
 * Copyright 2021 Artyom Mironov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kazufukurou.nanji.model

import java.util.Calendar
import java.util.Locale

class TimeZh(private val simplified: Boolean) : Time {
  override fun getPercentText(value: Int, digits: Boolean): String = convert(value, digits) + '％'

  override fun getTimeText(cal: Calendar, digits: Boolean, twentyFour: Boolean): String {
    val ampm = when {
      twentyFour -> ""
      cal.hourOfDay == 0 -> "午夜"
      cal.hourOfDay == 12 -> "中午"
      cal.ampm == Calendar.AM -> "上午"
      else -> "下午"
    }
    val hour = convertTime(if (twentyFour) cal.hourOfDay else cal.hour12, digits)
    val minute = convertTime(cal.minute, digits)
    val hourSuffix = if (simplified) "点" else "點"
    return String.format("%s%s%s%s分", ampm, hour, hourSuffix, minute)
  }

  override fun getDateText(cal: Calendar, digits: Boolean, era: Boolean): String {
    val year = convert(cal.year, digits)
    val month = convert(cal.monthNum, digits)
    val day = convert(cal.day, digits)
    val weekday = cal.weekday(Locale.CHINESE)
    return String.format("%s年%s月%s日%s", year, month, day, weekday)
  }

  private fun convertTime(num: Int, digits: Boolean): String {
    val isWordTwo = num == 2 && !digits
    return when {
      isWordTwo && simplified -> "两"
      isWordTwo -> "兩"
      else -> convert(num, digits)
    }
  }

  private fun convert(num: Int, digits: Boolean): String = if (digits) "$num" else num.toWordsCJK(Int::kanji)
}