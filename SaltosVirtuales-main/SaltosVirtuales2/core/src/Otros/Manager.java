package Otros;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import javax.xml.soap.Text;

public class Manager {

    private AssetManager manager;
    public Manager(){
        manager=new AssetManager();
        manager.load("texturas/player.png", Texture.class);
        manager.load("texturas/spike.png", Texture.class);
        manager.load("texturas/spike2.png", Texture.class);
        manager.load("texturas/spike3.png", Texture.class);
        manager.load("texturas/overfloor.png",Texture.class);
        manager.load("texturas/floor.png", Texture.class);
        manager.load("audio/die.ogg", Sound.class);
        manager.load("audio/jump.ogg",Sound.class);
        manager.load("audio/song.ogg", Music.class);
        manager.load("texturas/gameover.png",Texture.class);
        manager.load("texturas/logo.png",Texture.class);
    }

    public Texture getTexture(String ruta){
      return  manager.get(ruta);
    }
    public Music getMusic(String ruta){
        return  manager.get(ruta);
    }
}
