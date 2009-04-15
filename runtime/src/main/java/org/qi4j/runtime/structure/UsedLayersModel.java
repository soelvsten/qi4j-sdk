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

package org.qi4j.runtime.structure;

import org.qi4j.api.common.Visibility;
import org.qi4j.api.composite.AmbiguousTypeException;
import org.qi4j.runtime.composite.CompositeModel;
import org.qi4j.spi.structure.UsedLayersDescriptor;

import java.io.Serializable;
import java.util.List;

/**
 * JAVADOC
 */
public final class UsedLayersModel
    implements UsedLayersDescriptor, Serializable
{
    private final List<LayerModel> usedLayers;

    public UsedLayersModel( List<LayerModel> usedLayers )
    {
        this.usedLayers = usedLayers;
    }

    public Iterable<LayerModel> layers()
    {
        return usedLayers;
    }

    public CompositeModel findCompositeFor( Class mixinType )
    {
        CompositeModel foundModel = null;
        for( LayerModel usedLayer : usedLayers )
        {
            CompositeModel module = usedLayer.findCompositeFor( mixinType, Visibility.application );
            if( module != null )
            {
                if( foundModel != null )
                {
                    throw new AmbiguousTypeException( mixinType );
                }
                foundModel = module;
            }
        }

        return foundModel;
    }

    public UsedLayersInstance newInstance( List<LayerInstance> usedLayerInstances )
    {
        return new UsedLayersInstance( usedLayerInstances );
    }

    public boolean visitModules( ModuleVisitor visitor )
    {
        for( LayerModel usedLayerModel : usedLayers )
        {
            if( !usedLayerModel.visitModules( visitor, Visibility.application ) )
            {
                return false;
            }
        }
        return true;
    }
}
