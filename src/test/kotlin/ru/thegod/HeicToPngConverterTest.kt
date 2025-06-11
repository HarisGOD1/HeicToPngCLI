package ru.thegod

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import ru.thegod.converters.LibheifHeicToPngConverter

@MicronautTest
class HeicToPngConverterTest {


    @Inject
    lateinit var heicToPngConverter: LibheifHeicToPngConverter
    @Test
    fun `test CLI`(){
        heicToPngConverter.performConvertInDefaultFolder("IMG_8497.HEIC")


    }
}