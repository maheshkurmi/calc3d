package com.calc3d.examples;

import java.io.File;

import com.calc3d.utils.FileUtils;

/**
 * Class with main function to create examples list, Make sure absolute path is correct
 * @author mahesh
 *
 */
public class createResourceProperty {

	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			final File dir = new File(
					"K:\\Java Projects 2018\\Calc3D\\src\\com\\calc3d\\examples\\tests");
			String str=createExamplesProperties(dir, "");
			str="examples"+str;
			str=str.replace('\\', '/');
			//str=str.replace('/', File.separatorChar);
			str=str.replace("K:/Java Projects 2018/Calc3D/src/com/calc3d/examples/tests", "/com/calc3d/examples/tests");

			System.out.println(str);

			FileUtils.writeFile("K:\\Java Projects 2018\\Calc3D\\src\\com\\calc3d\\examples\\examples.properties",str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String createExamplesProperties(File folder, String baseName) {
		StringBuilder str = new StringBuilder(baseName + "=");
		int j = 0;
		if (folder.listFiles().length == 0)
			return "";
		for (final File fileEntry : folder.listFiles()) {

			if (fileEntry.isDirectory()) {
				str.append(baseName + "_dir" + j + ",");
			} else {
				str.append(baseName + "_file" + j + ",");
			}
			j++;
		}

		str.append(folder.getName()+"\n");
		// if(str.lastIndexOf(",")==str.length()-1)str.deleteCharAt(str.length()-1);
		// if(!baseName.isEmpty())
		System.out.println(str.toString());

		j = 0;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				str.append(createExamplesProperties(fileEntry, baseName + "_dir" + j));
				// menu.add(mnu);
			} else {
				str.append(baseName + "_file" + j + "="	+ fileEntry.getPath().replace("C:\\Users\\mahesh\\Desktop\\Physics Simulations\\tests\\simulations", "\\org\\dyn4j\\sandbox\\tests\\simulations"));
				str.append("\n");
			}
			j++;
		}

		
		return str.toString();
	}

}
