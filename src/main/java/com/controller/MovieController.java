package com.controller;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MovieController {
//  @Autowired
//  private RestTemplate restTemplate;

  /*@GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
    return this.restTemplate.getForObject("http://localhost:8000/" + id, User.class);
  }*/

    @Autowired
    DiscoveryClient discoveryClient;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/user/{id}")
    public Map findById(@PathVariable Long id) {
        return new HashMap();
    }

    @GetMapping("/getInstances")
    public String getInstances() {
        StringBuilder buf = new StringBuilder();
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("eureka-provider-metadata");
        if (!CollectionUtils.isEmpty(serviceInstances)) {
            for (ServiceInstance si : serviceInstances) {
                buf.append("[" + si.getServiceId() + " host=" + si.getHost() + " port=" + si.getPort() + " uri=" + si.getUri() + "]");
            }
        } else {
            buf.append("no service.");
        }
        return buf.toString();
    }
}
