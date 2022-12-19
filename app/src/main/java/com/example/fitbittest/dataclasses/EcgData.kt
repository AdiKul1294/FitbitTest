package com.example.fitbittest.dataclasses

data class EcgData(
    val ecgReadings: List<EcgReading>,
    val pagination : Pagination
)