package com.andrewhe.neo4j.Patients_Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JTable;

public class Patient {
	
	public enum Gender {
		M {
			public String toString() {
				return "Male";
			}
		},
		F {
			public String toString() {
				return "Female";
			}
		};
	}
	
	public enum AliveState {
		Y {
			public String toString() {
				return "Y";
			}
		},
		N {
			public String toString() {
				return "N";
			}
		};
	}
	
	public enum Race {
		AMERICAN_INDIAN,
		ASIAN,
		AFRICAN_AMERICAN,
		HISPANIC_OR_LATINO,
		HAWAIIAN_OR_PACIFIC_ISLANDER,
		WHITE,
		OTHER;
		
		@Override
		public String toString() {
			switch(this) {
				case AMERICAN_INDIAN: return "American Indian or Alaska Native"; 
				case ASIAN: return "Asian";
				case AFRICAN_AMERICAN: return "Black or African American";
				case HISPANIC_OR_LATINO: return "Hispanic or Latino"; 
				case HAWAIIAN_OR_PACIFIC_ISLANDER: return "Hawaiian or Pacific Islander"; 
				case WHITE: return "White"; 
				default: return "Other"; 
			}
		}
	}
	
	//Need to add error checking to inputs, such as ensuring the PATID is 10 digits
	
	//Don't really need this -- in the real world, the patient will be supplied
	//To the database. So just have one constructor with all parameters necessary to pass

	//At some point, change Patient ID to String
	
	//PatientIDs will eventually be maintained through a file, so instead of a static variable just read file.
	//Properties of a Patient: they have name, height, weight, etc.
	private static ArrayList<String> patientIDs = new ArrayList<String>(); //Used to see if patientID initialized is already in existence
	private String patientID;
	private String name;
	private double height; //height should be in centimeters
	private double weight; //weight should be in kilograms
	private int age; //age will be in years
	private Race race;
	private String city;
	private String state;
	private String country;
	private AliveState alive;
	private String birthDate;
	private Gender gender;
	private String ethnicity; //Convert to enum later
	
	//Master constructor -- ultimate goal is to read in all these parameters from a file and create patients based on the file data.
	public Patient(String Pat_ID, String Name, String BirthDate, Gender Gender, Race Race, String Ethnicity, String City, String State, String Country, AliveState Alive) {
		patientID = Pat_ID;
		if (!patientIDs.contains(patientID)) {
			patientIDs.add(patientID);

		}
		name = Name;
		birthDate = BirthDate;
		gender = Gender;
		race = Race;
		ethnicity = Ethnicity;
		city = City;
		state = State;
		country = Country;
		alive = Alive;
	}
	
	//GETTERS AND SETTERS:
	public String getName() { return name; }
	
	public int getAge() { return age; }
	
	public double getWeight() { return weight; }
	
	public double getHeight() { return height; }
		
	public String getPatientID() { return patientID; }
	
	public Race getRace() { return race; }
	
	public String getBirthDay() { return birthDate; }
	
	public Gender getGender() { return gender; }
	
	public String getEthnicity() { return ethnicity; }
	
	public String getCity() { return city; }
	
	public String getState() { return state; }
	
	public String getCountry() { return country; }
	
	public AliveState getAlive() { return alive; }

	public ArrayList<String> getPatientIDs() { return patientIDs; }
		
	//Prints all patients IDs.
	public static void printPatientIDs() {
		int i = 0;
		for (String patientID : patientIDs) {
			System.out.println("Patient " + i + ": " + patientID);
			i++;
		}
	}
	
	public void printAllData() {
		System.out.printf("Patient ID: %s | Name: %15s | Gender: %6s | BirthDate: %s | City: %15s | State: %10s | Race: %25s\n", getPatientID(), getName(), getGender().toString(), getBirthDay(), getCity(), getState(), getRace().toString());
	} 
	
	//Returns patient data in string form
	@Override
	public String toString() {
		if (this != null) {
			String patientData = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", "Pat. ID: " + getPatientID(), "Name: " + getName(), "Birthday: " + getBirthDay(), "Gender: " + getGender().toString(), "Race: " + getRace().toString(), "Ethnicity: " + getEthnicity(), "City: " + getCity(), "State: " + getState(), "Country: " + getCountry(), "Alive: " + getAlive().toString());
			return patientData;
		} 
		return "Patient not found.";
	}
	
	public JTable returnTable() {
		String[] columnNames = {
				"Patient ID", "Name", "Birthday", "Gender", "Race", "Ethnicity", "City", "State", "Country", "Alive"
		};
		
		String[][]data = {
				{getPatientID(), getName(), getBirthDay(), getGender().toString(), getRace().toString(), getEthnicity(), getCity(), getState(), getCountry(), getAlive().toString()}
		};
		
		JTable table = new JTable(data, columnNames);
		return table;
	}
}
