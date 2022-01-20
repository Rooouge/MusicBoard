package musicboard.data.xml;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jutils.asserts.Assert;
import jutils.config.Config;
import jutils.config.Files;
import jutils.log.Log;
import lombok.experimental.UtilityClass;
import musicboard.Global;
import musicboard.data.Board;
import musicboard.data.Item;
import musicboard.data.State;

@UtilityClass
public class XML {

	private File file;
	
	
	public void init() throws Exception {
		Log.system("----------------");
		Log.system("Parsing XML");
		Board board = new Board();
		Document doc = getXML();
		
		NodeList boardElems = doc.getElementsByTagName("board");
		Assert.isTrue(boardElems.getLength() == 1, "Trovati" + boardElems.getLength() + " nodi \"Board\" nel file XML (Atteso 1)");
		
		Element boardElem = (Element) boardElems.item(0);
		board.setCounter(Integer.parseInt(boardElem.getAttribute("counter")));
		/*
		 * Parsing nodi <state>
		 */
		NodeList stateElems = boardElem.getElementsByTagName("state");
		for(int i = 0; i < stateElems.getLength(); i++) {
			Element stateElem = (Element) stateElems.item(i);
			String name = stateElem.getAttribute("name");
			String color = stateElem.getAttribute("color");
			
			State state = new State(name, color);
			
			/*
			 * Parsing nodi <item>
			 */
			NodeList itemElems = stateElem.getElementsByTagName("item");
			for(int j = 0; j < itemElems.getLength(); j++) {
				Element itemElem = (Element) itemElems.item(j);
				String value = itemElem.getAttribute("value");
				int id = Integer.parseInt(itemElem.getAttribute("id"));
				String creationTimeString = itemElem.getAttribute("creation-time");			
				
				Item item = new Item(id, value, creationTimeString.isEmpty() ? new Date() : new SimpleDateFormat(Config.getValue("creation-date-format")).parse(creationTimeString));
				
				state.addItem(item);	
				Log.system("  - [" + (j+1) + "/" + itemElems.getLength() + "] Founded item \"" + item.getValue() + "\"");
			}
			
			board.addState(state);			
			Log.system("- [" + (i+1) + "/" + stateElems.getLength() + "] Founded state \"" + state.getName() + "\"");
		}
		
		Global.add("board", board);
		Global.add("states", String.join(";", board.getStates().stream().map(State::getName).collect(Collectors.toList())));
	}
	
	private Document getXML() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		
		file = Files.getFile(Config.getValue("data"));
		Document doc = dbf.newDocumentBuilder().parse(file);
		doc.getDocumentElement().normalize();
		
		return doc;
	}
	
	
	
	public void write() throws Exception {
		Log.system("----------------");
		Log.system("Saving XML");
		Board board = Global.get("board", Board.class);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = dbf.newDocumentBuilder().newDocument();
		
		Element boardElem = doc.createElement("board");
		boardElem.setAttribute("counter", "" + board.getCounter());
		
		for(State state : board.getStates()) {
			Element stateElem = doc.createElement("state");
			stateElem.setAttribute("name", state.getName());
			Color color = state.getColor();
			String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
			stateElem.setAttribute("color", hex);
			
			for(Item item : state.getItems()) {
				Element itemElem = doc.createElement("item");
				itemElem.setAttribute("id", "" + item.getId());
				itemElem.setAttribute("value", item.getValue());
				itemElem.setAttribute("creation-time", Item.CREATION_DATE_FORMAT.format(item.getCreationDate()));
				
				stateElem.appendChild(itemElem);	
				Log.system("Added item \"" + item.getValue() + "\" + with Id = " + item.getId());
			}
			
			boardElem.appendChild(stateElem);
			Log.system("Added state \"" + state.getName() + "\"");
		}
		
		doc.appendChild(boardElem);
		
		Transformer tr = TransformerFactory.newInstance().newTransformer();
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.METHOD, "xml");
		tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		
		tr.transform(new DOMSource(doc), new StreamResult(file));
	}
}
