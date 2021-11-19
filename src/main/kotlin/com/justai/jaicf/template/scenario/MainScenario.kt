package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.hook.AnyErrorHook
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.justai.jaicf.template.configuration.Configuration
import com.justai.jaicf.template.extensions.SessionInfo
import com.justai.jaicf.template.extensions.clientInfo
import io.ktor.util.*

val MainScenario = Scenario {

    append(CityScenario)

    handle<AnyErrorHook> {
        reactions.say(Configuration.bot.onErrorReply)
        logger.error(exception)
    }

    state("Start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            SessionInfo.sessionInfoinit()
            reactions.say("Здравствуйте! Я голосовой помощник компании HeadHunter, могу помочь вам с подбором вакансий.")
            if (clientInfo.wasThere == null) {
                clientInfo.wasThere = true
                reactions.go("/Start/AskName")
            } else if (clientInfo.searchdata!!.HasFinishedSearch()) {
                reactions.say("Поиск закончен")
                //reactions.go("/FinishedSearch")
            } else if (clientInfo.searchdata!!.HasStartedSearch()) {
                reactions.say("Поиск начат")
                //reactions.go("/StartedSearch")
            } else {
                reactions.go("/AskCity")
            }
        }

        state("AskName") {
            action {
                reactions.say("Скажите, пожалуйста, как я могу к вам обращаться?")
            }

            state("GetName") {
                activators {
                    intent("Name")
                }
                action {
                    reactions.say("Приятно познакомиться!")
                    clientInfo.name = activator.caila?.slots?.get("Name")?.capitalize()
                    reactions.go("/AskCity")
                }
            }

            state("Obscene") {
                activators {
                    intent("Obscene")
                }

                action {
                    reactions.go("/AskCity")
                }
            }

            state("NoName") {
                activators {
                    intent("No")
                }

                action {
                    reactions.say("Как вам будет удобно \uD83D\uDE0A")
                    reactions.go("/AskCity")
                }
            }

            state ("FallbackName") {
                activators ("/Start/AskName") {
                    catchAll()
                }
                action {
                    SessionInfo.fallbackCountName++
                    if (SessionInfo.fallbackCountName < 3) {
                        clientInfo.name = request.input.capitalize()
                        reactions.say("${clientInfo.name}, Ваше имя звучит необычно. Мне следует обращаться к вам именно так?")
                        reactions.buttons("Да" toState "./Yes", "Нет" toState "./No")
                    } else {
                        reactions.go("/AskCity")
                        clientInfo.name = null
                    }
                }

                state ("Yes") {
                    activators ("/Start/AskName/FallbackName"){
                        intent("Yes")
                    }

                    action {
                        reactions.say("Приятно познакомиться!")
                        reactions.go("/AskCity")
                    }
                }

                state ("No") {
                    activators ("/Start/AskName/FallbackName") {
                        intent("No")
                    }

                    action {
                        reactions.say("Прошу прощения! Скажите, пожалуйста, как я могу к вам обращаться?")
                        // reactions.go("..")
                    }
                }
            }
        }
    }

    fallback {
        if (context.dialogContext.transitionHistory.first != "/fallback") {
            SessionInfo.fallbackCount = 0
        }
        SessionInfo.fallbackCount++
        if (SessionInfo.fallbackCount < 3) {
            reactions.sayRandom(
                "Я не понимать тебя, хозяйка(",
                "Перефразируйте фразу"
            )
        }
    }
}
