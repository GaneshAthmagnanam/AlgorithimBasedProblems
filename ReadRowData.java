package xlstovcf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import common.ErrorLogger;

import vcardcomponents.AddressIndex;
import vcardcomponents.EMailIndex;
import vcardcomponents.MailerIndex;
import vcardcomponents.TelephoneIndex;
import vcardcomponents.URLIndex;
import vcardcomponents.VCard;

import exception.FormatUnknownException;
import exception.NameFieldBlankException;


public class ReadRowData{
	
	private static int name;
	private static int formattedName;
	private static List<TelephoneIndex> telephone;
	private static List<EMailIndex> eMail;
	private static List<AddressIndex> address;
	private static int organization;
	private static int title;
	private static List<URLIndex> url;
	private static int note;
	private static List<MailerIndex> mailer;
	private static int bDay;
	
	private static List<String> telephoneTypes;
	private static List<String> eMailTypes;
	private static List<String> addressTypes;
	private static List<String> mailers;
	
	static{
		telephoneTypes=new ArrayList<String>();
		telephoneTypes.add("HOME");
		telephoneTypes.add("WORK");
		telephoneTypes.add("MOBILE");
		telephoneTypes.add("FAX");
		telephoneTypes.add("OTHER");
		telephoneTypes.add("PERSONAL");
		
		eMailTypes=new ArrayList<String>();
		eMailTypes.add("HOME");
		eMailTypes.add("WORK");
		eMailTypes.add("MOBILE");
		
		addressTypes=new ArrayList<String>();
		addressTypes.add("HOME");
		addressTypes.add("WORK");
		
		mailers=new ArrayList<String>();
		mailers.add("YAHOO");
		mailers.add("SKYPE");
		mailers.add("GOOGLE TALK");
		mailers.add("JABBER");
		mailers.add("AIM");
		mailers.add("WINDOWS LIVE");
		mailers.add("QQ");
		mailers.add("ICQ");
		
		
		
	}

	public static VCard extractRow (XSSFRow row)throws NameFieldBlankException{
		VCard contact=new VCard();
		XSSFCell tempCell=null,tempCell2=null;
		int tempCellType=-1,tempCellType2=-1;
		String formattedNameLoc=null;
		try{
			tempCell=row.getCell(formattedName);
			if(tempCell==null){
				throw new NameFieldBlankException();
			}else{
				tempCellType=tempCell.getCellType();
				if(tempCellType==1 ){
					formattedNameLoc=tempCell.getStringCellValue();
					contact.setFormattedName(formattedNameLoc);
				}else{
					throw new FormatUnknownException();
				}
			}
		}catch(NameFieldBlankException e){
			throw e;
		}catch(FormatUnknownException e){
			ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Display Name", e.getMessage());
		}
		
		tempCell=name==-1?null:row.getCell(name);
		if(tempCell!=null){
			tempCellType=tempCell.getCellType();
			if(tempCellType==1 ){
				contact.setName(tempCell.getStringCellValue());
			}else{
				contact.setName(formattedNameLoc);
				ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Name", "Name Field copied from Display Name field");
			}
		}else{
			contact.setName(formattedNameLoc);
			ErrorLogger.ErrorLogging(formattedName+",Name", "Name Field copied from Display Name field");
		}
		
		try{
			tempCell=organization==-1?null:row.getCell(organization);
			if(tempCell!=null){
				tempCellType=tempCell.getCellType();
				if(tempCellType==1 ){
					contact.setOrganization(tempCell.getStringCellValue());
				}else{
					throw new FormatUnknownException();
				}
			}
		}catch(FormatUnknownException e){
			ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Organization", e.getMessage()+1);
		}
		
		try{
			tempCell=note==-1?null:row.getCell(note);
			if(tempCell!=null){
				tempCellType=tempCell.getCellType();
				if(tempCellType==1 ){
					contact.setNote(tempCell.getStringCellValue());
				}else{
					throw new FormatUnknownException();
				}
			}
		}catch(FormatUnknownException e){
			ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Note", e.getMessage()+1);
		}
		
		try{
			tempCell=bDay==-1?null:row.getCell(bDay);
			if(tempCell!=null){
				tempCellType=tempCell.getCellType();
				if(tempCellType==0 ){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
					Date tempbDay=tempCell.getDateCellValue();
					contact.setbDay(sdf.format(tempbDay));
				}else{
					throw new FormatUnknownException();
				}
			}
		}catch(FormatUnknownException e){
			ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Birthday", e.getMessage()+1);
		}
		
		if(telephone!=null){
			Map<String,String> tempTele=new HashMap<String, String>();
			//System.out.println("Telephone Size "+telephone.size());
			for(int counter=0;counter<telephone.size();counter++){
				try{
					//System.out.println("Inside Telephone loop");
					//Double tempNum=row.getCell(telephone[counter]).getNumericCellValue();
					tempCell=telephone.get(counter).getNumber()==-1?null:row.getCell(telephone.get(counter).getNumber());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						//System.out.println("Tele type "+tempCellType);
						if(tempCellType==0){
							Double tempNum=tempCell.getNumericCellValue();
							Long tempLongNum=tempNum.longValue();
							String tempStrNum=tempLongNum.toString();
							if(!tempStrNum.startsWith("0")){
								tempStrNum="0"+tempStrNum;
							}
							//System.out.println("tel no "+tempNum);
							tempCell2=telephone.get(counter).getType()==-1?null:row.getCell(telephone.get(counter).getType());
							if(tempCell2!=null){
								tempCellType2=tempCell2.getCellType();
								if(tempCellType2==0){
									Double tempType=tempCell2.getNumericCellValue();
									Long tempTypeLong=tempType.longValue();
									tempTele.put(tempStrNum,"X-"+tempTypeLong.toString());
								}else if(tempCellType2==1){
									String telType=tempCell2.getStringCellValue().toUpperCase();
									//System.out.println("before contains");
									if(!telephoneTypes.contains(telType)){
										telType="X-"+telType;
									}
									//System.out.println("after contains");
									tempTele.put(tempStrNum,telType);
								}else{
									tempTele.put(tempStrNum,"X-"+tempCell2.getCellFormula());
								}
							}else{
								tempTele.put(tempStrNum,"HOME");
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Telephone"+(counter+1), "Telephone assigned to default 'HOME' type ");
							}
							
						}else{
							throw new FormatUnknownException();
						}
					}
				}catch(FormatUnknownException e){
					ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Telephone"+(counter+1), e.getMessage()+1);
				}
			}
			contact.setTelephone(tempTele);
		}

