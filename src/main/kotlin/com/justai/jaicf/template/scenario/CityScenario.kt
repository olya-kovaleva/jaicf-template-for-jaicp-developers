package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.hook.AnyErrorHook
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.justai.jaicf.template.configuration.Configuration
import com.justai.jaicf.template.extensions.SessionInfo
import com.justai.jaicf.template.extensions.clientInfo
import com.justai.jaicf.template.scenario.CityScenario
import io.ktor.util.*

val CityScenario = Scenario {

    handle<AnyErrorHook> {
        reactions.say(Configuration.bot.onErrorReply)
        logger.error(exception)
    }

    state("AskCity") {
        action {
            if (clientInfo.name == null) {
                reactions.say("Скажите, в каком городе вы ищете работу?")
            } else {
                reactions.say(clientInfo.name + ", в каком городе вы ищете работу?")
            }
        }
    }
}