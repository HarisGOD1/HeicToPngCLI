package ru.thegod.services

import io.micronaut.context.annotation.Value
import jakarta.inject.Inject
import jakarta.inject.Singleton
import ru.thegod.converters.LibheifHeicToPngConverter

@Singleton
class ConvertingService() {

    @Inject
    lateinit var libheifConverter: LibheifHeicToPngConverter

    fun performConvertInDefaultFolder(filename: String){
        libheifConverter.performConvertInDefaultFolder(filename)
    }

    fun performConvert(inputFilePath: String,outputFilePath: String){
        libheifConverter.performConvert(inputFilePath,outputFilePath)

    }


}