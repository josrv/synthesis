package com.josrv.synthesis

data class AgendaState(
    val topics: Map<Topic, TopicState>
) : State