package Entity;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import javafx.scene.image.Image;


import Chapter3.Chapter3;

public class BlackP2 extends Entity{



    private Body body;
    private Body attackBox1;
    private Body attackBox2;
    private Body attackBox3;

    public int maxHealth = 4000; // Máu tối đa, giả sử là 3.000 để phù hợp với số trên hình ảnh
    private int currentHealth = 4000; // Máu hiện tại
    private int barWidth = 400; // Chiều rộng của thanh máu
    private int barHeight = 20; // Chiều cao của thanh máu

    private boolean isMoving = false; // Biến để kiểm tra xem nhân vật có di chuyển hay không
    private boolean isUsingSkill1 = false;
    private boolean isUsingSkill2 = false;
    private boolean isUsingSkill3 = false;
    private boolean isTakeHit = false;
    private boolean damageFrame = false;

    private final int animationSpeed = 20; // Tốc độ chuyển đổi khung hình

    private int animationFrame = 0; // Biến đếm khung hình hiện tại
    private int animationCounter = 0; // Biến đếm thời gian chuyển đổi khung hình
    private int takeHitCounter = 4;

    private int skill1Counter = 0; // Đếm thời gian để điều khiển hoạt ảnh skill
    private int skill2Counter = 0;
    private int skill3Counter = 0;


    Chapter3 gp;
    public KeyHandlers keyH;

