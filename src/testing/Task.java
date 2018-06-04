package testing;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.*;

public class Task {
	
	public static String path;
	
	public Task()
	{
		path="data.json";
	}
	
	public Task(String _path)
	{
		path=_path;
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

	private static void creat_file(Vector v) {
		JSONObject obj = new JSONObject();
		obj = make_jsonarr(v);

		try (FileWriter file = new FileWriter(path)) {

			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private static Vector jsonload() {
		Vector v = new Vector();

		JSONParser parser = new JSONParser();
		File file = new File(path);
		if(!file.exists())
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

	public static Vector add_person(String first_name, String last_name, String title, String phone, String mail,
			int age) {
		Vector v = new Vector();
		v = jsonload();

		v.add(new person(first_name, last_name, title, phone, mail, age));

		creat_file(v);
		
		return v;
	}

	public static Vector list_persons() {
		Vector v = new Vector();
		v = jsonload();

		for (int i = 0; i < v.size(); i++) {
			System.out.println("Person #" + String.valueOf(i) + ":");
			person tmp = (person) v.elementAt(i);
			tmp.show();
		}
		return v;
	}

	public static Vector filter_persons(String field) {
		Vector v = new Vector();
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
		creat_file(v);
	}

	public static void update_person(int index, String first_name, String last_name, String title, String phone,
			String mail, int age) {
		Vector v = new Vector();
		v = jsonload();

		v.setElementAt(new person(first_name, last_name, title, phone, mail, age), index);

		creat_file(v);
	}

	public static void delete_persons() {
		try {
			Files.delete(Paths.get(path));
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
		path="D:\\list.json";
		
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

				System.out.println("person added successfully");

			}
		}
		scanner.close();
	}

}
