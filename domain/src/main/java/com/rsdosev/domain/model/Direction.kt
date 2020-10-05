package com.rsdosev.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList

data class Direction(

    @field:Attribute(name = "name", required = false)
    @param:Attribute(name = "name", required = false)
    val name: String,

    @field:ElementList(required = false, inline = true)
    @param:ElementList(required = false, inline = true)
    val directions: List<Tram>,
)
