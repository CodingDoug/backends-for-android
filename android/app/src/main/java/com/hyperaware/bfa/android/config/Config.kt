package com.hyperaware.bfa.android.config

data class Config(
    val cloudRunRoot: String
) {
    companion object {
        private lateinit var ins: Config
        fun set(config: Config) {
            ins = config
        }
        fun get(): Config {
            return ins
        }
    }
}

