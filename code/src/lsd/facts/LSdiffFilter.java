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
package lsd.facts;

public class LSdiffFilter {

	final boolean packageLevel; 
	final boolean typeLevel; 
	final boolean methodLevel; 
	final boolean fieldLevel; 
	final boolean bodyLevel;
	
	public LSdiffFilter( boolean packageL, boolean classL, boolean methodL, boolean fieldL, boolean bodyL) { 
		packageLevel = packageL; 
		typeLevel = classL;
		methodLevel = methodL;
		fieldLevel = fieldL; 
		bodyLevel = bodyL;
	}
	
	
	
}
