package com.justai.jaicf.template.extensions

import com.justai.jaicf.context.DefaultActionContext
import com.justai.jaicf.template.contextinf.Clientinfo

val DefaultActionContext.clientInfo: Clientinfo
    get() = Clientinfo(context)