package com.andrewhe.neo4j.Patients_Database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.io.fs.FileUtils;

import com.andrewhe.neo4j.Patients_Database.Encounter.Encounter_Type;
import com.andrewhe.neo4j.Patients_Database.Patient.AliveState;
import com.andrewhe.neo4j.Patients_Database.Patient.Gender;
import com.andrewhe.neo4j.Patients_Database.Patient.Race;

public class MedicalGraph {
//--Attributes Used in the Graph
	private enum RelTypes implements RelationshipType {
		IS,
		TREATED_AS_AN,
		LIVES_IN_CITY,
		LIVES_IN_STATE,
		LIVES_IN_COUNTRY,
		HAS_OR_HAD,
		RESULTED_IN,
		VISITED,
		MEDICATED_THROUGH,
		TREATED_WITH,
		TESTED,
		DIAGNOSED_WITH;
		
		public String toString() {
			switch (this) {
				case HAS_OR_HAD:
					return "has or had";
				case RESULTED_IN:
					return "resulted in";
				case VISITED:
					return "visited";
				case DIAGNOSED_WITH:
					return "diagnosed with";
				case IS:
					return "is a";
				case LIVES_IN_CITY:
					return "lives in city";
				case LIVES_IN_STATE:
					return "lives in state";
				case LIVES_IN_COUNTRY:
					return "lives in country";
				case TREATED_AS_AN:
					return "was treated as an";
				case MEDICATED_THROUGH:
					return "medicated through";
				case TREATED_WITH:
					return "treated with";
				case TESTED:
					return "tested";
				default:
					return "other";
			}
		}
	}
	private static final File Patient_DB = new File("database");
	//private long patientZero;
	private GraphDatabaseService graphDb;// = new GraphDatabaseFactory().newEmbeddedDatabase(Patient_DB);
	
	//Assortment of ArrayLists
	//Patient Demographics
	ArrayList<String> genders = new ArrayList<String>();
	ArrayList<String> races = new ArrayList<String>();
	ArrayList<String> ethnicities = new ArrayList<String>();
	ArrayList<String> cities = new ArrayList<String>();
	ArrayList<String> states = new ArrayList<String>();
	ArrayList<String> countries = new ArrayList<String>();
	
	//Encounter Information
	ArrayList<String> encounterTypes = new ArrayList<String>();
	ArrayList<String> departments = new ArrayList<String>();
	
	//Diagnosis Information
	ArrayList<String> diagnosisCodes = new ArrayList<String>();
	ArrayList<String> diagnosisDescriptions = new ArrayList<String>();
	
	//Medication Information
	ArrayList<String> medTypes = new ArrayList<String>();
	ArrayList<String> medNames = new ArrayList<String>();
	
	//Lab Information
	ArrayList<String> testNames = new ArrayList<String>();
	
	//Patient Problem Information
	ArrayList<String> problemCodes = new ArrayList<String>();
	ArrayList<String> problemDescriptions = new ArrayList<String>();
	
