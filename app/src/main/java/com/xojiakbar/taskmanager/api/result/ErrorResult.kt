package com.xojiakbar.taskmanager.api.result

class ErrorResult {
    var status = 0
    var code = 0
    var message: String? = null
    var description: String? = null
    var isHas_new_version = false
    var version_name: String? = null
    var download_url: String? = null

     constructor(status: Int, code: Int, message: String?, description: String?) {
        this.status = status
        this.code = code
        this.message = message
        this.description = description
    }

    constructor(defaultMessage: String?) {
        message = defaultMessage
    }

    constructor() {}
}
