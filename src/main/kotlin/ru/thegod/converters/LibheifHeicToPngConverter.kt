package ru.thegod.converters

import io.micronaut.context.annotation.Value
import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.text.iterator
import kotlinx.coroutines.*


@Singleton
class LibheifHeicToPngConverter(
    @Value("\${ru.thegod.path-to-images}")
    private var defaultImagesDir: String)
    :HeicToPngConverter{

    private val badSymbols = " \n\r\t&\\"



    override fun convert(inputFilePath: String, outputFilePath: String) {
        performConvert(inputFilePath,outputFilePath)
    }

    fun performConvertInDefaultFolder(filename: String){
        performConvert(getDefaultInputPathFromFilename(filename),
            getDefaultOutputPathFromFilename(filename))
    }

    fun performConvert(inputFilePath: String,outputFilePath: String){
        var command = getHeifCommand(inputFilePath,outputFilePath)

        runCommand(command)

    }

    private fun isVulnerable(filename: String): Boolean {
        for (e in filename)
            if (badSymbols.contains(e)) return true
        return false
    }

    // todo: throw illegalArgException and handle
    fun getHeifCommand(inputFilePath: String, outputFilePath: String): String {
        if(isVulnerable(inputFilePath)) return ""
        if(isVulnerable(outputFilePath)) return ""
        return "heif-convert $inputFilePath $outputFilePath"

    }

    fun getDefaultInputPathFromFilename(filename: String): String{
        return "$defaultImagesDir/HEIC/$filename"
    }
    fun getDefaultOutputPathFromFilename(filename: String): String{
        return "$defaultImagesDir/JPG/${filename}_conv.jpg"
    }


    // todo: refactor in case SRP
    fun runCommand(command: String,
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