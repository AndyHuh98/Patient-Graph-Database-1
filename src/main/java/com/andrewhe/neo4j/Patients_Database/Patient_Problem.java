package com.andrewhe.neo4j.Patients_Database;

import java.util.HashMap;

//A "Problem" is basically like a symptom -- offers hints to potential diagnosis
public class Patient_Problem {
	private String Prb_patientID;
	private String problemCode;
	private String problemDescription;
	private String symptomOnsetDate;
	private String symptomResolutionDate;
	//If descriptions aren't standardized, just check using containsKey and don't link value to it.
	//Ex: if map contains problemcode, then valid, else not valid

	/*
	private static HashMap<String, String> problemCodeToDescription = new HashMap<String, String>() {{ 
		put("D64.9", "Anemia");
		put("E13.10", "Diabetic ketoacidosis (HCC)");
		put("E87.2", "Alcoholic ketoacidosis");
		put("E87.8", "Electrolyte imbalance");
		put("F10.10", "Alcohol abuse");
		put("F10.239", "Alcohol withdrawal (HCC)");
		put("G62.1", "Alcoholic peripheral neuropathy (HCC)");
		put("I46.9", "Cardiac Arrest (HCC)");
		put("J18.9", "CAP (community acquired pneumonia");
		put("K83.8", "Dilated bile duct");
		put("K92.2", "Upper GI bleed");
		put("L02.419", "Axillary abscess");
		put("L73.9", "Folliculitis");
		put("N17.9", "AKI (acute kidney injury) (HCC)");
		put("N61.1", "Breast abscess");
		put("N63.10", "Breast mass, right");
		put("R07.9", "Chest pain");
		put("R74.8", "Elevated liver enzymes");
	}};
	*/
	
	//Getters and Setters
	public String getPrbPatientID() { return Prb_patientID; }
	public String getProblemCode() { return problemCode; }
	public String getProblemDescription() { return problemDescription; }
	public String getSymptomOnsetDate() { return symptomOnsetDate; }
	public String getSymptomResolutionDate() { return symptomResolutionDate; }
	
	//Method to retrieve description from dictionary based on code
	/*
	public String codeToDescription(String code) {
		return problemCodeToDescription.get(code);
	}
	*/

	//Constructor
	public Patient_Problem(String PatientID, String sympOnset, String probCode, String probDescription, String resolutionDate) {
		Prb_patientID = PatientID;
		problemCode = probCode;
		problemDescription = probDescription;
		symptomOnsetDate = sympOnset;
		symptomResolutionDate = resolutionDate;
	}
	
	public void printPatientProblemData() {
		System.out.printf("Patient ID: %s | Problem Code: %-8s | Description: %-40s | Symptom Onset Date: %s | Symptom Resolution Date: %-11s\n", new String(getPrbPatientID()), getProblemCode(), getProblemDescription(), getSymptomOnsetDate(), getSymptomResolutionDate());
	}
	
	@Override
	public String toString() {
		String probData = String.format("%s\n%s\n%s\n%s\n%s", "Patient ID: " + getPrbPatientID(), "Problem Code: " + getProblemCode(), "Description: " + getProblemDescription(), "Symptom Onset Date: " + getSymptomOnsetDate(), "Resolution Date: " + getSymptomResolutionDate());
		return probData;
	}
}
