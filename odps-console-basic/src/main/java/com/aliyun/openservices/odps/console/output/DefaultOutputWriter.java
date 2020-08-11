/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.aliyun.openservices.odps.console.output;

import java.io.OutputStream;

import com.aliyun.openservices.odps.console.ExecutionContext;

public class DefaultOutputWriter {

  private final ExecutionContext sessionContext;

  public DefaultOutputWriter(ExecutionContext context) {
    this.sessionContext = context;
  }

  public void writeError(String str) {
    System.err.println(str);
  }

  public void writeIntermediateError(String str) {
    System.err.print(str);
  }

  public void writeErrorFormat(String format, Object... args) {
    System.err.format(format, args);
  }

  public void writeDebug(String str) {
    if (getSessionContext().isDebug()) {
      System.err.println("[DEBUG]:" + str);
    }
  }

  public void writeDebug(Throwable t) {
    if (getSessionContext().isDebug()) {
      System.err.print("[DEBUG]: ");
      t.printStackTrace();
    }
  }

  public void writeResult(String str) {
    System.out.println(str);
  }

  public OutputStream getResultStream() {
    return new CloseProtectedOutputStream(System.out);
  }

  public void writeIntermediateResult(String str) {
    System.out.print(str);
  }

  public ExecutionContext getSessionContext() {
    return sessionContext;
  }
}