	//Function to add value to arraylist if it's not in the list already and creates main node
	public void addIfNotContained(String value, String label, ArrayList<String> list) {
		if (!list.contains(value)) {
			list.add(value);
			try (Transaction tx = graphDb.beginTx()) {
				Node node = graphDb.createNode();
				node.setProperty("value", value);
				node.addLabel(Label.label(label));
				tx.success();
			}
		}
	}
	
//--Creating Nodes, Setting Properties
	public <T> void createNodes(ArrayList<T> list) throws IOException {
		int i = 0;
		
		try (Transaction tx = graphDb.beginTx()) {
			for (T object : list) {
				Node node = graphDb.createNode();
				//FIX USING POLYMORPHISM TO AVOID INSTANCEOF
				if (object instanceof Patient) {
					setProperties(node, (Patient) object);
					node.addLabel(Label.label("Patient"));
					createLinksToMain(node, "gender", genders, "Gender", RelTypes.IS);
					createLinksToMain(node, "race", races, "Race", RelTypes.IS);
					createLinksToMain(node, "ethnicity", ethnicities, "Ethnicity", RelTypes.IS);
					createLinksToMain(node, "city", cities, "City", RelTypes.LIVES_IN_CITY);
					createLinksToMain(node, "state", states, "State", RelTypes.LIVES_IN_STATE);
					createLinksToMain(node, "country", countries, "Country", RelTypes.LIVES_IN_COUNTRY);
				} else if (object instanceof Encounter) {
					setProperties(node, (Encounter) object);
					node.addLabel(Label.label("Encounter"));
					createLinksToMain(node, "encounter_type", encounterTypes, "Encounter Type", RelTypes.TREATED_AS_AN);
					createLinksToMain(node, "department", departments, "Department", RelTypes.VISITED);
				} else if (object instanceof Diagnosis) {
					setProperties(node, (Diagnosis) object);
					node.addLabel(Label.label("Diagnosis"));
					createLinksToMain(node, "diagnosis_code", diagnosisCodes, "Diagnosis (Code)", RelTypes.DIAGNOSED_WITH);
					//createLinksToMain(node, "diagnosis_description"
				} else if (object instanceof Lab) {
					setProperties(node, (Lab) object);
					node.addLabel(Label.label("Lab"));
					//createLinksToMain(node, "test_name", testNames, "Lab Test", RelTypes.TESTED);
				} else if (object instanceof Medication) {
					setProperties(node, (Medication) object);
					node.addLabel(Label.label("Medication"));
					createLinksToMain(node, "med_type", medTypes, "Medication Type", RelTypes.MEDICATED_THROUGH);
					createLinksToMain(node, "med_name", medNames, "Medication Name", RelTypes.TREATED_WITH);
				} else if (object instanceof Patient_Problem) {
					setProperties(node, (Patient_Problem) object);
					node.addLabel(Label.label("Patient_Problem"));
					createLinksToMain(node, "id", problemCodes, "Problem (Code)", RelTypes.HAS_OR_HAD);
					createLinksToMain(node, "problem_description", problemDescriptions, "Problem (Description)", RelTypes.HAS_OR_HAD);
				} else if (object instanceof Procedure) {
					setProperties(node, (Procedure) object);
					node.addLabel(Label.label("Procedure"));
				}
				i++;
			}
			System.out.println("Nodes created: " + i);
			tx.success();
		}
	}
	
	public void createLinksToMain(Node node, String key, ArrayList<String> list, String label, RelTypes type) {
		try (Transaction tx = graphDb.beginTx()) {
			if (node.hasProperty(key)) {
				if (list.contains((String) node.getProperty(key))) {
					Node otherNode = graphDb.findNode(Label.label(label), "value", (String) node.getProperty(key));
					node.createRelationshipTo(otherNode, type);
				}
			}
			tx.success();
		}
	}
	
	//Method to create and set properties for node instead of using multiple set properties each time.
	public void setProperties(Node node, Patient patient) {
		try (Transaction tx = graphDb.beginTx()) {
			node.setProperty("name", patient.getName());
			//node.setProperty("weight", patient.getWeight());
			node.setProperty("patient_id", new String(patient.getPatientID()));
			node.setProperty("race", patient.getRace().toString());
			addIfNotContained(patient.getRace().toString(), "Race", races);
			node.setProperty("gender", patient.getGender().toString());
			addIfNotContained(patient.getGender().toString(), "Gender", genders);
			node.setProperty("ethnicity", patient.getEthnicity());
			addIfNotContained(patient.getEthnicity(), "Ethnicity", ethnicities);
			node.setProperty("birthday", patient.getBirthDay().toString()); //not really sure how I'll use this - maybe need to convert to int or something similar
			node.setProperty("city", patient.getCity());
			addIfNotContained(patient.getCity(), "City", cities);
			node.setProperty("state", patient.getState());
			addIfNotContained(patient.getState(), "State", states);
			node.setProperty("country", patient.getCountry());
			addIfNotContained(patient.getCountry(), "Country", countries);
			node.setProperty("alive", patient.getAlive().toString());
			//node.setProperty("age", patient.getAge());
			
			//Don't worry about diagnoses yet;
			//To get it to work, just turn the diagnoses ArrayList into a String separated by commas.
			tx.success();
		}
	}
	
