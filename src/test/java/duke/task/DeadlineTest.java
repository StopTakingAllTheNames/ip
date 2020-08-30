package duke.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {
    @Test
    public void factoryMethods_success() {
        String command1 = "deadline test /by 2/8/20";
        String command2 = "test";
        String command3 = "2020-08-02";
        try {
            assertEquals(Deadline.create(command1),
                    Deadline.createFromFile(command2, command3));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void print_success() {
        try {
            Deadline test = Deadline.create("deadline test /by 9-12-10");
            test.setDone();
            assertEquals("D | 1 | test | 2010-12-09", test.print());
        } catch (Exception e) {
            fail();
        }

    }
}
