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
package org.apache.polygene.library.sql.generator.implementation.transformation.mysql;

import org.apache.polygene.library.sql.generator.grammar.definition.schema.SchemaDefinition;
import org.apache.polygene.library.sql.generator.grammar.definition.table.ColumnDefinition;
import org.apache.polygene.library.sql.generator.implementation.transformation.DefinitionProcessing.ColumnDefinitionProcessor;
import org.apache.polygene.library.sql.generator.implementation.transformation.DefinitionProcessing.SchemaDefinitionProcessor;
import org.apache.polygene.library.sql.generator.implementation.transformation.spi.SQLProcessorAggregator;

/**
 *
 */
public class DefinitionProcessing
{

    public static class MySQLSchemaDefinitionProcessor extends SchemaDefinitionProcessor
    {

        @Override
        protected void doProcess( SQLProcessorAggregator aggregator, SchemaDefinition object, StringBuilder builder )
        {
            // Just process schema elements
            this.processSchemaElements( aggregator, object, builder );
        }
    }

    public static class MySQLColumnDefinitionProcessor extends ColumnDefinitionProcessor
    {
        @Override
        protected void processAutoGenerationPolicy( ColumnDefinition object, StringBuilder builder )
        {
            // MySQL combines both ALWAYS and BY DEFAULT policies.
            builder.append( " AUTO_INCREMENT" );
        }
    }
}
