
public class ObjectManager {
	public ObjectManager(){
		
	}
	
	public static void objectMotion(){
		for(int i=0; i<LayerManager.objects.length; i++){
			LayerManager.objects[i].X += LayerManager.objects[i].objectMotion.update()[0];
			LayerManager.objects[i].Y += LayerManager.objects[i].objectMotion.update()[1];
		}
	}
}
