/*
 * Copyright (c) 2010, Rickard Öberg. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.qi4j.api.composite;

import org.qi4j.api.property.StateHolder;

import java.lang.reflect.Method;

/**
 * All composites must implement this interface. Methods that are invoked
 * may reside either in the public Composite interface or in any internal mixins.
 */
public interface CompositeInvoker
{
    Object invokeComposite( Method method, Object[] args )
        throws Throwable;
}