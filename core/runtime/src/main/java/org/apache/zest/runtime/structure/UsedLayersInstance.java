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

package org.apache.zest.runtime.structure;

import java.util.List;
import java.util.stream.Stream;
import org.apache.zest.api.common.Visibility;
import org.apache.zest.api.composite.ModelDescriptor;
import org.apache.zest.api.composite.TransientDescriptor;
import org.apache.zest.api.entity.EntityDescriptor;
import org.apache.zest.api.object.ObjectDescriptor;
import org.apache.zest.api.service.ServiceReference;
import org.apache.zest.api.value.ValueDescriptor;
import org.apache.zest.spi.module.ModelModule;

/**
 * JAVADOC
 */
public final class UsedLayersInstance
{
    private final List<LayerInstance> usedLayerInstances;

    public UsedLayersInstance( List<LayerInstance> usedLayerInstances )
    {
        this.usedLayerInstances = usedLayerInstances;
    }

    /* package */ Stream<ModelModule<? extends ModelDescriptor>> visibleObjects()
    {
        return usedLayerInstances.stream()
            .flatMap( layerInstance -> layerInstance.visibleObjects( Visibility.application ) );
    }

    /* package */ Stream<ModelModule<? extends ModelDescriptor>> visibleTransients()
    {
        return usedLayerInstances.stream()
            .flatMap( layerInstance -> layerInstance.visibleTransients( Visibility.application ) );
    }

    /* package */ Stream<ModelModule<? extends ModelDescriptor>> visibleEntities()
    {
        return usedLayerInstances.stream()
            .flatMap( layerInstance -> layerInstance.visibleEntities( Visibility.application ) );
    }

    /* package */ Stream<ModelModule<? extends ModelDescriptor>> visibleValues()
    {
        return usedLayerInstances.stream()
            .flatMap( layerInstance -> layerInstance.visibleValues( Visibility.application ) );
    }

    /* package */ Stream<ServiceReference<?>> visibleServices()
    {
        return usedLayerInstances.stream()
            .flatMap( layerInstance -> layerInstance.visibleServices( Visibility.application ) );
    }
}