/*
 * This file is part of Musicott software.
 *
 * Musicott software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Musicott library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Musicott. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.musicott;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Octavio Calleya
 *
 */
public class MainPreloader extends Preloader {
	
	private Stage preloaderStage;
	protected Scene preloaderScene;
	private ProgressBar preloaderProgressBar;
	private Label title;
	
	public MainPreloader() {
	}
	
	@Override
    public void init() throws Exception {
        Platform.runLater(() -> {
            title = new Label("Loading track library...");
            preloaderProgressBar = new ProgressBar();
            preloaderProgressBar.setId("preloaderProgressBar");
            preloaderProgressBar.setPrefSize(470.0, 20.0);
            title.setTextAlignment(TextAlignment.CENTER);
            VBox root = new VBox(title, preloaderProgressBar);
            root.setAlignment(Pos.CENTER);
            preloaderScene = new Scene(root, 500, 50);
        });
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set preloader scene and show stage.
        preloaderStage = primaryStage;
        preloaderStage.setTitle("Musicott");
        preloaderStage.getIcons().add(new Image("file:resources/images/musicotticon.png"));
        preloaderStage.setScene(preloaderScene);
        preloaderStage.setResizable(false);
        preloaderStage.show();
	}
	
	@Override
	public void handleProgressNotification(ProgressNotification info) {
		preloaderProgressBar.setProgress(info.getProgress());
		title.setText("Progress "+info.getProgress()+" of 1");
	}

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        // Handle state change notifications.
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                // Called after MainApp#start is called.
                break;
            case BEFORE_INIT:
                // Called before MainApp#init is called.
                break;
            case BEFORE_START:
                // Called after MainApp#init and before MainApp#start is called.
	            preloaderStage.close();
                break;
        }
    }
}