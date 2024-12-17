package Entity;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import javafx.scene.image.Image;

import Chapter1.Chapter1;

public class AzureP2 extends Entity{
    public Player1C1 player11;

    private Body body;
    private Body attackBody;

    public int maxHealth = 3000; // Máu tối đa, giả sử là 3.000 để phù hợp với số trên hình ảnh
    public int currentHealth = 3000; // Máu hiện tại
    private int barWidth = 400; // Chiều rộng của thanh máu
    private int barHeight = 20; // Chiều cao của thanh máu
    private int originalY;

    private MediaPlayer skill1SoundPlayer;
    private MediaPlayer skill2SoundPlayer;
    private MediaPlayer runningSoundPlayer;

    private boolean damageFrame = false;
    private boolean isMoving = false; // Biến để kiểm tra xem nhân vật có di chuyển hay không
    public boolean isUsingSkill1 = false;
    public boolean isUsingSkill2 = false;
    private boolean isUsingJump = false;
    private boolean isTakenHit = false;

    private final int animationSpeed = 15; // Tốc độ chuyển đổi khung hình
    private int animationFrame = 0; // Biến đếm khung hình hiện tại
    private int animationCounter = 0; // Biến đếm thời gian chuyển đổi khung hình
    private int skillCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill
    private int skillKCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill qua phím K
    private int takeHitCounter = 4;


    Chapter1 gp;
    public KeyHandlers keyH2;

    public boolean isDenfensed(){
        if(isTakenHit) return true;
        else return false;
    }

