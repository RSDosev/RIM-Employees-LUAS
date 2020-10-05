package com.rsdosev.domain.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root
data class Forecast(

    @field:Attribute(name = "created", required = false)
    @param:Attribute(name = "created", required = false)
    val created: String,

    @field:Attribute(name = "stop", required = false)
    @param:Attribute(name = "stop", required = false)
    val stop: String,

    @field:Attribute(name = "stopAbv", required = false)
    @param:Attribute(name = "stopAbv", required = false)
    val stopAbbreviation: String,

    @field:Element(name = "message", required = false)
    @param:Element(name = "message", required = false)
    val message: String,

    @field:ElementList(name = "direction", required = false, inline = true)
    @param:ElementList(name = "direction", required = false, inline = true)
    val directions: List<Direction>
) {

    val outboundDirection = directions.firstOrNull { it.name.equals("outbound", ignoreCase = true) }

    val inboundDirection = directions.firstOrNull { it.name.equals("inbound", ignoreCase = true) }

    companion object {
        val dummyForecast1 = Forecast(
            created = "",
            stop = "Marlborough",
            stopAbbreviation = "",
            message = "",
            directions = listOf(
                Direction("Outbound", listOf(Tram("Bride's Glen", "5"), Tram("Sandyford", "3"))),
                Direction("Inbound", listOf(Tram("Bride's Glen", "5"), Tram("Sandyford", "3")))
            )
        )
        val dummyForecast2 = Forecast(
            created = "",
            stop = "Stillorgan",
            stopAbbreviation = "",
            message = "",
            directions = listOf()
        )
    }
}

