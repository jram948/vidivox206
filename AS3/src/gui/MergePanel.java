package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gui.actionlisteners.audio.AddAudioToTableActionListener;
import helpers.JTextFieldLimit;

@SuppressWarnings("serial")
public class MergePanel extends JPanel {

	private final int MAX_INSERT_TIME_LIMIT = 2;
	
	private HashMap<String, Number> audioToAdd = new HashMap<String, Number>();
	private JTable audioTable;
	private String[] columnNames = { "Audio file name", "Time to insert" };
	
	public MergePanel(VideoWindow parent) {
		
		setLayout(new BorderLayout());

		//Creating the table for audio to add
		audioTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(audioTable);
		audioTable.setFillsViewportHeight(true);
		DefaultTableModel model = new DefaultTableModel();
		audioTable.setModel(model);
		model.setColumnIdentifiers(columnNames);
		
		//Creating the various panels needed
		JPanel filePanel = new JPanel();
		JButton fileChooseBtn = new JButton("Choose audio to add");
		filePanel.add(fileChooseBtn);
	
		JPanel timePanel = new JPanel();
		JLabel timeLabel = new JLabel("Insert at time:");
		JLabel colon = new JLabel(":");
		JTextField insertMinutes = new JTextField(2);
		JTextField insertSeconds = new JTextField(2);
		timePanel.add(timeLabel);
		timePanel.add(insertMinutes);
		timePanel.add(colon);
		timePanel.add(insertSeconds);
		
		//Setting up the textfield limit and tooltip
		insertMinutes.setDocument(new JTextFieldLimit(MAX_INSERT_TIME_LIMIT));
		insertSeconds.setDocument(new JTextFieldLimit(MAX_INSERT_TIME_LIMIT));
		insertMinutes.setToolTipText("Time must be in format mm:ss");
		insertSeconds.setToolTipText("Time must be in format mm:ss");
		
		//Creating action listener for the audio button
		ActionListener addAL = new AddAudioToTableActionListener(parent, insertMinutes,
				insertSeconds, model, this);
		fileChooseBtn.addActionListener(addAL);
		
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.add(filePanel, BorderLayout.CENTER);
		buttonsPanel.add(timePanel, BorderLayout.SOUTH);
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	public HashMap<String, Number> getHashMap() { return audioToAdd; }
}
