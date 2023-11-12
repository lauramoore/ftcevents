package org.waltonfrc.ftcevents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = false)
public record Match (String description) {}
