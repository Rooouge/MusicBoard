package musicboard.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import jutils.config.Config;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

	public static final SimpleDateFormat CREATION_DATE_FORMAT = new SimpleDateFormat(Config.getValue("creation-date-format"));
	
	private int id;
	private String value;
	private Date creationDate;
	
}
