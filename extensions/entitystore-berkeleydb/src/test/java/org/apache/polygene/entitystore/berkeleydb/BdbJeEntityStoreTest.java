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
package org.apache.polygene.entitystore.berkeleydb;

import org.apache.polygene.api.common.Visibility;
import org.apache.polygene.bootstrap.AssemblyException;
import org.apache.polygene.bootstrap.ModuleAssembly;
import org.apache.polygene.entitystore.berkeleydb.assembly.BerkeleyDBEntityStoreAssembler;
import org.apache.polygene.library.fileconfig.FileConfigurationAssembler;
import org.apache.polygene.library.fileconfig.FileConfigurationOverride;
import org.apache.polygene.test.EntityTestAssembler;
import org.apache.polygene.test.TemporaryFolder;
import org.apache.polygene.test.entity.AbstractEntityStoreTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith( TemporaryFolder.class )
public class BdbJeEntityStoreTest
    extends AbstractEntityStoreTest
{
    private TemporaryFolder tmpDir;

    @Override
    public void assemble( ModuleAssembly module )
        throws Exception
    {
        super.assemble( module );

        ModuleAssembly config = module.layer().module( "config" );
        new EntityTestAssembler().defaultServicesVisibleIn( Visibility.layer ).assemble( config );

        new FileConfigurationAssembler()
            .withOverride( new FileConfigurationOverride().withConventionalRoot( tmpDir.getRoot() ) )
            .assemble( module );
        new BerkeleyDBEntityStoreAssembler().withConfig( config, Visibility.layer ).assemble( module );
    }

    @AfterEach
    public void cleanup()
    {

    }
}
