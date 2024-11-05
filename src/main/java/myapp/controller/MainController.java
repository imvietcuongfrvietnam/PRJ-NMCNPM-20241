package myapp.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Label slideshowLabel;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;

    private List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private ImageView imageView;

    @FXML
    public void initialize() {

        images.add(new Image(getClass().getResource("/image/Slideshow1.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow2.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow3.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow4.png").toExternalForm()));
        images.add(new Image(getClass().getResource("/image/Slideshow5.png").toExternalForm()));

        imageView = createImageView(images.get(currentImageIndex), 1470, 530, 30, 30);
        slideshowLabel.setGraphic(imageView);

        setButtonGraphics(previousButton, "/image/Back.png");
        setButtonGraphics(nextButton, "/image/Next.png");

        previousButton.setOnAction(e -> showPreviousImage());
        nextButton.setOnAction(e -> showNextImage());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private ImageView createImageView(Image image, double width, double height, double arcWidth, double arcHeight) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);

        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(imageView.fitWidthProperty());
        rectangle.heightProperty().bind(imageView.fitHeightProperty());
        rectangle.setArcWidth(arcWidth);
        rectangle.setArcHeight(arcHeight);
        imageView.setClip(rectangle);
        return imageView;
    }

    private void setImageForLabel(int imageIndex) {
        imageView.setImage(images.get(imageIndex));
        slideshowLabel.setGraphic(imageView);
    }

    private void setButtonGraphics(Button button, String imagePath) {
        ImageView iconView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        iconView.setFitWidth(30);
        iconView.setFitHeight(30);
        button.setGraphic(iconView);
    }

//    private void slideTransition(ImageView imageView, double fromX, double toX) {
//        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), imageView);
//        transition.setFromX(fromX);
//        transition.setToX(toX);
//        transition.setOnFinished(event -> setImageForLabel(currentImageIndex));
//        transition.play();
//    }

    @FXML
    private void showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % images.size();
        setImageForLabel(currentImageIndex);
    }

    @FXML
    private void showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
        setImageForLabel(currentImageIndex);
    }
}
