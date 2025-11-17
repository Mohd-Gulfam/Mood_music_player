import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class MoodMusicPlayer extends JFrame implements ActionListener {
    private JComboBox<String> moodBox;
    private JButton playButton, stopButton;
    private Clip clip;

    public MoodMusicPlayer() {
        setTitle("🎵 Mood-Based Music Player");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Select Your Mood to Play Music 🎧", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        String[] moods = {"Happy", "Sad", "Relax", "Energetic"};
        moodBox = new JComboBox<>(moods);
        moodBox.setFont(new Font("Arial", Font.PLAIN, 14));

        playButton = new JButton("Play");
        stopButton = new JButton("Stop");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Mood: "));
        panel.add(moodBox);
        panel.add(playButton);
        panel.add(stopButton);
        add(panel, BorderLayout.CENTER);

        playButton.addActionListener(this);
        stopButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            playMusic((String) moodBox.getSelectedItem());
        } else if (e.getSource() == stopButton) {
            stopMusic();
        }
    }

    private void playMusic(String mood) {
        stopMusic(); // Stop any currently playing music
        try {
            String filePath = switch (mood) {
                case "Happy" -> "music/happy.wav";
                case "Sad" -> "music/sad.wav";
                case "Relax" -> "music/relax.wav";
                case "Energetic" -> "music/energetic.wav";
                default -> "music/default.wav";
            };

            File musicFile = new File(filePath);
            if (!musicFile.exists()) {
                JOptionPane.showMessageDialog(this, "Music file not found for " + mood);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            JOptionPane.showMessageDialog(this, "Playing " + mood + " music 🎶");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error playing music: " + ex.getMessage());
        }
    }

    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoodMusicPlayer().setVisible(true));
    }
}