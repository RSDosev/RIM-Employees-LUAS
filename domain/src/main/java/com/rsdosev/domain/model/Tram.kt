package com.rsdosev.domain.model

import org.simpleframework.xml.Attribute

data class Tram(

    @field:Attribute(name = "destination", required = false)
    @param:Attribute(name = "destination", required = false)
    val destination: String,

    @field:Attribute(name = "dueMins", required = false)
    @param:Attribute(name = "dueMins", required = false)
    val dueMinutes: String
)
