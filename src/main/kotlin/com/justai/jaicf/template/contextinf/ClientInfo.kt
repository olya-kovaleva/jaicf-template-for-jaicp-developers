package com.justai.jaicf.template.contextinf


import com.justai.jaicf.context.BotContext

class Clientinfo (context: BotContext) {
    var wasThere: Boolean? by context.client
    var name: String? by context.client
    var searchdata: SearchData? by context.client


    init {
        if (searchdata == null) {
            searchdata = SearchData()
        }
    }
    class SearchData (
        var city: String? = null,
        var salary: String? = null,
        var occupation: String? = null
    ) {
        fun HasFinishedSearch(): Boolean {
            return (city != null && salary != null && occupation != null)
        }
        fun HasStartedSearch(): Boolean {
            if (city != null || salary != null || occupation != null) return true
            return false
        }
    }


}
