package main;
import java.util.Comparator;

public class person {
	public String first_name, last_name, title, phone, mail;
	public int age;

	public person(String _first_name, String _last_name, String _title, String _phone, String _mail, int _age) {
		first_name = _first_name;
		last_name = _last_name;
		title = _title;
		phone = _phone;
		mail = _mail;
		age = _age;
	}

	public person() {
		age = 0;
	}

	public void show() {
		System.out.println("\tFirst name: " + first_name);
		System.out.println("\tLast name: " + last_name);
		System.out.println("\tTitle: " + title);
		System.out.println("\tPhone: " + phone);
		System.out.println("\tMail: " + mail);
		System.out.println("\tAge: " + age);
		System.out.println("");
	}

	public void show(String field) {
		if (field.toLowerCase().equals("first name"))
			System.out.println("\tFirst name: " + first_name);
		if (field.toLowerCase().equals("last name"))
			System.out.println("\tLast name: " + last_name);
		if (field.toLowerCase().equals("title"))
			System.out.println("\tTitle: " + title);
		if (field.toLowerCase().equals("phone"))
			System.out.println("\tPhone: " + phone);
		if (field.toLowerCase().equals("mail"))
			System.out.println("\tMail: " + mail);
		if (field.toLowerCase().equals("age"))
			System.out.println("\tAge: " + age);
		System.out.println("");
	}

	public static Comparator<person> personfirstnamecomp = new Comparator<person>() {
		public int compare(person x, person y) {

			String first_name1 = x.first_name.toUpperCase();
			String first_name2 = y.first_name.toUpperCase();

			return first_name1.compareTo(first_name2);

		}
	};


	public static Comparator<person> personlastnamecomp = new Comparator<person>() {
		public int compare(person x, person y) {

			String last_name1 = x.last_name.toUpperCase();
			String last_name2 = y.last_name.toUpperCase();

			return last_name1.compareTo(last_name2);

		}
	};

	
	public static Comparator<person> persontitlecomp = new Comparator<person>() {
		public int compare(person x, person y) {

			String title1 = x.title.toUpperCase();
			String title2 = y.title.toUpperCase();

			return title1.compareTo(title2);

		}
	};

	public static Comparator<person> personmailcomp = new Comparator<person>() {
		public int compare(person x, person y) {

			String mail1 = x.mail.toUpperCase();
			String mail2 = y.mail.toUpperCase();

			return mail1.compareTo(mail2);

		}
	};

	public static Comparator<person> personphonecomp = new Comparator<person>() {
		public int compare(person x, person y) {

			String phone1 = x.phone.toUpperCase();
			String phone2 = y.phone.toUpperCase();

			return phone1.compareTo(phone2);

		}
	};

	public static Comparator<person> personagecomp = new Comparator<person>() {
		public int compare(person x, person y) {

			int age1 = x.age;
			int age2 = y.age;

			if (age1 < age2)
				return -1;
			if (age1 == age2)
				return 0;
			return 1;
		}
	};

	
	public Object getfield(String field) {
		if (field.toLowerCase().equals("first name"))
			return first_name;
		else if (field.toLowerCase().equals("last name"))
			return last_name;
		else if (field.toLowerCase().equals("title"))
			return title;
		else if (field.toLowerCase().equals("phone"))
			return phone;
		else if (field.toLowerCase().equals("mail"))
			return mail;
		return age;
	}

}
