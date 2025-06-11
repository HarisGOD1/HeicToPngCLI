package ru.thegod.converters

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
class LibheifHeicToPngConverterTest {


    @Inject
    lateinit var libheifHeicToPngConverter: LibheifHeicToPngConverter
    @Test
    fun `test CLI`(){

        libheifHeicToPngConverter.performConvertInDefaultFolder("IMG_8497.HEIC")


    }

    @Test
    fun `profilling test`(){
        for(i in 0..40000)
            libheifHeicToPngConverter.performConvertInDefaultFolder("IMG_8497.HEIC")


    }
}