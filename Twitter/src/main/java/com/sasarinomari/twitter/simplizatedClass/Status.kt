package com.sasarinomari.twitter.simplizatedClass

import java.util.*

class Status(src: twitter4j.Status) {
    val id: Long = src.id
    val text: String = src.text
    val createdAt: Date = src.createdAt
}