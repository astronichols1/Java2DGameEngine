import java.io.File;
import java.util.Arrays;

public class AssetLoader {
	static WorldObject[] objects;
	static Ground[] maps;
	static File objectPath = new File("textures/objects/world");
	static File mapPath = new File("textures/ground");
	static File[] objectLoad;
	static File[] mapList;
	public static void loadAssets(){
		objectLoad = objectPath.listFiles();
		mapList = mapPath.listFiles();
	}
	
	private static void loadObjects(File[] f){
		
	}
	
	public static String getObjectLiet(){
		String out="";
		for(int i=0; i<objectLoad.length; i++){
			out+=objectLoad[i];
			out+="\n";
		}
		return out;
	}
	
	public static String getMapLiet(){
		String out="";
		for(int i=0; i<mapList.length; i++){
			out+=mapList[i];
			out+="\n";
		}
		return out;
	}
}