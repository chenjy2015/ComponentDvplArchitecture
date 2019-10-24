package com.example.baselib.bean

class LunarCalendarVO {
    lateinit var animal: String
    var bigMonth: Boolean = false
    lateinit var cnday: String
    lateinit var cnmonth: String
    lateinit var cnyear: String
    lateinit var cyclicalDay: String
    lateinit var cyclicalMonth: String
    lateinit var cyclicalYear: String
    var day: Int = 0
    lateinit var festivalList: List<Any>
    lateinit var hyear: String
    lateinit var jieqi: SolarTerms
    var leap: Boolean = false
    var lunarDay: Int = 0
    var lunarMonth: Int = 0
    var lunarYear: Int = 0
    lateinit var lunarYearString: String
    var maxDayInMonth: Int = 0
    var month: Int = 0
    lateinit var suit: String
    lateinit var taboo: String
    lateinit var week: String
    var year: Int = 0

    class SolarTerms(
        var `jieqi`: String,
        var `5`: String
    )
}