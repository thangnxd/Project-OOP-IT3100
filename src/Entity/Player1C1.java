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

public class Player1C1 extends Entity{

    AzureP2 player12;
    private Body body;
    private Body attackBody;

    public double maxHealth = 3000; // Máu tối đa, giả sử là 3.000 để phù hợp với số trên hình ảnh
    public int currentHealth = 3000; // Máu hiện tại
    private int barWidth = 400; // Chiều rộng của thanh máu
    private int barHeight = 20; // Chiều cao của thanh máu

    private int controlDamage = 0;

    private MediaPlayer runningSoundPlayer;
    private MediaPlayer attackSoundPlayer;

    private boolean isMoving = false; // Biến để kiểm tra xem nhân vật có di chuyển hay không
    private boolean isUsingSkill = false;
    private boolean isUsingJump = false;
    public boolean isUsingSkillJ = false;
    public boolean isUsingSkillK = false;
    private boolean damageFrame = false;
    private boolean isTakenHit = false;

    private final int animationSpeed = 15; // Tốc độ chuyển đổi khung hình
    private int animationFrame = 0; // Biến đếm khung hình hiện tại
    private int animationCounter = 0; // Biến đếm thời gian chuyển đổi khung hình
    private int skillCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill
    private int skillKCounter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill qua phím K
    private int takeHitCounter = 4;

    Chapter1 gp;
    public KeyHandlers keyH;


