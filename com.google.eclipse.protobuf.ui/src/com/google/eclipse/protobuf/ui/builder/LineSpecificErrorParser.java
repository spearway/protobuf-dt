/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.ui.builder;

import static java.lang.Integer.parseInt;

import java.util.regex.*;

import org.eclipse.core.runtime.CoreException;

/**
 * @author alruiz@google.com (Alex Ruiz)
 */
class LineSpecificErrorParser implements ProtocOutputParser {

  /*
   * (.*):(\\d+):(\\d+):\\s*(.*)
   * --1- ---2-- ---3-- -*- --4-
   *
   * 1: file name
   * 2: line number
   * 3: column
   * *: whitespace
   * 4: description
   */
  private static final Pattern ERROR_PATTERN = Pattern.compile("(.*):(\\d+):(\\d+):\\s*(.*)");

  public boolean parseAndAddMarkerIfNecessary(String line, ProtocMarkerFactory markerFactory) throws CoreException {
    Matcher errorMatcher = ERROR_PATTERN.matcher(line);
    if (!errorMatcher.matches()) return false;
    markerFactory.createErrorIfNecessary(errorMatcher.group(1), errorMatcher.group(4), parseInt(errorMatcher.group(2)));
    return true;
  }
}