package edu.uni.lab.utility;

import javafx.scene.image.Image;

public class Texture {
	private final Image image;
	private final int width;
	private final int height;

	public Texture(Image image, int width, int height) {
		this.image = image;
		this.width = width;
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}