    public Player1C1(Chapter1 gp, KeyHandlers keyH, AzureP2 player12){
        this.gp = gp;
        this.keyH = keyH;
        this.player12 = player12;
        setDefaultValues();
        getPlayerImage();
//        updateHealth();
        body = new Body(x,y,200, 200);
        attackBody = new Body(x,y, 200, 200);
        try {
            Media runningSound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Wukong/SoundEffect/runningSoundEffect.mp3").toURI().toString());
            runningSoundPlayer = new MediaPlayer(runningSound);
            runningSoundPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            Media attackSound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Wukong/SoundEffect/attackSoundEffect.mp3").toURI().toString());
            attackSoundPlayer = new MediaPlayer(attackSound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Body getBody(){
        return body;
    }
    public Body getAttackBody(){
        return attackBody;
    }
    public void setDefaultValues(){
        x = 100;
        y = 550;
        speed = 4;
        direction = "down";

    }

    public boolean isDamageFrame(){
        return damageFrame;
    }

    public boolean isAttacking(){
        if(isUsingSkillK || isUsingSkillJ) return true;
        return false;
    }
    public boolean isDenfensed(){
        if(isUsingSkill) return true;
        return false;
    }


    public void drawHealthBar(GraphicsContext gc) {
        // Đảm bảo currentHealth nằm trong khoảng từ 0 đến maxHealth
        double healthRatio = Math.max(0, Math.min(1, (double) currentHealth / maxHealth));

        // Vẽ viền ngoài của thanh máu
        gc.setFill(Color.WHITE); // Màu viền trắng
        gc.fillRoundRect(0, 0, barWidth + 4, barHeight + 4, 10, 10);

        // Vẽ nền thanh máu (màu xám)
        gc.setFill(Color.GRAY);
        gc.fillRoundRect(2, 2, barWidth, barHeight, 8, 8);

        // Vẽ thanh máu (với gradient)
        int healthBarWidth = (int) (barWidth * healthRatio);

        LinearGradient gradient = new LinearGradient(
                0, 0, barWidth, 0, // Vị trí của gradient
                false,
                null,
                new Stop(0, Color.RED),   // Màu đỏ khi máu thấp
                new Stop(0.5, Color.ORANGE),  // Màu cam khi máu ở mức trung bình
                new Stop(1, Color.GREEN)  // Màu xanh khi máu đầy
        );
        gc.setFill(gradient);
        gc.fillRoundRect(2, 2, healthBarWidth, barHeight, 8, 8);
    }



    public void getPlayerImage() {
        try {
            stand1 = new Image(new File("res/Wukong/right/standard1.png").toURI().toString());
            stand2 = new Image(new File("res/Wukong/right/standard2.PNG").toURI().toString());
            stand3 = new Image(new File("res/Wukong/right/standard3.PNG").toURI().toString());
            stand4 = new Image(new File("res/Wukong/right/standard4.PNG").toURI().toString());
            stand5 = new Image(new File("res/Wukong/right/standard5.PNG").toURI().toString());

            // right
            right1 = new Image(new File("res/Wukong/right/right1.PNG").toURI().toString());
            right2 = new Image(new File("res/Wukong/right/right2.PNG").toURI().toString());
            right3 = new Image(new File("res/Wukong/right/right3.PNG").toURI().toString());

            // jump_right Wukong/left/
            jump1 = new Image(new File("res/Wukong/right/jump_right/jump1.PNG").toURI().toString());
            jump2 = new Image(new File("res/Wukong/right/jump_right/jump2.PNG").toURI().toString());
            jump3 = new Image(new File("res/Wukong/right/jump_right/jump3.PNG").toURI().toString());

            // jump_left
            jump1_1 = new Image(new File("res/Wukong/left/jump_left/jump1.PNG").toURI().toString());
            jump2_1 = new Image(new File("res/Wukong/left/jump_left/jump2.PNG").toURI().toString());
            jump3_1 = new Image(new File("res/Wukong/left/jump_left/jump3.PNG").toURI().toString());

            // skill j
            j1 = new Image(new File("res/Wukong/right/quaytay1.PNG").toURI().toString());
            j2 = new Image(new File("res/Wukong/right/quaytay2.PNG").toURI().toString());
            j3 = new Image(new File("res/Wukong/right/quaytay3.PNG").toURI().toString());
            j4 = new Image(new File("res/Wukong/right/quaytay4.PNG").toURI().toString());
            j5 = new Image(new File("res/Wukong/right/quaytay5.PNG").toURI().toString());
            j6 = new Image(new File("res/Wukong/right/quaytay6.PNG").toURI().toString());

            // skill k
            k2 = new Image(new File("res/Wukong/right/skill2.PNG").toURI().toString());
            k3 = new Image(new File("res/Wukong/right/skill3.PNG").toURI().toString());
            k4 = new Image(new File("res/Wukong/right/skill4.PNG").toURI().toString());
            k5 = new Image(new File("res/Wukong/right/skill5.PNG").toURI().toString());
            k6 = new Image(new File("res/Wukong/right/skill6.PNG").toURI().toString());
            k7 = new Image(new File("res/Wukong/right/skill8.PNG").toURI().toString());
            k8 = new Image(new File("res/Wukong/right/skill7.PNG").toURI().toString());
            k9 = new Image(new File("res/Wukong/right/skill9.PNG").toURI().toString());

            // left
            left1 = new Image(new File("res/Wukong/left/left1.PNG").toURI().toString());
            left2 = new Image(new File("res/Wukong/left/left2.PNG").toURI().toString());
            left3 = new Image(new File("res/Wukong/left/left3.PNG").toURI().toString());

            // j_left
            j1_l = new Image(new File("res/Wukong/left/quaytay1_left.PNG").toURI().toString());
            j2_l = new Image(new File("res/Wukong/left/quaytay2_left.PNG").toURI().toString());
            j3_l = new Image(new File("res/Wukong/left/quaytay3_left.PNG").toURI().toString());
            j4_l = new Image(new File("res/Wukong/left/quaytay4_left.PNG").toURI().toString());
            j5_l = new Image(new File("res/Wukong/left/quaytay5_left.PNG").toURI().toString());
            j6_l = new Image(new File("res/Wukong/left/quaytay6_left.PNG").toURI().toString());

            // k_left
            k2_l = new Image(new File("res/Wukong/left/skill2_left.PNG").toURI().toString());
            k3_l = new Image(new File("res/Wukong/left/skill3_left.PNG").toURI().toString());
            k4_l = new Image(new File("res/Wukong/left/skill4_left.PNG").toURI().toString());
            k5_l = new Image(new File("res/Wukong/left/skill5_left.PNG").toURI().toString());
            k6_l = new Image(new File("res/Wukong/left/skill6_left.PNG").toURI().toString());
            k7_l = new Image(new File("res/Wukong/left/skill7_left.PNG").toURI().toString());
            k8_l = new Image(new File("res/Wukong/left/skill8_left.PNG").toURI().toString());
            k9_l = new Image(new File("res/Wukong/left/skill9_left.PNG").toURI().toString());

            // standing_left
            stand1_l = new Image(new File("res/Wukong/left/standard1_left.PNG").toURI().toString());
            stand2_l = new Image(new File("res/Wukong/left/standard2_left.PNG").toURI().toString());
            stand3_l = new Image(new File("res/Wukong/left/standard3_left.PNG").toURI().toString());
            stand4_l = new Image(new File("res/Wukong/left/standard4_left.PNG").toURI().toString());
            stand5_l = new Image(new File("res/Wukong/left/standard5_left.PNG").toURI().toString());


            //skill s right
            s1 = new Image(new File("res/Wukong/right/s1_r.png").toURI().toString());
            s2 = new Image(new File("res/Wukong/right/s2_r.png").toURI().toString());
            s3 = new Image(new File("res/Wukong/right/s3_r.png").toURI().toString());
            s4 = new Image(new File("res/Wukong/right/s4_r.png").toURI().toString());
            //skill s left
            s1_l = new Image(new File("res/Wukong/left/s1_l.png").toURI().toString());
            s2_l = new Image(new File("res/Wukong/left/s2_l.png").toURI().toString());
            s3_l = new Image(new File("res/Wukong/left/s3_l.png").toURI().toString());
            s4_l = new Image(new File("res/Wukong/left/s4_l.png").toURI().toString());

            // Take hit right
            R_takeHit1 = new Image(new File("res/Wukong/right/standard3.PNG").toURI().toString());

            // Take hit left
            L_takeHit1 = new Image(new File("res/Wukong/left/standard3_left.png").toURI().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getHealth() {
        return currentHealth;
    }
    public void Health2(double damage){
        isTakenHit = true;
        takeHitCounter = 1;
        isUsingSkillJ = false;
        isUsingSkillK = false;
        isMoving = false;
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
        }
    }

    public void updateHealth(double damage) {

//        getHealth();

    }
    public void Health1(double damage){
        currentHealth -= damage;
    }







    public void update() {
        // Kiểm tra xem phím "J" có được nhấn không
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
            if (keyH.sPressed) {
                isUsingSkill = true; // Bắt đầu sử dụng skill
                skillCounter = 0; // Reset bộ đếm thời gian cho skill
            }
            if (keyH.kPressed) {
                isUsingSkillK = true; // Bắt đầu sử dụng skill qua phím K
                animationFrame = 0;
                attackBody.setX(direction.equals("left") ? x - 200 : x + 200);
                attackBody.setY(y);
                skillKCounter = 0; // Reset bộ đếm thời gian cho skill K
            }
            if (keyH.wPressed) {
                isUsingJump = true;
                skillCounter = 0;
            }
            if(keyH.jPressed) {
                isUsingSkillJ = true;
                animationFrame = 0;
                attackBody.setX(direction.equals("left") ? x - 100 : x + 100);
                attackBody.setY(y);
                skillCounter = 0;
            }


            // Chỉ cho phép di chuyển nếu không sử dụng skill
            if (!isUsingSkill) {
                // Giả định ban đầu là nhân vật không di chuyển
                isMoving = false;


                if (keyH.aPressed) {
                    direction = "left";
                    x -= speed;
                    isMoving = true;
                } else if (keyH.dPressed) {
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
            body.setY(y);
            body.setX(x);
        }
    }

    public void draw(GraphicsContext gc) {
        Image image = null;
        drawHealthBar(gc);  // Giả sử bạn đã có phương thức vẽ thanh máu
        gc.save();
        // Tăng animationCounter mỗi lần vẽ
        animationCounter++;
        if (animationCounter > animationSpeed) {
            animationCounter = 0;

            // Xử lý khi đang sử dụng skill qua phím s
            if (isUsingSkill) {
                animationFrame = (animationFrame + 1) % 6; // Chuyển đổi giữa 6 khung hình skill J
                skillCounter++;

                if (skillCounter >= 6) {
                    isUsingSkill = false; // Dừng sử dụng skill s
                }
            }
            else if (isUsingSkillJ) {
                animationFrame = (animationFrame + 1) % 4;
                skillCounter ++;

                if (animationFrame == 2 || animationFrame == 1){
                    playAttackSound();
                    damageFrame = true;
                } else{
                    damageFrame = false;
                }

                if (skillCounter >= 4){
                    isUsingSkillJ = false;
                    stopAttackSound();
                }
            }
            // Xử lý khi đang sử dụng skill qua phím K
            else if (isUsingSkillK) {
                animationFrame = (animationFrame + 1) % 8; // Chuyển đổi giữa 8 khung hình skill K
                skillKCounter++;

                if( animationFrame == 6 || animationFrame == 7 || animationFrame == 5){
                    playAttackSound();
                    damageFrame = true;
                } else{
                    damageFrame = false;
                }

                if (skillKCounter >= 8) {
                    isUsingSkillK = false; // Dừng sử dụng skill K
                    stopAttackSound();
                }
            }
            // Xử lý khi sử dụng skill nhảy
            else if (isUsingJump) {
                animationFrame = (animationFrame + 1) % 3;
                skillCounter++;
                if (skillCounter >= 3) {
                    isUsingJump = false;
                }
            }
            // Xử lý di chuyển hoặc đứng yên
            else if (isMoving) {
                switch (direction) {
                    case "left":
                    case "right":
                        animationFrame = (animationFrame + 1) % 3; // Chuyển đổi giữa 3 khung hình
                        break;
                }
            }
            else if (isTakenHit) {
                animationFrame = (animationFrame + 1) % 1;
            }
            else {
                animationFrame = (animationFrame + 1) % 5; // Chuyển đổi giữa 5 khung hình đứng yên
            }
        }

        // Vẽ hình ảnh cho skill s
        if (isUsingSkill) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = j1_l; break;
                    case 1: image = j2_l; break;
                    case 2: image = j3_l; break;
                    case 3: image = j4_l; break;
                    case 4: image = j5_l; break;
                    case 5: image = j6_l; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = j1; break;
                    case 1: image = j2; break;
                    case 2: image = j3; break;
                    case 3: image = j4; break;
                    case 4: image = j5; break;
                    case 5: image = j6; break;
                }
            }
        }
        // Vẽ hình ảnh cho skill K
        else if (isUsingSkillK) {
            controlDamage ++;
            if (direction.equals("left")) {

                switch (animationFrame) {
                    case 0: image = k2_l; break;
                    case 1: image = k3_l; break;
                    case 2: image = k4_l; break;
                    case 3: image = k5_l; break;
                    case 4: image = k6_l; break;
                    case 5: image = k7_l; x -= 2 * speed; break;
                    case 6: image = k8_l; break;
                    case 7: image = k9_l; break;
                    case 8: controlDamage --;

                }
            } else {
                switch (animationFrame) {
                    case 0: image = k2; break;
                    case 1: image = k3; break;
                    case 2: image = k4; break;
                    case 3: image = k5; break;
                    case 4: image = k6; break;
                    case 5: image = k7; x += 2 * speed; break;
                    case 6: image = k8; break;
                    case 7: image = k9; break;
                    case 8: controlDamage --;
                }
            }
        }
        else if (isUsingSkillJ) {
            controlDamage ++;
            if (direction.equals("left")) {

                switch (animationFrame) {
                    case 0: image = s1_l; break;
                    case 1: image = s2_l; break;
                    case 2: image = s3_l; x-=2;break;
                    case 3: image = s4_l; break;


                }
            } else {
                switch (animationFrame) {
                    case 0: image = s1; break;
                    case 1: image = s2; break;
                    case 2: image = s3; x+=2;break;
                    case 3: image = s4; break;

                }
            }
        }

        // Vẽ hình ảnh cho skill nhảy
        else if (isUsingJump) {

            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = jump1_1; x -= 1 * speed; break;
                    case 1: image = jump2_1; x -= 1 * speed; break;
                    case 2: image = jump3_1; x -= 1 * speed; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = jump1; x += 1 * speed; break;
                    case 1: image = jump2; x += 1 * speed; break;
                    case 2: image = jump3; x += 1 * speed; break;
                }
            }
        }
        // Di chuyển hoặc đứng yên
        else if(!isMoving) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = stand1_l; break;
                    case 1: image = stand2_l; break;
                    case 2: image = stand3_l; break;
                    case 3: image = stand4_l; break;
                    case 4: image = stand5_l; break;
                }
            } else {
                switch (animationFrame) {
                    case 0:
                        image = stand1;
                        break;
                    case 1:
                        image = stand2;
                        break;
                    case 2:
                        image = stand3;
                        break;
                    case 3:
                        image = stand4;
                        break;
                    case 4:
                        image = stand5;
                        break;
                }
            }
        } else if (isTakenHit) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = L_takeHit1;
                }
            } else if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = R_takeHit1;
                }
            }
        }
        else  {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = left1; break;
                    case 1: image = left2; break;
                    case 2: image = left3; break;
                }
            }
            // Xử lý di chuyển sang phải
            else if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = right1; break;
                    case 1: image = right2; break;
                    case 2: image = right3; break;
                }
            }
        }

        // Vẽ hình ảnh lên Canvas
        if ( image !=  null) {
            gc.drawImage(image, x, y, image.getWidth() * 0.8, image.getHeight() * 0.8); // x, y là tọa độ vẽ hình ảnh
        }
    }

    public int getX() {
        return x;
    }

    public String getDirection(){
        return direction;
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

    private void playAttackSound() {
        if (attackSoundPlayer != null && attackSoundPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            attackSoundPlayer.play();
        }
    }

    private void stopAttackSound() {
        if (attackSoundPlayer != null && attackSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            attackSoundPlayer.stop();
        }
    }
}