	//Method to set properties for encounters for each node
	public void setProperties(Node node, Encounter encounter) {
		try (Transaction tx = graphDb.beginTx()) {
			node.setProperty("encounter_id", new String(encounter.getEncounterID()));
			node.setProperty("patient_id", new String(encounter.getPatientIDForEncounter()));
			node.setProperty("encounter_type", encounter.getEncounterType().toString());
			addIfNotContained(encounter.getEncounterType().toString(), "Encounter Type", encounterTypes);
			node.setProperty("department", encounter.getDepartment());
			addIfNotContained(encounter.getDepartment(), "Department", departments);
			node.setProperty("encounter_start_date", encounter.getStartDateForEncounter().toString());
			node.setProperty("encounter_end_date", encounter.getEndDateForEncounter().toString());
			
			tx.success();
		}
	}
	
	//Method to set properties for diagnosis for each node
	public void setProperties(Node node, Diagnosis diagnosis) {
		node.setProperty("id", diagnosis.getDiagnosisID());
		node.setProperty("encounter_id", diagnosis.getDiagnosisEncID());
		node.setProperty("diagnosis_code", diagnosis.getDiagnosisCode());
		addIfNotContained(diagnosis.getDiagnosisCode(), "Diagnosis (Code)", diagnosisCodes);
		node.setProperty("diagnosis_description", diagnosis.getDiagnosisDescription());
		//addIfNotContained(diagnosis.getDiagnosisDescription(), "Diagnosis (Description)", diagnosisDescriptions);
		node.setProperty("first_symptom_date", diagnosis.getDiagSympDate());
		node.setProperty("diagnosis_date", diagnosis.getDiagDate());
	}
	
	//Method to set properties for medication for each node
	public void setProperties(Node node, Medication medication) {
		node.setProperty("id", medication.getMedOrderID());
		node.setProperty("encounter_id", medication.getMedEncounterID());
		node.setProperty("med_type", medication.getMedType().toString());
		addIfNotContained(medication.getMedType().toString(), "Medication Type", medTypes);
		node.setProperty("med_name", medication.getMedName());
		addIfNotContained(medication.getMedName(), "Medication Name", medNames);
		node.setProperty("med_start_date", medication.getMedStartDate());
		node.setProperty("med_order_date", medication.getMedOrderDate());
		node.setProperty("med_end_date", medication.getMedEndDate());
		node.setProperty("dosage", medication.getDosage() + medication.getUnit().toString());
		node.setProperty("unit", medication.getUnit());
		node.setProperty("dose", medication.getDosage());
		node.setProperty("route", medication.getApplication());
	}
	
	//Method to set properties for Patient_Problem in each node
	public void setProperties(Node node, Patient_Problem problem) {
		node.setProperty("patient_id", problem.getPrbPatientID());
		node.setProperty("id", problem.getProblemCode());
		addIfNotContained(problem.getProblemCode(), "Problem (Code)", problemCodes);
		node.setProperty("problem_description", problem.getProblemDescription());
		addIfNotContained(problem.getProblemDescription(), "Problem (Description)", problemDescriptions);
		node.setProperty("first_symptom_date", problem.getSymptomOnsetDate());
		node.setProperty("resolution_date", problem.getSymptomResolutionDate());
	}
	
	//Method to set properties for labs in each node
	public void setProperties(Node node, Lab lab) {
		node.setProperty("id", lab.getLabID());
		node.setProperty("encounter_id", lab.getLabEncID());
		node.setProperty("lab_order_date", lab.getLabOrderDate());
		node.setProperty("lab_collection_date", lab.getLabCollectionDate());
		node.setProperty("lab_result_date", lab.getLabResultDate());
		node.setProperty("test_name", lab.getTestName());
		//addIfNotContained(lab.getTestName(), "Lab Test", testNames);
		node.setProperty("lab_value", lab.getValue());
		node.setProperty("lab_unit", lab.getUnit());
		node.setProperty("lab_result", lab.getValue() + lab.getUnit());
	}
	
