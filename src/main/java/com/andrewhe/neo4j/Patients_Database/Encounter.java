package com.andrewhe.neo4j.Patients_Database;

import java.util.ArrayList;
import java.util.Arrays;

import scala.Array;

public class Encounter {
	public enum Encounter_Type {
		INPATIENT,
		EMERGENCY,
		OUTPATIENT,
		OTHER;
		
		public String toString() {
			switch(this) {
				case INPATIENT: 
					return "Inpatient";
				case EMERGENCY:
					return "Emergency";
				case OUTPATIENT:
					return "Outpatient";
				default:
					return "Other";
			}
		}
	}
	
	private static ArrayList<String> encounter_ids = new ArrayList<String>();
	private String encounter_id;
	private String patient_id; //will be supplied in constructor
	private String start_date;
	private String end_date;
	private Encounter_Type type;
	private String department; //could make into enum later
	//private ArrayList<Disorders> diagnosed = new ArrayList<Disorders>(); //disorders diagnosed during each encounter
	//private ArrayList<Medication> medsUsed = new ArrayList<Medication>(); //medications administered or prescribed during encounter
	
	//Getters and Setters:
	public ArrayList<String> getEncounterIDs() { return encounter_ids; }
	public String getEncounterID() { return encounter_id; }
	public String getPatientIDForEncounter() { return patient_id; }
	public String getStartDateForEncounter() { return start_date; }
	public String getEndDateForEncounter() { return end_date; }
	public Encounter_Type getEncounterType() { return type; }
	public String getDepartment() { return department; }
	
	
	//Master constructor - received via file. --> potential change: passing in a patient?
	public Encounter(String EncounterID, String PatientID, String startDate, String endDate, Encounter_Type Type, String Dept) {
		patient_id = PatientID; //check to see if patient exists
		encounter_id = EncounterID;
		encounter_ids.add(encounter_id);
		start_date = startDate;
		end_date = endDate;
		type = Type;
		department = Dept;
	}
	
	@Override
	public String toString() {
		if (this != null) {
			String encounterData = String.format("%s\n%s\n%s\n%s\n%s\n%s", "Enc. ID: " + getEncounterID(), "Pat. ID: " + getPatientIDForEncounter(), "Start Date: " + getStartDateForEncounter(), "End Date: " + getEndDateForEncounter(), "Encounter Type: " + getEncounterType().toString(), "Department " + getDepartment());
			return encounterData;
		} 
		return "Encounter not found.";
	}
	
	public void printEncounterData() {
		System.out.printf("Encounter ID: %-8s | Patient ID: %-10s | Encounter Start Date: %-10s | Encounter End Date: %-10s | Type: %-12s | Department: %s\n", new String(getEncounterID()), new String(getPatientIDForEncounter()), getStartDateForEncounter().toString(), getEndDateForEncounter().toString(), getEncounterType().toString(), getDepartment());
	}
}
