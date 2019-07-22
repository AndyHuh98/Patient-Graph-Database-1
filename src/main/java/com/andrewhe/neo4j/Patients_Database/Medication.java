package com.andrewhe.neo4j.Patients_Database;

import java.util.ArrayList;
import java.util.Arrays;

public class Medication {
	/*
	public enum Med_Order_Type {
		adm,
		presc;
		
		public String toString() {
			switch(this) {
				case adm:
					return "adm";
				case presc:
					return "presc";
				default:
					return "other";
			}
		}
	}
	
	public enum Unit { //add more units later
		mg, mL, mEq, g;
		
		public String toString() {
			switch(this) {
				case mg: return "mg";
				case mL: return "mL";
				case mEq: return "mEq";
				case g: return "g";
				default: return "unit invalid";
			}
		}
	}
	
	public enum Med_Route { //add more types of application later
		Oral, IV_Push, Nebulization, IV_Piggyback;
		public String toString() {
			switch(this) {
				case Oral: return "Oral";
				case IV_Push: return "IV Push";
				case Nebulization: return "Nebulization";
				case IV_Piggyback: return "IV Piggyback";
				default: return "Other";
			}
		}
	}
	*/
	
	private static ArrayList<String> medication_ids = new ArrayList<String>();
	private String medication_order_id;
	private String med_encounter_id;
	private String medType;
	private String medName;
	private String medOrderDate;
	private String medStartDate;
	private String medEndDate;
	private double dosage;
	private String unit;
	private String application;
	
	//Getters and Setters:
	public String getMedOrderID() { return medication_order_id; }
	public String getMedEncounterID() { return med_encounter_id; }
	public String getMedType() { return medType; }
	public String getMedName() { return medName; }
	public String getMedOrderDate() { return medOrderDate; }
	public String getMedStartDate() { return medStartDate; }
	public String getMedEndDate() { return medEndDate; }
	public double getDosage() { return dosage; }
	public String getUnit() { return unit; }
	public String getApplication() { return application; }
	
	//Constructor
	public Medication(String medID, String encounter_id, String type, String name, String orderDate, String startDate, String endDate, double dose, String Unit, String route) {
		med_encounter_id = encounter_id; 
		medication_order_id = medID;
		medication_ids.add(medication_order_id);
		medType = type;
		medName = name;
		medOrderDate = orderDate;
		medStartDate = startDate;
		medEndDate = endDate;
		dosage = dose;
		unit = Unit;
		application = route;
	}
	
	public void printMedicationData() {
		System.out.printf("Medication Order ID: %s | Encounter ID: %s | Medication Order Type: %-5s | Medication Name: %-65s | Order Date: %s | Start Date: %s | End Date: %s | Dosage: %-6.2f%-3s | Application Method: %s\n", new String(getMedOrderID()), new String(getMedEncounterID()), getMedType().toString(), getMedName(), getMedOrderDate().toString(), getMedStartDate().toString(), getMedEndDate().toString(), getDosage(), getUnit().toString(), getApplication().toString());
	}
	
	@Override 
	public String toString() {
		String medData = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s %s\n%s", "Medication ID: " + getMedOrderID(), "Enc. ID: " + getMedEncounterID(), "Type: " + getMedType(), "Name: " + getMedName(), "Order Date: " + getMedOrderDate(), "Start Date: " + getMedStartDate(), "End Date: " + getMedEndDate(), "Dosage: " + getDosage(), getUnit(), "Application Route: " + getApplication());
		return medData;
	}
}
