package com.josrv.synthesis

fun main() {
    val synthesis = Synthesis {
        module {
            AgendaModule.withTopics("dogs", "cats")
        }

        onOutput {
            println(it)
        }
    }

}