package Chapter3;

import MainMenu.ChapterMenu;
import MainMenu.GameLauncher;
import Entity.KeyHandlers;

import Entity.Player1C3;
import Entity.BlackP2;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

public class Chapter3 extends Pane {
    private GraphicsContext gc;
    private final GameLauncher gameLauncher;
    private MediaPlayer chapter3MusicPlayer;
    private boolean isPlaying;
    private boolean isPaused;
    private ChapterMenu chapterMenu;

    private Stage stage;
    private StackPane root;
    private StackPane overlayPane;
    private int count = 0;
    private javafx.animation.AnimationTimer gameLoop;

    private final int originalTileSize = 20;  // Kích thước gốc của tile
    private final int scale = 3;              // Tỷ lệ scale
    private final int tileSize = originalTileSize * scale;  // Kích thước của tile sau khi scale

    private final int maxScreenCol = 23;
    private final int maxScreenRow = 13;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    public Image backgroundImage;
    private Player1C3 player1;
    private BlackP2 player2;
    KeyHandlers keyH = new KeyHandlers();
    KeyHandlers keyH1 = new KeyHandlers();
    private Canvas canvas;



    public Chapter3(GameLauncher gameLauncher, ChapterMenu chapterMenu){
        this.gameLauncher = gameLauncher;
        this.chapterMenu = chapterMenu;
    }



    public void showChapter3(Stage stage) {
        isPlaying = true;
        isPaused = false;
        this.stage = stage;
//        String musicPath = "D:/BMW/Project_OOP_IT3100/res/Sound/Chapter3_sound.mp3";
//        Media music = new Media(Paths.get(musicPath).toUri().toString());
        Media music = new Media(new File("res/Sound/Chapter3_sound.mp3").toURI().toString());
        chapter3MusicPlayer = new MediaPlayer(music);

        // Bat dau phat nhac cua Chapter 1
        chapter3MusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        chapter3MusicPlayer.setVolume(0.5);
        chapter3MusicPlayer.play();

        // tao man hinh cot truyen
        Label storyLabel1 = new Label("Crimson đã bị đánh bại, một tiếng nổ lớn vang lên, bóng dáng ai đó xuất hiện dần sau làn khói.");
        storyLabel1.setFont(new Font("Arial", 24));
        storyLabel1.setTextAlignment(TextAlignment.CENTER);
        storyLabel1.setStyle("-fx-text-fill: white;");

        Label storyLabel2 = new Label("Black đã chính thức xuất hiện, tạo nên cuộc chiến sống còn với Ngộ Không...");
        storyLabel2.setFont(new Font("Arial", 24));
        storyLabel2.setTextAlignment(TextAlignment.CENTER);
        storyLabel2.setStyle("-fx-text-fill: white;");
        AnchorPane.setLeftAnchor(storyLabel2, 260.0);
        AnchorPane.setTopAnchor(storyLabel2, 410.0);

        AnchorPane story = new AnchorPane(storyLabel2);
        StackPane storyPane = new StackPane(storyLabel1, story);
        storyPane.setStyle("-fx-background-color: black;");

        Scene storyScene = new Scene(storyPane, 1380, 800);

        PauseTransition delay = new PauseTransition(Duration.seconds(10));
        delay.setOnFinished(e -> {
            showChapter3Background(stage);
        });
        delay.play();
        // thêm thao tac nhan nut space de co the bo qua
        storyScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                delay.stop();
                showChapter3Background(stage);
            }
        });

        stage.setScene(storyScene);
        stage.setTitle("Chapter 3: Story Intro");
        stage.show();



    }

    public void showChapter3Background(Stage stage) {
        // Tạo giao diện cho Chapter 1
        this.stage = stage;
        if (player1 == null) {
            player1 = new Player1C3(this, keyH); // Chỉ khởi tạo nếu chưa có
        }
        if (player2 == null) {
            player2 = new BlackP2(this, keyH1);  // Chỉ khởi tạo Player 2 nếu chưa có
        }
        if (canvas == null){
            canvas = new Canvas(screenWidth, screenHeight);
            getChildren().add(canvas);
        }
        gc = canvas.getGraphicsContext2D();

        //Vẽ background
//        String backgroundPath = "D:/BMW/Project_OOP_IT3100/res/Background/background_chap2chap3.png";
//
//        backgroundImage = new Image(Paths.get(backgroundPath).toUri().toString());

        backgroundImage = new Image(new File("res/Background/background_chap2chap3.png").toURI().toString());

        // Vẽ background đã scale
        gc.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight);


        // Nut pause