	//Method to set properties for procedures in each node 
	public void setProperties(Node node, Procedure procedure) {
		node.setProperty("id", procedure.getProcedureID());
		node.setProperty("encounter_id", procedure.getProcEncID());
		node.setProperty("procedure_code", procedure.getProcedureCode());
		node.setProperty("procedure_name", procedure.getProcedureName());
		node.setProperty("procedure_date", procedure.getProcedureDate());
		node.setProperty("provider", procedure.getProviderName());	
	}
	
    //Master Create Direct Relationships Method - source is origin of relationship, destination is end of relationship
    private void createRelationship(String nodeLabelTo, String nodeLabelFrom, String sharedPropertyKey, RelTypes relType) {
    	try (Transaction tx = graphDb.beginTx()) {
    		ResourceIterator<Node> sourceNodes = graphDb.findNodes(Label.label(nodeLabelFrom));
    		while (sourceNodes.hasNext()) {
    			Node sourceNode = sourceNodes.next();
    			String sharedProperty = (String) sourceNode.getProperty(sharedPropertyKey);
    			ResourceIterator<Node> destinationNodes = graphDb.findNodes(Label.label(nodeLabelTo), sharedPropertyKey, sharedProperty);
    			while(destinationNodes.hasNext()) {
    				Node destinationNode = destinationNodes.next();
    				//if (destinationNode.hasProperty("resolution_date")) 
    				sourceNode.createRelationshipTo(destinationNode, relType);
    				if (destinationNode.hasProperty("id")) {
    					System.out.println(nodeLabelFrom + " " + sharedProperty + " " + relType.toString() + " " + nodeLabelTo + " " + destinationNode.getProperty("id"));
    				} else {
    					System.out.println(nodeLabelFrom + " " + sharedProperty + " " + relType.toString() + " " + nodeLabelTo);
    				}
    			}
    		}
    		tx.success();
    	}
    }
    
	
	public void excelSetUp() throws IOException {
		FileUtils.deleteRecursively(Patient_DB);
		registerShutdownHook();
		
		GraphDatabaseSettings.BoltConnector bolt = GraphDatabaseSettings.boltConnector( "0" );
		
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(Patient_DB)
				.setConfig(bolt.type, "BOLT")
				.setConfig(bolt.enabled, "true")
				.setConfig(bolt.address, "localhost:7687")
				.newGraphDatabase();
		
		
		ExcelReader excelReader = new ExcelReader();
		
		//Reads Patient Data
		System.out.println("\n------PATIENTS------\n");
		excelReader.readObjectData("Patient");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getPatients());
			tx.success();
		}
		//Prints patient data -- remove once testing is done
		for (Patient patient : excelReader.getPatients()) {
			patient.printAllData();
		}
		
		//Reads Encounter Data
		System.out.println("\n------ENCOUNTERS------\n");
		excelReader.readObjectData("Encounter");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getEncounters());
			tx.success();
		}
		for (Encounter encounter : excelReader.getEncounters()) {
			encounter.printEncounterData();
		}
		
		//Reads Diagnosis Data
		System.out.println("\n------DIAGNOSES------\n");
		excelReader.readObjectData("Diagnosis");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getDiagnoses());
			tx.success();
		}
		for (Diagnosis diagnosis : excelReader.getDiagnoses()) {
			diagnosis.printDiagnosisData();
		}
		
		//Reads Lab Data
		System.out.println("\n------Labs------\n");
		excelReader.readObjectData("Lab Result");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getLabs());
			tx.success();
		}
		for (Lab lab : excelReader.getLabs()) {
			lab.printLabData();
		}
		
		//Reads Medication Data
		System.out.println("\n------Medications------\n");
		excelReader.readObjectData("Medication");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getMedications());
			tx.success();
		}
		for (Medication medication : excelReader.getMedications()) {
			medication.printMedicationData();
		}
		
		//Reads Patient_Problem Data
		System.out.println("\n------Patient_Problems------\n");
		excelReader.readObjectData("Patient Problem");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getPatientProblems());
			tx.success();
		}
		for (Patient_Problem patientProblem : excelReader.getPatientProblems()) {
			patientProblem.printPatientProblemData();
		}
		
		//Reads Procedure Data
		System.out.println("\n------Procedure------\n");
		excelReader.readObjectData("Procedure");
		try (Transaction tx = graphDb.beginTx()) {
			createNodes(excelReader.getProcedures());
			tx.success();
		}
		for (Procedure procedure : excelReader.getProcedures()) {
			procedure.printProcedureData();
		}
			
		System.out.println("\n-----CREATING RELATIONSHIPS:-----");
		createRelationship("Patient_Problem", "Patient", "patient_id", RelTypes.HAS_OR_HAD);
		createRelationship("Encounter", "Patient", "patient_id", RelTypes.VISITED);
		createRelationship("Lab", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		createRelationship("Diagnosis", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		createRelationship("Procedure", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		createRelationship("Medication", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
	}
	
    public void shutdown()
    {
        graphDb.shutdown();
    }
	
    private void registerShutdownHook()
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> graphDb.shutdown()));
    }
    
