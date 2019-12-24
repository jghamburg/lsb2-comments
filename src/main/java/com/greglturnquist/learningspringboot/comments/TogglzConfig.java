package com.greglturnquist.learningspringboot.comments;

import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.user.UserProvider;
import org.togglz.spring.security.SpringSecurityUserProvider;

@Configuration
public class TogglzConfig implements org.togglz.core.manager.TogglzConfig {

  @Override
  public Class<? extends Feature> getFeatureClass() {
    return MyFeatures.class;
  }

  @Override
  public StateRepository getStateRepository() {
    return new InMemoryStateRepository();
  }

  @Override
  public UserProvider getUserProvider() {
    return new SpringSecurityUserProvider("ROLE_ADMIN");
  }
}