    private MediaPlayer skill1SoundPlayer;
    private MediaPlayer skill2SoundPlayer;
    private MediaPlayer runningSoundPlayer;
    private MediaPlayer skill3_1SoundPlayer;
    private MediaPlayer skill3_2SoundPlayer;
    public BlackP2(Chapter3 gp, KeyHandlers keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        updateHealth(currentHealth);
        body = new Body(x, y , 180, 520);
        attackBox1 = new Body(x, y, 150, 520);
        attackBox2 = new Body(x, y, 150, 520);
        attackBox3 = new Body(x, y, 400, 520);

        try {
            Media skill1Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Wukong/SoundEffect/attackSoundEffect.mp3").toURI().toString());
            skill1SoundPlayer = new MediaPlayer(skill1Sound);

            Media skill2Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Black/Sound/skill2.mp3").toURI().toString());
            skill2SoundPlayer = new MediaPlayer(skill2Sound);

            Media skill3_1Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Black/Sound/skill3_3.mp3").toURI().toString());
            skill3_1SoundPlayer = new MediaPlayer(skill3_1Sound);

            Media skill3_2Sound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/Black/Sound/skill3_4.mp3").toURI().toString());
            skill3_2SoundPlayer = new MediaPlayer(skill3_2Sound);


            Media runningSound = new Media(new File("D:/BMW/Project_OOP_IT3100/res/MartialBoss/SoundEffect/runningSoundEffect.mp3").toURI().toString());
            runningSoundPlayer = new MediaPlayer(runningSound);
            runningSoundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues(){
        x = 1000;
        y = 300;
        speed = 4;
        direction = "left";
    }


    public Body getBody() {
        return body;
    }

    public Body getAttackBox1() {
        return attackBox1;
    }

    public Body getAttackBox2(){
        return attackBox2;
    }

    public Body getAttackBox3(){
        return attackBox3;
    }

    public boolean isDamageFrame() {return damageFrame;}

    public void takeDamage(double damage) {
        isTakeHit = true;
        takeHitCounter = 4;
        isUsingSkill1 = false;
        isUsingSkill2 = false;
        isUsingSkill3 = false;
        isMoving = false;
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
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
        double healthRatio = (double) currentHealth / maxHealth;
        double healthWidth = barWidth * healthRatio;
        gc.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.YELLOW)));
        gc.fillRoundRect(healthBarX + 2 + barWidth - healthWidth, healthBarY + 2, healthWidth, barHeight, 8, 8);
    }



    public void getPlayerImage() {
        try {
            BOSS_standing_right_1 = new Image(new File("res/Black/right/black_move_1_right.png").toURI().toString());
            BOSS_standing_right_2 = new Image(new File("res/Black/right/black_move_2_right.png").toURI().toString());

            //Boss_standing_left
            BOSS_standing_left_1 = new Image(new File("res/Black/left/black_move_1_left.png").toURI().toString());
            BOSS_standing_left_2 = new Image(new File("res/Black/left/black_move_2_left.png").toURI().toString());

            //Boss_move_right
            BOSS_move_right_1 = new Image(new File("res/Black/right/black_move_1_right.png").toURI().toString());
            BOSS_move_right_2 = new Image(new File("res/Black/right/black_move_2_right.png").toURI().toString());
            BOSS_move_right_3 = new Image(new File("res/Black/right/black_move_3_right.png").toURI().toString());
            BOSS_move_right_4 = new Image(new File("res/Black/right/black_move_4_right.png").toURI().toString());
            BOSS_move_right_5 = new Image(new File("res/Black/right/black_move_5_right.png").toURI().toString());

            //Boss_move_left
            BOSS_move_left_1 = new Image(new File("res/Black/left/black_move_1_left.png").toURI().toString());
            BOSS_move_left_2 = new Image(new File("res/Black/left/black_move_2_left.png").toURI().toString());
            BOSS_move_left_3 = new Image(new File("res/Black/left/black_move_3_left.png").toURI().toString());
            BOSS_move_left_4 = new Image(new File("res/Black/left/black_move_4_left.png").toURI().toString());
            BOSS_move_left_5 = new Image(new File("res/Black/left/black_move_5_left.png").toURI().toString());

            // Boss_skill1_right
            BOSS_skill1_right_1 = new Image(new File("res/Black/right/black_attack1_1_right.png").toURI().toString());
            BOSS_skill1_right_2 = new Image(new File("res/Black/right/black_attack1_2_right.png").toURI().toString());
            BOSS_skill1_right_3 = new Image(new File("res/Black/right/black_attack1_3_right.png").toURI().toString());
            BOSS_skill1_right_4 = new Image(new File("res/Black/right/black_attack1_4_right.png").toURI().toString());
            BOSS_skill1_right_5 = new Image(new File("res/Black/right/black_attack1_5_right.png").toURI().toString());
            BOSS_skill1_right_6 = new Image(new File("res/Black/right/black_attack1_6_right.png").toURI().toString());

            //Boss_skill1_left
            BOSS_skill1_left_1 = new Image(new File("res/Black/left/black_attack1_1_left.png").toURI().toString());
            BOSS_skill1_left_2 = new Image(new File("res/Black/left/black_attack1_2_left.png").toURI().toString());
            BOSS_skill1_left_3 = new Image(new File("res/Black/left/black_attack1_3_left.png").toURI().toString());
            BOSS_skill1_left_4 = new Image(new File("res/Black/left/black_attack1_4_left.png").toURI().toString());
            BOSS_skill1_left_5 = new Image(new File("res/Black/left/black_attack1_5_left.png").toURI().toString());
            BOSS_skill1_left_6 = new Image(new File("res/Black/left/black_attack1_6_left.png").toURI().toString());

            //Boss_sKill2_right
            BOSS_SKILL2_right_1 = new Image(new File("res/Black/right/black_attack2_1_right.png").toURI().toString());
            BOSS_SKILL2_right_2 = new Image(new File("res/Black/right/black_attack2_2_right.png").toURI().toString());
            BOSS_SKILL2_right_3 = new Image(new File("res/Black/right/black_attack2_3_right.png").toURI().toString());
            BOSS_SKILL2_right_4 = new Image(new File("res/Black/right/black_attack2_3_right.png").toURI().toString());
            BOSS_SKILL2_right_5 = new Image(new File("res/Black/right/black_attack2_3_right.png").toURI().toString());

            //Boss_skill2_left
            BOSS_SKILL2_left_1 = new Image(new File("res/Black/left/black_attack2_1_left.png").toURI().toString());
            BOSS_SKILL2_left_2 = new Image(new File("res/Black/left/black_attack2_2_left.png").toURI().toString());
            BOSS_SKILL2_left_3 = new Image(new File("res/Black/left/black_attack2_3_left.png").toURI().toString());
            BOSS_SKILL2_left_4 = new Image(new File("res/Black/left/black_attack2_3_left.png").toURI().toString());
            BOSS_SKILL2_left_5 = new Image(new File("res/Black/left/black_attack2_3_left.png").toURI().toString());

            //Boss_skill3_right
            BOSS_SKILL3_right_1 = new Image(new File("res/Black/right/black_attack3_1_right.png").toURI().toString());
            BOSS_SKILL3_right_2 = new Image(new File("res/Black/right/black_attack3_2_right.png").toURI().toString());
            BOSS_SKILL3_right_3 = new Image(new File("res/Black/right/black_attack3_3_right.png").toURI().toString());
            BOSS_SKILL3_right_4 = new Image(new File("res/Black/right/black_attack3_4_right.png").toURI().toString());
            BOSS_SKILL3_right_5 = new Image(new File("res/Black/right/black_attack3_5_right.png").toURI().toString());
            BOSS_SKILL3_right_6 = new Image(new File("res/Black/right/black_attack3_6_right.png").toURI().toString());

            //Boss_skill3_left
            BOSS_SKILL3_left_1 = new Image(new File("res/Black/left/black_attack3_1_left.png").toURI().toString());
            BOSS_SKILL3_left_2 = new Image(new File("res/Black/left/black_attack3_2_left.png").toURI().toString());
            BOSS_SKILL3_left_3 = new Image(new File("res/Black/left/black_attack3_3_left.png").toURI().toString());
            BOSS_SKILL3_left_4 = new Image(new File("res/Black/left/black_attack3_4_left.png").toURI().toString());
            BOSS_SKILL3_left_5 = new Image(new File("res/Black/left/black_attack3_5_left.png").toURI().toString());
            BOSS_SKILL3_left_6 = new Image(new File("res/Black/left/black_attack3_6_left.png").toURI().toString());

            //Boss_takeHit_right
            Boss_takeHit_right_1 = new Image(new File("res/Black/right/black_take_hit_1_right.png").toURI().toString());

            //Boss_takeHit_left
            Boss_takeHit_left_1 = new Image(new File("res/Black/left/black_take_hit_1_left.png").toURI().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHealth(){

        return currentHealth;
    }


    public void updateHealth(int newHealth) {
        currentHealth = newHealth;
        // Gọi hàm vẽ lại thanh máu tại đây hoặc từ vòng lặp game
    }



    public void update() {
        // Kiểm tra xem phím "J" có được nhấn không
        if (isTakeHit) {
            takeHitCounter--; // Giảm bộ đếm
            if (direction.equals("left")) {
                x += 1;
            } else if (direction.equals("right")) {
                x -= 1;
            }
            if (takeHitCounter <= 0) {
                isTakeHit = false; // Kết thúc trạng thái takeHit
            }
        }
        if(!isTakeHit) {
            if (keyH.Pressed1) {
                isUsingSkill1 = true; // Bắt đầu sử dụng skill
                animationFrame = 0;
                attackBox1.setX(direction.equals("left") ? x - 20 : x + 100);
                attackBox1.setY(y);
                skill1Counter = 0; // Reset bộ đếm thời gian cho skill
            }
            if (keyH.Pressed2) {
                isUsingSkill2 = true; // Bắt đầu sử dụng skill qua phím K
                animationFrame = 0;
                playSkill2Sound();
                attackBox2.setX(direction.equals("left") ? x - 40 : x + 100);
                attackBox2.setY(y);
                skill2Counter = 0; // Reset bộ đếm thời gian cho skill K
            }
            if (keyH.Pressed3) {
                isUsingSkill3 = true;
                animationFrame = 0;
                playSkill3_1Sound();
                if (direction.equals("left")){
                    body.setX(x + 400);
                    body.setY(y);
                } else{
                    body.setX(x + 100);
                    body.setY(y);
                }
                attackBox3.setX(direction.equals("left") ? x - 15 : x + 320);
                attackBox3.setY(y);
                skill3Counter = 0;
            }


            // Chỉ cho phép di chuyển nếu không sử dụng skill
            if (!isUsingSkill1 && !isUsingSkill2 && !isUsingSkill3) {
                // Giả định ban đầu là nhân vật không di chuyển
                isMoving = false;


                if (keyH.leftPressed) {
                    direction = "left";
                    x -= speed;
                    isMoving = true;
                } else if (keyH.rightPressed) {
                    direction = "right";
                    x += speed;
                    isMoving = true;
                }
                body.setX(x);
                body.setY(y);
            }
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

            // Xử lý khi đang sử dụng skill qua phím J
            if (isUsingSkill1) {
                animationFrame = (animationFrame + 1) % 6; // Chuyển đổi giữa 6 khung hình skill J
                skill1Counter++;
                if (animationFrame == 2 || animationFrame == 5){
                    damageFrame = true;
                    playSkill1Sound();
                } else {
                    damageFrame = false;
                }
                if (skill1Counter >= 6) {
                    isUsingSkill1 = false;// Dừng sử dụng skill J
                    stopSkill1Sound();
                }
            }
            // Xử lý khi đang sử dụng skill qua phím K
            else if (isUsingSkill2) {
                animationFrame = (animationFrame + 1) % 5; // Chuyển đổi giữa 8 khung hình skill K
                skill2Counter++;

                if (animationFrame == 2 || animationFrame == 3 || animationFrame == 4){
                    damageFrame = true;
                } else {
                    damageFrame = false;
                }

                if (skill2Counter >= 5) {
                    isUsingSkill2 = false; // Dừng sử dụng skill K
                    stopSkill2Sound();
                }
            }
            // Xử lý khi sử dụng skill nhảy
            else if (isUsingSkill3) {
                animationFrame = (animationFrame + 1) % 12;
                skill3Counter++;

                if(animationFrame == 8){
                    damageFrame = true;
                    stopSkill3_1Sound();
                    playSkill3_2Sound();
                } else {
                    damageFrame = false;
                }

                if (skill3Counter >= 13) {
                    isUsingSkill3 = false;

                    stopSkill3_2Sound();
                }
            }
            // Xử lý di chuyển hoặc đứng yên
            else if (isMoving) {
                switch (direction) {
                    case "left":
                    case "right":
                        animationFrame = (animationFrame + 1) % 5; // Chuyển đổi giữa 3 khung hình
                        break;
                }
            }
            else if(isTakeHit) {
                animationFrame = (animationFrame + 1) % 1;
            }
            else {
                animationFrame = (animationFrame + 1) % 1; // Chuyển đổi giữa 5 khung hình đứng yên
            }
        }

        // Vẽ hình ảnh cho skill J
        if (isUsingSkill1) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = BOSS_skill1_left_1; break;
                    case 1: image = BOSS_skill1_left_2; break;
                    case 2: image = BOSS_skill1_left_3; break;
                    case 3: image = BOSS_skill1_left_4; break;
                    case 4: image = BOSS_skill1_left_5; break;
                    case 5: image = BOSS_skill1_left_6; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = BOSS_skill1_right_1; break;
                    case 1: image = BOSS_skill1_right_2; break;
                    case 2: image = BOSS_skill1_right_3; break;
                    case 3: image = BOSS_skill1_right_4; break;
                    case 4: image = BOSS_skill1_right_5; break;
                    case 5: image = BOSS_skill1_right_6; break;
                }
            }
        }
        // Vẽ hình ảnh cho skill K
        else if (isUsingSkill2) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = BOSS_SKILL2_left_1; break;
                    case 1: image = BOSS_SKILL2_left_2; break;
                    case 2: image = BOSS_SKILL2_left_3; break;
                    case 3: image = BOSS_SKILL2_left_3; break;
                    case 4: image = BOSS_SKILL2_left_3; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = BOSS_SKILL2_right_1; break;
                    case 1: image = BOSS_SKILL2_right_2; break;
                    case 2: image = BOSS_SKILL2_right_3; break;
                    case 3: image = BOSS_SKILL2_right_3; break;
                    case 4: image = BOSS_SKILL2_right_3; break;
                }
            }
        }
        // Vẽ hình ảnh cho skill nhảy
        else if (isUsingSkill3) {

            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = BOSS_SKILL3_left_1; break;
                    case 1: image = BOSS_SKILL3_left_2; break;
                    case 2: image = BOSS_SKILL3_left_3; break;
                    case 3: image = BOSS_SKILL3_left_4; break;
                    case 4: image = BOSS_SKILL3_left_5; break;
                    case 5: image = BOSS_SKILL3_left_3; break;
                    case 6: image = BOSS_SKILL3_left_4; break;
                    case 7: image = BOSS_SKILL3_left_5; break;
                    case 8: image = BOSS_SKILL3_left_6; break;
                    case 9: image = BOSS_SKILL3_left_6; break;
                    case 10: image = BOSS_SKILL3_left_6; break;
                    case 11: image = BOSS_SKILL3_left_6; break;
                    case 12: image = BOSS_SKILL3_left_6; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = BOSS_SKILL3_right_1; break;
                    case 1: image = BOSS_SKILL3_right_2; break;
                    case 2: image = BOSS_SKILL3_right_3; break;
                    case 3: image = BOSS_SKILL3_right_4; break;
                    case 4: image = BOSS_SKILL3_right_5; break;
                    case 5: image = BOSS_SKILL3_right_3; break;
                    case 6: image = BOSS_SKILL3_right_4; break;
                    case 7: image = BOSS_SKILL3_right_5; break;
                    case 8: image = BOSS_SKILL3_right_6; break;
                    case 9: image = BOSS_SKILL3_right_6; break;
                    case 10: image = BOSS_SKILL3_right_6; break;
                    case 11: image = BOSS_SKILL3_right_6; break;
                    case 12: image = BOSS_SKILL3_right_6; break;
                }
            }
        } else if (isTakeHit) {
            if(direction.equals("left")){
                switch (animationFrame){
                    case 0: image = Boss_takeHit_left_1; break;
                }
            }
            else{
                switch (animationFrame){
                    case 0: image = Boss_takeHit_right_1; break;
                }
            }
        }
        // Di chuyển hoặc đứng yên
        else if(!isMoving) {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = BOSS_standing_left_1; break;
                }
            } else {
                switch (animationFrame) {
                    case 0: image = BOSS_standing_right_1; break;
                }
            }
        } else  {
            if (direction.equals("left")) {
                switch (animationFrame) {
                    case 0: image = BOSS_move_left_1; break;
                    case 1: image = BOSS_move_left_2; break;
                    case 2: image = BOSS_move_left_3; break;
                    case 3: image = BOSS_move_left_4; break;
                    case 4: image = BOSS_move_left_5; break;
                }
            }
            // Xử lý di chuyển sang phải
            else if (direction.equals("right")) {
                switch (animationFrame) {
                    case 0: image = BOSS_move_right_1; break;
                    case 1: image = BOSS_move_right_2; break;
                    case 2: image = BOSS_move_right_3; break;
                    case 3: image = BOSS_move_right_4; break;
                    case 4: image = BOSS_move_right_5; break;
                }
            }
        }
        double scaleFactor = 0.7;  // Tỷ lệ thu nhỏ
        if(image != null) {
            double newWidth = image.getWidth() * scaleFactor;
            double newHeight = image.getHeight() * scaleFactor;
            // Vẽ hình ảnh lên Canvas
            gc.drawImage(image, x, y, image.getWidth() * 0.7, image.getHeight() * 0.7); // x, y là tọa độ vẽ hình ảnh
        }

