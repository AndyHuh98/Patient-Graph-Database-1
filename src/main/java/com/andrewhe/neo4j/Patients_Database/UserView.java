package com.andrewhe.neo4j.Patients_Database;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumnModel;

public class UserView implements ActionListener {
	private JFrame frame;
	private MedicalGraph mg = new MedicalGraph();
	
	//Attributes for Patients Tab
	private JPanel patientButtonPanel;
	private JPanel patientInputPanel;
	private JTextArea patientTextInputForButtons = new JTextArea();
	private JTextArea patientButtonOutput = new JTextArea();
	
	//Attributes for Encounters Tab
	private JPanel encounterButtonPanel;
	private JPanel encounterInputPanel;
	private JTextArea encounterTextInputForButtons = new JTextArea();
	private JTextArea encounterButtonOutput = new JTextArea();
	
	//Attributes for Labs Tab
	private JPanel labButtonPanel;
	private JPanel labInputPanel;
	private JTextArea labTextInputForButtons = new JTextArea();
	private JTextArea labButtonOutput = new JTextArea();
	
	//Attributes for Diagnosis Tab
	private JPanel diagnosisButtonPanel;
	private JPanel diagnosisInputPanel;
	private JTextArea diagnosisTextInputForButtons = new JTextArea();
	private JTextArea diagnosisButtonOutput = new JTextArea();
	
	//Attributes for Medication Tab
	private JPanel medicationButtonPanel;
	private JPanel medicationInputPanel;
	private JTextArea medicationTextInputForButtons = new JTextArea();
	private JTextArea medicationButtonOutput = new JTextArea();

	//Attributes for Problem Tab
	private JPanel problemButtonPanel;
	private JPanel problemInputPanel;
	private JTextArea problemTextInputForButtons = new JTextArea();
	private JTextArea problemButtonOutput = new JTextArea();
	
	//Attributes for Procedure Tab
	private JPanel procedureButtonPanel;
	private JPanel procedureInputPanel;
	private JTextArea procedureTextInputForButtons = new JTextArea();
	private JTextArea procedureButtonOutput = new JTextArea();
	
