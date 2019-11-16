package com.josrv.synthesis

class AgendaModule private constructor(
    override var state: AgendaState
) : Module<AgendaState> {
    override fun addInput(someNLPAbstraction: ProcessedInput) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //TODO use nlp to extract information from input and change state
    }

    companion object {
        fun withTopics(vararg topics: String): AgendaModule {
            val state = AgendaState(topics.mapIndexed(::Topic).associateWith { TopicState.NOT_DISCUSSED })
            return AgendaModule(state)
        }
    }


}