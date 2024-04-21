package gr.codelearn.rentbnb;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeTakenTestListener implements TestExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(RentBnbDemo.class);

    private long startTime;

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL ||
                testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info(testIdentifier.getDisplayName() + " finished with status " + testExecutionResult.getStatus() + " and took " + elapsedTime + " milliseconds to run.");
        }
    }
}
