package entity;

import java.math.BigDecimal;

public class Util {
	
	public static boolean isNeitherNullNorEmpty(Object obj) {
		boolean isNeitherNullNorEmpty = true;

		if (obj == null || "".equals(obj.toString().trim())) {
			isNeitherNullNorEmpty = false;
		}
		return isNeitherNullNorEmpty;
	}
	
	public static boolean checkNullEmptyStr(String checkableString) {

		boolean isValid = false;
		if (checkableString != null && checkableString.trim().length() > 0) {
			isValid = true;
		}
		return isValid;
	}
	
	
	public static boolean isNeitherNullNorEmptyNorZero(Object obj) {
		boolean isNeitherNullNorEmptyNorZero = true;

		if (obj == null || "".equals(obj.toString().trim())) {
			isNeitherNullNorEmptyNorZero = false;
		} else if ((obj instanceof BigDecimal)) {
			if (BigDecimal.ZERO.equals(new BigDecimal(obj.toString()))) {
				isNeitherNullNorEmptyNorZero = false;
			}
		}else if ((obj instanceof Integer)) {
			if (new Integer(0).equals(new Integer(obj.toString()))) {
				isNeitherNullNorEmptyNorZero = false;
			}
		}
		return isNeitherNullNorEmptyNorZero;
	}
	
	public static boolean checkEqualsObjects(Object value, Object value1) {
		boolean isEqual = false;
		if(value==null && value1==null){
			isEqual = true;
		}
		if((value!=null && value1!=null) && value.equals(value1)){
			isEqual = true;
		}
		return isEqual;
	}

}
