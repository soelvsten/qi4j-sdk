/*
 * Copyright 2007, 2008 Niclas Hedhman.
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
package org.qi4j.library.rdf.serializer;

import org.openrdf.model.Graph;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.GraphImpl;
import org.qi4j.spi.structure.ApplicationSPI;
import org.qi4j.structure.Application;

public class ApplicationSerializer
{
    public Iterable<Statement> serialize( Application app )
    {
        Graph graph = new GraphImpl();
        SerializerContext context = new SerializerContext( graph );
        ApplicationSerializerVisitor applicationVisitor = new ApplicationSerializerVisitor( context );
        ( (ApplicationSPI) app ).visitDescriptor( applicationVisitor );
        return graph;
    }
}
