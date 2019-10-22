package com.example.baselib.constants

interface SettingConst {
    interface LanguageType {
        companion object {
            val Chinese = "中文"
            val English = "English"
        }
    }

    interface LanguageSettingKey {
        companion object {
            val language_set_key = "language"
        }
    }
}