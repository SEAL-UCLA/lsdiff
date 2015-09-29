package metapackage;

import java.io.File;

public class MetaInfo {
	public static int k=1; 
	public static int minConcFact=3;
	public static int beamSize=100;
	public static double accuracy =0.75;
	public static int maxException=10;
	
//  JFreeChart
	
	public static String[] jfreechartVersions= new String[] { 
		//"0.9.9_0.9.10"
		"1.0.12_1.0.13"};
	
	
//	Carol
	public static String[] carolVersions = new String[]{
		"1.0.1_1.0.2","1.0.2_1.1.0","1.1.0_1.2.0",
		"1.2.0_1.3.0","1.3.0_1.3.1","1.3.1_1.3.2",
		"1.3.2_1.3.4","1.3.4_1.4.0","1.4.0_1.5.1","1.5.1_2.0.0"};

//	DNSJava
	public static String[] dnsjavaVersions = new String[]{
		"0.1_0.2","0.2_0.3","0.3_0.4","0.4_0.5",
		"0.5_0.6","0.6_0.7","0.7_0.8","0.8.1_0.8.2",
		"0.8.2_0.8.3","0.8.3_0.9","0.8_0.8.1","0.9.1_0.9.2",
		"0.9.2_0.9.3","0.9.3_0.9.4","0.9.4_0.9.5","0.9.5_1.0",
		"0.9_0.9.1","1.0.1_1.0.2","1.0.2_1.1","1.0_1.0.1",
		"1.1.1_1.1.2","1.1.2_1.1.3","1.1.3_1.1.4","1.1.4_1.1.5",
		"1.1.5_1.1.6","1.1.6_1.2.0","1.1_1.1.1","1.2.0_1.2.1",
		"1.2.1_1.2.2","1.2.2_1.2.3","1.2.3_1.2.4","1.2.4_1.3.0",
		"1.3.0_1.3.1","1.3.1_1.3.2","1.3.2_1.3.3","1.3.3_1.4.0",
		"1.4.0_1.4.1","1.4.1_1.4.2","1.4.2_1.4.3","1.4.3_1.5.0",
		"1.5.0_1.5.1","1.5.1_1.5.2","1.5.2_1.6.1","1.6.1_1.6.2",
		"1.6.2_1.6.3","1.6.3_1.6.4","1.6.4_1.6.5","1.6.5_1.6.6",
		"1.6.6_2.0.0","2.0.0_2.0.1","2.0.2_2.0.3"};//,"2.0.1_2.0.2"};
	
//	LSDiff
	public static String[] lsdiffVersions = new String[]{
		"13_20","20_21","21_26","26_27",
		"27_28","28_34","34_36","36_39",
		"3_4","4_13"};
	
	public static String[] version = jfreechartVersions;
	private static String project = "jfreechart"; 

	public static String folderName = project;

	public static String srcDir = "input";
	private static String resDir = "output";
	
	public static File included2kb = new File (srcDir, "2KB_lsdPred.rub");
	public static File includedDelta = new File (srcDir, "deltaKB_lsdPred.rub");
	public static String inFile2K=  new File (srcDir, version + "2KB.rub").getAbsolutePath();
	public static String deltaFile = new File(srcDir, version + "delta.rub").getAbsolutePath();
	public static String winnowings = new File(srcDir, "winnowingRules.rub").getAbsolutePath();
	public static String modifiedWinnowings = new File(srcDir, "convertedwinnowingRules.rub").getAbsolutePath();

	public static String resultsFile = new File(resDir, "Hierarchical_" + folderName + "_Temp.rub").getAbsolutePath();
	
	public static String get2KB(int i){
		File projectDir = new File (srcDir, folderName);
		if (!projectDir.exists()) projectDir.mkdir();
		inFile2K = new File (projectDir, version[i] + "2KB.rub").getAbsolutePath();
		return inFile2K;
	}
	
	public static String getDelta(int i){
		File projectDir = new File (srcDir, folderName);
		if (!projectDir.exists()) projectDir.mkdir();
		deltaFile = new File (projectDir, version[i] + "delta.rub").getAbsolutePath();
		return deltaFile;
	}
	
}
