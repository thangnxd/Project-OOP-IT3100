package Entity;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandlers {

    public boolean aPressed, dPressed, jPressed, kPressed, wPressed, sPressed, lPressed;
    public boolean leftPressed, rightPressed,upPressed, Pressed1, Pressed2, Pressed3, Pressed0;


    // Phương thức để xử lý sự kiện khi một phím được nhấn
    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            wPressed = true;
        }
        if (code == KeyCode.S){
            sPressed = true;
        }
        if (code == KeyCode.A) {
            aPressed = true;
        }
        if (code == KeyCode.D) {
            dPressed = true;
        }
        if (code == KeyCode.J) {
            jPressed = true;
        }
        if (code == KeyCode.K) {
            kPressed = true;
        }
        if (code == KeyCode.L){
            lPressed = true;
        }
        if (code == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (code == KeyCode.RIGHT){
            rightPressed = true;
        }
        if (code == KeyCode.UP){
            upPressed = true;
        }
        if (code == KeyCode.NUMPAD1){
            Pressed1 = true;
        }
        if (code == KeyCode.NUMPAD2){
            Pressed2 = true;
        }
        if (code == KeyCode.NUMPAD3){
            Pressed3 = true;
        }


    }

    // Phương thức để xử lý sự kiện khi một phím được thả ra
    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W) {
            wPressed = false;
        }
        if (code == KeyCode.S){
            sPressed = false;
        }
        if (code == KeyCode.A) {
            aPressed = false;
        }
        if (code == KeyCode.D) {
            dPressed = false;
        }
        if (code == KeyCode.J) {
            jPressed = false;
        }
        if (code == KeyCode.K) {
            kPressed = false;
        }
        if (code == KeyCode.L){
            lPressed = false;
        }
        if (code == KeyCode.LEFT){
            leftPressed = false;
        }
        if (code == KeyCode.RIGHT){
            rightPressed = false;
        }
        if (code == KeyCode.UP){
            upPressed = false;
        }
        if (code == KeyCode.NUMPAD1){
            Pressed1 = false;
        }
        if (code == KeyCode.NUMPAD2){
            Pressed2 = false;
        }
        if (code == KeyCode.NUMPAD3){
            Pressed3 = false;
        }

    }
}