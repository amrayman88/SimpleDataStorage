package main;

import java.util.*;
import com.opencsv.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.*;

public class Task {

	public static boolean file_type;

	public static String pathJSON, pathCSV;

	public Task() {
		pathJSON = "data.json";
		pathCSV = "data.csv";
		file_type = false;
	}

	public Task(String _pathJSON, String _pathCSV, boolean _file_type) {
		pathJSON = _pathJSON;
		pathCSV = _pathCSV;
		file_type = _file_type;
	}

	private static JSONObject make_jsonobj(person tmp) {
		JSONObject obj = new JSONObject();

		obj.put("First name", tmp.first_name);
		obj.put("Last name", tmp.last_name);
		obj.put("Title", tmp.title);
		obj.put("Phone", tmp.phone);
		obj.put("Mail", tmp.mail);
		obj.put("Age", tmp.age);

		return obj;
	}

	private static JSONObject make_jsonarr(Vector v) {
		JSONArray list = new JSONArray();

		for (int i = 0; i < v.size(); i++) {
			person tmp = (person) v.elementAt(i);
			list.add(make_jsonobj(tmp));
		}

		JSONObject obj = new JSONObject();
		obj.put("Persons", list);
		return obj;
	}

	private static void creat_file_JSON(Vector v) {
		JSONObject obj = new JSONObject();
		obj = make_jsonarr(v);

		try (FileWriter file = new FileWriter(pathJSON)) {

			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void creat_file_CSV(Vector v) {
		try (Writer writer = Files.newBufferedWriter(Paths.get(pathCSV));

				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			for (int i = 0; i < v.size(); i++) {
				person tmpp = (person) v.elementAt(i);
				String[] tmp = new String[] { tmpp.first_name, tmpp.last_name, tmpp.title, tmpp.phone, tmpp.mail,
						String.valueOf(tmpp.age) };
				csvWriter.writeNext(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static person make_person_JSON(JSONObject tmp) {

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

	private static Vector jsonload() {
		Vector v = new Vector();

		JSONParser parser = new JSONParser();
		File file = new File(pathJSON);
		if (!file.exists())
			return v;
		try {
			Object obj = parser.parse(new FileReader(pathJSON));

			JSONObject jsonObject = (JSONObject) obj;

			// loop array
			JSONArray persons = (JSONArray) jsonObject.get("Persons");
			Iterator<JSONObject> iterator = persons.iterator();
			while (iterator.hasNext()) {
				JSONObject tmp = iterator.next();
				person ans = make_person_JSON(tmp);
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

	public static Vector add_person(String first_name, String last_name, String title, String phone, String mail,
			int age) {
		Vector v = new Vector();

		if (file_type)
			v = csvload();
		else
			v = jsonload();

		v.add(new person(first_name, last_name, title, phone, mail, age));

		if (file_type)
			creat_file_CSV(v);
		else
			creat_file_JSON(v);
		return v;
	}

	public static Vector list_persons() {
		Vector v = new Vector();
		if (file_type)
			v = csvload();
		else
			v = jsonload();

		for (int i = 0; i < v.size(); i++) {
			System.out.println("Person #" + String.valueOf(i) + ":");
			person tmp = (person) v.elementAt(i);
			tmp.show();
		}
		return v;
	}

	private static Vector csvload() {
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

	public static Vector filter_persons(String field) {
		Vector v = new Vector();

		if (file_type)
			v = csvload();
		else
			v = jsonload();

		Vector ans = new Vector();

		for (int i = 0; i < v.size(); i++) {
			System.out.println("Person #" + String.valueOf(i) + ":");
			person tmp = (person) v.elementAt(i);
			ans.add(tmp.getfield(field));
			tmp.show(field);
		}

		return ans;
	}

	public static void sort_persons(String field) {
		Vector v = new Vector();

		if (file_type)
			v = csvload();
		else
			v = jsonload();

		if (field.toLowerCase().equals("first name"))
			Collections.sort(v, person.personfirstnamecomp);
		if (field.toLowerCase().equals("last name"))
			Collections.sort(v, person.personlastnamecomp);
		if (field.toLowerCase().equals("title"))
			Collections.sort(v, person.persontitlecomp);
		if (field.toLowerCase().equals("phone"))
			Collections.sort(v, person.personphonecomp);
		if (field.toLowerCase().equals("mail"))
			Collections.sort(v, person.personmailcomp);
		if (field.toLowerCase().equals("age"))
			Collections.sort(v, person.personagecomp);

		if (file_type)
			creat_file_CSV(v);
		else
			creat_file_JSON(v);
	}

	public static void update_person(int index, String first_name, String last_name, String title, String phone,
			String mail, int age) {
		Vector v = new Vector();

		if (file_type)
			v = csvload();
		else
			v = jsonload();

		v.setElementAt(new person(first_name, last_name, title, phone, mail, age), index);

		if (file_type)
			creat_file_CSV(v);
		else
			creat_file_JSON(v);
	}

	public static void delete_persons() {
		try {
			if (file_type)
				Files.delete(Paths.get(pathCSV));
			else
				Files.delete(Paths.get(pathJSON));
			System.out.println("Deletion successful.");
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Vector v = new Vector();

		v = jsonload();

		while (true) {
			String in = scanner.nextLine();

			if (in.toLowerCase().equals("add")) {
				String first_name, last_name, title, phone, mail;
				int age;

				System.out.println("enter the first name:");
				first_name = scanner.nextLine();

				System.out.println("enter the last name:");
				last_name = scanner.nextLine();

				System.out.println("enter the title:");
				title = scanner.nextLine();

				System.out.println("enter the phone:");
				phone = scanner.nextLine();

				System.out.println("enter the mail:");
				mail = scanner.nextLine();

				System.out.println("enter the age:");
				age = scanner.nextInt();
				scanner.nextLine();

				add_person(first_name, last_name, title, phone, mail, age);

				System.out.println("person added successfully");
			} else if (in.toLowerCase().equals("list")) {
				list_persons();
			} else if (in.toLowerCase().equals("quit")) {
				break;
			} else if (in.toLowerCase().equals("delete")) {
				delete_persons();
			} else if (in.toLowerCase().equals("filter")) {
				System.out.println("please choose the filtering field:");
				String field = scanner.nextLine();

				filter_persons(field);
			} else if (in.toLowerCase().equals("sort")) {
				System.out.println("please choose the sorting field:");
				String field = scanner.nextLine();

				sort_persons(field);

				System.out.println("persons sorted successfully");
			} else if (in.toLowerCase().equals("update")) {
				System.out.println("please choose the number of the person you want to update:");
				int index = scanner.nextInt();
				scanner.nextLine();
				String first_name, last_name, title, phone, mail;
				int age;

				System.out.println("enter the first name:");
				first_name = scanner.nextLine();

				System.out.println("enter the last name:");
				last_name = scanner.nextLine();

				System.out.println("enter the title:");
				title = scanner.nextLine();

				System.out.println("enter the phone:");
				phone = scanner.nextLine();

				System.out.println("enter the mail:");
				mail = scanner.nextLine();

				System.out.println("enter the age:");
				age = scanner.nextInt();

				update_person(index, first_name, last_name, title, phone, mail, age);

				System.out.println("persons updated successfully");

			} else if (in.toLowerCase().equals("change format")) {
				file_type = !file_type;
				if (file_type) {
					System.out.println("CSV file is now used");
					v = csvload();
				} else {
					System.out.println("JSON file is now used");
					v = jsonload();
				}

			} else if (in.toLowerCase().equals("check format")) {
				if(file_type)
					System.out.println("CSV is being used");
				else
					System.out.println("JSON is being used");
			} else {
				System.out.println("please enter a valid command");
			}
		}
		System.out.println("Thanks, bye :)");
		scanner.close();
	}

}
