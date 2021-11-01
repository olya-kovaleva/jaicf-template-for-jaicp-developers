package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.hook.AnyErrorHook
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.justai.jaicf.template.configuration.Configuration
import com.justai.jaicf.template.extensions.clientInfo
import io.ktor.util.*

val MainScenario = Scenario {

    append(ExampleBitcoinScenario)

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
            reactions.say("Здравствуйте! Я голосовой помощник компании HeadHunter, могу помочь вам с подбором вакансий.")
            if (clientInfo.wasThere == null) {
                clientInfo.wasThere = true;
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

            state("Name") {
                activators {
                    intent("Name")
                }

                action {
                    
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

            fallback {
                reactions.sayRandom(
                    "Это точно твое имя?",
                    "Так людей не называют"
                )
            }
        }

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




//        TODO("Реплика; " +
//                   "вложенные стэйты для плохих слов и \"Не скажу\" (активируются по интентам); " +
//            "вложенный локальный фоллбэк (пока без счетчика и уточнения имени)"
//      )


    // Пока оставляем нездоровый фоллбэк
    fallback {
        reactions.sayRandom(
            "Я не понимать тебя, хозяйка(",
            "Перефразируйте фразу"
        )
    }
}