package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class TestFilter {

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

	@Test
	void test() {
		Task test = new Task("D:\\test.json");
		Vector v = new Vector();

		test.delete_persons();

		test.add_person("omar", "ahmed", "old", "011111111", "omar@gmail.com", 20);
		test.add_person("amr", "ayman", "new", "012222222", "amr@gmail.com", 21);

		// first name
		v = test.filter_persons("first name");

		String[] expf = { "omar", "amr" };

		for (int i = 0; i < v.size(); i++) {
			String act = (String) v.elementAt(i);
			assertEquals(expf[i], act);
		}

		// last name
		v = test.filter_persons("last name");

		String[] expl = { "ahmed", "ayman" };

		for (int i = 0; i < v.size(); i++) {
			String act = (String) v.elementAt(i);
			assertEquals(expl[i], act);
		}

		// title
		v = test.filter_persons("title");

		String[] expt = { "old", "new" };

		for (int i = 0; i < v.size(); i++) {
			String act = (String) v.elementAt(i);
			assertEquals(expt[i], act);
		}
		
		// phone
		v = test.filter_persons("phone");

		String[] expp = { "011111111", "012222222" };

		for (int i = 0; i < v.size(); i++) {
			String act = (String) v.elementAt(i);
			assertEquals(expp[i], act);
		}
		
		// mail
		v = test.filter_persons("mail");

		String[] expm = { "omar@gmail.com", "amr@gmail.com" };

		for (int i = 0; i < v.size(); i++) {
			String act = (String) v.elementAt(i);
			assertEquals(expm[i], act);
		}
		
		// age
		v = test.filter_persons("age");

		int[] expa = { 20, 21 };

		for (int i = 0; i < v.size(); i++) {
			int act = (int) v.elementAt(i);
			assertEquals(expa[i], act);
		}
	}

}
