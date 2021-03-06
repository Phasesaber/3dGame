package engineTester;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

public class MainGameLoop {
	public static void main(String[] args) {

		DisplayManager.createDisplay();

		Loader loader = new Loader();
		
		StaticShader shader = new StaticShader();
		
		Renderer rend = new Renderer(shader);
		
		
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		TexturedModel texmod = new TexturedModel(model, texture);
		Entity entity = new Entity(texmod, new Vector3f(0,0,-50),0,0,0,1);
		
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			entity.increasePosition(0f, 0.0f, -0.01f);
			entity.increaseRotation(0.25f,0.5f,1);
			camera.move();
			rend.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			rend.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
