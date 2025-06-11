package ru.thegod.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import jakarta.inject.Inject
import ru.thegod.services.ConvertingService


@Controller
class LibheifConvertertingController {


    @Inject
    lateinit var convertingService: ConvertingService

    @Get("/perform/default/{filename}")
    fun performConvert(filename: String){
        convertingService.performConvertInDefaultFolder(filename)

    }

    @Get("/test")
    fun performConvertDefault(): HttpResponse<String>{
        val millisecondsStart = System.currentTimeMillis()
        convertingService.performConvertInDefaultFolder("IMG_8497.HEIC")
        val millisecondsEnd = System.currentTimeMillis()

        return HttpResponse.created("Test performed, time took ${millisecondsEnd-millisecondsStart}ms")
    }

    @Get("/test/coroutine")
    suspend fun performConvertCoroutine(): HttpResponse<String>{
        val millisecondsStart = System.currentTimeMillis()
        convertingService.performConvertCoroutine("IMG_8497.HEIC")
        val millisecondsEnd = System.currentTimeMillis()

        return HttpResponse.created("Test performed, time took ${millisecondsEnd-millisecondsStart}ms")
    }

}