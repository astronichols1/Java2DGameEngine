import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LayerManager {
	public static Player player = new Player(50, 100);
	public static Ground ground = new Ground(new File("textures/ground/ground1.jpg"), 0, 0);
	public static Shade shade = new Shade();
	public static WorldObject[] objects;
	public static LightSource[] lights;
	public static WorldChanger[] wChange;
	public static LightSource cursorLight;
	public static ArrayList<GuiElement> gui = new ArrayList<GuiElement>();
	public static boolean drawWorldLoaders;
	public static boolean drawShade;
	
	public LayerManager(){
		drawWorldLoaders = true;
		drawShade = true;
		AssetLoader.loadAssets();
		wChange = new WorldChanger[0];
		objects = new WorldObject[2];
		cursorLight = new LightSource(700, 200, 0, Draw.height, true, 200, 0, 200);
		objects[0] = new WorldObject(new File("textures/objects/world/object1.png"), 200, 90);
		objects[1] = new WorldObject(new File("textures/objects/world/fire2.gif"), 200, 400, true);
		objects[1].shadow = 0;
		objects[1].collision = false;
		objects[0].objectMotion.move(0.8, 100, 900, true);
		lights = new LightSource[2];
		//for(int i=0; i<objects.length; i++){
			//lights[i] = new LightSource(300, 230, 0, Draw.height, 156, 42, 0);
		//}
		lights[0] = new LightSource(600, 240, 200, 550, true, 156, 42, 0);
		lights[1] = new LightSource(300, 240, 600, 600, false, 156, 42, 0);
		lights[1].setFlicker(true);
		gui.add(new GuiElement("Hello", Color.WHITE, 100, 400, false));
		gui.add(new GuiElement(new File("textures/gui/items/orb3.png"), 25, Color.WHITE, 300, 300, true));
		reDraw();
	}
	
	public static void reDraw(){
		Render.updated();
		///////////////////////////////////////////////////////////////////////////////
		if(drawShade) Shade.drawShade();
		///////////////////////////////////////////////////////////////////////////////
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			for (int i=0; i<objects.length; i++) {
				final int i2 = i;
				exec.submit(new Runnable() {
					@Override
					public void run() {
						if(objects[i2].shadow==1)Render.drawLightCollisions(objects[i2].graphic, objects[i2].X-ground.X, objects[i2].Y-ground.Y);
					}
				});
			}
		} finally {exec.shutdown();}
		try {exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);} catch (InterruptedException e) {e.printStackTrace();}
		//Render.drawLights(cursorLight, cursorLight.X+MouseHandler.mouseX+cursorLight.getRandMotion(), cursorLight.Y-MouseHandler.mouseY+lights[0].getRandMotion());
		///////////////////////////////////////////////////////////////////////////////
		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			for (int i=0; i<lights.length; i++) {
				final int i2 = i;
				exec.submit(new Runnable() {
					@Override
					public void run() {
						Render.drawLights(lights[i2], lights[i2].X-ground.X+lights[i2].getRandMotion(), lights[i2].Y-ground.Y+lights[i2].getRandMotion());
					}
				});
			}
		} finally {exec.shutdown();}
		try {exec.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);} catch (InterruptedException e) {e.printStackTrace();}
		///////////////////////////////////////////////////////////////////////////////
		Render.fromLargerArray(ground.image, ground.X, ground.Y);
		for(int i=0; i<objects.length; i++){
			Render.fromArray(objects[i].graphic, objects[i].X-ground.X, objects[i].Y-ground.Y);
			objects[i].animTick();
		}
		///////////////////////////////////////////////////////////////////////////////
		Render.fromArray(player.graphic, player.X, player.Y);
		///////////////////////////////////////////////////////////////////////////////
		for(int i=0; i<gui.size(); i++){
			double relativeX = gui.get(i).isStatic ? 0 : ground.X;
			double relativeY = gui.get(i).isStatic ? 0 : ground.Y;
			Render.fromArrayNoLight(gui.get(i).graphic, gui.get(i).X-relativeX, gui.get(i).Y-relativeY);
		}
		///////////////////////////////////////////////////////////////////////////////
		if(drawWorldLoaders){
			for(int i=0; i<wChange.length; i++){
				Render.fromArray(wChange[i].graphic, wChange[i].X-ground.X, wChange[i].Y-ground.Y);
				System.out.println("here");
			}
		}
	}
	
	public static void addObject(String objFile, int X, int Y, boolean animated){
		WorldObject[] temp = new WorldObject[objects.length+1];
		for(int i=0; i<objects.length; i++){
			temp[i] = objects[i];
		}
		objects = new WorldObject[temp.length];
		for(int i=0; i<temp.length-1; i++){
			objects[i] = temp[i];
		}
		if(animated) objects[objects.length-1] = new WorldObject(new File(objFile), (double)X, (double)Y, true);
		else objects[objects.length-1] = new WorldObject(new File(objFile), (double)X, (double)Y);
	}
	
	public static void remObject(int index){
		if(objects.length <= 0) return;
		WorldObject[] temp = new WorldObject[objects.length-1];
		for(int i=index; i<objects.length-1; i++){
			objects[i] = objects[i+1];
		}
		for(int i=0; i<objects.length-1; i++){
			temp[i] = objects[i];
		}
		objects = new WorldObject[temp.length];
		for(int i=0; i<temp.length; i++){
			objects[i] = temp[i];
		}
	}
	
	public static void addLight(int cast, int brightness, int X, int Y, boolean shadows, int r, int g, int b){
		LightSource[] temp = new LightSource[lights.length+1];
		for(int i=0; i<lights.length; i++){
			temp[i] = lights[i];
		}
		lights = new LightSource[temp.length];
		for(int i=0; i<temp.length-1; i++){
			lights[i] = temp[i];
		}
		lights[lights.length-1] = new LightSource(cast, brightness, (double)X, (double)Y, shadows, r, g, b);
	}
	
	public static void removeLight(int index){
		if(lights.length <= 0) return;
		LightSource[] temp = new LightSource[lights.length-1];
		for(int i=index; i<lights.length-1; i++){
			lights[i] = lights[i+1];
		}
		for(int i=0; i<lights.length-1; i++){
			temp[i] = lights[i];
		}
		lights = new LightSource[temp.length];
		for(int i=0; i<temp.length; i++){
			lights[i] = temp[i];
		}
	}
	
	public static void addLoader(int width, int height, int X, int Y, String toWorld){
		WorldChanger[] temp = new WorldChanger[wChange.length+1];
		for(int i=0; i<wChange.length; i++){
			temp[i] = wChange[i];
		}
		wChange = new WorldChanger[temp.length];
		for(int i=0; i<temp.length-1; i++){
			wChange[i] = temp[i];
		}
		wChange[wChange.length-1] = new WorldChanger(width, height, X, Y, toWorld);
	}
	
	public static void removeLoader(int index){
		if(wChange.length <= 0) return;
		WorldChanger[] temp = new WorldChanger[wChange.length-1];
		for(int i=index; i<wChange.length-1; i++){
			wChange[i] = wChange[i+1];
		}
		for(int i=0; i<wChange.length-1; i++){
			temp[i] = wChange[i];
		}
		wChange = new WorldChanger[temp.length];
		for(int i=0; i<temp.length; i++){
			wChange[i] = temp[i];
		}
	}
}
