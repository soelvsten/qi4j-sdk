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
package org.apache.polygene.runtime.activation;

import java.util.Arrays;
import org.apache.polygene.api.activation.Activators;
import org.apache.polygene.api.mixin.Mixins;
import org.apache.polygene.api.service.ServiceComposite;
import org.apache.polygene.api.service.ServiceReference;
import org.apache.polygene.bootstrap.AssemblyException;
import org.apache.polygene.bootstrap.ModuleAssembly;
import org.apache.polygene.bootstrap.SingletonAssembler;
import org.apache.polygene.runtime.activation.ActivatorOrderTestSupport.ActivationStepsRecorder;
import org.apache.polygene.runtime.activation.ActivatorOrderTestSupport.ActivationStepsRecorderInstance;
import org.apache.polygene.runtime.activation.ActivatorOrderTestSupport.Expected;
import org.apache.polygene.runtime.activation.ActivatorOrderTestSupport.OrderTestActivator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ServiceActivatorOrderTest
{

    //
    // ActivationStepsRecorder --------------------------------------------
    //
    public static final ActivationStepsRecorder RECORDER = new ActivationStepsRecorderInstance();

    @BeforeEach
    public void beforeEachTest()
    {
        RECORDER.reset();
    }

    //
    // FooBarService ------------------------------------------------------
    //
    public static interface FooBar
    {
        
        String foo();
        
    }

    public static abstract class FooBarInstance
            implements FooBar
    {

        @Override
        public String foo()
        {
            return "bar";
        }

    }

    @Mixins( FooBarInstance.class )
    public static interface FooBarService
            extends FooBar, ServiceComposite
    {
    }

    @Mixins( FooBarInstance.class )
    @Activators( { GammaActivator.class, DeltaActivator.class } )
    public static interface FooBarServiceWithActivators
            extends FooBar, ServiceComposite
    {
    }

    //
    // BazarService ------------------------------------------------------
    //
    @Activators( GammaActivator.class )
    public static interface Bazar
            extends Things, Stuff
    {
    }
    
    @Activators( DeltaActivator.class )
    public static interface Things
    {
        
        String things();
        
    }
    
    @Activators( EpsilonActivator.class )
    public static interface Stuff
    {
        
        String stuff();
        
    }
    
    public static class ThingsInstance
            implements Things
    {

        @Override
        public String things()
        {
            return "things";
        }
        
    }
    
    public static class StuffInstance
            implements Stuff
    {

        @Override
        public String stuff()
        {
            return "stuff";
        }
        
    }
    
    @Mixins( { ThingsInstance.class, StuffInstance.class } )
    @Activators( BetaActivator.class )
    public static interface BazarService
            extends Bazar, ServiceComposite
    {
    }
    
    //
    // Activators in order: Alpha, Beta, Gamma, Delta, Epsilon, Zeta ------
    //
    public static class AlphaActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public AlphaActivator()
        {
            super( "Alpha", RECORDER );
        }

    }

    public static class BetaActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public BetaActivator()
        {
            super( "Beta", RECORDER );
        }

    }

    public static class GammaActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public GammaActivator()
        {
            super( "Gamma", RECORDER );
        }

    }

    public static class DeltaActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public DeltaActivator()
        {
            super( "Delta", RECORDER );
        }

    }

    public static class EpsilonActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public EpsilonActivator()
        {
            super( "Epsilon", RECORDER );
        }

    }

    public static class ZetaActivator
            extends OrderTestActivator<ServiceReference<?>>
    {

        public ZetaActivator()
        {
            super( "Zeta", RECORDER );
        }

    }

    //
    // Tests --------------------------------------------------------------
    //
    @Test
    public void testTwoActivatorsOrderOnSimpleService()
            throws Exception
    {
        new SingletonAssembler()
        {

            @Override
            public void assemble( ModuleAssembly module )
                    throws AssemblyException
            {
                module.services( FooBarService.class ).
                        withActivators( AlphaActivator.class, BetaActivator.class ).
                        instantiateOnStartup();
            }

        }.application().passivate();

        String actual = Arrays.toString( RECORDER.steps().toArray() );
        // System.out.println( "\n" + Expected.ALPHA_BETA_SINGLE + "\n" + actual + "\n" );
        assertThat( actual, equalTo( Expected.ALPHA_BETA_SINGLE ) );
    }

    @Test
    public void testAnnotationActivatorsOrderOnSimpleService()
            throws Exception
    {
        new SingletonAssembler()
        {

            @Override
            public void assemble( ModuleAssembly module )
                    throws AssemblyException
            {
                module.services( FooBarServiceWithActivators.class ).
                        instantiateOnStartup();
            }

        }.application().passivate();

        String expected = Arrays.toString( new String[]{
                    "Gamma.beforeActivation",   // Annotation
                    "Delta.beforeActivation",   // Annotation
                    // -> Activation
                    "Gamma.afterActivation",
                    "Delta.afterActivation",
                    // -> Active
                    "Delta.beforePassivation",
                    "Gamma.beforePassivation",
                    // -> Passivation
                    "Delta.afterPassivation",
                    "Gamma.afterPassivation"
                } );

        String actual = Arrays.toString( RECORDER.steps().toArray() );
        // System.out.println( "\n" + expected + "\n" + actual + "\n" );
        assertThat( actual, equalTo( expected ) );
    }

    @Test
    public void testMixedAnnotationAndAssemblyActivatorsOrderOnSimpleService()
            throws Exception
    {
        new SingletonAssembler()
        {

            @Override
            public void assemble( ModuleAssembly module )
                    throws AssemblyException
            {
                module.services( FooBarServiceWithActivators.class ).
                        withActivators( AlphaActivator.class, BetaActivator.class ).
                        instantiateOnStartup();
            }

        }.application().passivate();

        String expected = Arrays.toString( new String[]{
                    "Alpha.beforeActivation",   // Assembly
                    "Beta.beforeActivation",    // Assembly
                    "Gamma.beforeActivation",   // Annotation
                    "Delta.beforeActivation",   // Annotation
                    // -> Activation
                    "Alpha.afterActivation",
                    "Beta.afterActivation",
                    "Gamma.afterActivation",
                    "Delta.afterActivation",
                    // -> Active
                    "Delta.beforePassivation",
                    "Gamma.beforePassivation",
                    "Beta.beforePassivation",
                    "Alpha.beforePassivation",
                    // -> Passivation
                    "Delta.afterPassivation",
                    "Gamma.afterPassivation",
                    "Beta.afterPassivation",
                    "Alpha.afterPassivation"
                } );

        String actual = Arrays.toString( RECORDER.steps().toArray() );
        // System.out.println( "\n" + expected + "\n" + actual + "\n" );
        assertThat( actual, equalTo( expected ) );
    }
    
    @Test
    public void testMixedAnnotationAndAssemblyActivatorsOrderOnComplexService()
            throws Exception
    {
        new SingletonAssembler()
        {

            @Override
            public void assemble( ModuleAssembly module )
                    throws AssemblyException
            {
                module.services( BazarService.class ).
                        withActivators( AlphaActivator.class ).
                        instantiateOnStartup();
            }

        }.application().passivate();
        
        String expected = Arrays.toString( new String[]{
                    "Alpha.beforeActivation",   // Assembly
                    "Beta.beforeActivation",    // Service type annotation
                    "Gamma.beforeActivation",   // Base type annotation
                    "Delta.beforeActivation",   // First composite type annotation
                    "Epsilon.beforeActivation", // Second composite type annotation
                    // -> Activation
                    "Alpha.afterActivation",
                    "Beta.afterActivation",
                    "Gamma.afterActivation",
                    "Delta.afterActivation",
                    "Epsilon.afterActivation",
                    // -> Active
                    "Epsilon.beforePassivation",
                    "Delta.beforePassivation",
                    "Gamma.beforePassivation",
                    "Beta.beforePassivation",
                    "Alpha.beforePassivation",
                    // -> Passivation
                    "Epsilon.afterPassivation",
                    "Delta.afterPassivation",
                    "Gamma.afterPassivation",
                    "Beta.afterPassivation",
                    "Alpha.afterPassivation"
        } );
        String actual = Arrays.toString( RECORDER.steps().toArray() );
        // System.out.println( "\n" + expected + "\n" + actual + "\n" );
        assertThat( actual, equalTo( expected ) );
    }

}
