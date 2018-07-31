package xlstovcf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.POIXMLException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vcardcomponents.VCard;

import common.ErrorLogger;

import exception.NameFieldBlankException;
import exception.UserDefinedExceptionMessage;

public class ReadFromExcel {

	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@SuppressWarnings("unchecked")
	public void indexingXLSX() throws Exception{
		InputStream inputXL=null;
		VCard contact=null;
		try {
			//system.out.println("file"+fileName);
			//System.out.println(fileName);
			inputXL=new FileInputStream(fileName);
			XSSFWorkbook inputWB=new XSSFWorkbook(inputXL);
			XSSFSheet sheet1=inputWB.getSheetAt(0);
			//XSSFRow row1=sheet1.getRow(0);
			//Cell cell1=row1.getCell(0);
			//System.out.println(cell1.getStringCellValue());
			Iterator rows=sheet1.rowIterator();
			while(rows.hasNext()){
				XSSFRow row=(XSSFRow)rows.next();
				try{
					ErrorLogger.ErrorLogging(row.getRowNum()+1);
					contact=ReadRowData.extractRow(row);
					try{
						WriteInNotepad.saveAsVCF(contact);
					}catch(UserDefinedExceptionMessage e){
						throw e;
					}
				}catch(NameFieldBlankException e){
					//system.out.println("Inside Row Catch");
					ErrorLogger.ErrorLogging("", e.getMessage());
				}
			}
			
		} catch (FileNotFoundException e) {
			throw new UserDefinedExceptionMessage("Input File or Path Invalid");
		} catch (IOException e) {
			throw new UserDefinedExceptionMessage("Contact Admin : IOException in read");
		}catch (POIXMLException e){
			if(e.getMessage().contains("InvalidFormatException")){
				e.printStackTrace();
				throw new UserDefinedExceptionMessage("Input file format not compatible");
			}else{
				throw e;
			}
		}
	}

	/*
	public static void main(String [] args) throws Exception{
		
		int  [] tel={4,6,8,10};
		int [] telType={5,7,9,11};
		int [] eMail={12}; 
		int [] emailType =null;
		int [] addr = {14};
		int [] addrType =null;
		int [] url={13};
		int [] mailers=null;
		
		new ReadFromExcel().indexingXLSX("dummy.xlsx",0,1,tel,telType,eMail,emailType,addr,addrType,-1,-1,url,-1,mailers,15);
		//system.out.println("Write Successful");

	}
	*/
}
