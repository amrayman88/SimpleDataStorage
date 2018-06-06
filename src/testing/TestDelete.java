package testing;

import main.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestDelete {

	@Test
	void test() {
		Task test = new Task();

		test.delete_persons();

		File file = new File(test.pathJSON);
		assertEquals(false, file.exists());

		test.delete_persons();
		assertEquals(false, file.exists());
	}

	@Test
	void test_CSV() {
		Task test = new Task();
		test.file_type = true;

		test.delete_persons();

		File file = new File(test.pathCSV);
		assertEquals(false, file.exists());

		test.delete_persons();
		assertEquals(false, file.exists());
	}

}