//---QUERY METHODS:
    public ArrayList<Patient> findPatient(String nodeLabel, String nodePropertyKey, String nodePropertyValue) {
    	ArrayList<Patient> patients = new ArrayList<Patient>();
    	try (Transaction tx = graphDb.beginTx()) {
    		ResourceIterator<Node> foundNodes = graphDb.findNodes(Label.label(nodeLabel), nodePropertyKey, nodePropertyValue);
    		while (foundNodes.hasNext()) {
    			Node foundNode = foundNodes.next();
				if (!foundNode.equals(null)) {
			    	switch (nodeLabel) {
				    	case "Patient":
				    		break;
				    	case "Encounter":
				    		String patientID = (String) foundNode.getProperty("patient_id");
				    		foundNode = graphDb.findNode(Label.label("Patient"), "patient_id", patientID);
				    		break;
				    	case "Lab":
				    	case "Procedure":
				    	case "Medication":
				    	case "Diagnosis":
				    		String encounterID = (String) foundNode.getProperty("encounter_id");
				    		foundNode = graphDb.findNode(Label.label("Encounter"), "encounter_id", encounterID);
				    		patientID = (String) foundNode.getProperty("patient_id");
				    		foundNode = graphDb.findNode(Label.label("Patient"), "patient_id", patientID);
				    		break;
				    	case "Patient_Problem":
				    		patientID = (String) foundNode.getProperty("patient_id");
				    		foundNode = graphDb.findNode(Label.label("Patient"), "patient_id", patientID);
				    		break;
			    	}
			    	Gender gender = Gender.F;
					if (((String) foundNode.getProperty("gender")).toUpperCase().equals("MALE")) {
						gender = Gender.M;
					}
					
					//Turns String into Race object needed for constructor
					Race race = Race.WHITE;
					switch (((String) foundNode.getProperty("race")).toLowerCase()) {
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
					if (((String) foundNode.getProperty("alive")).toUpperCase().equals("N")) {
						alive = AliveState.N;
					}
					
			    	Patient foundPatient = new Patient((String) foundNode.getProperty("patient_id"), (String) foundNode.getProperty("name"), (String) foundNode.getProperty("birthday"), gender, race, (String) foundNode.getProperty("ethnicity"), (String) foundNode.getProperty("city"), (String) foundNode.getProperty("state"), (String) foundNode.getProperty("country"), alive);
					foundPatient.printAllData();  
					//Contains doesn't work 
					if (!patients.contains(foundPatient)) {
						patients.add(foundPatient);
					}
				}
    		}
    		tx.success();
    		return patients;
    	}
    }
    
    /*
    public void nestedSwitchFor(String node) {
    	try (Transaction tx = graphDb.beginTx()) {
	    	switch (node) {
	    		case "Patient":
	    			graphDb.findNodes(label)
	    	}
    	}
    }
    */
    
    
    //Finds an encounter based on problem code
    public ArrayList<Encounter> findEncounterFromProblem(String problemCode) {
    	ArrayList<Encounter> encounters = new ArrayList<Encounter>();
    	
    	try (Transaction tx = graphDb.beginTx()) {
    		ResourceIterator<Node> nodes = graphDb.findNodes(Label.label("Patient_Problem"), "id", problemCode);
    		while (nodes.hasNext()) {
    			Node node = nodes.next();
	    		if (!node.equals(null)) {
	    			String patientID = (String) node.getProperty("patient_id");
	    			ResourceIterator<Node> foundNodes = graphDb.findNodes(Label.label("Encounter"), "patient_id", patientID);
	    			while (foundNodes.hasNext()) {
	    				Node foundNode = foundNodes.next();
	    				
						Encounter_Type type = Encounter_Type.EMERGENCY;
						String encounterType = (String) foundNode.getProperty("encounter_type");
						switch (encounterType.toLowerCase()) {
	    					case "inpatient":
	    						type = Encounter_Type.INPATIENT;
	    						break;
	    					case "outpatient":
	    						type = Encounter_Type.OUTPATIENT;
	    						break;
	    					case "emergency": 
	    						break;
	    					default:
	    						type = Encounter_Type.OTHER;
	    						break;
	    				}
						Encounter encounter = new Encounter((String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("patient_id"), (String) foundNode.getProperty("encounter_start_date"), (String) foundNode.getProperty("encounter_end_date"), type, (String) foundNode.getProperty("department"));
						encounters.add(encounter);
	    			}
	    		}
    		}
    		tx.success();
			return encounters;
    	}
    }
    
   //Temporary method used to find elements linked to encounter based on patient ID
   public ArrayList findElementByPatientID(String element, String patientID) {
    	ArrayList elements = new ArrayList();
    	
    	try (Transaction tx = graphDb.beginTx()) {
    		ResourceIterator<Node> patientEncounters = graphDb.findNodes(Label.label("Encounter"), "patient_id", patientID);
    		while (patientEncounters.hasNext()) {
    			Node foundNode = patientEncounters.next();
    			String encounterID = (String) foundNode.getProperty("encounter_id");
    			ResourceIterator<Node> foundNodes = graphDb.findNodes(Label.label(element), "encounter_id", encounterID);
    			while (foundNodes.hasNext()) {
    				foundNode = foundNodes.next();
	    			switch (element) {
	    			case "Lab":
						Lab lab = new Lab((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("lab_order_date"), (String) foundNode.getProperty("lab_collection_date"), (String) foundNode.getProperty("lab_result_date"), (String) foundNode.getProperty("test_name"), (Double) foundNode.getProperty("lab_value"), (String) foundNode.getProperty("lab_unit")); 
						elements.add(lab);
						break;
					case "Diagnosis":
						Diagnosis diagnosis = new Diagnosis((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("diagnosis_code"), (String) foundNode.getProperty("diagnosis_description"), (String) foundNode.getProperty("first_symptom_date"), (String) foundNode.getProperty("diagnosis_date"));
						elements.add(diagnosis);
						break;
	    			case "Medication":
	    				Medication medication = new Medication((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("med_type"), (String) foundNode.getProperty("med_name"), (String) foundNode.getProperty("med_order_date"), (String) foundNode.getProperty("med_start_date"), (String) foundNode.getProperty("med_end_date"), (Double) foundNode.getProperty("dose"), (String) foundNode.getProperty("unit"), (String) foundNode.getProperty("route"));
	    				elements.add(medication);
	    				break;
	    			case "Procedure":
	    				Procedure procedure = new Procedure((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("procedure_code"), (String) foundNode.getProperty("procedure_name"), (String) foundNode.getProperty("procedure_date"), (String) foundNode.getProperty("provider"));
	    				elements.add(procedure);
	    				break;
	    			}
    			}
    		}
    		tx.success();
    		return elements;
    	}
    }
    
    //Finds Labs, Medications, Encounters, Procedures, etc.
    public ArrayList findElement(String element, String nodeLabel, String nodePropertyKey, String nodePropertyValue) {
    	ArrayList elements = new ArrayList();
   
    	if (element.equals("Patient_Problem")) {
    		try (Transaction tx = graphDb.beginTx()) {
    			ResourceIterator<Node> problemNodes = graphDb.findNodes(Label.label(element), nodePropertyKey, nodePropertyValue);
    			while (problemNodes.hasNext()) {
    				Node foundNode = problemNodes.next();
					Patient_Problem problem = new Patient_Problem((String) foundNode.getProperty("patient_id"), (String) foundNode.getProperty("first_symptom_date"), (String) foundNode.getProperty("id"), (String) foundNode.getProperty("problem_description"), (String) foundNode.getProperty("resolution_date"));
					elements.add(problem);
    			}
    			tx.success();
    			return elements;
    		}
    	}
    	
    	if (nodeLabel.equals("Patient") && (element.equals("Lab") || element.equals("Diagnosis") || element.equals("Medication") || element.equals("Procedure"))) {
    		System.out.println("findElement1");
    		elements = findElementByPatientID(element, nodePropertyValue);
    		return elements;
    	}
    	
    	try (Transaction tx = graphDb.beginTx()) {
    		ResourceIterator<Node> foundNodez = graphDb.findNodes(Label.label(nodeLabel), nodePropertyKey, nodePropertyValue);
    		while (foundNodez.hasNext()) {
    			Node foundNode = foundNodez.next();
    			//foundNode.relationship
	    		if (foundNode != null) {
	    			switch(element) {
	    				case "Encounter":
	    					nodePropertyKey = "encounter_id";
	    					nodePropertyValue = (String) foundNode.getProperty(nodePropertyKey);
	    					break;
	    				default: 
	    					break;
	    			}
	    			ResourceIterator<Node> foundNodes = graphDb.findNodes(Label.label(element), nodePropertyKey, nodePropertyValue);
	    			while (foundNodes.hasNext()) {
	    				foundNode = foundNodes.next();
	    				switch (element) {
	    				case "Encounter":
	    					Encounter_Type type = Encounter_Type.EMERGENCY;
	    					String encounterType = (String) foundNode.getProperty("encounter_type");
	    					switch (encounterType.toLowerCase()) {
		    					case "inpatient":
		    						type = Encounter_Type.INPATIENT;
		    						break;
		    					case "outpatient":
		    						type = Encounter_Type.OUTPATIENT;
		    						break;
		    					case "emergency": 
		    						break;
		    					default:
		    						type = Encounter_Type.OTHER;
		    						break;
		    				}
	    					Encounter encounter = new Encounter((String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("patient_id"), (String) foundNode.getProperty("encounter_start_date"), (String) foundNode.getProperty("encounter_end_date"), type, (String) foundNode.getProperty("department"));
	    					elements.add(encounter);
	    					break;
	    				case "Lab":
	    					Lab lab = new Lab((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("lab_order_date"), (String) foundNode.getProperty("lab_collection_date"), (String) foundNode.getProperty("lab_result_date"), (String) foundNode.getProperty("test_name"), (Double) foundNode.getProperty("lab_value"), (String) foundNode.getProperty("lab_unit")); 
	    					elements.add(lab);
	    					break;
	    				case "Diagnosis":
	    					Diagnosis diagnosis = new Diagnosis((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("diagnosis_code"), (String) foundNode.getProperty("diagnosis_description"), (String) foundNode.getProperty("first_symptom_date"), (String) foundNode.getProperty("diagnosis_date"));
	    					elements.add(diagnosis);
	    					break;
	    				case "Medication":
		    				Medication medication = new Medication((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("med_type"), (String) foundNode.getProperty("med_name"), (String) foundNode.getProperty("med_order_date"), (String) foundNode.getProperty("med_start_date"), (String) foundNode.getProperty("med_end_date"), (Double) foundNode.getProperty("dose"), (String) foundNode.getProperty("unit"), (String) foundNode.getProperty("route"));
		    				elements.add(medication);
		    				break;
	    				case "Procedure":
		    				Procedure procedure = new Procedure((String) foundNode.getProperty("id"), (String) foundNode.getProperty("encounter_id"), (String) foundNode.getProperty("procedure_code"), (String) foundNode.getProperty("procedure_name"), (String) foundNode.getProperty("procedure_date"), (String) foundNode.getProperty("provider"));
		    				elements.add(procedure);
		    				break;
	    				}
	    			} 
	    			//Temporary fix -- when nodelabel and element are same, it loops twice (squaring the result)
	    			if (nodeLabel.equals(element)) {
	    				return elements;
	    			}
	    		}
    		}
    		tx.success();
    		return elements;
    	}
    }
    	
   
   

    //Finds how many nodes were created with a certain label
    public void findNumberOfNodesWithLabel(String label) {
    	try (Transaction tx = graphDb.beginTx()) {
    		int i = 0;
    		ResourceIterator<Node> iterator = graphDb.findNodes(Label.label(label));
    		while (iterator.hasNext()) {
    			iterator.next();
    			i++;
    		}
    		System.out.println("Nodes with Label (" + label + "): " + i);
    		tx.success();
    	}
    }
    
    //Finds labs that pertain to a specific patient:
    public ArrayList<Node> findPatientLabs(String PatientID) {
    	try (Transaction tx = graphDb.beginTx()) {
    		ArrayList<Node> patientLabs = new ArrayList<Node>();
    		ResourceIterator<Node> patientEncounters = graphDb.findNodes(Label.label("Encounter"), "patient_id", PatientID);
    		while (patientEncounters.hasNext()) {
    			Node patientEncounter = patientEncounters.next();
    			String encounterID = (String) patientEncounter.getProperty("encounter_id");
    			ResourceIterator<Node> encounterLabs = graphDb.findNodes(Label.label("Lab"), "encounter_id", encounterID);
    			while (encounterLabs.hasNext()) {
    				Node encounterLab = encounterLabs.next();
    				patientLabs.add(encounterLab);
    				System.out.println("Patient " + PatientID + " had lab " + encounterLab.getProperty("id"));
    				System.out.println("-----------------------");
    				
    				java.util.Map<String, Object> labContents = encounterLab.getAllProperties();
    				for (Entry<String, Object> entry : labContents.entrySet()) {
    					System.out.println(entry.getKey() + " : " + entry.getValue());
    				}
    				System.out.println("-----------------------\n");
    			}
    		}
    		tx.success();
    		return patientLabs;
    	}
    }
    
    //Custom Cypher Query
    public String executeCypherQuery(String query) {
    	String rows = "";
    	if (!query.replaceAll("\\s+","").equals("")) {
			try (Transaction tx = graphDb.beginTx(); 
				Result result = graphDb.execute(query))
			{
				while (result.hasNext()) {
					Map<String, Object> row = result.next();
					for (Entry<String, Object> column : row.entrySet()) {
						rows += column.getKey() + ": " + column.getValue() + "; ";
					}
					rows+="\n";
				}
				return rows;
			}
    	}
    	return "No input given.";
	}
   
    /*
	public static void main(String[] args) throws IOException {
		MedicalGraph mg = new MedicalGraph();
		//mg.clearDatabase();
		
		//creating nodes:
		System.out.println("CREATING NODES:");
		mg.excelSetUp();
		
		//creating relationships:
		System.out.println("\n-----CREATING RELATIONSHIPS:-----");
		mg.createRelationship("Patient_Problem", "Patient", "patient_id", RelTypes.HAS_OR_HAD);
		mg.createRelationship("Encounter", "Patient", "patient_id", RelTypes.HAS_OR_HAD);
		mg.createRelationship("Lab", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		mg.createRelationship("Diagnosis", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		mg.createRelationship("Procedure", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		mg.createRelationship("Medication", "Encounter", "encounter_id", RelTypes.RESULTED_IN);
		//Queries:
		System.out.println("\n----------QUERIES-----------");
		System.out.println("\n*****Query One: \n");
		mg.findPatientByID("PT000124");
		System.out.println("\n*****Query Two: \n");
		mg.findNumberOfNodesWithLabel("Diagnosis");
		System.out.println("\n*****Query Three: \n");
		mg.findPatientLabs("PT000125");
		mg.shutdown();
	}
	*/
}
