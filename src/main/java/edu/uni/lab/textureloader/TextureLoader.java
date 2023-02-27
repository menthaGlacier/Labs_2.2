package edu.uni.lab.textureloader;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public final class TextureLoader {
	private static final Image devTexture;
	private static final Image mgrTexture;

	static {
		String imagePath = new File("").getAbsolutePath() + "/src/main/resources/images/dev.gif";
		devTexture = new Image(imagePath, 30.0, 30.0, false, true);

		imagePath = new File("").getAbsolutePath() + "/src/main/resources/images/mgr.png";
		mgrTexture = new Image(imagePath, 30.0, 30.0, false, true);
	}

	public static ImageView createMgrImageView() { return new ImageView(mgrTexture); }
	public static ImageView createDevImageView() { return new ImageView(devTexture); }

	public static double getMgrTextureWidth() { return mgrTexture.getWidth(); }
	public static double getMgrTextureHeight() { return mgrTexture.getHeight(); }
	public static double getDevTextureWidth() { return devTexture.getWidth(); }
	public static double getDevTextureHeight() { return devTexture.getHeight(); }
}