//        String pauseButtonPath = "D:/BMW/Project_OOP_IT3100/res/ChapterImage/pause_button.png";
//        Image pauseButtonImage = new Image(Paths.get(pauseButtonPath).toUri().toString());
        Image pauseButtonImage = new Image(new File("res/ChapterImage/pause_button.png").toURI().toString());
        ImageView pauseButtonView = new ImageView(pauseButtonImage);
        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseButtonView);
        pauseButton.setStyle("-fx-background-color: transparent;");
        pauseButton.setLayoutX(665);
        pauseButton.setLayoutY(10);

        PauseMenu pauseMenu = new PauseMenu(this, chapter3MusicPlayer, gameLauncher);
        pauseButton.setOnAction(e ->{
            isPaused = !isPaused;  // Đổi trạng thái Pause
            if (isPaused) {
                if (gameLoop != null) gameLoop.stop(); // Dừng vòng lặp game
                pauseMenu.showPauseMenu(stage); // Hiển thị menu Pause
                if (chapter3MusicPlayer != null) chapter3MusicPlayer.pause(); // Dừng nhạc
                count ++;
            } else {
                resumeGameActions(); // Tiếp tục game
            }
        });

        StackPane fightPane = new StackPane();
//        String fightPath = "D:/BMW/Project_OOP_IT3100/res/ChapterImage/fight_screen.png";
//        Image fightImage = new Image(Paths.get(fightPath).toUri().toString());

        Image fightImage = new Image(new File("res/ChapterImage/fight_screen.png").toURI().toString());
        ImageView fightView = new ImageView(fightImage);
        fightPane.getChildren().add(fightView);
        if (count == 0){
            fightPane.setVisible(true);
            PauseTransition fightTransition = new PauseTransition(Duration.seconds(1.2));
            fightTransition.setOnFinished(e -> {
                fightPane.setVisible(false);
            });
            fightTransition.play();
        } else {
            fightPane.setVisible(false);
        }
        PauseTransition result = new PauseTransition(Duration.seconds(5));

        result.play();
        // Tạo giao diện chính
        AnchorPane pausePane = new AnchorPane(pauseButton);
        root = new StackPane(canvas, fightPane, pausePane);  // Thêm canvas vào root
        createOverlayPane(stage);
        // Tạo scene và gắn vào stage
        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setTitle("Chapter 3");
        stage.show();

        // Bắt đầu các hành động game (gọi thread game loop)
        // Thêm Debug
        scene.setOnKeyPressed(event -> {
//            System.out.println("Key pressed: " + event.getCode());
            keyH.handleKeyPressed(event);   // Xử lý sự kiện cho Player 1
            keyH1.handleKeyPressed(event);  // Xử lý sự kiện cho Player
        });

        scene.setOnKeyReleased(event -> {
//            System.out.println("Key released: " + event.getCode());
            keyH.handleKeyReleased(event);  // Xử lý sự kiện thả phím cho Player 1
            keyH1.handleKeyReleased(event); // Xử lý sự kiện thả phím cho Player 2
        });




        //Player1
