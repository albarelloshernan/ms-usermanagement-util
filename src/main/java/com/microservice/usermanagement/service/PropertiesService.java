package com.microservice.usermanagement.service;

import java.util.Map;

/**
 * Service for translation basic error to business error.
 */
public interface PropertiesService {

  public String translation(String key);

  public Map<String, Object> getAllProperties();

}
