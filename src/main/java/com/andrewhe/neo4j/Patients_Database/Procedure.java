package com.andrewhe.neo4j.Patients_Database;

import java.util.ArrayList;
import java.util.HashMap;

public class Procedure {
	//Attributes
	private static ArrayList<String> procedure_IDs = new ArrayList<String>();
	private String procedureID;
	private String procEncounterID;
	private String procedureCode; //or int; but string is immutable which we like
	private String procedureName;
	private String procedureDate;
	private String providerName;
	/*
	private static HashMap<String, String> procedureCodeToName = new HashMap<String, String>() {{
		put("10022", "PR FINE NEEDLE ASP; W/ IMAGING GUIDANCE");
		put("31575", "PR LARYNGOSCOPY FLEXIBLE DIAGNOSTIC");
		put("32551", "PR TUBE THORACOSTOMY INCLUDES WATER SEAL");
		put("35226", "PR REBL VES DIRECT,LOW EXTREM");
		put("36556", "PR INSERT NON-TUNNEL CV CATH");
		put("37244", "PR VASCULAR EMBOLIZATION OR OCCLUSION HEMORRHAGE");
		put("58300", "PR INSERT INTRAUTERINE DEVICE");
		put("58301", "PR REMOVE INTRAUTERINE DEVICE");
		put("61650", "PR EVASC INTRACRANIAL PROLNG ADMN RX AGENT ART 1ST");
		put("61651", "PR EVASC INTRACRANIAL PROLNG ADMN RX AGENT ART ADDL");
		put("65285", "PR REPAIR CORNEA LAC,PERF,RESEC UVEAL");
		put("67042", "PR VITRECTOMY PARS PLANA REMOVE INT MEMB RETINA");
		put("71020", "CHG CHEST X-RAY 2 VW");
		put("71046", "CHG RADIOLOGIC EXAM CHEST 2 VIEWS");
		put("73030", "CHG X-RAY SHOULDER 2+ VW");
		put("73564", "CHG X-RAY KNEE 4+ VIEW");
		put("73600", "RADIOLOGY (DIAGNOSTIC IMAGING) OF LOWER EXTREMITIES");
		put("75710", "CHG ANGIO EXTREMITY UNILAT");
		put("76536", "CHG US, HEAD/NECK TISSUES,REAL TIME");
		put("76942", "CHG SONO GUIDE NEEDLE BIOPSY");
		put("90471", "PR IMMUNIZ ADMIN,1 SINGLE/COMB VAC/TOXOID");
		put("90472", "PR IMMUNIZ,ADMIN,EACH ADDL");
		put("90713", "PR POLIOMYELITIS IMMUNIZATN,INACTV,SUB-Q");
		put("92014", "PR EYE EXAM, EST PATIENT,COMPREHESV");
		put("92083", "PR VISUAL FIELD EXAM,EXTENDED");
		put("92134", "PR COMPUTERIZED OPHTHALMIC IMAGING RETINA");
		put("92950", "PR HEART/LUNG RESUSCITATION (CPR)");
		put("97110", "PR THERAPEUTIC EXERCISES");
		put("99149", "PR MODERATE SEDATJ DIFF PHYS/QHP >5 YRS INIT 30 MIN");
		put("99465", "PR DELIVERY/BIRTHING ROOM RESUSCITATION");
	}};
	*/
	
	//Getters and Setters
	public String getProcedureID() { return procedureID; }
	public String getProcEncID() { return procEncounterID; }
	public String getProcedureCode() { return procedureCode; }
	public String getProcedureName() { return procedureName; }
	public String getProcedureDate() { return procedureDate; }
	public String getProviderName() { return providerName; }
	
	//Method to retrieve name based on procedure code provided
	/*
	public String procedureCodeToName(String code) {
		return procedureCodeToName.get(code);
	}
	*/
	
	public Procedure(String procedure_ID, String encounterID, String code, String name, String procDate, String provName) {
		procedureID = procedure_ID;
		procedure_IDs.add(procedureID);
		procEncounterID = encounterID;
		procedureCode = code;
		procedureName = name;
		procedureDate = procDate;
		providerName = provName;
	}
	
	public void printProcedureData() {
		System.out.printf("Procedure ID: %s | Encounter ID: %s| Code: %s | Name: %-50s | Date: %s | Provider: %s\n", new String(getProcedureID()), new String(getProcEncID()), getProcedureCode(), getProcedureName(), getProcedureDate().toString(), getProviderName());
	}
	
	@Override
	public String toString() {
		String procData = String.format("%s\n%s\n%s\n%s\n%s\n%s", "Procedure ID: " + getProcedureID(), "Enc. ID: " + getProcEncID(), "Procedure Code: " + getProcedureCode(), "Procedure Name: " + getProcedureName(), "Procedure Date: " + getProcedureDate(), "Provider Name: " + getProviderName());
		return procData;
	}
}
