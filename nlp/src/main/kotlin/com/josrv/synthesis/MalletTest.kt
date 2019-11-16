package com.josrv.synthesis

import cc.mallet.examples.TopicModel
import cc.mallet.grmm.inference.Inferencer
import cc.mallet.pipe.*
import cc.mallet.pipe.iterator.CsvIterator
import cc.mallet.topics.ParallelTopicModel
import cc.mallet.types.FeatureSequence
import cc.mallet.types.Instance
import cc.mallet.types.InstanceList
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern


fun main() {
    val pipeList = listOf(
        CharSequenceLowercase(),
        CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")),
        TokenSequenceRemoveStopwords(Paths.get("stoplist.txt").toFile(), "UTF-8", false, false, false),
        TokenSequence2FeatureSequence()
    )

    val pipes = SerialPipes(pipeList)
    val instances = InstanceList(pipes)
    val fileReader = InputStreamReader(FileInputStream(Paths.get("ap.txt").toFile()), "UTF-8")
    instances.addThruPipe(CsvIterator(fileReader, "(\\w+)\\s+(\\w+)\\s+(.*)", 3, 2, 1))

    val numTopics = 100
    val model = ParallelTopicModel(numTopics, 1.0, 0.01)
    model.addInstances(instances)
    model.setNumThreads(4)
    model.setNumIterations(200)
    model.estimate()

//    model.printState(Paths.get("model").toFile())

    val inferencer = model.inferencer
//    inferencer.

    val testData = Files.readString(Paths.get("test2.txt"))
    val testInstance = Instance(testData, null, null, null)
    pipes.pipes().forEach { it.pipe(testInstance) }

    val sampledDistribution = inferencer.getSampledDistribution(testInstance, 10, 1, 5)

    val alphabet = pipes.dataAlphabet
    val tokens = testInstance.data as FeatureSequence

//    val testProbs = sampledDistribution.mapIndexed {i, d -> alphabet.lookupObject(tokens.getIndexAtPosition(i)) to d }.sortedByDescending { it.second }
    val zeroProbs = model.getTopicProbabilities(0).mapIndexed {i, d -> alphabet.lookupObject(tokens.getIndexAtPosition(i)) to d }.sortedByDescending { it.second }

    testData

}