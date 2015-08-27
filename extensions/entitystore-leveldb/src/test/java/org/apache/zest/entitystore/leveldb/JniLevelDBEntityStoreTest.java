/*
 * Copyright 2012, Paul Merlin.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.zest.entitystore.leveldb;

import org.apache.zest.api.common.Visibility;
import org.apache.zest.bootstrap.AssemblyException;
import org.apache.zest.bootstrap.ModuleAssembly;
import org.apache.zest.library.fileconfig.FileConfigurationService;
import org.apache.zest.test.EntityTestAssembler;
import org.apache.zest.test.entity.AbstractEntityStoreTest;
import org.apache.zest.valueserialization.orgjson.OrgJsonValueSerializationAssembler;

public class JniLevelDBEntityStoreTest
    extends AbstractEntityStoreTest
{

    @Override
    public void assemble( ModuleAssembly module )
        throws AssemblyException
    {
        super.assemble( module );

        ModuleAssembly config = module.layer().module( "config" );
        new EntityTestAssembler().visibleIn( Visibility.module ).assemble( config );
        new OrgJsonValueSerializationAssembler().assemble( module );

        module.services( FileConfigurationService.class );

        new LevelDBEntityStoreAssembler().
            withConfig( config, Visibility.layer ).
            identifiedBy( "jni-leveldb-entitystore" ).
            assemble( module );

        config.forMixin( LevelDBEntityStoreConfiguration.class ).declareDefaults().flavour().set( "jni" );
    }
}