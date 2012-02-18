/*
 * Copyright (c) 2010-2012, Paul Merlin. All Rights Reserved.
 * Copyright (c) 2012, Niclas Hedhman. All Rights Reserved.
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
package org.qi4j.library.scheduler.task;

import org.qi4j.api.common.UseDefaults;
import org.qi4j.api.property.Property;
import org.qi4j.api.unitofwork.UnitOfWork;

import java.util.List;

/**
 * Compose an Entity using this type to be able to Schedule it.
 *
 * A Task is wrapped in a {@link org.qi4j.library.scheduler.SchedulerMixin.ScheduleRunner} before being run by an executor.
 * {@link org.qi4j.library.scheduler.SchedulerMixin.ScheduleRunner} wrap a {@link UnitOfWork} around the {@link Task#run()} invocation.
 *
 * Here is a simple example:
 * <pre>
 *  interface MyTaskEntity
 *      extends Task, EntityComposite
 *  {
 *      Property&lt;String customState();
 *      Association&lt;AnotherEntity&gt; anotherEntity();
 *  }
 *
 *  abstract class MyTaskMixin
 *      implements Runnable
 *  {
 *      &#64;This MyTaskEntity me;
 *      public void run()
 *      {
 *          me.customState().set( me.anotherEntity().get().doSomeStuff( me.customState().get() ) );
 *      }
 *  }
 * </pre>
 */
public interface Task
        extends Runnable
{

    Property<String> name();

    @UseDefaults
    Property<List<String>> tags();

}
