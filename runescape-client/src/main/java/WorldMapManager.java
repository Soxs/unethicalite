import java.util.LinkedList;
import net.runelite.mapping.ObfuscatedName;
import java.util.HashMap;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.Implements;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import net.runelite.mapping.Export;
@ObfuscatedName("ha")
@Implements("WorldMapManager")
public final class WorldMapManager {
	@ObfuscatedName("o")
	@Export("loaded")
	boolean loaded = false;

	@ObfuscatedName("q")
	@Export("loadStarted")
	boolean loadStarted = false;

	@ObfuscatedName("f")
	@ObfuscatedSignature(descriptor = "Lii;")
	@Export("mapAreaData")
	WorldMapAreaData mapAreaData;

	@ObfuscatedName("u")
	@ObfuscatedSignature(descriptor = "Lqj;")
	@Export("compositeTextureSprite")
	SpritePixels compositeTextureSprite;

	@ObfuscatedName("c")
	@Export("icons")
	HashMap icons;

	@ObfuscatedName("w")
	@ObfuscatedSignature(descriptor = "[[Lhp;")
	@Export("regions")
	WorldMapRegion[][] regions;

	@ObfuscatedName("z")
	@Export("scaleHandlers")
	HashMap scaleHandlers = new HashMap();

	@ObfuscatedName("j")
	@ObfuscatedSignature(descriptor = "[Lql;")
	@Export("mapSceneSprites")
	IndexedSprite[] mapSceneSprites;

	@ObfuscatedName("h")
	@ObfuscatedSignature(descriptor = "Llc;")
	@Export("geographyArchive")
	final AbstractArchive geographyArchive;

	@ObfuscatedName("a")
	@ObfuscatedSignature(descriptor = "Llc;")
	@Export("groundArchive")
	final AbstractArchive groundArchive;

	@ObfuscatedName("d")
	@Export("fonts")
	final HashMap fonts;

	@ObfuscatedName("n")
	@ObfuscatedGetter(intValue = 1353770129)
	@Export("tileX")
	int tileX;

	@ObfuscatedName("x")
	@ObfuscatedGetter(intValue = 1414469824)
	@Export("tileY")
	int tileY;

	@ObfuscatedName("g")
	@ObfuscatedGetter(intValue = -1875214775)
	@Export("tileWidth")
	int tileWidth;

	@ObfuscatedName("p")
	@ObfuscatedGetter(intValue = 2069231743)
	@Export("tileHeight")
	int tileHeight;

	@ObfuscatedName("b")
	@ObfuscatedGetter(intValue = 316879279)
	@Export("pixelsPerTile")
	public int pixelsPerTile = 0;

	@ObfuscatedSignature(descriptor = "([Lql;Ljava/util/HashMap;Llc;Llc;)V")
	public WorldMapManager(IndexedSprite[] var1, HashMap var2, AbstractArchive var3, AbstractArchive var4) {
		this.mapSceneSprites = var1;
		this.fonts = var2;
		this.geographyArchive = var3;
		this.groundArchive = var4;
	}

