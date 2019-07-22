package com.andrewhe.neo4j.Patients_Database;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.andrewhe.neo4j.Patients_Database.Encounter.Encounter_Type;
import com.andrewhe.neo4j.Patients_Database.Patient.AliveState;
import com.andrewhe.neo4j.Patients_Database.Patient.Gender;
import com.andrewhe.neo4j.Patients_Database.Patient.Race;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;



public class ExcelReader {
	//attributes:
	private static final String FILE_PATH = "/Users/andrew/Downloads/Patient_Sample.xlsx";
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	private ArrayList<Encounter> encounters = new ArrayList<Encounter>();
	private ArrayList<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
	private ArrayList<Lab> labs = new ArrayList<Lab>();
	private ArrayList<Medication> medications = new ArrayList<Medication>();
	private ArrayList<Patient_Problem> patientProblems = new ArrayList<Patient_Problem>();
	private ArrayList<Procedure> procedures = new ArrayList<Procedure>();
	private Workbook workbook;
	
	//Constructor:
	public ExcelReader() throws EncryptedDocumentException, IOException {
		workbook = WorkbookFactory.create(new File(FILE_PATH));
	}
	
	//Getters
	public ArrayList<Patient> getPatients() { return patients; }
	public ArrayList<Encounter> getEncounters() { return encounters; }
	public ArrayList<Diagnosis> getDiagnoses() { return diagnoses; }
	public ArrayList<Lab> getLabs() { return labs; }
	public ArrayList<Medication> getMedications() { return medications; }
	public ArrayList<Patient_Problem> getPatientProblems() { return patientProblems; }
	public ArrayList<Procedure> getProcedures() { return procedures; }
	
	//Reads type of object data specified in parameter
	public void readObjectData(String object) {		
		Sheet sheet = workbook.getSheet(object);
		
		System.out.println("Sheet name: " + sheet.getSheetName());
		System.out.println("Sheet rows: " + sheet.getLastRowNum());
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				boolean valid = true;
				ArrayList<String> objectAttributes = new ArrayList<String>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						DataFormatter dataFormatter = new DataFormatter();
						String value = dataFormatter.formatCellValue(cell);
						if (!value.equals("")) {
							objectAttributes.add(value);
						} else {
							valid = false;
						}
					}
				}
				if (objectAttributes.size() > 1 && valid) {
					switch (object.toLowerCase()) {
					//If we want to read in 
						case "patient":
							//Turns String into Gender object needed for constructor
							Gender gender = Gender.F;
							if (objectAttributes.get(3).toUpperCase().equals("M")) {
								gender = Gender.M;
							}
							
							//Turns String into Race object needed for constructor
							Race race = Race.WHITE;
							switch (objectAttributes.get(4).toLowerCase()) {
							case "black or african american": 
								race = Race.AFRICAN_AMERICAN;
								break;
							case "american indian or alaska native":
								race = Race.AMERICAN_INDIAN;
								break;
							case "asian":
								race = Race.ASIAN;
								break;
							case "hispanic or latino":
								race = Race.HISPANIC_OR_LATINO;
								break;
							case "native hawaiian or other pacific islander":
								race = Race.HAWAIIAN_OR_PACIFIC_ISLANDER;
								break;
							case "white":
								break;
							default: 
								race = Race.OTHER;
								break;
							}
							
							//Turns String into AliveStatus object needed for constructor
							AliveState alive = AliveState.Y;
							if (objectAttributes.get(9).toUpperCase().equals("N")) {
								alive = AliveState.N;
							}
							
							Patient patient = new Patient(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), gender, race, objectAttributes.get(5), objectAttributes.get(6), objectAttributes.get(7), objectAttributes.get(8), alive);
							patients.add(patient);
							break;
						case "encounter":
						Encounter_Type type = Encounter_Type.EMERGENCY;
						//Converts String into Encounter Type for constructor
							switch(objectAttributes.get(4).toLowerCase()) {
								case "emergency":
									break;
								case "inpatient":
									type = Encounter_Type.INPATIENT;
									break;
								case "outpatient":
									type = Encounter_Type.OUTPATIENT;
									break;
							}
							Encounter encounter = new Encounter(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), type, objectAttributes.get(5));
							encounters.add(encounter);
							break;
						case "diagnosis":
							//deals with unresolved diagnoses
							if (objectAttributes.get(4).toLowerCase().equals("null")) {
								objectAttributes.set(4, "");
							}
							
							Diagnosis diagnosis = new Diagnosis(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), objectAttributes.get(4), objectAttributes.get(5));
							diagnoses.add(diagnosis);
							break;
						case "lab result":
							//Find some other way to deal with the > problem
							if (objectAttributes.get(6).contains(">") || objectAttributes.get(6).contains("<")) {
								StringBuilder sb = new StringBuilder();
								sb.append(objectAttributes.get(6));
								sb.deleteCharAt(0);
								objectAttributes.set(6, sb.toString());
							}
							if (objectAttributes.get(6).contains(",")) {
								StringBuilder sb = new StringBuilder();
								sb.append(objectAttributes.get(6));
								sb.replace(sb.indexOf(","), sb.indexOf(",") + 1, "");
								objectAttributes.set(6, sb.toString());
							}
							Lab lab = new Lab(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), objectAttributes.get(4), objectAttributes.get(5), Double.parseDouble(objectAttributes.get(6)), objectAttributes.get(7));
							labs.add(lab);
							break;
						case "medication":	
							if (objectAttributes.get(6).toLowerCase().equals("null")) {
								objectAttributes.set(6, "");
							}
							
							/*
							//Converts string into Med Order Type for Constructor
							Med_Order_Type medType = Med_Order_Type.adm;
							if (objectAttributes.get(2).toLowerCase().equals("presc")) {
								medType = Med_Order_Type.presc;
							}
							
							//Converts String object into Unit for constructor
							Unit unit = Unit.g;
							//If more units are added, get rid of lowerCase -> Mg vs mg
							switch (objectAttributes.get(8).toLowerCase()) {
								case "mg": 
									unit = Unit.mg;
									break;
								case "meq":
									unit = Unit.mEq;
									break;
								case "mL":
									unit = Unit.mL;
									break;
								case "g":
									break;
							}
							
							//Converts String object into Med Route for constructor
							Med_Route route = Med_Route.IV_Piggyback;
							switch (objectAttributes.get(9).toLowerCase() ) {
								case "oral":
									route = Med_Route.Oral;
									break;
								case "iv push":
									route = Med_Route.IV_Push;
									break;
								case "nebulization":
									route = Med_Route.Nebulization;
									break;
								case "iv piggyback":
									break;
							}
							*/
							Medication medication = new Medication(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), objectAttributes.get(4), objectAttributes.get(5), objectAttributes.get(6), Double.parseDouble(objectAttributes.get(7)), objectAttributes.get(8), objectAttributes.get(9));
							medications.add(medication);
							break;
							
						case "patient problem":
							//Deals with unresolved problems
							if (objectAttributes.get(4).toLowerCase().equals("null")) {
								objectAttributes.set(4, "");
							}
							
							Patient_Problem patientProblem = new Patient_Problem(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), objectAttributes.get(4));
							patientProblems.add(patientProblem);
							break;
						case "procedure":
							Procedure procedure = new Procedure(objectAttributes.get(0), objectAttributes.get(1), objectAttributes.get(2), objectAttributes.get(3), objectAttributes.get(4), objectAttributes.get(5));
							procedures.add(procedure);
							break;
					}
				}
			}
		}
	}
}
