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
package org.apache.polygene.index.rdf;

import org.apache.polygene.bootstrap.AssemblyException;
import org.apache.polygene.bootstrap.ModuleAssembly;
import org.apache.polygene.library.rdf.entity.EntityStateSerializer;
import org.apache.polygene.library.rdf.entity.EntityTypeSerializer;
import org.apache.polygene.library.rdf.repository.MemoryRepositoryService;
import org.apache.polygene.test.indexing.AbstractComplexQueryTest;
import org.junit.jupiter.api.Disabled;

@Disabled( "RDF Index/Query do not support Complex Queries, ie. queries by 'example values'" )
public class RdfComplexQueryTest
        extends AbstractComplexQueryTest
{

    @Override
    public void assemble( ModuleAssembly module )
            throws AssemblyException
    {
        super.assemble( module );
        module.services( RdfIndexingService.class ).instantiateOnStartup();
        module.objects( EntityStateSerializer.class, EntityTypeSerializer.class );
        module.services( MemoryRepositoryService.class ).identifiedBy( "rdf-indexing" ).instantiateOnStartup();
    }

}