	@ObfuscatedName("o")
	@ObfuscatedSignature(descriptor = "(Llc;Ljava/lang/String;ZB)V", garbageValue = "65")
	@Export("load")
	public void load(AbstractArchive var1, String var2, boolean var3) {
		if (!this.loadStarted) {
			this.loaded = false;
			this.loadStarted = true;
			System.nanoTime();
			int var4 = var1.getGroupId(WorldMapCacheName.field2867.name);
			int var5 = var1.getFileId(var4, var2);
			Buffer var6 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2867.name, var2));
			Buffer var7 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2861.name, var2));
			System.nanoTime();
			System.nanoTime();
			this.mapAreaData = new WorldMapAreaData();
			try {
				this.mapAreaData.init(var6, var7, var5, var3);
			} catch (IllegalStateException var19) {
				return;
			}
			this.mapAreaData.getOriginX();
			this.mapAreaData.getOriginPlane();
			this.mapAreaData.getOriginY();
			this.tileX = this.mapAreaData.getRegionLowX() * 64;
			this.tileY = this.mapAreaData.getRegionLowY() * 4096;
			this.tileWidth = (this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1) * 64;
			this.tileHeight = (this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1) * 64;
			int var16 = this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1;
			int var9 = this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1;
			System.nanoTime();
			System.nanoTime();
			AbstractWorldMapIcon.method5045();
			this.regions = new WorldMapRegion[var16][var9];
			Iterator var10 = this.mapAreaData.worldMapData0Set.iterator();
			while (var10.hasNext()) {
				WorldMapData_0 var11 = ((WorldMapData_0) (var10.next()));
				int var12 = var11.regionX;
				int var13 = var11.regionY;
				int var14 = var12 - this.mapAreaData.getRegionLowX();
				int var15 = var13 - this.mapAreaData.getRegionLowY();
				this.regions[var14][var15] = new WorldMapRegion(var12, var13, this.mapAreaData.getBackGroundColor(), this.fonts);
				this.regions[var14][var15].initWorldMapData0(var11, this.mapAreaData.iconList);
			} 
			for (int var17 = 0; var17 < var16; ++var17) {
				for (int var18 = 0; var18 < var9; ++var18) {
					if (this.regions[var17][var18] == null) {
						this.regions[var17][var18] = new WorldMapRegion(this.mapAreaData.getRegionLowX() + var17, this.mapAreaData.getRegionLowY() + var18, this.mapAreaData.getBackGroundColor(), this.fonts);
						this.regions[var17][var18].initWorldMapData1(this.mapAreaData.worldMapData1Set, this.mapAreaData.iconList);
					}
				}
			}
			System.nanoTime();
			System.nanoTime();
			if (var1.isValidFileName(WorldMapCacheName.field2863.name, var2)) {
				byte[] var20 = var1.takeFileByNames(WorldMapCacheName.field2863.name, var2);
				this.compositeTextureSprite = class147.method3105(var20);
			}
			System.nanoTime();
			var1.clearGroups();
			var1.clearFiles();
			this.loaded = true;
		}
	}

	@ObfuscatedName("q")
	@ObfuscatedSignature(descriptor = "(B)V", garbageValue = "110")
	@Export("clearIcons")
	public final void clearIcons() {
		this.icons = null;
	}

	@ObfuscatedName("f")
	@ObfuscatedSignature(descriptor = "(IIIIIIIII)V", garbageValue = "-1493726072")
	@Export("drawTiles")
	public final void drawTiles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
		int[] var9 = Rasterizer2D.Rasterizer2D_pixels;
		int var10 = Rasterizer2D.Rasterizer2D_width;
		int var11 = Rasterizer2D.Rasterizer2D_height;
		int[] var12 = new int[4];
		Rasterizer2D.Rasterizer2D_getClipArray(var12);
		WorldMapRectangle var13 = this.createWorldMapRectangle(var1, var2, var3, var4);
		float var14 = this.getPixelsPerTile(var7 - var5, var3 - var1);
		int var15 = ((int) (Math.ceil(((double) (var14)))));
		this.pixelsPerTile = var15;
		if (!this.scaleHandlers.containsKey(var15)) {
			WorldMapScaleHandler var16 = new WorldMapScaleHandler(var15);
			var16.init();
			this.scaleHandlers.put(var15, var16);
		}
		int var23 = var13.x + var13.width - 1;
		int var17 = var13.height + var13.y - 1;
		int var18;
		int var19;
		for (var18 = var13.x; var18 <= var23; ++var18) {
			for (var19 = var13.y; var19 <= var17; ++var19) {
				this.regions[var18][var19].drawTile(var15, ((WorldMapScaleHandler) (this.scaleHandlers.get(var15))), this.mapSceneSprites, this.geographyArchive, this.groundArchive);
			}
		}
		Rasterizer2D.Rasterizer2D_replace(var9, var10, var11);
		Rasterizer2D.Rasterizer2D_setClipArray(var12);
		var18 = ((int) (var14 * 64.0F));
		var19 = this.tileX * 4096 + var1;
		int var20 = this.tileY * 64 + var2;
		for (int var21 = var13.x; var21 < var13.x + var13.width; ++var21) {
			for (int var22 = var13.y; var22 < var13.y + var13.height; ++var22) {
				this.regions[var21][var22].method4658(var5 + var18 * (this.regions[var21][var22].regionX * 64 - var19) / 64, var8 - var18 * (this.regions[var21][var22].regionY * 64 - var20 + 64) / 64, var18);
			}
		}
	}

	@ObfuscatedName("u")
	@ObfuscatedSignature(descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZI)V", garbageValue = "-1764328690")
	@Export("drawElements")
	public final void drawElements(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13) {
		WorldMapRectangle var14 = this.createWorldMapRectangle(var1, var2, var3, var4);
		float var15 = this.getPixelsPerTile(var7 - var5, var3 - var1);
		int var16 = ((int) (var15 * 64.0F));
		int var17 = this.tileX * 4096 + var1;
		int var18 = this.tileY * 64 + var2;
		int var19;
		int var20;
		for (var19 = var14.x; var19 < var14.width + var14.x; ++var19) {
			for (var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
				if (var13) {
					this.regions[var19][var20].initWorldMapIcon1s();
				}
				this.regions[var19][var20].method4668(var5 + var16 * (this.regions[var19][var20].regionX * 64 - var17) / 64, var8 - var16 * (this.regions[var19][var20].regionY * 64 - var18 + 64) / 64, var16, var9);
			}
		}
		if (var10 != null && var11 > 0) {
			for (var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
				for (var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
					this.regions[var19][var20].flashElements(var10, var11, var12);
				}
			}
		}
	}

	@ObfuscatedName("c")
	@ObfuscatedSignature(descriptor = "(IIIILjava/util/HashSet;IIB)V", garbageValue = "16")
	@Export("drawOverview")
	public void drawOverview(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
		if (this.compositeTextureSprite != null) {
			this.compositeTextureSprite.drawScaledAt(var1, var2, var3, var4);
			if (var6 > 0 && var6 % var7 < var7 / 2) {
				if (this.icons == null) {
					this.buildIcons0();
				}
				Iterator var8 = var5.iterator();
				while (true) {
					List var10;
					do {
						if (!var8.hasNext()) {
							return;
						}
						int var9 = ((Integer) (var8.next()));
						var10 = ((List) (this.icons.get(var9)));
					} while (var10 == null );
					Iterator var11 = var10.iterator();
					while (var11.hasNext()) {
						AbstractWorldMapIcon var12 = ((AbstractWorldMapIcon) (var11.next()));
						int var13 = var3 * (var12.coord2.x - this.tileX * 4096) / (this.tileWidth * 4096);
						int var14 = var4 - var4 * (var12.coord2.y - this.tileY * 64) / (this.tileHeight * 4096);
						Rasterizer2D.Rasterizer2D_drawCircleAlpha(var13 + var1, var14 + var2, 2, 16776960, 256);
					} 
				} 
			}
		}
	}

	@ObfuscatedName("w")
	@ObfuscatedSignature(descriptor = "(IIIIIIIIIIB)Ljava/util/List;", garbageValue = "-5")
	public List method4792(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
		LinkedList var11 = new LinkedList();
		if (!this.loaded) {
			return var11;
		} else {
			WorldMapRectangle var12 = this.createWorldMapRectangle(var1, var2, var3, var4);
			float var13 = this.getPixelsPerTile(var7, var3 - var1);
			int var14 = ((int) (64.0F * var13));
			int var15 = this.tileX * 4096 + var1;
			int var16 = this.tileY * 64 + var2;
			for (int var17 = var12.x; var17 < var12.width + var12.x; ++var17) {
				for (int var18 = var12.y; var18 < var12.height + var12.y; ++var18) {
					List var19 = this.regions[var17][var18].method4689(var5 + var14 * (this.regions[var17][var18].regionX * 64 - var15) / 64, var8 + var6 - var14 * (this.regions[var17][var18].regionY * 64 - var16 + 64) / 64, var14, var9, var10);
					if (!var19.isEmpty()) {
						var11.addAll(var19);
					}
				}
			}
			return var11;
		}
	}

	@ObfuscatedName("z")
	@ObfuscatedSignature(descriptor = "(IIIII)Lio;", garbageValue = "1749225445")
	@Export("createWorldMapRectangle")
	WorldMapRectangle createWorldMapRectangle(int var1, int var2, int var3, int var4) {
		WorldMapRectangle var5 = new WorldMapRectangle(this);
		int var6 = this.tileX * 4096 + var1;
		int var7 = this.tileY * 64 + var2;
		int var8 = var3 + this.tileX * 4096;
		int var9 = this.tileY * 64 + var4;
		int var10 = var6 / 64;
		int var11 = var7 / 64;
		int var12 = var8 / 64;
		int var13 = var9 / 64;
		var5.width = var12 - var10 + 1;
		var5.height = var13 - var11 + 1;
		var5.x = var10 - this.mapAreaData.getRegionLowX();
		var5.y = var11 - this.mapAreaData.getRegionLowY();
		if (var5.x < 0) {
			var5.width += var5.x;
			var5.x = 0;
		}
		if (var5.x > this.regions.length - var5.width) {
			var5.width = this.regions.length - var5.x;
		}
		if (var5.y < 0) {
			var5.height += var5.y;
			var5.y = 0;
		}
		if (var5.y > this.regions[0].length - var5.height) {
			var5.height = this.regions[0].length - var5.y;
		}
		var5.width = Math.min(var5.width, this.regions.length);
		var5.height = Math.min(var5.height, this.regions[0].length);
		return var5;
	}

	@ObfuscatedName("j")
	@ObfuscatedSignature(descriptor = "(B)Z", garbageValue = "56")
	@Export("isLoaded")
	public boolean isLoaded() {
		return this.loaded;
	}

	@ObfuscatedName("h")
	@ObfuscatedSignature(descriptor = "(I)Ljava/util/HashMap;", garbageValue = "-1481313256")
	@Export("buildIcons")
	public HashMap buildIcons() {
		this.buildIcons0();
		return this.icons;
	}

	@ObfuscatedName("a")
	@ObfuscatedSignature(descriptor = "(B)V", garbageValue = "70")
	@Export("buildIcons0")
	void buildIcons0() {
		if (this.icons == null) {
			this.icons = new HashMap();
		}
		this.icons.clear();
		for (int var1 = 0; var1 < this.regions.length; ++var1) {
			for (int var2 = 0; var2 < this.regions[var1].length; ++var2) {
				List var3 = this.regions[var1][var2].icons();
				Iterator var4 = var3.iterator();
				while (var4.hasNext()) {
					AbstractWorldMapIcon var5 = ((AbstractWorldMapIcon) (var4.next()));
					if (var5.hasValidElement()) {
						int var6 = var5.getElement();
						if (!this.icons.containsKey(var6)) {
							LinkedList var7 = new LinkedList();
							var7.add(var5);
							this.icons.put(var6, var7);
						} else {
							List var8 = ((List) (this.icons.get(var6)));
							var8.add(var5);
						}
					}
				} 
			}
		}
	}

	@ObfuscatedName("d")
	@ObfuscatedSignature(descriptor = "(IIB)F", garbageValue = "-85")
	@Export("getPixelsPerTile")
	float getPixelsPerTile(int var1, int var2) {
		float var3 = ((float) (var1)) / ((float) (var2));
		if (var3 > 8.0F) {
			return 8.0F;
		} else if (var3 < 1.0F) {
			return 1.0F;
		} else {
			int var4 = Math.round(var3);
			return Math.abs(((float) (var4)) - var3) < 0.05F ? ((float) (var4)) : var3;
		}
	}

	@ObfuscatedName("o")
	@ObfuscatedSignature(descriptor = "(II)F", garbageValue = "-2106238491")
	public static float method4818(int var0) {
		var0 &= 16383;
		return ((float) (((double) (((float) (var0)) / 16384.0F)) * 6.283185307179586));
	}

	@ObfuscatedName("ih")
	@ObfuscatedSignature(descriptor = "(III)V", garbageValue = "1427877552")
	@Export("updateItemPile")
	static final void updateItemPile(int var0, int var1) {
		NodeDeque var2 = Client.groundItems[FriendSystem.Client_plane][var0][var1];
		if (var2 == null) {
			Decimator.scene.removeGroundItemPile(FriendSystem.Client_plane, var0, var1);
		} else {
			long var3 = -99999999L;
			TileItem var5 = null;
			TileItem var6;
			for (var6 = ((TileItem) (var2.last())); var6 != null; var6 = ((TileItem) (var2.previous()))) {
				ItemComposition var7 = class258.ItemDefinition_get(var6.id);
				long var11 = ((long) (var7.price));
				if (var7.isStackable == 1) {
					var11 *= ((long) (var6.quantity + 1));
				}
				if (var11 > var3) {
					var3 = var11;
					var5 = var6;
				}
			}
			if (var5 == null) {
				Decimator.scene.removeGroundItemPile(FriendSystem.Client_plane, var0, var1);
			} else {
				var2.addLast(var5);
				TileItem var13 = null;
				TileItem var8 = null;
				for (var6 = ((TileItem) (var2.last())); var6 != null; var6 = ((TileItem) (var2.previous()))) {
					if (var6.id != var5.id) {
						if (var13 == null) {
							var13 = var6;
						}
						if (var13.id != var6.id && var8 == null) {
							var8 = var6;
						}
					}
				}
				long var9 = class394.calculateTag(var0, var1, 3, false, 0);
				Decimator.scene.newGroundItemPile(FriendSystem.Client_plane, var0, var1, SecureRandomFuture.getTileHeight(var0 * 128 + 64, var1 * 128 + 64, FriendSystem.Client_plane), var5, var9, var13, var8);
			}
		}
	}
}