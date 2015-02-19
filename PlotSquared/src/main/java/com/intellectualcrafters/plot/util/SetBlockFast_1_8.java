////////////////////////////////////////////////////////////////////////////////////////////////////
// PlotSquared - A plot manager and world generator for the Bukkit API                             /
// Copyright (c) 2014 IntellectualSites/IntellectualCrafters                                       /
//                                                                                                 /
// This program is free software; you can redistribute it and/or modify                            /
// it under the terms of the GNU General Public License as published by                            /
// the Free Software Foundation; either version 3 of the License, or                               /
// (at your option) any later version.                                                             /
//                                                                                                 /
// This program is distributed in the hope that it will be useful,                                 /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                                  /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                   /
// GNU General Public License for more details.                                                    /
//                                                                                                 /
// You should have received a copy of the GNU General Public License                               /
// along with this program; if not, write to the Free Software Foundation,                         /
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA                               /
//                                                                                                 /
// You can contact us via: support@intellectualsites.com                                           /
////////////////////////////////////////////////////////////////////////////////////////////////////

package com.intellectualcrafters.plot.util;

import static com.intellectualcrafters.plot.util.ReflectionUtils.getRefClass;

import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.intellectualcrafters.plot.util.ReflectionUtils.RefClass;
import com.intellectualcrafters.plot.util.ReflectionUtils.RefConstructor;
import com.intellectualcrafters.plot.util.ReflectionUtils.RefMethod;

/**
 * SetBlockFast class<br> Used to do fast world editing
 *
 * @author Empire92
 */
public class SetBlockFast_1_8 extends SetBlockManager {


    private static final RefClass classBlock = getRefClass("{nms}.Block");
    private static final RefClass classBlockPosition = getRefClass("{nms}.BlockPosition");
    private static final RefClass classIBlockData = getRefClass("{nms}.IBlockData");
    
    
    
    
    private static final RefClass classChunk = getRefClass("{nms}.Chunk");
    private static final RefClass classWorld = getRefClass("{nms}.World");
    private static final RefClass classCraftWorld = getRefClass("{cb}.CraftWorld");

    private static RefMethod methodGetHandle;
    private static RefMethod methodGetChunkAt;
    private static RefMethod methodA;
    private static RefMethod methodGetByCombinedId;

    private static RefConstructor constructorBlockPosition;
    
    /**
     * Constructor
     *
     * @throws NoSuchMethodException
     */
    public SetBlockFast_1_8() throws NoSuchMethodException {
        
        constructorBlockPosition = classBlockPosition.getConstructor(int.class, int.class, int.class);
        methodGetByCombinedId = classBlock.getMethod("getByCombinedId", int.class);
        
        methodGetHandle = classCraftWorld.getMethod("getHandle");
        methodGetChunkAt = classWorld.getMethod("getChunkAt", int.class, int.class);
        methodA = classChunk.getMethod("a", classBlockPosition, classIBlockData);
    }

    /**
     * Set the block at the location
     *
     * @param world   World in which the block should be set
     * @param x       X Coordinate
     * @param y       Y Coordinate
     * @param z       Z Coordinate
     * @param blockId Block ID
     * @param data    Block Data Value
     *
     * @return true
     */
    @Override
    public void set(final World world, final int x, final int y, final int z, final int blockId, final byte data) {

        final Object w = methodGetHandle.of(world).call();
        final Object chunk = methodGetChunkAt.of(w).call(x >> 4, z >> 4);
        final Object pos = constructorBlockPosition.create((int) (x & 0x0f), y, (int) (z & 0x0f));
        final Object id = methodGetByCombinedId.of(null).call(blockId + (data << 12));
        methodA.of(chunk).call(pos, id);

    }

    /**
     * Update chunks
     *
     * @param player Player whose chunks we're updating
     */
    @Override
    public void update(List<Chunk> chunks) {
        if (chunks.size() == 0) {
            return;
        }
        if (!PlotHelper.canSendChunk) {
            World world = chunks.get(0).getWorld();
            for (Chunk chunk : chunks) {
                world.refreshChunk(chunk.getX(), chunk.getZ());
            }
            return;
        }
        try {
            SendChunk.sendChunk(chunks);
        } catch (final Throwable e) {
            PlotHelper.canSendChunk = false;
        }
    }
}