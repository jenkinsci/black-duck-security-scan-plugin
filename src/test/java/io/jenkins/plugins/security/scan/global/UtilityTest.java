package io.jenkins.plugins.security.scan.global;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.Result;
import hudson.model.TaskListener;
import io.jenkins.plugins.security.scan.global.enums.BuildStatus;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UtilityTest {
    private FilePath workspace;
    private final TaskListener listenerMock = Mockito.mock(TaskListener.class);
    private final EnvVars envVarsMock = Mockito.mock(EnvVars.class);
    private LoggerWrapper logger;
    private URL url;

    @BeforeEach
    void setup() {
        workspace = new FilePath(new File(getHomeDirectory()));
        logger = new LoggerWrapper(listenerMock);
        when(listenerMock.getLogger()).thenReturn(Mockito.mock(PrintStream.class));

        try {
            url = new URL("https://fake-url.com");
        } catch (MalformedURLException e) {
            System.out.println("Exception occurred while creating url in test");
        }
    }

    @Test
    public void getDirectorySeparatorTest() {

        String separator = Utility.getDirectorySeparator(workspace, listenerMock);
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            assertEquals("\\", separator);
        } else {
            assertEquals("/", separator);
        }
    }

    @Test
    public void getAgentOsTest() {
        String os = Utility.getAgentOs(workspace, listenerMock);

        assertEquals(System.getProperty("os.name").toLowerCase(), os);
    }

    @Test
    public void getAgentOsArchTest() {
        String arch = Utility.getAgentOsArch(workspace, listenerMock);

        assertEquals(System.getProperty("os.arch").toLowerCase(), arch);
    }

    @Test
    public void testRemoveFile() throws IOException {
        File tempFile = new File(getHomeDirectory(), "testfile.txt");
        tempFile.createNewFile();
        String filePath = tempFile.getAbsolutePath();

        Utility.removeFile(filePath, workspace, listenerMock);

        assertFalse(tempFile.exists());
    }

    @Test
    public void isStringNullOrBlankTest() {
        String str = null;
        String emptyString = "";
        String emptyStringContainingSpace = "   ";
        String validString = " This is a valid string  ";

        assertTrue(Utility.isStringNullOrBlank(str));
        assertTrue(Utility.isStringNullOrBlank(emptyString));
        assertTrue(Utility.isStringNullOrBlank(emptyStringContainingSpace));
        assertFalse(Utility.isStringNullOrBlank(validString));
    }

    @Test
    public void getHttpURLConnectionTest() {
        EnvVars envVars = new EnvVars();
        envVars.put("HTTP_PROXY", "http://fake-proxy.com:1010");

        HttpURLConnection httpProxyConnection = Utility.getHttpURLConnection(url, envVars, logger);

        assertNotNull(httpProxyConnection);
        assertEquals(url, httpProxyConnection.getURL());

        envVars.put("NO_PROXY", "https://test-url.com, https://fake-url.com");

        HttpURLConnection noProxyConnection = Utility.getHttpURLConnection(url, envVars, logger);

        assertNotNull(noProxyConnection);
        assertEquals(url, noProxyConnection.getURL());
    }

    @Test
    public void getProxyTest() throws IOException {
        EnvVars envVars = new EnvVars();

        assertEquals(ApplicationConstants.NO_PROXY, Utility.getProxy(url, envVars, logger));

        envVars.put("NO_PROXY", "https://test-url.com, https://fake-url.com");

        assertEquals(ApplicationConstants.NO_PROXY, Utility.getProxy(url, envVars, logger));

        envVars.put("HTTP_PROXY", "https://fake-proxy.com:1010");
        envVars.replace("NO_PROXY", "https://test-url.com");
        envVars.put("HTTPS_PROXY", "https://fake-proxy.com:1010");

        assertEquals(envVars.get("HTTPS_PROXY"), Utility.getProxy(url, envVars, logger));

        envVars.remove("HTTPS_PROXY");

        assertEquals(envVars.get("HTTP_PROXY"), Utility.getProxy(url, envVars, logger));
    }

    @Test
    public void getEnvOrSystemProxyDetailsTest() {
        EnvVars envVars = new EnvVars();

        assertNull(Utility.getEnvOrSystemProxyDetails(ApplicationConstants.NO_PROXY, envVars));
        assertNull(Utility.getEnvOrSystemProxyDetails(ApplicationConstants.HTTP_PROXY, envVars));
        assertNull(Utility.getEnvOrSystemProxyDetails(ApplicationConstants.HTTPS_PROXY, envVars));

        envVars.put("NO_PROXY", "https://test-url.com, https://fake-url.com");
        envVars.put("HTTP_PROXY", "https://fake-proxy.com:1010");
        envVars.put("HTTPS_PROXY", "https://fake-proxy.com:1010");

        assertEquals(
                envVars.get("NO_PROXY"), Utility.getEnvOrSystemProxyDetails(ApplicationConstants.NO_PROXY, envVars));
        assertEquals(
                envVars.get("HTTP_PROXY"),
                Utility.getEnvOrSystemProxyDetails(ApplicationConstants.HTTP_PROXY, envVars));
        assertEquals(
                envVars.get("HTTPS_PROXY"),
                Utility.getEnvOrSystemProxyDetails(ApplicationConstants.HTTPS_PROXY, envVars));
    }

    @Test
    public void setDefaultProxyAuthenticatorTest() {
        Authenticator.setDefault(null);

        PasswordAuthentication passwordAuth = new PasswordAuthentication("username", "password".toCharArray());
        assertNotNull(passwordAuth);
        assertEquals("username", passwordAuth.getUserName());
        assertArrayEquals("password".toCharArray(), passwordAuth.getPassword());

        Utility.setDefaultProxyAuthenticator(
                passwordAuth.getUserName().concat(":").concat(Arrays.toString(passwordAuth.getPassword())));
        Authenticator authenticator = Authenticator.getDefault();
        assertNotNull(authenticator);

        Authenticator.setDefault(null);
    }

    @Test
    public void testSetDefaultProxyAuthenticatorWithInvalidUserInfo() {
        Authenticator.setDefault(null);

        Utility.setDefaultProxyAuthenticator("invalidUserInfo");

        assertNull(Authenticator.getDefault());
    }

    @Test
    public void testGetDefaultSarifReportFilePath_BlackDuckScan() {
        boolean isBlackDuckScan = true;
        boolean isPolarisDuckScan = false;
        String expected = ApplicationConstants.DEFAULT_BLACKDUCKSCA_SARIF_REPORT_FILE_PATH.concat(
                ApplicationConstants.SARIF_REPORT_FILENAME);
        String result = Utility.getDefaultSarifReportFilePath(isBlackDuckScan, isPolarisDuckScan);
        assertEquals(expected, result);
    }

    @Test
    public void testGetDefaultSarifReportFilePath_PolarisDuckScan() {
        boolean isBlackDuckScan = false;
        boolean isPolarisDuckScan = true;
        String expected = ApplicationConstants.DEFAULT_POLARIS_SARIF_REPORT_FILE_PATH.concat(
                ApplicationConstants.SARIF_REPORT_FILENAME);
        String result = Utility.getDefaultSarifReportFilePath(isBlackDuckScan, isPolarisDuckScan);
        assertEquals(expected, result);
    }

    @Test
    public void testGetCustomSarifReportFilePath_BlackDuckScan() {
        Map<String, Object> scanParams = new HashMap<>();
        scanParams.put(
                ApplicationConstants.BLACKDUCKSCA_REPORTS_SARIF_FILE_PATH_KEY, "customPath/report_blackduck.json");
        boolean isBlackDuckScan = true;
        boolean isPolarisDuckScan = false;
        String result = Utility.getCustomSarifReportFilePath(scanParams, isBlackDuckScan, isPolarisDuckScan);
        assertEquals("customPath/report_blackduck.json", result);
    }

    @Test
    public void testGetCustomSarifReportFilePath_PolarisDuckScan() {
        Map<String, Object> scanParams = new HashMap<>();
        scanParams.put(ApplicationConstants.POLARIS_REPORTS_SARIF_FILE_PATH_KEY, "customPath/report_polaris.json");
        boolean isBlackDuckScan = false;
        boolean isPolarisDuckScan = true;
        String result = Utility.getCustomSarifReportFilePath(scanParams, isBlackDuckScan, isPolarisDuckScan);
        assertEquals("customPath/report_polaris.json", result);
    }

    @Test
    public void testDetermineSARIFReportFilePath_CustomPathNull() {
        String customPath = null;
        String defaultPath = "defaultPath";
        String result = Utility.determineSARIFReportFilePath(customPath, defaultPath);
        assertEquals(defaultPath, result);
    }

    @Test
    public void testDetermineSARIFReportFileName_CustomPathNotNull() {
        String customPath = "customPath/file.txt";
        String result = Utility.determineSARIFReportFileName(customPath);
        assertEquals("file.txt", result);
    }

    @Test
    public void testDetermineSARIFReportFileName_CustomPathNull() {
        String customPath = null;
        String result = Utility.determineSARIFReportFileName(customPath);
        assertEquals(ApplicationConstants.SARIF_REPORT_FILENAME, result);
    }

    @Test
    public void isPullRequestEventForPRContextTest() {
        Mockito.when(envVarsMock.get(ApplicationConstants.ENV_CHANGE_ID_KEY)).thenReturn("1");
        assertTrue(Utility.isPullRequestEvent(envVarsMock));
    }

    @Test
    public void isPullRequestEventForNonPRContextTest() {
        assertFalse(Utility.isPullRequestEvent(envVarsMock));
    }

    @Test
    public void getMappedResultForBuildStatusTest() {
        assertEquals(Utility.getMappedResultForBuildStatus(BuildStatus.FAILURE), Result.FAILURE);
        assertEquals(Utility.getMappedResultForBuildStatus(BuildStatus.UNSTABLE), Result.UNSTABLE);
        assertEquals(Utility.getMappedResultForBuildStatus(BuildStatus.SUCCESS), Result.SUCCESS);
    }

    @Test
    public void isBooleanTest() {
        assertTrue(Utility.isBoolean("true"));
        assertTrue(Utility.isBoolean("false"));
        assertFalse(Utility.isBoolean("null"));
    }

    @Test
    public void testParseJsonFile() throws IOException {
        String jsonContent = "{"
                + "\"data\": {\"product1\": {\"project\": {\"issues\": {\"url\": \"http://example.com/issues\"}}}}}";
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(jsonContent);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(tempFile);
        JsonNode actualNode = Utility.parseJsonFile(tempFile);

        assertEquals(expectedNode, actualNode);
    }

    @Test
    public void testGetIssuesUrl_ValidProduct() throws IOException {
        String jsonContent =
                "{" + "\"data\": {\"polaris\": {\"project\": {\"issues\": {\"url\": \"http://example.com/issues\"}}}}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        String product = "polaris";

        String issuesUrl = Utility.getIssuesUrl(rootNode, product);

        assertEquals("http://example.com/issues", issuesUrl);
    }

    @Test
    public void testGetIssuesUrl_InvalidProduct() throws IOException {
        // Arrange
        String jsonContent = "{"
                + "\"data\": {\"product1\": {\"project\": {\"issues\": {\"url\": \"http://example.com/issues\"}}}}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        String product = "invalidProduct";

        String issuesUrl = Utility.getIssuesUrl(rootNode, product);

        assertNull(issuesUrl);
    }

    @Test
    public void testCalculateTotalIssues() throws IOException {
        String jsonContent = "{"
                + "\"data\": {\"polaris\": {\"test\": {\"SAST\": {\"issues\": {\"critical\": 2, \"high\": 3}}, \"SCA\": {\"issues\": {\"medium\": 4, \"low\": 5}}}}}}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        String product = "polaris";

        int totalIssues = Utility.calculateTotalIssues(rootNode, product);

        assertEquals(14, totalIssues);
    }

    @Test
    public void testCalculateIssues() throws IOException {
        String jsonContent = "{" + "\"issues\": {\"critical\": 2, \"high\": 3, \"medium\": 4, \"low\": 5}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode testNode = objectMapper.readTree(jsonContent);

        int totalIssues = Utility.calculateIssues(testNode);

        assertEquals(14, totalIssues);
    }

    public String getHomeDirectory() {
        return System.getProperty("user.home");
    }
}
