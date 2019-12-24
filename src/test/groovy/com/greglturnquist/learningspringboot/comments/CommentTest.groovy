package com.greglturnquist.learningspringboot.comments

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@JsonTest
class CommentTest extends Specification {
  @Autowired
  private ObjectMapper objectMapper;

  def "deserialize a comment from json"() {
    given: "a json string"
    String id = "4711"
    String imageId = "myimageId"
    String commentText = "This is a comment"
    String json = """{"id":"${id}","imageId":"${imageId}","comment":"${commentText}"}"""
    when: "mapping to object"
    Comment comment = objectMapper.readValue(json, Comment.class);
    then: "object contains all elements of json"
    assertThat(comment).isEqualTo(new Comment(id:id,imageId:imageId,comment:commentText))
  }

  def "verify serialization of comment object"(){
    given: "a comment object"
    String id = "4711"
    String imageId = "myimageId"
    String commentText = "This is a comment"
    Comment comment = new Comment(id:id, imageId: imageId, comment: commentText)
    when: "serializing the object"
    String jsonString = objectMapper.writeValueAsString(comment)
    then: "a valid json sting is provided"
    String json = """{"id":"${id}","imageId":"${imageId}","comment":"${commentText}"}"""
    assertThat(jsonString).containsIgnoringCase(""""id":"4711","imageId":"myimageId",""")
  }
}
