package vidivox.actionlisteners.audio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import vidivox.gui.CreateMP3Panel;
import vidivox.helpers.FileOpener;
import vidivox.swingworker.CreateMP3;

/**
 * This class is used to invoke the creation of an MP3 file in a swingworker thread.
 * @author John Ramirez (jram948)
 *
 */
public class SaveMP3ActionListener implements ActionListener {

	private String commentary;
	private JFrame parentFrame;
	private CreateMP3Panel panel;
	private JTextArea textArea;
	private FileOpener fo;
	
	public SaveMP3ActionListener(JFrame parentFrame, CreateMP3Panel panel, JTextArea textArea, FileOpener fo) {
		this.parentFrame = parentFrame;
		this.panel = panel;
		this.textArea = textArea;
		this.fo = fo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		commentary = textArea.getText();

		//Ensures the commentary isn't empty.
		if (commentary.isEmpty() || commentary.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parentFrame, "Text can't be blank", "Text Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			String mp3 = fo.saveFile();

			if (mp3 != null) {
				mp3 = mp3.substring(0, mp3.length() - 4);

				// Creates the mp3 file in the background
				panel.setMp3Task(new CreateMP3(commentary, mp3, parentFrame));
				panel.getMp3Task().execute();
			}
		}
	}

}