//        scene.setOnKeyPressed(event -> keyH1.handleKeyPressed(event));
//        scene.setOnKeyReleased(event -> keyH1.handleKeyReleased(event));




        canvas.setFocusTraversable(true);
        canvas.requestFocus();


        startGameLoop(gc);
    }



    public void resumeGameActions() {
        isPaused = false;
        // Tiếp tục vòng lặp game nếu đã tồn tại
        if (gameLoop != null) {
            gameLoop.start();
        }
        // Tiếp tục nhạc nếu đã bị tạm dừng
        if (chapter3MusicPlayer != null && !chapter3MusicPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            chapter3MusicPlayer.play();
        }
        // Đảm bảo canvas lấy lại focus
        if (canvas != null) {
            canvas.requestFocus();
        }
    }


    private void createOverlayPane(Stage stage){
        overlayPane = new StackPane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        overlayPane.setVisible(false);

        ImageView resultView = new ImageView();
        resultView.setFitWidth(400);
        resultView.setFitHeight(400);

//        String continueButtonPath = "D:/BMW/Project_OOP_IT3100/res/ChapterImage/continue_button.png";
//        Image continueButtonImage = new Image(Paths.get(continueButtonPath).toUri().toString());
        Image continueButtonImage = new Image(new File("res/ChapterImage/continue_button.png").toURI().toString());
        ImageView continueButtonView = new ImageView(continueButtonImage);
        Button continueButton = new Button();
        continueButton.setGraphic(continueButtonView);
        continueButton.setStyle("-fx-background-color: transparent;");
        continueButton.setOnAction(e -> chapterMenu.showChapterMenu(stage));
        AnchorPane.setTopAnchor(continueButton, 505.0);
        AnchorPane.setLeftAnchor(continueButton, 645.0);

        StackPane.setAlignment(resultView, javafx.geometry.Pos.CENTER);
        //StackPane.setAlignment(continueButton, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(continueButton, new javafx.geometry.Insets(20));

        AnchorPane continuePane = new AnchorPane(continueButton);
        overlayPane.getChildren().addAll(resultView, continuePane);
        root.getChildren().add(overlayPane);
    }

    private void showEndOverlay(String result){
        Platform.runLater(() -> {
            if (chapter3MusicPlayer != null){
                chapter3MusicPlayer.stop();
            }

            // lay hinh anh thang/ thua
//            String resultPath = "D:/BMW/Project_OOP_IT3100/res/ChapterImage/" + result.toLowerCase() + ".png";
//            Image resultImage = new Image(Paths.get(resultPath).toUri().toString());
            Image resultImage = new Image(new File("res/ChapterImage/" + result.toLowerCase() + ".png").toURI().toString());
            ImageView resultView = (ImageView) overlayPane.getChildren().get(0);
            resultView.setImage(resultImage);
            overlayPane.setVisible(true);
        });
    }
    private void startGameLoop(GraphicsContext gc) {
        if (gameLoop != null) {
            gameLoop.start();
            return;
        }
        // Tạo vòng lặp mới nếu chưa tồn tại
        gameLoop = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isPaused) {
                    return; // Dừng vẽ khi game bị tạm dừng
                }

                // Cập nhật trạng thái game
                player1.update();
                player2.update();

                // Kiểm tra chiêu 1 của player2
                if (player1.isDamageFrame() && player1.getAttackBox().intersects(player2.getBody())) {
                    player2.takeDamage(3); // Chiêu 1 gây sát thương
                }

                // Kiểm tra chiêu 2 của player2
                if (player1.isDamageFrame() && player1.getAttackBox().intersects(player2.getBody())) {
                    player2.takeDamage(5); // Chiêu 2 gây sát thương mạnh hơn
                }

                // Kiểm tra chiêu 1 của player2
                if (player2.isDamageFrame() && player2.getAttackBox1().intersects(player1.getBody())) {
                    player1.takeDamage(3); // Chiêu 1 gây sát thương
                }

                if (player2.isDamageFrame() && player2.getAttackBox2().intersects(player1.getBody())) {
                    player1.takeDamage(1); // Chiêu 1 gây sát thương
                }


                // Kiểm tra chiêu 2 của player2
                if (player2.isDamageFrame() && player2.getAttackBox3().intersects(player1.getBody())) {
                    player1.takeDamage(30); // Chiêu 2 gây sát thương mạnh hơn
                }

                if (player1.getHealth() == 0) {
                    stop(); // Dừng vòng lặp game
                    showEndOverlay("defeat_screen"); // Hiển thị màn hình thua
                    return;
                }
                if (player2.getHealth() == 0) {
                    stop(); // Dừng vòng lặp game
                    showEndOverlay("victory_screen"); // Hiển thị màn hình thắng
                    return;
                }

                // Xóa màn hình và vẽ lại background
                gc.clearRect(0, 0, screenWidth, screenHeight);
                gc.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight);
                // Vẽ lại nhân vật
                player2.draw(gc);
                player1.draw(gc);

            }
        };


        // Bắt đầu vòng lặp game
        gameLoop.start();
    }
}