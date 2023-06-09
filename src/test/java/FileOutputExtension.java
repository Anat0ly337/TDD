import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.mockito.Mockito;

public class FileOutputExtension implements TestWatcher, AfterAllCallback {
    private  FileWriter fileWriter;

    private MySpyService myService;


    @Override
    public void testSuccessful(ExtensionContext context) {
        writeTestInfoToFile(context, "SUCCESS");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        writeTestInfoToFile(context, "FAILED");
    }

    private void writeTestInfoToFile(ExtensionContext context, String status) {
        try {
            if (fileWriter == null) {
                fileWriter = new FileWriter("test_execution_info.txt");
            }
            String testName = context.getDisplayName();
            String output = String.format("[%s] Test: %s - %s%n", status, testName, LocalDate.now());
            fileWriter.write(output);

            if (myService == null) {
                myService = Mockito.spy(new MySpyService());
            }

            myService.someMethod();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class MySpyService {

        public void someMethod() {
            System.out.println("method spy is calling");
        }
    }
}