//        // Vẽ hitbox chính (debug)
//        gc.setStroke(Color.RED);
//        gc.strokeRect(body.getX(), body.getY(), body.getWidth(), body.getHeight());
//
//        // Vẽ hitbox chiêu 1 (debug)
//        if (isUsingSkill1) {
//            gc.setStroke(Color.BLUE);
//            gc.strokeRect(attackBox1.getX(), attackBox1.getY(), attackBox1.getWidth(), attackBox1.getHeight());
//        }
//
//        if (isUsingSkill2) {
//            gc.setStroke(Color.BLUE);
//            gc.strokeRect(attackBox2.getX(), attackBox2.getY(), attackBox2.getWidth(), attackBox2.getHeight());
//        }
//
//        if (isUsingSkill3) {
//            gc.setStroke(Color.BLUE);
//            gc.strokeRect(attackBox3.getX(), attackBox3.getY(), attackBox3.getWidth(), attackBox3.getHeight());
//        }
    }
    public int getX() {
        return x;
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

    private void playSkill3_1Sound() {
        if (skill3_1SoundPlayer != null) {
            skill3_1SoundPlayer.stop(); // Đảm bảo phát từ đầu
            skill3_1SoundPlayer.play();
        }
    }

    private void stopSkill3_2Sound() {
        if (skill3_2SoundPlayer != null) {
            skill3_2SoundPlayer.stop(); // Dừng phát âm thanh
        }
    }

    private void playSkill3_2Sound() {
        if (skill3_2SoundPlayer != null) {
            skill3_2SoundPlayer.stop(); // Đảm bảo phát từ đầu
            skill3_2SoundPlayer.play();
        }
    }

    private void stopSkill3_1Sound() {
        if (skill3_1SoundPlayer != null) {
            skill3_1SoundPlayer.stop(); // Dừng phát âm thanh
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
}