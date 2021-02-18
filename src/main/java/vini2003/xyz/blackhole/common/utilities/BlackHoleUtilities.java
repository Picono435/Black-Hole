/*
 * MIT License
 *
 * Copyright (c) 2020 Chainmail Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package vini2003.xyz.blackhole.common.utilities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import vini2003.xyz.blackhole.client.utilities.BlackHoleClientUtilities;
import vini2003.xyz.blackhole.common.entity.BlackHoleEntity;

import java.util.List;

public class BlackHoleUtilities {
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	
	public static boolean isWithinDistance(int x1, int x2, int y1, int y2, int distance) {
		return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) < distance * distance;
	}
	
	public static void destroy(BlackHoleEntity entity, World world, int x, int y, int z, int radius) {
		int clearRadius = (int) (radius * 1.5F);
		
		int pickupRadius = (int) (radius * 2.5F);
		
		int chunkRadius = Math.max(1, radius / 16) + 6;
		
		for (int chunkX = (x >> 4) - chunkRadius; chunkX < (x >> 4) + chunkRadius; ++chunkX) {
			for (int chunkZ = (z >> 4) - chunkRadius; chunkZ < (z >> 4) + chunkRadius; ++chunkZ) {
				WorldChunk chunk = world.getChunk(chunkX, chunkZ);
				
				// Select up to four random blocks for removal.
				int rX = chunk.getWorld().random.nextInt(16);
				int rZ = chunk.getWorld().random.nextInt(16);
				
				int rCX = (chunkX * 16 + rX - x) * (chunkX * 16 + rX - x);
				int rCZ = (chunkZ * 16 + rZ - z) * (chunkZ * 16 + rZ - z);
				
				// If is within radius, proceed.
				if (rCX + rCZ < pickupRadius * pickupRadius) {
					BlockPos topPosition = chunk.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(chunkX * 16 + rX, 0, chunkZ * 16 + rZ)).add(0, -1, 0);
					
					BlockState state = chunk.getBlockState(topPosition);
					
					if (!state.isAir() && !(state.getBlock() instanceof FluidBlock)) {
						if (world.isClient) {
							PlayerEntity player = BlackHoleClientUtilities.getPlayer();
							
							// The player is within distance.
							// Add the removed block to the entity.
							if (((List) entity.getParticles()).size() < 2048 && isWithinDistance(topPosition.getX(), (int) player.getX(), topPosition.getZ(), (int) player.getZ(), 64)) {
								entity.addParticle(new BlackHoleEntity.Particle(new Vec3d(topPosition.getX(), topPosition.getY(), topPosition.getZ()), state));
							}
						} else {
							// Remove the block.
							if (world.random.nextInt(5) == 0) {
								world.setBlockState(topPosition, Blocks.AIR.getDefaultState());
							}
						}
					}
				}
				
				
				// Check if the chunk is immediately adjacent.
				boolean isAdjacent =
						chunkX == (x >> 4) - 1 && chunkZ == (z >> 4) - 1 ||
								chunkX == (x >> 4) - 1 && chunkZ == (z >> 4) ||
								chunkX == (x >> 4) - 1 && chunkZ == (z >> 4) + 1 ||
								chunkX == (x >> 4) && chunkZ == (z >> 4) + 1 ||
								chunkX == (x >> 4) && chunkZ == (z >> 4) - 1 ||
								chunkX == (x >> 4) && chunkZ == (z >> 4) ||
								chunkX == (x >> 4) + 1 && chunkZ == (z >> 4) - 1 ||
								chunkX == (x >> 4) + 1 && chunkZ == (z >> 4) ||
								chunkX == (x >> 4) + 1 && chunkZ == (z >> 4) + 1;
				
				// Get the positions of the chunk's corners.
				int cX = (chunkX * 16 - x) * (chunkX * 16 - x);
				int cZ = (chunkZ * 16 - z) * (chunkZ * 16 - z);
				int cX16 = ((chunkX + 1) * 16 - x) * ((chunkX + 1) * 16 - x);
				int cZ16 = ((chunkZ + 1) * 16 - z) * ((chunkZ + 1) * 16 - z);
				
				// If is adjacent, or within radius, proceed.
				if (isAdjacent ||
						(cX + cZ) <= clearRadius * clearRadius
						|| (cX16 + cZ) <= clearRadius * clearRadius
						|| (cX + cZ16) <= clearRadius * clearRadius
						|| (cX16 + cZ16) <= clearRadius * clearRadius
				) {
					boolean shouldUpdate = false;
					
					// Go over each section.
					for (ChunkSection section : chunk.getSectionArray()) {
						if (section != null) {
							int chunkY = section.getYOffset() >> 4;
							
							int cY = (chunkY * 16 - y) * (chunkY * 16 - y);
							int cY16 = ((chunkY + 1) * 16 - y) * ((chunkY + 1) * 16 - y);
							
							// If is adjacent and the section Y equals the entity's section Y,
							// or is within radius, proceed.
							if (isAdjacent && section.getYOffset() >> 4 == y >> 4 ||
									(cX + cZ + cY) <= clearRadius * clearRadius
									|| (cX16 + cZ + cY) <= clearRadius * clearRadius
									|| (cX + cZ16 + cY) <= clearRadius * clearRadius
									|| (cX16 + cZ16 + cY) <= clearRadius * clearRadius
									|| (cX + cZ + cY16) <= clearRadius * clearRadius
									|| (cX16 + cZ + cY16) <= clearRadius * clearRadius
									|| (cX + cZ16 + cY16) <= clearRadius * clearRadius
									|| (cX16 + cZ16 + cY16) <= clearRadius * clearRadius) {
								// If is empty, skip.
								if (section.isEmpty()) continue;
								
								for (int sectionX = 0; sectionX < 16; ++sectionX) {
									for (int sectionY = 0; sectionY < 16; ++sectionY) {
										for (int sectionZ = 0; sectionZ < 16; ++sectionZ) {
											int absoluteSectionX = chunkX * 16 + sectionX;
											int absoluteSectionY = section.getYOffset() + sectionY;
											int absoluteSectionZ = chunkZ * 16 + sectionZ;
											
											// If is within radius, proceed.
											if (
													(0 < x ? (absoluteSectionX - x) * (absoluteSectionX - x) : (x - absoluteSectionX) * (x - absoluteSectionX))
															+ (0 < y ? (absoluteSectionY - y) * (absoluteSectionY - y) : (y - absoluteSectionY) * (y - absoluteSectionY))
															+ (0 < z ? (absoluteSectionZ - z) * (absoluteSectionZ - z) : (z - absoluteSectionZ) * (z - absoluteSectionZ)) <= clearRadius * clearRadius) {
												if (!section.getBlockState(sectionX, sectionY, sectionZ).isAir() && section.getBlockState(sectionX, sectionY, sectionZ).getBlock() != Blocks.BEDROCK) {
													section.setBlockState(sectionX, sectionY, sectionZ, AIR);
													shouldUpdate = true;
												}
											}
										}
									}
								}
							}
						}
					}
					
					// Synchronize altered chunk.
					if (!world.isClient) {
						if (shouldUpdate) {
							chunk.markDirty();
							
							((ServerChunkManager) world.getChunkManager()).threadedAnvilChunkStorage.getPlayersWatchingChunk(new ChunkPos(chunkX, chunkZ), false).forEach(player -> player.networkHandler.sendPacket(new ChunkDataS2CPacket(chunk, 65535)));
						}
					}
				}
			}
		}
	}
}