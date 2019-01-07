package com.example.demo.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.DemoApplication;
import com.example.demo.DemoClient;
import com.github.tomakehurst.wiremock.WireMockServer;

import feign.Feign;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureWireMock(port = 10001)
public class DemoTest {
    
    private DemoClient demoClient = Feign.builder()
            .contract(new SpringMvcContract())
            .target(DemoClient.class, "http://localhost:10001");
    
    @Autowired
    private WireMockServer wireMockServer;
    
    @Before
    public void setUp() {
        wireMockServer.stubFor(get(anyUrl()).willReturn(aResponse().withStatus(200)));
    }
    
    @Test
    public void testNonEmptyParam() {
        demoClient.test("asd");
        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/test")).withQueryParam("param", equalTo("asd")));
    }
    
    @Test
    public void testEmptyParam() {
        demoClient.test("");
        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/test")).withQueryParam("param", equalTo("")));
    }

}