    public AzureP2(Chapter1 gp, KeyHandlers keyH2, Player1C1 player11){
        this.gp = gp;
        this.keyH2 = keyH2;
        this.player11 = player11;
        setDefaultValues();
        getPlayerImage();
        updateHealth(getHealth());
        originalY = y;
        body = new Body(x, y+ 100, 120, 180);
        attackBody = new Body(x, y, 200, 200);
        try {
            Media skill1Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Azure/SoundEffect/skill1SoundEfffect.mp3").toURI().toString());
            skill1SoundPlayer = new MediaPlayer(skill1Sound);

            Media skill2Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Azure/SoundEffect/skill2SoundEffect.mp3").toURI().toString());
            skill2SoundPlayer = new MediaPlayer(skill2Sound);

            Media runningSound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Azure/SoundEffect/runningSoundEffect.mp3").toURI().toString());
            runningSoundPlayer = new MediaPlayer(runningSound);
            runningSoundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Body getBody() {
        return body;
    }

    public Body getAttackBody() {
        return attackBody;
    }


    public void setDefaultValues(){
        x = 1100;
        y = 425;
        speed = 4;
        direction = "left";
    }

    private void drawHealthBar(GraphicsContext gc) {
        int healthBarX = 1380 - barWidth - 10;
        int healthBarY = 0;

        // Vẽ viền ngoài
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(healthBarX, healthBarY, barWidth + 4, barHeight + 4, 10, 10);

        // Vẽ nền thanh máu
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(healthBarX + 2, healthBarY + 2, barWidth, barHeight, 8, 8);

        // Vẽ thanh máu
        double healthRatio = (double)  currentHealth / maxHealth;
        double healthWidth = barWidth * healthRatio;
        gc.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.YELLOW)));
        gc.fillRoundRect(healthBarX + 2 + barWidth - healthWidth, healthBarY + 2, healthWidth, barHeight, 8, 8);
    }

    public void getPlayerImage() {
        try {
            // IDLE Right
            R_IDLE_1 = new Image(new File("res/Azure/Right/IDLE/1.png").toURI().toString());
            R_IDLE_2 = new Image(new File("res/Azure/Right/IDLE/2.png").toURI().toString());
            R_IDLE_3 = new Image(new File("res/Azure/Right/IDLE/3.png").toURI().toString());
            R_IDLE_4 = new Image(new File("res/Azure/Right/IDLE/4.png").toURI().toString());
            R_IDLE_5 = new Image(new File("res/Azure/Right/IDLE/5.png").toURI().toString());
            R_IDLE_6 = new Image(new File("res/Azure/Right/IDLE/6.png").toURI().toString());
            R_IDLE_7 = new Image(new File("res/Azure/Right/IDLE/7.png").toURI().toString());
            R_IDLE_8 = new Image(new File("res/Azure/Right/IDLE/8.png").toURI().toString());

            // IDLE Left
            L_IDLE_1 = new Image(new File("res/Azure/Left/IDLE/1.png").toURI().toString());
            L_IDLE_2 = new Image(new File("res/Azure/Left/IDLE/2.png").toURI().toString());
            L_IDLE_3 = new Image(new File("res/Azure/Left/IDLE/3.png").toURI().toString());
            L_IDLE_4 = new Image(new File("res/Azure/Left/IDLE/4.png").toURI().toString());
            L_IDLE_5 = new Image(new File("res/Azure/Left/IDLE/5.png").toURI().toString());
            L_IDLE_6 = new Image(new File("res/Azure/Left/IDLE/6.png").toURI().toString());
            L_IDLE_7 = new Image(new File("res/Azure/Left/IDLE/7.png").toURI().toString());
            L_IDLE_8 = new Image(new File("res/Azure/Left/IDLE/8.png").toURI().toString());

            // Run Right - Keycode Right
            R_Run_1 = new Image(new File("res/Azure/Right/Run/1.png").toURI().toString());
            R_Run_2 = new Image(new File("res/Azure/Right/Run/2.png").toURI().toString());
            R_Run_3 = new Image(new File("res/Azure/Right/Run/3.png").toURI().toString());
            R_Run_4 = new Image(new File("res/Azure/Right/Run/4.png").toURI().toString());
            R_Run_5 = new Image(new File("res/Azure/Right/Run/5.png").toURI().toString());
            R_Run_6 = new Image(new File("res/Azure/Right/Run/6.png").toURI().toString());
            R_Run_7 = new Image(new File("res/Azure/Right/Run/7.png").toURI().toString());
            R_Run_8 = new Image(new File("res/Azure/Right/Run/8.png").toURI().toString());

            // Run Left - Keycode Left
            L_Run_1 = new Image(new File("res/Azure/Left/Run/1.png").toURI().toString());
            L_Run_2 = new Image(new File("res/Azure/Left/Run/2.png").toURI().toString());
            L_Run_3 = new Image(new File("res/Azure/Left/Run/3.png").toURI().toString());
            L_Run_4 = new Image(new File("res/Azure/Left/Run/4.png").toURI().toString());
            L_Run_5 = new Image(new File("res/Azure/Left/Run/5.png").toURI().toString());
            L_Run_6 = new Image(new File("res/Azure/Left/Run/6.png").toURI().toString());
            L_Run_7 = new Image(new File("res/Azure/Left/Run/7.png").toURI().toString());
            L_Run_8 = new Image(new File("res/Azure/Left/Run/8.png").toURI().toString());

            // TakenHit Right
            R_Take_Hit_1 = new Image(new File("res/Azure/Right/TakenHit/1.png").toURI().toString());
            R_Take_Hit_2 = new Image(new File("res/Azure/Right/TakenHit/2.png").toURI().toString());
            R_Take_Hit_3 = new Image(new File("res/Azure/Right/TakenHit/3.png").toURI().toString());
            R_Take_Hit_4 = new Image(new File("res/Azure/Right/TakenHit/4.png").toURI().toString());

            // TakenHit Left
            L_Take_Hit_1 = new Image(new File("res/Azure/Left/TakenHit/1.png").toURI().toString());
            L_Take_Hit_2 = new Image(new File("res/Azure/Left/TakenHit/2.png").toURI().toString());
            L_Take_Hit_3 = new Image(new File("res/Azure/Left/TakenHit/3.png").toURI().toString());
            L_Take_Hit_4 = new Image(new File("res/Azure/Left/TakenHit/4.png").toURI().toString());

            // Attack1 Right - Keycode 1
            R_Attack1_1 = new Image(new File("res/Azure/Right/Attack1/1.png").toURI().toString());
            R_Attack1_2 = new Image(new File("res/Azure/Right/Attack1/2.png").toURI().toString());
            R_Attack1_3 = new Image(new File("res/Azure/Right/Attack1/3.png").toURI().toString());
            R_Attack1_4 = new Image(new File("res/Azure/Right/Attack1/4.png").toURI().toString());
            R_Attack1_5 = new Image(new File("res/Azure/Right/Attack1/5.png").toURI().toString());
            R_Attack1_6 = new Image(new File("res/Azure/Right/Attack1/6.png").toURI().toString());

            // Attack1 Left - Keycode 1
            L_Attack1_1 = new Image(new File("res/Azure/Left/Attack1/1.png").toURI().toString());
            L_Attack1_2 = new Image(new File("res/Azure/Left/Attack1/2.png").toURI().toString());
            L_Attack1_3 = new Image(new File("res/Azure/Left/Attack1/3.png").toURI().toString());
            L_Attack1_4 = new Image(new File("res/Azure/Left/Attack1/4.png").toURI().toString());
            L_Attack1_5 = new Image(new File("res/Azure/Left/Attack1/5.png").toURI().toString());
            L_Attack1_6 = new Image(new File("res/Azure/Left/Attack1/6.png").toURI().toString());

            // Attack2 Right - Keycode 2
            R_Attack2_1 = new Image(new File("res/Azure/Right/Attack2/1.png").toURI().toString());
            R_Attack2_2 = new Image(new File("res/Azure/Right/Attack2/2.png").toURI().toString());
            R_Attack2_3 = new Image(new File("res/Azure/Right/Attack2/3.png").toURI().toString());
            R_Attack2_4 = new Image(new File("res/Azure/Right/Attack2/4.png").toURI().toString());
            R_Attack2_5 = new Image(new File("res/Azure/Right/Attack2/5.png").toURI().toString());
            R_Attack2_6 = new Image(new File("res/Azure/Right/Attack2/6.png").toURI().toString());

            // Attack2 Left - Keycode 2
            L_Attack2_1 = new Image(new File("res/Azure/Left/Attack2/1.png").toURI().toString());
            L_Attack2_2 = new Image(new File("res/Azure/Left/Attack2/2.png").toURI().toString());
            L_Attack2_3 = new Image(new File("res/Azure/Left/Attack2/3.png").toURI().toString());
            L_Attack2_4 = new Image(new File("res/Azure/Left/Attack2/4.png").toURI().toString());
            L_Attack2_5 = new Image(new File("res/Azure/Left/Attack2/5.png").toURI().toString());
            L_Attack2_6 = new Image(new File("res/Azure/Left/Attack2/6.png").toURI().toString());

            // Death Right
            R_Death_1 = new Image(new File("res/Azure/Right/Death/1.png").toURI().toString());
            R_Death_2 = new Image(new File("res/Azure/Right/Death/2.png").toURI().toString());
            R_Death_3 = new Image(new File("res/Azure/Right/Death/3.png").toURI().toString());
            R_Death_4 = new Image(new File("res/Azure/Right/Death/4.png").toURI().toString());
            R_Death_5 = new Image(new File("res/Azure/Right/Death/5.png").toURI().toString());
            R_Death_6 = new Image(new File("res/Azure/Right/Death/6.png").toURI().toString());

            // Death Left
            L_Death_1 = new Image(new File("res/Azure/Left/Death/1.png").toURI().toString());
            L_Death_2 = new Image(new File("res/Azure/Left/Death/2.png").toURI().toString());
            L_Death_3 = new Image(new File("res/Azure/Left/Death/3.png").toURI().toString());
            L_Death_4 = new Image(new File("res/Azure/Left/Death/4.png").toURI().toString());
            L_Death_5 = new Image(new File("res/Azure/Left/Death/5.png").toURI().toString());
            L_Death_6 = new Image(new File("res/Azure/Left/Death/6.png").toURI().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHealth() {
        if(player11 != null && player11.isAttacking() && player11.getAttackBody().intersects(body)){
            isTakenHit = true;
            takeHitCounter = 4;
            isUsingSkill1 = false;
            isUsingSkill2 = false;
            isMoving = false;
            currentHealth -= 0.0001;
        }
        return currentHealth;
    }

    public void updateHealth(int damage) {
        getHealth();
    }

    public void update() {
        if (isTakenHit) {
            takeHitCounter--; // Giảm bộ đếm
            if (direction.equals("left")) {
                x += 1;
            } else if (direction.equals("right")) {
                x -= 1;
            }
            if (takeHitCounter <= 0) {
                isTakenHit = false; // Kết thúc trạng thái takeHit
            }
        }
        if (!isTakenHit) {
            if (keyH2.Pressed1) {
                isUsingSkill1 = true; // Bat dau su dung skill 1
                animationFrame = 0;
                playSkill1Sound();
                attackBody.setX(direction.equals("left") ? x + 70 : x + 280);
                skillCounter = 0;
            }
            if (keyH2.Pressed2) {
                isUsingSkill2 = true; // Bat dau su dung skill 2
                animationFrame = 0;
                playSkill2Sound();
                attackBody.setX(direction.equals("left") ? x + 70 : x + 280);
                skillCounter = 0;
            }
            if (keyH2.upPressed) {
                isUsingJump = true;
                skillCounter = 0;
            }

            if (!isUsingSkill1 && !isUsingSkill2) {
                isMoving = false;

                if (keyH2.leftPressed) {
                    direction = "left";
                    x -= speed;
                    isMoving = true;
                }

                if (keyH2.rightPressed) {
                    direction = "right";
                    x += speed;
                    isMoving = true;
                }

                if (isMoving) {
                    playRunningSound();
                } else {
                    stopRunningSound();
                }
            }
        }
        body.setX(x);
        body.setY(y + 100);
        attackBody.setX(x);
        attackBody.setY(y);
    }

    public void draw(GraphicsContext gc) {
        Image image = null;
        drawHealthBar(gc);
        // Tăng animationCounter mỗi lần vẽ
        animationCounter++;
        if (animationCounter > animationSpeed) {
            animationCounter = 0;

            // Xử lý khi đang sử dụng skill qua phím J
            if (isUsingSkill1) {
                animationFrame = (animationFrame + 1) % 6; // Chuyển đổi giữa 6 khung hình skill 1
                skillCounter++;

                if (animationFrame == 5){
                    damageFrame = true;
                } else {
                    damageFrame = false;
                }

                if (skillCounter >= 6) {
                    isUsingSkill1 = false; // Dừng sử dụng skill 1
                    stopSkill1Sound();
                }
            }
            // Xử lý khi đang sử dụng skill qua phím K
            else if (isUsingSkill2) {
                animationFrame = (animationFrame + 1) % 6; // Chuyển đổi giữa 8 khung hình skill K
                skillKCounter++;

                if (animationFrame == 5){
                    damageFrame = true;
                } else {
                    damageFrame = false;
                }

                if (skillKCounter >= 6) {
                    isUsingSkill2 = false; // Dừng sử dụng skill 2
                    stopSkill2Sound();
                }
            }
            // Xử lý khi sử dụng skill nhảy
            else if (isUsingJump) {
                animationFrame = (animationFrame + 1) % 4;
                skillCounter++;
                if (skillCounter >= 4) {
                    isUsingJump = false;
                }
            }
            // Xử lý di chuyển hoặc đứng yên
            else if (isMoving) {
                switch (direction) {
                    case "left":
                    case "right":
                        animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 3 khung hình
                        break;
                }
            } else if (isTakenHit) {
                animationFrame = (animationFrame + 1) % 4;

            }
            else {
                animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 5 khung hình đứng yên
            }
        }

        gc.save();

        drawHealthBar(gc);

        gc.scale(3.5, 3.5);
        if (isMoving) {
            // Di chuyển - điều chỉnh frame tương ứng
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Run_1; break;
                    case 1: image = R_Run_2; break;
                    case 2: image = R_Run_3; break;
                    case 3: image = R_Run_4; break;
                    case 4: image = R_Run_5; break;
                    case 5: image = R_Run_6; break;
                    case 6: image = R_Run_7; break;
                    case 7: image = R_Run_8; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Run_1; break;
                    case 1: image = L_Run_2; break;
                    case 2: image = L_Run_3; break;
                    case 3: image = L_Run_4; break;
                    case 4: image = L_Run_5; break;
                    case 5: image = L_Run_6; break;
                    case 6: image = L_Run_7; break;
                    case 7: image = L_Run_8; break;
                }
            }
        }
        else if (isUsingSkill1) {
            // Kỹ năng 1
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Attack1_1; break;
                    case 1: image = R_Attack1_2; break;
                    case 2: image = R_Attack1_3; break;
                    case 3: image = R_Attack1_4; break;
                    case 4: image = R_Attack1_5; break;
                    case 5: image = R_Attack1_6;break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Attack1_1; break;
                    case 1: image = L_Attack1_2; break;
                    case 2: image = L_Attack1_3; break;
                    case 3: image = L_Attack1_4; break;
                    case 4: image = L_Attack1_5; break;
                    case 5: image = L_Attack1_6; break;
                }
            }
        }
        else if (isUsingSkill2) {
            // Kỹ năng 2
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Attack2_1; break;
                    case 1: image = R_Attack2_2; break;
                    case 2: image = R_Attack2_3; break;
                    case 3: image = R_Attack2_4; break;
                    case 4: image = R_Attack2_5; break;
                    case 5: image = R_Attack2_6; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Attack2_1; break;
                    case 1: image = L_Attack2_2; break;
                    case 2: image = L_Attack2_3; break;
                    case 3: image = L_Attack2_4; break;
                    case 4: image = L_Attack2_5; break;
                    case 5: image = L_Attack2_6; break;
                }
            }
        }
        else if (isTakenHit) {
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_Take_Hit_1; break;
                    case 1: image = R_Take_Hit_2; break;
                    case 2: image = R_Take_Hit_3; break;
                    case 3: image = R_Take_Hit_4; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_Take_Hit_1; break;
                    case 1: image = L_Take_Hit_2; break;
                    case 2: image = L_Take_Hit_3; break;
                    case 3: image = L_Take_Hit_4; break;
                }
            }
        }
        else {
            // Đứng yên
            if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_IDLE_1; break;
                    case 1: image = R_IDLE_2; break;
                    case 2: image = R_IDLE_3; break;
                    case 3: image = R_IDLE_4; break;
                    case 4: image = R_IDLE_5; break;
                    case 5: image = R_IDLE_6; break;
                    case 6: image = R_IDLE_7; break;
                    case 7: image = R_IDLE_8; break;
                }
            } else if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_IDLE_1; break;
                    case 1: image = L_IDLE_2; break;
                    case 2: image = L_IDLE_3; break;
                    case 3: image = L_IDLE_4; break;
                    case 4: image = L_IDLE_5; break;
                    case 5: image = L_IDLE_6; break;
                    case 6: image = L_IDLE_7; break;
                    case 7: image = L_IDLE_8; break;
                }
            }
        }

        gc.drawImage(image,  x/3.5, y/3.5); // x, y là tọa độ vẽ hình ảnh

        gc.restore();




    }
    public int getX() {
        return x;
    }
    public String getDirection(){
        return direction;
    }

    public boolean distance(){
        int distanceToPlayer = Math.abs(x - player11.getX());
        if (distanceToPlayer < 30) return true;
        else return false;
    }

    public void takeDamage(double damage) {
        isTakenHit = true;
        takeHitCounter = 4;
        isUsingSkill1 = false;
        isUsingSkill2 = false;
        isMoving = false;
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;

    }

    private void playSkill1Sound() {
        if (skill1SoundPlayer != null) {
            skill1SoundPlayer.stop(); // Đảm bảo phát từ đầu
            skill1SoundPlayer.play();
        }
    }

    private void stopSkill1Sound() {
        if (skill1SoundPlayer != null) {
            skill1SoundPlayer.stop(); // Dừng phát âm thanh
        }
    }

    private void playSkill2Sound() {
        if (skill2SoundPlayer != null) {
            skill2SoundPlayer.stop(); // Đảm bảo phát từ đầu
            skill2SoundPlayer.play();
        }
    }

    private void stopSkill2Sound() {
        if (skill2SoundPlayer != null) {
            skill2SoundPlayer.stop(); // Dừng phát âm thanh
        }
    }

    private void playRunningSound() {
        if (runningSoundPlayer != null && runningSoundPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            runningSoundPlayer.play();
        }
    }

    private void stopRunningSound() {
        if (runningSoundPlayer != null && runningSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            runningSoundPlayer.stop();
        }
    }

    public boolean isDamageFrame() {
        return damageFrame;
    }
}