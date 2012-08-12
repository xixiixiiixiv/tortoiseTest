import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

//�Q�l/���p
//http://www.javaroad.jp/java_character7.htm
//http://java.sun.com/j2se/1.5.0/ja/docs/ja/api/java/util/regex/Matcher.html

public class CpyFileRenamer {
	public static void main(String args[]) {
		String extdel = "\\..*$"; // �g���q�擾�p���K�\��
		Pattern ptnExt = Pattern.compile(extdel);
		
		File[] arry = new File(".").listFiles();
		for(File oFile : arry){
			Matcher m = ptnExt.matcher(oFile.getName());
			String extName = "";
			if (m.find()) extName = m.group(0); // �g���q�ޔ�
			String oldFileName = m.replaceFirst(""); // �g���q����
			String newFileName = oldFileName.replaceFirst(" - �R�s�[.*$|^�R�s�[ (|\\([0-9]+\\) )�` ",""); // �ҏW
			
			// �t�@�C�����ύX�ΏۊO�̏ꍇ�͎��̃t�@�C����
			if (oldFileName.equals(newFileName)) continue;

			System.out.println("   " + oFile.getName());
			
			// �t�@�C�����ҏW
			StringBuilder sbFileName = new StringBuilder();
			sbFileName.append(newFileName);
			sbFileName.append(new SimpleDateFormat("_yyyyMMddhhmmss").format(oFile.lastModified()));
			sbFileName.append(extName);
			newFileName = sbFileName.toString();
			
			// �t�@�C���ړ�
			boolean isSuccess = false;
			if (new File(newFileName).exists()) {
				System.out.println("   ���ɓ����̃t�@�C�������݂��܂��B");
			} else {
				try
				{
					oFile.renameTo(new File(newFileName));
					isSuccess = true;
				}catch(SecurityException e){
					System.out.println("   �t�@�C���̃��l�[���Ɏ��s���܂����B");
				}
			}
			System.out.println((isSuccess ? "��" : "�~") + ">" + sbFileName.toString());
		}
	}
}
