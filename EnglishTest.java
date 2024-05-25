import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EnglishTest{
    Clip clip ;
    long clipPosition = 0;
    public static void main(String[] args) {
        EnglishTest music = new EnglishTest();
        String[] question = new String[10];
        music.load();
        //music.playMusic();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                question[i] = line;
                i++;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scn = new Scanner(System.in);
        String[][] record = new String[10][1];
        String[] answer = new String[10];
        music.createGUI();
        for(int i=0;i<10;i++){
            int num = i+1;
            System.out.println("第"+num+"題");
            answer[i] = scn.nextLine();
            if(answer[i].equals(question[i])){
                record[i][0] = "Correct";
            }
            else{
                record[i][0] = "Incorrect";
            } 
        }
        music.stop();
        scn.close();
        int cnt =0;
        for(int i=0;i<10;i++){
            if(record[i][0].equals("Incorrect")){
                System.out.println("第"+i+1+"題答錯");
                System.out.println("你的答案："+ answer[i]);
                System.out.println("正確答案為："+ question[i]);
                cnt++;
            }
        }
        int score = (10-cnt)*10;
        System.out.println("總分：" + score +"分");
        
    }

    public void load(){
        String filePath = Paths.get("text.wav").toString();
       try{
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } else {
                System.out.println("Can't find file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMusic() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipPosition);
            clip.start();
        }
    }

    public void pauseMusic() {
        if (clip != null) {
            clipPosition = clip.getMicrosecondPosition(); // 記錄當前位置
            clip.stop();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void createGUI() {
        JFrame frame = new JFrame("English Test Music Player");
        JPanel panel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");

        playButton.addActionListener(e -> playMusic());
        pauseButton.addActionListener(e -> pauseMusic());

        panel.add(playButton);
        panel.add(pauseButton);
        frame.add(panel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        load();
    }
}