package com.josrv.synthesis

interface Module<S: State> {
    var state: S

    fun addInput(someNLPAbstraction: ProcessedInput)
    fun sendOutput(output: String) {}
}