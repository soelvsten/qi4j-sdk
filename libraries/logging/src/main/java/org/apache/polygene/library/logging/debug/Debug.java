/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.apache.polygene.library.logging.debug;

import org.apache.polygene.api.concern.Concerns;
import org.apache.polygene.api.mixin.Mixins;
import org.apache.polygene.api.mixin.NoopMixin;

@Concerns( DebugConcern.class )
@Mixins( NoopMixin.class )
public interface Debug
{
    int OFF = Integer.MIN_VALUE;
    int LOWLOW = -100;
    int LOW = -50;
    int NORMAL = 0;
    int HIGH = 50;
    int HIGHHIGH = 100;

    Integer debugLevel();

    void debug( int level, String message );

    void debug( int level, String message, Object param1 );

    void debug( int level, String message, Object param1, Object param2 );

    void debug( int level, String message, Object... params );

}