	public UserView() throws IOException {
		mg.excelSetUp();	//UNCOMMENT AFTER DESIGNING UI
		//Setting up the Frame
		frame = new JFrame("Medical Data: Graph Database");
		frame.getContentPane();
		frame.setSize(1000,1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new GridLayout(3,1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel patientsPanel = findPatientsMenu();
		JPanel encountersPanel = findEncountersMenu();
		JPanel labsPanel = findLabsMenu();
		JPanel diagnosisPanel = findDiagnosisMenu();
		JPanel medicationPanel = findMedicationsMenu();
		JPanel problemPanel = findProblemsMenu();
		JPanel procedurePanel = findProceduresMenu();
		
		tabbedPane.addTab("Find Patients", null, patientsPanel, "Finds Patients");
		tabbedPane.addTab("Find Encounters", null, encountersPanel, "Finds Encounters");
		tabbedPane.addTab("Find Labs", null, labsPanel, "Finds Labs");
		tabbedPane.addTab("Find Diagnoses", null, diagnosisPanel, "Finds Diagnoses");
		tabbedPane.addTab("Find Medications", null, medicationPanel, "Find Medications");
		tabbedPane.addTab("Find Problems", null, problemPanel, "Finds Problems");
		tabbedPane.addTab("Find Procedures", null, procedurePanel, "Finds Procedures");
		
		frame.add(tabbedPane);

		frame.setVisible(true);
	}
	
	//Menu for finding patients
	public JPanel findPatientsMenu() throws IOException {
		JPanel PatientPanel = new JPanel();
		PatientPanel.setLayout(new GridLayout(3, 1));
		PatientPanel.setPreferredSize(new Dimension(frame.getWidth() - 50, frame.getHeight() - 50));
		
		//Creating buttons and adding them to panel
		patientButtonPanel = new JPanel();
		patientButtonPanel.setLayout(new GridLayout(3,1));
		
		//Piece 1
		JButton findPatientByIDButton = new JButton("Find Patient By ID");
		JButton findPatientByNameButton = new JButton("Find Patient By Name");
		JButton findPatientByCityButton = new JButton("Find Patient By City");
		
		//Piece 2
		JButton findPatientByEncounterIDButton = new JButton("Find Patient By Encounter ID"); //remove ID aspect soon
		JButton findPatientByLabIDButton = new JButton("Find Patient by Lab ID");
		JButton findPatientByDiagnosisIDButton = new JButton("Find Patient by Diagnosis ID");
		JButton findPatientByProcedureIDButton = new JButton("Find Patient by Procedure ID");
		JButton findPatientByMedicationIDButton = new JButton("Find Patient by Medication Order ID");
		JButton findPatientByProblemCodeButton = new JButton("Find Patient(s) by Problem Code");
		JButton findPatientsWhoHaveDiagnosis = new JButton("Find Patient(s) with Diagnosis (Input Code)");
		JButton findPatientsWhoHaveProblem = new JButton("Find Patient(s) with Problem (Input Description)");
		JButton findPatientsWhoHadProcedure = new JButton("Find Patient(s) with Procedure (Code)");
		
		//Piece3
		JButton findPatientByCYPHER = new JButton("Find Patient(s) Using Custom CYPHER Query");
		
		//Piece 1 of the 3 part panel (Name and ID buttons)
		JPanel piece1 = new JPanel();
		piece1.setLayout(new GridLayout(1, 3));

			findPatientByIDButton.setPreferredSize(new Dimension(200, 50));
			findPatientByNameButton.setPreferredSize(new Dimension(200, 50));
			findPatientByCityButton.setPreferredSize(new Dimension(200, 50));
			piece1.add(findPatientByIDButton);
			piece1.add(findPatientByNameButton);
			piece1.add(findPatientByCityButton);
		
		//Piece 2 of the 3 part panel (Encounter ID and items discovered through encounters (Labs, Medications, Diagnosis, Procedures)
		JPanel piece2 = new JPanel();
		piece2.setLayout(new GridLayout(3, 3));
		
			piece2.add(findPatientByEncounterIDButton);
			piece2.add(findPatientByLabIDButton);
			piece2.add(findPatientByDiagnosisIDButton);
			piece2.add(findPatientByProcedureIDButton);
			piece2.add(findPatientByMedicationIDButton);
			piece2.add(findPatientByProblemCodeButton);
			piece2.add(findPatientsWhoHaveDiagnosis);
			piece2.add(findPatientsWhoHaveProblem);
			piece2.add(findPatientsWhoHadProcedure);
				
		//Adding all the buttons to the button panel
		patientButtonPanel.add(piece1);
		patientButtonPanel.add(piece2);
		patientButtonPanel.add(findPatientByCYPHER);
		
		//Creating button actions
		findPatientByIDButton.addActionListener(this);
		findPatientByIDButton.setActionCommand("FPBID");
		findPatientByNameButton.addActionListener(this);
		findPatientByNameButton.setActionCommand("FPBN");
		findPatientByCityButton.addActionListener(this);
		findPatientByCityButton.setActionCommand("FPBC");
		findPatientByEncounterIDButton.addActionListener(this);
		findPatientByEncounterIDButton.setActionCommand("FPBEID");
		findPatientByLabIDButton.addActionListener(this);
		findPatientByLabIDButton.setActionCommand("FPBLID");
		findPatientByDiagnosisIDButton.addActionListener(this);
		findPatientByDiagnosisIDButton.setActionCommand("FPBDID");
		findPatientByProcedureIDButton.addActionListener(this);
		findPatientByProcedureIDButton.setActionCommand("FPBPID");
		findPatientByMedicationIDButton.addActionListener(this);
		findPatientByMedicationIDButton.setActionCommand("FPBMID");
		findPatientByProblemCodeButton.addActionListener(this);
		findPatientByProblemCodeButton.setActionCommand("FPBPC");
		findPatientByCYPHER.addActionListener(this);
		findPatientByCYPHER.setActionCommand("PatCYPHER");
		findPatientsWhoHaveDiagnosis.addActionListener(this);
		findPatientsWhoHaveDiagnosis.setActionCommand("FPWHD");
		findPatientsWhoHaveProblem.addActionListener(this);
		findPatientsWhoHaveProblem.setActionCommand("FPWHP");
		findPatientsWhoHadProcedure.addActionListener(this);
		findPatientsWhoHadProcedure.setActionCommand("FPWHPr");
		
		//Creating input panel for the buttons
		patientInputPanel = new JPanel();
		patientTextInputForButtons.setPreferredSize(new Dimension(750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		patientInputPanel.add(inputLabel);
		patientInputPanel.add(patientTextInputForButtons);
		
		//Result panel
		//patientResultPanel = new JPanel();	
		patientButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(patientButtonOutput);
		resultScroller.add(outputLabel);
		//patientResultPanel.add(resultScroller);
		//resultPanel.add(outputLabel);
		
		//Building frame and making it visible
		PatientPanel.add(patientButtonPanel);
		PatientPanel.add(patientInputPanel);
		PatientPanel.add(resultScroller);

		return PatientPanel;
	}
	
	//Menu for finding encounters
	public JPanel findEncountersMenu() {
		JPanel encounterPanel = new JPanel();
		encounterPanel.setLayout(new GridLayout(3, 1));
		
		//Creating button panel and adding buttons
		encounterButtonPanel = new JPanel();
		encounterButtonPanel.setLayout(new GridLayout(3, 1));
		
		//Creating the three piece panel
		JPanel piece1 = new JPanel();
		JPanel piece2 = new JPanel();
		JPanel piece3 = new JPanel();
		
		//Piece 1
		piece1.setLayout(new GridLayout(1, 4));
		JButton findEncounterByID = new JButton("Find Encounter by ID");
		findEncounterByID.addActionListener(this);
		findEncounterByID.setActionCommand("FEBID");
		JButton findEncounterByType = new JButton("Find Encounter(s) by Type");
		findEncounterByType.addActionListener(this);
		findEncounterByType.setActionCommand("FEBT");
		JButton findEncounterByDept = new JButton("Find Encounter(s) by Dept.");
		findEncounterByDept.addActionListener(this);
		findEncounterByDept.setActionCommand("FEBD");
		JButton findEncounterByPatientID = new JButton("Find Encounter(s) by Patient ID");
		findEncounterByPatientID.addActionListener(this);
		findEncounterByPatientID.setActionCommand("FEBPID");
		
		piece1.add(findEncounterByID);
		piece1.add(findEncounterByType);
		piece1.add(findEncounterByDept);
		piece1.add(findEncounterByPatientID);
		
		//Piece2
		piece2.setLayout(new GridLayout(1, 4));
		JButton findEncounterByLabID = new JButton("Find Encounter by Lab ID");
		findEncounterByLabID.addActionListener(this);
		findEncounterByLabID.setActionCommand("FEBLID");
		JButton findEncounterByDiagnosisID = new JButton("Find Encounter by Diagnosis ID");
		findEncounterByDiagnosisID.addActionListener(this);
		findEncounterByDiagnosisID.setActionCommand("FEBDID");
		JButton findEncounterByMedicationID = new JButton("Find Encounter by Medication ID");
		findEncounterByMedicationID.addActionListener(this);
		findEncounterByMedicationID.setActionCommand("FEBMID");
		JButton findEncounterByProblemCode = new JButton("Find Encounter by Problem Code");
		findEncounterByProblemCode.addActionListener(this);
		findEncounterByProblemCode.setActionCommand("FEBPC");
		
		piece2.add(findEncounterByLabID);
		piece2.add(findEncounterByDiagnosisID);
		piece2.add(findEncounterByMedicationID);
		piece2.add(findEncounterByProblemCode);
		
		//Piece3
		piece3.setLayout(new GridLayout(1, 3));
		JButton findEncountersBeforeDate = new JButton("Find Encounter(s) before Date");
		findEncountersBeforeDate.addActionListener(this);
		findEncountersBeforeDate.setActionCommand("FEBD");
		JButton findEncountersAfterDate = new JButton("Find Encounter(s) after Date");
		findEncountersAfterDate.addActionListener(this);
		findEncountersAfterDate.setActionCommand("FEAD");
		JButton findEncountersCYPHER = new JButton("Custom Cypher Query");
		findEncountersCYPHER.addActionListener(this);
		findEncountersCYPHER.setActionCommand("encounterCYPHER");
		
		piece3.add(findEncountersBeforeDate);
		piece3.add(findEncountersAfterDate);
		piece3.add(findEncountersCYPHER);
		
		//Adding pieces to the button panel
		encounterButtonPanel.add(piece1);
		encounterButtonPanel.add(piece2);
		encounterButtonPanel.add(piece3);
		
		//Adding input/result panels with text areas
		encounterInputPanel = new JPanel();
		encounterTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		encounterInputPanel.add(inputLabel);
		encounterInputPanel.add(encounterTextInputForButtons);
		
		encounterButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(encounterButtonOutput);
		resultScroller.add(outputLabel);
		
		//Adding panels to the master panel
		encounterPanel.add(encounterButtonPanel);
		encounterPanel.add(encounterInputPanel);
		encounterPanel.add(resultScroller);
		
		return encounterPanel;
	}
	
	public JPanel findLabsMenu() {
		JPanel labPanel = new JPanel(new GridLayout(3, 1));
		
		//Creating the button panel -- split into 3 pieces 
		JPanel piece1 = new JPanel(new GridLayout(1, 3));
		JPanel piece2 = new JPanel(new GridLayout(1, 3));
		JPanel piece3 = new JPanel(new GridLayout(1, 1));
		
		labButtonPanel = new JPanel(new GridLayout(3, 1));
		
		//Piece 1:
			//Buttons
			JButton findLabByID = new JButton("Find Lab by ID");
			findLabByID.addActionListener(this);
			findLabByID.setActionCommand("FLBID");
			JButton findLabByTestName = new JButton("Find Lab(s) by Test Name");
			findLabByTestName.addActionListener(this);
			findLabByTestName.setActionCommand("FLBTN");
			JButton findLabByEncounterID = new JButton("Find Lab(s) by Encounter ID");
			findLabByEncounterID.addActionListener(this);
			findLabByEncounterID.setActionCommand("FLBEID");
			
			piece1.add(findLabByID);
			piece1.add(findLabByTestName);
			piece1.add(findLabByEncounterID);
			
			labButtonPanel.add(piece1);
			
		//Piece 2:
			//Buttons
			JButton findLabByPatientID = new JButton("Find Lab(s) by Patient ID");
			findLabByPatientID.addActionListener(this);
			findLabByPatientID.setActionCommand("FLBPID");
			JButton findLabBeforeDate = new JButton("Find Lab(s) Before Date");
			findLabBeforeDate.addActionListener(this);
			findLabBeforeDate.setActionCommand("FLBD");
			JButton findLabAfterDate = new JButton("Find Lab(s) After Date");
			findLabAfterDate.addActionListener(this);
			findLabAfterDate.setActionCommand("FLAD");
			
			piece2.add(findLabByPatientID);
			piece2.add(findLabBeforeDate);
			piece2.add(findLabAfterDate);
			
			labButtonPanel.add(piece2);
			
		//Piece 3:
			//Buttons
			JButton cypherQuery = new JButton("Custom Cypher Query");
			cypherQuery.addActionListener(this);
			cypherQuery.setActionCommand("labCYPHER");
			
			piece3.add(cypherQuery);
			
			labButtonPanel.add(piece3);
			
		//Adding ButtonPanel to Main Panel
		labPanel.add(labButtonPanel);
			
		//Creating InputPanel and Output Panel
		labInputPanel = new JPanel();
		
		labTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		labInputPanel.add(inputLabel);
		labInputPanel.add(labTextInputForButtons);
		
		labButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(labButtonOutput);
		resultScroller.add(outputLabel);
		
		labPanel.add(labInputPanel);
		labPanel.add(resultScroller);
		
		return labPanel;
	}
	
	public JPanel findDiagnosisMenu() {
		//Main panel
		JPanel diagnosisPanel = new JPanel(new GridLayout (3, 1));
		
		//Button Panel:
		diagnosisButtonPanel = new JPanel(new GridLayout(3, 1));
		
		//Piece 1 of button panel:
		JPanel piece1 = new JPanel(new GridLayout(1, 4));
		
		JButton findDiagnosisByID = new JButton("Find Diagnosis by ID");
		findDiagnosisByID.addActionListener(this);
		findDiagnosisByID.setActionCommand("FDBID");
		JButton findDiagnosisByCode = new JButton("Find Diagnosis by Code");
		findDiagnosisByCode.addActionListener(this);
		findDiagnosisByCode.setActionCommand("FDBC");
		JButton findDiagnosisByDescription = new JButton("Find Diagnosis by Description");
		findDiagnosisByDescription.addActionListener(this);
		findDiagnosisByDescription.setActionCommand("FDBD");
		JButton findDiagnosisByPatientID = new JButton("Find Diagnosis by Patient ID");
		findDiagnosisByPatientID.addActionListener(this);
		findDiagnosisByPatientID.setActionCommand("FDBPID");
		
		piece1.add(findDiagnosisByID);
		piece1.add(findDiagnosisByCode);
		piece1.add(findDiagnosisByDescription);
		piece1.add(findDiagnosisByPatientID);
		
		diagnosisButtonPanel.add(piece1);
		
		//Piece 2 of button panel:
		JPanel piece2 = new JPanel(new GridLayout(1, 3));
		
		JButton findDiagnosisByEncID = new JButton("Find Diagnosis by Encounter ID");
		findDiagnosisByEncID.addActionListener(this);
		findDiagnosisByEncID.setActionCommand("FDBEID");
		JButton findDiagnosesBeforeDate = new JButton("Find Diagnoses Before Date");
		findDiagnosesBeforeDate.addActionListener(this);
		findDiagnosesBeforeDate.setActionCommand("FDBDate");
		JButton findDiagnosesAfterDate = new JButton("Find Diagnoses After Date");
		findDiagnosesAfterDate.addActionListener(this);
		findDiagnosesAfterDate.setActionCommand("FDADate");
		
		piece2.add(findDiagnosisByEncID);
		piece2.add(findDiagnosesBeforeDate);
		piece2.add(findDiagnosesAfterDate);
		
		diagnosisButtonPanel.add(piece2);
		
		//Piece 3 of button panel:
		JButton cypherQuery = new JButton("Custom Cypher Query");
		cypherQuery.addActionListener(this);
		cypherQuery.setActionCommand("diagnosisCYPHER");
		
		diagnosisButtonPanel.add(cypherQuery);
		
		//Adding button panel, input panel, result panel to main panel
		diagnosisPanel.add(diagnosisButtonPanel);
		
		//Creating Input and Result Panels
		diagnosisInputPanel = new JPanel();
		
		diagnosisTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		diagnosisInputPanel.add(inputLabel);
		diagnosisInputPanel.add(diagnosisTextInputForButtons);
		
		diagnosisButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(diagnosisButtonOutput);
		resultScroller.add(outputLabel);
		
		diagnosisPanel.add(diagnosisInputPanel);
		diagnosisPanel.add(resultScroller);
		
		return diagnosisPanel;
	}
	
	public JPanel findMedicationsMenu() {
		//Main Panel
		JPanel medicationPanel = new JPanel(new GridLayout(3, 1));
		
		//Button Panel
		medicationButtonPanel = new JPanel(new GridLayout(4, 1));
		
		//Piece 1
		JPanel piece1 = new JPanel(new GridLayout(1, 4));
		
		JButton findMedicationByID = new JButton("Find Medication By ID");
		findMedicationByID.addActionListener(this);
		findMedicationByID.setActionCommand("FMBID");
		JButton findMedicationByType = new JButton("Find Medication by Type");
		findMedicationByType.addActionListener(this);
		findMedicationByType.setActionCommand("FMBT");
		JButton findMedicationByName = new JButton("Find Medication by Name");
		findMedicationByName.addActionListener(this);
		findMedicationByName.setActionCommand("FMBN");
		JButton findMedicationByRoute = new JButton("Find Medication by Route");
		findMedicationByRoute.addActionListener(this);
		findMedicationByRoute.setActionCommand("FMBR");
		
		piece1.add(findMedicationByID);
		piece1.add(findMedicationByType);
		piece1.add(findMedicationByName);
		piece1.add(findMedicationByRoute);	
		
		medicationButtonPanel.add(piece1);
		
		//Piece 2
		JPanel piece2 = new JPanel(new GridLayout (1, 2));
		
		JButton findMedicationByEncID = new JButton("Find Medication by Encounter ID");
		findMedicationByEncID.addActionListener(this);
		findMedicationByEncID.setActionCommand("FMBEID");
		JButton findMedicationByPatientID = new JButton("Find Medication by Patient ID");
		findMedicationByPatientID.addActionListener(this);
		findMedicationByPatientID.setActionCommand("FMBPID");
		
		piece2.add(findMedicationByEncID);
		piece2.add(findMedicationByPatientID);
		
		
		medicationButtonPanel.add(piece2);
		
		//Piece 3
		JPanel piece3 = new JPanel(new GridLayout(1, 3));
		
		JButton findMedicationByOrderDate = new JButton("Find Medication by Order Date");
		findMedicationByOrderDate.addActionListener(this);
		findMedicationByOrderDate.setActionCommand("FMBOD");
		JButton findMedicationOrderedBefore = new JButton("Find Medication(s) Ordered Before Date");
		findMedicationOrderedBefore.addActionListener(this);
		findMedicationOrderedBefore.setActionCommand("FMOB");
		JButton findMedicationOrderedAfter = new JButton("Find Medication Ordered After Date");
		findMedicationOrderedAfter.addActionListener(this);
		findMedicationOrderedAfter.setActionCommand("FMOA");
		
		piece3.add(findMedicationByOrderDate);
		piece3.add(findMedicationOrderedBefore);
		piece3.add(findMedicationOrderedAfter);
		
		medicationButtonPanel.add(piece3);
		
		//Piece 4
		JButton cypherQuery = new JButton("Custom Cypher Query");
		cypherQuery.addActionListener(this);
		cypherQuery.setActionCommand("medicationCYPHER");
		
		medicationButtonPanel.add(cypherQuery);
		
		
		//Adding to the main panel
		medicationPanel.add(medicationButtonPanel);
		
		//Adding the input Panel and result panel
		medicationInputPanel = new JPanel();
		
		medicationTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		medicationInputPanel.add(inputLabel);
		medicationInputPanel.add(medicationTextInputForButtons);
		
		medicationButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(medicationButtonOutput);
		resultScroller.add(outputLabel);
		
		//Adding to main panel:
		medicationPanel.add(medicationInputPanel);
		medicationPanel.add(resultScroller);
		
		return medicationPanel;
	}
	
	public JPanel findProblemsMenu() {
		//Main Panel
		JPanel problemPanel = new JPanel(new GridLayout(3, 1));
		
		//Button Panel
		problemButtonPanel = new JPanel(new GridLayout(3, 1));
		
		//Piece 1:
		JPanel piece1 = new JPanel(new GridLayout(1, 3));
		
		JButton findProblemByPatientID = new JButton("Find Problem(s) by Patient ID");
		findProblemByPatientID.addActionListener(this);
		findProblemByPatientID.setActionCommand("FPPBPID");
		JButton findProblemByCode = new JButton("Find Problem(s) by Code");
		findProblemByCode.addActionListener(this);
		findProblemByCode.setActionCommand("FPPBC");
		JButton findProblemByDescription = new JButton("Find Problem(s) by Description");
		findProblemByDescription.addActionListener(this);
		findProblemByDescription.setActionCommand("FPPBD");
		
		piece1.add(findProblemByPatientID);
		piece1.add(findProblemByCode);
		piece1.add(findProblemByDescription);
		
		problemButtonPanel.add(piece1);
		
		//Piece 2:
		JPanel piece2 = new JPanel(new GridLayout(1, 3));
		
		JButton findProblemByOnsetDate = new JButton("Find Problem(s) by Onset Date");
		findProblemByOnsetDate.addActionListener(this);
		findProblemByOnsetDate.setActionCommand("FPPBOD");
		JButton findProblemsResolvedBefore = new JButton("Find Problem(s) Resolved On or Before Date");
		findProblemsResolvedBefore.addActionListener(this);
		findProblemsResolvedBefore.setActionCommand("FPPRB");
		JButton findProblemsResolvedAfter = new JButton("Find Problem(s) Resolved After Date");
		findProblemsResolvedAfter.addActionListener(this);
		findProblemsResolvedAfter.setActionCommand("FPPRA");
		
		piece2.add(findProblemByOnsetDate);
		piece2.add(findProblemsResolvedBefore);
		piece2.add(findProblemsResolvedAfter);
		
		problemButtonPanel.add(piece2);
		
		//Piece 3
		JButton cypherQuery = new JButton("Custom Cypher Query");
		cypherQuery.addActionListener(this);
		cypherQuery.setActionCommand("problemCYPHER");
		
		problemButtonPanel.add(cypherQuery);
		
		
		//Adding the input Panel and result panel
		problemInputPanel = new JPanel();
		
		problemTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		problemInputPanel.add(inputLabel);
		problemInputPanel.add(problemTextInputForButtons);
		
		problemButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(problemButtonOutput);
		resultScroller.add(outputLabel);
		
		//adding to main panel
		problemPanel.add(problemButtonPanel);
		problemPanel.add(problemInputPanel);
		problemPanel.add(resultScroller);
		
		return problemPanel;
	}
	
	public JPanel findProceduresMenu() {
		//Main Panel
		JPanel procedurePanel = new JPanel(new GridLayout(3, 1));
		
		//Button Panel
		procedureButtonPanel = new JPanel(new GridLayout(3, 1));
		
		//Piece 1:
		JPanel piece1 = new JPanel(new GridLayout(1, 4));
		
		JButton findProcedureByID = new JButton("Find Procedure by ID");
		findProcedureByID.addActionListener(this);
		findProcedureByID.setActionCommand("FPrBID");
		JButton findProcedureByCode = new JButton("Find Procedure(s) by Code");
		findProcedureByCode.addActionListener(this);
		findProcedureByCode.setActionCommand("FPrBC");
		JButton findProcedureByName = new JButton("Find Procedure(s) by Name");
		findProcedureByName.addActionListener(this);
		findProcedureByName.setActionCommand("FPrBN");
		JButton findProcedureByProvider = new JButton("Find Procedure(s) by Provider");
		findProcedureByProvider.addActionListener(this);
		findProcedureByProvider.setActionCommand("FPrBP");
		
		piece1.add(findProcedureByID);
		piece1.add(findProcedureByCode);
		piece1.add(findProcedureByName);
		piece1.add(findProcedureByProvider);
		
		procedureButtonPanel.add(piece1);
		
		//Piece 2:
		JPanel piece2 = new JPanel(new GridLayout(1, 4));
		
		JButton findProcedureByEncounterID = new JButton("Find Procedure by Encounter ID");
		findProcedureByEncounterID.addActionListener(this);
		findProcedureByEncounterID.setActionCommand("FPrBEID");
		JButton findProcedureByPatientID = new JButton("Find Procedure(s) by Patient ID");
		findProcedureByPatientID.addActionListener(this);
		findProcedureByPatientID.setActionCommand("FPrBPID");
		JButton findProcedureBeforeDate = new JButton("Find Procedure(s) Before Date");
		findProcedureBeforeDate.addActionListener(this);
		findProcedureBeforeDate.setActionCommand("FPrBD");
		JButton findProcedureAfterDate = new JButton("Find Procedure(s) After Date");
		findProcedureAfterDate.addActionListener(this);
		findProcedureAfterDate.setActionCommand("FPrAD");
		
		piece2.add(findProcedureByEncounterID);
		piece2.add(findProcedureByPatientID);
		piece2.add(findProcedureBeforeDate);
		piece2.add(findProcedureAfterDate);
		
		procedureButtonPanel.add(piece2);
		
		//Piece 3:
		JButton cypherQuery = new JButton("Custom Cypher Query");
		cypherQuery.addActionListener(this);
		cypherQuery.setActionCommand("procedureCYPHER");
		
		procedureButtonPanel.add(cypherQuery);
		
		//Adding the input Panel and result panel
		procedureInputPanel = new JPanel();
		
		procedureTextInputForButtons.setPreferredSize(new Dimension (750, 200));
		JLabel inputLabel = new JLabel("Input text in the field below, then select a query button.");
		inputLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
		inputLabel.setHorizontalAlignment(JLabel.CENTER);
		procedureInputPanel.add(inputLabel);
		procedureInputPanel.add(procedureTextInputForButtons);
		
		procedureButtonOutput.setEditable(false);
		JLabel outputLabel = new JLabel("The output of your query will appear below: ");
		outputLabel.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane resultScroller = new JScrollPane(procedureButtonOutput);
		resultScroller.add(outputLabel);
		
		//adding to main panel
		procedurePanel.add(procedureButtonPanel);
		procedurePanel.add(procedureInputPanel);
		procedurePanel.add(resultScroller);
		
		return procedurePanel;
	}
	
	public <T> void setOutputText(ArrayList<T> elements, JTextArea output) {
		if (elements.size() == 0) {
			output.setText("Query search found zero matches.");
		} else {
			String dataText = "";
			int i = 0;
			for (T element : elements) {
				i++;
				dataText += element.toString() + "\n" + "---------------------------\n";
			}
			String count = String.format("-----------Matches found: %d-----------\n", i);
			count += dataText;
			output.setText(count);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
			//Find Patient Commands:
			case "FPBID":
				ArrayList<Patient> patients = mg.findPatient("Patient", "patient_id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBN":
				patients = mg.findPatient("Patient", "name", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBC":
				patients = mg.findPatient("Patient", "city", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBEID":
				patients = mg.findPatient("Encounter", "encounter_id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBLID":
				patients = mg.findPatient("Lab", "id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBDID":
				patients = mg.findPatient("Diagnosis", "id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBPID":
				patients = mg.findPatient("Procedure", "id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBMID":
				patients = mg.findPatient("Medication", "id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPBPC":
				patients = mg.findPatient("Patient_Problem", "id", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPWHD":
				patients = mg.findPatient("Diagnosis", "diagnosis_code", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPWHP":
				patients = mg.findPatient("Patient_Problem", "problem_description", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "FPWHPr":
				patients = mg.findPatient("Procedure", "procedure_code", patientTextInputForButtons.getText());
				setOutputText(patients, patientButtonOutput);
				break;
			case "PatCYPHER":
				String text = mg.executeCypherQuery(patientTextInputForButtons.getText());
				patientButtonOutput.setText(text);
				break;
			//Find Encounter Commands
			case "FEBID":
				ArrayList<Encounter> encounters = mg.findElement("Encounter", "Encounter", "encounter_id", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBT":
				encounters = mg.findElement("Encounter", "Encounter", "encounter_type", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBD":
				encounters = mg.findElement("Encounter", "Encounter", "department", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBPID":
				encounters = mg.findElement("Encounter", "Encounter", "patient_id", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBLID":
				encounters = mg.findElement("Encounter", "Lab", "id", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBDID":
				encounters = mg.findElement("Encounter", "Diagnosis", "id", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBMID":
				encounters = mg.findElement("Encounter", "Medication", "id", encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			case "FEBPC":
				encounters = mg.findEncounterFromProblem(encounterTextInputForButtons.getText());
				setOutputText(encounters, encounterButtonOutput);
				break;
			//case "FEBD":
			//case "FEAD":
			case "encounterCYPHER":
				text = mg.executeCypherQuery(encounterTextInputForButtons.getText());
				encounterButtonOutput.setText(text);
			//Find Lab Commands
			case "FLBID":
				ArrayList<Lab> labs = mg.findElement("Lab", "Lab", "id", labTextInputForButtons.getText());
				setOutputText(labs, labButtonOutput);
				break;
			case "FLBTN":
				labs = mg.findElement("Lab", "Lab", "test_name", labTextInputForButtons.getText());
				setOutputText(labs, labButtonOutput);
				break;
			case "FLBEID":
				labs = mg.findElement("Lab", "Encounter", "encounter_id", labTextInputForButtons.getText());
				setOutputText(labs, labButtonOutput);
				break;
			case "FLBPID":
				labs = mg.findElement("Lab", "Patient", "patient_id", labTextInputForButtons.getText());
				setOutputText(labs, labButtonOutput);
				break;
			//case "FLBD":
			//case "FLAD":
			case "labCYPHER":
				text = mg.executeCypherQuery(labTextInputForButtons.getText());
				labButtonOutput.setText(text);
				break;
			//Find Diagnosis Commands:
			case "FDBID":
				ArrayList<Diagnosis> diagnoses = mg.findElement("Diagnosis", "Diagnosis", "id", diagnosisTextInputForButtons.getText());
				setOutputText(diagnoses, diagnosisButtonOutput);
				break;
			case "FDBC":
				diagnoses = mg.findElement("Diagnosis", "Diagnosis", "diagnosis_code", diagnosisTextInputForButtons.getText());
				setOutputText(diagnoses, diagnosisButtonOutput);
				break;
			case "FDBD":
				diagnoses = mg.findElement("Diagnosis", "Diagnosis", "diagnosis_description", diagnosisTextInputForButtons.getText());
				setOutputText(diagnoses, diagnosisButtonOutput);
				break;
			case "FDBPID":
				diagnoses = mg.findElement("Diagnosis", "Patient", "patient_id", diagnosisTextInputForButtons.getText());
				setOutputText(diagnoses, diagnosisButtonOutput);
				break;
			case "FDBEID":
				diagnoses = mg.findElement("Diagnosis", "Encounter", "encounter_id", diagnosisTextInputForButtons.getText());
				setOutputText(diagnoses, diagnosisButtonOutput);
				break;
			//case "FDBDate":
			//case "FDADate":
			case "diagnosisCYPHER":
				text = mg.executeCypherQuery(diagnosisTextInputForButtons.getText());
				diagnosisButtonOutput.setText(text);
				break;
			//Find Medication Commands:
			case "FMBID":
				ArrayList<Medication> medications = mg.findElement("Medication", "Medication", "id", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			case "FMBT":
				medications = mg.findElement("Medication", "Medication", "med_type", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			case "FMBN":
				medications = mg.findElement("Medication", "Medication", "med_name", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			case "FMBR":
				medications = mg.findElement("Medication", "Medication", "route", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			case "FMBEID":
				medications = mg.findElement("Medication", "Encounter", "encounter_id", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			case "FMBPID":
				medications = mg.findElement("Medication", "Patient", "patient_id", medicationTextInputForButtons.getText());
				setOutputText(medications, medicationButtonOutput);
				break;
			//case "FMBOD":
			//case "FMOB":
			//case "FMOA":
			case "medicationCYPHER":
				text = mg.executeCypherQuery(medicationTextInputForButtons.getText());
				medicationButtonOutput.setText(text);
				break;
			//Find Procedure Commands:
			case "FPrBID":
				ArrayList<Procedure> procedures = mg.findElement("Procedure", "Procedure", "id", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			case "FPrBC":
				procedures = mg.findElement("Procedure", "Procedure", "procedure_code", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			case "FPrBN":
				procedures = mg.findElement("Procedure", "Procedure", "procedure_name", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			case "FPrBP":
				procedures = mg.findElement("Procedure", "Procedure", "provider", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			case "FPrBEID":
				procedures = mg.findElement("Procedure", "Encounter", "encounter_id", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			case "FPrBPID":
				procedures = mg.findElement("Procedure", "Patient", "patient_id", procedureTextInputForButtons.getText());
				setOutputText(procedures, procedureButtonOutput);
				break;
			//case "FPrBD":
			//case "FPrAD":
			case "procedureCYPHER":
				text = mg.executeCypherQuery(procedureTextInputForButtons.getText());
				procedureButtonOutput.setText(text);
				break;
			//Find Problems Commands:
			case "FPPBPID":
				ArrayList<Patient_Problem> problems = mg.findElement("Patient_Problem", "Patient", "patient_id", problemTextInputForButtons.getText());
				setOutputText(problems, problemButtonOutput);
				break;
			case "FPPBC":
				problems = mg.findElement("Patient_Problem", "Patient_Problem", "id", problemTextInputForButtons.getText());
				setOutputText(problems, problemButtonOutput);
				break;
			case "FPPBD":
				problems = mg.findElement("Patient_Problem", "Patient_Problem", "problem_description", problemTextInputForButtons.getText());
				setOutputText(problems, problemButtonOutput);
				break;
			//case "FPPBOD":
			//case "FPPRB":
			//case "FPPRA":
			case "problemCYPHER":
				text = mg.executeCypherQuery(problemTextInputForButtons.getText());
				problemButtonOutput.setText(text);
				break;
		}
	}
	
	public static void main(String[] args) throws IOException {
		UserView userView = new UserView();
	}

}
