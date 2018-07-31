package xlstovcf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import vcardcomponents.VCard;

import exception.UserDefinedExceptionMessage;

public class WriteInNotepad {
	
	public static String destPath;
	
	public static String getDestPath() {
		return destPath;
	}

	public static void setDestPath(String destPath) {
		WriteInNotepad.destPath = destPath;
	}

	public static void saveAsVCF(VCard contact) throws Exception{
		try {
			FileOutputStream vcf=new FileOutputStream(destPath+contact.getFormattedName()+".vcf");
			//system.out.println(contact.getFormattedName()+".vcf");
			StringBuffer writeStr=new StringBuffer("BEGIN:VCARD\r\nVERSION:2.1\r\n");
			if(contact.getName()!=null){
				writeStr.append("N:;"+contact.getName()+";;;\r\n");
			}
			if(contact.getFormattedName()!=null){
				writeStr.append("FN:"+contact.getFormattedName()+"\r\n");
			}
			writeStr.append("CUSTOM:vnd.android.cursor.item/nickname;Tes;1;;;;;;;;;;;;;\r\n");
			if(contact.getTelephone()!=null){
				Map<String,String> tempTele=contact.getTelephone();
				for(String ptr:tempTele.keySet()){
					writeStr.append("TEL;"+tempTele.get(ptr)+":"+ptr+"\r\n");
				}
			}
			if(contact.geteMail()!=null){
				Map<String,String> tempeMail=contact.geteMail();
				for(String ptr:tempeMail.keySet()){
					writeStr.append("EMAIL;"+tempeMail.get(ptr)+":"+ptr+"\r\n");
				}
			}
			if(contact.getAddress()!=null){
				Map<String,String> tempAdd=contact.getAddress();
				for(String ptr:tempAdd.keySet()){
					writeStr.append("ADR;"+tempAdd.get(ptr)+":"+ptr+"\r\n");
				}
			}
			if(contact.getOrganization()!=null){
				writeStr.append("ORG:"+contact.getOrganization()+"\r\n");
			}
			if(contact.getTitle()!=null){
				writeStr.append("TITLE:"+contact.getTitle()+"\r\n");
			}
			if(contact.getUrl()!=null){
				List<String> tempUrl=contact.getUrl();
				for(String ptr:tempUrl){
					writeStr.append("URL:"+ptr+"\r\n");
				}
			}
			if(contact.getbDay()!=null){
				writeStr.append("BDAY:"+contact.getbDay()+"\r\n");
			}
			if(contact.getNote()!=null){
				writeStr.append("NOTE:"+contact.getNote()+"\r\n");
			}
			if(contact.getMailers()!=null){
				Map<String,String> tempMailers=contact.getMailers();
				for(String ptr:tempMailers.keySet()){
					writeStr.append("X-"+ptr+":"+tempMailers.get(ptr)+"\r\n");
				}
			}
			writeStr.append("END:VCARD");
			byte [] contactDetails=writeStr.toString().getBytes();
			for(byte temp:contactDetails){
				vcf.write(temp);
			}
			vcf.close();
			
		} catch (FileNotFoundException e) {
			throw new UserDefinedExceptionMessage("Destination Path Invalid");
		} catch (IOException e) {
			throw new UserDefinedExceptionMessage("Contact Admin : IOException in Write");
		}catch (Exception e){
			throw e;
		}
		
	}
}
