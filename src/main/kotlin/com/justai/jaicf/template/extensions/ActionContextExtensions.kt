package com.justai.jaicf.template.extensions

import com.justai.jaicf.context.DefaultActionContext
import com.justai.jaicf.template.contextinf.SessionInfo
import com.justai.jaicf.template.contextinf.clientInfo

val DefaultActionContext.clientInfo: clientInfo
    get() = clientInfo(context)

val DefaultActionContext.SessionInfo: SessionInfo
    get() = SessionInfo(context)