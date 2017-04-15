
public class Motion {
	public static void motionExec(){
		keyMotion();
		ObjectManager.objectMotion();
	}
	
	private static boolean wPress = false;
	private static boolean aPress = false;
	private static boolean sPress = false;
	private static boolean dPress = false;
	public static char key;
	public static char releaseKey;
	public static void keyMotion(){
		switch(key){
		case 'w':
			wPress = true;
			key = 0;
			break;
		case 'a':
			aPress = true;
			key = 0;
			break;
		case 's':
			sPress = true;
			key = 0;
			break;
		case 'd':
			dPress = true;
			key = 0;
			break;
		default:
			break;
		}
		
		switch(releaseKey){
		case 'w':
			wPress = false;
			releaseKey = 0;
			key = 0;
			break;
		case 'a':
			aPress = false;
			releaseKey = 0;
			key = 0;
			break;
		case 's':
			sPress = false;
			releaseKey = 0;
			key = 0;
			break;
		case 'd':
			dPress = false;
			releaseKey = 0;
			key = 0;
			break;
		default:
			break;
		}
		
		if(wPress) LayerManager.player.move('w');
		if(aPress) LayerManager.player.move('a');
		if(sPress) LayerManager.player.move('s');
		if(dPress) LayerManager.player.move('d');
	}
}
