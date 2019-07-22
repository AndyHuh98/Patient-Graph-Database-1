package com.andrewhe.neo4j.Patients_Database;

import java.util.HashMap;

public class Lab {
	//Attributes
	private String labID; //unique lab ID for each lab
	private String labEncID; //labID linked to an existing encounter
	private String labOrderDate; //date the lab was ordered
	private String labCollectionDate; //date the lab was performed (?)
	private String labResultDate; //date the lab results were delivered
	private String testName; //name of the lab test
	private String unit; //should I change this to an enum? unit of measurement
	private double value; //value of the lab result
	/*
	private static HashMap<String, String> testNameToUnit = new HashMap<String, String>() {{
		//put("INVALID", "INVALID");
		put("ALBUMIN", "g/dL");
		put("BANDS, BMAR", "%");
		put("BASOS, BMAR", "%");
		put("BILIRUBIN TOTAL", "mg/dL");
		put("BLASTS, BMAR", "%");
		put("BLOOD UREA NITRO", "mg/dL");
		put("CALCIUM", "mg/dL");
		put("CHLORIDE", "mmol/L");
		put("CHOLESTEROL", "mg/dL");
		put("CO2", "mmol/L");
		put("CREATININE", "mmol/L");
		put("EOS, BMAR", "%");
		put("GLUCOSE RANDOM", "mg/dL");
		put("HDL CHOLESTEROL", "mg/dL");
		put("HEMATOCRIT", "%");
		put("HEMOGLOBIN", "g/dL");
		put("HISTIOCYTE, BMAR", "%");
		put("LYMPHS, BMAR", "%");
		put("LDL CHOLESTEROL CALCULATED", "mg/dL");
		put("MALB/CREAT RATIO", "mg/g");
		put("MEAN CORPUSCULAR HEMOGLOBIN", "pg");
		put("MEAN CORPUSCULAR HEMOGLOBIN CONC", "g/dL");
		put("MEAN CORPUSCULAR VOLUME", "fL");
		put("MEAN PLATELET VOLUME", "fL");
		put("PLATELET COUNT", "K/uL");
		put("METAMYELOS, BMAR", "%");
		put("MONOCYTES, BMAR", "%");
		put("MYELOCYTE, BMAR", "%");
		put("POC CREATININE", "mg/dL");
		put("POTASSIUM", "mmol/L");
		put("PROTEIN, TOTAL (SERUM)", "g/dL");
		put("RED BLOOD CELL COUNT", "M/uL");
		put("SODIUM", "mmol/L");
		put("TRIGLYCERIDES", "mg/dL");
		put("VITAMIN B-12", "pg/mL");
		put("VITAMIN D 25-HYDROXY", "ng/mL");
		put("WHITE BLOOD CELL COUNT", "K/uL");
	}};
	*/
	
	//Method to retrieve the proper unit of measurement for a respective test
	/*
	public String unit(String test) {
		if (testNameToUnit.containsKey(test)) {
			return testNameToUnit.get(test);
		} else {
			return "INVALID";
		}
	}
	*/
	
	//Getters and Setters:
	public String getLabID() { return labID; }
	public String getLabEncID() { return labEncID; }
	public String getLabOrderDate() { return labOrderDate; }
	public String getLabCollectionDate() { return labCollectionDate; }
	public String getLabResultDate() { return labResultDate; }
	public String getTestName() { return testName; }
	public String getUnit() { return unit; }
	public double getValue() { return value; }
	
	//Constructor
	public Lab(String LabID, String encounterID, String orderDate, String collectionDate, String resultDate, String test, double value, String unit) {
		labID = LabID;
		labEncID = encounterID;
		labOrderDate = orderDate;
		labCollectionDate = collectionDate;
		labResultDate = resultDate;
		testName = test;
		this.unit = unit;
		this.value = value;
	}
	
	public void printLabData() {
		System.out.printf("Lab ID: %9s | Encounter ID: %9s | Lab Order Date: %11s | Collection Date: %11s | Result Date: %11s | Test Name: %-33s | Results: %-7.2f%s\n", new String(getLabID()), new String(getLabEncID()), getLabOrderDate().toString(), getLabCollectionDate().toString(), getLabResultDate().toString(), getTestName(), getValue(), getUnit());
	}
	
	@Override
	public String toString() {
		if (this != null) {
			String labData = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s %s", "Lab ID: " + getLabID(), "Enc. ID: " + getLabEncID(), "Order Date: " + getLabOrderDate(), "Collection Date: " + getLabCollectionDate(), "Result Date: " + getLabResultDate(), "Test Name: " + getTestName(), "Test Result: " + getValue(), getUnit());
			return labData;
		} 
		return "Lab not found.";
	}
	
}
