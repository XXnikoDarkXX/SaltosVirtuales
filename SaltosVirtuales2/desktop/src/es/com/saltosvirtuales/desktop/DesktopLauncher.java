package es.com.saltosvirtuales.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import es.com.saltosvirtuales.Principal;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width=1024;
		config.height=500;
		config.resizable=false;
		config.title="Saltos Virtuales";
		new LwjglApplication(new Principal(), config);
	}
}
