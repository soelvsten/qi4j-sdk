/*
 * Copyright (c) 2008, Rickard Öberg. All Rights Reserved.
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

package org.qi4j.library.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.qi4j.api.injection.scope.Uses;

/**
 * Generic decorator mixin that allows a Composite to wrap
 * any other Composite as long as they share an interface.
 * <p/>
 * Can be used to effectively implement
 * singleton mixins, since the decorated object can be shared between
 * many instances.
 */
public class DecoratorMixin
    implements InvocationHandler
{
    @Uses private Object delegate;

    public Object invoke( Object object, Method method, Object[] args )
        throws Throwable
    {
        if( delegate instanceof InvocationHandler )
        {
            InvocationHandler handler = (InvocationHandler) delegate;
            return handler.invoke( object, method, args );
        }
        else
        {
            try
            {
                final Object invocation = method.invoke( delegate, args );
                return invocation;
            }
            catch( InvocationTargetException e )
            {
                throw e.getCause();
            }
            catch( IllegalArgumentException e )
            {
                String message = constructMessage( method, args );
                System.err.println( message );
                throw new IllegalArgumentException( message, e );
            }
        }
    }

    private String constructMessage( Method method, Object[] args )
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "method: " );
        builder.append( method.getDeclaringClass().getName() );
        builder.append( "." );
        builder.append( method.getName() );
        builder.append( "\ndelegate: " );
        builder.append( delegate );
        builder.append( "\ndelegateType: " );
        builder.append( delegate.getClass().getName() );
        builder.append( "\narguments: \n" );
        for( Object arg : args )
        {
            builder.append( "    " );
            builder.append( arg.getClass().getName() );
            builder.append( '\n' );
        }
        return builder.toString();
    }
}