		if(eMail!=null){
			Map<String,String> tempeMail=new HashMap<String, String>();
			for(int counter=0;counter<eMail.size();counter++){
				try{
					tempCell=eMail.get(counter).getMailId()==-1?null:row.getCell(eMail.get(counter).getMailId());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							tempCell2=eMail.get(counter).getMailType()==-1?null:row.getCell(eMail.get(counter).getMailType());
							if(tempCell2!=null){
								tempCellType2=tempCell2.getCellType();
								if(tempCellType2==0){
									Double tempType=tempCell2.getNumericCellValue();
									Long tempTypeLong=tempType.longValue();
									tempeMail.put(tempCell.getStringCellValue(),"X-"+tempTypeLong.toString());
								}else if(tempCellType2==1){
									String mailType=tempCell2.getStringCellValue().toUpperCase();
									if(!addressTypes.contains(mailType)){
										mailType="X-"+mailType;
									}
									tempeMail.put(tempCell.getStringCellValue(),mailType);
								}else{
									tempeMail.put(tempCell.getStringCellValue(),"X-"+tempCell2.getCellFormula());
								}
							}else{
								tempeMail.put(tempCell.getStringCellValue(),"HOME");
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",eMail"+(counter+1), "eMail assigned to default 'HOME' type ");
							}
							
						}else{
							throw new FormatUnknownException();
						}
					}
				}catch(FormatUnknownException e){
					ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",eMail"+(counter+1), e.getMessage()+1);
				}
			}
			contact.seteMail(tempeMail);
		}
		if(address!=null){
			Map<String,String> tempAddress=new HashMap<String, String>();
			for(int counter=0;counter<address.size();counter++){
				try{
					StringBuffer consolidatedAddress=new StringBuffer();
					
					tempCell=address.get(counter).getPostBoxNo()==-1?null:row.getCell(address.get(counter).getPostBoxNo());					
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==0){
							Double tempDouble=tempCell.getNumericCellValue();
							consolidatedAddress.append(tempDouble.longValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address PB No."+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					tempCell=address.get(counter).getLandMark()==-1?null:row.getCell(address.get(counter).getLandMark());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							consolidatedAddress.append(tempCell.getStringCellValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address Landmark"+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					tempCell=address.get(counter).getStreet()==-1?null:row.getCell(address.get(counter).getStreet());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							consolidatedAddress.append(tempCell.getStringCellValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address Street"+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					tempCell=address.get(counter).getCity()==-1?null:row.getCell(address.get(counter).getCity());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							consolidatedAddress.append(tempCell.getStringCellValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address City"+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					tempCell=address.get(counter).getState()==-1?null:row.getCell(address.get(counter).getState());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							consolidatedAddress.append(tempCell.getStringCellValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address State"+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					tempCell=address.get(counter).getPincode()==-1?null:row.getCell(address.get(counter).getPincode());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==0){
							Double tempDouble=tempCell.getNumericCellValue();
							consolidatedAddress.append(tempDouble.longValue()+";");
						}else{
							try{
								consolidatedAddress.append(";");
								throw new FormatUnknownException();
							}catch(FormatUnknownException e){
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address Pincode"+(counter+1), e.getMessage()+1);
							}
						}
					}else{
						consolidatedAddress.append(";");
					}
					
					if(!consolidatedAddress.toString().matches(";;;;;;")){
						tempCell2=address.get(counter).getAddressType()==-1?null:row.getCell(address.get(counter).getAddressType());
						if(tempCell2!=null){
							tempCellType2=tempCell2.getCellType();
							if(tempCellType2==0){
								Double tempType=tempCell2.getNumericCellValue();
								Long tempTypeLong=tempType.longValue();
								tempAddress.put(consolidatedAddress.toString(),"X-"+tempTypeLong.toString());
							}else if(tempCellType2==1){
								String addType=tempCell2.getStringCellValue().toUpperCase();
								if(!addressTypes.contains(addType.toUpperCase())){
									addType="X-"+addType;
								}
								tempAddress.put(consolidatedAddress.toString(),addType);
							}else{
								tempAddress.put(consolidatedAddress.toString(),"X-"+tempCell2.getCellFormula());
							}
						}else{
							tempAddress.put("HOME",tempCell.getStringCellValue());
							ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Address"+(counter+1), "Address assigned to default 'HOME' type ");
						}
					}else{
						throw new FormatUnknownException();
					}
				}catch(FormatUnknownException e){
					ErrorLogger.ErrorLogging("All address colums for Address"+(counter+1), e.getMessage()+1);
				}
			}
			contact.setAddress(tempAddress);
		}

		if(url!=null){
			List<String> tempUrl=new ArrayList<String>();
			for(int counter=0;counter<url.size();counter++){
				try{
					tempCell=url.get(counter).getUrl()==-1?null:row.getCell(url.get(counter).getUrl());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							tempUrl.add(tempCell.getStringCellValue());						
						}else{
							throw new FormatUnknownException();
						}
					}
				}catch(FormatUnknownException e){
					ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",URL"+(counter+1), e.getMessage()+1);
				}
			}
			contact.setUrl(tempUrl);
		}
		
		if(mailer!=null){
			Map<String,String> tempMailer=new HashMap<String, String>();
			for(int counter=0;counter<mailer.size();counter++){
				try{
					tempCell=mailer.get(counter).getMailerUsername()==-1?null:row.getCell(mailer.get(counter).getMailerUsername());
					if(tempCell!=null){
						tempCellType=tempCell.getCellType();
						if(tempCellType==1){
							tempCell2=mailer.get(counter).getMailer()==-1?null:row.getCell(mailer.get(counter).getMailer());
							if(tempCell2!=null){
								tempCellType2=tempCell2.getCellType();
								if(tempCellType2==0){
									Double tempType=tempCell2.getNumericCellValue();
									Long tempTypeLong=tempType.longValue();
									tempMailer.put("CUSTOM-IM;"+tempTypeLong.toString(),tempCell.getStringCellValue());
								}else if(tempCellType2==1){
									String mailer=tempCell2.getStringCellValue().toUpperCase();
									if(!mailers.contains(mailer)){
										mailer="CUSTOM-IM;"+mailer;
									}else{
										mailer=mailer+"-USERNAME";
									}
									tempMailer.put(mailer,tempCell.getStringCellValue());
								}else{
									tempMailer.put("CUSTOM-IM;"+tempCell2.getCellFormula(),tempCell.getStringCellValue());
								}
							}else{
								tempMailer.put("SKYPE",tempCell.getStringCellValue());
								ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Mailer"+(counter+1), "eMail assigned to default 'SKYPE' type ");
							}
							
						}else{
							throw new FormatUnknownException();
						}
					}
				}catch(FormatUnknownException e){
					ErrorLogger.ErrorLogging((tempCell.getColumnIndex()+1)+",Mailer"+(counter+1), e.getMessage()+1);
				}
			}
			contact.setMailers(tempMailer);
		}
		
	return contact;			
	}

	public static int getName() {
		return name;
	}

	public static void setName(int name) {
		ReadRowData.name = name;
	}

	public static int getFormattedName() {
		return formattedName;
	}

	public static void setFormattedName(int formattedName) {
		ReadRowData.formattedName = formattedName;
	}

	public static List<TelephoneIndex> getTelephone() {
		return telephone;
	}

	public static void setTelephone(List<TelephoneIndex> telephone) {
		ReadRowData.telephone = telephone;
	}

	public static List<EMailIndex> getEMail() {
		return eMail;
	}

	public static void setEMail(List<EMailIndex> mail) {
		eMail = mail;
	}

	public static List<AddressIndex> getAddress() {
		return address;
	}

	public static void setAddress(List<AddressIndex> address) {
		ReadRowData.address = address;
	}

	public static int getOrganization() {
		return organization;
	}

	public static void setOrganization(int organization) {
		ReadRowData.organization = organization;
	}

	public static int getTitle() {
		return title;
	}

	public static void setTitle(int title) {
		ReadRowData.title = title;
	}

	public static List<URLIndex> getUrl() {
		return url;
	}


	public static void setUrl(List<URLIndex> url) {
		ReadRowData.url = url;
	}

	public static int getNote() {
		return note;
	}

	public static void setNote(int note) {
		ReadRowData.note = note;
	}

	public static List<MailerIndex> getMailer() {
		return mailer;
	}

	public static void setMailer(List<MailerIndex> mailer) {
		ReadRowData.mailer = mailer;
	}

	public static int getBDay() {
		return bDay;
	}

	public static void setBDay(int day) {
		bDay = day;
	}	
}
