package testing;

import main.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;

class TestList {

	void assertPerson(person experson, person tstperson) {
		assertEquals(experson.age, tstperson.age);
		assertEquals(experson.first_name, tstperson.first_name);
		assertEquals(experson.last_name, tstperson.last_name);
		assertEquals(experson.mail, tstperson.mail);
		assertEquals(experson.phone, tstperson.phone);
		assertEquals(experson.title, tstperson.title);
	}

	private static person make_person(JSONObject tmp) {

		String first_name = (String) tmp.get("First name");
		String last_name = (String) tmp.get("Last name");
		String phone = (String) tmp.get("Phone");
		String mail = (String) tmp.get("Mail");
		String title = (String) tmp.get("Title");
		long agee = (long) tmp.get("Age");
		int age = (int) agee;

		person ans = new person(first_name, last_name, title, phone, mail, age);
		return ans;
	}

	private static Vector jsonload(String path) {
		Vector v = new Vector();

		JSONParser parser = new JSONParser();
		File file = new File(path);
		if (!file.exists())
			return v;
		try {
			Object obj = parser.parse(new FileReader(path));

			JSONObject jsonObject = (JSONObject) obj;

			// loop array
			JSONArray persons = (JSONArray) jsonObject.get("Persons");
			Iterator<JSONObject> iterator = persons.iterator();
			while (iterator.hasNext()) {
				JSONObject tmp = iterator.next();
				person ans = make_person(tmp);
				v.add(ans);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return v;
	}

	private static Vector csvload(String pathCSV) {
		Vector v = new Vector();
		File file = new File(pathCSV);
		if (!file.exists())
			return v;
		try (Reader reader = Files.newBufferedReader(Paths.get(pathCSV));
				CSVReader csvReader = new CSVReader(reader);) {
			// Reading Records One by One in a String array
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				person tmp = new person(nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3], nextRecord[4],
						Integer.parseInt(nextRecord[5]));
				v.add(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return v;
	}

	
	@Test
	void test_list() {
		Task test = new Task();

		test.delete_persons();

		test.add_person("omar", "ahmed", "old", "011111111", "omar@gmail.com", 20);
		test.add_person("amr", "ayman", "new", "012222222", "amr@gmail.com", 21);

		Vector exv = new Vector();
		Vector tstv = new Vector();

		exv = test.list_persons();
		tstv = jsonload(test.pathJSON);

		for (int i = 0; i < exv.size(); i++) {
			assertPerson((person) exv.elementAt(i), (person) tstv.elementAt(i));
		}
	}

	@Test
	void test_list_CSV() {
		Task test = new Task();
		test.file_type = true;

		test.delete_persons();

		test.add_person("omar", "ahmed", "old", "011111111", "omar@gmail.com", 20);
		test.add_person("amr", "ayman", "new", "012222222", "amr@gmail.com", 21);

		Vector exv = new Vector();
		Vector tstv = new Vector();

		exv = test.list_persons();
		tstv = csvload(test.pathCSV);

		for (int i = 0; i < exv.size(); i++) {
			assertPerson((person) exv.elementAt(i), (person) tstv.elementAt(i));
		}
	}

}
