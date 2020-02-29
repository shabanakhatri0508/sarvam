package com.sarvam.models

class BaseExtras {
    var apiError: String? = null
    var errorMsg: Int = -1
    var progressBar: Boolean? = null

    constructor(apiError: String? = null, errorMsg: Int, progressBar: Boolean) {
        this.apiError = apiError
        this.errorMsg = errorMsg
        this.progressBar = progressBar
    }

    constructor(errorMsg: Int) {
        this.errorMsg = errorMsg
    }

    constructor(apiError: String? = null) {
        this.apiError = apiError
    }

    constructor(progressBar: Boolean) {
        this.progressBar = progressBar
    }
}