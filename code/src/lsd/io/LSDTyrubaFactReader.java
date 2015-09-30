/* 
*    Logical Structural Diff (LSDiff)  
*    Copyright (C) <2015>  <Dr. Miryung Kim miryung@cs.ucla.edu>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package lsd.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lsd.rule.*;
import lsdSimplified.LSDiffSimplified;

public class LSDTyrubaFactReader {

	private ArrayList<LSDFact> facts = null;

	public LSDTyrubaFactReader(File inputFile) {
		ArrayList<LSDFact> fs = new ArrayList<LSDFact>();
		try {
			if (inputFile.exists()) {
				BufferedReader in = new BufferedReader(
						new FileReader(inputFile));
				String line = null;
				while ((line = in.readLine()) != null) {
					if (line.trim().equals("") || line.trim().charAt(0) == '#'
							|| line.trim().startsWith("//"))
						continue;
					LSDFact fact = parseTyrubaFact(line);
					fs.add(fact);
				}
				in.close();
			}
			this.facts = fs;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<LSDFact> convertToClassLevel(
			ArrayList<LSDFact> readDeltaFacts) {

		LSDFact tempFact;
		ArrayList<LSDFact> facts = new ArrayList<LSDFact>();
		for (LSDFact fact : readDeltaFacts) {
			if (fact.getPredicate().isMethodLevel()) {
				tempFact = fact.convertToClassLevel();
				if (tempFact == null)
					continue;
				// if (!facts.contains(tempFact))
				facts.add(tempFact);
			} else
				facts.add(fact);
		}
		return facts;
	}

	public ArrayList<LSDFact> getFacts() {
		return facts;
	}

	public static LSDFact parseTyrubaFact(String line) {
		String factString = line.trim();
		// predicate '(' args ')''.'
		String predicateName = factString.substring(0, factString.indexOf('('))
				.trim();
		LSDPredicate predicate = LSDPredicate.getPredicate(predicateName);
		factString = factString.substring(factString.indexOf('(') + 1).trim();
		int endOfArgs = factString.lastIndexOf(')');
		String arguments = factString.substring(0, endOfArgs).trim();
		factString = factString.substring(endOfArgs + 1).trim();
		if (!factString.equals(".")) {
			System.err
					.println("Facts must be in the form 'predicate(const, const, ...).'");
			System.err.println("Line: " + line);
			System.exit(-3);
		}

		if (predicate == null) {
			System.err.println("Predicate " + predicateName
					+ " is not defined.");
			System.err.println("Line: " + line);
			System.exit(-1);
		}
		return LSDFact.createLSDFact(predicate, arguments, true);
	}
}
