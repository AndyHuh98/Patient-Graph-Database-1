package com.andrewhe.neo4j.Patients_Database;

import java.util.HashMap;

public class Diagnosis {
	//REMEMBER TO DO ERROR HANDLING ONCE ALL CLASSES ARE FLESHED OUT

	//Attributes
	private String diagnosis_id; //unique ID for each time a patient is diagnosed with a disorder during an encounter
	private String diagnosis_enc_id; //encounter ID, used to relate a diagnosis with a certain encounter
	private String diagnosisCode; //unique ID for each TYPE of diagnosis (ex: all GSI diagnoses have the same code)
	private String diagnosisCodeDescription; //String description of the problem, eventually utilize a switch (pass in code)
	private String diagnosisFirstSymptomDate; //When symptoms were first noticed
	private String diagnosisDate; //When the diagnosis occurred
	//private possibly not necessary -- see what happens whenever its fleshed out
	/*
	private static HashMap<String, String> codeToDescriptionMap = new HashMap<String, String>() {{
		put("C24.1", "Ampullary carcinoma (HCC)");
		put("C25.9", "Pancreatic adenocarcinoma (HCC)");
		put("C90.00", "Multiple myeloma not having achieved remission (HCC)");
		put("D70.1", "Chemotherapy induced neutropenia (HCC)");
		put("E11.40", "Type 2 diabetes, uncontrolled, with neuropathy (HCC)");
		put("E11.65", "Type 2 diabetes, uncontrolled, with neuropathy (HCC)"); //bug?
		put("E83.51", "Hypocalcemia");
		put("E87.6", "Hypokalemia");
		put("F25.9", "Schizoaffective disorder, unspecified type (HCC)");
		put("F33.1", "Depression, major, recurrent, moderate (HCC)");
		put("F40.00", "Agoraphobia");
		put("F41.0", "Panic disorder");
		put("F41.1", "Generalized anxiety disorder");
		put("I10", "Essential hypertension");
		put("K22.2", "Esophageal stricture");
		put("L30.9", "Dermatitis, unspecified");
		put("K94.23", "PEG tube malfunction (HCC)");
		put("N39.3", "GSI (genuine stress incontinence), female");
		put("R11.0", "Nausea");
		put("R35.0", "Increased frequency of urination");
		put("S93.40", "Sprain of unspecified ligament of ankle");
		put("Z51.81", "Medication monitoring encounter");
	}};
	*/
	
	//Getters and Setters
	public String getDiagnosisID() { return diagnosis_id; }
	public String getDiagnosisEncID() { return diagnosis_enc_id; }
	public String getDiagnosisCode() { return diagnosisCode; }
	public String getDiagnosisDescription() { return diagnosisCodeDescription; } 
	public String getDiagSympDate() { return diagnosisFirstSymptomDate; }
	public String getDiagDate() { return diagnosisDate; }
	
	//Method that returns a string description of a particular diagnosis code that's passed in
	/*
	public String codeDescriptor(String diagCode) {
		if (codeToDescriptionMap.containsKey(diagCode)) {
			return codeToDescriptionMap.get(diagCode);
		} else {
			return "Invalid diagnosis code";
		}
	}
	*/
	
	//Constructor
	public Diagnosis(String DiagnosisID, String EncDiagID, String DiagCode, String diagDescription, String DiagFirstSymptom, String DiagDate) {
		diagnosis_id = DiagnosisID;
		diagnosis_enc_id = EncDiagID;
		diagnosisCode = DiagCode;
		diagnosisCodeDescription = diagDescription;
		diagnosisFirstSymptomDate = DiagFirstSymptom;
		diagnosisDate = DiagDate;
	}
	
	//Prints all data related to diagnoses
	public void printDiagnosisData() {
		System.out.printf("Diagnosis ID: %-10s | Encounter ID: %-8s | Diagnosis Code: %6s | Description: %-55s | First Symptom Onset Date: %11s | Diagnosis Date: %11s\n", new String(getDiagnosisID()), new String(getDiagnosisEncID()), getDiagnosisCode(), getDiagnosisDescription(), getDiagSympDate().toString(), getDiagDate().toString());
	}
	
	@Override
	public String toString() {
		String diagData = String.format("%s\n%s\n%s\n%s\n%s\n%s", "Diagnosis ID: " + getDiagnosisID(), "Enc. ID: " + getDiagnosisEncID(), "Diagnosis Code: " + getDiagnosisCode(), "Diagnosis Description: " + getDiagnosisDescription(), "First Symptom Date: " + getDiagSympDate(), "Diagnosis Date: " + getDiagDate());
		return diagData;
	}
}
