package org.qi4j.sample.scala;

import org.junit.Assert;
import org.junit.Test;
import org.qi4j.api.constraint.ConstraintViolationException;
import org.qi4j.api.query.QueryExpressions;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.SingletonAssembler;
import org.qi4j.index.rdf.assembly.RdfMemoryStoreAssembler;
import org.qi4j.test.EntityTestAssembler;

import static org.qi4j.api.query.QueryExpressions.eq;
import static org.qi4j.api.query.QueryExpressions.templateFor;

/**
 * TODO
 */
public class HelloWorldCompositeTest
{
    @Test
    public void testComposite()
    {
        SingletonAssembler assembler = new SingletonAssembler()
        {
            @Override
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.transients( HelloWorldComposite.class, HelloWorldComposite2.class ).
                        withMixins( TraitMixin.class ).
                        withConcerns( ExclamationGenericConcern.class );
            }
        };

        HelloWorldComposite composite = assembler.transientBuilderFactory().newTransient( HelloWorldComposite.class );
        Assert.assertEquals( "Do stuff!", composite.doStuff() );
        Assert.assertEquals( "Hello there World!", composite.sayHello( "World" ) );

        try
        {
            composite.sayHello( "AReallyReallyLongName" );
        } catch( ConstraintViolationException e )
        {
            // Ok!
        }

        HelloWorldComposite2 composite2 = assembler.transientBuilderFactory().newTransient( HelloWorldComposite2.class);
        Assert.assertEquals( "Do custom stuff!", composite2.doStuff());
    }

    @Test
    public void testEntity() throws UnitOfWorkCompletionException
    {
        SingletonAssembler assembler = new SingletonAssembler()
        {
            @Override
            public void assemble( ModuleAssembly module ) throws AssemblyException
            {
                module.entities( TestEntity.class ).withMixins( TraitMixin.class );
                module.services( TestService.class ).withMixins( TraitMixin.class );

                new EntityTestAssembler(  ).assemble( module );
                new RdfMemoryStoreAssembler().assemble(module);
            }
        };

        // Create and update Entity
        UnitOfWork uow = assembler.unitOfWorkFactory().newUnitOfWork();
        try
        {
            Commands entity = uow.newEntity( Commands.class );
            entity.updateFoo( "Foo" );

            Data data = uow.get( Data.class, entity.toString() );

            Assert.assertEquals( "FooFoo", data.foo().get() );
        } finally
        {
            uow.complete();
        }

        // Find it
        uow = assembler.unitOfWorkFactory().newUnitOfWork();
        try
        {
            Data data = assembler.queryBuilderFactory().newQueryBuilder( Data.class ).where( eq( templateFor( Data.class ).foo(), "FooFoo" ) ).newQuery( uow ).find();
            Assert.assertEquals( "FooFoo", data.foo().get() );
        } finally
        {
            uow.discard();
        }
    }
}
