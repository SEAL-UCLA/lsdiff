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
package lsd.rule;

import java.util.ArrayList;
import java.util.List;

public class LSDFact extends LSDLiteral implements Comparable<LSDFact> {
	private LSDFact(LSDPredicate pred, ArrayList<LSDBinding> bindings,
			boolean nonNegated) throws LSDInvalidTypeException {
		super(pred, bindings, nonNegated);
	}

	public boolean contains(String filter) {
		return (super.toString().contains(filter));
	}

	public boolean equals(Object o) {
		if (o instanceof LSDFact) {
			LSDFact of = (LSDFact) o;
			if (of.toString().equals(this.toString())) {
				return true;
			}
		}
		return false;
	}

	public static LSDFact createLSDFact(LSDPredicate pred, ArrayList<LSDBinding> bindings) { 
		LSDFact theFact = null;
		try {
			theFact = new LSDFact(pred,bindings, true); 
			
		} catch (LSDInvalidTypeException e) {
			System.err.println("LSDFact cannot have an invalid type.");
			System.exit(1);
		}
		return theFact;
	}

	public int compareTo(LSDFact arg0) {
		return (toString().compareTo(arg0.toString()));
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public static LSDFact createLSDFact(LSDPredicate pred, String constants,
			boolean nonNegated) {
		ArrayList<LSDBinding> bindings = new ArrayList<LSDBinding>();
		String[] split = constants.split(",");
		String compose = "";
		for (int i = 0; i < split.length; i++) {
			if (!compose.equals(""))
				compose += ",";
			compose += split[i];
			if (compose.indexOf('"') != -1
					&& compose.indexOf('"', compose.indexOf('"') + 1) == -1) {
				// If there's only one '"' in the constant we're composing
				// now...
				continue;
			}
			String trimed = compose.trim();
			LSDBinding binding = new LSDBinding(trimed);
			bindings.add(binding);
			compose = "";
		}
		if (!compose.equals("")) {
			System.err.print('"' + constants + '"'
					+ "contains unballanced \"s.");
			System.exit(-5);
		}
		LSDFact theFact = null;
		try {
			theFact = new LSDFact(pred, bindings, nonNegated);
		} catch (LSDInvalidTypeException e) {
			System.err.println("LSDFact cannot have an invalid type.");
			System.exit(1);
		}
		if (theFact.bindings == null)
			return null;
		return theFact;
	}

	public LSDFact nonNegatedCopy() {
		try {
			return new LSDFact(predicate, bindings, true);
		} catch (LSDInvalidTypeException e) {
			System.err.println("LSDFact cannot have an invalid type.");
			System.exit(1);
			return null;
		}
	}

	public LSDFact addedCopy() {
		LSDPredicate newPredicate = predicate.getPrefixPredicate("added");
		if (newPredicate == null) {
			System.err
					.println("All predicates should have an added/deleted version.");
			System.exit(1);
			return null;
		}
		try {
			return new LSDFact(newPredicate, bindings, true);
		} catch (LSDInvalidTypeException e) {
			System.err.println("LSDFact cannot have an invalid type.");
			System.exit(1);
			return null;
		}
	}

	public LSDFact deletedCopy() {
		LSDPredicate newPredicate = predicate.getPrefixPredicate("deleted");
		if (newPredicate == null) {
			System.err
					.println("All predicates should have an added/deleted version.");
			System.exit(1);
			return null;
		}
		try {
			return new LSDFact(newPredicate, bindings, true);
		} catch (LSDInvalidTypeException e) {
			System.err.println("LSDFact cannot have an invalid type.");
			System.exit(1);
			return null;
		}
	}

	// FIXME:PREDICATE CONTENT DEPENT
	public String[] getPrimaryConstants() {
		String name = this.getPredicate().getSuffix();
		if (name.equals("type")) {
			String s[] = { this.bindings.get(0).toString() };
			return s;
		} else if (name.equals("field")) {
			String s[] = { this.bindings.get(0).toString() };
			return s;
		} else if (name.equals("method")) {
			String s[] = { this.bindings.get(0).toString() };
			return s;
		} else if (name.equals("typeintype")) {
			String s[] = { this.bindings.get(0).toString() };
			return s;
		} else if (name.equals("inheritedmethod")) {
			String n = this.bindings.get(0).toString();
			String s[] = { this.bindings.get(1).toString() + "#" + n,
					this.bindings.get(2).toString() };
			return s;
		} else if (name.equals("inheritedfield")) {
			String n = this.bindings.get(0).toString();
			String s[] = { this.bindings.get(1).toString() + "#" + n,
					this.bindings.get(2).toString() };
			return s;
		}
		String s[] = new String[this.bindings.size()];
		for (int i = 0; i < s.length; i++) {
			s[i] = this.bindings.get(i).toString();
		}
		return s;
	}

	public String getReferenceConstant() {
		return this.bindings.get(this.predicate.getReferenceArgument())
				.toString();
	}

	public LSDFact convertToClassLevel() {
		try {
			String fact = this.toString();
			String arg = fact.substring(fact.indexOf("(") + 1, fact
					.lastIndexOf(")"));
			if (predicate.isConclusionPredicate()
					|| predicate.isDependencyPredicate()) {
				LSDPredicate changedPred = predicate.toClassLevel();
				arg = predicate.getConvertedArgs(arg);
				return LSDFact.createLSDFact(changedPred, arg, true);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("Conversion Error");
			return this;
		}
	}

}
