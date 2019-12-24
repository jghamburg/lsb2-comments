package com.greglturnquist.learningspringboot.comments;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;

public enum MyFeatures implements Feature {
  @EnabledByDefault
  @Label("Special feature")
  FEATURE_ENABLE_SPECIAL,

}
