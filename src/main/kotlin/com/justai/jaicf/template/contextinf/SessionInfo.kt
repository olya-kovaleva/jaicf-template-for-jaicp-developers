package com.justai.jaicf.template.contextinf

import com.justai.jaicf.context.BotContext

class SessionInfo (context: BotContext) {
    var fallbackCount: Int by context.session
    var fallbackCountName: Int by context.session

    fun sessionInfoinit() {
        fallbackCount = 0
        fallbackCountName = 0
    }

    fun clearSession() { //for reset
        fallbackCount = 0
        fallbackCountName = 0
    }
}