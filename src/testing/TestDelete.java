package testing;

import main.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestDelete {

	@Test
	void test() {
		Task test=new Task("D:\\test.json");
		
		test.delete_persons();
		
		File file=new File(test.path);
		assertEquals(false, file.exists());
		
		test.delete_persons();
		assertEquals(false, file.exists());
	}

}
