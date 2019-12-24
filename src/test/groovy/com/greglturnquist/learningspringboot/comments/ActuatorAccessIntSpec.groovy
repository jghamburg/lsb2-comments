package com.greglturnquist.learningspringboot.comments

import groovy.transform.CompileStatic
import org.apache.tools.ant.types.FilterChain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.security.web.FilterChainProxy
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    , properties = ["spring.data.mongodb.port="])
@ActiveProfiles("test")
class ActuatorAccessIntSpec extends Specification {

  @LocalServerPort
  int randomServerPort;
  @LocalManagementPort
  int randomManagedPort

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private FilterChainProxy springSecurityFilterChain;

  private MockMvc mvc;

  WebTestClient webClient

  void setup() {
    webClient = WebTestClient.bindToServer().baseUrl("http://localhost:${randomServerPort}").build()
/*
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .alwaysDo(print())
        .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
        .build();
*/
  }

  def "access health check with webClient"() {
    given: "calling the actualtor health endpoint"
    def result = webClient.get().uri("/actuator/health").exchange()
    expect: "status details are displayed in json format"
    result.expectStatus().is2xxSuccessful()
        .expectBody().json("{'status': 'UP'}")
    and:
    result.expectBody().jsonPath("./status", "UP")
  }

/*
  def "access info with mvc client"() {
    given: "a call to the info endpoint with no authentication"
    MvcResult result = mvc.perform(get("/actuator/info")
        .with(anonymous()))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    then: "a list of application specific information is provided"
    assertThat(result.getResponse().getContentAsString()).contains("lsbxxx")
  }
*/
}
