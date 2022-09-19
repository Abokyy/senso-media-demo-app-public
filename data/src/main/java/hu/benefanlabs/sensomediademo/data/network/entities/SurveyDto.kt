package hu.benefanlabs.sensomediademo.data.network.entities

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(strict = false, name = "root")
class SurveyXMLRoot {
    @field:Element(name = "row", required = false)
    var row: SurveyXMLRow? = null
}

@Root(strict = false, name = "row")
class SurveyXMLRow {
    @field:Element(name = "id", required = false)
    var id: Int? = null

    @field:ElementList(inline = true,name = "row", required = false)
    lateinit var questions: List<SurveyXMLQuestions>
}

@Root(strict = false, name = "questions")
class SurveyXMLQuestions {
    @field:Element(name = "id", required = false)
    var id: Int? = null

    @field:Element(name = "question", required = false)
    var question: String? = null

    @field:Element(name = "image", required = false)
    var image: String? = null

    @field:Element(name = "type", required = false)
    var type: String? = null

    @field:ElementList(inline = true, name = "questions", required = false)
    lateinit var answers: List<SurveyXMLAnswer>
}

@Root(strict = false, name = "ansewrs")
class SurveyXMLAnswer {
    @field:Element(name = "answer", required = false)
    var answer: String? = null
}