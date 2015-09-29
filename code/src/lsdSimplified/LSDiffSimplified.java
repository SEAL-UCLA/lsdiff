package lsdSimplified;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.List;

import lsd.facts.LSDRuleEnumerator;
import lsd.facts.LSdiffFilter;
import lsd.rule.LSDRule;
import metapackage.MetaInfo;

public class LSDiffSimplified {

	public static void main(String[] args) throws Exception {
		BufferedWriter output = null;
		System.out.println("On Demand Hierarchical Algorithm");
		try{
			File winnowingRulesFile = new File(MetaInfo.winnowings);
			File typeLevelWinnowingRulesFile = new File(MetaInfo.modifiedWinnowings);	
			File resultsFile = new File(MetaInfo.resultsFile);
			output =  new BufferedWriter(new FileWriter(resultsFile));
			output.write("Version \t ExeTime \t GeneratedR \t SelectedR \t RemainingF \t Facts \t Coverage \t Conciseness \t CInfo");
			output.newLine();
			for(int i = 0; i < MetaInfo.version.length; i++)
			{
				File twoKBFile = new File(MetaInfo.get2KB(i));
				File deltaKBFile = new File(MetaInfo.getDelta(i));
				System.out.println(deltaKBFile.getAbsolutePath());
				output.write(MetaInfo.version[i]+" \t ");
				LSDRuleEnumerator enumerator = new LSDRuleEnumerator(twoKBFile, deltaKBFile, winnowingRulesFile, resultsFile,
						MetaInfo.minConcFact,MetaInfo.accuracy, MetaInfo.k, MetaInfo.beamSize, MetaInfo.maxException, typeLevelWinnowingRulesFile, output);
				
				LSdiffFilter filter = new LSdiffFilter(false, true, false, false, false); 
//				enumerator.bruteForceLearning(1, filter);
//				MK commented out and tried out the API differences
				File ruleResult = new File ("temp_rule");
				PrintStream result = new PrintStream(ruleResult);
				List<LSDRule> rules = enumerator.levelIncrementLearning(result);
//				enumerator.levelIncrementLearning2();
				output.newLine();
			}
			output.close();
		}
		catch (Exception e) {
			 System.out.println(e.getMessage());
		}
	}
}

