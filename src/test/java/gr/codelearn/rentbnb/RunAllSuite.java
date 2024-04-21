package gr.codelearn.rentbnb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

import java.io.PrintWriter;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("RunAll Suite")
@SelectPackages("gr.codelearn.rentbnb.service")
public class RunAllSuite {
    @Test
    public void runAllTests() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectPackage("gr.codelearn.rentbnb.service"))
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
        TestExecutionListener timingListener = new TimeTakenTestListener();

        try {
            launcher.registerTestExecutionListeners(summaryListener, timingListener);
            launcher.execute(request);
        } catch (Exception e) {
            e.getMessage();
        }

        summaryListener.getSummary().printTo(new PrintWriter(System.out));
    }
}

