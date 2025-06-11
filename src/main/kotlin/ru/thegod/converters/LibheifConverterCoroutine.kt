package ru.thegod.converters

import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.io.File
import java.util.concurrent.TimeUnit

@Singleton
class LibheifConverterCoroutine {
    @Inject
    lateinit var libheifHeicToPngConverter: LibheifHeicToPngConverter



    fun performConvertAsCoroutine(inputFilePath: String,outputFilePath: String){
        var command = libheifHeicToPngConverter.getHeifCommand(inputFilePath,outputFilePath)

        Thread{
            runCoroutineCommand(command)
        }
    }

    fun runCoroutineCommand(command: String,
                   workingDir: File = File("."),
                   timeoutAmount: Long = 60,
                   timeoutUnit: TimeUnit = TimeUnit.SECONDS
                    ): String? = runCatching {
        ProcessBuilder("\\s".toRegex().split(command))
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start().also { it.waitFor(timeoutAmount, timeoutUnit) }
            .inputStream.bufferedReader().readText()
    }.onFailure { it.printStackTrace() }.getOrNull()


}