package com.example.fitbittest.dataclasses

data class Pagination(
    val afterDate : String,
    val limit : Int,
    val next : String,
    val offset : Int,
    val previous : String,
    val sort : String
)
