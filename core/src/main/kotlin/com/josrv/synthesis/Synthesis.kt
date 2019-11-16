package com.josrv.synthesis

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface Synthesis {
    fun start()

    fun addInput(input: String)
    fun sendOutput(output: String)
}

class SynthesisImpl(
    private val modules: Set<Module<*>>,
    private val outputListeners: Set<OutputListener>
) : Synthesis {
    override fun start() {
        GlobalScope.launch {

        }
    }

    override fun sendOutput(output: String) {
        outputListeners.forEach {
            it(output)
        }
    }

    override fun addInput(input: String) {
        modules.forEach {
            it.addInput(input)
        }
    }
}

class SynthesisInitScope(
    val modules: MutableSet<Module<*>> = mutableSetOf(),
    val outputListeners: MutableSet<OutputListener> = mutableSetOf()
) {
    fun build(): Synthesis {
        return SynthesisImpl(modules.toSet(), outputListeners.toSet())
    }
}

fun Synthesis(initModules: SynthesisInitScope.() -> Unit): Synthesis {
    val synthesisInitScope = SynthesisInitScope()
    synthesisInitScope.initModules()

    return synthesisInitScope.build()
}

fun <S : State> SynthesisInitScope.module(initBlock: () -> Module<S>) {
    modules += initBlock()
}

fun SynthesisInitScope.onOutput(callback: OutputListener) {
    outputListeners += callback
}