package com.microservice.usermanagement.service.impl;

import com.microservice.usermanagement.service.PropertiesService;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Propertie service.
 */
@Service
public class PropertieServiceImpl implements PropertiesService {

  private final Environment environment;

  /**
   * Instantiates a new Propertie service.
   *
   * @param environment the environment
   */
  public PropertieServiceImpl(Environment environment) {
    super();
    this.environment = environment;
  }

  @Override
  public String translation(String key) {
    return environment.getProperty(key);
  }

  @Override
  public Map<String, Object> getAllProperties() {
    Map<String, Object> map = new LinkedHashMap<>();
    for (Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
      Object propertySource = it.next();
      if (propertySource instanceof MapPropertySource) {
        if (((MapPropertySource) propertySource).getSource() instanceof Map) {
          if (((MapPropertySource) propertySource).getName().contains("application.yml")) {
            try {
              Map<String, Object> source = ((MapPropertySource) propertySource).getSource();
              map.putAll(source.entrySet().stream()
                  .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
    return map;
  }

}
