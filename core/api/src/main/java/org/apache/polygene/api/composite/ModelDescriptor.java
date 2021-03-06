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
package org.apache.polygene.api.composite;

import org.apache.polygene.api.common.Visibility;
import org.apache.polygene.api.structure.MetaInfoHolder;
import org.apache.polygene.api.structure.ModuleDescriptor;
import org.apache.polygene.api.type.HasTypes;

/**
 * Composite ModelDescriptor.
 */
public interface ModelDescriptor extends HasTypes, MetaInfoHolder
{
    Visibility visibility();

    /** The Module that the Model is declared in.
     *
     * @return The Module that this Model was declared in.
     */
    ModuleDescriptor module();

    boolean isAssignableTo( Class<?> type );
}
